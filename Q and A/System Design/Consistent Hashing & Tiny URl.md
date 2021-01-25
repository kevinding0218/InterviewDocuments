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
- 3 machines: DB1 
#### Horizontal
- 2 machines: DB1 --> [0, 179], DB2 --> [180, 359]
- 3 machines: DB1 --> [0, 119], DB2 --> [240, 359], DB3 --> [120,239]
	- DB1: [0,119] data remain there, [120,179] 60 data migrated to DB3
	- DB2: [240, 359] data remain there, [180, 239] 60 data migrated to DB3
	- total of 120/360 = 1/3 data migrated
- 4 machines: 

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwNTY0Nzk1NDEsLTI5ODU2MzkzMywxNz
g0NzAzMjA2LC05MDk5MTE5MDldfQ==
-->