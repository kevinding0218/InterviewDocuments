#### NoSQL
##### Nodes and Shards
- **In NoSQL world, we split data into chunks, shards, also known as nodes**
- Instead of having leaders and followers, we say that **each shard is Equal**, we no longer need configuration service to monitor health of each shard. Instead, let's **allow shards talk to each other and exchange information about their state**.
- To reduce network load, we don't need each shard to talk to every other shard. Every second shard may exchange information with a few other shards, no more than 3. Qucik enough state information about every node propagates throughout the cluster. This procedure is called a gossip protocol.
- Ok, each node in the cluster knows about other node and this is a big deal. Remember preivously we used `Cluster Proxy` component to route requests to a particular shard, as `Cluster Proxy` was the only one who knew about all shards, but now every node knows about each other. So clients of our database no longer need to call a special component for routng requests.
##### How NoSQL Shards work
- Clients may call any node in the cluster and node itself will decide where to forward this request further.
	```
	Node1(A-F) - Node2(G-L) 
		|			|
	Node3(M-R) - Node4(S-Z)
	```
- `Processing Service` makes a call to store views count for some video B, let's say `Node 4` is selected to serve this request, we can use a simple round robin algorithm to chose this initial node, or we may be smarter and chose a node that is "closet" to the client in terms of network distance. 
- Let's call this `Node 4` a `Coordinator Node` needs to decide which node stores data for the requested video. We can use `Consistent Hashing` algorithm to pick the node. As you may see, `Node 1` should store the data for the video B. `Coordinate Node` will make a call to the `Node 1` and wait for the response.
##### Quorum Writes
- Actually nothing stops `Coordinator Node` to call multiple nodes to replicate data, for example 3 nodes if we want 3 copies of data. Waiting for 3 responses from replicate may be too slow, so we may consider the write to be successful as soon as only 2 replication requests succeeded. This approach is called `Quorum Writes`
##### Quorum Reads
- Similar to `Quorum Writes`, there is a `Quorum Reads` approach, when `Query Service` retrieves views count for video B, `Coordinate Node 4` will initiate several read requests in paralle. In theory, the coordinate node may get different responses from replica nodes. Why? Because some node could have been unavailable when write request happened. That node has stale data right now, other 2 nodes has up-to-date data. `Read Quorum` defines a minimum number of nodes that have to agree on the response.
	```
	Processing Service			Node1(A-F) - Node2(G-L) 
						\\			|			|
	Query Service	====>		Node3(M-R) - Node4(S-Z)
	```
##### NoSQL Availability
- Cassandra uses version numbers to determine staleness of data, and similar to SQL database, we want to store copies of data across several different data centers for high availabilities.
	```
		Processing Service			Node1(A-F) - Node2(G-L) 		Node1(A-F) - Node2(G-L) 
							\\			|			|					|			|
		Query Service	====>		Node3(M-R) - Node4(S-Z)			Node3(M-R) - Node4(S-Z)
										Data Center A					Data Center B
	```
#### Consistency
##### Cons of not choose consistency
- If we choose availability over consistency, it simply means we prefer to show stale (not up-to-date) data than no data at all.
- Synchronous data replicate is slow, we usually replicate data asynchronously.
##### Eventual Consistency
- In case of a leader-follower replication for example, some read replicas may be behind their master. Which leads to a situation when different users see different total count for a video.
- This inconsistency is temporary, over time all writes will propagate to replicas. This effect is known as `Eventual Consistency`
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTQyMjcyMzc0MywxNjg3MTI3MjYzXX0=
-->