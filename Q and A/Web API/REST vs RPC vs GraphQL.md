### Request-Response API Paradigms (Client <-> Server)
- Fundamentally apis can be broken down into either request response apis or event driven apis (CQRS)
- When it comes to request response style apis there are three commonly used standards here they are **representational state transfer which is more commonly referred to as rest apis** and then you have **remote procedure call or rpc style apis** and finally you have **GraphQL apis**
#### REST API
- REST apis are **all about resources**, typically you would name your resources using nouns and your method using verbs with `CRUD like` Options in HTTP verbs like POST/PUT/PATCH/GET/DELETE/OPTIONS
##### Pros
- Standard method names, arguments and status codes
- Utilized HTTP features really well by using the http verbs to describe the precise operation that you're trying to perform
- Easy to maintain
##### Cons
- Big payloads, for example i might be getting the entire user entity but i only probably want to use maybe the name or maybe another particular field.  Unfortunately you're gonna be getting the entire resource back and this is quite unnecessary so it leads to big payloads
- Multiple HTTP roundtrips, for example if you want to get a resource and it's sub-resource well you have to make two separate calls first to get the resource and second to get the sub-resource there's no way of combining the results together not in a restful manner anyway
#### RPC API
- RPC apis are **all about actions**. rpc have become quite common among api providers these days and slack is one of them. HTTP  based rpc apis usually support get for read-only requests and posts for the rest
```
https://slack.com/api/chat.postMessage
https://slack.com/api/chat.scheduleMessage
```

<!--stackedit_data:
eyJoaXN0b3J5IjpbOTU2MDk2NDkzLC0xODM3Njk2NDgsMzc5NT
QyNjEzLDc0MjAyOTkwMCwtMTEwODIzOTkwNl19
-->