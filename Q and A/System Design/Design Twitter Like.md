
#### Step 1: Requirements clarifications
Here are some questions for designing Twitter that should be answered before moving on to the next steps:
• Will users of our service be able to post tweets and follow other people?
• Should we also design to create and display the user’s timeline?
• Will tweets contain photos and videos?
• Are we focusing on the backend only or are we developing the front-end too?
• Will users be able to search tweets?
• Do we need to display hot trending topics?
• Will there be any push notification for new (or important) tweets?
#### Step 2: Functional requirements - API
postTweet(user_id, tweet_data, tweet_location, user_location, timestamp, …)
generateTimeline(user_id, current_time, user_location, …)
markTweetFavorite(user_id, tweet_id, timestamp, …)
#### Step 3: Non Functional requirements
- High Scalability
- High Availibility
- High Performant
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwNTgxODAxMjUsMTc1NzgyMTk0MV19
-->