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
#### Horizontal
- 2 machines: DB1 --> [0, 179], DB2 --> [180, 359]
- 3 machines: DB1 --> [0, 119], DB2 --> , DB3 --> [120,239]
	- DB1: [0,119] data remain there, [120,179] data migrated

<!--stackedit_data:
eyJoaXN0b3J5IjpbMzY5OTAwNDIyLC0yOTg1NjM5MzMsMTc4ND
cwMzIwNiwtOTA5OTExOTA5XX0=
-->