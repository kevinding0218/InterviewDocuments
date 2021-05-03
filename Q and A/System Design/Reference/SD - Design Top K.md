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
										  Processor Host B	[C = 2, D = 1, E = 1] -> heapify [C = 2, D = 1]	-> sorted	- Storage Host(Merge sorted lists)	
```
#### Merge K sorted Lists 
- How do we create a final list that combines information from every Processor host? **It is important to note that Processor hosts only pass a list of size k to the Storage host.**
- We cannot pass all the data, meaning that we cannot pass each Processor hash table to the Storage host, as one combined hash table may be too big to fit in memory. That was the whole point of data partitioning after all, to not accumulate all the data on a single host.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjUwNTQ0MjQxLDE1OTU2MzU1OTAsLTIwOD
g3NDY2MTJdfQ==
-->