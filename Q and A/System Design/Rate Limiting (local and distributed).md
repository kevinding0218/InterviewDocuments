## [Rate Limiter](https://www.youtube.com/watch?v=FU4WlwfS3G0&t=1s)
### Problem Statement
- Let’s imaging we launched a web application. And the application became highly popular. Meaning that thousands of clients send thousands of requests every second to the front-end web service of our application.
- Everything works well.Until suddenly one or several clients started to send much more requests than they did previously.And this may happen due to a various of reasons.
	- For example, our client is another popular web service and it experienced a sudden traffic spike.
	- Or developers of that web service started to run a load test.
	- Or this is just a malicious client who tried to DDoS our service.
- All these situations may lead to a so called “noisy neighbor problem”, when one client utilizes too much shared resources on a service host, like CPU, memory, disk or network I/O. And because of this, other clients of our application start to experience higher latency for their requests, or higher rate of failed requests.
- One of the ways to solve a “noisy neighbor problem” is to introduce a rate limiting (also known as throttling).
#### Rate Limiting/Throttling
- Throttling helps to limit the number of requests a client can submit in a given amount of time. Requests submitted over the limit are either immediately rejected or their processing is delayed.
### Question to Interviewer
- Can it be solved by scaling out the cluster of hosts that run our web service, by some kind of auto-scaling, right?
	- problem with scaling up or scaling out is that it is not happening immediately, even autoscaling takes time.
	- And by the time scaling process completes it may already be late. Our service may already crash.
	- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTAwNDc1NjE2MiwtNTg3NzA0MTk0XX0=
-->