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
``
							A A
Partitioning			  B B B
						
``
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTU0NDg5NDgyMCwyMTIxMDA3Mzc0LC02MT
g0MjUxNTEsLTE5NTIyNzQwOTIsLTE3MzAxNjI2ODQsLTY1OTEy
ODk3NCwtNzMwODA1MjQ1LDE0MTkxODY2MzEsNzEwMDU5Njg5LD
Q0Njc2MjI0MSwxMzY5NDU3NjQsLTE1OTA5MTU0NzAsLTEzNDYz
Mzc4OTQsNDY0NjM5NDgzXX0=
-->