### HashTable vs HashMap
- Java has two hash table classes:  HashTable  and  HashMap. In general, you should use a  HashMap.
- Difference:
	-  A  HashTable  doesn't allow null keys or values; a  HashMap  does.
	-  A  HashTable  is synchronized to prevent multiple threads from accessing it at once; a  HashMap  isn't.
### HashMaps are built on arrays
- All we need is a function to convert a key into an array index (an integer). That function is called a **Hash Function**
### Hash Function
- A **hash function** is any function that can be used to map data of arbitrary size to fixed-size values. 
- The values returned by a hash function are called _hash values_, hash codes and are used to index a fixed-size table called a hash table.
### Hash Collision
- There’s always a chance that two different inputs for hash function will generate the same **hash value**. This is known as a **hash collision**.
- **Prior to Java 8**, HashMap and all other hash table based Map implementation classes in Java handle collision by _chaining_, i.e. they **use linked list to store map entries** which ended in the same bucket due to a collision. instead of storing the actual values in our array, let's have each array slot hold a _pointer_ to a linked list holding the values for all the keys that hash to that index. 
	- If a key end up in the same bucket location where entry is already stored then this entry is just added at the head of the linked list there.  
  
Read more:  [https://javarevisited.blogspot.com/2016/01/how-does-java-hashmap-or-linkedhahsmap-handles.html#ixzz6tw877ldJ](https://javarevisited.blogspot.com/2016/01/how-does-java-hashmap-or-linkedhahsmap-handles.html#ixzz6tw877ldJ)
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTU0MTcyNDYyMSwtMTU2NTMwOTI3MCwxMD
k0OTI1MzQ1XX0=
-->