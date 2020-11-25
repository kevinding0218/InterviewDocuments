### Design A Twitter
#### 4S
- Ask before design
- No more no less
- Done is better than perfect!
- It's all about Trade Off!
- Analysis is important than solution
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
		- **How to Implement Get New Feed**
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
				- **Pull vs Push**
					- Facebook / Twitter - Pull
					- Instragram - Push + Pull
			- Now we should have an executable plan as we discuss with interviewr in previous 3 steps, we would get a work solution but not perfect solution
- **Scale**
	- **Sharding / Optimize / Special Case**
	- Step 1: **Optimize**
		- Solve the drawback in Pull vs Push Model
			- **Solve Pull Model Drawback**
				- The slowest part happens when user is request getNewFeeds
					- **Add Cache in memory before visiting DB**
					- **Cache every user's timeline**
						- N times of DB requests => N times of Cache requests (N is the number of your followings)
						- Trade Off: Cache all tweets / Cache lastest 1000 tweets?
					- **Cache News Feed of every user**
						- User who doesn't have Cache News Feed: merge latest 100 tweets of N user, take the latest 100 from merged result
						- User who has Cache New Feed: merge all tweets of N user after some specific time stamp
				- **Solve Push Model Drawback**
					- Disk is cheap
					- Inactive Users, **rank follower by weight** (e.g: last login time)
					- Followers >> follwings (Lady Gaga)
		- **More features design (Edit, Delete, Media, Ads)**
		- **Special Cases (Lady Gaga, Inactive users)**
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
			- **When to user Pull or Push**
				- User **Pull** when you
					- enough resource
					- **high requirement for real-time**
					- user post a lot tweets
					- single-directional friendship relationship, lady gaga
				- Use **Push** when you
					- less resource
					- **not require for real-time**
					- user post less tweets
					- bi-directional relationship, no lady gaga (like WeChat Friend)
	- Step 2: **Maintenance**
		- Robust (what if one server/database goes down)
		- Scalability ( how to scale if there is request peak)
#### Other question
- **How to implement follow & unfollow**
	- After follow a user, Merge timeline into news feed asynchronously
	- After unfollow a user, Pick out tweets from news feed asynchronously.
	- Why Async? User get action completed immediately
	- Drawback: refresh news feed after unfollow, the tweets are still there for a short time, but will be erased eventually
- **How to store "likes"**
	- Tweet Table (id, user_id<FK>, content, created_at, like_nums, comment_nums, retweet_nums) where like_nums could be de-normalized as
	- Like Table: (id, user_id<FK>, tweet_id<FK>, created_at)
- **Lady Gaga Post II - Thundering Herd**
	- high volumn request at peak from countless user
	- Database cannot take pressure
		- because it's large amount of requests to single common data in a short time, so it's useless with load balancer, sharding, consistent hashing
	- Follow up: like, re-tweet, comment will edit the basic infor about this twee, how to refresh?
	- Follow up: what to do when cache is invalidate?
##### Common QA
- Cache是什么？
	- 你可以认为相当于算法中的HashMap
	- 是Key-value的结构
- Cache存在哪儿？Cache存在内存里
- 常用的Cache工具/服务器有什么？
	- Memcached
	- Redis
- 为什么需要用Cache?
	- Cache因为是内存里，所以存取效率比DB要高
- 为什么不全放Cache里？
	- 内存中的数据断电就会丢失
	- Cache 比硬盘贵
- News Feed 和 Timeline 的定义和区别？
	- News Feed：新鲜事，我朋友+我发的所有帖子按照某种顺序排列的整合（比如按照时间排序）
		- 用户打开Twitter之后首先看到的界面就是News Feed界面，这些 tweets 来自你关注的用户
	- Timeline：某个用户发的所有帖子
		- 用户点开某个人的页面之后，看到这个人发的所有帖子
- News Feed 中 push model 和 pull model 的差別
	- 区别就是有没有 News Feed Table。Pull 模型是没有 News Feed Table的，Pull 只有 Timeline table。有一些网上的 NewsFeed 和 Timeline 的定义和我的定义刚好相反，需要注意一下。

我的 NewsFeed Table 存的是，谁发了什么，谁可以看到。包含三个部分，owner_user_id（这个newsfeed 是发给谁的），user_id 这个内容是谁发的（也可以不存这个，因为 可以根据 tweet_id 去查是谁发的），然后 tweet_id，然后一些其他的内容。

而 Timeline Table 存的是，谁发了什么。也就是主要包含2个部分，user_id和发的内容。也就是说，如果你不做任何优化的话，实际上 Timeline Table 就是 Tweet Table。因为你可以  `select * from tweet_table where user_id=某人`，这就是某人的 timeline 了。

那么我们看看，当你发了一个帖子之后，如果是 pull 模型，那么只需要在 Tweet Table 里增加一个你发的帖子的记录就好了。其他什么也不用做，当你的好友需要看你的帖子的时候，主动去找你发过的最近100条什么的。

而 Push Model 下，你发了一个tweet之后，系统需要主动的 deliver你的这个帖子去到 newsfeed table 里去。比如你有3个好友A,B,C。那么系统需要往 news feed table 里存入 [A+你的帖子], [B+你的帖子], [C+你的帖子] 三条数据。
		- 

<!--stackedit_data:
eyJoaXN0b3J5IjpbOTMwNTI4MTY0LC03MTU4NjAxOCwtMTIxMz
c4OTg2NSwtNzU5Nzg4MTU0LC0xNDg4NDQ4ODM4LC0zNjgxMTk1
OTksLTgxMDMwNTkzNSwtMjA4ODc0NjYxMl19
-->