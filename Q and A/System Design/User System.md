## Design User System
### Memcached
### Authentication
### SQL vs NoSQL
### Friendship
## How to scale?
### Sharding
### Consistent Hashing
### Replica

#### Scenario
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
			- disadvantage: unlike above two, memcached does NOT support data persistence in hard disk, once electric cut off, data might be missing and need reload once it's back up
#### Service
- AuthService - register/login
- UserService - user info storage/edit
- FriendshipService - friendship storage
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzk2OTEyMDIxLDE1NjIwNjkxNTUsMTM5Nz
A3MTI2OF19
-->