### How HashMap is implemented
HashMap internally is implemented by Array and LinkedList, when the LinkedList length is greater than 8, JDK replaced LinkedList with RedBlack Tree
#### What is Red-Black Tree
1.  
2. Every node has a color either red or black.
3.  The root of the tree is always black.
4.  There are no two adjacent red nodes (A red node cannot have a red parent or red child).
5.  Every path from a node (including root) to any of its descendants NULL nodes has the same number of black nodes.
6.  All leaf nodes are black nodes.
#### Why Red-Black Tree
Most of the BST operations (e.g., search, max, min, insert, delete.. etc) take O(h) time where h is the height of the BST. The cost of these operations may become O(n) for a skewed Binary tree. If we make sure that the height of the tree remains O(log n) after every insertion and deletion, then we can guarantee an upper bound of O(log n) for all these operations. The height of a Red-Black tree is always O(log n) where n is the number of nodes in the tree.
7. 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyMTcxNjEwMzRdfQ==
-->