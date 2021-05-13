### Ways of sharing info between hosts
1. **Tell every host everything**
```
A	-	B
|	X	|
C	-	D
```
- It means that every host in the cluster knows about every other host in the cluster and share messages with each one of them. **For cluster A, B, C & D, each cluster will communicate the other**
- You may also heard a term full mesh that describes this network topology. How do hosts discover each other? When a new host is added, how does everyone else know? And there are several approaches used for hosts discovery.
- One option is to use **a 3-rd party service which will listen to heartbeats coming from every host. As long as heartbeats come, host is keep registered in the system. If heartbeats stop coming, the service unregister host that is no longer alive.** And all hosts in our cluster ask this 3-rd party service for the full list of members.
-  **when user provides a list of hosts via some configuration file. We then need a way to deploy this file across all cluster nodes every time this list changes**. Full mesh broadcasting is relatively straightforward to implement.
- But the **main problem with this approach is that it is not scalable**. Number of messages grows quadratically with respect to the number of hosts in a cluster. Approach works well for small clusters, but we will not be able to support big clusters. So, let’s investigate some other options that may require less messages to be broadcasted within the cluster.
2. **Gossip Communication**
```
A	-	B
|
C	-	D
```
- This protocol is based on the way that epidemics spread. Computer systems typically implement this type of protocol with a form of random "peer selection": with a given frequency, each machine picks another machine at random and shares data. B picks A, A picks C, C picks D
- By the way, rate limiting solution at Yahoo uses this approach.
3. Distributed Cache
```
A	B
		-> In memory store (e.g. Redis)
C	D
```
- Next option is to use distributed cache cluster. For example, Redis. Or we can implement custom distributed cache solution.
- **The pros for this approach is that distributed cache cluster is relatively small and our service cluster can scale out independently**. This cluster can be shared among many different service teams in the organization. Or each team can setup their own small cluster.
- In case of a rally large clusters, like tens of thousands of hosts, we may no longer rely on host-to-host communication in the service cluster as it becomes costly. And we need a separate cluster for making a throttling decision. This is a distributed cache option we discussed above. But the drawback of this approach is that it **increases latency and operational cost**. It would be good to have these tradeoff discussions with your interviewer.
4. Coordination Service
```
A	B
| /
C - D
(leader C)
```
- A coordination service that **helps to choose a leader**. Choosing a leader helps to decrease number of messages broadcasted within the cluster. **Leader asks everyone to send it all the information. And then it calculates and sends back the final result**. E.g: Cordination Service choose Host C and let C be responsible for A, B & D, So, each host only needs to talk to a leader or a set of leaders, where each leader is responsible for its own range of keys.  
- but the main drawback is that we need to setup and maintain Coordination Service. **Coordination service is typically a very sophisticated component that has to be very reliable and make sure one and only one leader is elected**.
5. Random Leader Selection
```
A(L)- 	B
|	\	|
C	-	D(L)
```
- Let’s say we use a simple algorithm to elect a leader. But because of the simplicity of the algorithm **it may not guarantee one and only one leader.** So that we may end up with multiple leaders being elected.
- Is this an issue? Actually, no. **Each leader will calculate rate and share with everyone else.** This will cause unnecessary messaging overhead, but **each leader will have its own correct view of the overall rate**. And to finish message broadcasting discussion, I want to talk about communication protocols, how hosts talk to each other.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTgxMTg1MTg1MF19
-->