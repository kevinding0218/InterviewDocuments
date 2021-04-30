#### FrontEnd web service
- FrontEnd is a lightweight web service, consisting of stateless machines located across several data centers.
- FrontEnd service is responsible for: **request validation, authentication and authorization, SSL termination, server-side data encryption, caching, rate limiting (also known as throttling), request dispatching, request deduplication, usage data collection.**
##### Request validation
- Request validation helps to ensure that all the required parameters are present in the request and values of these parameters honor constraints.
- For example, in our case we want to make sure queue name comes with every send message request. And message size does not exceed a specified threshold.
##### Authentication
- During authentication check we verify that message sender is a registered customer of our distributed queue service.
And during authorization check we verify that sender is allowed to publish messages to the queue it claims.
##### TLS
- TLS is a protocol that aims to provide privacy and data integrity. TLS termination refers to the process of decrypting request and passing on an unencrypted request to the backend service. And we want to do TLS termination on FrontEnd hosts because TLS on the load balancer is expensive. Termination is usually handled by not a FrontEnd service itself, but a separate HTTP proxy that runs as a process on the same host.
##### Server-side Encryption
- Because we want to store messages securely on backend hosts, messages are encrypted as soon as FrontEnd receives them. **Messages are stored in encrypted form and FrontEnd decrypts them only when they are sent back to a consumer**.
##### Cache
- Cache stores copies of source data. In FrontEnd cache we will store metadata information about the most actively used queues. As well as user identity information to save on calls to authentication and authorization services.
##### Rate Limiting/Throttling
- Rate limiting or throttling is the process of limiting the number of requests you can submit to a given operation in a given amount of time. 
- Throttling protects the web service from being overwhelmed with requests. Leaky bucket algorithm is one of the most famous.
##### Request Dispatching
- Responsible for all the activieties associated with sending requests to backend services (clients management, response handling, resources isolation, etc), Frontend service makes remote calls to at least two other web services: Metadata service and Backend service, 
- FrontEnd service creates HTTP clients for both services and makes sure that calls to these services are properly isolated. It means that when one service, let's say Metadata service experiences a slowdown, requests to Backend service are not impacted.
- Bulkhead pattern helps to isloate elements of an application into pools so that if one fails, the others will continue to function
- Circuit Breaker pattern prevents an application from repeatedly trying to execute an operation that's likely to fail
##### Deduplication
- It may occur when a response from a successful send message request failed to reach a client. 
- Lesser an issue for ‘at least once’ delivery semantics, a bigger issue for ‘exactly once’ and ‘at most once’ delivery semantics, when we need to guarantee that message was never processed more than one time.
- Caching is usually used to store previously seen request ids to avoid deduplication.
##### Data Collection 
- When we gather real-time information that can be used for audit, gather real-time information that can be used for audit and billing (invoices)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTA2NDA1NDYzNV19
-->