## Design User System
### Memcached
### Authentication
### SQL vs NoSQL
### Friendship
## How to scale?
### Sharding
### Consistent Hashing
### Replica

### Scenario
- Register, Login, Query & ProfileEdit, Query needs most requests
- Interviewer: support 100M daily active user
	- Estimation of register, login and profile edit (SQL DB)
		- 0.1 = for average time per user login+register+edit per day
		- QPS (query per second): 100M * 0.1 / 86400 ~ 100 QPS
		- Peak: 100 * 3 = 300
	- Esitmation of Query (Memory NoSQL)
		- 100 = times of query user profile per day per user (check friends, send message, update blog, etc)
		- QPS (query per second): 100M * 100 / 86400 ~ 100k QPS
		- Peak: 100k * 3 = 300k
	- QPS vs common storage system
		- MySql/PosgreSQL/Sql Server **(SQL DB): 1k QPS** ~ we need 300 - 600 MySql
		- MongoDB/Cassandra **(Hard disk NoSQL DB): 10k QPS**
		- Redis/Memcached **(Memory NoSQL DB): 100k - 1M QPS**
			- **disadvantage**: unlike above two, memcached does NOT support data persistence in hard disk, once electric cut off, data might be missing and need reload once it's back up
			- User system is read more than write, for a system that works like that, must use Cache for performance improvement
			- 爬虫系统 is write more than read, catch and store web page key words, it doesn't support an api for user to view those contents. e.g: Google search engine use this to store web page in its service then indexing them when you search for certain keywords
			- System for human beings usage is read more than write, for system usage is write more than read
### Service
- AuthService - register/login
- UserService - user info storage/edit
- FriendshipService - friendship storage
### Cache
#### What is cache
- store info first that can be used later
	
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE4Mzg3MTg3NDMsMTY5NTA2NTgzOCwxNT
YyMDY5MTU1LDEzOTcwNzEyNjhdfQ==
-->