## Design Top K
### Functional Requirement
- return a list of K most frequent items
- because the list changes over time, we also need to provide a time interval, and more specifically, start and end time of that interval.
### Non Functional Requirement
- Let's do some analysis on the Daily Active User (DAU) or Monthly Active User (MAU)
	- **QPS**:  ~ 100k
	- **Peak QPS** ~ 300k
- **High scalability**: supports an arbitrarily large number of posts or able to **handle load increase**
- **High availability**: **survives hardware/network failures**
- **High performant**: keep end-to-end **latency as low as possible**, avoid heavy calculations while calling the topK API.
- Accuracy
### Start with Single Host
- let's assume the whole data set can be loaded into a memory of that single host. For example, we have a list of events (video views). Every time user opens a video, we log such event by adding video identifier to the list of events. A, B, C, D represent unique video identifiers. Given a list of events, how do we calculate the k most frequent elements?
- First, we calculate how many times each element appears in the list. So, **we create a hash table that contains frequency counts.**
- And to get top k elements we can either sort the hash table based on frequency counts. Or we can add all elements into a heap data structure. **We make sure heap contains only k elements every time we add new element to the heap.**
- Heap approach is faster. When we sort all elements in the hash table, the time complexity of such algorithm is n*log(n), where n is the number of elements in the hash table. By using heap, we can reduce time complexity to be **n*log(k)**.
```
TopK method gets a list of events as the input And returns a list of k heavy hitters.
We then create a hash table that counts how many times each video appeared in the list of events.
We define a min heap data structure (priority queue in Java) And add each element from the hash table to the heap.
While doing this, we check if heap has more than k elements. And if this is the case, we remove a top element from the heap. Because this is a min heap, the top element of the heap is the one with the minimum frequency count.
Elements with higher frequency count remain in the heap, while elements with lower frequency count are removed periodically.This way we make sure that only heavy hitters remain in the heap.
```
#### Cons
-  the first and the most obvious problem with this solution - **it is not scalable**. If events are coming with a high rate, **single host will quickly become a bottleneck**. So, we may want to start **processing events in parallel.**
### Load Balancer
- This may be a classic load balancer or a distributed queue. Each event then goes to one of the hosts in a cluster. Let's call them Processor hosts. And because the same video identifier may appear on different Processor hosts, each Processor needs to flush accumulated data to a single Storage host.
```
A B C A A D C A B C -> LB -	Processor Host A[C = 2, D = 1, A = 2, B = 1]- Storage Host [C = 3, D = 1..]
						  \												/
							Processor Host B[C = 1, A = 2, B = 1]		
```
#### Pros
- It is a bit better now, we can process events in parallel. **Total throughput of the system has increased**.
#### Cons
- another evident problem of this solution is memory. We may **use too much memory on each Processor host as well as Storage host**. There are billions of videos on Youtube. Even if we store a fraction of this number in memory, hash table will become huge.
### Data Partitioner
- this component is responsible for **routing each individual video identifier to its own Processor host. Each Processor host only stores a subset of all the data.**
- we follow the same procedure as we did for a single host. We build a hash table, create a heap and add all elements from the hash table to the heap. Now, **each Processor host contains its own list of k heavy hitters. And each such list is sorted.**
```
A E C F A D C A B B -> Data Partitioner - Processor Host A[B = 2, F = 1, A = 3] -> heapify [B = 2, A = 3]	-> sorted								\
										  Processor Host B[C = 2, D = 1, E = 1] -> heapify [C = 2, D = 1]	-> sorted	- Storage Host(Merge sorted lists)	
```
#### Merge K sorted Lists - O(kn×logK)
- How do we create a final list that combines information from every Processor host? **It is important to note that Processor hosts only pass a list of size k to the Storage host.**
- We cannot pass all the data, meaning that we cannot pass each Processor hash table to the Storage host, as one combined hash table may be too big to fit in memory. That was the whole point of data partitioning after all, to not accumulate all the data on a single host.
#### Pros
- By partitioning the data, we **increased both scalability and throughput**.
#### Cons
- All this time we were talking about bounded data sets or data sets of limited size. Such data sets can indeed be split into chunks, and after finding top k heavy hitters for each chunk we just merge results together.
- But **streaming data is unbounded**, essentially **infinite**. Users keep clicking on videos every second. In these circumstances, Processor hosts can **accumulate data only for some period of time**, let's say 1-minute, and will flush 1-minute data to the Storage host. So, the Storage host stores a list of heavy hitters for every minute. And remember that this is only top k heavy hitters. **Information about all other elements, that did not make to the top k list is lost.** We cannot afford storing information about every video in memory.
- Another problem with this architecture, is that although it may seem simple, it is not. Every time we introduce data partitioning, we need to deal with data replication, so that copies of each partition are stored on multiple nodes. We need to think about rebalancing, when a new node is added to the cluster or removed from it. We need to deal with hot partitions.
### Challenge
- On one hand we need the whole data set for a particular time period, let's say 1-day. And on the other hand, we cannot accumulate data in memory for the whole day. What should we do?
- Let's **store all the data on disk and use batch processing framework to calculate a top k list**, and this is where **MapReduce** comes into play.
### High Level Architecture
```
Client -> API Gateway -> Distributed Messaging System -> Fast Path Count-Min Sketch Processcor -> Storage
													  \
														 Slow Path Data Partitioner -> Distributed Messaging System -> Partition Processor -> Distributed File System -> Frequency Count MR Job -> Top K MR Job
```
#### API Gateway
- **Every time user clicks on a video**, request goes through API Gateway, component that represents a **single-entry point** into a video content delivery system.
- API Gateway **routes client requests to backend services**. Nowadays majority of public distributed systems use API Gateways, it's a widely spread practice.
- For our use case, we are interested in one specific function of API Gateways, **log generation, when every call to API is logged.** Usually these logs are used for **monitoring, audit, billing**. We will use these logs for counting how many times each video was viewed.
- We may implement a background process that reads data from logs, does some initial aggregation, and sends this data for further processing. We **allocate a buffer in memory on the API Gateway host, read every log entry and build a frequency count hash table** we discussed before.
- This **buffer should have a limited size**, and **when buffer is full, data is flushed**. If buffer is **not full** for a specified period of time, we can **flush based on time**.
- There are also other options, like aggregating data on the fly, without even writing to logs. Or completely skip all the aggregation on the API Gateway side and send information about every individual video view further down for processing.
- We better serialize data into a compact binary format. This way we save on network IO utilization if request rate is very high. And let CPU pay the price. Once again, all these considerations depend on what resources are available on the API Gateway host: memory, CPU, network or disk IO.
#### Distributed Messaging System
- Initially aggregated data is then sent to a distributed messaging system, like **Apache Kafka, AWS Kinesis**. 
- Internally Kafka splits messages across several partitions, where each partition can be placed on a separate machine in a cluster. At this point we do not have any preferences how messages are partitioned. Default random partitioning will help to distribute messages uniformly across partitions. 
#### Fast/Slow Path
- We will split our data processing pipeline into two parts: fast path and slow path. 
- On the **fast path**, we will calculate a list of k heavy hitters **approximately**. And **results will be available within seconds**.
- On the **slow path**, we will calculate a list of k heavy hitters **precisely**. And results **will be available within minutes or hours**, depending on the data volume.
#### Fast Path
- **Every time we keep data in memory, even for a short period of time, we need to think about data replication. Otherwise, we cannot claim high availability for a service, as data may be lost due to hardware failures.**
- Create count-min sketch and aggregates data for a shor period of time(seconds)
- Because memory is no longer a problem, no need to partition the data
- Data replicate is nice to have, but may not be strictly required.
- Every several seconds Fast Processor flushes data to the Storage. **Remember that count-min sketch has a predefined size, it does not grow over time**. Nothing stops us from aggregating for longer than several seconds, we may wait for minutes.
#### Storage
- The Storage component is a service in front of a database. And it stores the final top k list for some time interval, for example every 1 minute or 5 minutes.
- SQL or NoSQL, stores a list of top k elements for a period of time,  we only deal with a small fraction of requests that landed initially on API Gateway hosts.
- Data replication is required
#### Slow Path
- On the slow path we also need to aggregate data, but we want to **count everything precisely.** There are several options how to do this. One option is to let **MapReduce** do the trick. We dump all the data to the **distributed file system**, for example HDFS or object storage, for example **S3**. And run two MapReduce jobs, **one job to calculate frequency counts and another job to calculate the actual top k list.**
#### Data Partitioner
- Partitioner will take each video identifier and send information to a correspondent partition in another Kafka cluster. And as we mentioned before, every time we partition data, we need to think about possibility of hot partitions, Data Partitioner read batches of events and parse them into individual events.
- And our partitioner service should take care of it. Now, each partition in Kafka or shard in Kinesis, depending on what we use, stores a subset of data. **Both Kafka and Kinesis will take care of data replication**.
#### Partition Processor
- we need a component that will read data from each partition and aggregate it further.
- It will aggregate data in memory over the course of several minutes, batch this information into files of the predefined size and send it to the distributed file system, where it will be further processed by MapReduce jobs.
- Partition Processor may also send aggregated information to the Storage service.
- If accuracy is important and results should be calculated in a matter of minutes, we need to partition the data and aggregate in memory. 
- And if time is not an issue but we still need accurate results and data set is big, Hadoop MapReduce should be our choice.
#### Map Reduce
- In MapReduce data processing is split into phases. The input to a MapReduce job is a set of files split into independent chunks which are processed by the map tasks in a parallel manner.
```
Frequency Count MapReducer
Input		Split		Map		Shuffle and Sort	Reduce		Output
B = 3					B,3		A(5,7)				A, 12		A = 12
A = 5		~			A,5		B(3,6)				B, 9		B = 9
C = 2					C,2										C = 2
																D = 15
B = 6					B,6		C(2)				C, 2
D = 15		~			D,15	D(15)				D, 15
A = 7					A,7

Top K Map Reducer
A = 12					Local Top K
B = 9					Local Top K					Global Top K Final Top K
C = 2					Local Top K
D = 15
```
#### Data Retrieval
```
												Merge 1 min list to get 5 min list
												1:00  1:01  1:02  1:03  1:04
Client -> top K -> API Gateway -> Storage ->	topK  topK  topK  topK  topK
									
```
- First of all API Gateway should expose topK operation. API Gateway will route data retrieval calls to the Storage service. And the Storage service just retrieves the data from its underlying database. This requires some more explanation.
- Let's say our fast path creates approximate top k list for every 1-minute interval. And our slow path creates precise top k list for every 1-hour interval, 24 lists total for a day.
- When user asks for the top k list for the last 5 minutes, we just merge together 5 1-minute lists. And get the final list, which is also approximate.
- When user asks for the top k list for some 1-hour interval, for example from 1 pm till 2 pm, it is easy, we have precise lists calculated for each hour of the day.
- But when user asks for the top k list for some 2-hour interval, for example from 1 pm till 3 pm, we cannot give back a precise list. The best we can do is to merge together 2 1-hour lists. And results may no longer be 100% correct.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwNjA2NjE3MTgsOTc5MzM1MTQsMTAzMj
k2NTY5NCwtMTE1NzYzMTgxOSwxNTk1NjM1NTkwLC0yMDg4NzQ2
NjEyXX0=
-->