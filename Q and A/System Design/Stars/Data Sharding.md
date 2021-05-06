### Data Sharding
#### Sharding based on UserID: 
- We can try storing all the data of a user on one server. While storing, we can pass the UserID to our hash function that will map the user to a database server where we will store all of the user’s tweets, favorites, follows, etc.
	- What if a user becomes hot? There could be a lot of queries on the server holding the user. This high load will affect the performance of our service.
	- Over time some users can end up storing a lot of tweets or having a lot of follows compared to others. Maintaining a uniform distribution of growing user data is quite difficult.
#### Sharding based on XXXID
- Our hash function will map each TweetID to a random server where we will store that Tweet. To search for tweets, we have to query all servers, and each server will return a set of tweets. A centralized server will aggregate these results to return them to the user.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTMwNDY2NjMxMV19
-->