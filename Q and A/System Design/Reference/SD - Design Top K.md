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
- let's assume the whole data set can be loaded into a memory of that single host.For example, we have a list of events (video views).
Every time user opens a video, we log such event by adding video identifier to the list
of events.
A, B, C, D represent unique video identifiers.
Given a list of events, how do we calculate the k most frequent elements?
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTkyODgzNjQ5MywtMjA4ODc0NjYxMl19
-->