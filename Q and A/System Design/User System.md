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
		- QPS: 100M * 0.1 / 86400 ~ 100 query per second (QPS)
		- Peak: 100 * 3 = 300
	- Esitmation of Query
		- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTA4NDY0MDA3NCwxMzk3MDcxMjY4XX0=
-->