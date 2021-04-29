### SQL
- SQL stores data in tables where each row represents an entity and each column represents a data point about that entity, each record conforms to a fixed schema, meaning the columns must be decided and chosen before data entry and each row must have data for each column
### NoSQL
- In NoSQL, schemas are dynamic. Columns can be added on the fly and each ‘row’ (or equivalent) doesn’t have to contain data for each ‘column
- **Key-Value** Stores: Data is stored in an array of key-value pairs. The ‘key’ is an attribute name which is linked to a ‘value’. e.g: **Redis**
- **Document Databases**: data is stored in documents (instead of rows and columns in a table) and these documents are grouped together in collections, Each document can have an entirely different structure, e.g: **Couchbase and MongoDB**
- **Wide-Column Databases**: Unlike relational databases, we don’t need to know all the columns up front and each row doesn’t have to have the same number of columns, e.g: **Cassandra**
- **Graph Databases**: These databases are used to store data whose relations are best represented in a graph
### Difference
#### Scalability
- In most common situations, **SQL databases are vertically scalable**, i.e., by increasing the horsepower (higher Memory, CPU, etc.) of the hardware, which can get very expensive
- **NoSQL databases are horizontally scalable**, meaning we can add more servers easily in our NoSQL database infrastructure to handle a lot of traffic. Any cheap commodity hardware or cloud instances can host NoSQL databases, thus making it a lot more cost-effective than vertical scaling.
#### Reliability or ACID Compliancy (Atomicity, Consistency, Isolation, Durability)
- when it comes to data reliability and safe guarantee of performing transactions, SQL databases are still the better bet.
- Most of the NoSQL solutions sacrifice ACID compliance for performance and scalability.
### Which one to choose
#### Reasons to use SQL database
- We need to ensure ACID compliance. ACID compliance reduces anomalies and protects the integrity of your database by prescribing exactly how transactions interact with the database
	- **Atomic**  – Transaction acting on several pieces of information complete only if all pieces successfully save. Here, “all or nothing” applies to the transaction.
	- **Consistent**  – The saved data cannot violate the integrity of the database. Interrupted modifications are rolled back to ensure the database is in a state before the change takes place.
	- **Isolation**  – No other transactions take place and affect the transaction in question. This prevents “mid-air collisions.”
	- **Durable**  – System failures or restarts do not affect committed transactions.
- Your data is structured and unchanging
#### Reasons to use NoSQL database
- Storing large volumes of data that often have little to no structure
- Making the most of cloud computing and storage. Cloud-based storage is an excellent cost-saving solution but requires data to be easily spread across multiple servers to scale up.
- Rapid development
#### SQL database
##### Sharding or Horizontal Partitioning
- Things are simple when we can store all our data on a single database machine. 
``MySQL-I (A-Z)``
- But when a single machine is not enough, we need to introduce more machines and split data between them, this procedure is called sharding or horizontal partitioning, each shard stores a subset of all the data.
``MySQL-I (A-M) + MySQL-II(N-Z)``
- And because we now have several machines, services that talk to the database need to know how many machines exist and which one to pick to store and retrieve data.
- We discussed before that we have `Processing Service` that stores data in the database, and `Query Service` that retrieve data from the database, we could have made both these services to call every database machine directly.
	```
	Processing Service 	  -			MySQL-I (A-M)
						  X
	Query Service		  -			MySQL-II(N-Z)
	```
##### Add Cluster Proxy Server
- A better approatch is to introduce a light `Cluster Proxy Server` that knows about all database machines and route traffic to the correct shard, now both services talk to the `Cluster Proxy` only, services do not need to know about each and every database machine anymore, but `Cluster Proxy` has to know.
	```
	Processing Service 	  				MySQL-I (A-M)
						\ ClusterProxy
						/
	Query Service		  				MySQL-II(N-Z)
	```
- Moreover, `Cluster Proxy` needs to know when some shard dies or become unavailable due to network partition. And if new shard has been added to the database cluster, proxy should become aware of it.
##### Add Configuration Service
- We introduce a new component - `Configuration Service(e.g ZooKeeper)`, which maintains a health check connection to all shards, so it alwasy knows what database machines are available. So `Cluster Proxy` calls a particular shard
	```
				 	  	Config Service		MySQL-I (A-M)
	Processing Service		  |
						\ ClusterProxy
						/
	Query Service							MySQL-II(N-Z)
	```
##### Add Shard Proxy
- And Instead of calling database instance directly, we can introduce one more proxy - `Shard Proxy`, that sits in front of database. `Shard Proxy` will help us in many different ways: it can cache query results, monitor database instance health and publish metrics, terminate queries that take too long to return data and many more.
	```
				 	  	Config Service		Shard Proxy + MySQL-I (A-M)
	Processing Service		  |
						\ ClusterProxy
						/
	Query Service							Shard Proxy + MySQL-II(N-Z)
	```
- This setup helps us address several requirements we mentioned before, like scalability and performance. But availability is not yet addressed.
##### Add Replica Lead/Follower (Master/Read) 
- What if database shard died? How to make sure data is not getting lost?
- We need replicate data. Lets call each existed shard a leader shard or a master shard.
- For every master shard we introduce a copy of it, called follower replica or a read replica. We call it follower replica because writes still go through a leader shard, but reads may go through both leader shard and a follower replica.
##### Add Data Center
- We also put some replicates to a data center different from their master shard, so that if the whole data center goes down, we still have a copy of data available.
	```	
														Lead/Master Shard				Follower/Read Shard
						 	  	  Config Service	Shard Proxy + MySQL-I (A-M) -->	Shard Proxy + MySQL-I (A-M)
	Processing Service		     		 |	     ^				||							   ||
								\store 	 |		/				||			OR				   ||	
								  ClusterProxy		<============================================= 
								//						Master/Lead Shard				Read/Follower Shard
	Query Service	       //retrieve			Shard Proxy + MySQL-II(N-Z)     Shard Proxy + MySQL-II(N-Z)
														Data Center A					Data Center B
	```
- When store data request comes, based on the information provided by `Configuration Service`, `Cluster Proxy` sends data to a shard. And data is either synchronously or asynchronously replicated to a corresponding read replica.
- When retrieve data request comes, `Cluster Proxy` may retrieve data either from a master or read replica
##### Cons
- This solution doesn't seems simple, we have all these proxies, configuration service, leader and follower replica instances, maybe we can use NoSQL to simplify things a little bit.
#### NoSQL
##### Nodes and Shards
- In NoSQL world, we split data into chunks, shards, also known as nodes
- Instead of having leaders and followers, we say that each shard is Equal, we no longer need configuration service to monitor health of each shard. Instead, let's allow shards talk to each other and exchange information about their state.
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
##### Cassandra (Tunable Consistency)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwMTcyODA1MzBdfQ==
-->