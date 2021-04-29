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
user_id		varchar	PK/UUID
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
feed_id		varchar/UUID	PK
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
	- Let's add a cache that stores **each user's timeline**
		- Is capacity an issue? Not really, big company should be able to support millions of Cache or distributed cache system
		- N times of DB request become N times of Cache request (N is your follower number)
		- Trade off: Cache all News Feed or Most recent 100/1k News Feed?
	- **Cache every user's news feed**
		- For user that's inactive for a long time, which doesn't have an entry, as soon as he make request, we would load the cache from his N following's and merge the Top K using Pull Approatch
		- For user that's already login and has an entry in the cache, we can just append news feed after a certain timestamp
- **News Feed**：新鲜事，我朋友+我发的所有帖子按照某种顺序排列的整合（比如按照时间排序）
	- 用户打开Twitter之后首先看到的界面就是News Feed界面，这些 tweets 来自你关注的用户
- **Timeline**：某个用户发的所有帖子
	- 用户点开某个人的页面之后，看到这个人发的所有帖子
- 每次发帖的时候会：更新自己的timeline cache
- 每次登陆会：pull following的 timeline, merge, 更新自己的 newsfeed cache

##### Push Model (with Async Fanout and NewsFeed table)
```
select * from news_feed where owner_id = A order by created_at desc limit 20;
NewFeeds Table
feed_id		varchar		PK
owner_id	varchar		FK
content		BLOB(binary large object)
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
- Normal user (who doesn't have a pretty large amount of followers) can use with Push
- Special user like celebrities (who has a large number of followers), we marked them as Star/VIP user
	-	For those users, we're not pushing their Post into followers' new feed, instead. when user request the feed, we are doing a Pulling from those VIP
###### Summary
- When to use Pull?
	- When we have enough resource
	- When we need real-time requirements
	- User post frequently
	- Single direction friendship/Celebrity User, etc
- When to use Push?
	- When we have limited resource
	- Real-time requirement is not needed
	- User post less (more read duration)
	- Round trip friendship/No Celebrity User
##### Media Service
- File/Photo can be stored in File System like AWS S3 or Azure Blob Storage
- We could also have a cache to load user most recent N posts such as LRU
- We could also Archieve the file after a certain time period
### Other Topic
#### 如何实现 follow 与 unfollow?
- Follow 一个用户之后，异步地将他的 Timeline 合并到你的 News Feed 中
	- Merge timeline into news feed asynchronously.
- Unfollow 一个用户之后，异步地将他发的 Tweets 从你的 News Feed 中移除
	- Pick out tweets from news feed asynchronously.
- 为什么需要异步 Async？
	- 因为这个过程一点都不快呀
- 异步的好处？
	- 用户迅速得到反馈，似乎马上就 follow / unfollow 成功了
- 异步的坏处？
	- Unfollow 之后刷新 News Feed，发现好像他的信息还在
	- 不过最终还是会被删掉的
#### 如何存储 likes?
- Table Schema
```
Feeds Table
feed_id		varchar/UUID	PK
user_id		varchar			FK
content		BLOB(binary large object)
create_at 	time_stamp		indexing
feed_type	smallint		FK	(Photo/Article/Video, etc)
like_num	integer			De-normalize
comment_num integer
share_num	integer

Like Table	(NoSQL)
like_id		integer
user_id		varchar			FK
feed_id		varchar			FK
create_at	timestamp
```
#### NewsFeed如何实现pagination
- 问：**是不是不管push还是pull模型，如果翻页的话都得pull?**  
	- 翻页是用户主动操作的过程，所以肯定是由client 发给 server，肯定是一个pull的过程。
- 问：**假设前100条中最早的timestamp是T，就分别请求follow的人在T之前的100条feed，然后再进行合并？**  
	- 答：对
- 问：**如果恰好有几条feed的timestamp一样该如何处理？**  
	- 答：首先不会有帖子的timestamp一样，timestamp的精度很高的（微秒级别）
	- 通常来说，翻页这个完全可以作为一道单独的系统设计面试题来问你。翻页并不是简单的1-100，101-200这样去翻页。因为当你在翻页的时候，你的news feed可能已经添加了新的 内容，这个时候你再去索引最新的101-200可能和你的1-100就有重叠了。
	- 通常的做法是，拿第101个帖子的timestamp作为下一页的起始位置，也就是说，当用户在看到第一页的前100个帖子的时候，他还有第101个帖子的timestamp信息（隐藏在你看不到的地方），然后你请求下一页的时候，会带上这个timestamp的信息，server端会去数据库里请求 >= timestamp 的前101个帖子，然后也同样把第101个帖子作为下一页的timestamp。这个方法比直接用第100个帖子的timestamp好的地方是，你如果读不到第101个帖子，说明没有下一页了，如果你刚才只有100个帖子的话，用第100个帖子的timestamp的坏处是，你会有一次`空翻`。
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTgxMTQyNzA1MywtMjAzNTU3Mjc0OSwtMT
Y3Mjg1MTI0MiwxMDUyNDU4ODQwLC0yMDAwMTU5MTA1LDE3NjMw
MDQ3MDksMTEyNDc3MjE0MSwtMTAyNDkxMzgwNywtMjEyNDMzMj
QyMCwtMjgwOTUzNzk0LDM1NDM3Mzc0NiwtMTUwMzY1MTU3Niwx
ODA1MDI2MzI0LDkyNTU3MDQ4MiwtMjA0NTk1MTY3NywtOTA2Mz
M4NTQwLC0zNzg1MTY2MDhdfQ==
-->