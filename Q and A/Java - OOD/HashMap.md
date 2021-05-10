### What is HashMap
1.  HashMap implements Map interface and maintains key and value pairs.
2.  HashMap internally works on the principle of Hashing
3.  HashMap can only contain unique keys and only one null key.
4.  HashMap methods are non-synchornized.
5.  HashMap lookups are O(1) in the average case, and  O(n) in the worst case
### How HashMap works internally ?
HashMap works on the principle of Hashing.
1.  Hash Function - the `hashCode()` method which returns an integer value is the Hash function.
2.  Hash Value - return an int value by the hash function
3.  Bucket - used to store key value pairs. A bucket can have multiple key-value pairs, In hash map, bucket used is simply a linked list or RedBlack Tree to store objects
4. HashMap get(Key k) method calls hashCode method on the key object and applies returned hashValue to its own static hash function to find a bucket location(backing array)
### What if when two different keys have the same HashCode?
- In this situation equals() method comes to rescue.
- Since bucket is one and we have two objects with the same hashcode. The bucket is the linked list effectively. So we traverse through linked list , comparing keys in each entries using keys.equals() until it return true. Then the corresponding entry object Value is returned .
### Hash Collision
- Collisions happen when 2 distinct keys generate the same hashCode() value.
- There are many collision-resolution strategies – chaining, double-hashing, clustering. However, java has chosen chaining strategy for hashMap, so in case of collisions, items are chained together just like in a linkedList.
### What is ConcurrentHashMap
- A concurrentHashMap is divided into number of segments [default 16] on initialization.
- ConcurrentHashMap allows similar number (16) of threads to access these segments concurrently so that each thread work on a specific segment during high concurrency.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwMDIxMTM2MDYsODk0OTAyNzc5XX0=
-->