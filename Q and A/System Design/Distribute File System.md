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
		- Client vs Server
			- User vs Browser
			- Browser vs Webserver
			- Webserver vs Database
			- Database vs GFS
			- Webserver vs GFS
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
		- Reduce the size of metadata in master, one file could be saved across different chunk
		- Reduce the traffic between master and ChunkServer (chunk offset change doesn't need to notify master)
### How to write a file
#### One write vs multiple write
- What if something goes wrong during write process
	- one write needs to write entire file again 
	- multiple writes only need to transfer a small piece
- Size of multiple write
	- File is stored by Chunk, so chunk (64M) is the transfer unit
- Split by master or client
	- GFS Client will cut down based on file size
	- e.g: gfd/home/file1.mp4 size = 576M, so cut down to 576M/64M = 9 chunks
	- every chunk has a chunk index : file1.map4-01-of-09, file1.map4-02-of-09, ...
- How each chunk write to server?
	- master will distribute chunkserver for each chunk to client
	1. GFS client tell master that I am going to write file_name = /gfs/home/file1.mp4, Chunk index = 1
	2. master response to client to "assign chunkserver_locations = US, CS1"
	3. GFS client trasnfer data = /gfs/home/file1.mp4-01-of-09 to ChunkServer 1
	4. ChunkServer 1 notify master transfer is done
#### How to modify a file
- One time to write, many times to read
- first delete /gfs/home/file1.mp4, then re-write the file

### How to read a file
#### read by chunks
1. Client tells master to read 'Filename=/gfs/home/file1.mp4'
2. Master returns a chunk list with chunk indexes and chunk servers
3. Client read '/gfs/home/file1.mp4-00-of-09' in Chunk Server 1
4. Chunk Server 1 return data of '/gfs/home/file1.mp4-00-of-09'

### Master Task
- Store metadata of each file
- Store a 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTU3OTU4ODgwOCwtOTY0MzQ4MDM4LDYxMj
Q2Mjk4OCwtMjEwODA0OTgzLDEwNDA3MTQxNjYsLTE5MzU2MjE2
MTksLTIwNzE1MTM1ODIsMjM4MTUwMDMwLDc4MTM0MTM3OCwtMT
Q0MTgyNzU5OCwxMDU0OTA2NjAxLC0xOTk2MzEwMjM1LC0xMzE4
MTg1NTA2LC0xMDc0MzQ3OTE4XX0=
-->