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
- We can start with a really straightforward idea, when we move the least recently used cache we just implemented to its own host. The benefit of this, we can now make each host to store only chunk of data, called shard. Because data is split across several hosts, we now can store much more data in memory. Service hosts know about all shards, and they forward put and get requests to a particular shard.
- The same idea, but a slightly different realization, is to use service hosts for the cache. We run cache as a separate process on a service host. And data is also split into shards. And similar to the first option, when service needs to make a call to the cache, it picks the shard that stores data and makes a call.
- Let's call these options as distributed cache cluster and co-located cache.
```
- Dedicated cache cluster
- Isolation of resources between service and cache
- Can be used by multiple services
Service Host A		Service Host B
	...					...
Cache Host A		Cache Host B
LRU Cache			LRU Cache
(A - M)				(N - Z)

- Co-located cache
- No extra hardware and operational cost
- Scales together with the service
Service Host A		Service Host B
LRU Cache			LRU Cache
(A - M)				(N - Z)
```
#### Dedicated Cache Cluster
- Dedicated cluster helps to isolate cache resources from the service resources. Both the cache and the service do not share memory and CPU anymore. And can scale on their own.
- Dedicated cluster can be used by multiple services. And we can utilize the same cluster across several microservices our team owns. And dedicated cluster also gives us flexibility in choosing hardware. We can choose hardware hosts with a lot of memory and high network bandwidth. Public clouds nowadays provide a variety of memory optimized hardware.
#### Co-located Cache Cluster
- We do not need a separate cluster. This helps to save on hardware cost and usually less operationally intensive than a separate cluster. And with co-location, both the service and the cache scale out at the same time. We just add more hosts to the service cluster when needed.
#### Cache Client
- We told cache clients to call the cache process using either TCP or UDP connection. How do cache clients decide which cache shard to call?
##### MOD function
- MOD function is based on the item key and some hash function we compute a hash. We divide this hash number by a number of available cache hosts. And take a remainder. We treat this remainder as an index in the array of cache hosts.
- For example, we have 3 cache hosts. And hash is equal to 8. 8 MOD 3 is 2, so the cache host with index 2 will be selected by the service to store this item in the cache and while retrieving the item from the cache.
###### what happens when we add a new cache host (or some host dies due to hardware failures)?
- The MOD function will start to produce completely different results. Service hosts will start choosing completely different cache hosts than they did previously, resulting in a high percentage of cache misses.
##### Consistent Hashing function
- Consistent hashing is based on mapping each object to a point on a circle. We pick an arbitrary point on this circle and assign a 0 number to it. We move clockwise along the circle and assign values.
- We then take a list of cache hosts and calculate a hash for each host based on a host identifier, for example IP address or name. The hash value tells us where on the consistent hashing circle that host lives. And the reason we do all that, is that we want to assign a list of hash ranges each cache host owns. Specifically, each host will own all the cache items that live between this host and the nearest clockwise neighbor.
- for a particular item, when we need to look up what cache host stores it, we calculate a hash and move backwards to identify the host. In this case, host 4 is storing the item.
###### what happens when we add new host to the cache cluster?
- Same as before, we calculate a hash for a new host, and this new host becomes responsible for its own range of keys on the circle. While its counter clockwise neighbor (host 4 in this case) becomes responsible for a smaller range.
- In other words, host 6 took responsibility for a subset of what was formerly owned by host 4. And nothing has changed for all the other hosts. Which is exactly what we wanted, to minimize a number of keys we need to re-hash.
- Consistent hashing is much better than MOD hashing, as significantly smaller fraction of keys is re-hashed when new host is added or host is removed from the cache cluster.
#### What does Cache Client do?
- It’s a small and lightweight library, that is integrated with the service code and is responsible for the cache host selection.
- Cache client knows about all cache servers. And all clients should have the same list. Otherwise, different clients will have their own view of the consistent hashing circle and the same key may be routed to different cache hosts.
- Client stores **list of cache hosts in sorted order (for a fast host lookup) and binary search can be used to find a cache server** that owns the key.
- Cache client talks to cache hosts using TCP or UDP protocol. And if cache host is unavailable, client proceeds as though it was a cache miss. As you may see, list of cache hosts is the most important knowledge for clients.
#### How cache hosts list is created, maintained and shared?
1. First option we store a list of cache hosts in a file and deploy this file to service hosts using some continuous deployment pipeline. This is the simplest option but not very flexible. Every time list changes we need to make a code change and deploy it out to every service host.
2. Second option, we keep the file, but simplify the deployment process. Specifically, we may put the file to the shared storage and make service hosts poll for the file periodically. All service hosts try to retrieve the file from some common location, for example S3 storage service.
	- To implement this option, we may introduce a daemon process that runs on each service host and polls data from the storage once a minute or several minutes.
	- The drawback of this approach is that we still need to maintain the file manually. Make changes and deploy it to the shared storage every time cache host dies or new host is added.
3. Third option, **Configuration Service (e.g ZooKeeper)**, It would be great if we could somehow monitor cache server health and if something bad happens to the cache server, all service hosts are notified and stop sending any requests to the unavailable cache server. And if a new cache server is added, all service hosts are also notified and start sending requests to it. To implement this approach, we will need a new service, **configuration service, whose purpose is to discover cache hosts and monitor their health.**
	- Each cache server registers itself with the configuration service and sends heartbeats to the configuration service periodically. As long as heartbeats come, server is keep registered in the system. If heartbeats stop coming, the configuration service unregisters a cache server that is no longer alive or inaccessible. And every cache client grabs the list of registered cache servers from the configuration service.
	- The third option is the hardest from implementation standpoint and its operational cost is higher. But it helps to fully automate the list maintenance.
### Summarize so far
- To store more data in memory we partition data into shards. And put each shard on its own server.
- Every cache client knows about all cache shards. And cache clients use consistent hashing algorithm to pick a shard for storing and retrieving a particular cache key.
#### Recall non functional requirements
##### Have we built a highly performant cache?
- Yes. Least recently used cache implementation uses constant time operations. Cache client picks cache server in log n time, very fast. And connection between cache client and cache server is done over TCP or UDP, also fast.
##### What about scalability ?
- We can easily create more shards and have more data stored in memory. Although those of you who did data sharding in real systems know that common problem for shards is that some of them may become hot.
- Meaning that some shards process much more requests then their peers. Resulting in a bottleneck. And adding more cache servers may not be very effective.
- With consistent hashing in place, a new cache server will further split some shard into two smaller shards. But we do not want to split any shard, we need to split a very concrete one.
##### What about availability?
- High availability is not there at all. If some shard dies or becomes unavailable due to a network partition, all cache data
for that shard is lost and all requests to that shard will result in a cache miss, until keys are re-hashed.
### Data Replication (deal with hot shard problem)
1. The first category includes a set of probabilistic protocols like gossip, these protocols tend to favor eventual consistency.
2. The second category includes consensus protocols such as chain replication, these protocols tend to favor strong consistency
#### Leader Follower (also known as master-slave) replication
- For each shard we will designate a master cache server and several read replicas. Replicas (or followers) try to be an exact copy of the master. Every time the connection between master and replica breaks, replica attempts to automatically
reconnect to the master. 
- Replicas live in different data centers, so that cache data is still available when one data center is down. 
- All put calls go through the master node, while get calls are handled by both master node and all the replicas. And because calls to a cache shard are now spread across several nodes, it is much easier to deal with hot shards. We may scale out by adding more read replicas.
##### How leaders are elected?
- Configuration service is responsible for monitoring of both leaders and followers and failover, if some leader is not working as expected, configuration service can promote follower to leader. And as we discussed before, configuration service is a source of authority for clients.
- Cache clients use configuration service to discover all cache servers. Configuration service is a distributed service by its nature. It usually consists of an odd number of nodes (to achieve quorum easier), nodes are located on machines that fail independently (so that configuration service remains available in case for example network partitions) and all nodes talk to each other using TCP protocol.
- **Zookeeper** is a good candidate for a configuration service, we can use it here. **Redis also implemented Redis Sentinel for this purpose.**
##### Points of Failures
- We do data replication asynchronously, to have a better performance. We do not want to wait until leader sever replicates data to all the followers. And if leader server got some data and failed before this data was replicated by any of the followers, data is lost. And this is actually an acceptable behavior in many real-life use cases, when we deal with cache.
- The first priority of the cache is to be fast, and if it loses data in some rare scenarios, it should not be a big deal. This is just a cache miss and we should design our service in a way that such failures are expected.
#### Inconsistency
- Distributed cache we built favors performance and availability over consistency.
- There are several things that lead to inconsistency. We replicate data asynchronously to have a better performance. So, a get call processed by the master node, may return a different result than a get call for the same key but processed by a read replica.
- Another potential source of inconsistency is when clients have a different list of cache servers. Cache servers may go down and go up again, and it is possible that a client write values that no other clients can read.
- Introduce synchronous replication. And make sure all clients share a single view of the cache servers list. But this will increase latency and overall complexity of the system.
#### Time to live (TTL)
- LRU used algorithm evicts data from cache when cache is full. But if cache is not full, some items may sit there for a long time. And such items may become stale. 
- To address this issue, we may introduce some metadata for a cache entry and include time-to-live attribute.
##### Clean up expired item from cache
- We can passively expire an item, when some client tries to access it, and the item is found to be expired.
- We can actively expire, when we create a maintenance thread that runs at regular intervals and removes expired items.
- As there may be billions of items in the cache, we cannot simply iterate over all cache items. Usually, some probabilistic algorithms are used, when several random items are tested with every run.
#### Local Cache & Remote Cache
- Services that use distributed (or remote) cache, often use local cache as well. If data is not found in the local cache, call to the distributed cache is initiated.
- To make life of service teams easier, so they do not need to deal with both caches, we can implement a support for the local cache inside the cache client.
- So, when cache client instance is created, we also construct a local cache. This way we hide all the complexity behind a single component - cache client.
- We can utilize previously introduced LRU cache implementation as a local cache, or use well-known 3-rd party implementations, for example Guava cache.
#### Security
- Caches are usually accessed by trusted clients inside trusted environments and we should not expose cache servers directly to the internet, if it is not absolutely required.
- For these reasons we should use a firewall to restrict access to cache server ports and ensure only approved clients can access the cache. 
- Clients may also encrypt data before storing it in cache and decrypt it on the way out. But we should expect performance implications.
#### Metrics & Logging
- This is especially important if we launch our distributed cache as a service. Because so many service teams in the organization may use our cache, every time those services experience performance degradation, they will come to us as one of the potential sources of these degradations.
- What metrics we may want to emit: number of faults while calling the cache, latency, number of hits and misses, CPU and memory utilization on cache hosts, network I/O. 
- With regards to logging we may capture the details of every request to the cache. The basic information like who and when accessed the cache, what was the key and return status code. Log entries should be small, but useful.
#### Proxy
- As you have seen cache client has many responsibilities: maintain a list of cache servers, pick a shard to route a request to, handle a remote call and any potential failures, emit metrics.
- A proxy, that will sit between cache clients and cache servers and will be responsible for picking a cache shard.
- Or to make cache servers responsible for picking a shard. Client sends request to a random cache server and cache server applies consistent hashing (or some other partitioning algorithm) and redirects request to the shard that stores the data. This idea is utilized by Redis cluster.
#### Disadvantage of consistent hashing
- Two major flaws: domino effect and the fact that cache servers do not split the circle evenly.
- Domino effect may appear when cache server dies. And all of its load is transferred to the next server. This transfer might overload the next server, and then that server would fail, causing a chain reaction of failures.
- Remember how we placed cache servers on the circle. Some servers may reside close to each other and some may be far apart. Causing uneven distribution of keys among the cache servers.
- To deal with these problems, several modifications of the consistent hashing algorithm have been introduced. One simple idea is to **add each server on the circle multiple times**.
### Summary
- We started with a single host and implemented least recently used cache. Because local cache has limited capacity and does not scale, we decided to run our LRU cache as a standalone process. Either on its own host or on the service host.
- We made each process responsible for its own part of the data set. We introduced a consistent hashing ring, a logical structure that helps to assign owners for ranges of cache keys.
- We introduced a cache client, that is responsible to routing requests for each key to a specific shard that stores data for this key. Memcached, which is an open source high-performance distributed cache is built on top of these principles.
- We went further, as we wanted to improve availability of our cache and read scalability.
And introduced master-slave data replication.
For monitoring leaders and read replicas and to provide failover support, we brought in
a configuration service.
Which is also used by cache clients for discovering cache servers.
<!--stackedit_data:
eyJoaXN0b3J5IjpbNDYzNTc4MTYsMjAxMTQwMDYsLTIwODg3ND
Y2MTJdfQ==
-->