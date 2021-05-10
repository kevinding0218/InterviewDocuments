### ArrayList vs LinkedList vs Vector
- Of the three,  `LinkedList` is generally going to give you the best performance. Here’s why:
- `ArrayList` and  `Vector`  each **use an array to store the elements** of the list. As a result, when an element is inserted into (or removed from) the middle of the list, the elements that follow must all be shifted accordingly. 
	- `Vector`  is synchronized, so if a thread-safe implementation is  _not_  needed, it is recommended to use  `ArrayList`  rather than Vector.
- `LinkedList`, on the other hand, is implemented using a doubly linked list. As a result, an inserting or removing an element only requires updating the links that immediately precede and follow the element being inserted or removed. LinkedList will usually take more memory than List because it needs space for all those next/previous references - and the data will probably have less locality of reference, as each node is a separate object.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1MjAyNTMzNTNdfQ==
-->