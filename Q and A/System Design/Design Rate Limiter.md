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
### Interview: How to limit request?
- e.g: cannot reset password for more than 5 times in one hour

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxMTM1MDA3MjAsLTIxNTMxMTM0MCw3Mz
A5OTgxMTZdfQ==
-->