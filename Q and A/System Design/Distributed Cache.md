### How cache works
- When client request comes, we first check the cache and try to retrieve information from memory. And only if data is unavailable or stale, we then make a call to the datastore.
- And why do we call it a distributed cache? Because amount of data is too large to be stored in memory of a single machine and we need to split the data and store it across several machines.
### Functional requirements
- We need to implement two main operations: put and get.
- Put stores object in the cache under some unique key and get retrieves object from the cache based on the key.
- To simplify things a bit, let’s consider both key and value to be strings.
### Non Functional requirements
- we want to design **scalable, highly available and fast cache**.
- **High scalability** will help to ensure our cache can handle increased number of put and get requests. And be able to handle increasing amount of data we may need to store in the cache.
- **High availability** will help to ensure that data in the cache is not lost during hardware failures and cache is accessible in case of network partitions. This will minimize number of cache misses and as a result number of calls to the datastore.
- **High performance** is probably the number one requirement for the cache. The whole point of the cache is to be fast as it is called on every request.
### Implementation
- LRU Cache
### Make it distributed
- We can start with a really straightforward idea, when we move the least recently used cache we just implemented to its own host.
- The benefit of this, we can now make each host to store only chunk of data, called shard. Because data is split across several hosts, we now can store much more data in memory. Service hosts know about all shards, and they forward put and get requests to a particular shard.
- The same idea, but a slightly different realization, is to use service hosts for the cache. We run cache as a separate process on a service host. And data is also split into shards. And similar to the first option, when service needs to make a call to the cache, it picks the shard that stores data and makes a call.
- Le’ts call these options as distributed cache cluster and co-located cache.
```
Service Host A		Service Host B

Cache Host A		Cache Host B
LRU Cache			
```

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxMDQyMjQ1MDgsLTIwODg3NDY2MTJdfQ
==
-->