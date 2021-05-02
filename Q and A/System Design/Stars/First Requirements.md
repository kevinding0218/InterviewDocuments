### Functional Requirement
- API Definition
### Non-functional requirements
- Let's do some analysis on the Daily Active User (DAU) or Monthly Active User (MAU)
	- **QPS**: DAU * Request per day per user(request not only about news feed but things like login/like/comment/post, etc) 1M * 60 / 86400 (total seconds) ~ 100k
	- **Peak QPS** ~ 300k
	- High QPS determines that we may need a cluster of 500 ~ 1k web servers to handle that request (considering of business logic and bottleneck of database query)
- Let's also do some estimation on storage
	- How many new photo will be uploaded every day or every second, what's going to be the average photo file size
	- Total space required for 1 day of photo/1 year/5 years?
	- For this news feed section, we can think of this would be a heave read and light write QPS
- When we talked about designing it as distributed system, which I assume it's something we're looking forward in today's design, we need to consider of
- **High scalability**: supports an arbitrarily large number of posts or able to **handle load increase**
- **High availability**: **survives hardware/network failures**
- **High performant**: keep end-to-end **latency as low as possible**, GEO might be a considration
 - **CAP(Consistency, Availability and Partition tolerance)** theorem tells us that we should be choosing between Availability and Consistency, **discuss with interviewer**
	- If we choose **Availability** over Consistency, it simply means we prefer to **show stale (not up-to-date) data than no data at all**.
	- On the other hand of choosing **Consistency**: Synchronous data replicate is slow, we usually replicate data asynchronously, if a user doesn't see a photo for a while, should it be fine or not?
- **Data Durable** is also something we need to think of, which refers to the **system being highly reliable**, any uploaded **feed should never be lost**
- **Cost**(hardware, development, maintenance), but we might ignore that for now
- **Let's revisit those once we finialize more details with our design**
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM4MzQ3NTg0XX0=
-->