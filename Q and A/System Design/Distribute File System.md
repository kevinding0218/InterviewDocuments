## Distribute File System
### What is distributed system?
- use multiply machine to solve issues that cannot be solved on one machine
	- e.g: not enough storage, large QPS, etc
- Distribute File System (Google File System)
	- How to store data effectively
	- NoSQL needs a file system at foundation level
- Map Reduce
	- How to process data faster
- Bigtable = NoSQL
	- How to connect data at foundation or upper level
- Master Slave Pattern
- How to check and handle system failure and error
- How to design Distributed File System
	- how to deal with failure and recovery
|Distribute File System| Company | Open Source |
|--|--|
| GFS | Google | No
| HDFS | Yahoo(Alibaba) | Yes
	- GFS (C++)
	- HDFS (Java) - Hadoop Distributed File System

### Scenario
- What requirement needs to design
	- Write a file
	- Read a file
	- What size does it support? > 1000T
	- How many machines to store these files? 100k (2007)

### Service
- Client + Server
	- What is client?
		- Webserver & Database
	- How to communicate across multiply machine?
		- Peer 2 Peer (BitComet, Cassandra)
			- Client -> Server 1 <-> Server 2 <...> Server N
			- adv: One server goes down but can still work
			- dis: multiple machines needs keep communication to persist their data
		- Master Slave
			- Client -> Master (Server 1, Server 2, ... Server N)
			- adv: Simple Design
			- dis: master goes down then all down
		- Final Des
		- 


<!--stackedit_data:
eyJoaXN0b3J5IjpbOTIzOTMyNzk5LC0xNDQxODI3NTk4LDEwNT
Q5MDY2MDEsLTE5OTYzMTAyMzUsLTEzMTgxODU1MDYsLTEwNzQz
NDc5MThdfQ==
-->