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
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjEyNjAwODk4M119
-->