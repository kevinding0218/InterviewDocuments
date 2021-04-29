### Requirements/Goals
#### Function Requirements
- A user may have many friends and follow a large number of pages/groups
- Newsfeed will be generated based on the posts from the people, pages or groups that a user follows
- Service should support appending new posts as they arrive to the newsfeed for all active user
- News feed would be sorted by timeline
- Post can be liked by other users
- Other functions like search/left comments/tagging user, etc
#### Non-functional requirements
- When we talked about distributed system, which I assume it's something we're looking forward in today's design, we'd basically mean
- High scalability: supports an arbitrarily large number of posts ,
- High availability: survives hardware/network failures, no single point of failure
- High performant: keep end-to-end latency as low as possible
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTY0Mjk1ODc3MiwtMzc4NTE2NjA4XX0=
-->