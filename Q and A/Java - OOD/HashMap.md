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
<!--stackedit_data:
eyJoaXN0b3J5IjpbODk0OTAyNzc5XX0=
-->