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
#### Non-functional
- When we talk about non-functional requirements, we basically mean such system qualities as scalability, maintainability, testability and others.
- As for non-functional requirements, we want our service to be scalable and support a big number of topics, publishers and subscribers; 
- highly available and survive hardware failures and network partitions; fast, so that messages are delivered to subscribers as soon as possible;and durable, so that messages are not lost and delivered to each subscriber at least once.
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
#### Component in detail
- FrontEnd is a lightweight web service responsible for: request validation, authentication and authorization, SSL termination, server-side encryption, caching, throttling, request dispatching and deduplucation, usage data collection. Specifically, let’s take a look at a FrontEnd host and discuss what components live there. When request lands on the host, the first component that picks it up is a Reverse Proxy. 
	- Reverse proxy is a lightweight server responsible for several things. Such as SSL termination, when requests that come over HTTPS are decrypted and passed further in unencrypted form. At the same time proxy is responsible for encrypting responses while sending them back to clients. 
	- Second responsibility is compression (for example with gzip), when proxy compresses responses before returning them back to clients. This way we reduce the amount of bandwidth required for data transfer.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjAyNTEwMTMwOSwtMTEzMzA2NjA5NCw4OD
Y0NzEyNjcsMTY0MjkzNjc3MiwtNTMwMzU2NTkzXX0=
-->