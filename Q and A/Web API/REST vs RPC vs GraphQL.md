### Request-Response API Paradigms (Client <-> Server)
- Fundamentally apis can be broken down into either request response apis or event driven apis (CQRS)
- When it comes to request response style apis there are three commonly used standards here they are **representational state transfer which is more commonly referred to as rest apis** and then you have **remote procedure call or rpc style apis** and finally you have **GraphQL apis**
#### REST API
- REST apis are **all about resources**, typically you would name your resources using nouns and your method using verbs with `CRUD like` Options in HTTP verbs like POST/PUT/PATCH/GET/DELETE/OPTIONS
##### Pros
- Standard method names, arguments and status codes
- Utilized HTTP features
- Easy to maintain
##### Cons
- Big payloads
- Multiple HTTP roundtrips
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzAwMzMxMjg4LDM3OTU0MjYxMyw3NDIwMj
k5MDAsLTExMDgyMzk5MDZdfQ==
-->