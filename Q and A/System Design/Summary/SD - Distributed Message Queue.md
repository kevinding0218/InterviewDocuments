[Link](https://serhatgiydiren.github.io/system-design-interview-distributed-message-queue)
### What is distributed message queue
- Let's say there are two web-services called producer and consumer, and they need to communicate with each other.
- One option is to setup a synchronous communication, when producer makes a call to a consumer and waits for a response.
	- This approach has its own pros and cons. Synchronous communication is easier and faster to implement. At the same time synchronous communication makes it harder to deal with consumer service failures. 
	- We need to think when and how to properly retry failed requests, how not to overwhelm consumer service with too many requests and how to deal with a slow consumer service host. 
- Another option is to introduce a new component that helps to setup asynchronous communication. Producer sends data to that component and exactly one consumer gets this data a short time after. Such component is called a queue.
- And it is distributed, because data is stored across several machines. 
- Please do not confuse queue with a topic.
	- In case of a topic, message that is published goes to each and every subscriber.
	- In case of a queue, message is received by one and only one consumer.
### Functional Requirements
- At this stage of the interview it may be hard to come up with a definitive set of requirements. And it’s usually not needed.
- Time limit allows us to only focus on several core APIs, like **send message(messageBody) and receive message**.
	- Among functional requirements, we can be asked to support create and delete queue APIs, or delete message API.
	- There may be specific requirements for the producer (for example system needs to avoid duplicate submissions), or security requirements, or an ask to implement a specific ordering guarantee.
### Non Functional Requirements
- we want our system to be **Scalable** and handle load increase, highly **Available** and tolerate hardware and network failures, highly **Performant**, so that both send and receive operations are fast, and **Durable**, so that data is persisted once submitted to the queue.
- there may be many other requirements either explicitly stated by the interviewer or intentionally omitted.
- the interviewer may define specific service level agreement numbers (so called SLA, for example minimum throughput our system needs to support), or requirements around cost-effectiveness (for example system needs to minimize hardware cost or operational support cost)
### Architecture
- Let’s start with components that are common for many distributed systems.
- First, we need a **virtual IP**. VIP refers to the symbolic hostname (for example myWebService.domain.com) that resolves to a load balancer system.
- Next, we have a **load balancer**. A load balancer is a device that routs client requests across a number of servers.
- Next, we have a **FrontEnd web service**. A component responsible for initial request processing, like validation, authentication, etc.
- Queue metadata information like its name, creation date and time, owner and any other configuration settings will be stored in a ** Metadata Database**.
- And best practices dictate that databases should be hidden behind some facade, a dedicated web service responsible for handling calls to a database, as **Metadata Service**
- And we need a place to store queue messages. So, lets introduce a backend web service, that will be responsible for message persistence and processing.
```
Client(producer)						Metadata Database
				\						Metadata Service
				---> VIP -- Load Balancer -- FrontEnd -- Backend
				/
Client(consumer)
```
#### Load balancing
- Load balancing is a big topic. And unless interviewer encourages you to dive deep into load balancing topic, we better not deviate too much from the main question of the interview. Always try to stay focused on what really matters.
- Internals of how load balancers work may not matter, but in order to make sure non-functional requirements to the system we build are fully met, we need to explain how load balancers will help us achieve high throughput and availability.
- When domain name is hit, request is transferred to one of the VIPs registered in DNS for our domain name. VIP is resolved to a load balancer device, which has a knowledge of FrontEnd hosts.
1. load balancer seems like a single point of failure. What happens if load balancer device goes down?
2. load balancers have limits with regards to number of requests they can process and number of bytes they can transfer. What happens when our distributed message queue service becomes so popular that load balancer limits are reached?
- To address **high availability** concerns, load balancers utilize a **concept of primary and secondary nodes**. The primary node accepts connections and serves requests while the secondary node monitors the primary. If, for any reason, the primary node is unable to accept connections, the secondary node takes over.
- As for scalability concerns, a concept of multiple VIPs (sometimes referred as **VIP partitioning**) can be utilized.
- In DNS we assign multiple A records to the same DNS name for the service. As a result, requests are partitioned across several load balancers. And by spreading load balancers across several data centers, we improve both availability and performance.
```
Distributed-Msg-Queue.domain.com
|		/	VIP1 LoadBalancer A -	FrontEnd-Host-1/2.domain.com	- Data Center A
Client	-	VIP2 LoadBalancer B - 	FrontEnd-Host-3/4.domain.com	- Data Center B
		\	VIP3 LoadBalancer C - 	FrontEnd-Host-5/6.domain.com	- Data Center C
```
#### FrontEnd web service
- FrontEnd is a lightweight web service, consisting of stateless machines located across several data centers.
- FrontEnd service is responsible for: **request validation, authentication and authorization, SSL termination, server-side data encryption, caching, rate limiting (also known as throttling), request dispatching, request deduplication, usage data collection.**
##### Request validation
- Request validation helps to ensure that all the required parameters are present in the request and values of these parameters honor constraints.
- For example, in our case we want to make sure queue name comes with every send message request. And message size does not exceed a specified threshold.
##### Authentication
- During authentication check we verify that message sender is a registered customer of our distributed queue service.
And during authorization check we verify that sender is allowed to publish messages to the queue it claims.
##### TLS
- TLS is a protocol that aims to provide privacy and data integrity. TLS termination refers to the process of decrypting request and passing on an unencrypted request to the backend service. And we want to do TLS termination on FrontEnd hosts because TLS on the load balancer is expensive. Termination is usually handled by not a FrontEnd service itself, but a separate HTTP proxy that runs as a process on the same host.
##### Server-side Encryption
- Because we want to store messages securely on backend hosts, messages are encrypted as soon as FrontEnd receives them. **Messages are stored in encrypted form and FrontEnd decrypts them only when they are sent back to a consumer**.
##### Cache
- Cache stores copies of source data. In FrontEnd cache we will store metadata information about the most actively used queues. As well as user identity information to save on calls to authentication and authorization services.
##### Rate Limiting/Throttling
- Rate limiting or throttling is the process of limiting the number of requests you can submit to a given operation in a given amount of time. 
- Throttling protects the web service from being overwhelmed with requests. Leaky bucket algorithm is one of the most famous.
##### Request Dispatching
- Responsible for all the activieties associated with sending requests to backend services (clients management, response handling, resources isolation, etc), Frontend service makes remote calls to at least two other web services: Metadata service and Backend service, 
- FrontEnd service creates HTTP clients for both services and makes sure that calls to these services are properly isolated. It means that when one service, let's say Metadata service experiences a slowdown, requests to Backend service are not impacted.
- Bulkhead pattern helps to isloate elements of an application into pools so that if one fails, the others will continue to function
- Circuit Breaker pattern prevents an application from repeatedly trying to execute an operation that's likely to fail
##### Deduplication
- It may occur when a response from a successful send message request failed to reach a client. 
- Lesser an issue for ‘at least once’ delivery semantics, a bigger issue for ‘exactly once’ and ‘at most once’ delivery semantics, when we need to guarantee that message was never processed more than one time.
- Caching is usually used to store previously seen request ids to avoid deduplication.
##### Data Collection 
- When we gather real-time information that can be used for audit, gather real-time information that can be used for audit and billing (invoices)
#### Metadata Service
- Metadata service stores information about queues. Every time queue is created, we store information about it in the database.
- Conceptually, Metadata service is a caching layer between the FrontEnd and a persistent storage. It handles many reads and a relatively small number of writes. As we read every time message arrives and write only when new queue is created. Even though strongly consistent storage is preferred to avoid potential concurrent updates, it is not strictly required.
- FrontEnd service makes remote calls to at least two other web services: Metadata service and backend service.
- FrontEnd service creates HTTP clients for both services and makes sure that calls to these services are properly isolated. It means that when one service let’s say Metadata service experiences a slowdown, requests to backend service are not impacted.
#### Cache Clusters
- The **First** option is **when cache is relatively small** and we can **store the whole data set on every cluster node**. FrontEnd host **calls a randomly chosen Metadata service host**, because all the cache cluster nodes contain the same information.
- The **Second** approach is to **partition data into small chunks, called shards**. Because data set is too big and cannot be placed into a memory of a single host. So, we **store each such chunk of data on a separate node in a cluster**. **FrontEnd then knows which shard stores the data** and calls the shard directly.
- The **Third** approach is We also partition data into shards, but **FrontEnd does not know on what shard data is stored**. So, **FrontEnd calls a random** Metadata service host and **host itself knows where to forward** the request to.
- In option 1, we can introduce a **load balancer between FrontEnd and Metadata service**. As all Metadata service hosts are equal and FrontEnd does not care which Metadata host handles the request.
- In option 2 and 3, **Metadata hosts represent a consistent hashing ring.**
#### Backend Service
- Is database an option? Yes, it is. But not the best one and let me explain why.
	- We are building a distributed message queue, a system that should be able to handle a very high throughput. And this means that all this throughput will be offloaded to the database. In other words, a problem of building a distributed message queue becomes a problem of building a database that can handle high throughput. And we know that highly-available and scalable databases exist out there.
	- Store in memory or file system, As we may need to store messages for days or even weeks, we need a more durable storage, like a local disk. At the same time newly arrived messages may live in memory for a short period of time or until memory on the backend host is fully utilized.
- How do we replicate data?
	- We will send copies of messages to some other hosts, so that data can survive host hardware or software failures.
And finally, let's think about how FrontEnd hosts select backend hosts for both storing messages and retrieving them.
- Message comes to the FrontEnd, FrontEnd consults Metadata service what backend host to send data to. Message is sent to a selected backend host and data is replicated. And when receive message call comes, FrontEnd talks to Metadata service to identify a backend host that stores the data.
- How Backend hosts relate to each other?
	- We will consider two options of how backend hosts relate to each other.
	- **In the first option, (Leader-Follower relationship)** each backend instance is considered a leader for a particular set of queues. And by leader we mean that all requests for a particular queue (like send message and receive message requests) go to this leader instance.
	- For example, 
		- Send message request comes to a FrontEnd instance. Message comes to a queue with ID equal to q1. FrontEnd service calls Metadata service to identify a leader backend instance for this queue. In this particular example, instance B is a leader for q1. 
		- Message is sent to the leader and the leader is fully responsible for data replication. 
		- When receive message request comes to a FrontEnd instance, it also makes a request to the Metadata service to identify the leader for the queue. Message is then retrieved from the leader instance and leader is responsible for cleaning up the original message and all the replicas.
		- We need a component that will help us with leader election and management. Let’s call it **In-cluster manager**. And as already mentioned, in-cluster manager is responsible for maintaining a mapping between queues, leaders and followers. In-cluster manager is a very sophisticated component. It has to be reliable, scalable and performant. Creating such a component from scratch is not an easy task.
	- In the second option, we have a set of small clusters, each cluster consists of 3-4 machines distributed across several data centers. 
		- When send message request comes, similar to the previous design option, we also need to call Metadata service to identify which cluster is responsible for storing messages for the q1 queue.
		- After that we just make a call to a randomly selected instance in the cluster. And instance is responsible for data replication across all nodes in the cluster.
		- When receive message request comes and we identified which cluster stores messages for the q1 queue, we once again call a randomly selected host and retrieve the message. Selected host is responsible for the message cleanup. 
		- As you may see, we no longer need a component for leader election, but we still need something that will help us to manage queue to cluster assignments. Let’s call this component an **Out-cluster manager**, this component will be responsible for maintaining a mapping between queues and clusters.
	- While in-cluster manager manages queue assignment within the cluster, out-cluster manager manages queue assignment across clusters.
	- In-cluster manager needs to know about each and every instance in the cluster. Out-cluster manager may not know about each particular instance, but it needs to know about each cluster.
	- In-cluster manager listens to heartbeats from instances. Out-cluster manager monitors health of each independent cluster. 
	- And while in-cluster manager deals with host failures and needs to adjust to the fact that instances may die and new instances may be added to the cluster, out-cluster manager is responsible for tracking each cluster utilization and deal with overheated clusters.
	- Meaning that new queues may no longer be assigned to clusters that reached their capacity limits.
- What about really big queues?
	- When a single queue gets so many messages that a single leader (in design option A) or a single cluster (in design option B) cannot handle such a big load?
	- In-cluster manager splits queue into parts (partitions) and each partition gets a leader server.
	- Out-cluster manager may split queue across several clusters. So that messages for the same queue are equally distributed between several clusters.
#### Queue creation and deletion.
- Queue can be auto-created, for example when the first message for the queue hits FrontEnd service, or we can define API for queue creation. API is a better option, as we will have more control over queue configuration parameters.
- Delete queue operation is a bit controversial, as it may cause a lot of harm and must be executed with caution. For this reason, you may find examples of well-known distributed queues that do not expose deleteQueue API via public REST endpoint. Instead, this operation may be exposed through a command line utility, so that only experienced admin users may call it.
- As for a message deletion, there are several options at our disposal.
	- One option is not to delete a message right after it was consumed. In this case consumers have to be responsible for what they already consumed. And it is not as easy as it sounds. As we need to maintain some kind of an order for messages in the queue and keep track of the offset, which is the position of a message within a queue. Messages can then be deleted several days later, by a job. This idea is used by Apache Kafka.
	- The second option, is to do something similar to what Amazon SQS is doing. Messages are also not deleted immediately, but marked as invisible, so that other consumers may not get already retrieved message. Consumer that retrieved the message, needs to then call delete message API to delete the message from a backend host. And if the message was not explicitly deleted by a consumer, message becomes visible and may be delivered and processed twice. We know that messages need to be replicated to achieve high durability. Otherwise, if we only have one copy of data, it may be lost due to unexpected hardware failure.
#### Message replication
- Messages can be replicated synchronously or asynchronously.
- Synchronously means that when backend host receives new message, it waits until data is replicated to other hosts. And only if replication is fully completed, successful response is returned to a producer.
- Asynchronous replication means that response is returned back to a producer as soon as message is stored on a single backend host. Message is later replicated to other hosts.
- Both options have pros and cons.
	- Synchronous replication provides higher durability, but with a cost of higher latency for send message operation.
	- Asynchronous replication is more performant, but does not guarantee that message will survive backend host failure.
#### Message Delivery Gurantees
- **At most once**, when messages may be lost but are never redelivered.
- **At least once**, when messages are never lost but may be redelivered.
- And **exactly once**, when each message is delivered once and only once.
- You probably have a question already, why do we need three? Will anyone ever want other than exactly once delivery? 
	- In a distributed message queue system there are many potential points of failure. Producer may fail to deliver or deliver multiple times, data replication may fail, consumers may fail to retrieve or process the message. All this adds complexity and leads to the fact that most distributed queue solutions today support at-least-once delivery, as it provides a good balance between durability, availability and performance.
- With a pull model, consumer constantly sends retrieve message requests and when new message is available in the queue, it is sent back to a consumer.
- With a push model, consumer is not constantly bombarding FrontEnd service with receive calls. Instead, consumer is notified as soon as new message arrives to the queue.
- Pros and Cons, from a distributed message queue perspective pull is easier to implement than a push. But from a consumer perspective, we need to do more work if we pull.
#### FIFO == Queue?
- Many of us think of FIFO acronym when we hear about queues. FIFO stands for first-in, first-out, meaning that the oldest message in a queue is always processed first. But in distributed systems, it is hard to maintain a strict order. Message A may be produced prior to message B, but it is hard to guarantee that message A will be stored and consumed prior to message B.
For these reasons many distributed queue solutions out there either does not guarantee a strict order. Or have limitations around throughput, as queue cannot be fast while it’s doing many additional validations and coordination to guarantee a strict order.
#### Security
- With regards to security, we need to make sure that messages are securely transferred to and from a queue. Encryption using SSL over HTTPS helps to protect messages in transit. And we also may encrypt messages while storing them on backend hosts. We discussed this when talked about FrontEnd service responsibilities.
#### Monitoring (Datadog)
- Monitoring is critical for every system. With regards to distributed message queue, we need to monitor components (or microservices) that we built: fronted, metadata and backend services.
- As well as provide visibility into customer’s experience. In other words, we need to monitor health of our distributed queue system and give customers ability to track state of their queues.
- Each service we built has to emit metrics and write log data. As operators of these services we need to create dashboards for each microservice and setup alerts. And customers of our queue have to be able to create dashboards and set up alerts as well. For this purpose, integration with monitoring system is required.
#### Final Review
- Is our system scalable?
	- Yes. As every component is scalable. When load increases, we just add more load balancers, more FrontEnd hosts, more Metadata service cache shards, more backend clusters and hosts.
- Is our system highly available?
	- Yes. As there is no a single point of failure, each component is deployed across several data centers. Individual hosts may die, network partitions may happen, but with this redundancy in place our system will continue to operate.
- Is our system highly performant?
	- It’s actually very well depends on the implementation, hardware and network setup. Each individual microservice needs to be fast. And we need to run our software in high-performance data centers.
- Is our system durable?
	- Yes, We replicate data while storing and ensure messages are not lost during the transfer from a producer and to a consumer.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTIyODEyMTUzOSwxOTQ5OTg4Nzk3XX0=
-->