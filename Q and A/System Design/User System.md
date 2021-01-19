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
		- Peak: 100k * 3 = 300
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjIyNjQwNzIwLDEzOTcwNzEyNjhdfQ==
-->