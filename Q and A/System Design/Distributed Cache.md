### How cache works
- When client request comes, we first check the cache and try to retrieve information from memory. And only if data is unavailable or stale, we then make a call to the datastore.
- And why do we call it a distributed cache? Because amount of data is too large to be stored in memory of a single machine and we need to split the data and store it across several machines.
### Functional requirements
- We need to implement two main operations: put and get.
- Put stores object in the cache under some unique key and get retrieves object from the cache based on the key.
- To simplify things a bit, let’s consider both key and value to be strings.
### Non Functional requirements
- we want to design **scalable, highly available and fast cache**.
- High scalability will help to ensure our cache can handle increased number of put and get requests. And be able to handle increasing amount of data we may need to store in the cache.
- High availability will help to ensure that data in the cache is not lost during hardware failures and cache is accessible in case of network partitions. This will minimize number of cache misses and as a result number of calls to the datastore.
- High performance is probably the number one requirement for the cache. The whole point of the cache is to be fast as it is called on every request.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTc1MzQxMTQ4LC0yMDg4NzQ2NjEyXX0=
-->