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
- Timeouts define how much time a client is willing to wait for a response from a server. We have two types of timeouts: connection timeout and request timeout.
- Connection timeout 
	- defines how much time a client is willing to wait for a connection to establish. Usually this value is relatively small, tens of milliseconds. Because we only try to establish a connection, no heavy request processing is happening just yet.
- Request timeout 
	- happens when request processing takes too much time, and a client is not willing to wait any longer.
To choose a request timeout value we need to analyze latency percentiles.
	- For example we measure latency of 1% of the slowest requests in the system. And set this value as a request timeout. It means that about 1% of requests in the system will timeout.
##### Retires
- What should we do with these failed requests? Let's retry them. May be we just hit a bad server machine with
the first request. And the second attempt may hit a different server machine, increasing our chances to succeed.
- But we should be smart when retry. Because if all clients retry at the same time or do it aggressively, we may create a so-called **retry storm event** and **overload sever** with too many requests.
##### Exponential backoff and jitter 
- Exponential backoff algorithm increases the waiting time between retries up to a maximum backoff time. We retry requests several times, but wait a bit longer with every retry attempt(1s, 2s, 4s, 8s, etc).
- Jitter adds randomness to retry intervals to spread out the load. If we do not add jitter, backoff algorithm will retry requests at the same time. And jitter helps to separate retries.
- Even with exponential backoff and jitter we may still be in danger of too many retries.
	- For example when partitioner service is down or degraded. And majority of requests are retried. 
##### Circuit Breaker Pattern
- The Circuit Breaker pattern stops a client from repeatedly trying to execute an operation that's likely to fail.
- We simply calculate how many requests have failed recently and if error threshold is exceeded we stop calling a downstream service. Some time later, limited number of requests from the client are allowed to pass through and invoke the operation.
- If these requests are successful, it's assumed that the fault that was previously causing the failure has been fixed. We allow all requests at this point and start counting failed requests from scratch. The loop completes. 
- Drawbacks. 
	- For example, it makes the system more difficult to test. And it may be hard to properly set error threshold and timers.
#### Load Balancer
- distribute datatraffic between multiple servers. There are two types of load balancers: hardware and software.
##### Hardware Load Balancer
- Hardware Load Balancer are network devices we buy from known organizations. Theses are powerful machines with many CPU
cores, memory and they are optimized to handle very high throughput, e.g millions of requests per second.
##### Software Load Balancer
- Software Load Balancers are only softwares that we install on hardware we choose. Load balancers provided by public clouds (for example ELB from AWS) are examples of software load balancer type as well.
##### TCP Load Balancer
- TCP Load Balancers simply forward network packets without inspecting the content of the packets. Think of it as if we established a single end-to-end TCP connection between a client and a server. This allows TCP load balancers to be super fast and handle millions of requests per second.
##### HTTP Load Balancer
- HTTP load balancers, on contrast, terminate the connection. Load balancer gets an HTTP request from a client, establishes a connection to a server and sends request to this server. HTTP load balancer can look inside a message and make a load‑balancing decision based on the content of the message.
- For example based on a cookie information or a header.
##### Load balancers algorithms
- Round robin algorithm distributes requests in order across the list of servers.
- Least connections algorithm sends requests to the server with the lowest number of active connections.
- Least response time algorithm sends requests to the server with the fastest response time.
- Hash-based algorithms distribute requests based on a key we define, such as the client IP address or the request URL.
##### DNS
- DomainName System. DNS is like a phone book for the internet. It maintains a directory of domain names and
translate them to IP addresses. We register our partitioner service in DNS, specify domain name, for example partitionerservice.domain.com and associate it with IP address of the load balancer device.
##### How does Partitioner Service interact with LB
- when clients hit domain name, requests are forwarded to the load balancer device. For the load balancer to know about partitioner service machines, we need to explicitly tell the load balancer the IP address of each machine.
- Both software and hardware load balancers provides API to register and unregister servers.
##### Health checking 
- Load balancers need to know which server from the registered list are healthy and which are unavailable at the moment. This way load balancers ensure that traffic is routed to healthy servers only. Load balancer pings each server periodically and if unhealthy server is identified, load balancer stops to send traffic to it. It will then resume routing traffic to that server when it detects that the server is healthy again.
##### How LB gurantee availability
- As for high availability of load balancers, they utilize a concept of primary and secondary nodes. 
- The primary load balancer accepts connections and serves requests, while the secondary load balancer monitors the primary. If, for any reason, the primary load balancer is unable to accept connections, the secondary one takes over. Primary and secondary also live in different data centers, in case one data center goes down.
#### Partitioner Service and Partitions
- Partitioner Service is a web service that gets requests from clients, looks inside each request to retrieve individual video view events (because remember we batch events on the client side), and routs each such event/message to some partition.
- what partitions are? Partitions is also a web service, that gets messages and stores them on disk in the form of the append-only log file. So, we have a totally-ordered sequence of messages ordered by time. This is not a single very large log file, but a set of log files of the predefined size.
##### Partition Strategy - Hash based on Id
- Partitioner service has to use some rule, partition strategy, that defines which partition gets what messages. A simple strategy is to **calculate a hash function based on some key**, let's say `videoId` and chose a machine based on this hash.
- This simple strategy does not work very well with large scale. As it may lead to so called "hot partitions". For example when we have a very popular video or set of videos and all view events for them go to the same partition.
##### Partition Strategy - Include event time
- One approach to deal with hot partitions is to include event time, for example in minutes, into partition key. All video events within the current minute interval are forwarded to some partition. 
- Next minute, all events go to a different partition. Within one minute interval a single partition gets a lot of data, but over several minutes data is spread more evenly among partitions.
##### Partition Strategy - Partition into more partition like consistent hashing
- Another solution to hot partitions problem is to split hot partition into two new partitions. To get an idea how this approach might work, remember consistent hashing algorithm and how adding a new node to the consistent hashing
ring splits a range of keys into two new ranges. 
	- And if to push this idea of partition split even further, we may explicitly allocate dedicated partitions for some popular video channels. All video view events from such channels go to their allocated partitions. And view events from all other channels never go to those partitions.
##### Service Discover
- To send messages to partitions, partitionerservice needs to know about every partition.This is where the concept of service discovery comes on stage.
- In the world of microservices there are two main service discovery patterns: server-side discovery and client-side discovery. We already looked at server-side discovery when talked about load balancers. Clients know about load balancer, load balancer knows about server-side instances.
- But we do not need a load balancer between partitioner service and partitions. Partitioner service itself acts like a load balancer by distributing events over partitions. This is a perfect match for the client-side discovery pattern.
- With client-side discovery every server instance registers itself in some common place, named service registry. Service registry is another highly available web service, which can perform health checks to determine health of each registered instance. Clients then query service registry and obtain a list of available servers.
- Example of such registry service is Zookeeper. In our case each partition registers itself in Zookeeper, while every partitioner service instance queries Zookeeper for the list of partitions.
- One more option for service discovery is similar to what Cassandra does. Remember we mentioned before that Cassandra nodes talk to each other? So, every node in the cluster knows about other nodes. It means clients only need to contact one node from the server cluster to figure out information about the whole cluster. Think about this.
##### Replication
- We must not lose events when store them in partitions. So, when event is persisted in a partition, we need to replicate it. If this partition machine goes down, events are not lost. There are three main approaches to replication: single leader replication, multi leader replication and leaderless replication.
	1. We use single leader replication when discussed how to scale a SQL database.
	2. We use leaderless replication when discussed how Cassandra works
	3. multi leader replication is mostly used to replicate between several data centers.
- Let's go with single leader replication. Each partition will have a leader and several followers.
	- We always write events and read them from the leader only.
	- While a leader stays alive, all followers copy events from their leader. And if the leader dies, we choose a new leader from its followers.
	- The leader keeps track of its followers: checks whether the followers are alive and whether any of the followers is too far behind. If a follower dies, gets stuck, or falls behind, the leader will remove it from the list of its followers.
- Remember a concept of a quorum write in Cassandra? We consider a write to be successful, when predefined number of replicas acknowledge the write. Similar concept applies to partitions. When partitioner service makes a call to a partition, we may send response back as soon as leader partition persisted the message, or only when message was replicated to a specified number of replicas.
- When we write to a leader only, we may still lose data if leader goes down before replication really happened. When we wait for the replication to complete, we increase durability of the system, but latency will increase. Plus, if required number of replicas is not available at the moment, availability will suffer. Tradeoffs, as usual. 
##### Message Format
- We can use either textual or binary formats for messages. Popular textual formats are XML, CSV, JSON. Popular binary formats are Thrift, Protocol Buffers and Avro.
	- Popular textual formats are XML, CSV, JSON. 
	- Popular binary formats are Thrift, Protocol Buffers and Avro.
- What's great about textual formats - they are human-readable. They are well-known, widely supported andn heavily used by many distributed systems. But for the large scale real-time processing systems binary formats provide much more benefits. Messages in binary format are more compact and faster to parse.
- Why binary format works better?
	- As mentioned before, messages contain several attributes, such as video identifier, timestamp, user related information. When represented in JSON format, for example, every message contains field names, which greatly increases total message size. 
	- Binary formats are smarter. Formats we mentioned before require a schema. And when schema is defined we no longer need to keep field names. For example Apache Thrift and Protocol Buffers use field tags instead of field names. Tags are just numbers and they act like aliases for fields.
	- Tags occupy less space when encoded. Schemas are crucial for binary formats. Message producers (or clients) need to know the schema to serialize the data. Message consumers (processing service in our case) require the schema to deserialize the message.
	- So, schemas are usually stored in some shared database where both producers and consumers can retrieve them.
Important to mention that schemas may and will change over time. We may want to add more attributes into messages and use them later for counting or filtering. Apache Avro is a good choice for our counting system.
### Data Retrieval Path
- When users open a video on Youtube, we need to show total views count for this video. To build a video web page, several web services are called. A web service that retrieves information about the video, a web service that retrieves comments, another one for recommendations. Among them there is our Query web service that is responsible for video statistics.
- All these web services are typically hidden behind an API Gateway service, a single-entry point. API Gateway routes client requests to backend services. So, get total views count request comes to the Query service.
- We can retrieve the total count number directly from the database. Remember we discussed before how both SQL
and NoSQL databases scale for reads. But total views count scenario is probably the simplest one. This is just a single value in the database per video.
- The more interesting use case is when users retrieve time-series data, which is a sequence of data points ordered in time. For example, when channel owner wants to see statistics for her videos.
- As discussed before, we aggregate data in the database per some time interval, let's say per hour. Every hour for every video. That is a lot of data, right? And it grows over time. Fortunately, this is not a new problem and solution is known. Monitoring systems, for example, aggregate data for every 1 minute interval or even 1 second. You can imaging how huge those data sets can be. So, we cannot afford storing time series data at this low granularity for a long period of time. The solution to this problem is to rollup the data.
For example, we store per minute count for several days. After let's say one week, per minute data is aggregated into per hour data. And we store per hour count for several months. Then we rollup counts even further and data
that is older than let's say 3 months, is stored with 1 day granularity. And the trick here is that we do not need to store old data in the database. We keep data for the last several days in the database, but the older data can be stored somewhere else, for example, object storage like AWS S3.
#### hot storage and a cold storage.
- Hot storage represents frequently used data that must be accessed fast.
- Cold storage doesn’t require fast access. It mostly represents archived and infrequently accessed data.
- When request comes to the Query service, it does so-called data federation, when it may need to call several storages to fulfill the request. Most recent statistics is retrieved from the database, while older statistics is retrieved from the Object Storage.
- Query service then stitches the data. And this is ideal use case for the cache. We should store query results in a distributed cache.
### Data flow simulation
1. Three users opened some video A. And API Gateway got 3 requests.
2. Partitioner service client batches all three events and sends them in a single request to the partitioner service.
3. This request hits the load balancer first. And load balancer routes it to one of the partitioner service machines.
4. Partitioner service gets all three events from the request and sends them to some partition. All three events end up in the same partition, as we partition data based on the video identifier.
5. Here is where processing service appears on the stage. 
	1. Partition consumer reads all three messages from the partition one by one and sends them to the aggregator.
	2. Aggregator counts messages for a one minute period and flushes calculated values to the internal queue at the end of that minute. 
	3. Database writer picks count from the internal queue and sends it to the database.
6. In the database we store count per hour and the total number of views for each video.
7. So, we just add a one minute value to the current hour count as well as the total count. Total count was 7 prior to this minute and we add 3 for the current minute. 
8. And during data retrieval, when user opens video A, API Gateway sends request to the Query service. 
9. Query service checks the cache. And if data is not found in the cache, or cache value has expired, we call the database. Total count value is then stored in the cache and Query service returns the total count back to the user.
### Technology Stack
- We rely on some well-regarded technologies. Either open source or commercial. Public cloud services.
- During the interview do not forget to discuss these technologies. You may do this along the way or at the end of the interview. 
#### Client side
- Netty is a high-performance non-blocking IO framework for developing network applications, both clients and servers.
- Frameworks such as Hystrix from Netflix
- Polly simplify implementation of many client-side concepts we discussed before: timeouts, retries, circuit breaker pattern.
#### Load balancing
- Citrix Netscaler is probably the most famous hardware load balancer.
- Among software load balancers NGINX is a very popular choice.
- And if we run our counting system in the cloud, for example Amazon cloud, then Elastic Load Balancer is a good pick.
#### Messaging systems
- Instead of using our custom Partitioner service and partitions, we could use Apache Kafkainstead.
- Or Kafka's public cloud counterpart, like Amazon Kinesis.
#### Data processing
- To process events and aggregate them in memory we can use stream-processing frameworks such as Apache Spark or Flink.
- Or cloud-based solutions, such as Kinesis Data Analytics.
#### Storage
- We already talked about Apache Cassandra.
- Another popular choice for storing time-series data is Apache HBase database. These are wide column databases.
- There are also databases optimized for handling time series data, like InfluxDB.
- We can store raw events in Apache Hadoop or in a cloud data warehouse, such as AWS Redshift.
- And when we roll up the data and need to archive it, AWS S3 is a natural choice.
#### Other
- Vitess is a database solution for scaling and managing large clusters of MySQL instances. Vitess has been serving all Youtube database traffic since 2011.
- In several places of our design we rely on a distributed cache: for message deduplication and to scale read data queries. Redis is a good option.
- For a dead-letter queue mechanism, when we need to temporarily queue undelivered messages, we may use an open-source message-broker such as RabbitMQ. Or public cloud alternative, such as Amazon SQS.
- For data enrichment, when we store video and channel related information locally on the machine and inject this information in real-time, we may use RocksDB, a high performance embedded database for key-value data.
- To do leader election for partitions and to manage service discovery, we may rely on Apache Zookeeper, which is a distributed configuration service.
- For the service discovery piece we actually have an alternative, Eureka web service from Netflix.
- To monitor each of our system design components we may rely on monitoring solutions provided by public cloud services, such as AWS CloudWatch.
- Or use a popular stack of open source frameworks: Elasticsearch, Logstash, Kibana. Or ELK for short.
- We discussed before that binary message format is preferred for our system. Popular choices are Thrift, Protobuf and Avro.
- For Partitioner service to partition the data, we should use a good hashing function, for example a MurmurHash.
### Additional interview notes
- Have you noticed that I usually use verbs like may or can and rarely use must or have
to? This is because we usually have several options to choose from. When we design a system or a part of it in
real life, we usually bring several options to discuss with the team, right? And very important is not only know your options, but be able to explain pros and cons of each one.
- Let's see what else the interviewer may want to discuss with us.
#### Performance Testing (how to identify bottleneck)
- To identify bottlenecks in the system we need to test it under a heavy load. This is what performance testing is about.
- There are several types of performance testing. 
	- We have **load testing**, when we measure behavior of a system under a specific expected load.
		- With load testing we want to **understand that our system is indeed scalable and can handle a load we expect.** For example, a two or three times increase in traffic.
	- We have **stress testing**, when we test beyond normal operational capacity, often to a breaking point. 
		- With stress testing we want to **identify a breaking point in the system. Which component will start to suffer first**. And what resource it will be: memory, CPU, network, disk IO.
	- We have **soak testing**, when we test a system with a typical production load for an extended period of time.
		- with soak testing we want to **find leaks in resources**. For example, **memory leaks.** So, generating high load is the key. Tools like Apache JMeter can be used to generate a desired load.
#### Health monitoring (how to make sure system running healthy)
- All the components of our system must be instrumented with monitoring of their health. **Metrics, dashboards and alerts** should be our friends all the time.
	- Metric is a variable that we measure, like error count or processing time.
	- Dashboard provides a summary view of a service’s core metrics.
	- And alert is a notification sent to service owners in a reaction to some issue happening in the service.
- Remember about the four golden signals of monitoring, which are **latency, traffic, errors, and saturation.**
#### how to make sure it counts things correctly?
- This becomes critical when we not just count video views, but, for example, number of times some ad was played in a video. As we need to properly charge an ad owner and pay money to a video owner. This problem is typically addressed by building an audit system.
- There can be two flavors of audit systems. Let's call them weak and strong.
##### Weak audit system
- Weak audit system is a continuosly running end-to-end test. When let's say once a minute we generate several video view events in the system, call query service and validate that returned value equals to the expected count.  This simple test gives us a high confidence that the system counts correctly. And it is easy to implement and maintain such test.
- But unfortunately, this test is not 100% reliable. What if our system loses events in some rare scenarios? And weak audit test may not identify this issue for a long period of time. That is why we may need a better approach.
##### Strong audit system
- Strong audit system calculates video views using a completely different path then out main system.
- For example we store raw events in Hadoop and use MapReduce to count events. And then compare results of both systems. Having two different systems doing almost the same may seem like an overkill, right? You may be surprised but this is not so uncommon in practice.
- Not such a long time ago it was quite a popular idea. And it even has a name - Lambda Architecture. The key idea is to send events to a batch system and a stream processing system in parallel.
And stitch together the results from both
systems at query time.
You can get a better understanding of this
idea if you watch the previous video on the
channel, where we designed a system for finding
the top k most frequent items.
Ideally, we should have a single system.
Let me share with you advice from Jay Kreps,
who is one of the authors of Apache Kafka.
We should use a batch processing framework
like MapReduce if we aren’t latency sensitive,
and use a stream processing framework if we
are, but not to try to do both at the same
time unless we absolutely must.
And please note that out today's problem can
indeed be solved with MapReduce.
But MapReduce-based system would have a much
higher latency.
We already discussed the problem with popular
videos.
I will just reiterate the key idea.
We have to spread events coming for a popular
video across several partitions.
Otherwise, a single consumer of a single "hot"
partition may not be able to keep up with
the load.
And will fall behind.
Let's talk more about this.
Imaging a situation when the processing service
cannot keep up with the load.
Maybe because number of events is huge, maybe
because processing of a single event is complicated
and time consuming.
I will not dive too much into details, but
describe the main idea of the solution.
We batch events and store them in the Object
Storage service, for example AWS S3.
Every time we persist a batch of events, we
send a message to a message broker.
For example SQS.
Then we have a big cluster of machines, for
example EC2, that retrieve messages from SQS,
read a corresponding batch of events from
S3 and process each event.
This approach is a bit slower than stream
processing, but faster than batch processing.
Everything is a tradeoff.
Let's summarize what we have discussed.
We start with requirements clarification.
And more specifically, we need to define APIs,
what exactly our system is supposed to do.
We then discuss non-functional requirements
with the interviewer and figure out what qualities
of the system she is most interested in.
We can now outline a high-level architecture
of the system.
Draw some key components on the whiteboard.
At the next stage we should dive deep into
several of those components.
Our interviewer will help us understand what
components we should focus on.
And the last important step is to discuss
bottlenecks and how to deal with them.
And let me quickly remind you some specifics
we discussed for each of these steps.
To define APIs, we discuss with the interviewer
what specific behaviors or functions of the
system we need to design.
We write down verbs characterizing these functions
and start thinking about input parameters
and return values.
We then can make several iterations to brush
up the APIs.
After this step we should be clear on what
the scope of the design is.
To define non-functional requirements, just
know what your options are.
Open a list of non-functional requirements
on wiki and read the list.
There are many of them.
I recommend to focus on scalability, availability
and performance.
Among other popular choices we have consistency,
durability, maintainability and cost.
Try to pick not more than 3 qualities.
To outline a high-level design, think about
how data gets into the system, how it gets
out of the system and where data is stored
inside the system.
Draw these components on the whiteboard.
It is ok to be rather generic at this stage.
Details will follow later.
And although it is not easy, try to drive
the conversation.
Our goal here is to get understanding of what
components to focus on next.
And the interviewer will help us.
While designing specific components, start
with data.
How it is stored, transferred and processed.
Here is where our knowledge and experience
becomes critical.
By using fundamental concepts of system design
and by knowing how to combine these concepts
together, we can make small incremental improvements.
And apply relevant technologies along the
way.
After technical details are discussed, we
can move to discussing other important aspects
of the system.
Listen carefully to the interviewer.
She sees bottlenecks of our design and in
her questions there will be hints what those
bottlenecks are.
And what can really help us here is the knowledge
of different tradeoffs in system design.
We just need to pick and apply a proper one.
Today we covered a big topic.
If you are still with me watching this video
you should be proud of yourself.
Seriously.
There were many system design concepts covered
in the video.
And I hope you have a better understanding
right now why I consider knowledge the key
to the successful system design interview.
And system design in general.
And although we talked about a specific problem
today, like video views counting, the same
ideas can be applied to other problems, for
example counting likes, shares, reposts, ad
impressions and clicks.
The same ideas can be applied to designing
monitoring systems, when we count metrics.
When we design a fraud prevention system we
need to count number of times each credit
card was used recently.
When we design recommendation service we may
use counts as input to machine learning models.
When we design "what's trending" service,
we count all sorts of different reactions:
views, re-tweets, comments, likes.
And many other applications.
There are plenty of other important system
design concepts we have not covered today.
As it is practically impossible to do in a
single video.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwMTMwNTg4NDEsMTg1NDM5OTgwMiwtOT
g5ODcyNTk2LC00ODIzNDgwMjMsLTY3MjkzOTY2MywyMTQ0NTAy
NjY2LC02MzM2MDQ1OTUsLTE1MjM2Mjc3MTIsMTM5MjUwMjI3NC
w0NjY5MjYyNzAsMTU5MzM5NTM1LC0xNzU5NTQzMDEwLC0yMTM1
NTkzNjQsMTQ3MzUxNTc1LDE0NTUxNjQ4MDQsMTY2MDc0NDEwLC
0xOTEwNjMyOTQ3LC02MjE3MjY4NDAsLTEzNTIwMDY2MjUsLTE3
ODQ3NzExNThdfQ==
-->