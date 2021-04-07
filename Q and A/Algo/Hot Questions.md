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
##### Space:O(n or m)) - can improve by choosing min(n, m)
#### Sort & Two Pointers
1. Sort two arrays: O(nlogN + mlogM)
2. Create HashSet1 to store duplicate number if found
3. Two pointers iterative through each array
	- if num1 < num2, increase index1
	- if num1 > num2, increase index2
	- otherwise insert into result array also check if current element was in array before
```
// 保证加入元素的唯一性  
if (index == 0 || num1 != intersection[index - 1]) {  
  intersection[index++] = num1;  
}
```
#### Time: O(mlogm+nlogn)
#### Space:O(logm+logn) 空间复杂度主要取决于排序使用的额外空间。
### [26. Remove Duplicates from Sorted Array(Easy)](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
1. 要求每个元素至少出现1次，即数组大小至少得从`2`开始遍历有意义, **`validation about null/length = 0/length < 2`**
2. Initiate count = 1, 即前1个元素不用管, 从第2个元素（以1为下标）开始read，每次和之前（read - 1）的元素比较, read每次移一步，包括遇到相同的元素
3. 遇到不同的元素时，此时read已经跳过之前在[count - 1 ~ read - 1]区间内重复的元素,  写入那个元素并递增count
5. 最后数组下标 0 - count 部分即为去重后的数组,数组长度为count
#### Time: O(n)
#### Space:O(1)
#### [Follow up](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/): Remove duplicate that only allow duplicate appears at k times
1. 要求每个元素至少出现1次，即数组大小至少得从`k+1`开始遍历有意义, **`validation about null/length = 0/length < k`**
2. Initiate count = k, 即前k个元素不用管, 从第k+1个元素(以k为下标)开始read，每次和之前的元素比较, read每次移一步，包括遇到相同的元素
3. compare current iteration element with its prev (read - k) index value
	- if the values are equal, do nothing and move forward
	- otherwise, replace with array[count] once different value found, count++
	- initially the 1st different value will be replaced at array[k]
4. eventually array element from index 0 to count - 1 will be replaced with number of k times of
#### Time: O(n)
#### Space:O(1)
### [157. Read-n-characters-given-read4](https://aaronice.gitbook.io/lintcode/data_structure/read-n-characters-given-read4)
#### Queue Idea using array
- declare an boolean `eof` to indicate if current read4 has read till end of file
- declare a pointer `total` to indicate current position that reads from beginning to current length
- declare a char array `tmp` of size 4 to store `read4` content
- while (!eof && total < n) we keep read from file by `read4`， each time 
	- declare a local int `bufCount` to indicate total characters read count from read4 as `int bufCount = read4(buffer);`
	- check `eof = bufCount< 4`
	- update `bufCount= Math.min(bufCount, n - total)` in case current read4 returns more than what we need to just total of n characters
	- update result as `buf[total++] = buffer[0 ~ count]`
#### Time: O(n/4)
#### Space:O(1)
### [158. Read-n-characters-given-read4-stream-II]()
- declare an boolean `eof` to indicate if current read4 has read till end of file
- declare a pointer `total` to indicate current position that reads from beginning to current length
- declare a char array `tmp` of size 4 to store `read4` content
- buffer是一个队列, 队列先进先出可以保持顺序不变
- 队列为空时就进队（read4）
- 队列不为空时就出队，并把出队的元素放到答案中
- declare a global int `bufCount` to indicate total characters read count from read4 each time
- declare a global int `offset` to indicate how many characters left in last time that needs to continue reading
- while (!eof && total < n) we keep read from file by `read4`， each time 
	- `(bufCount == 0` meaning all elements have been finished reading last time, if so, we continue calling `bufCount = read4(buffer);` and check `eof = bufCount < 4`
	- declare a local variable `used = min(bufCount, n - total)` to indicate how many character should be used current round
	- update result as `buf[total+i] = buffer[offset + i]`
	- update `total += used`, `bufCount -= used` and `offset = (offset + used) % 4`
### Calculate salary tax
#### Question
-  Calculate tax if Salary and Tax Brackets are given as list in the form   [ [10000, 0.3],[20000, 0.2], [30000, 0.1], [null, .1]] , null being rest of the salary  
1. loop through brackets starting from index 1 and while salary > 0, calculate in current brancket range, how much salary needs to pay out by `double payout = min(bracket[i][0] - bracket[i-1][0], salary);`
2. get tax to pay in current period that `tax += bracket[i][1] * payout` and add to result
3. decrease salary by payout `salary -= payout;`
#### Time:O(bracket.size())
#### Space:O(1)
### [451. Sort Characters By Frequency(Medium)](https://leetcode.com/problems/sort-characters-by-frequency/) 
#### HashMap & PriorityQueue
1. 根据 s 构造 HashMap, 因为面向字母所以我们可以用int[]来存储每个character出现的个数,下标可以直接(char[i])
2. 然后我们构造一个priority queue/maxHeap用来排序, heap top store max appearance character,with comparetor can be `(a, b) -> frequency[b] - frequency[a]`
3. 把所有frequency中不为0的character进队列, 此时队列里面最顶端为出现次数最大的character
4. 使用StringBuilder构造队列里面一个个poll出来的character组成String, 每次都decrement frequency[ch]
#### Time: O(n)
#### Space:O(n)
#### Check TopKFrequentElements, TopKFrequentWordsI && II
#### follow up, what if input is stream
### [424. Longest Repeating Character Replacement(Medium)](https://leetcode.com/problems/longest-repeating-character-replacement/)
#### HashMap & Sliding Window
- declare a HashMap **`int[] freq = new int[26]`** to store character with its appearance count in current window `(left, right) 内最多替换 k 个字符可以得到只有一种字符的子串`
- Move right pointer from **0 ~ len**
	1. add frequency count of current **`freq[s.charAt(right) - 'A']++;`**
	2. calculate current window's max frequent character count **`maxCount = Math.max(maxCount, freq[s.charAt(right) - 'A'])`**
	3. increment right by **`right ++`**
	4. check current sliding window length if it's able to allow **`right - left > maxCount + k`**
		- **if true, meaning current k is not enough**, move left point by decrement appearance of current left character **`freq[s.charAt(left) - 'A']--`** , then increment left **`left++`**
		- **if false, meaning current window can allow** `maxCount + k` range, left remain same
	5. update result by **`max(res, right - left)`**
#### Time: O(n)
#### Space:  O(A)，这里 A 是输入字符串 `S` 出现的字符 ASCII 值的范围
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
### [827. Making a Large Island(Hard)](https://leetcode.com/problems/making-a-large-island/)




### [283. Move Zeroes(Easy)](https://leetcode.com/problems/move-zeroes/)
####  Two Pointers
- 使用两个指针right和left
	1. 将两个指针先指向0，即数组头部
	2. right向后扫描 **`right++`**，当遇到非0数即 **`nums[right] != 0`**时，将其赋值给left指针指向的位置，即**`nums[left] = nums[right]`**，并将left向后移动一位 **`left ++`**
	3. 若left指针还未指向尾部 **`while(left < length)`**，即剩余的位置都是0，将剩余数组赋值为0 **`nums[left] = 0`**
#### Time: O(n)
#### Space:O(1)
### [31. Next Permutation(Medium)](https://leetcode.com/problems/next-permutation/)
#### Simulate
- 注意到下一个排列总是比当前排列要大，除非该排列已经是最大的排列。
- 我们希望找到一种方法，能够找到一个大于当前序列的新序列，且变大的幅度尽可能小. 具体地：
	1.我们需要将一个左边的「较小数」与一个右边的「较大数」交换，以能够让当前排列变大，从而得到下一个排列, 同时我们要让这个「较小数」尽量靠右，而「较大数」尽可能小。当交换完成后，「较大数」右边的数需要按照升序重新排列。
	2.这样可以在保证新排列大于原来排列的情况下，使变大的幅度尽可能小。
- 具体地，我们这样描述该算法，对于长度为 n 的排列a：
	1.首先从后向前查找第一个顺序对 (descStart,descStart+1)，满足 `if (nums[descStart] < nums[descStart + 1])`。这样「较小数」即为 a[descStart]。**此时 [descStart+1,n) 必然是下降序列**。
	2.如果找到了顺序对，那么在区间 [i+1,n) 中从后向前查找第一个元素 j 满足 **`if (nums[descStart] < nums[greaterDescStart])`**。这样「较大数」即为 a[greaterDescStart]。
	3.**`swap(nums, descStart, greaterDescStart)`**，此时可以证明区间 **[i+1,n) 必为降序**。
	4.我们可以直接使用双指针 **`reverse(nums, descStart + 1)`** 使其变为升序，而无需对该区间进行排序。
- 注意: 如果在步骤 1 找不到顺序对，说明当前序列已经是一个降序序列，即最大的序列，我们直接跳过步骤 2 执行步骤 3，即可得到最小的升序序列
#### Time: O(n)
#### Space:O(1)
### [1053. Previous Permutation With One Swap(Medium)](https://leetcode.com/problems/previous-permutation-with-one-swap/)
- 一次交换后字典序就变小，交换的两个数，肯定原先是大数在前，小数在后。交换后，小数换到前面来，大数换到后面去。那么有
	1. 从后向前查找第一个顺序对 (ascStart,ascStart+1)，满足 **`if (nums[ascStart] > nums[ascStart + 1])`**, 这样「较大数」即为 a[ascStart]。**此时 [ascStart+1,n) 必然是上升序列**
	2. 寻找在 nums[ascStart] 最右边且小于 A[ascStart] 的最大的数字 nums[smallerAscStart], 由于 **`nums[smallerAscStart] < nums[ascStart]`**, 交换 nums[ascStart] 与 nums[smallerAscStart] 后的序列字典序一定小于当前字典序
		- 注意:  the second check to skip duplicate numbers **`if (..
	&& nums[smallerAscStart] != nums[smallerAscStart - 1])`**
		- swap(nums, ascStart, smallerAscStart); 序列字典序一定小于当前字典序
#### Time: O(n)
#### Space:O(1)
### [146. LRU Cache(Medium)](https://leetcode.com/problems/lru-cache/)
#### HashMap & Double LinkedList
- LRU 缓存机制可以通过哈希表辅以双向链表实现，我们用一个哈希表和一个双向链表维护所有在缓存中的键值对。
- 双向链表按照被使用的顺序存储了这些键值对，靠近尾部的键值对是最近使用的，而靠近头部的键值对是最久未使用的 **` dummyHead <-> least used <-> most used <-> dummyTail`**
	- 在双向链表的实现中，使用一个伪头部（dummy head）和伪尾部（dummy tail）标记界限, 这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在
- 我们首先使用哈希表进行定位，找出缓存项在双向链表中的位置，随后将其移动到双向链表的尾部，
- 对于 get 操作，首先判断 key 是否存在：
	- 如果 key 不存在，则返回 -1；
	- 如果 key 存在，则 key 对应的节点是最近被使用的节点。
		1.通过哈希表定位到该节点在双向链表中的位
		2.将该节点前后相连并将该节点移动到双向链表的尾部
		3.最后返回该节点的值
- 对于 put 操作，首先判断 key 是否存在：
	- 如果 key 存在，则直接调用 get 操作
	- 如果 key 不存在
		1. 判断双向链表的节点数是否超出容量
		2. 如果超出容量，则删除哈希表中最靠近头部的项
		3. 并删除双向链表的尾部节点
		4. 使用 key 和 value 创建一个新的节点，将 key 和该节点添加进哈希表中。
		5. 在双向链表的尾部添加该节点
### [498. Diagonal Traverse(Medium)](https://leetcode.com/problems/diagonal-traverse/)
#### Simulate
- 在第一行最后一列的元素作为起点的对角线上，对于给定元素 [i, j][i,j]，可以向右移动一行向上移动一列沿对角线向上移动 [i - 1, j + 1]，也可以向左移动一行向下移动一列沿对角线向下移动 [i + 1, j - 1]。(注意：这种移动方式仅适用于从右往左的对角线)
- 将元素添加到最终结果数组之前，只需要翻转奇数对角线上的元素顺序即可。例如：从左边开始的第三条对角线 [3, 7, 11]，将这些元素添加到最后结果之前先翻转为 [11, 7, 3] 再添加即可。
- 算法  
	- 初始化数组 result，用于存储最后结果。  
	- 使用一个外层循环遍历所有的对角线。第一行和最后一列的元素都是对角线的起点。**`for (int d = 0; d < rows + cols - 1; d++)`**
	- 使用一个内层 while 循环遍历对角线上的所有元素。可以计算指定对角线上的元素数量，也可以简单迭代直到索引超出范围。 
		```
		int row = d < cols ? 0 : d - cols + 1;  
		int col = d < cols ? d : cols - 1;
		```
	- 因为不知道每条对角线上的元素数量，需要为每条对角线分配一个列表或动态数组。但是同样也可以通过计算得到当前对角线上的元素数量。
		```
		while (row < rows && col > -1) {  
		  diagonalNodes.add(matrix[row][col]);  
		    ++row;  
		    --col;  
		}
		```  
	- 对于奇数编号的对角线，只需要将迭代结果翻转再加入结果数组即可 
		```
		if (d % 2 == 0) {  
		  Collections.reverse(diagonalNodes);  
		}
		for (Integer diagonalNode : diagonalNodes) {  
		  result[k++] = diagonalNode;  
		}
		```
#### Time: O(n * m)  
#### Space:O(min(m,n))
### what if output in one diagonal direction (easy)
- 只需要遍历起始点为 **`for (int d = 0; d < cols; d++)`**
- 每次起始点由一维变二维，因为都从第一行开始所以
	```
	int row = 0;  
	int col = d;
	```
- 直接将对角线遍历加入结果
	```
	while (row < rows && col > -1) {  
	  result[k++] = matrix[row][col];  
	    ++row;  
	    --col;  
	}
	```
### [547. Number of Provinces/# Friend Circles](https://leetcode.com/problems/number-of-provinces/)
#### BFS
- 通过广度优先搜索的方法得到省份的总数。对于每个城市，如果该城市尚未被访问过，则从该城市开始广度优先搜索, 直到同一个连通分量中的所有城市都被访问到, 即可得到一个circle
#### Time: O(n^2)
#### Space:O(n) 需要使用数组visited记录每个城市是否被访问过，数组长度是n，广度优先搜索使用的队列的元素个数不会超过n。
#### DFS 
- 遍历所有城市，对于每个城市，如果该城市尚未被访问过，则从该城市开始深度优先搜索
- 通过矩阵isConnected得到与该城市直接相连的城市有哪些，这些城市和该城市属于同一个连通分量，
- 然后对这些城市继续深度优先搜索，直到同一个连通分量的所有城市都被访问到，即可得到一个circle。
- 遍历完全部城市以后，即可得到连通分量的总数，即circle的总数。
### [1382. Balance a Binary Search Tree(Medium)](https://leetcode.com/problems/balance-a-binary-search-tree/)
### [88. Merge sorted array(Easy)](https://leetcode.com/problems/merge-sorted-array/)
### [15. 3Sum(Medium)](https://leetcode.com/problems/3sum/)
### [238. Product of Array Except Self(Medium)](https://leetcode.com/problems/product-of-array-except-self/)
#### Simulate
- for every i, **answer[i[ = product(left of nums[i]) * product(right of nums[i]) = product(L) * product(R)**
1.初始化 answer 数组，对于给定索引 i，answer[i] 代表的是 i 左侧所有数字的乘积L, 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1. start from index 1, **`answer[i] = nums[i - 1] * answer[i - 1];`**
2.用一个变量R来跟踪右边元素的乘积，R 为右侧所有元素的乘积，刚开始右边没有元素，所以 **`R = 1`**
	- 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 R, 并更新数组 **answer[i]=answer[i]∗R**。
	- R 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 R 上, R更新为 **`R=R∗nums[i]`**
#### Time: O(n)
#### Space:O(1)
### [304. Range Sum Query 2D - Immutable(Medium)](https://leetcode.com/problems/range-sum-query-2d-immutable/)
### [953. Verifying an Alien Dictionary(Easy)](https://leetcode.com/problems/verifying-an-alien-dictionary/)
#### Iterator
 - store the order in a hash using int[] array
 - 检查相邻单词 a 和 b 是否满足 a <= b
 - 为了检查相邻单词 a，b 是否满足 a <= b，只需要检查它们第一个不同的字母就可以了。例如，对于"applying" 和 "apples"，第一个不同的字母是 y 和 e。之后只需要比较这两个字母在 order 中的下标就可以了。
 - 还需要考虑两个单词长度不等的情况。例如，当比较 "app" 和 "apply" 的时候，前三个字母都是相等的，但 "app" 比 "apply" 更短，所以满足 a <= b。
### [560. Subarray Sum Equals K(Medium)](https://leetcode.com/problems/subarray-sum-equals-k/)
#### PrefixSum & HashMap
- 我们定义**pre[i]为[0..i]里所有数的和**，则pre[i]可以由pre[i−1]递推而来，即：**pre[i] = pre[i - 1] + nums[i]**
- 那么**[j..i] 这个子数组和为k**这个条件我们可以转化为 **pre[i] - pre[j-1] == k**
- 移项可得符合条件的下标j需要满足 **pre[j - 1] = pre[i] - k**
- 所以我们考虑**以i结尾的和为k的连续子数组**个数时只要统计**有多少个前缀和为pre[i]−k的pre[j]** 即可。
- 我们建立哈希表mp，以和为键，出现次数为对应的值，**记录pre[i]出现的次数**，从左往右边更新mp边计算答案
- 那么以i结尾的答案mp[pre[i]−k]即可在 O(1)时间内得到。最后的答案即为所有下标结尾的和为k的子数组个数之和。**`psc.put(0, 1)`**
- 由于pre[i]的计算只与前一项的答案有关，因此我们可以不用建立pre数组，直接用pre变量来记录pre[i−1]的答案即可。
	```
	int prefixSum = 0;  
	for (int num : nums) {  
	  prefixSum += num;  
	    result += psc.getOrDefault(prefixSum - k, 0);  
	    psc.put(prefixSum, psc.getOrDefault(prefixSum, 0) + 1);  
	}
	```
#### Time: O(n)
#### Space: O(n)
### [71. Simplify Path(Medium)](https://leetcode.com/problems/simplify-path/)
### [410. Split Array Largest Sum(Hard)](https://leetcode.com/problems/split-array-largest-sum/)
### [426. Convert the binary search tree into a sorted doubly linked list(Medium)](https://www.jiuzhang.com/solutions/convert-binary-search-tree-to-sorted-doubly-linked-list)
#### Inorder execution
1. 利用dummy node来保存头结点，这样就不需要对NULL进行判断了。**`TreeNode dummy = new TreeNode(0);`**
2. 用pre **`TreeNode pre = dummy;`** 来保存之前的节点，因为是中序遍历，所以刚好能把当前节点跟之前的节点连接起来。
``` 
private void inorder(TreeNode node) {  
  if (node == null) return;  
    inorder(node.left); 
    // connect pre and node so that pre <-> node
	// then assign node to pre 
    exec(node);  
    inorder(node.right);  
}
```
4. 中序遍历完之后，就是一个头尾暂时不相连的双向链表. 此时pre指向的尾节点，dummy的right指向头结点, 对收尾节点进行连接即可。
#### Time: 
#### Space: O(1)
### [347. Top K Frequent Elements(Medium)](https://leetcode.com/problems/top-k-frequent-elements/)
### Find median in BST without extra space
- 先找出bst的size, 然后再用inorder traversal 取第 k 个值.
### [863. All Nodes Distance K in Binary Tree](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)
### [278. First Bad Version(Easy)](https://leetcode.com/problems/first-bad-version/)
#### Binary Search
#### Time: O(logN)
#### Space:O(1)
### [621. Task Scheduler(Medium)](https://leetcode.com/problems/task-scheduler/)
#### Simulate
- 我们使用一个宽为n+1的矩阵可视化地展现执行A 的时间点。其中任务以行优先的顺序执行，没有任务的格子对应 CPU 的待命状态。
- 由于冷却时间为n，因此我们将所有的A 排布在矩阵的第一列，可以保证满足题目要求，并且容易看出这是可以使得总时间最小的排布方法，对应的总时间为即为求面积：**`(maxExec - 1)*(n+1) + 1`**
	```
	  <---n--->  
	 Annn...nnn  
	 A  
	 A  
	 A
	```
- 同理，如果还有其它也需要执行maxExec 次的任务，我们也需要将它们依次排布成列。例如，当还有任务B和C时，我们需要将它们排布在矩阵的第二、三列。如果需要执行maxExec次的任务的数量为maxCount，那么类似地可以得到对应的总时间为 **`(maxExec - 1)*(n+1) + maxCount`**
	```
	  <---n--->  
	 Annn...nnn  
	 ABC  
	 ABC  
	 ABC
	```
- 如果超过了n+1列则有 **`tasks.length > (maxExec - 1)*(n-1) + maxCount`**, e.g: ["A","A","A","B","B","B"] & 0
- 最后我们pick **`max((maxExec - 1)*(n-1) + maxCount, tasks.length)`**
#### Time: O(number of tasks + number of different task categories)
#### Space: O(number of different task categories)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM2NzQ5Mjc2NywxODcwNzI3NDgwLDExMj
kwNDk1NCwyMTEzODk5NDY1LDEyNzY5ODMyMzAsMTg2MTcxNDM1
NCwtMTcyNjg5NTUyMCw1MjgxNTQ2ODcsLTEzMTI2MjIzODIsLT
EyMDgwMzIwNjUsLTUwMzU4NDU5NCw1MjgyMDI1NzAsLTE4MzU2
MDI5NTEsMTYxMDk1MTk5NiwtMjI3NDE0Njg3LC01Mjg5MDAyNj
IsLTE3MjUxNzYwMzcsNDA3NzE1NjY2LC03MjI0MTY4MzUsNDkw
ODc2NjUyXX0=
-->