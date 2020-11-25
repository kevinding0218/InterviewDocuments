### Design A Twitter
#### 4S
- Scenario
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
- Service
	- Split / Application / Module
- Storage
	- Schema / Data / SQL / NoSQL / File System
- Scale
	- Sharding / Optimize / Special Case

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTc0NDE1Nzc2MCwtMjA4ODc0NjYxMl19
-->