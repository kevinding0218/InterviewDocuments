### Requirements/Goals
#### Function Requirements
- User Login/Logout
- A user may have many friends and follow a large number of pages/groups
- Newsfeed will be generated based on the posts from the people, pages or groups that a user follows
- Service should support appending new posts as they arrive to the newsfeed for all active user
- News feed would be sorted by timeline
- Post feeds can be liked by other users
- Other functions like search/left comments/like comment/tagging user/share post, etc
#### Non-functional requirements
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
- **High performant**: keep end-to-end **latency as low as possible**
 - **CAP(Consistency, Availability and Partition tolerance)** theorem tells us that we should be choosing between Availability and Consistency, **discuss with interviewer**
	- If we choose **Availability** over Consistency, it simply means we prefer to **show stale (not up-to-date) data than no data at all**.
	- On the other hand of choosing **Consistency**: Synchronous data replicate is slow, we usually replicate data asynchronously, if a user doesn't see a photo for a while, should it be fine or not?
- **Data Durable** is also something we need to think of, which refers to the **system being highly reliable**, any uploaded **feed should never be lost**
- **Cost**(hardware, development, maintenance), but we might ignore that for now
- **Let's revisit those once we finialize more details with our design**
### High level Architecture
```
Client -> Browser -> VIP -> LB -> (API gateway/Router) -> User/Friendship/Feeds/Media Service    -> Database -> Query Service -> Browser -> Client
```
### Detail Analysis of each component in architecture
#### VIP/virtual IP
- refers to the symbolic hostname (for example myWebService.domain.com) that resolves to a load balancer system.
#### LB/Load Balancer
- All requests coming from our clients will go through a load balancer first. This will ensure requests are equally distributed among requests processing servers.
#### API gateway
- An API management tool that sits between a client and a collection of backend services, acts as a reverse proxy to accept all, aggregate the various services required to fulfill them, and return the appropriate result, in a microservices architecture, in which case a single request could require calls to dozens of distinct applications.
####  Service - Storage as well as API/Schema design
##### Storage (SQL vs NoSQL vs File System)
- Relational Database 
- NoSQl
- File System
##### User Service
- **@Post registerUser(RegistrationInfo regInfo)**, the `RegistrationInfo` may include information such as username/email/password/confirmedPassword/contactInfo, etc)
- **@Post loginUser(LoginInfo logInfo)**, the `LoginInfo` may include information such as `username/email/password`, it may return an JWT token for further API request authentication and authorization
- **User info are tend to be structure** so we can just use a SQL Database to save such info in a user table, each user would be assigned with **an unique UserId/UUID**
```
User Table
id			varchar	PK
username	varchar
email		varchar
password	varchar
```
##### Friendship Service
- **@Post createConnection(UUID from_user_id, UUID to_user_id)**, build a mapping relationship between User A and User B, which would return a Http Status of 200 if disconnect succesfully or 422 if unprocessable
- **@Get getConnections(UUID from_user_id)** which would return a list of UUID of user_id that the `from_user_id` is following.
- **@Delete removeConnection(UUID from_user_id, UUID to_user_id)**, which would return a Http Status of 200 if disconnect succesfully or 422 if unprocessable
- F**riendship relationship can be stored in in SQL like as well as NoSQL**, for cosidering data durability and performance
	- when we consider SQL or NoSQL for storing messages, we need to think of **if we need ACID transactions, if we need to run complex dynamic queries, if we would use this storage for analytics or data warehousing.**
```
// Friendship Table
from_user_id	varchar		FK
to_user_id		varchar		FK
update
// NoSQL Schema
user1 {
  _id: "user1",
  name: "john smith",
  email: "blah@test.com,
  friends: [
    "user2",
    "user3",
    "user4",
  ]
}
```
##### Feeds Service
- @Get getFeeds(UUID user_id): return top 100 news feed
```
Feeds Table
id			varchar/UUID	PK
user_id		varchar			FK
content		BLOB(binary large object)
create_at 	time_stamp
feed_type	smallint		FK	(Photo/Article/Video, etc)
```

<!--stackedit_data:
eyJoaXN0b3J5IjpbNTU1NTQ1MzcwLC0yMTI0MzMyNDIwLC0yOD
A5NTM3OTQsMzU0MzczNzQ2LC0xNTAzNjUxNTc2LDE4MDUwMjYz
MjQsOTI1NTcwNDgyLC0yMDQ1OTUxNjc3LC05MDYzMzg1NDAsLT
M3ODUxNjYwOF19
-->