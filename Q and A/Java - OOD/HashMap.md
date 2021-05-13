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
### What is Hashing?
- **Hashing** is the practice of using **an algorithm (or hash function)** to map data of _any_ size to a _fixed_ length. 
- keys are converted into _hash values_ or _indexes_ by using **hash functions**. Hashing is a one-way function.
### Hash Collision
- Collisions happen when 2 distinct keys generate the same hashCode() value.
- There are many collision-resolution strategies – chaining, double-hashing, clustering. However, java has chosen chaining strategy for hashMap, so in case of collisions, items are chained together just like in a linkedList.
### Load Factor
- The Load factor is a measure that decides when to **increase** the HashMap capacity to maintain the get() and put() operation complexity of **O(1)**. The default load factor of HashMap is **0.75f** (75% of the map size).
### How Load Factor is calculated
- **Load Factor decides "when to increase the number of buckets."**
We can find when to increase the hashmap size by using the following formula:
1.  initial capacity of the hashmap*Load factor of the hashmap.
**The initial capacity of hashmap is=16**  
**The default load factor of hashmap=0.75**  
**According to the formula as mentioned above: 16*0.75=12**
It represents that 12th  key-value pair of hashmap will keep its size to 16. As soon as 13th  element (key-value pair) will come into the Hashmap, it will increase its size from default  **2^4  = 16**  buckets to  **2^5  = 32**  buckets.
### HashTable vs HashMap vs HashSet
- HashTable for multi thread, that it's safe to visit HashTable across multi thread
- HashMap for single thread
- HashSet no value
### HashMap vs LinkedHashMap vs TreeMap
1.  **HashMap**  :-  `HashMap`  never preserves your Insertion Order. It Internally Use a hashing Concept by which it generate a  `HashCode`  to the Corresponding  `key`  and add it to the  `HashMap`.
2.  **LinkedHashMap**  :-  `LinkedHashMap`  It preserves your Insertion Order. and  `keys`  will be found as same order you Insert into this  `LinkedHashMap`.
3.  **TreeMap**  :- The  `TreeMap`  class implements the  `Map`  **interface**  by using a  **Tree**. A  `TreeMap`  provides an efficient means of storing  **key/value**  pairs in sorted order, and allows rapid retrieval.
	- unlike a `HashMap`, a tree map guaran



<!--stackedit_data:
eyJoaXN0b3J5IjpbMTExNjc1NjY4OCwtMTI1OTEyMDAxNiwxNj
AyMzg0NDUsODk0OTAyNzc5XX0=
-->