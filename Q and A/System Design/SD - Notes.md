### Requirement Definition
#### Functional Requirement
- API Definition
#### Non Functional Requirement
- **High scalability** will help to ensure our XX can handle increased number of put and get requests. And be able to handle increasing amount of data we may need to apply with XX.
- **High availability** will help to ensure that data in the XX is not lost during hardware failures and cache is accessible in case of network partitions. This will minimize number of XX misses and as a result number of calls to the datastore.
- **High performance** will help to ensure data is retrieving fast on every request
- High Durability: if data persistence is important
### Design/Implementation
 - start approaching any design problem with some small and simple steps. evolve your solution with every next step.
 - The set of components we just discussed: **VIP + Load Balancer + FrontEnd web service + Metadata web service that represents a caching layer on top of a database** is so popular in the world of distributed systems, that you may consider it a standard and apply to many system designs.
 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTQ3NzQzMTE0MV19
-->