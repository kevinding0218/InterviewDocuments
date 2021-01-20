## Design User System
### Memcached
### Authentication
### SQL vs NoSQL
### Friendship
## How to scale?
### Sharding
### Consistent Hashing
### Replica

## Memcached
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
- database.set succeeded but cache.delete failed, now cache still holds old data, as next time the get would still return old data
- database should always be the source of truth
What happens when setUser occurs right after user = database.get(user_id) in getUser?
- This might occur minor chance
Inconsistency between cache and database
- This is not avoided, often we add a timeout in cache.set(key, user, ttl=5mins) to minimize the inconsistancy scenarios, called eventually consistent

## Authentication Service
### How to persist user login
### Session Table
|column name|data type|usage
|--|--|--|
|session_key|string|a unique global hash key
| user_id | Foreign key | point to User Table
|expire_at|timestamp|when this key expire
### After user login
- Create a session object
- return session_key as cookie value to browser
- browser save this session_key in browser cookie
- every time user sends request to server, will automatically bring **all** cookie of current website
	- do not store anything in cookie, which would make the request data size too big
- server received request and will validate the session_key in cookie to consider if user has been logged in
### After user logout
- delete according data in session table
### Where does Session Table store
- database
- cache
	- when system goes down or needs restart, while a log of user tries to login, will create too many write requests peak
- both: normally stores in database, and use cache for performance improvement

## Friendship Service
### Single Direction (Twitter, Instagram, Weibo)
|column_name|key_type|usage
|--|--|--|
|from_user_id|Foreign Key|user entity
|to_user_id|Foreign Key| user to be followed
### Bi-direction (WhatsApp, Facebook, Wechat)
- Solution 1: use above table and store two message that A to B and B to A
	- Friendship Table
	
| from_user_id | to_user_id |
|--|--|
| A | B |
| B | A |
| A | C |
| C | A |
| B | C |
| C | B |
	- Query B's friends
	SELECT * FROM friendship WHERE from_user_id = B - return A, C
- Solution 2: save as one message, but need to check twice when query
### Friendship actions
- Most of them are key-value
1. Query all users that user A follows
2. Query all followers after user A
3. A follows B --> insert a record
4. A un-follow B --> delete a record
### NoSQL Data Structure
Cassandra is a 3-level NoSQL database
1. First Level: row_key
	- also known as hash_key, tells you data stored in which machine
	- same key as we know in traditional key-value
	- Always attach this key in any query, no ways to perform range query
	- common row_key: user_id
2. Second Level:  column_key
- Sorted, can perform range query
- can be a composite value such as: timestamp+user_id
3. Third Level: value
- usually String
- you need to do Serialization if stored too many info
	- Serialization is the process of converting an object hash into a string
- Friendship Example
	- row_key: user_id
	- col_key: other_user_id
	- value: is_mutual_friend, is_blocked, time_stamp, etc
- NewsFeed Example
	- row_key: owner_id
	- col_key: <created_at + tweet_id>
	- value: tweet_data1

## SQL vs NoSQL
1. Most scenario it's ok to use either SQL or NoSQL
2. Do **Not** choose NoSQL if needs support **Transaction**
```
Transaction Example: Transfer $10 from A to B
A.money -= 10
B.money += 10
two action must be completed in one transaction
```
3.  SQL does more things for you than NoSQL (Serialization, Secondary Index)
4. User NoSQL if looking for better perfornamce, or query is not too complex, hard disk NoSQL is 10 times faster than SQL

## Summary
### for User System
- Write less
- Read more
### Write less
- from the perspective of QPS, one MySql could handle it
### Read more
- we can use Memcached to improve the performance of read
### What if read and write a lot
- Solution 1: we can use more database server to take over the stream
- Solution 2: we can use Cache-through database like Redis which read/write both fast, Memcached is a cache-aside database, client needs to manage data loading when cache-missing
### Cache Aside
- DB <-> Web Server <-> Cache
- Server communicate with DB and Cache seperately
- No direct communication between DB and Cache
- Example: Memcached + MySQL/Couchbase + Sql Server
### Cache Through
- Web Server <-> Cache <-> DB
- Server only communicate with Cache
- Cache needs to communicate with DB and persist data
- Example: Redis (Redis contains a cache and a DB)

## How to scale?
- What to consider except QPS and storage after cache?
	- Single point failure: What if the current database goes down
	- temporarily down: website unavailable
	- permanent down: data might be lost
1. Data Sharding
	- Separate data into different parts and store in different machines by certain rule
	- in SQL vs NoSQL
		- SQL does not have sharding
		- NoSQL have self-sharding
	- Vertical Sharding
		- Simple example:
			- User table in database A
			- Friendship table in database B
			- Message table in database C
		- Complex example:
			- For a user table that has (email, username, password, push_preference, avatar)
			- we know that for email/username/password won't change often, while push_preference, avatar could be updated frequently
			- split them into two tables: user table and user profile table
			- and store these two tables in different database
			- so if user profile table goes down, doesn't affect user table to login
		- Disadvantage:
			- 
	- How to store data seperately in different machines
		- 
2. Replica
	- 一式三份
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTk0NjQwMDcwNiwxNTI3OTU2NTU4LDcxMT
cyNzQ4MiwyNzQyMzkwMzEsMTIwMzk0NDI3NCwtMjExODY1NTAz
MiwtMjU4NzA3MTMwLDY4ODgyMTAyOCwxMjgwNjQ1MDU5LC0zMT
U5NDMzNSwtMzkwMzgzMzU4LDIwOTIyODMzOTEsNTY5NTk4MjAz
LDE0MTk5OTQ4NzksMjA1MDA2NTM1NCwzNzgyMTY1MjUsMTY5NT
A2NTgzOCwxNTYyMDY5MTU1LDEzOTcwNzEyNjhdfQ==
-->