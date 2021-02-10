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
### Interview: How to limit request?
- e.g: cannot reset password for more than 5 times in one hour

<!--stackedit_data:
eyJoaXN0b3J5IjpbNTMyNTA2NDE4LC0yMTEzNTAwNzIwLC0yMT
UzMTEzNDAsNzMwOTk4MTE2XX0=
-->