### [149. Max Points in one Line (Hard)](https://le
etcode.com/problems/max-points-on-a-line/)
#### HashMap - slope
1. loop from each Point I with another Point j in 2 for loop
2. in inner loop, **the goal is to get the max point with Point i of same lines when end of the inner loop**
	- define **`maxPoints = 1, duplicates = 0, horizontalLines = 1`**
	- increment duplicates if Point i and Point j are same coordinates **`duplicates ++`**
	- increment horizontalLines if i.y == j.y, update maxPoint with **`Max(maxPoint, horizontalLines)`**
	- calculate slope then increment count in map, update maxPoint **`Max(maxPoint, map.get(slope))`**
3. in outer loop
	- increment maxPoints with duplicates, **`maxPoints += duplicates`**
	- update ans with **`Max(ans, maxPoints)`**
- Time: O(n^2)
- Space:O(n)
### [53. Maximum Subarray(Easy)](https://leetcode.com/problems/maximum-subarray/)
#### PrefixSum
- define a `result` and initialize as `Integer.MIN_VALUE`, a `sum` as **prefixSum** and a `minSum` as **min prefixSum** at current element 
- iterate each element in array, 
	- sum up `num` to `sum`, 
	- update `result` with **`Math.max(result, sum - minSum)`**, 
	- update `minSum` with **`Math.min(minSum, sum)`**
- Time:  O(n)
- Space:O(1)
#### DP
- max subarray sum at element i can be formalized as **`f(i) = max(f(i-1) + A[i], A[i])**
- we can just use array `num` as `dp` array because we're always iterating forward and never come back
- initialize result as 1st element in array
- iterate each element in array from 2nd element
	- calculate current max subarray sum at element i as **`dp[i] = Math.max(nums[i], dp[i-1]+nums[i])`**
	- update result with `Math.max(max, nums[i])`
- Time:  O(n)
- Space:O(1)
#### Follow up: Divide and Conquer
- using  **Divide and Conquer**  approach, we can find the maximum subarray sum in O(nLogn) time. Following is the Divide and Conquer algorithm.
1.  Divide the given array in two halves
2.  Return the maximum of following three
    -   Maximum subarray sum in left half (Make a recursive call)
    -   Maximum subarray sum in right half (Make a recursive call)
    -   Maximum subarray sum such that the subarray crosses the midpoint
 - The idea is simple, find the maximum sum starting from mid point and ending at some point on left of mid, then find the maximum sum starting from mid + 1 and ending with sum point on right of mid + 1. Finally, combine the two and return.
 - Time: O(nlogN)
 - Space:O(1)
### [1186. Maximum Subarray Sum with One Deletion(Medium)](https://leetcode.com/problems/maximum-subarray-sum-with-one-deletion/)
#### DP
- define `max0` as max sub array ending with arr[i] without skipping any element, initialize as 1st element
- define `max1`: max sub array ending with arr[i] or array[i-1] with skipping one element, , initialize as 1st element
- iterate through array and update max1 then max0 (because max1 uses previous max0 and we start from 2nd element)
	- update `max1` as `max1 = Math.max(max1 + arr[i], max0) `, meaning skipping array[i-1] or array[i]
	- update `max0` as `Math.max(max0 + arr[i], arr[i])` meaning not skipping any element, same as without deleting element
	- update `result` as `max(result, max0, max1)`
- Time: O(n)
- Space:O(n)
### [20. Valid Parentheses (Easy)](https://leetcode.com/problems/valid-parentheses/)
#### Stack
- since we know the parenthese character, 
### [1249. Minimum Remove to Make Valid Parentheses(Medium)**](https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/)
### [301. Remove Invalid Parentheses(Hard)](https://leetcode.com/problems/remove-invalid-parentheses/)(https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/)
### [32. Longest Valid Parentheses(Hard)](https://leetcode.com/problems/longest-valid-parentheses/)
### [72. Edit Distance(Hard)](https://leetcode.com/problems/edit-distance/)
### [199. Binary Tree Right Side View(Medium)](https://leetcode.com/problems/binary-tree-right-side-view/)
### [234. Palindrome Linked List(Easy)](https://leetcode.com/problems/palindrome-linked-list/)
### [125. Valid Palindrome(Easy)](https://leetcode.com/problems/valid-palindrome/)
### [680. Valid Palindrome II(Medium)](https://leetcode.com/problems/valid-palindrome-ii/)
### [1332. Remove Pallindrome Subsequence(Medium)](https://leetcode.com/problems/remove-palindromic-subsequences/)
### [124. BinaryTreeMaximumPathSum (Hard)](https://leetcode.com/problems/binary-tree-maximum-path-sum/)
#### follow up - nary
- 做法是找出他们n个children中最大的两个sum，剩下的与要而思一个做法。
### [973. K Closest Points to Origin (Medium)](https://leetcode.com/problems/k-closest-points-to-origin/)
### [827. Making a Large Island(Hard)](https://leetcode.com/problems/making-a-large-island/)
### [349. Intersection of Two Arrays(Easy)](https://leetcode.com/problems/intersection-of-two-arrays/)
### read-n-characters-given-read4
### read-n-characters-given-read4-stream-II
### [26. Remove Duplicates from Sorted Array(Easy)](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
### [1305. All Elements in Two BST(Medium)](https://leetcode.com/problems/all-elements-in-two-binary-search-trees/)
### [1428. Leftmost Column with at Least a One(Medium)](https://www.cnblogs.com/cnoodle/p/12759214.html)
### [987. Vertical Order Traversal of a Binary Tree(Hard)](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/)
### [102. Binary Tree Level Order Traversal(Medium)](https://leetcode.com/problems/binary-tree-level-order-traversal/)
### Calculate salary tax
-  Calculate tax if Salary and Tax Brackets are given as list in the form  
   [ [10000, 0.3],[20000, 0.2], [30000, 0.1], [null, .1]]  
   null being rest of the salary  
```
double[] max = {0,9075,36900,89350,186350,405100};
double[] rate = {0,0.10,0.15,0.25,0.28,0.33};
double left = income;
double tax = 0.0d;
for(int i = 1; i < max.length && left > 0; i++) {
    double df = Math.min(max[i]-max[i-1],left);
    tax += rate[i]*df;
    left -= df;
}
```
### [510. # Inorder Successor in BST II(Medium)](https://junhaow.com/lc/problems/tree/bst/510_inorder-successor-in-bst-ii.html/)
### [424. Longest Repeating Character Replacement(Medium)](https://leetcode.com/problems/longest-repeating-character-replacement/)
### [451. Sort Characters By Frequency(Medium)](https://leetcode.com/problems/sort-characters-by-frequency/) 
#### follow up, what if input is stream
### [283. Move Zeroes(Easy)](https://leetcode.com/problems/move-zeroes/)
### [235. Lowest Common Ancestor of a Binary Search Tree(Easy)](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)
### [236. Lowest Common Ancestor of a Binary Tree(Medium)](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/)
### [31. Next Permutation(Medium)](https://leetcode.com/problems/next-permutation/)
### [1053. Previous Permutation With One Swap(Medium)](https://leetcode.com/problems/previous-permutation-with-one-swap/)
### [266. palindrome-permutation(Easy)](https://www.jiuzhang.com/solutions/palindrome-permutation)
### Check if contiguous subarray sum can be equals to K
### [146. LRU Cache(Medium)](https://leetcode.com/problems/lru-cache/)
### [1382. Balance a Binary Search Tree(Medium)](https://leetcode.com/problems/balance-a-binary-search-tree/)
### [88. Merge sorted array(Easy)](https://leetcode.com/problems/merge-sorted-array/)
### [15. 3Sum(Medium)](https://leetcode.com/problems/3sum/)
### [238. Product of Array Except Self(Medium)](https://leetcode.com/problems/product-of-array-except-self/)
### [304. Range Sum Query 2D - Immutable(Medium)](https://leetcode.com/problems/range-sum-query-2d-immutable/)
### [953. Verifying an Alien Dictionary(Easy)](https://leetcode.com/problems/verifying-an-alien-dictionary/)
### [560. Subarray Sum Equals K(Medium)](https://leetcode.com/problems/subarray-sum-equals-k/)
### [71. Simplify Path(Medium)](https://leetcode.com/problems/simplify-path/)
### [410. Split Array Largest Sum(Hard)](https://leetcode.com/problems/split-array-largest-sum/)
### [426. Convert the binary search tree into a sorted doubly linked list(Medium)](https://www.jiuzhang.com/solutions/convert-binary-search-tree-to-sorted-doubly-linked-list)
### [347. Top K Frequent Elements(Medium)](https://leetcode.com/problems/top-k-frequent-elements/)
### Find median in BST without extra space
- 先找出bst的size, 然后再用inorder traversal 取第 k 个值.
### [863. All Nodes Distance K in Binary Tree](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTA1NDYwNTQ5OSwxMjc1NDI5MzEwLDU2OD
M0MjAzMSw3NjI5MzM3NjMsODcxNzMzOTI1LDYzMjE1Nzg2Nyw0
OTYzMTgxNywxMDQ4ODM3OTc5LDM1MTM4MjY2MiwxMTEyMTI0MD
U0LC0xMzIwMDMxMTYzLC0xMjk2NjgyOTYsNDIwODMyOTEyLC0y
OTA3OTA0OTQsLTE2MzM1NjY0MDgsMTM2OTkxOTgyMiwxMTI0Nj
U0OTY1LDU3MDg4MzU0OCw4ODcyMTk0NzAsLTIwNDI3MjQ2MTVd
fQ==
-->