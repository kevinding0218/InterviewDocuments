### Requirements/Goals
#### Function Requirements
- while user are typing in the search bar, we should have service with response of suggested top N (10 or 20) terms matching with whatever user typed
#### Non-functional requirements
- QPS discussion or data entry size billion level?
	- DAU: 500m
	- Search: 4 * 6 * 500m = 12b (every user searches 6 times, types 4 letters)
	- QPS: 12b/86400 ~ 138k
	- Peak QPS: QPS * 2 = 276k
- Obviously we'd be looking forward a large scale data collection with query
- When we talked about designing it as distributed system, which I assume it's something we're looking forward in today's design, we need to consider of
- **High scalability**: supports an arbitrarily large number of data collection as well as query to **handle load increase**
- **High availability**: **survives hardware/network failures**
- **High performant**: keep end-to-end **latency as low as possible** GEO might be a considration
-  **CAP(Consistency, Availability and Partition tolerance)** theorem tells us that we should be choosing between Availability and Consistency, **discuss with interviewer**
	- If we choose **Availability** over Consistency, it simply means we prefer to **show stale (not up-to-date) data than no data at all**.
- **Data Durable** is also something we need to think of, which refers to the **system being highly reliable**, any uploaded **feed should never be lost**
- **Cost**(hardware, development, maintenance), but we might ignore that for now
- **Let's revisit those once we finialize more details with our design**
### High level Architecture
#### What services do we need to have?
- Query Service
- DataCollection Service
```
User typing -> Browser -> LB -> Router - Query Service - DataCollection Service - Metadata Database/Logs
```



cache-control
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTgzNTg5NzY5LDEwMjk5NzQyNTEsNzMwOT
k4MTE2XX0=
-->