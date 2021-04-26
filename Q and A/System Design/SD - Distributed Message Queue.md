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
#### Each Component
##### Load balancing
- Load balancing is a big topic. And unless interviewer encourages you to dive deep into load balancing topic, we better not deviate too much from the main question of the interview. Always try to stay focused on what really matters.
- Internals of how load balancers work may not matter, but in order to make sure non-functional requirements to the system we build are fully met, we need to explain how load balancers will help us achieve high throughput and availability.
- When domain name is hit, request is transferred to one of the VIPs registered in DNS for our domain name. VIP is resolved to a load balancer device, which has a knowledge of FrontEnd hosts.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbOTE3Mzg4MzIxLC0xNDQwOTMwMTg3XX0=
-->