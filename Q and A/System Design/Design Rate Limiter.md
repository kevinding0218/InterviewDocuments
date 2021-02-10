## Design a RateLimiter
### Scenario
- Restriction on network request features
	- IP, User, Email
- What system would do
	- reject request and return 4xx error code if request more than certain number within certain period
	- 2/s, 5/m, 10/h, 100/d

### Service
- it's a small application or service already

### Storage
- Need to log any feature at any time, did what event
- Such data can keep no more than 1 day (for rate = 5/m, there is no mean after logging for 1 mins)
- Must have high-efficient structure
- So we'll Memcached as storage structure as data doesn't need persist

### Describe
- User event + feature + timestamp as key of memcached
- Record a visit
	- code: memcached.increment(key, ttl=60s)
	- increase visit count of corresponding bucket, and time to live is 60 sec
- Query whether is over limitation
	```
	for t in 0 ~ 59 do
		key = event + feature + (current_timestamp - t)
		sum += memcached.get(key, default=0)
	```
	- sum up all records in most recent 1 mins 
- Example:
	- event = url_shorten
	- feature = 192.168.0.1
	- 00:01:11(100)/00:01:12(0)/00:01:13(1).....00:02:10(10)/00:02:11(3)
	- add value up from 00:01:11 ~ 00:02:11 and compare with condition
### Question: for one day there is 86400 seconds, each time we need query for 86k cache data size
- Store level by level
	- if we take unit as 1 mins, each bucket can set up as 1 sec, one query would read at most 60 times
	- if we take unit as 1 hour, each bucket can set up as per 1 mins, one query would read at most 60 times
	- if we take unit as 1 day, each bucket can set up as 1 hour, one query would read at most 24 times
- If there is minor delay in above case, how to solve it
	- First of all, rate limiter doesn't need 100% accurancy, it's a trade off
	- we can have more accurancey by adding a third level bucket(sec, min, hour)
	- e.g when getting visit count of most recent 1 day, let's say current time is 23:30:33, we can do:
		- in the second bucket, add sum between today's 23:30:00 ~ 23:30:33 (total of 34 queries)
		- in the minute bucket, add sum between today's 23:00 ~ 23:29 (total of 30 queries)
		- in the hour bucket, add sum between today's 00 ~ 22 (total of 23 queries)
		- in the second bucket, add sum between yesterday 23:30:34 ~ 23:30:59 (total of 26 queries)
		- in the minute bucket, add sum between yesterday 23:31 ~ 23:59 (total of 29 queries)
		- total query would be 34 + 30 + 23 + 26 + 29 = 142 queries which is acceptable
- e.g: cannot reset password for more than 5 times in one hour

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTU3Mzk2Mjk5NSwtMjgyMDE5NTUyLDIxMD
A3MjQ3MzIsLTM1ODUxMTg1NCwtMjExMzUwMDcyMCwtMjE1MzEx
MzQwLDczMDk5ODExNl19
-->