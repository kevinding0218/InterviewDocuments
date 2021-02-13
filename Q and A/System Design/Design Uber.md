## Design Uber
- Design Facebook Nearby
- Design Yelp
- Design Pokemon Go

### Uber related Tech
#### Ring-Pop
- Similar as Consistent Hashing, services are in a circle, Load Balancer will randomly assign a service machine to accep the request, then the initial service will check the data and decide which would be the next exact service machine.
- The exact service machine can have the webserver and DB in same machine, so it will be faster
- Unlike Master-Slave mode, while DB and server might not be in one region, DB request connection could be delayed

#### TChannel
- a high-efficient RPC

#### Google S2
- an algorithm for geo location query and storage

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTk5ODY4OTU3MCwtMjA1NjkwMDE4MV19
-->