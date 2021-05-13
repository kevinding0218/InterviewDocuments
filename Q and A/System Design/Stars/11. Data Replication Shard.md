### Data Replication (deal with hot shard problem)

1.  The first category includes a set of probabilistic protocols like gossip, these protocols tend to favor eventual consistency.
2.  The second category includes consensus protocols such as chain replication, these protocols tend to favor strong consistency

#### Leader Follower (also known as master-slave) replication

-   For each shard we will designate a master cache server and several read replicas. Replicas (or followers) try to be an exact copy of the master. Every time the connection between master and replica breaks, replica attempts to automatically  
    reconnect to the master.
-   Replicas live in different data centers, so that cache data is still available when one data center is down.
-   All put calls go through the master node, while get calls are handled by both master node and all the replicas. And because calls to a cache shard are now spread across several nodes, it is much easier to deal with hot shards. We may scale out by adding more read replicas.

##### How leaders are elected?

-   Configuration service is responsible for monitoring of both leaders and followers and failover, if some leader is not working as expected, configuration service can promote follower to leader. And as we discussed before, configuration service is a source of authority for clients.
-   Cache clients use configuration service to discover all cache servers. Configuration service is a distributed service by its nature. It usually consists of an odd number of nodes (to achieve quorum easier), nodes are located on machines that fail independently (so that configuration service remains available in case for example network partitions) and all nodes talk to each other using TCP protocol.
-   **Zookeeper**  is a good candidate for a configuration service, we can use it here.  **Redis also implemented Redis Sentinel for this purpose.**

##### Points of Failures

-   We do data replication asynchronously, to have a better performance. We do not want to wait until leader sever replicates data to all the followers. And if leader server got some data and failed before this data was replicated by any of the followers, data is lost. And this is actually an acceptable behavior in many real-life use cases, when we deal with cache.
-   The first priority of the cache is to be fast, and if it loses data in some rare scenarios, it should not be a big deal. This is just a cache miss and we should design our service in a way that such failures are expected.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTU4MzAxMjYwNV19
-->