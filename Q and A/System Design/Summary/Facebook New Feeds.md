### Requirements/Goals
#### Function Requirements
- User Login/Logout
- A user may have many friends and follow a large number of pages/groups
- Newsfeed will be generated based on the posts from the people, pages or groups that a user follows
- Service should support appending new posts as they arrive to the newsfeed for all active user
- News feed would be sorted by timeline
- Post feeds can be liked by other users
- Other functions like search/left comments/like comment/tagging user/share post, etc
#### Non-functional requirements
- Let's do some analysis on the Daily Active User (DAU) or Monthly Active User (MAU)
	- **QPS**: DAU * Request per day per user(request not only about news feed but things like login/like/comment/post, etc) 1M * 60 / 86400 (total seconds) ~ 100k
	- **Peak QPS** ~ 300k
	- High QPS determines that we may need a cluster of 500 ~ 1k web servers to handle that request (considering of business logic and bottleneck of database query)
- Let's also do some estimation on storage
	- How many new photo will be uploaded every day or every second, what's going to be the average photo file size
	- Total space required for 1 day of photo/1 year/5 years?
	- For this news feed section, we can think of this would be a heave read and light write QPS
- When we talked about designing it as distributed system, which I assume it's something we're looking forward in today's design, we need to consider of
- **High scalability**: supports an arbitrarily large number of posts or able to **handle load increase**
- **High availability**: **survives hardware/network failures**
- **High performant**: keep end-to-end **latency as low as possible**
 - **CAP(Consistency, Availability and Partition tolerance)** theorem tells us that we should be choosing between Availability and Consistency, **discuss with interviewer**
	- If we choose **Availability** over Consistency, it simply means we prefer to **show stale (not up-to-date) data than no data at all**.
	- On the other hand of choosing **Consistency**: Synchronous data replicate is slow, we usually replicate data asynchronously, if a user doesn't see a photo for a while, should it be fine or not?
- **Data Durable** is also something we need to think of, which refers to the **system being highly reliable**, any uploaded **feed should never be lost**
- **Cost**(hardware, development, maintenance), but we might ignore that for now
- **Let's revisit those once we finialize more details with our design**
### High level Architecture
```
User A -> Browser -> Add/Update Post/Follow 						  User(SQL)
									\								/ Friendship -> Cache -> Metadata Storage(SQL/NoSQL)
									VIP -> LB -> API Gateway/Router - Feeds	-> Cache -> Post Storage (NoSQL)
																	\ Media Service	-> Cache ->			Media Storage(File DB)
																			|
																		Query Service -> Notification Service -> User B/C
						
Client -> Browser -> VIP -> LB -> (API gateway/Router) -> User(SQL)/Friendship(SQL/NoSQL)/Feeds(NoSQL)/Media(File DB) Service    -> Database -> Query Service -> Browser -> Client
```
### Detail Analysis of each component in architecture
#### VIP/virtual IP
- refers to the symbolic hostname (for example myWebService.domain.com) that resolves to a load balancer system.
#### LB/Load Balancer
- All requests coming from our clients will go through a load balancer first. This will ensure requests are equally distributed among requests processing servers.
#### API gateway
- An API management tool that sits between a client and a collection of backend services, acts as a reverse proxy to accept all, aggregate the various services required to fulfill them, and return the appropriate result, in a microservices architecture, in which case a single request could require calls to dozens of distinct applications.
####  Service - Storage as well as API/Schema design
##### Storage (SQL vs NoSQL vs File System)
- Relational Database 
- NoSQl
- File System
##### User Service
- **@Post registerUser(RegistrationInfo regInfo)**, the `RegistrationInfo` may include information such as username/email/password/confirmedPassword/contactInfo, etc)
- **@Post loginUser(LoginInfo logInfo)**, the `LoginInfo` may include information such as `username/email/password`, it may return an JWT token for further API request authentication and authorization
- **User info are tend to be structure** so we can just use a SQL Database to save such info in a user table, each user would be assigned with **an unique UserId/UUID** in **Metadata DB**
```
User Table
id			varchar	PK/UUID
username	varchar
email		varchar
password	varchar
lastLogin	timestamp
```
##### Friendship Service
- **@Post createConnection(UUID from_user_id, UUID to_user_id)**, build a mapping relationship between User A and User B, which would return a Http Status of 200 if disconnect succesfully or 422 if unprocessable
- **@Get getConnections(UUID from_user_id)** which would return a list of UUID of user_id that the `from_user_id` is following.
- **@Delete removeConnection(UUID from_user_id, UUID to_user_id)**, which would return a Http Status of 200 if disconnect succesfully or 422 if unprocessable
- F**riendship relationship can be stored in in SQL like as well as NoSQL using Graph DB**, for considering data durability and performance
	- when we consider SQL or NoSQL for storing messages, we need to think of **if we need ACID transactions, if we need to run complex dynamic queries, if we would use this storage for analytics or data warehousing.**
```
// Friendship Table
from_user_id	varchar		FK
to_user_id		varchar		FK
update
// NoSQL Schema
user1 {
  _id: "user1",
  name: "john smith",
  email: "blah@test.com,
  friends: [
    "user2",
    "user3",
    "user4",
  ]
}
```
##### Feeds Service
- **@Get getTopFeeds(UUID user_id)**: return Top K news feed for user_id's following's feeds
- **@Post createNewFeed(FeedInfo feedInfo)**: return Http status
- **@Get getBatchFeeds(Collection< UUID> user_ids)**: return a list of Top K news feed for each requested user in response
```
Feeds Table
id			varchar/UUID	PK
user_id		varchar			FK
content		BLOB(binary large object)
create_at 	time_stamp		indexing
feed_type	smallint		FK	(Photo/Article/Video, etc)
```

##### Pull Model
1. User A request "Give me new feed" using `getRecentFeeds`
2. Get followings User Ids from Friendship Service/Table using `getConnections`
3. Get followings **Top N feeds from Feed Service for each User Id**, **ordered by create_at** timestamp
4. **Merge K sorted feed list** and return the Top 100 NewsFeeds after merging
```
getNewsFeed(request)
	followings = DB.getFollowings(user=request.user)
	news_feed = empty
	for follow in followings:
		tweets = DB.getTweets(follow.to_user, 100)
		news_feed.merge(tweets)
	sort(news_feed)
	return news_feed

postTweet(request, tweet)
	DB.insertTweet(request.user, tweet)
return success
```
###### Time Complexity
- **For Get Feeds**: suppose I have N followings, then it's going to be **N * DB Reads + O(kn*logK)**, here the merge K might be ignored as it's processing in memory. **In real time experience through profiling, we know that there are some facts taking an obvious duration such as Object Deserialization and I/O connection**
- **For Post a Feeds**: Only 1 time DB write (**Advantage**)
###### Disadvantage
- N times of DB reads is very slow, and it has to process during user request the news feed
- New data might not be shown to the users until they issue a pull request
- It’s hard to find the right pull time, as most of the time pull requests will result in an empty response if there is no new data, causing waste of resources
###### Improve Pull
- The slowest/longest duration was taken while user is reading requests
	- Let's add a cache that stores each user's timeline
		- Is capacity an issue? Not really, big company should be able to support millions of Cache or distributed cache system
	- N times of DB request become N times of Cache request (N is your follower number)
		- Trade off: Cache all News Feed or Most recent 100/1k News Feed?
	- Cache every user's news feed
		- For user that's inactive for a long time, which doesn't have an entry, as soon as he make request, we would load the cache from his N following's and merge the Top K using Pull Approatch
		- For user that's already login and has an entry in the cache, we can just append news feed after a certain timestamp

##### Push Model (with Async Fanout and NewsFeed table)
```
select * from news_feed where owner_id = A order by created_at desc limit 20;
NewFeeds Table
feed_id		varchar		PK
owner_id	varchar		FK
tweet_id	varchar		FK
create_at	timestap
```
1. User A post a new feed
2. New feed will be inserted into DB through Feeds Service
3. Feeds Service Send a message of the new feed to A's friends through Aysnc Task
4. Feeds Consumer will get A's followers from Friendship Service/Table
5. Feeds Consuer perform a Fan-out to insert that new feed to each followers' new feed list
```
getNewsFeed(request)
	return DB.getNewsFeed(request.user)

postTweet(request, tweet_info)
	tweet = DB.insertTweet(request.user, tweet_info)
	AsyncService.fanoutTweet(request.user, tweet)
	return success

AsyncService::fanoutTweet(user, tweet)
	followers = DB.getFollowers(user)
	for follower in followers:
		DB.insertNewsFeed(tweet, follower)
```
- The **advantage** is that It significantly reduces read operations.
###### Time Complexity
- **Get Feed: Only 1 DB Read**
- **Post Feed**: N times of DB Writes, benefit is it can be done within consumer as async task, user doesn't have to wait
###### Disadvantage
- A possible problem with this approach is that when a user has millions of followers (a celebrity-user), the server has to push updates to a lot of people.
- DB write is usually slower than DB Read
- Waste of request send to inactive user
###### Improve on Push
- Adding more service instance during busy hours to scale out
#### Push + Pull
- Normal user (who doesn't have a pretty large amount of followers) 

<!--stackedit_data:
eyJoaXN0b3J5IjpbNTYzNDMzMzg5LDEwNTI0NTg4NDAsLTIwMD
AxNTkxMDUsMTc2MzAwNDcwOSwxMTI0NzcyMTQxLC0xMDI0OTEz
ODA3LC0yMTI0MzMyNDIwLC0yODA5NTM3OTQsMzU0MzczNzQ2LC
0xNTAzNjUxNTc2LDE4MDUwMjYzMjQsOTI1NTcwNDgyLC0yMDQ1
OTUxNjc3LC05MDYzMzg1NDAsLTM3ODUxNjYwOF19
-->