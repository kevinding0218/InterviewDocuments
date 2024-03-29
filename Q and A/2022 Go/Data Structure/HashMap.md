### How is HashMap implemented
- HashMap internally is implemented by **Array** and **LinkedList**, where each element of the Array node is Singe  Direction LinkedList, which represents the `Map.Entry` Interface
- When there is hash collision makes the node LinkedList length greater than **8**, JDK replaced LinkedList with **Red-Black Tree**
#### What is Red-Black Tree
1. A self-balancing Binary Search Tree 
2. Every node has a color either red or black.
3. The root of the tree is always black.
4. There are no two adjacent red nodes (A red node cannot have a red parent or red child).
5. Every path from a node (including root) to any of its descendants NULL nodes has the same number of black nodes.
6. All leaf nodes are black nodes.
#### Why Red-Black Tree
- Most of the BST operations (e.g., Search, Max, Min, Insert, Delete.. etc) take O(h) time where h is the height of the BST. 
	- The cost of these operations may become O(n) for a skewed Binary tree. 
- If we make sure that the height of the tree remains O(log n) after every insertion and deletion, then we can guarantee an upper bound of O(log n) for all these Search, Insert and Delete operations. 
	- The height of a Red-Black tree is always O(log n) where n is the number of nodes in the tree.
### How does HashMap PUT work
1. Call the `hashCode()` method to calculate **hash code of Key** as from K/V set, then calculate the **array index/bucket** based on **hashing value and length of the array**
2. **Adjust length of the array** (if element count of container is greater than **capacity * loadfactor**, would resize it to be **2*n**)
3. Insert K/V into HashMap
	- If hash code of Key doesn't existed in current HashMap
	- otherwise, the `equals()` method comes to rescue, since bucket is either LinkedList or Red-Black Tree, we **traverse through the bucket, comparing keys in each entries using keys.equals().**
		- If we found there is a entry of in the bucket where `equals()` is true, **replace that node with new Value** as from K/V set
		- Otherwise, **insert at the end of the bucket** (tail insertion as in LinkedList or tree insertion as in Red-Black Tree)
			- Before JDK 1.7 LinkedList was using Head Insertion, on or after JDK 1.8 using Tail Insertion
			- When hash collision results in LinkedList length > 8, turn the LinkedList into Red-Black Tree
** hashCode() is used for locating bucket of storage while equals() is used for locating actual node inside the bucket**
### How does HashMap GET work
1. Call the `hashCode()` method to calculate hash code of Key, then calculate the array index/bucket based on hashing value and length of the array
2. Traverse the node of either LinkedList or Red-Black Tree, and use `equals()` method to find entry
** hashCode() is used for locating bucket of storage while equals() is used for locating actual node inside bucket**
### Hash Collision
- Collisions happen when 2 distinct keys generate the same `hashCode()` value.
- There are many collision-resolution strategies – chaining, double-hashing, clustering. However, java has chosen chaining strategy for hashMap, so in case of collisions, items are chained together just like in a linkedList.
### How Load Factor is calculated
- The Load factor is a measure that decides when to **increase** the HashMap capacity to maintain the get() and put() operation complexity of **O(1)**.
	- The **initial capacity** of hashmap is **16**
	- The **default load factor** of hashmap is **0.75**
	- e.g: According to the formula as mentioned above: 16*0.75=12, It represents that 12th  key-value pair of hashmap will keep its size to 16. As soon as 13th element (key-value pair) will come into the Hashmap, it will increase its size from default  **16**  buckets to  **2*16  = 32**  buckets.
### How does HashMap RESIZE/TRANSFER work
- Create a new array where its capacity is double as existed one then re-calculate storage index of node in old array.
- New position of node entry can only be either original index or orginal index + length of array (e.g: if length is 5 orginally and index is 3, after resizing, index would either still be 3 or be 3 + 5 = 8)
### HashMap vs HashTable
#### Thread Safe
- HashMap is not thread safe while HashTable is thread safe
	- In order to make HashTable thread safe, it uses `synchronize` keyword to lock for entire linkedlist in bucket node while it may create block when multiple threads trying to acess same buckcet, therefore the performance of HashTable is worse than HashMap
#### Null Key
- HashMap allows at most one Key as null, also allows value to be null while HashTable doesn't allow
#### Initial Capacity and Resize
- The default capacity is 16 of a HashMap while only 11 of a HashTable
- When resize happen, HashMap would increase to 2 times while HashTable increases as 2 times + 1
### HashMap vs LinkedHashMap vs TreeMap
1.  **HashMap**  :-  `HashMap`  never preserves your Insertion Order. It Internally Use a hashing Concept by which it generate a  `HashCode`  to the Corresponding  `key`  and add it to the  `HashMap`.
2.  **LinkedHashMap**  :-  `LinkedHashMap`  It preserves your Insertion Order. and  `keys`  will be found as same order you Insert into this  `LinkedHashMap`.
3.  **TreeMap**  :- The  `TreeMap`  class implements the  `Map`  **interface**  by using a  **Tree**. A  `TreeMap`  provides an efficient means of storing  **key/value**  pairs in sorted order, and allows rapid retrieval.
	- unlike a `HashMap`, a tree map guaran
### HashMap vs ConcurrentHashMap
#### Thread Safe
- HashMap is not thread safe while ConcurrentHashMap is thread safe
	- ConcurrentHashMap allows concurrent threads to read the value without locking at all.
	- In JDK 1.7 uses ReentrantLock + Segment + HashEntry, it's like turn one HashMap into multiple segments, and **each segmenet may contain several Map.Entry, the scope of Lock is on segment to allow multiple threads to visit**
	- In JDK 1.8 uses CAS + synchronized + Node + Red-Black Tree, **it decreases the scope of lock from segment which contains multiple bucket node into each single node**
		- Scope of Lock decreases to avoid unnecessary thread block
		- still uses `synchronized` that based on JVM which has more space for performant
		- Replace of using `ReentrantLock` based on API that would cause more JVM memory regarding large volume of data operation
	- **Default max thread count** to update ConcurrentHashMap is **16** in order to **avoid generating Race Lock**
#### Null Key
- HashMap allows at most one Key as null, also allows value to be null while ConcurrentHashMap doesn't allow



<!--stackedit_data:
eyJoaXN0b3J5IjpbMTQ0ODE2OTg5XX0=
-->