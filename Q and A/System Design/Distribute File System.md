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
			- adv: Simple Design and data is easy to persist
			- dis: master goes down then all down
		- Final Decision of GFS
			- Master + Slave
	
### Storage

### Interview: How to save one file
- What contains in a file
	- Metadata (describe "other data", usually visited more than content)
		- File info(FileName, CreatedTime, Size)
		- Windows: Sequential Storage - disk piece management for removed file
		- Linux: Separate Storage - Reduce size of metadata, waste space for small files
			- Index: Chunk 11 -> diskOffset1, Chunk 12 -> diskOffset2
			- 1 chunk = 64M = 64 * 1024K
- Scale about the Storage
	- One Master + many Chunk Slave Server
	- Key point: master don't record the diskOffset of a chunk
	- Advantage
		- Reduce the size of metadata in master
		- Reduce the traffic between master and ChunkServer (chunk offset change doesn't need to notify master)

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTUwNTIyNjQxNiwtMTkzNTYyMTYxOSwtMj
A3MTUxMzU4MiwyMzgxNTAwMzAsNzgxMzQxMzc4LC0xNDQxODI3
NTk4LDEwNTQ5MDY2MDEsLTE5OTYzMTAyMzUsLTEzMTgxODU1MD
YsLTEwNzQzNDc5MThdfQ==
-->