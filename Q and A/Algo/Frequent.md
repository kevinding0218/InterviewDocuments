### Max Points in one Line (Hard)
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
### 72. Edit Distance (Hard)
### 199. Binary Tree Right Side View (Medium)
### 234. Palindrome Linked List (Easy)
### [125. Valid Palindrome (Easy)](https://leetcode.com/problems/valid-palindrome/)
### [680. Valid Palindrome II (Medium)](https://leetcode.com/problems/valid-palindrome-ii/)
### 124. BinaryTreeMaximumPathSum (Hard)
### [973. K Closest Points to Origin (Medium)](https://leetcode.com/problems/k-closest-points-to-origin/)
### [827. Making a Large Island(Hard)](https://leetcode.com/problems/making-a-large-island/)
### [349. Intersection of Two Arrays](https://leetcode.com/problems/intersection-of-two-arrays/)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTYxMTM3NDY1Myw2NzUxNjM2MV19
-->