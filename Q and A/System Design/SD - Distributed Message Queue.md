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
#### FrontEnd web service
- FrontEnd is a lightweight web service, consisting of stateless machines located across several data centers.
- FrontEnd service is responsible for: **request validation, authentication and authorization, SSL termination, server-side data encryption, caching, rate limiting (also known as throttling), request dispatching, request deduplication, usage data collection.**
#### Request validation
- Request validation helps to ensure that all the required parameters are present in the request and values of these parameters honor constraints.
- For example, in our case we want to make sure queue name comes with every send message request. And message size does not exceed a specified threshold.
#### Authentication
- During authentication check we verify that message sender is a registered customer of our distributed queue service.
And during authorization check we verify that sender is allowed to publish messages to the queue it claims.
#### TLS
- TLS is a protocol that aims to provide privacy and data integrity. TLS termination refers to the process of decrypting request and passing on an unencrypted request to the backend service. And we want to do TLS termination on FrontEnd hosts because TLS on the load balancer is expensive. Termination is usually handled by not a FrontEnd service itself, but a separate HTTP proxy that runs as a process on the same host.
#### Server-side Encryption
- Because we want to store messages securely on backend hosts, messages are encrypted as soon as FrontEnd receives them. **Messages are stored in encrypted form and FrontEnd decrypts them only when they are sent back to a consumer**.
#### Cache
- Cache stores copies of source data. In FrontEnd cache we will store metadata information about the most actively used queues. As well as user identity information to save on calls to authentication and authorization services.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5MzQ3OTg0NjMsLTE0NDA5MzAxODddfQ
==
-->