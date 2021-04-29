### Requirements/Goals
#### Function Requirements
- User Login/Logout
- A user may have many friends and follow a large number of pages/groups
- Newsfeed will be generated based on the posts from the people, pages or groups that a user follows
- Service should support appending new posts as they arrive to the newsfeed for all active user
- News feed would be sorted by timeline
- Post can be liked by other users
- Other functions like search/left comments/tagging user/share post, etc
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
 - CAP(**Consistency**, **Availability** and **Partition tolerance**) theorem tells me I should be choosing between Availability and Consistency, **discuss with interviewer**
	- If we choose **Availability** over Consistency, it simply means we prefer to **show stale (not up-to-date) data than no data at all**.
	- On the other hand of choosing **Consistency**: Synchronous data replicate is slow, we usually replicate data asynchronously, if a user doesn't see a photo for a while, should it be fine or not?
- **Data Durable** is also something we need to think of, which refers to the **system being highly reliable**, any uploaded **feed should never be lost**
- **Cost**(hardware, development, maintenance), but we might ignore that for now
- **Let's revisit those once we finialize more details with our design**
### High level Architecture
```
Client -> Browser -> VIP -> LB -> (API gateway/Router) -> User/Feed/Media/Friendship Service    -> Database -> Query Service -> Browser -> Client
```
### Detail Analysis of each component in architecture
- **VIP/virtual IP**. refers to the symbolic hostname (for example myWebService.domain.com) that resolves to a load balancer system.
- **LB/Load Balancer** : All requests coming from our clients will go through a load balancer first. This will ensure requests are equally distributed among requests processing servers.
- **API gateway** is an API management tool that sits between a client and a collection of backend services, acts as a reverse proxy to accept all, aggregate the various services required to fulfill them, and return the appropriate result, in a microservices architecture, in which case a single request could require calls to dozens of distinct applications.
- When comes to the **Service**, let's talk about the **Storage** as well as **API design**
- User Service
	- registerUser(RegistrationInfo regInfo), the `RegistrationInfo` may include information such as username/email/password/confirmedPassword/contactInfo, etc)
	- loginUser(LoginInfo logInfo), the `LoginInfo` may include information such as `username/email/password`, it may return an JWT token for further API request authentication and authorization
	- User info are tend to be structure so we can just use a SQL Database to save such info in a user table, each user would be assigned with an unique UserId
```
User Table
id			integer
username	varchar
email		varchar
password	varchar
```
- Friendship Service
	- follow(

#### Storage (SQL vs NoSQL vs File System)
- Relational Database 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzU0MzczNzQ2LC0xNTAzNjUxNTc2LDE4MD
UwMjYzMjQsOTI1NTcwNDgyLC0yMDQ1OTUxNjc3LC05MDYzMzg1
NDAsLTM3ODUxNjYwOF19
-->