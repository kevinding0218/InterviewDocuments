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
### Pre-Question to Interviewer
#### What about auto-scaling?
	- problem with scaling up or scaling out is that it is not happening immediately, even autoscaling takes time.
	- And by the time scaling process completes it may already be late. Our service may already crash.
#### What about load balancer?
	- Load balancer will either reject any request over the limit or send the request to a queue, so that it can be processed later, but rate limiter cannot be applied on load balancer level
	- Let’s say our web service exposes several different operations. Some of them are fast operations, they take little time to complete. But some operations are slow and heavy and each request may take a lot of processing power.
	- Load balancer does not have knowledge about a cost of each operation. And if we want to limit number of requests for a particular operation, we can do this on application server only, not at a load balancer level.
	- If we have a load balancer in front of our web service and this load balancer spreads requests evenly across application servers and each request takes the same amount of time to complete - you are right. In this case this is a single instance problem and there is no need in any distributed solution. Application servers do not need to talk to each other. They throttle requests independently.
	- But in the real-world load balancers cannot distribute requests in a perfectly even manner. Plus, as we discussed before different web service operations cost differently. And each application server itself may become slow due to software failures or overheated due to some other background process running on it. All this leads to a conclusion that we will need a solution where application servers will communicate with each other and share information about how many client requests each one of them processed so far.
### Formalize Requirements
#### Functional Requirements
- For a given request our rate limiting solution should return a boolean value, whether request is throttled or not.
#### Non-functional Requirements
- we need rate limiter to be fast (as it will be called on every request to the service), 
- accurate (as we do not want to throttle customers unless it is absolutely required)
- scalable (so that rate limiter scales out together with the service itself). If we need to add more hosts to the web service cluster, this should not be a problem for the rate limiter.
#### What about high availability and fault tolerance?
- Two common requirements for many distributed systems but not so much important to a rate limiter
### Build Solution
#### Start from Simple
- let’s implement a rate limiting solution for a single server first. So, no communication between servers just yet
#### Rules Retriever
- Each rule specifies a number of requests allowed for a particular client per second.
- These rules are defined by service owners and stored in a database.
- And there is a web service that manages all the operation with rules.
- Rules retriever is a background process that polls Rules service periodically to check if there are any new or modified rules. Rules retriever stores rules in memory on the host.
#### Client Identifier
- Let’s call it a key, for short. This may be a login for registered clients or remote IP address or some combination of attributes that uniquely identify the client.
- The key is then passed to the Rate Limiter component, that is responsible for making a decision. Rate Limiter checks the key against rules in the cache. And if match is found, Rate Limiter checks if number of requests made by the client for the last second is below a limit specified in the rule.
	- If threshold is not exceeded, request is passed further for processing.
	- If threshold is exceeded, the request is rejected. Our service may return a specific response status code, for example service unavailable or too many requests. Or we can queue this request and process it later. Or we can simply drop this request on the floor.
#### Summary
- The thinking process has been pretty straightforward. We know we need a database to store the rules. And we need a service on top of this database for all the so-called CRUD operations (create, read, update, delete).
- We know we need a process to retrieve rules periodically. And store rules in memory. And we need a component that makes a decision. You may argue whether we need the client identifier builder as a separate component or should it just be a part of the decision-making component. It is up to you.

<!--stackedit_data:
eyJoaXN0b3J5IjpbMzQxNzM1MzIsNzk1MDg4OTc2LDE1ODYxND
c1NzIsMTMzMTM1MDM4NSwyMDYzMjM3NTMwLC01ODc3MDQxOTRd
fQ==
-->