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
- let's assume the whole data set can be loaded into a memory of that single host. For example, we have a list of events (video views). Every time user opens a video, we log such event by adding video identifier to the list
of events. A, B, C, D represent unique video identifiers. Given a list of events, how do we calculate the k most frequent elements?
- First, we calculate how many times each element appears in the list. So, we create a hash table that contains frequency counts.
- And to get top k elements we can either sort the hash table based on frequency counts. Or we can add all elements into a heap data structure. We make sure heap contains only k elements every time we add new element to the heap.
- Heap approach is faster. When we sort all elements in the hash table, the time complexity of such algorithm is n*log(n), where n is the number of elements in the hash table. By using heap, we can reduce time complexity to be n*log(k).
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTU5NTYzNTU5MCwtMjA4ODc0NjYxMl19
-->