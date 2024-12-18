### Request-Response API Paradigms (Client <-> Server)
- Fundamentally apis can be broken down into either request response apis or event driven apis (CQRS)
- When it comes to request response style apis there are three commonly used standards here they are **representational state transfer which is more commonly referred to as rest apis** and then you have **remote procedure call or rpc style apis** and finally you have **GraphQL apis**
#### REST API
- REST apis are **all about resources**, typically you would name your resources using nouns and your method using verbs with `CRUD like` Options in HTTP verbs like POST/PUT/PATCH/GET/DELETE/OPTIONS
- REST are **STATELESS**
##### Pros
- **Standard method names, arguments and status codes**
- **Utilized HTTP features really well** by using the http verbs to describe the precise operation that you're trying to perform
- **Easy to maintain**
##### Cons
- **Big payloads**, for example i might be getting the entire user entity but i only probably want to use maybe the name or maybe another particular field.  Unfortunately you're gonna be getting the entire resource back and this is quite unnecessary so it leads to big payloads
- **Multiple HTTP roundtrips**, for example if you want to get a resource and it's sub-resource well you have to make two separate calls first to get the resource and second to get the sub-resource there's no way of combining the results together not in a restful manner anyway
#### RPC API
- RPC apis are **all about actions**. rpc have become quite common among api providers these days and slack is one of them. HTTP  based rpc apis usually support get for read-only requests and posts for the rest
```
https://slack.com/api/chat.postMessage
https://slack.com/api/chat.scheduleMessage
```
##### Pros
- **Easy to understand**:  as the action is usually a part of the url itself they are pretty self-descriptive
- **Lightweight payloads**: because they are tied to actions directly payloads tend to be associated with the action itself and therefore tends to be lightweight
- **High performance**: because they are so action oriented
##### Cons
- **Discovery is difficult**: it is not possible to assume operations available on resources because they are not standardized like rest apis
- **Limited Standardization**: can become tedious to work with referencing documentation becomes super important
- **Leads to function explosion**: over time as features are added to your products the number of endpoints representing
these functions can grow out of control this leads to function explosion overall. 
- Overall RPC apis are good for apis exposing actions rather than crude-like operations
#### GraphQL API
- GraphQL is a query language for apis, they expose a **single endpoint** as an entry point the **client defines the structure of the data that is required** and the **server returns exactly that structure** typically **only post and get** are supported in graphql apis
- for example: a request is something like this this specifies the required structure to be a collection of users which contain the name and the username fields and the server returns exactly that a collection of users which contain the name and the username fields only
##### Pros
- **Save multiple round trips**: the client can define the exact data that is required and this is going to save the number of trips the client is going to need to make to get that requested data a client could request multiple nested levels of data
from a resource in a single call for example getting the orders and the users can be done in a single call in this case as long as you structure the data the way you require it to be
- **Avoid versioning**: in graphql apis you don't necessarily have to do that you can add new fields without breaking the existing queries similarly you can deprecate existing fields
- **Smaller payload size**: client definition results in smaller payload
##### Cons
- **Added Complexity**: the **server needs to handle the complexity of the type of query the client constructs** this can get quite complicated depending on the nature of your data optimizing performance in the backend
- **Optimizing performance is difficult**: when working with external users it becomes difficult to identify their use cases as they can vary quite a bit
- **Too complicated for a simple API**: 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyMDEwMTI2MDEsLTU1NjExNzI4LC02OD
IzOTA0MTYsLTExNTYyMDM5MDcsLTE4NjQ2NTk1OTAsLTE4Mzc2
OTY0OCwzNzk1NDI2MTMsNzQyMDI5OTAwLC0xMTA4MjM5OTA2XX
0=
-->