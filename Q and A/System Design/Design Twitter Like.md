
#### Step 1: Requirements clarifications
Here are some questions for designing Twitter that should be answered before moving on to the next steps:
• Will users of our service be able to post tweets and follow other people?
• Should we also design to create and display the user’s timeline?
• Will tweets contain photos and videos?
• Are we focusing on the backend only or are we developing the front-end too?
• Will users be able to search tweets?
• Do we need to display hot trending topics?
• Will there be any push notification for new (or important) tweets?
#### Step 2: Functional requirements
##### API
```
postTweet(user_id, tweet_data, tweet_location, user_location, timestamp, …)
generateTimeline(user_id, current_time, user_location, …)
markTweetFavorite(user_id, tweet_id, timestamp, …)
```
##### Data Model
User: UserID, Name, Email, DoB, CreationData, LastLogin, etc.
Tweet: TweetID, Content, TweetLocation, NumberOfLikes, TimeStamp, etc.
UserFollowo: UserdID1, UserID2
FavoriteTweets: UserID, TweetID, TimeStamp
#### Step 3: Non Functional requirements
- High Scalability
- High Availibility
- High Performant
• What scale is expected from the system (e.g., number of new tweets, number of tweet views,
number of timeline generations per sec., etc.)?
• How much storage will we need? We will have different numbers if users can have photos and
videos in their tweets.
• What network bandwidth usage are we expecting? This will be crucial in deciding how we will
manage traffic and balance load between servers.
#### Step 4: High level architecture
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE4MTc2OTI5MTcsNTM3MjQyODgzLC0yMD
U4MTgwMTI1LDE3NTc4MjE5NDFdfQ==
-->