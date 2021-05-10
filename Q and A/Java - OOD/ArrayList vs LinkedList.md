### ArrayList vs LinkedList vs Vector
- Of the three,  `LinkedList` is generally going to give you the best performance. Here’s why:
- `ArrayList` and  `Vector`  each **use an array to store the elements** of the list. As a result, when an element is inserted into (or removed from) the middle of the list, the elements that follow must all be shifted accordingly. 
	- `Vector`  is synchronized, so if a thread-safe implementation is  _not_  needed, it is recommended to use  `ArrayList`  rather than Vector.
- `LinkedList`, on the other hand, is implemented using a doubly linked list. As a result, an inserting or removing an element only requires updating the links that immediately precede and follow the element being inserted or removed. 	
	- LinkedList will usually take more memory than List because it needs space for all those next/previous references - and the data will probably have less locality of reference, as each node is a separate object.
### Array vs ArrayList
- Array is a fixed length data structure/object whereas ArrayList is a variable length Collection class, it implements **List<E>, Collection<E>, Iterable<E>, Cloneable, and Serializable** interfaces. It extends **AbstractList<E>** class.
- We **cannot change length of array once created** in Java but **ArrayList can be changed**.
- We **cannot store primitives in ArrayList, it can only store objects**. But array can contain both primitives and objects in Java
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTYwNjg3NjE4Nl19
-->