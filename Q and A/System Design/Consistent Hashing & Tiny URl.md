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
	- data distribution not shared equaly
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTMxMTI3MDc4Nyw3OTQxMjUyNjMsLTI5OD
U2MzkzMywxNzg0NzAzMjA2LC05MDk5MTE5MDldfQ==
-->