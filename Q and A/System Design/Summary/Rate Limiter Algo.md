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
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzk4MTk0NjM4XX0=
-->