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
- Such data can kee
### Interview: How to limit request?
- e.g: cannot reset password for more than 5 times in one hour

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTUwNzM1MjUwMCwtMjE1MzExMzQwLDczMD
k5ODExNl19
-->