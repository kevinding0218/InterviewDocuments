### Requirements/Goals
#### Function Requirements
- while user are typing in the search bar, we should have service with response of suggested terms matching with whatever user typed
- Discuss: are we only returning the result or maybe Hot words like top N (10 or 20) 
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
User typing -> Browser -> LB -> Router - Query Service - DataCollection Service - (MapReduce) - Metadata Database/Logs
```
### Detail Analysis of each component in architecture
#### LB/Load Balancer
- All requests coming from our clients will go through a load balancer first. This will **ensure requests are equally distributed among processing servers**.
#### API gateway/Router
- An API management tool that sits between a client and a collection of backend services, acts as a reverse proxy to accept all, aggregate the various services required to fulfill them, and return the appropriate result, in a microservices architecture, in which case a single request could require calls to dozens of distinct applications.
#### Storage
-   What kind of data do we need to store?
    -   The naive way
    -   keyword (e.g: “amazon”, “apple”, “adidas”)
    -   hit_count (e.g: 20b, 15b 7b)
| keyword | hitcount |
|--|--|
| "amazon" | 20b |
| "apple" | 15b |
| "adidas" | 7b |
```
SELECT * FROM hit_stats
WHERE keyword LIKE '${key}%'
ORDER BY hit_count DESC
LIMIT 10
```
- Pros
	- If search range is fixed or small
	- fast to implement
- Cons
	-   Like Operation is expensive
		- If your filter criteria uses equals = and the field is indexed, then most likely it will use an INDEX/CLUSTERED INDEX SEEK
		- If your filter criteria uses LIKE, with no wildcards, it is about as likely as #1 to use the index. The increased cost is almost nothing
		- If your filter criteria uses LIKE, but with a wildcard it's much less likely to use the index
		- the SQL engine still might not use an index the way you're expecting, depending on what else is going on in your query and what tables you're joining to


cache-control
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM4MDY0ODI3OSwxNzI0NTI2MjEwLDEwMj
k5NzQyNTEsNzMwOTk4MTE2XX0=
-->