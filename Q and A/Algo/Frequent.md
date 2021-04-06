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
##### Time: O(n^2)
##### Space:O(n)
### [53. Maximum Subarray(Easy)](https://leetcode.com/problems/maximum-subarray/)
#### PrefixSum
- define a `result` and initialize as `Integer.MIN_VALUE`, a `sum` as **prefixSum** and a `minSum` as **min prefixSum** at current element 
- iterate each element in array, 
	- sum up `num` to `sum`, 
	- update `result` with **`Math.max(result, sum - minSum)`**, 
	- update `minSum` with **`Math.min(minSum, sum)`**
##### Time:  O(n)
##### Space:O(1)
#### DP
- max subarray sum at element i can be formalized as **`f(i) = max(f(i-1) + A[i], A[i])**
- we can just use array `num` as `dp` array because we're always iterating forward and never come back
- initialize result as 1st element in array
- iterate each element in array from 2nd element
	- calculate current max subarray sum at element i as **`dp[i] = Math.max(nums[i], dp[i-1]+nums[i])`**
	- update result with `Math.max(max, nums[i])`
##### Time:  O(n)
##### Space:O(1)
#### Follow up: Divide and Conquer
- using  **Divide and Conquer**  approach, we can find the maximum subarray sum in O(nLogn) time. Following is the Divide and Conquer algorithm.
1.  Divide the given array in two halves
2.  Return the maximum of following three
    -   Maximum subarray sum in left half (Make a recursive call)
    -   Maximum subarray sum in right half (Make a recursive call)
    -   Maximum subarray sum such that the subarray crosses the midpoint
 - The idea is simple, find the maximum sum starting from mid point and ending at some point on left of mid, then find the maximum sum starting from mid + 1 and ending with sum point on right of mid + 1. Finally, combine the two and return.
##### Time: O(nlogN)
##### Space:O(1)
### [1186. Maximum Subarray Sum with One Deletion(Medium)](https://leetcode.com/problems/maximum-subarray-sum-with-one-deletion/)
#### DP
- define `max0` as max sub array ending with arr[i] without skipping any element, initialize as 1st element
- define `max1`: max sub array ending with arr[i] or array[i-1] with skipping one element, , initialize as 1st element
- iterate through array and update max1 then max0 (because max1 uses previous max0 and we start from 2nd element)
	- update `max1` as `max1 = Math.max(max1 + arr[i], max0) `, meaning skipping array[i-1] or array[i]
	- update `max0` as `Math.max(max0 + arr[i], arr[i])` meaning not skipping any element, same as without deleting element
	- update `result` as `max(result, max0, max1)`
##### Time: O(n)
##### Space:O(n)
### [20. Valid Parentheses (Easy)](https://leetcode.com/problems/valid-parentheses/)
#### Stack
- since we know the parenthese character, we use a stack to track when there is non matching parenthese.
- iterate through charaters
	- When met with left parentheses such as **`'(', '[','{'`**, push its right parenthese into stack **`stack.push(')', ']', '}')`**
	- otherwise when met with right parentheses , check if **`stack.pop() != ch` or `stack.isEmpty`**, meaning there is no left matching parentheses, **`return false`**
##### Time: O(n)
##### Space: O(n)
### [1249. Minimum Remove to Make Valid Parentheses(Medium)**](https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/)
#### Stack + StringBuilder
- 使用StringBuilder, 在 字符串 的 任意一个位置 添加、删除或更改一个字符的操作都是 O(n)O(n) 的，因为 String 是 不可变的。每次修改整个字符串都会重建。
- 确定所有需要删除字符的索引。
- 根据删除字符的索引创建一个新字符串。
- 如上所述，使用 栈 存储所有 "(" 的索引。
	- 每次遇到 "("，都将它的索引压入栈中。
	-  每次遇到 ")"，都从栈中移除一个索引，用该 ")" 与栈顶的 "(" 匹配。栈的深度等于余量。
	- 扫描到字符串末尾时，栈中剩余的所有索引都是没有匹配的 "("。还需要使用一个 集合 跟踪不匹配的 ")"。
- 然后根据索引删除每个需要删除的字符，返回重新创建的字符串
##### Time: O(n)
##### Space:O(n)
#### StringBuilder 2 loop
1. 从左到右扫描String s，StringBuilder sb 删除不匹配的")",记录一共出现的**'('次数openSeen**和**'()'匹配的次数balance** 
```
char c = s.charAt(i);
if (c == '(') {
    openSeen++;
    balance++;
} if (c == ')') {
    if (balance == 0) {
        // skip current ')', do not add into sb
        continue;
    }
    balance--;
}
sb.append(c);
```
2. 计算openToKeep = openSeen - balance
3. 从左到右扫描StringBuilder sb，StringBuilder result每次遇到"("，decrease openToKeep, 如果openToKeep < 0则删除 
```
char c = sb.charAt(i); 
if (c == '(') {
	openToKeep--;
	if (openToKeep < 0) {continue;} 
} 
result.append(c);
```
##### Time: O(n)
##### Space:O(n)
### [301. Remove Invalid Parentheses(Hard)](https://leetcode.com/problems/remove-invalid-parentheses/)
#### DFS
1. 一次遍历计算出多余的「左括号」和「右括号」
- 当遍历到「右括号」的时候，
	- 如果此时「左括号」的数量不为 0，因为 「右括号」可以与之前遍历到的「左括号」匹配，此时「左括号」出现的次数 -1；
	- 如果此时「左括号」的数量为 00，「右括号」数量加 1；
- 当遍历到「左括号」的时候，「左括号」数量加 1
2. 我们设计变量 leftCount 和 rightCount 分别表示在遍历的过程中已经遍历到的左括号和右括号的数量，统计它们是为了方便 剪枝。这是因为 只有当「已经遍历到的左括号的数量」严格大于「已经遍历到的右括号的数量」的时候，才可以继续添加「右括号」
```
/*
* @param index 当前遍历到的下标  
* @param leftCount 已经遍历到的左括号的个数  
* @param rightCount 已经遍历到的右括号的个数  
* @param leftRemove 最少应该删除的左括号的个数  
* @param rightRemove 最少应该删除的右括号的个数  
* @param path 一个可能的结果
* */
void dfs(int index, int leftCount, int rightCount, int leftRemove, int rightRemove, StringBuilder path)
```
### [32. Longest Valid Parentheses(Hard)](https://leetcode.com/problems/longest-valid-parentheses/)
#### Stack
- 我们始终保持栈底元素为当前已经遍历过的元素中「最后一个没有被匹配的右括号的下标」，这样的做法主要是考虑了边界条件的处理，栈里其他元素维护左括号的下标：
	- 对于遇到的每个‘(’ ，我们将它的下标放入栈中
	- 对于遇到的每个 ‘)’ ，我们先弹出栈顶元素表示匹配了当前右括号：
		- 如果栈为空，说明当前的右括号为没有被匹配的右括号，我们将其下标放入栈中来更新我们之前提到的「最后一个没有被匹配的右括号的下标」
		- 如果栈不为空，当前右括号的下标减去栈顶元素即为「以该右括号为结尾的最长有效括号的长度」
- 我们从前往后遍历字符串并更新答案即可。
- 需要注意的是，如果一开始栈为空，第一个字符为左括号的时候我们会将其放入栈中，这样就不满足提及的「最后一个没有被匹配的右括号的下标」，为了保持统一，我们在一开始的时候往栈中放入一个值为 -1−1 的元素。
##### Time: O(n)
##### Space:O(n)
#### Double Loop
- 我们利用两个计数器 left 和 right 。  
- 首先，我们从左到右遍历字符串，  
	 - 对于遇到的每个 ‘(’，我们增加 left 计数器，  
	- 对于遇到的每个 ‘)’ ，我们增加 right 计数器。  
	- 每当 left 计数器与 right 计数器相等时，我们计算当前有效字符串的长度，并且记录目前为止找到的最长子字符串。  
	- 当 right 计数器比 left 计数器大时，我们将 left 和 right 计数器同时变回 0。  
- 这样的做法贪心地考虑了以当前字符下标结尾的有效括号长度，  
	- 每次当右括号数量多于左括号数量的时候之前的字符我们都扔掉不再考虑，重新从下一个字符开始计算，  
	- 但这样会漏掉一种情况，就是遍历的时候左括号的数量始终大于右括号的数量，即 (((() ，这种时候最长有效括号是求不出来的。  
- 我们只需要从右往左遍历用类似的方法计算即可，只是这个时候判断条件反了过来：  
	- 当 left 计数器比 right 计数器大时，我们将 left 和 right 计数器同时变回 0  
	- 当 left 计数器与 right 计数器相等时，我们计算当前有效字符串的长度，并且记录目前为止找到的最长子字符串
### [72. Edit Distance(Hard)](https://leetcode.com/problems/edit-distance/)
#### DP
- 定义f[i][j]为word1前i个字符到word2的前j个字符的转化的最小步。**`int[][] dp = new int[length1+1][length2+1];`**
- 假设对于f[i][j]以前的之都已知，考虑f[i][j]的情形
	1. 若word1[i] = word2[j]，那么说明只要word1的前i-1个能转换到word2的前j-1个即可，所以 **`f[i][j] = f[i-1][j-1`]**, 反之，若不等
	2. 给word1插入一个和word2最后的字母相同的字母，这时word1和word2的最后一个字母就一样了，此时编辑距离等于1（插入操作） + 插入前的word1到word2去掉最后一个字母后的编辑距离 **`f[i][j] = f[i][j - 1] + 1`;** , 
		- 例从ab --> cd: 我们可以计算从 ab --> c 的距离，也就是 f[i][j - 1]，最后再在尾部加上d，距离+1
	3. 删除word1的最后一个字母, 此时编辑距离等于1（删除操作） + word1去掉最后一个字母到word2的编辑距离 **`f[i][j] = f[i - 1][j] + 1`**, 
		- 例从ab --> cd: 我们计算先删除b，先+1，再加上从 a --> cd 的距离： f[i - 1][j] ，
	4. 把word1的最后一个字母替换成word2的最后一个字母, 此时编辑距离等于 1（替换操作） + word1和word2去掉最后一个字母的编辑距离。为 **`f[i][j] = f[i - 1][j - 1] + 1`**
	- 三者取最小值即可
		```
		if (word1.charAt(i - 1) == word2.charAt(j - 1)) {  
		  // case 1.  
		  dp[i][j] = dp[i - 1][j - 1];  
		}else{  
		  // case 2, 3 & 4  
		  dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));  
		}
		```
- 初始条件
	- 当word1和word2都为空：f[0][0] = 0
	- dp[0][i ~ length2] = i; dp[i ~ length1][0] = i
#### Time: O(n*m)
#### Space:O(n*m)
### [234. Palindrome Linked List(Easy)](https://leetcode.com/problems/palindrome-linked-list/)
#### Find Mid and Reverse 2nd half
#### Time: O(n)
#### Space:O(1)
- Refer to Template LinkedList
### [125. Valid Palindrome(Easy)](https://leetcode.com/problems/valid-palindrome/)
#### Two Pointers
- start and end, moving towards each other when current char is letter or digit `while (start <= end && !Character.isLetterOrDigit(s.charAt(start))) {  start ++; }`
- return false as long as character not matching
##### Always check `start <= end` inside while loop
##### Time: O(n)
##### Space:O(1)
### [680. Valid Palindrome II(Medium)](https://leetcode.com/problems/valid-palindrome-ii/)
#### Two Pointers
- start and end, moving towards each other as long as current characters are same
- since we only may delete one character, when there is unmatched character, we skip that condition by moving start and end one more index **`return helper(s, start + 1, end) || helper(s, start, end - 1);`**
	- why need both? consider a string like "abcdca", just moving start wouldn't work
- `helper` will continue moving two pointers and compare, return false as long as we found another unmatched character
##### Time: O(n)
##### Space:O(1)
### [1332. Remove Palindrome Subsequence(Easy)](https://leetcode.com/problems/remove-palindromic-subsequences/)
#### Check if s was a palindrome
- 回文子序列不是回文子字符串，比如"aababdaba"，"aaaaa"就是他的回文子序列。
	- 也就是顶多删两次，一次全删掉"a"，一次全删掉"b"。
	- 如果是回文字符串，就删一次。
	- 空字符串就0次。
- Check if S is empty, return 0
- Otherwise check if S is palindrome by using two pointers, as long as there is unmatched character, meaning S is not palindrome, return 2
- Else meaning S is palindrome, return 1
##### Time: O(n)
##### Space:O(1)
### [973. K Closest Points to Origin (Medium)](https://leetcode.com/problems/k-closest-points-to-origin/)
#### MaxHeap/PriorityQueue
1. condition check when input is empty or input size less or equals than K
2. define a Point class for better comparing
3. define a MaxHeap by using custom comparator 
	- asking for Kth smallest we use MaxHeap, so that top of MaxHeap stores the max element and all its children nodes stores element smaller than itself
	- asking for Kth greatest use MinHeap, so that top of MinHeap stores the min element and all its children nodes stores element greater than itself
```
PriorityQueue<Point> maxHeap = new PriorityQueue<>(k + 1,  
  Comparator.comparing((Point p) -> getDistance(p, origin))  
  .thenComparingInt(p -> p.x)  
  .thenComparingInt(P -> P.y)  
  .reversed()  
);
```
4. Offer element into MaxHeap until it reaches capacity of K, poll elements out
- Remaining would be Kth Point with smallest distance
#### Time: O(logK*n) - offer/poll of Heap is logK
#### Space:O(K)
### 最优解法：基于 quick select 的 O(n + klogk)
- 先用 Quick Select 找到 kth closest point：O(n)；
- 对 top k 个点按 distance metric sort 一遍：O(klogk)
### [349. Intersection of Two Arrays(Easy)](https://leetcode.com/problems/intersection-of-two-arrays/)
#### HashSet
1. Validation about both inputs is null or length is 0
2. Create HashSet1 to store all element of nums1, Create HashSet2 to store duplicate number if found
3. Iterator through nums2 check if any element contains in HashSet1 , if there is, meaning current element is duplicate, add into HashSet2 
4. Initialize result array of HashSet2.size(), add item into results
##### Time: O(n + m)
##### Space:O(min(n + m))
#### Sort & Two Pointers
### read-n-characters-given-read4
### read-n-characters-given-read4-stream-II
### [26. Remove Duplicates from Sorted Array(Easy)](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
### [1305. All Elements in Two BST(Medium)](https://leetcode.com/problems/all-elements-in-two-binary-search-trees/)
### [1428. Leftmost Column with at Least a One(Medium)](https://www.cnblogs.com/cnoodle/p/12759214.html)
### [987. Vertical Order Traversal of a Binary Tree(Hard)](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/)
### [102. Binary Tree Level Order Traversal(Medium)](https://leetcode.com/problems/binary-tree-level-order-traversal/)
### [199. Binary Tree Right Side View(Medium)](https://leetcode.com/problems/binary-tree-right-side-view/)
### [235. Lowest Common Ancestor of a Binary Search Tree(Easy)](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)
### [236. Lowest Common Ancestor of a Binary Tree(Medium)](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/)
### [124. BinaryTreeMaximumPathSum (Hard)](https://leetcode.com/problems/binary-tree-maximum-path-sum/)
#### follow up - nary
- 做法是找出他们n个children中最大的两个sum，剩下的与要而思一个做法。

### [510. # Inorder Successor in BST II(Medium)](https://junhaow.com/lc/problems/tree/bst/510_inorder-successor-in-bst-ii.html/)
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
### [827. Making a Large Island(Hard)](https://leetcode.com/problems/making-a-large-island/)

### [424. Longest Repeating Character Replacement(Medium)](https://leetcode.com/problems/longest-repeating-character-replacement/)
### [451. Sort Characters By Frequency(Medium)](https://leetcode.com/problems/sort-characters-by-frequency/) 
#### follow up, what if input is stream
### [283. Move Zeroes(Easy)](https://leetcode.com/problems/move-zeroes/)
### [31. Next Permutation(Medium)](https://leetcode.com/problems/next-permutation/)
### [1053. Previous Permutation With One Swap(Medium)](https://leetcode.com/problems/previous-permutation-with-one-swap/)
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
eyJoaXN0b3J5IjpbMTgzODAxODQ0LC0xNjg3MDcwNjY2LDIxMz
E1NzkxMDksMjA2OTg4ODQxMiwyMDA2NzIzMDUxLDUyNzkwMTc4
LDc4ODI4MzI4NCwxMDMyNjY4ODk1LC0xMDIxOTM5ODE2LDE2OD
Q5MTk4MTksMTE4Njk3MTcyNSwyMTA1OTM4ODQ0LC0xOTY3OTg5
MTM1LDEyNzU0MjkzMTAsNTY4MzQyMDMxLDc2MjkzMzc2Myw4Nz
E3MzM5MjUsNjMyMTU3ODY3LDQ5NjMxODE3LDEwNDg4Mzc5Nzld
fQ==
-->