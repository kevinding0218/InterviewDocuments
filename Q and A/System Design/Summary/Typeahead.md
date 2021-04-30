### Requirements/Goals
#### Function Requirements
- while user are typing in the search bar, we should have service with response of suggested top N (10 or 20) terms matching with whatever user typed
#### Non-functional requirements
- QPS discussion
	- DAU: 500m
	- Search: 4 * 6 * 500m = 12b (every user searches 6 times, types 4 letters)
	- QPS: 12b/86400 ~ 138k
	- Peak QPS: QPS * 2 = 276k
- Obviously we'd be looking forward a large scale data collection with query
- When we talked about designing it as distributed system, which I assume it's something we're looking forward in today's design, we need to consider of
- **High scalability**: supports an arbitrarily large number of posts or able to **handle load increase**
- **High availability**: **survives hardware/network failures**
- **High performant**: keep end-to-end **latency as low as possible**
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzY1ODcyMDk3LDczMDk5ODExNl19
-->