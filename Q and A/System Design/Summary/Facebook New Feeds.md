### Requirements/Goals
#### Function Requirements
- A user may have many friends and follow a large number of pages/groups
- Newsfeed will be generated based on the posts from the people, pages or groups that a user follows
- Service should support appending new posts as they arrive to the newsfeed for all active user
- News feed would be sorted by timeline
- Post can be liked by other users
- Other functions like search/left comments/tagging user, etc
#### Non-functional requirements
- 
- When we talked about designing it as distributed system, which I assume it's something we're looking forward in today's design
- we'd basically mean
- **High scalability**: supports an arbitrarily large number of posts or able to **handle load increase**
- **High availability**: **survives hardware/network failures**
- **High performant**: keep end-to-end **latency as low as possible**
 - CAP(**Consistency**, **Availability** and **Partition tolerance**) theorem tells me I should be choosing between Availability and Consistency, **discuss with interviewer**
	- If we choose **Availability** over Consistency, it simply means we prefer to **show stale (not up-to-date) data than no data at all**.
	- On the other hand of choosing **Consistency**: Synchronous data replicate is slow, we usually replicate data asynchronously, if a user doesn't see a photo for a while, should it be fine or not?
- **Data Durable** is also something we need to think of, which refers to the **system being highly reliable**, any uploaded **feed should never be lost**
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTI1Njc1ODM3MiwtMzc4NTE2NjA4XX0=
-->