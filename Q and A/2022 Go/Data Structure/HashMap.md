### How is HashMap implemented
- HashMap internally is implemented by **Array** and **LinkedList**, where each element of the Array node is Singe  Direction LinkedList, which represents the `Map.Entry` Interface
- When the LinkedList length is greater than 8, JDK replaced LinkedList with RedBlack Tree
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
### How does HashMap work
Since we know behind the scene HashMap is implemented with hashed Array, 
#### PUT
1. Call the `hashCode()` method to calculate hash code of **Key** as in K/V set, then calculate the array index based on hashing value and length of the array
2. Adjust length of the array (if element count of container is greater than capacity * loadfactor, would resize it to be 2*n)
3. Insert K/V into HashMap
	- If hash code of Key doesn't existed in current HashMap
	- otherwise, the `equals()` method comes to rescue, since bucket is either LinkedList or Red-Black Tree, we traverse through the bucket, comparing keys in each entries using keys.equals().
		- If we found there is a entry of in the bucket where `equals()` is true, replace that node with new Value as from K/V set
		- Otherwise, insert at the end of the bucket (tail insertion as in LinkedList or tree insertion as in Red-Black Tree)
			- Before JDK 1.7 LinkedList was using Head Insertion, on or after JDK 1.8 using Tail Insertion
			- When hash collision results in LinkedList length > 8, turn the LinkedList into Red-Black Tree
#### GET
1. Call the `hashCode()` method to calculate hash code of Key
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE0NDI0ODA5OTBdfQ==
-->