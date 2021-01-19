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
	- Estimation of register, login and profile edit
		- 0.1 = for average time per user login+register+edit per day
		- QPS (query per second): 100M * 0.1 / 86400 ~ 100 QPS
		- Peak: 100 * 3 = 300
	- Esitmation of Query
		- 100 = times of query user profile per day per user (check friends, send message, update blog, etc)
		- QPS (query per second): 100M * 100 / 86400 ~ 100k QPS
		- Peak: 100k * 3 = 300k
	- QPS vs comon
	- MySql/PosgreSQL/Sql Server: 1k QPS ~ we need 300 - 600 MySql
	- MongoDB/Cassandra: 10k QPS
	- Redis/Memcached: 100k - 1M QPS
#### Service
- AuthService - register/login
- UserService - user info storage/edit
- FriendshipService - friendship storage
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwMTU0MTQ0ODgsMTM5NzA3MTI2OF19
-->