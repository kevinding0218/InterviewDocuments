## Consistent Hashing & Design Tiny Url
- Consistent Hashing
- Replica
	- How SQL does replica
	- How NoSQL does replica
- Design a tiny url
	- 4S
	- No Hire/Weak Hire/Hire/Strong Hire
- What happened when visit www.google.com

### Consistent Hashing
#### What is consistent hashing
- when base mod number changes, data after mod changed a lot
- server pressure when data migration during hashing
- data might change during migration, consistency is not guranteed
- 3 machines: DB1 --> [0,3,6,9], DB2 --> [1,4,7,10], DB3 --> [2,5,8,11]
- 4 machines: DB1 --> [0,4,8], DB2 --> [1,5,9], DB3 --> [2,6,10], DB4 --> [3,7,11]
	- DB1: only 0 remain there, [3,6,9] 3 data migrated somewhere else
	- DB2: only 1 remain there, [4,7,10] 3 data migrated somewhere else
	- DB3: only 2 remain there, [6,10] 2 data migrated somewhere else
	- total of 9/12 = 75% data migrated
#### Horizontal
##### consider as a circle and data can be distributed into 360 pieces/degress in the circle
- 2 machines: DB1 --> [0, 179], DB2 --> [180, 359]
- 3 machines: DB1 --> [0, 119], DB2 --> [240, 359], DB3 --> [120,239]
	- DB1: [0,119] data remain there, [120,179] 60 data migrated to DB3
	- DB2: [240, 359] data remain there, [180, 239] 60 data migrated to DB3
	- total of 120/360 = 33% data migrated
- 4 machines: DB1 --> [0, 79], DB2 --> [240, 359], DB3 --> [160, 239], DB4 --> [80, 159]
- find max closet two section and divided into 3 sections, move part of data from both into new section
- other section might remain same
	- DB1: [0,79] data remain there, [80, 110] 40 data migrated to DB4
	- DB2: [240, 359] all data remain there
	- DB3: [160, 239] data remain there, [120, 159] 40 data migrated to DB4
	- total of 80/360 = 22% data migrated
- disadvantage: 
	- data distribution not shared equaly, e.g DB2 remained same for 120 data, while other 3 DB shared 60 each
	- pressure on those 2 servers might got heavy during migration, e.g: suppose we have 100 server, only those 2 servers got higher pressure, while the other 98 server didn't shared the pressure
##### more practical way
- still consider data storage as a circle
- instead of thinking 360 pieces, consider as 2^64 - 1 pieces,  all big data will be allowed
- each machine and data can be considered as a point in the circle by their hashing value
- import the concept of micro shards/virtual nodes
	- one physical machine refers 1000 micro shards/virtual nodes
- each virtual node refers to a point in the circle
- each time when adding a new machine, placing 1000 virtual nodes randomly in the circle
- when adding a new data
	- calculate the hashing key --> get the number of [0, 2^64]
	- clockwise find the first virtual nodes
	- the physical machine which represents the virtual node will be the final data storage
	- use TreeMap/RBT --> O(logN) finds minimum value that's greater than X
- when adding a new machine
	- 1000 virtual nodes clockwise asking data of its next virtual node
- leetcode consistent-hashing

### Replica
#### difference between backup and replica
- backup: schedule job
	- normaly schedule periodically
	- when data lost, usually can only recover at certain time stamp
	- not used as online data service, not for distribute data reading
- replicate: real-time executing
	- store multiply copies
	- when data lost, can restore through other copy
	- used as online data service, used to distribute data reading
- Why still needs backup
	- money cost
#### SQL database Replica
- usually self-contained Master -Slave replica
	- Master = write, Slave = read
	- Slave sync data from master
- write ahead log
	- all operations in SQL database will have a record in the form of log, e.g:
	- data A updated from C to D at time B
	- when Slave get activated, will inform Master it's ready
	- everytime when operation happened in Master will inform Slave to read log
	- so data in Slave will have a "delay"
- what if master is down?
	- promote one Slave to be Master, which accept read + write
	- might cause data lost and inconsistency
#### NoSQL database Replica
- usually store data cloc
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTA4MzQ1NjU3Miw3MzY3NDIyOCwtMTMxNT
gwMDA5NSwtMTU0NzI2ODIyMiw3OTQxMjUyNjMsLTI5ODU2Mzkz
MywxNzg0NzAzMjA2LC05MDk5MTE5MDldfQ==
-->