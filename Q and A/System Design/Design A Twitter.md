### Design A Twitter
#### 4S
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
				- Push Model
					- **Description**: Create a list for every user to store user's followings' news feed, whenever a user post a tweet, Fanout this tweet to every user's new feed list, when user needs to getNewsFeed, just ready the latest 100 from News Feed List
					- Process:
					- Complexity
						- News Feed => 1 time of DB Read
						- Post a tweet => N times of DB write if there is N follwings, the benefit is that this could be an asychronized task running in background, 
					- 
- Scale
	- Sharding / Optimize / Special Case

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTI2NzE2Nzg4MSwtODEwMzA1OTM1LC0yMD
g4NzQ2NjEyXX0=
-->