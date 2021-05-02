### Load balancing
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
### Round Robin
- Round Robin is a CPU scheduling algorithm where each process is assigned a fixed time slot in a cyclic way.  
-  It is simple, easy to implement, and starvation-free as all processes get fair share of CPU.  
- One of the most commonly used technique in CPU scheduling as a core.  
- It is preemptive as processes are assigned CPU only for a fixed slice of time at most.  
- The disadvantage of it is more overhead of context switching, server load is not taken into consideration. If a server is
overloaded or slow, the LB will not stop sending new requests to that server
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTkzODQ4MjY1Nl19
-->