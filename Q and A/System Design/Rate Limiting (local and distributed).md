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
- We know we need a process to retrieve rules periodically. 
- And store rules in memory. 
- And we need a component that makes a decision. 
- You may argue whether we need the client identifier builder as a separate component or should it just be a part of the decision-making component. It is up to you.
### Algorithm
- Google Guava RateLimiter class.
- Fixed and sliding window paradigms
#### Simplest Token Bucket
##### Idea
- The token bucket algorithm is based on an analogy of a bucket filled with tokens.
- Each bucket has three characteristics: a maximum amount of tokens it can hold, amount of tokens currently available and a refill rate, the rate at which tokens are added to the bucket.
- Every time request comes, we take a token from the bucket. If there are no more tokens available in the bucket, request is rejected. And the bucket is refilled with a constant rate.
##### Implementation
```
public class RateLimiterTokenBucket {  
  private final long maxBucketSize;  
    private final long refillRate;  
  
    private double currentBucketSize;  
    private long lastRefillTimestamp;  
  
    public RateLimiterTokenBucket(long maxBucketSize, long refillRate) {  
  this.maxBucketSize = maxBucketSize;  
      this.refillRate = refillRate;  
  
        // Numbers of tokens initially is equal to the maximum capacity  
	  currentBucketSize = maxBucketSize;  
        // Current time in nanoseconds  
	  lastRefillTimestamp = System.nanoTime();  
    }  
  
  /**  
 * Synchronized, as several threads may be calling the method concurrently */  
 public synchronized boolean allowRequest(int tokens) {  
	  // First, refill bucket with tokens accumulated since the last call  
	  refill();  
      // If bucket has enough tokens, call is allowed  
	  if (currentBucketSize > tokens) {  
		  currentBucketSize -= tokens;  
            return true;  
      }  
      // Request is throttled as bucket does not have enough tokens  
      return false;  
}  
  
  private void refill() {  
		  long now = System.nanoTime();  
        // These many tokens accumulated since the last refill  
 // 1e9 ~ 10^9  double tokensToAdd = (now - lastRefillTimestamp) * refillRate / 1e9;  
        // Number of tokens should never exceed maximum capacity  
  currentBucketSize = Math.min(currentBucketSize + tokensToAdd, maxBucketSize);  
        lastRefillTimestamp = now;  
    }  
}
```
- There are 4 class fields: maximum bucket size, refill rate, number of currently available, tokens and timestamp that indicates when bucket was last refilled.
- Constructor accepts two arguments: maximum bucket size and refill rate. Number of currently available tokens is set to the maximum bucket size. And timestamp is set to the current time in nanoseconds.
- Allow request method has one argument - number of tokens that represent a cost of the operation. 
	- Usually, the cost is equal to 1. Meaning that with every request we take a single token from the bucket. But it may be a larger value as well.
	- For example, when we have a slow operation in the web service and each request to that operation may cost several tokens.
	- The first thing we do is refilling the bucket. And right after that we check if there are enough tokens in the bucket.
	- In case there are not enough tokens, method return false, indicating that request must be throttled.
	- Otherwise, we need to decrease number of available tokens by the cost of the request. 
	- And the last piece is the refill method. It calculates how many tokens accumulated since the last refill and increases currently available tokens in the bucket by this number.
#### Examples
- Maximum capacity is set to 10 and refill rate is set to 2 tokens per second. So, the bucket currently has 10 tokens available.
- In time T1, which is 300 milliseconds later, allow request method call was initiated and the cost of that request is 6 tokens. How many tokens have remained in the bucket after allow request method completed
	- The answer is 4. Bucket was full all this time, no new tokens have been added to the bucket. So, we simply subtract 6
	tokens. (10 - 6 = 4)
- 200 milliseconds later one more allow request call was initiated with the 5 tokens cost. How many tokens have remained after this call?
	- And the answer is 1.
	- First, two more tokens have been added to the bucket by the refill method.
	- And then 5 tokens have been subtracted.
- And 1 second later, actually 900 milliseconds, bucket is full again.
### Object-Oriented Design
#### Class and Interface
1. Job Scheduler interface is responsible for scheduling a job that runs every several seconds and retrieves rules from Rules service.
	1.2 RetrieveJobScheduler class implements JobScheduler interface. Its responsibility is to instantiate, start and stop the 	scheduler. And to run retrieve rules task periodically.
	1.3 RetrieveJobScheduler runs RetrieveRulesTask, which makes a remote call to the Rules service for retrieving all the rules for this service. It then creates token buckets and puts them into the cache.
2. RulesCache interface is responsible for storing rules in memory.
	- TokenBucketCache class implements RulesCache, it is responsible for storing token bucket objects, Map / ConcurrentHashMap / Google Guava Cache
3. ClientIdentifier Interface builds a key that uniquely identifies a client.
	- ClientIdentifierBuilder implements ClientIdentifier, it is responsible for building a key based on user identity information (for example login). There can be other implementations as well, for example based on IP address or retrieve client identity information from request context
4. RateLimiter Interface is responsible for decision making.
	-	TokenBucketRateLimiter class implements RateLimiter, it is responsible for retrieves token bucket from cache, and calls allowRequest() on the bucket
#### Interaction
1. RetrieveJobScheduler runs RetrieveRulesTask, which makes a remote call to the Rules service. It then creates token buckets and puts them into the cache.
2. When client request comes to the host, RateLimiter first makes a call to the ClientIdentifierBuilder to build a unique identifier for the client.
3. And then it passes this key to the cache in TokenBucketCache and retrieves the bucket.
4. And the last step to do is to call allow request on the bucket.
### Distributed World
#### How we can make rate limiting work across many machines in a cluster.
- We have a cluster that consists of 3 hosts. And we want rate limiting solution to **allow 4 requests per second for each client**. How many tokens should we give to a bucket on every host? Should we give 4 divided by 3?
- Answer is 4. Each bucket should have 4 tokens initially. The reason for this is that all requests for the same bucket may in theory land on the same host. Load balancers try to distributed requests evenly, but they do not know anything about keys, and requests for the same key will not be evenly distributed.  
#### Simulation
- Let's add load balancer into the picture and run a very simple simulation. 
	- The first request goes to host A, one token is consumed. Remaining 3 tokens
	- The second request goes to host C and one token is consumed there.  Remaining 3 tokens
	- Two other requests, within the same 1 second interval, go to host B. And take two tokens from the bucket.  Remaining 2 tokens
	- All 4 allowed requests hit the cluster; we should throttle all the remaining requests for this second. But we still have tokens available. What should we do?
	- We must allow hosts to talk to each other and share how many tokens they consumed altogether.
		- In this case host A will see that other two hosts consumed 3 tokens. And host A will subtract this number from its bucket. Leaving it with 0 tokens available. 
		- Host B will find out that A and C consumed two tokens already. Leaving host B with 0 tokens as well.
		- And the same logic applies to host C, C will find out that A and B consumed 3 tokens already, leaving host C with 0 tokens. Now everything looks correct.
		- 4 requests have been processed and no more requests allowed.
- We gave each bucket 4 tokens. If many requests for the same bucket hit our cluster exactly at the same second. Does this mean that 12 requests may be processed, instead of only 4 allowed? Or may be a more realistic scenario. Because communication between hosts takes time, until all hosts agree on what that final number of tokens must be, may there be any requests that slip into the system at that time?
- Yes. Unfortunately, this is the case. We should expect that sometimes our system may be processing more requests than we expect and we need to scale out our cluster accordingly.
- By the way, the token bucket algorithm will still handle this use case well. We just need to slightly modify it to allow negative number of available tokens. 
- When 12 requests hit the system, buckets will start sharing this information. After sharing, every bucket will have -8 tokens and for the duration of the next 2 seconds all requests will be throttled. So, on average we processed 12 requests within 3 seconds.
- Although in reality all 12 were processed within the first second. So, communication between hosts is the key. Let’s see how this communication can be implemented.
#### Ways of sharing between hosts

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1NjU5OTk3NDgsLTUwOTk4MDcxMiwtMT
EwMzkxNjk3MiwtMTYyNjg3NTk0MiwtMTE2NzkyNjQwMSwtNjEz
MTk1OTExLC0xNDIzMTExNzEwLDQ4OTYzMDEyNiwtMTA3ODI2Nz
IzNCwtMTc2OTMzMDM1Nyw4NDk0NzM0ODEsLTEzOTc0NDM2NTcs
MzQxNzM1MzIsNzk1MDg4OTc2LDE1ODYxNDc1NzIsMTMzMTM1MD
M4NSwyMDYzMjM3NTMwLC01ODc3MDQxOTRdfQ==
-->