### Design A Twitter
#### 4S
- It's all about Trade Off!
- **Scenario**
	- Ask / Features/ QPS (Queries Per Second) / DAU (Daily Active Users) / Interfaces
	- List features of twitter such as
		- Register / Login
		- User Profile Display / Edit
		- Upload Image / Video
		- Search
		- Post / Share a tweet
		- Timeline / News Feed
		- Follow / Unfollow a user
	- Sort them and pick up the core feature as you cannot design everything in such a short time
		- Post a Tweet
		- Timeline
		- News Feed
		- Follow / Unfollow a user
		- Register / Login
		- Concurrent User (mock up the process)
			- Daily Active User * Request Per User / Seconds Per Day = 150 M * 60 / 86400 ~ 100K
			- Peak = Average Concurrent User * 3 ~ 300K
			- Read QPS ~ 300K
			- Write QPS ~ 5K
		- QPS usage
			- 100 ~ use a laptop/desktop as Web Server
			- 1K ~ use a better Web Server, need to consider Single Point Failure
			- 1M ~ use a cluster of 1000 Web Server, need consider how to maintain those server if any of them doesn't work
		- QPS vs Web Server vs Database
			- Web Server ~ 1K QPS (including bottleneck of logic processing time and database query)
			- SQL Database ~ 1K QPS (could be less if there is more JOIN or Index query)
			- NoSQL(Cassandra) ~ 10K QPS
			- NoSQL(Memcached) ~ 1M QPS
- **Service**
	- Split / Application / Module
		- **Replay** : go over every requirement and add a service for each requirement
		- **Merge**: merge common function as a service
		- Router
			- User Service: Register / Login
			- Tweet Service: Post a tweet / News Feed / Timeline
			- Media Service: Upload Image / Upload Video
			- Friendship Service: Follow / Unfollow
- **Storage**
	- Schema / Data / SQL / NoSQL / File System
	- **Select** Storage structure for every Service
		- SQL Database
			-User Service/Friendship Service/Tweet Service)
			- User Table (id, username, email, password)
			- Friendship Table (from_user_id<FK>, to_user_id<FK>)
			- Tweet Table (id, user_id<FK>, content, created_at)
		- NoSQL Database
			- Tweet Service/Friendship Service
			- Tweets / Social Graph
		- File System
			- Media Service
			- Image / Video / Media files 
	- Go draw detailed **Schema**
		- New Feed
			- What is New Feed
				- the information stream when you login in Facebook / Twitter / Wechat, the union of all the message sent by your friends
				- **Pull Model**
					- **Description**: When user is requesting News Feed, get first 100 tweets of every friend, merge them and sort the first 100 News Feed (Merge K Sorted Arrays)
					- **Process**:
						1. Send Get News Feed to Web Server
						2. Server goes to Friendship Table and get followings list
						3. Server goes to Tweet Table and get tweets from followings
						4. Merge and return to UI
					- **Complexity**: 
						- Get news feed => if user has N friends, then it will take N times of DB Reads + O(MergeKSortedArray) which could be ignored
						- Post a tweet => 1 time of DB Write
					- **Drawback**:
						- getNewsFeed(request)
							- follwings = DB.getFollowings(user = request.user)
							- news_feed = empty
							- for follow in followings:
								- tweets = DB.getTweets(follow.to_user, 100): **N times of DB read is kinda slow, which is happening during getNewsFeed Request**
								- news_feed.merge(tweets)
							- sort(news_feed)
							- return news_feed
						- postTweet(request, tweet)
							- DB.insertTweet(request.user, tweet)
							- return success
				- **Push Model**
					- **Description**: Create a list for every user to store user's followings' news feed, whenever a user post a tweet, Fanout this tweet to every user's new feed list, when user needs to getNewsFeed, just ready the latest 100 from News Feed List
					- News Feed Table (id, owner_id<FK>, tweet_id<FK>, created_at), owner_id will be the fan_out of self and its followings user id
					- **Process**:
						1. Send Post tweet to Web Server
						2. Insert the tweet to DB Tweet Table
						3. Publish message to MQ: send tweets to my friends/followers
						4. Async Tasks Server: Get friends/followers
						5. Fanout in MQ: Insert new tweet to follwers DB News Feed Table
					- **Complexity**
						- News Feed => 1 time of DB Read
						- Post a tweet => N times of DB write if there is N follwings, the benefit is that this could be an asychronized task running in background, no need to have user wait when post a tweet
					- **Drawback**:
						- getNewsFeed(request)
							- return DB.getNewsFeed(request.user)
						- postTweet(request, tweet_info)
							- tweet = DB.insertTweet(request.user, tweet_info)
							- AsyncService.fanoutTweet(request.user, tweet)
							- return success
						- asyncService::fanoutTweet(user, tweet)
							- followers = DB.getFollowers(user)
							- for follwer in followers:
								- DB.insertNewsFeed(tweet, follower)	<= **the number of followers could be pretty big**
				- Pull vs Push
					- Facebook / Twitter - Pull
					- Instragram - Push + Pull
			- Now we should have an executable plan as we discuss with interviewr in previous 3 steps, we would get a work solution but not perfect solution
- Scale
	- Sharding / Optimize / Special Case
	- Step 1: Optimize
		- Solve the drawback in Pull vs Push Model
			- **Solve Pull Model Drawback**
				- The slowest part happens when user is request getNewFeeds
					- **Add Cache in memory before visiting DB**
					- Cache every user's timeline
						- N times of DB requests => N times of Cache requests (N is the number of your followings)
						- Trade Off: Cache all tweets / Cache lastest 1000 tweets?
					- Cache News Feed of every user
						- User who doesn't have Cache News Feed: merge latest 100 tweets of N user, take the latest 100 from merged result
						- User who has Cache New Feed: merge all tweets of N user after some specific time stamp
				- Solve Push Model Drawback
					- Disk is cheap
					- Inactive Users, rank follower by weight (e.g: last login time)
					- Followers >> follwings (Lady Gaga)
		- More features design (Edit, Delete, Media, Ads)
		- Special Cases (Lady Gaga, Inactive users)
			- For some user who has followers >> followings, fanout in Push could take up to couple of hours
				- Wrong answer: switch to Pull
				- Correct answer: try as min change to optimize current plan
					- e.g: add more machines to do the Push Async Task
				- Evaluate for longer-term whether it's worth to converting entire model
			- **Push + Pull Plan**!
				- **Normal user still use Push**
				- **Lady Gaga as Star User which followers > 1m, do not use Push to NewsFeed Table**
				- **When normal user gets new tweets, Pull from Star User's timeline and merge into his news feed**
				- If a star user's follower number decrease that doens't match a star user, we don't need to change original code but checking the current status in timeline to determine whether Pull or not
			- When to user Pull or Push?
				- User Pull when you
					- enough resource
					- high requirement for real-time
					- user post a lot tweets
					- single friendship relationship, lady gaga
				- Use Push when you
					- less resource
					- not require for real-time
					- user post less tweets
					- bi-directional relationship, no lady gaga (like WeChat Friend)
	- Step 2: Maintenance
		- Robust (what if one server/database goes down)
		- Scalability ( how to scale if there is request peak)

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTY0ODQ1NTg3OCwtNzU5Nzg4MTU0LC0xND
g4NDQ4ODM4LC0zNjgxMTk1OTksLTgxMDMwNTkzNSwtMjA4ODc0
NjYxMl19
-->