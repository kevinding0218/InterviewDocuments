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
			- Web crawler (爬虫系统) & Google Suggestion is write more than read, catch and store web page key words, it doesn't support an api for user to view those contents. e.g: Google search engine use this to store web page in its service then indexing them when you search for certain keywords
			- System for human beings usage is read more than write, for system usage is write more than read
### Service
- AuthService - register/login
- UserService - user info storage/edit
- FriendshipService - friendship storage
### Cache
#### What is cache
- store info that could be used later on first
	- next time when query for such info, can get it directly from here, no need to re-calculation or db query, etc
- can be consider as a software level HashMap
	- Java hashmap is process level
- key-value structure
#### Common cache software
- Memcached (not support data persistence)
- Redis (support data persistence)
#### Does cache always store in memory?
- No, cache doesn't mean it has to be stored in whichever storage
- Memory cache in refer to file system
- File system can used as cache
	- in refer to network, reading faster from local file system 
- CPU can used as cache
	- read from register, L1, L2, L3 cache, faster than memory cache
#### Does cache mean Server Cache?
- No, frontend/client/browser can also have client side cache
#### Example of UserService
```
class UserService:
	def getUser(self, user_id):
		key = "user::%s" % user_id
		user = cache.get(key)
		if user:
			return user
		user = database.get(user_id)
		cache.set(key, user)
		return user
	def setUser(self, user)
		key = "user::%s" % user.id
		cache.delete(key)
		database.set(user)
```
What would happen to below scenario?
- database.set(user); cache.set(key,user);
	- db connection might fail, notify user current action failed and retry, but this should be Ok'
	- if db.set succeeded, but cache.set failed, now db has new data but cache holds old data, next time the get would return old data while data stores inconsistent between db and cache
- cache.set(key, user); database.set(user)
	- if cache.set succeeded, but db.set failed, now cache has new data but dbholds old data, next time the get would return new data while data stores inconsistent between db and cache
- cache.delete(key); database.set(user)
	- it would be Ok if cache failed and database succeeded, as next time the get would still return whatever from db
- database.set(user); cache.delete(key)
	- 
- database should be the source of truth
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjA1MDA2NTM1NCwzNzgyMTY1MjUsMTY5NT
A2NTgzOCwxNTYyMDY5MTU1LDEzOTcwNzEyNjhdfQ==
-->