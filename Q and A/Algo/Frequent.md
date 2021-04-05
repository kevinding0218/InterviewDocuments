### [149. Max Points in one Line (Hard)](https://leetcode.com/problems/max-points-on-a-line/)
1. loop from each Point I with another Point j in 2 for loop
2. in inner loop, **the goal is to get the max point with Point i of same lines when end of the inner loop**
	- define `maxPoints = 1, duplicates = 0, horizontalLines = 1`
	- increment duplicates if Point i and Point j are same coordinates `duplicates ++`
	- increment horizontalLines if i.y == j.y, update maxPoint with `Max(maxPoint, horizontalLines)`
	- calculate slope then increment count in map, update maxPoint `Max(maxPoint, map.get(slope))`
3. in outer loop
	- increment maxPoints with duplicates, `maxPoints += duplicates`
	- update ans with `Max(ans, maxPoints)`
- Time: O(n^2)
- Space:O(n)
### [20. Valid Parentheses (Easy)](https://leetcode.com/problems/valid-parentheses/)
### [1249. Minimum Remove to Make Valid Parentheses(Medium)](https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/)
### [32. Longest Valid Parentheses](https://leetcode.com/problems/longest-valid-parentheses/)
### [72. Edit Distance (Hard)](https://leetcode.com/problems/edit-distance/)
### [199. Binary Tree Right Side View (Medium)](https://leetcode.com/problems/binary-tree-right-side-view/)
### [234. Palindrome Linked List (Easy)](https://leetcode.com/problems/palindrome-linked-list/)
### [125. Valid Palindrome (Easy)](https://leetcode.com/problems/valid-palindrome/)
### [680. Valid Palindrome II (Medium)](https://leetcode.com/problems/valid-palindrome-ii/)
### [1332. Remove Pallindrome Subsequence](https://leetcode.com/problems/remove-palindromic-subsequences/)
### [124. BinaryTreeMaximumPathSum (Hard)](https://leetcode.com/problems/binary-tree-maximum-path-sum/)
### [973. K Closest Points to Origin (Medium)](https://leetcode.com/problems/k-closest-points-to-origin/)
### [827. Making a Large Island(Hard)](https://leetcode.com/problems/making-a-large-island/)
### [349. Intersection of Two Arrays(Easy)](https://leetcode.com/problems/intersection-of-two-arrays/)
### read-n-characters-given-read4
### read-n-characters-given-read4-stream-II
### [26. Remove Duplicates from Sorted Array(Easy)](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
### [1305. All Elements in Two BST(Medium)](https://leetcode.com/problems/all-elements-in-two-binary-search-trees/)
### [1428. Leftmost Column with at Least a One(Medium)](https://www.cnblogs.com/cnoodle/p/12759214.html)
### [987. Vertical Order Traversal of a Binary Tree(Hard)](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/)


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE4MDUzMjg1OTIsNjc1MTYzNjFdfQ==
-->