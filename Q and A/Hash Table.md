### HashTable vs HashMap
- Java has two hash table classes:  HashTable  and  HashMap. In general, you should use a  HashMap.
- Difference:
	-  A  HashTable  doesn't allow null keys or values; a  HashMap  does.
	-  A  HashTable  is synchronized to prevent multiple threads from accessing it at once; a  HashMap  isn't.
### 
### Hash Function
- A **hash function** is any function that can be used to map data of arbitrary size to fixed-size values. 
- The values returned by a hash function are called _hash values_, hash codes and are used to index a fixed-size table called a hash table.
### Hash Collision
- There’s always a chance that two different inputs for hash function will generate the same **hash value**. This is known as a **hash collision**.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwODg1MTk0NjQsMTA5NDkyNTM0NV19
-->