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
- we would focus on this design

### Scenario
#### Feature
- MVP (minimum value product), what minimum featured need can make out this product
- First Stage
	- Driver report locations
	- Rider request Uber, match a driver wi
#### QPS/Storage

### Service
#### Service Oriented Architectore

### Storage
#### Schema

### Scale
#### Robust
#### Feature
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTY3MzMwOTQzMyw0MjE2MjAxOTMsLTIwNT
Y5MDAxODFdfQ==
-->