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
- **High performant**: keep end-to-end **latency as low as possible**, GEO might be a considration
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjEyNDgyMDk2NCwtMjA4ODc0NjYxMl19
-->