###  Problem Statement
- In more general terms, let's say there is a component called Publisher which produces messages that need to be delivered to a group of other components, called Subscribers.
- We could have setup a synchronous communication between Publisher and Subscribers, when Publisher calls each Subscriber in some order and waits for the response.
- But this introduces many different challenges: hard to scale such system when number of subscribers and messages grow and hard to extend such solution to support different types of subscribers. Instead, we can introduce a new system that can register an arbitrary large number of publishers and subscribers and coordinates message delivery between them.
### Function and Non-functional requirement
#### Functional
- when we talk about functional requirements, we want to define system behavior, or more specifically APIs - a set of operations the system will support.
- The core APIs our notification service need to support are: create a topic, publish a
message to a topic and subscribe to a topic to receive published messages. 
- Topic represents a named resource to which messages are sent. You can think of it as a bucket that stores messages from a publisher and all subscribers receive a copy of a message from the bucket.
```
createTopic(topicName)
publish(topicName, message)
subscribe(topicName, endpoint)
```
#### Non-functional
- When we talk about non-functional requirements, we basically mean such system qualities as scalability, maintainability, testability and others.
- Scalable: supports an arbitrarily large number of topics, publishers and subscribers
- Highly Available: survives hardware/network failures, no single point of failure
- Highly Performant: keep end-to-end latency as low as possible
- Durable: messages must not be lost, each subscriber must receive every message at least once
### High level architectureenter image description here```
```
				Metadata Database
						|
				Metadata Service ---------------------------------------------------------------------|	
						|																			  |
Client --(create topic subscribe/publish)--> Load Balaner ---> FrontEnd  --- Temporary Storage --- Sender 	--- Subscribe A, B, C
```
- All requests coming from our clients will go through a load balancer first. This will ensure requests are equally distributed among requests processing servers.
- the component that does this initial request processing is a FrontEnd service.
- We will use a database to store information about topics and subscriptions.
- We will hide the database behind another miscroservice, Metadata service, There are several reasons for this decision.
	- First, separation of concerns, a design principle that teaches us to provide access to the database through a well-defined interface. It greatly simplifies maintenance and ability to make changes in the future.
	- Second, Metadata service will act as a caching layer between the database and other components. We do not want to hit database with every message published to the system. We want to retrieve topic metadata from cache.
- We need to store messages for some period of time. This period will generally be short if all subscribers are available and message was successfully sent to all of them. Or we may need to store messages a bit longer (say several days), so that messages can be retried later if some subscriber is not available right now. And one more component we need is the one that retrieves messages from the message store and sends them to subscribers.
- Sender also needs to call Metadata service to retrieve information about subscribers. When create topic and subscribe APIs are called, we just need to store all this information in the database.
- We followed some pretty common patterns, like having a FrontEnd service behind a load balancer. And having a database for storing metadata and hide this database behind some facade, which is a distributed cache microservice.
### Component in detail
#### FrontEnd Service
- FrontEnd is a lightweight web service responsible for: request validation, authentication and authorization, SSL termination, server-side encryption, caching, throttling, request dispatching and deduplucation, usage data collection. Specifically, let’s take a look at a FrontEnd host and discuss what components live there. When request lands on the host, the first component that picks it up is a Reverse Proxy. 
```
								FrontEnd Service
					/					|			\
request ---> Reverse Proxy			Local Disk		Cache
								/		|		\
						ServiceLogs   Metris	AuditLogs	(Agents)
							|			|			|
						 SL Storage    M Storage  Audit Storage
```
##### Proxy
	- Reverse proxy is a lightweight server responsible for several things. Such as SSL termination, when requests that come over HTTPS are decrypted and passed further in unencrypted form. At the same time proxy is responsible for encrypting responses while sending them back to clients. 
	- Second responsibility is compression (for example with gzip), when proxy compresses responses before returning them back to clients. This way we reduce the amount of bandwidth required for data transfer.
	- Next proxy feature we may use is to properly handle FrontEnd service slowness. We may return HTTP 503 status code (which is service unavailable) if FrontEnd service becomes slow or completely unavailable. Reverse proxy then passes request to the FrontEnd web service.
- We know that for every message that is published, FrontEnd service needs to call Metadata service to get information about the message topic. To minimize number of calls to Metadata service, FrontEnd may use local cache. We may use some well-known cache implementations (like Google Guava) or create our own implementation of LRU cache (least recently used).
##### Logging
- FrontEnd service also writes a bunch of different logs. We definitely need to log information about service health, write down exceptions that happen in the service.
##### metrics
- We also need to emit metrics. This is just a key-value data that may be later aggregated and used to monitor service health and gather statistics. For example, number of requests, faults, calls latency, we will need all this information for system monitoring.
##### Audit
- Also, we may need to write information that may be used for audit, for example log who and when made requests to a specific API in the system.
##### Agent
- Important to understand here, is that FrontEnd service is responsible for writing log data. But the actual log data processing is managed by other components, usually called agents. Agents are responsible for data aggregation and transferring logs to other system, for post processing and storage. This separation of responsibilities is what helps to make FrontEnd service simpler, faster and more robust.
#### Metadata Service
- A web service responsible for storing information about topics and subscriptions in the database. It is a **distributed cache**.
- When our notification service becomes so popular that we have millions of topics, all this information cannot be loaded into a memory on a single host. Instead, information about topics is divided between hosts in a cluster.
- Cluster represents a consistent hashing ring. Each FrontEnd host calculates a hash, for example MD5 hash, using some key, for example a combination of topic name and topic owner identifier. Based on the hash value, FrontEnd host picks a corresponding Metadata service host.
##### how FrontEnd hosts know which Metadata service host to call.
1. In the first option we introduce a component responsible for coordination(Configuration Service, e.g. ZooKeper). This component knows about all the Metadata service hosts, as those hosts constantly send heartbeats to it. Each FrontEnd host asks Configuration service what Metadata service host contains data for a specified hash value.
	- Every time we scale out and add more Metadata service hosts, Configuration service becomes aware of the changes and re-maps hash key ranges, this is so called Central registry
```
		ConfigurationService	
		/					\\\
FrontEnd Host				[A-G][H-N][O-T][U-Z]
```
2. In the second option we do not use any coordinator. Instead, we make sure that every FrontEnd host can obtain information about all Metadata service hosts. And every FrontEnd host is notified when more Metadata service hosts are added or if any Metadata host died due to a hardware failure.
	- Gossip protocol: Computer systems typically implement this type of protocol with a form of random "peer selection": with a given frequency, each machine picks another machine at random and shares data.
```
FrontEnd Host	---			[A-G]
				\			[H-N]
				\			[O-T][U-Z]
```
#### Temporary Stroage Service
- messages supposed to stay in this storage for a very short period of time. Sooner we can deliver messages to subscribers is better, unless topic is configured to deliver messages with some delay.
- What do we expect from the Temporary Storage service?
	- First, it must be fast, highly-available and scalable.
	- Second, it has to guarantee data persistence, so that messages survive unavailability of a subscriber. And can be re-delivered later.
##### start discussing databases with the interviewer
- consider pros and cons of SQL versus NoSQL databases, evaluate different NoSQL database types and give specific names to the interviewer.
- For example, when we consider SQL or NoSQL for storing messages, we may mention that we do not need ACID transactions, we do not need to run complex dynamic queries, we do not plan to use this storage for analytics or data warehousing. Instead, we need a database that can be easily scaled for both writes and reads. It should be highly available and tolerate network partitions.
- Summing all these up, it is clear that NoSQL wins for our use case.
	- If we need to choose a particular NoSQL database type, we need to mention that messages have **limited size** (let’s say not more than 1 MB), meaning that we **do not actually need a document store**. And there is **no any specific relationship** between messages. And thus, we can **exclude graph type** as well. Which leaves us with either column or **key-value** database types.
	- we can mention several well-regarded names of these two database types. For example, Apache Cassandra and Amazon DynamoDB.
#### in-memory storage.
<!--stackedit_data:
eyJoaXN0b3J5IjpbNDM0MzI3OTE4LC0xMTMzMDY2MDk0LDg4Nj
Q3MTI2NywxNjQyOTM2NzcyLC01MzAzNTY1OTNdfQ==
-->