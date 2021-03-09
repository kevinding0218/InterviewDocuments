### Data Storage
- SQL Database (MySQL, PostgreSQL) 
- NoSQL database (Cassandra, DynamoDB)
- Cache (Redis)
- Stream processing (Kafka + Spark)
- Cloud native stream processing (Kinesis)
- Batch processing (Hadoop MapReduce)

### What questions we must think or ask ("Count of videos" design)
- Think along 4 categories mentioned here, think about data, what data, how data gets in and out of the system, and do not worry about time too much, you better spend additional 5 minutes clarifying requirements and scope than find yourself solving a different or more complex problem than the interewer actually asked
- Users/Customers : help us understand what data we store in the system
	- Who will use the system?
		- is this all Youtube viewers who will see the total views count for a video?
		- is this a per-hour statistics available to a video owners only
		- is this used by some machine learning models to generate recommendations
	- How the system will be used?
		- data may be used by marketing department only to generate some monthly reports,
			- meaning data is retrieved not often
		- data is sent to recommendation service in real-time
			- meaning data may be retrieved from the system with a very high pace
		- questions in this category 
- Scale (read and write): give us a clue how much data is coming to the system and how much data is retrieved from the system
	- How many read queries per second the system needs to process?
	- How much data is queried per request?
	- How many video views are processed per second?
	- Can there be spikes in traffic and how big they may be?
- Performance: help us quickly evaluate different design options
	- What is expected write-to-read data delay? 
		- if we can count views several hours later than these views actually happened, both batch data processing and stream processing design options can be considered
		- but if time matters and we need to count views on the fly in real-time, batch data processing might be slow and not considered as an option,
	- What is exepcted p99 latency for read queries? how fast data must be retrieved from the system
		- if interviewer tells us that response time must be as small as possible,  it's a hint that we must count views when we write data, and we should do minimal or no counting calculation when we read data, in other words, data must already be aggregated.
- Cost: help us evaluate technology stack
	- Should the design minimize the cost of development?
		- if asked to minimize development cost, we should be leaning towards well-regarded open-source frameworks
	- Should the design minimize the cost of maintenance?
		- if future maintenance cost is a primary concern, we should consider publich cloud services
- The end goal of requirements clarification discussion is to get us closer to defining both FUNCTIONAL and NON-FUNCTIONAL requirements
	- Functional requirements, we mean system behavior, or more specifically APIs, a set of operations the system will support, basically how system will do
	- Non-functional requirements, we mean system qualities, such as fast, fault-tolerant, secure, basically how system is supposed to be

### Functional Requirements - API
- After figureing out what the system should do, you write it down on the whiteboard in a few sentences, help you identifiy name of APIs, input parameters and if needed, make several iteration to generalize APIs. e.g
	- The system has to count video view events
		- count view events is the actual action the system performs and `videoId` becomes the input
		``countViewEvent(videoId)``
		- if we want the system to calculate not just views, but a broader set of events, let's say `likes` and `share`, we may generalize our API a bit and introduce `eventType` parameter, this parameter indicates type of the event we process
		``countEvent(videoId, eventType) where eventType is enum of view/like/share``
		- we can go one step further and make the system calculate not only count function, but other functions as well, like sum and average. 
			- By supporting sum function we can calculate such metric as "total watch time" for a video
			- By supporting avg function, we can calculate average view duration
		``processEvent(videoId, eventType, function) where function can be delegate to count/sum/average``
		- we can further generalize the API and say that system will not just process events one by one, but a list of events as a single batch, where each event is an object that contains information about a video, type of event, time when event happened and so forth
		``processEvents(List<Event>)``
		- Similar thought process can be applied for data retrieval API.
	- The system has to return video views count for a time period
		- GetViewsCount becomes an action, while videoId, start and end time become input parameters.
		``getViewsCount(videoId, startTime, endTime)``
		- if we want to retrieve count not only for video views, but for likes and dislikes, we can introduce `eventType` parameter
		``getCount(videoId, eventType, startTime, endTime) where eventType is enum of view/like/dislike/share``
		- if we want our API return not just count statistics, but also for sum and average, we should specify function as a parameter and rename our API in a more generic way, like `getStats`
		``getStats(videoId, eventType, function, startTime, endTime) where function can be delegate to count/sum/average``

### Non-Functional APIs
 - Usually interviewers will not tell us specific non-function requirements, most likely will challenge use with mentioning big scale and high performance, and we will need to find trade offs.
 - Focus on scalability, performance and availability as top priority requirements, e.g
	- Scalable: we need to design such should handle tens of thousands of requests per second
	- Highly Performant: we want view count statistic to be returned in a matter of few tends of milliseconds.
	- Highly Available: we want statistics to be shown to users all the time, survives hardware/network failures, no single point of failure
 - CAP theorem tells me I should be choosing between Availability and Consistency
	- If we choose availability over consistency, it simply means we prefer to show stale (not up-to-date) data than no data at all.
	- Consistency: Synchronous data replicate is slow, we usually replicate data asynchronously.
	- Cost(hardware, development, maintenance)
		
### High-level architecture
#### Start with something SIMPLE
- we need a database to store data and services to interact with database
#### Services
- we will have a web service that processing incoming video view events and stores data in the database - "Processing Service"
- we will have another web service that to retrieve view counts from the database - "Query Service"
``User -> Browser -> Processing Service -> Database -> Query Service -> Browser -> User``
 - Interviewers may start asking questions about any component we outlined in the high-level architecture, but we may not feel comfortable discussing any component just yet, we need to start with something simple and construct the puzzle/frame with the outside pieces.
 - What is the frame of outside pieces of a system design puzzle? **DATA**!!!
	- more specifically, we need to think what data we want to store and how, we need to define a data model.
#### How we store the data
- we have 2 options of how we want to store
	- we may store each individual video view event
	- or we may calcuate views on the fly and store aggregated data.
##### Invidual events (every click)
- we need capture all attributes of the event: videoId, timestamp, user related information such as country, device type, operating system and so on	
	
| videoId | timestamp | ...
|--|--|--|
| A | 2019-08-26 15: 21:17 | ... |
| A | 2019-08-26 15: 21:32 | ... |
| B | 2019-08-26 15: 22:04 | ... |
| B | 2019-08-26 15: 22:47 | ... |



##### Aggregate data (e.g per minute) in real-time
- we calculate a total count per some time interval, let's say one minute and we lose details of each individul event.
- 
| videoId | timestamp | count |
|--|--|--|
| A | 2019-08-26 15:21 | 2 |
| B | 2019-08-26 15:21 | 3 |
##### Pros and Cons
 - Individual events Pros
	 - **Fast write**: can be stored really fast, we just get the event and push it to the database. 
	 - Later, when we retrieve data, we can **slice and dice data however we want**,  we can filter based on specific attributes, aggregate based on some rules. 
	 -  if there was a bug in some business report, we can **recalculate numbers from scratch.**
 - Individual events Cons
	 - we **cannot read data quickly**, we need to count each individual event when total count is requested, this takes time.
	 - it may **cost a lot of money to store** all the raw events, costly for a large scale (many events), Youtube generates billions of views every day so raw events storage must be huge.
 - Aggregate events Pros
	 - **Fast read**: we do not need to calculate each indivudal event, we just retrieve total count value.
	 - **Decision making in real-time**, for exmaple, we may send the total count value to a recommendation service or trending service for popular videos to be promoted to trends.
 - Aggregate events Cons
	 - We can **only query data the way it was aggregated**, ability to filter data or aggregate it differently is very limited.
	 - Also requires us to implement data aggregation pipeline, we need to somehow **pre-aggregate data in memory before storing it in the database**, this is not an easy task and later you will see why.
	 - Important: It's **hard to even impossible to fix errors**. Let's say we introduced a bug in the aggregation logic. Then how do we fix total counts after the bug was fixed?
##### Which approatch to choose?
 - we need interviewer to help us make a decision, we should ask interviewer about expected data delay, time between when event happened and when it was processed.
	 - If it should be no more than several minutes, we must aggregate data on the fly, this is called batch data.
	 - if several hours is ok, then we can store raw events and process them in the background, this is known as stream data processing.
 - we can also combine both approaches which makes a lot of sense for many systems out there.
	 - we will store raw events, and because there are so many of the, we will store events for several days or weeks only. And then purge old data, and we will also calculate and store numbers in real-time. So that statistics is available for users right away, by storing both raw events and aggreated data we get the best of both words: Fast Read, ability to aggregate data differently and re-calculate statistics if there were bugs or failures on a real-time path.
	 - But there is a price to pay for all the flexibility, the system becomes more complex and expensive.
#### Where we store the data
- Interviewer wants to know specific database name and why we make this choice, you should know both SQL and NoSQL database can scale and perform well, so we should evaulate both types. Here is what we should recall non-functional requirements: Scalability, performance and availability, we should evaluate databases against these requirements.
- Here is a list we should think when choosing database solutions
	- How to scale writes?
	- How to scale reads?
	- How to make both writes and reads fast?
	- How not to lose data in case of hardware faults and network partitions?
	- How to achieve strong consistency? What are the tradeoffs?
	- How to recover data in case of an outage?
	- How to ensure data security?
	- How to make it extensible for data model changes in the future?
	- Where to run (clous vs on-premises data centers)? 
	- How much money will it all cost?
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
### How we can store the data
#### Data Modeling between SQL and NoSQL databases
- build report that shows the following 3 entities:
	- information about video
	- number of total views per hour for last several hours
	- information about the channel this video belongs to
- SQL
	- 3 tables
		- video_info table
			- video_id
			- name
			- ...
			- channel_id
		- video_stas table
			- video_id
			- timestamp
			- count
		- channel_info
			- channel_id
			- name
			- ...
	- To generate report mentioned above, we run a JOIN query that retrieves data from all three tables
	- Import property of a relational database: data is normalized
		- it simply means we minimize data duplication across different tables
		- e.g, we store video names in the video info table only, and we don't store video name in other tables, otherwise because if some video name changes, we have to change it in several places which may leads to inconsistent data.
- NoSQL
	- Cassandra, logical view, we choose Cassandra because it is fault-tolerant, scalable (both read and write throughput increases linearly as new machines are added), it support multi data center replication and works well with time-series data. It's a wide column database that supports asynchronous masterless replication.
	- 
| videoId | channelName | videoName | 15:00 | 16:00 | 17:00 | ... |
|--|--|--|--|--|--|--|
| A | SD Interview | Distributed Cache | 2 | 3 | 8 | ... |
- 
	- Instead of adding rows in the tables of SQL databases, we keep adding columns every hour
	- 4 Types of NoSQL databases
		- Column: Cassandra, HBase has a master-based architecture
		- Document: MongoDB, uses leader-based replication.
		- Key-value
		- Graph
### Data Process
#### Define Process in our example
- Process basically means we get a video view event and increment several counters, total and per hour counters.
- Where to start? Start with requirement we wrote on the whiteboard, we want the processing service to scale together with increase in video views.
#### Think of following questions
- How to scale?
- How to achieve high throughput?
- How not to lose data when processing service machine node crashes?
- What to do when database is unavailable or slow?
- How to make data processing scalable, reliable and fast?
	- Scalable = Partitioning
	- Reliable = Replication and checkpointing to avoid data loss
	- Fast = Keep things In-memory and minimize disk reads
	- Let's see if we can combine all 3
#### Should pre-aggregate data in the processing service?
##### 1st Option
- `Processing Service` increment counter for every incoming event, 
	- for example, if 3 users opened the same video A, the `Processing Service` simply increment total count in the database 3 times
##### 2nd Option
- We accumulate data in the `Processing Service` memory for some period of time, let's say several seconds, and then add the accumulated value to the database counter.
	- for example, if 3 users opened the same video A, the `Processing Service` takes each event and increments in-memory counter, and every several seconds in-memory counter value is sent to the database to calculate the final count
- 2nd Option might be better when work in large scale systems, let's assuming we go with 2nd option
### Some Important Concept
#### Push or Pull?
##### Push
- Push means that some other service sends events synchronously to the processing service.
##### Pull
- Pull means that the processing service pulls events from some temporary storage.
##### Which is better?
- Although answer is that both options are totally valid and we can make both work, `Pull` has more advantages, as it provides a better fault-tolerance support and easier to scale.
- Let's say we have 2 users opening two different videos A and B
	- Processing service updates in-memory counters, returns successful responses back to client and the machine crashes without sending this updated in-memory data to the database, then Data is lost.
##### Alternative Push
- The alternative to `Push` approach is for the processing service to pull events from a storage. Events generated by users are stored in that temporary storage first. `Processing Service` machine pulls events and updates in-memory counters.
- And if machine crashes, we still have events in the storage and can re-process them.
	```
	A
		\
		   Storage/Queue --> Processing Service --> Database
		/
	B
	```
##### Checkpointing
- Intentionally draw the storage as a queue, because when events arrive we put them in that storage in order, one by one.
- Fixed order allows us to assign an offset for each event in the storage
	``
	Checkpointing					C	B	A		=>		Processing Service Machine
						offset		2	1	0					A = 1, B = 1, C = 1
	``
- This offset indicates event position in the sequence. 
	- Events are alwasy consumed sequentially.
	- Every time an event is read from the storage, the current offset moves forwards.
	- After we processed several events and successfully stored them in the database, we write checkpoint to some persistent storage. If `Processing Service` machine fails, it will be replaced with another machine, and this new machine will resume processing where the failed machine left off.
	- This is a very important concept in stream processing
##### Partitioning
- Instead of putting all events into a single queue, we could have several queues.
- Each queue is independent from the others
- Every queue physically lives on its own machine and stores a subset of all events.
	- For example, we compute a hash based on video Identifier and use the hash number to pick a queue
		```
									A A		=> 	A = 2
		Partitioning			  B B B		=> 	B = 3	=> Database
									  C		=> 	C = 1
		```
	- As you may see, partitioning allows us to parrallelize events processing
	- More events we get, more partitions we create.
### Processing Service Detail Design
- `Processing Service` reads events from partition one by one, count events in memory, and flushes this counted values to the database periodically, so we need a component to read events.
- Partition Consumer
	- The consumer establishes and maintains TCP connection with the partition to fetch data, we can think of it as an infinite loop that polls data from the partition.
	- When consumer reads event, it deserializes it, meaning it converts byte array into the actual object.
	- Usually, consumer is a single threaded component, we can also implement multi-threaded access. When several threads read from the partition in parallel, but this approach comes with a cost, checkpointing becomes more complicated and it's hard to perserve order of events if needed.
- Deduplicate Cache
	- Consumer does one more important thing - helps to eliminate duplicate events. If the same message was submitted to the partition several times, we need a mechanism to avoid double counting.
	- To achieve this we use a distributed cache that stores unique event identifiers for, let's say last 10 minutes, and if several identical messages arrived within a 10 minutes interval, only one of them (the first one) will be processed.
		```
												In-memory Store	
														|
		Partition/Shard	=> Partition Consumer	=>	Aggregator	=>	Internal Queue	=>  Database Writer =>	Database																					^
									^														|
								    |													Dead-letter Queue
							Deduplicate Cache				
		```
	- Aggregator
		- Even then comes to the component that does in-memory counting. Let's call it `Aggregator`
		- Think of it as a hash table that accumulates data for some period of time.
		- Periodically, we stop writing to the current hash table and create a new one, the new hash table keeps accumulating incoming data, while old hash table is no longer counting any data and each counter from the old hash table is sent to the internal queue for further processing.
	- Internal Queue
		- Why do we need this internal queue? Why can't we send data directly to the database?
			- Remember we have a single thread that reads events from the partition, but nothing stops us from processing these events by multiple threads, to speed up processing, especially if processing takes time.
			- By sending data to the internal queue we decouple consumption and processing.
	- Database Write
		- either a single-thread or a multi-threaded component.
		- Each thread takes a message from the internal queue and stores pre-aggregated views count into database.
	- Single Thread vs Multi Threads
		- Single Thread makes checkpointing easier, but multi-threaded version increases throughput.
- Deadletter Queue
	- The DLQ is a queu to which messages are sent if they cannot be routed to their correct destination
	- Why need it? To protect ourselves from database performance or availability issues. If database becomes slow or we cannot reach database due to network issues, we simply push messages to the dead letter queue.
	- There is a separate process that reads messages from this queue and sends them to the database
	- The DLQ is widely used when you need to preserve data in case of downstream services degradation, but you can also store the undeliverable messages in local disk
- Data Enrichment
	- Like Cassandra, we store data the way it would be queried. For example, if we want to show video title in the report, we need to store video title together with views count.
	- Same is true for the channel name and many other attribute that we may want to display, but all these attributes do not come to the `Processing Service` with every video view event.
	- Event contains minimum information, like videoId and timestamp. It doesn't need to contain video title or channel name or video creation date. There information is going to be coming from somewhere else, like some Embedded Database.
	- But the trick here is that this database lives on the same machine as the `Processing Service`. All these additional attributes should be retrieved from the database really quickly. Thus, having it on the same machine eliminates a need for remote calls.
		```
												In-memory Store							Embedded Database
														|										|
		Partition/Shard	=> Partition Consumer	=>	Aggregator	=>	Internal Queue	=>  Database Writer =>	Database																					^
									^														|
								    |													Dead-letter Queue
							Deduplicate Cache				
		```
	- LinkedIn for example uses this concept for the "who viewed your profile" feature, when they show additional information about people who viewed your profile, e.g: how many viewers have recruiter job title.
- State Management
	- Since we keep counters in memory for some period of time, either in in-memory store or internal queue, and everytime we keep anything in memory we need to understand what to do when machine fails and this in-memory state is lost.
	- We have events stored in the partition, let's just re-create the state from the point where we failed and re-process one more time, this approach will work well if we store data in-memory for a relatively short period of time and state is small.
	- But sometimes it may be hard to re-create the state from raw events from scratch. The solution in this case is to periodically save the entire in-memory data to a durable storage.
		```
												In-memory Store							Embedded Database
														|										|
		Partition/Shard	=> Partition Consumer	=>	Aggregator	=>	Internal Queue	=>  Database Writer =>	Database																					^
									^	     \______________|_________|________________/									|
								    |						|							Dead-letter Queue
							Deduplicate Cache			State Store	
		```
	- New machine just re-loads this state into its memory when started.
### Data Ingestion Path
- When user opens a video, request goes through API gateway, component that represents a single-entry point into a video content delivery system
- API Gateway routes client requests to backend services. Our counting system may be one of such backend services.
```
																Queue A		ProcessingServiceI
User => API Gateway => Load Balancer => Patitioner Service =>   Queue B	=>	ProcessingServiceII		=> Database
																Queue C		ProcessingServiceIII

```
### Ingestion Path Components
#### Partition Service Client
##### Blocking vs non-blocking I/O
	1. When client makes a request to a server, server processes the request and sends back a response.
	2. The client initiates the connection by using sockets. When a client makes a request, the socket that handles that connection on the server-side is blocked. This happens within a single execution thread. So the thread that handles that connection is blocked as well.
	3. When another client sends a request at the same time, we need to create one more thread to process that request. This is how blocking systems work.They create one thread per connection. 
	4. Modern multi-core machines can handle hundreds of concurrent connections each. But let's say server starts to experience a slow down and number of active connections and threads increases. When this happens, machines can go into a death spiral and the whole cluster of machines may die. This is why we need `Rate Limiting` solution, to help keep systems stable during traffic peeks.
-  non-blockingI/O
	- When we can use a single thread on the server side to handle multiple concurrent connections. Server just queues the request and the actual I/O is then processed at some later point. 
	- Piling up requests in the queue are far less expensive than piling up threads. Non-blocking systems are more efficient and as a result has higher throughput. 
	- You may be wondering that if non-blocking systems are so great, why we still have so many blocking systems out there? Because everything has a price. And the price of non-blocking systems is increased complexity of operations. Blocking systems are easy to debug. 
- Blocking Pros
	- Blocking systems are easy to debug.And this is a big deal.
	- In blocking systems we have a thread per request and we can easily track progress of the request by looking into the thread's stack. Exceptions pop up the stack and it is easy to catch and handle them. We can use thread local variables in blocking systems.
##### Buffering and Batching
- Why use Buffering and Batching
	- There are thousands of video view events happening on Youtube every second. To process all these requests, API Gateway cluster has to be big in size. Thousands of machines.
	- If we then pass each individual event to the partitioner service, partitioner service cluster of machines has to be big as well. This is not efficient.
- How it works
	- Batching: We should somehow combine events together and send several of them in a single request to the partitioner service. This is what batching is about.
	- Buffering: Instead of sending each event individually, we first put events into a buffer. We then wait up to several seconds before sending buffer's content or until batch fills up, whichever comes first.
	- There are many benefits of batching: 
		- it increases throughput, 
		- it helps to save on cost, 
		- request compression is more effective. 
- Cons
	- It introduces some complexity both on the client and the server side.
	- For example think of a scenario when partitioner service processes a batch request and several events from the batch fail, while other succeed. Should we re-send the whole batch? Or only failed events?
##### Timeout
- Timeouts define how much time a client is willing to wait for a response from a server.
- We have two types of timeouts: connection timeout and request timeout.
	- Connection timeout defines how much time a client is willing to wait for a connection to establish. Usually this value is relatively small, tens of milliseconds. Because we only try to establish a connection, no heavy request processing is happening just yet.
	- Request timeout happens when request processing takes too much time, and a client is not willing to wait any longer.
To choose a request timeout value we need to analyze latency percentiles.
		- For example we measure latency of 1% of the slowest requests in the system. And set this value as a request timeout. It means that about 1% of requests in the system will timeout.
		- And what should we do with these failed requests? Let's retry them. May be we just hit a bad server machine with
the first request. And the second attempt may hit a different server machine, increasing our chances to succeed.
		- But we should be smart when retry. Because if all clients retry at the same time or do it aggressively, we may create a so-called retry storm event and overload sever with too many requests.
		- To prevent this, we should use exponential backoff and jitter algorithms.
			- Exponential backoff algorithm increases the waiting time between retries up to a maximum backoff time. We retry requests several times, but wait a bit longer with every retry attempt.
			- Jitter adds randomness to retry intervals to spread out the load. If we do not add jitter, backoff algorithm will retry requests at the same time. And jitter helps to separate retries.
			- Even with exponential backoff and jitter we may still be in danger of too many retries.
				- For example when partitioner service is down or degraded. And majority of requests are retried. The Circuit Breaker pattern stops a client from repeatedly trying to execute an operation that's likely to fail.
				- We simply calculate how many requests have failed recently and if error threshold is exceeded we stop calling a downstream service. Some time later, limited number of requestsfrom the client are allowed to pass through
and invoke the operation.
If these requests are successful, it's assumed
that the fault that was previously causing
the failure has been fixed.
We allow all requests at this point and start
counting failed requests from scratch.
The loop completes.
The Circuit Breaker pattern also has drawbacks.
For example, it makes the system more difficult
to test.
And it may be hard to properly set error threshold
and timers.
#### Load Balancer
#### Partitioner Service and Partitions
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjczOTQyOTkyLDE0NzM1MTU3NSwxNDU1MT
Y0ODA0LDE2NjA3NDQxMCwtMTkxMDYzMjk0NywtNjIxNzI2ODQw
LC0xMzUyMDA2NjI1LC0xNzg0NzcxMTU4LDIxMjEwMDczNzQsLT
YxODQyNTE1MSwtMTk1MjI3NDA5MiwtMTczMDE2MjY4NCwtNjU5
MTI4OTc0LC03MzA4MDUyNDUsMTQxOTE4NjYzMSw3MTAwNTk2OD
ksNDQ2NzYyMjQxLDEzNjk0NTc2NCwtMTU5MDkxNTQ3MCwtMTM0
NjMzNzg5NF19
-->