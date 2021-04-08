### [149. Max Points in one Line (Hard)](https://leetcode.com/problems/max-points-on-a-line/)
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
##### Time: O(n*m)
##### Space:O(n*m)
### [234. Palindrome Linked List(Easy)](https://leetcode.com/problems/palindrome-linked-list/)
#### Find Mid and Reverse 2nd half
##### Time: O(n)
##### Space:O(1)
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
##### Time: O(logK*n) - offer/poll of Heap is logK
##### Space:O(K)
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
##### Time: O(mlogm+nlogn)
##### Space:O(logm+logn) 空间复杂度主要取决于排序使用的额外空间。
### [26. Remove Duplicates from Sorted Array(Easy)](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
1. 要求每个元素至少出现1次，即数组大小至少得从`2`开始遍历有意义, **`validation about null/length = 0/length < 2`**
2. Initiate count = 1, 即前1个元素不用管, 从第2个元素（以1为下标）开始read，每次和之前（read - 1）的元素比较, read每次移一步，包括遇到相同的元素
3. 遇到不同的元素时，此时read已经跳过之前在[count - 1 ~ read - 1]区间内重复的元素,  写入那个元素并递增count
5. 最后数组下标 0 - count 部分即为去重后的数组,数组长度为count
##### Time: O(n)
##### Space:O(1)
#### [Follow up](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/): Remove duplicate that only allow duplicate appears at k times
1. 要求每个元素至少出现1次，即数组大小至少得从`k+1`开始遍历有意义, **`validation about null/length = 0/length < k`**
2. Initiate count = k, 即前k个元素不用管, 从第k+1个元素(以k为下标)开始read，每次和之前的元素比较, read每次移一步，包括遇到相同的元素
3. compare current iteration element with its prev (read - k) index value
	- if the values are equal, do nothing and move forward
	- otherwise, replace with array[count] once different value found, count++
	- initially the 1st different value will be replaced at array[k]
4. eventually array element from index 0 to count - 1 will be replaced with number of k times of
##### Time: O(n)
##### Space:O(1)
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
##### Time: O(n/4)
##### Space:O(1)
### [158. Read-n-characters-given-read4-stream-II](https://aaronice.gitbook.io/lintcode/data_structure/read-n-characters-given-read4-ii-call-multiple-times)
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
##### Time:O(bracket.size())
##### Space:O(1)
### [451. Sort Characters By Frequency(Medium)](https://leetcode.com/problems/sort-characters-by-frequency/) 
#### HashMap & PriorityQueue
1. 根据 s 构造 HashMap, 因为面向字母所以我们可以用int[]来存储每个character出现的个数,下标可以直接(char[i])
2. 然后我们构造一个priority queue/maxHeap用来排序, heap top store max appearance character,with comparetor can be `(a, b) -> frequency[b] - frequency[a]`
3. 把所有frequency中不为0的character进队列, 此时队列里面最顶端为出现次数最大的character
4. 使用StringBuilder构造队列里面一个个poll出来的character组成String, 每次都decrement frequency[ch]
##### Time: O(n)
##### Space:O(n)
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
##### Time: O(n)
##### Space:  O(A)，这里 A 是输入字符串 `S` 出现的字符 ASCII 值的范围
### [1305. All Elements in Two BST(Medium)](https://leetcode.com/problems/all-elements-in-two-binary-search-trees/)
#### BST Inorder & Merge 2 sorted arrays
- 这两棵树都是二叉搜索树（BST），而一颗BST中序遍历的结果就是排好序的
1. 新建两个list，分别对两棵树进行中序遍历得到分别排好序的list1，list2;
2. 已知list1和list2有序，那么将二者归并即可的到一个排好序的总list。
##### Time: O(m+n) 
- 其中m和n是两棵树中的节点个数。中序遍历的时间复杂度为O(m+n)，归并排序的时间复杂度同样为O(m+n)。
##### Space:O(m+n)
- 我们需要使用额外的空间存储数组 v1 和 v2
### follow up - what about Binary Tree
1. Preorder/Inorder traversal
2. Sort List
##### Time: O((m+n)*log(M+N)) 
##### Space:O(Hm + Hn + log(M+N)
### [1428. Leftmost Column with at Least a One(Medium)](https://www.cnblogs.com/cnoodle/p/12759214.html)
#### Binary Search
- 对于本题，由于每一行上的数字已经按照升序排列，因此我们可以通过二分查找的方法找到每一行上第一个大于等于1的数字
- 在找到每一行中第一个1所在的列位置之后，我们只要找到一个最小列即是本题的结果。
	```
	int findFirstOne(BinaryMatrix binaryMatrix, int row){  
	  int low = 0,high = cols - 1;  
	    while(low <= high){  
	  int mid = low + (high - low) / 2;  
	        if(binaryMatrix.get(row, mid) == 1){  
	  high = mid - 1;  
	        }else{  
	  low = mid + 1;  
	        }  
	 }  return low;  
	}
	```
##### Time: O(row*logCol)
##### Space:(1)
### [987. Vertical Order Traversal of a Binary Tree(Hard)](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/)
#### Coordinate Class and DFS
-  该解决方案有两个步骤：首先，找出每个节点所在的坐标，然后报告他们的坐标。
1. Create a class `Coordinate` to store `x`, `y` and `val`, also implement the `compareTo` by compare `x` first then `y` then `val`
	```
	  @Override  
	  public int compareTo(Coordinate that) {  
	  if (this.x != that.x)  
	  return Integer.compare(this.x, that.x);  
	        else if (this.y != that.y)  
	  return Integer.compare(this.y, that.y);  
	        else  
	 return Integer.compare(this.val, that.val);  
	    }  
	}
	```
2. 我们可以使用深度优先搜索找到每个节点的坐标。保持当前节点 (x, y)，移动的过程中, 坐标变化为左孩子(x-1, y+1) 或 右孩子(x+1, y+1), e.g `(0,0),(1,-1),(1,1),(2,0),(2,2)`
3.  我们通过 x 坐标排序，再根据 y 坐标排序，这样确保以正确的顺序添加到答案中 e.g:`(-1,1),(0,0),(0,2),(1,1),(2,2)`
4. For each coordinate, we insert into results by **`ans.get(ans.size() - 1).add(coordinate.val); `**
5. To identify when new list needed depend on coordinate x, declare a `prev` to indicate current `x`, iterate through all coodinates if **`prev != current.x`**, meaning we'll create a new list to store coordinate in new `x`, 
##### Time: O(N) N 是树中节点个数
##### Space:O(N)
### [863. All Nodes Distance K in Binary Tree](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)
#### DFS HashMap of parent & BFS visited
- 如果节点有指向父节点的引用，也就知道了距离该节点 1 距离的所有节点。之后就可以从 target 节点开始进行广度优先搜索了。
- DFS对所有节点添加一个指向父节点的引用，之后做BFS，找到所有距离 target 节点 K 距离的节点。
1. DFS用一个`Map<TreeNode, TreeNode>` 存储每个节点与其父节点的mapping
	```
	public static void dfs(TreeNode node, TreeNode par) {  
	  if (node != null) {  
	  parent.put(node, par);  
	        dfs(node.left, node);  
	        dfs(node.right, node);  
	    }  
	}
	```
2. BFS以target 节点为中心展开, 先放入null再放入target, 
	- 每当queue pop是null时来表示以target node为中心的即将走到当前深度为dist的所有结点，并比较当前一轮BFS走过的距离是否等于k，
		- 若等于，则把剩余queue中节点加进去，
		- 若不等则重新加入null并`dist++`
	- 若pop不是null则说明应该继续BFS遍历以当前pop节点为中心的左右父三个结点，加入queue之前用Set<TreeNode> 来过滤掉已经visited过的下一个可能的结点
##### Time: O(N) N 是树中节点个数
##### Space:O(N)
### [102. Binary Tree Level Order Traversal(Medium)](https://leetcode.com/problems/binary-tree-level-order-traversal/)
#### BFS
- use `queue.size()` to identify how many nodes in current level
- then only iterative the queue.size() node in current queue, adding their child nodes continuously
##### Time: O(n)
##### Space:O(n)
### [515. Find Largest Value in Each Tree Row(Medium)](https://leetcode.com/problems/find-largest-value-in-each-tree-row/)
#### BFS
Like level ordering
##### Time: O(n)
##### Space:O(n)
### [173. Binary Search Tree Iterator(Medium)](https://leetcode.com/problems/binary-search-tree-iterator/)
#### Stack
- Initialization: put root and all its leftmost nodes into stack
- next: 
	- pop() from stack
	- check if poped node has right child, if it has, put its right child and the leftmost nodes into stack
	```
	TreeNode node = stack.pop();  
	// push all left path of right subtree  
	TreeNode right = node.right;  
	while (right != null) {  
	  stack.push(right);  
	    right = right.left;  
	}
	```
- hasNext(): equals to if `stack.isNotEmpty()`
#### Time: O(n)
#### Space:O(h) 
- worst case: O(n)
### [199. Binary Tree Right Side View(Medium)](https://leetcode.com/problems/binary-tree-right-side-view/)
#### BFS
- 利用广度优先搜索进行层次遍历，记录下每层的最后一个元素。
- 左结点排在右结点之前，这样，我们对每一层都从左到右访问。因此，只保留每个深度最后访问的结点，我们就可以在遍历完整棵树后得到每个深度最右的结点
```
int size = queue.size();  
for (int i = 0; i < size; i++) {
	...
	if (i == size - 1) { //将当前层的最后一个节点放入结果列表  
	  res.add(node.val);  
	}
}
```
##### Time: O(n)。 每个节点最多进队列一次，出队列一次，因此广度优先搜索的复杂度为线性。
##### Space:O(n)。每个节点最多进队列一次，所以队列长度最大不不超过n，所以这里的空间代价为O(n)。
### [235. Lowest Common Ancestor of a Binary Search Tree(Easy)](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)
#### Traversal
- 我们从根节点开始遍历；**`TreeNode ancestor = root; while(true)`** 
- 如果当前节点的值大于 pp 和 qq 的值，说明 pp 和 qq 应该在当前节点的左子树，因此将当前节点移动到它的左子节点；**`if (ancestor.val > p.val && ancestor.val > q.val) ancestor = ancestor.left;`** 
- 如果当前节点的值小于 pp 和 qq 的值，说明 pp 和 qq 应该在当前节点的右子树，因此将当前节点移动到它的右子节点; **`else if (ancestor.val < p.val && ancestor.val < q.val) ancestor = ancestor.right;`**
- 如果当前节点的值不满足上述两条要求，那么说明当前节点就是「分岔点」。此时，pp 和 qq 要么在当前节点的不同的子树中，要么其中一个就是当前节点。**`else break;`**
##### Time: O(n) n 是给定的二叉搜索树中的节点个数  
##### Space:O(1)
### [236. Lowest Common Ancestor of a Binary Tree(Medium)](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/)
#### DFS HashMap of parent and visited
1. 我们可以用哈希表存储所有节点的父节点，  
2. 然后我们就可以利用节点的父节点信息从 p 结点开始不断往上跳，并记录已经访问过的节点，  
3. 再从 q 节点开始不断往上跳，  
4. 如果碰到已经访问过的节点，那么这个节点就是我们要找的最近公共祖先。  
##### 算法  
- 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针。  
- 从 p 节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点。  
- 同样，我们再从 q 节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是 p 和 q 的深度最深的公共祖先，即 LCA 节点。  
##### Time: O(n) 二叉树的所有节点有且只会被访问一次，从 p 和 q 节点往上跳经过的祖先节点个数不会超过 NN  
##### Space:O(n) 其中N是二叉树的节点数。递归调用的栈深度取决于二叉树的高度，二叉树最坏情况下为一条链，此时高度为N,哈希表存储  
* 每个节点的父节点也需要O(N)的空间复杂度
### [510. Inorder Successor in BST II(Medium)](https://junhaow.com/lc/problems/tree/bst/510_inorder-successor-in-bst-ii.html/)
#### Node has parent
- Case 1: if node right child is not null -> go to its right child then leftmost of that is the successor
	```
	TreeNode p = x.right;  
	while(p!=null){  
	  result = p;  
	    p = p.left;  
	}  
	if(result != null){  
	  return result;  
	}
	```
- Case 2: if node right child is null -> go up to the parent until the node is a left child, return parent
	```
	while(p!=null){  
	  if(p.parent!=null && p.parent.left == p){  
	  return p.parent;  
	    }  
	  p = p.parent;  
	}
	```
#### Node doesn't have parent
1. keep find p from root so that root = p and successor is p's parent,  set successor to be p's parent node for now
	```
	TreeNode successor = null;   
	// keep find p from root so that root = p and successor is p's parent  
	// set successor to be p's parent node for now  
	while (root != null && root != p) {  
	  if (root.val > p.val) {  
	  successor = root;  
	        root = root.left;  
	    } else {  
	  root = root.right;  
	    }  
	}
	```
2. if root not found return null
3. if root found but no right node, then its parent is the successor
	```
	if (root.right == null) {  
	  return successor;  
	}
	```
4. if root found and has right node, then the left most of its right node is the successor
	```
	root = root.right;  
	while (root.left != null) {  
	  root = root.left;  
	}
	```
##### Time: O(logN) worst O(n)
##### Space:O(1)
### [124. BinaryTreeMaximumPathSum (Hard)](https://leetcode.com/problems/binary-tree-maximum-path-sum/)
#### Divide & Conquer
- 考虑实现一个简化的函数helper,该函数计算二叉树中的一个节点的最大贡献值,  具体而言，该函数的计算如下。
	- 空节点的最大贡献值等于0。
	- 非空节点的最大贡献值等于节点值与其子节点中的最大贡献值之和（对于叶节点而言，最大贡献值等于节点值。  
- 根据函数helper得到每个节点的最大贡献值之后，如何得到二叉树的最大路径和？
- 对于二叉树中的一个节点，该节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值 `return node.val + Math.max(leftMax, rightMax);`
- 如果子节点的最大贡献值为正，则计入该节点的最大路径和，否则不计入该节点的最大路径和。`leftMax = Math.max(helper(node.left), 0);`
- 维护一个全局变量 maxSum 存储最大路径和，在递归过程中更新 maxSum 的值 `maxSum = Math.max(maxSum, node.val + leftMax + rightMax);`
- 最后得到的 maxSum 的值即为二叉树中的最大路径和。
#### follow up - nary
- 做法是找出他们n个children中最大的两个sum，剩下的与要而思一个做法。
### [1382. Balance a Binary Search Tree(Medium)](https://leetcode.com/problems/balance-a-binary-search-tree/)
#### Inorder & build
- 我们可以通过中序遍历将原来的二叉搜索树转化为一个有序序列，然后对这个有序序列递归建树，对于区间[L, R]  
	 - 取mid=(L+R)/2, 即中心位置作为当前节点的值  
	 - 如果L<=mid-1,那么递归地将区间[L, mid-1]作为当前节点的左子树  
	 - 如果mid+1<=R,那么递归地将区间[mid+1, R]作为当前节点的右子树  
##### Time: O(n) 获得中序遍历的时间代价  
##### Space:O(n) 这里使用了一个数组作为辅助空间，存放中序遍历后的有序序列
#### [Is Balanced Binary Tree](https://leetcode.com/problems/balanced-binary-tree/submissions/)
### [283. Move Zeroes(Easy)](https://leetcode.com/problems/move-zeroes/)
####  Two Pointers
- 使用两个指针right和left
	1. 将两个指针先指向0，即数组头部
	2. right向后扫描 **`right++`**，当遇到非0数即 **`nums[right] != 0`**时，将其赋值给left指针指向的位置，即**`nums[left] = nums[right]`**，并将left向后移动一位 **`left ++`**
	3. 若left指针还未指向尾部 **`while(left < length)`**，即剩余的位置都是0，将剩余数组赋值为0 **`nums[left] = 0`**
##### Time: O(n)
##### Space:O(1)
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
##### Time: O(n)
##### Space:O(1)
### [1053. Previous Permutation With One Swap(Medium)](https://leetcode.com/problems/previous-permutation-with-one-swap/)
- 一次交换后字典序就变小，交换的两个数，肯定原先是大数在前，小数在后。交换后，小数换到前面来，大数换到后面去。那么有
	1. 从后向前查找第一个顺序对 (ascStart,ascStart+1)，满足 **`if (nums[ascStart] > nums[ascStart + 1])`**, 这样「较大数」即为 a[ascStart]。**此时 [ascStart+1,n) 必然是上升序列**
	2. 寻找在 nums[ascStart] 最右边且小于 A[ascStart] 的最大的数字 nums[smallerAscStart], 由于 **`nums[smallerAscStart] < nums[ascStart]`**, 交换 nums[ascStart] 与 nums[smallerAscStart] 后的序列字典序一定小于当前字典序
		- 注意:  the second check to skip duplicate numbers **`if (..
	&& nums[smallerAscStart] != nums[smallerAscStart - 1])`**
		- swap(nums, ascStart, smallerAscStart); 序列字典序一定小于当前字典序
##### Time: O(n)
##### Space:O(1)
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
##### Time: O(n * m)  
##### Space:O(min(m,n))
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
##### Time: O(n^2)
##### Space:O(n) 需要使用数组visited记录每个城市是否被访问过，数组长度是n，广度优先搜索使用的队列的元素个数不会超过n。
#### DFS 
- 遍历所有城市，对于每个城市，如果该城市尚未被访问过，则从该城市开始深度优先搜索
- 通过矩阵isConnected得到与该城市直接相连的城市有哪些，这些城市和该城市属于同一个连通分量，
- 然后对这些城市继续深度优先搜索，直到同一个连通分量的所有城市都被访问到，即可得到一个circle。
- 遍历完全部城市以后，即可得到连通分量的总数，即circle的总数。
### [88. Merge sorted array(Easy)](https://leetcode.com/problems/merge-sorted-array/)
#### Two Pointer
- 涉及两个有序数组合并,设置i和j双指针,分别从两个数组的尾部想头部移动,并判断Ai和Bj的大小关系,从而保证最终数组有序, **`while (i >= 0 && j >= 0) {  
  nums1[k--] = nums1[i] >= nums2[j] ? nums1[i--] : nums2[j--];  
}`**
- Check if either of the array has left elements, keep insert into result
##### Time: O(m + n)
##### Space:O(1)
### Follow up: Merge K Sorted Array
#### Divide and Conquer
1. divide total list left and right until 2 or 1 sorted array with index from of 0 ~ list.length
2. merge 2 sorted arrays and up
	```
	private static int [] divide(List<int[]> lists, int start, int end) {  
	  if (end - start == 1) {  
	  return merge(lists.get(start), lists.get(end));  
	    }  
	  if (end - start == 0) {  
	  return lists.get(start);  
	    }  
	  int mid = start + (end-start)/2;  
	    int [] left = divide(lists, start, mid);  
	    int [] right = divide(lists, mid+1, end);  
	    return merge(left, right);  
	}
	```
##### Time: O(kn×logK)
##### Space: O(logK) 递归会使用到的栈空间
### [23. Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/)
#### Divide and Conquer
1. divide total list left and right until 2 or 1 sorted array with index from of 0 ~ list.length
2. merge 2 sorted lists and up
```
public ListNode divide(ListNode[] lists, int start, int end) {  
  if (start > end) {  
  return null;  
    }  
  if (start == end) {  
  return lists[start];  
    }  
  int mid = start + (end-start)/2;  
    ListNode left = divide(lists, start, mid);  
    ListNode right = divide(lists, mid + 1, end);  
    return merge(left, right);  
}
```
##### Time: O(kn×logK)
##### Space: O(logK) 递归会使用到的栈空间 
### [15. 3Sum(Medium)](https://leetcode.com/problems/3sum/)
#### Two Pointers
- a + b + c = 0 ==> a + b = -c
1. Sort Array
2. 1st element can be loop through 0 ~ n with deduplicate
3. 2nd and 3rd element can be found through 2Sum using 2 Pointers facing direction
	- need deduplicate 2nd and 3rd when sum == target(-first) before adding into result **`while (secondPtr + 1 < thirdPtr && nums[secondPtr+1] == nums[secondPtr]) secondPtr++;`**
	- if sum > target, move right pointer to left
	- else move left pointer to right
##### Time: O(N^2)
##### Space:O(logN) 我们忽略存储答案的空间，额外的排序的空间复杂度为O(logN), 然而我们修改了输入的数组nums，在实际情况下不一定允许，因此也可以看成使用了一个额外的数组存储了nums的副本并进行排序空间复杂度为O(N)。
### [16. 3Sum Closest](https://leetcode.com/problems/3sum-closest/)
#### Two Pointers
1. 第一步，对数组进行排序。只有将数组转化为有序数组，我们才方便移动双指针。  
2.* 第二步，在数组numbers中遍历，每次固定numbers[i]作为第一个数  
3. 建立双指针left和right，初始化分别指向i + 1和len(numbers) - 1  
4. 求出此时的三数之和sum，如果sum和target恰好相等，我们可以直接返回target。  
5. 比较sum和closest谁距离target更近，如果是sum，那么将closest更新为target   **`if (Math.abs(sum - target) < Math.abs(closest - target)) { closest = sum; }`**
6. 判断sum和target的大小关系，如果sum > target，那么right左移；反之，left右移。继续第二步的过程，直到left >= right。  
7. 此外，当数组中有重复元素时，为了避免重复运算，在代码中添加了三处剪枝操作。当指针指向新的位置和旧的位置的值相等时，我们继续移动指针。  
##### Time：O(n^2)  
##### Space:O（1），只需要常量空间。
### [18. 4Sum](https://leetcode.com/problems/4sum/)
#### One more loop with 3Sum
##### Time:  O(N^3)
##### Space:O(N^2)
#### DFS & Backtrack
1.对数组排序
2. 对排序后的队列进行搜索每次选取当前数后的比当前值大的数压入List，当List大小为4的时候判断是否四个元素的和为target
```
private void dfs(int[] nums, int startIndex, int remainTarget, List<Integer> list, List<List<Integer>> results) {  
  // exit  
  if (list.size() == 4 && remainTarget == 0) {  
  results.add(new ArrayList<>(list));  
        return;  
    }  
  for (int i = startIndex; i < nums.length; i++) {  
  // deduplicate  
  if (i != startIndex && nums[i] == nums[i-1]) {  
  continue;  
        }  
  // 选取当前元素  
  list.add(nums[i]);  
        dfs(nums,i+1, remainTarget-nums[i], list, results);  
        // backtracking  
  list.remove(list.size() - 1);  
    }  
}
```
##### Time:  O(N^3)
##### Space:O(N^2)
### [200. Number of Islands](https://leetcode.com/problems/number-of-islands/)
#### BFS
- define a visited[][] to indicate if node has been visited
- define a Coordinate class with x and y
- define a direction array of `dx = {0, 1, -1, 0}, dy = {1, 0, 0, -1};`
- BFS for each node which has not been visited, each node would go 4 directional, continue if new node is invalid by checking
	- if new node coordinate is out of matrix
	- if new node has been visisted already
	- if new node is not an island
##### Time: O(mn)
##### Space:O(mn)
### [827. Making a Large Island(Hard)](https://leetcode.com/problems/making-a-large-island/)
### [238. Product of Array Except Self(Medium)](https://leetcode.com/problems/product-of-array-except-self/)
#### Simulate
- for every i, **answer[i[ = product(left of nums[i]) * product(right of nums[i]) = product(L) * product(R)**
1.初始化 answer 数组，对于给定索引 i，answer[i] 代表的是 i 左侧所有数字的乘积L, 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1. start from index 1, **`answer[i] = nums[i - 1] * answer[i - 1];`**
2.用一个变量R来跟踪右边元素的乘积，R 为右侧所有元素的乘积，刚开始右边没有元素，所以 **`R = 1`**
	- 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 R, 并更新数组 **answer[i]=answer[i]∗R**。
	- R 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 R 上, R更新为 **`R=R∗nums[i]`**
##### Time: O(n)
##### Space:O(1)
### [304. Range Sum Query 2D - Immutable(Medium)](https://leetcode.com/problems/range-sum-query-2d-immutable/)
#### PrefixSum
- 初始化时对矩阵的每一行计算前缀和`int[][] prefixSum = new int[rows][cols + 1]`, 将sums的列数设为n+1的目的是为了方便计算每一行的子数组和，不需要对col1= 的情况特殊处理
	- 对于0≤i<n都有 prefixSum[row][i+1]=prefixSum[row][i]+matrix[row][i]，  
	- 则当0<i≤n时，prefixSum[row][i]表示matrix从下标[row][0]到下标[row][i-1]的前缀和,  
	- prefixSum[i+1]表示matrix从下标[row][0]到下标[row][i]的前缀和,
	```
	for (int x = 0; x < rows; x++) {  
	  for (int y = 0; y < cols; y++) {  
	  // prefixSum 从第二行开始计算，类似一维数组  
	  prefixSum[x][y + 1] = prefixSum[x][y] + matrix[x][y];  
	    }  
	}
	```
- 检索时对二维区域中的每一行计算子数组和，然后对每一行的子数组和计算总和。
	```
	for (int row = row1; row <= row2; row++) {  
	  sum += prefixSum[row][col2 + 1] - prefixSum[row][col1];  
	}  
	return sum;
	```
##### Time: O(mn)
##### Space:O(mn) - 需要创建一个m行n+1列的前缀和数组
#### 1D array
```
int[] prefixSum = new int[n + 1];
for (int i = 0; i < n; i++) {  
  prefixSum[i + 1] = prefixSum[i] + nums[i];  
}
sumRange(i, j) = prefixSum(j + 1) - prefixSum(i)
```
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
##### Time: O(n)
##### Space: O(n)
### [71. Simplify Path(Medium)](https://leetcode.com/problems/simplify-path/)
- Split("/"), 遇到.. pop， 遇到string push， 其余情况不用管
	```
	for(String str: path.split("/+")){  
	  if(!str.equals("..") && !str.equals(".") && !str.equals("")){  
	  stack.push(str);  
	    }  
	  if(str.equals("..") && !stack.isEmpty()){  
	  stack.pop();  
	    }  
	}
	```
- 遍历完成后把所得到的stack 反着拼接即可
	```
	while(!stack.isEmpty()){  
	  sb.insert(0, stack.pop());  
	    sb.insert(0, "/");  
	}
	String res = sb.toString();
	```
- 最后要考虑所处在根目录下要有个"/"
	```
	if(res.equals("")){  
	  return "/";  
	}
	```
### [410. Split Array Largest Sum(Hard)](https://leetcode.com/problems/split-array-largest-sum/)
#### Greedy & Binary Search
- 这样我们可以用二分查找来解决。二分的上界right为数组 nums 中所有元素的和，下界left为数组 nums 中所有元素的最大值。
- 我先猜一个mid值，然后遍历数组划分，使每个子数组和都最接近mid（贪心地逼近mid），这样我得到的子数组数一定最少;
	- 如果即使这样子数组数量仍旧多于m个，那么说明划分的子数组多了,明显说明我mid猜小了，因此 left = mid + 1;
	- 如果得到的子数组数量小于等于m个，那么说明划分的子数组少了,我可能会想，mid是不是有可能更小？值得一试。因此 right = mid;
- 贪心地模拟分割的过程，从前到后遍历数组，用 sum 表示当前分割子数组的和，cnt 表示已经分割出的子数组的数量（包括当前子数组），那么每当 sum 加上当前值超过了x，我们就把当前取的值作为新的一段分割子数组的开头，并将cnt 加 1。遍历结束后验证是否 cnt 不超过 m
##### Time: O(n*log(sum-max of n)) 其中 sum 表示数组 nums 中所有元素的和，maxn 表示数组所有元素的最大值。每次二分查找时，需要对数组进行一次遍历，时间复杂度为 O(n)
##### Space:O(1)
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
3. 中序遍历完之后，就是一个头尾暂时不相连的双向链表. **此时pre指向的尾节点，dummy的right指向头结点**, 对收尾节点进行连接即可, **`connect pre and dummy.right which is head node`**
	- e.g dummy's right is 1. pre is 5 when tree is [4,2,5,1,3], 
##### Time: 
##### Space: O(1)
### [347. Top K Frequent Elements(Medium)](https://leetcode.com/problems/top-k-frequent-elements/)
### Find median in BST without extra space
- 先找出bst的size, 然后再用inorder traversal 取第 k 个值.
### [278. First Bad Version(Easy)](https://leetcode.com/problems/first-bad-version/)
#### Binary Search
##### Time: O(logN)
##### Space:O(1)
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
##### Time: O(number of tasks + number of different task categories)
##### Space: O(number of different task categories)
### [67. Add Binary(Easy)](https://leetcode.com/problems/add-binary/)
#### Traversal
- 整体思路是将两个字符串较短的用 00 补齐，使得两个字符串长度一致，然后从末尾进行遍历计算并插入结果首位，得到最终结果
- 按照位置给结果字符赋值，最后如果有进位，则在前方进行字符串拼接添加进位
- declare carry = 0; every iteration sum = last carry;
```
// in loop
ans.insert(0, sum % 2); // 如果二者都为1 那么sum%2应该刚好为0 否则为1
arry = sum / 2;   //如果二者都为1 那么carry应该刚好为1 否则为0
// finally
ans.insert(0, carry == 1 ? carry : "");// 判断最后一次计算是否有进位  有则在最前面加上1 否则原样输出
```
##### Time: O(n)
### [39. Combination Sum(Medium)](https://leetcode.com/problems/combination-sum/)
#### DFS & Backtrack
- Sort array 减少回溯次数
- 定义递归DFS为找到所有以combination开头的那些和为target的组合（出口），并丢到results里面。
	- 拆解）如果当前sum小于或等于target，继续dfs，否则中止。
	- 并且下一个可以加入combination中的数 至少从candidate的startIdx开始，因为结果允许重复
```
private static void dfs(
  int[] candidates,  
  int target,  
  int begin,  
  int sum,  
  List<Integer> combination)
```
##### Time: O(S) 其中 SS 为所有可行解的长度之和
##### Space:O(target) 空间复杂度取决于递归的栈深度，在最差情况下需要递归O(target)层。
### [40. Combination Sum II(Medium)](https://leetcode.com/problems/combination-sum-ii/)
#### DFS & Backtrack
- 与39不同的是结果不允许重复
- - Sort array 减少回溯次数
- 定义递归DFS为找到所有以combination开头的那些和为target的组合（出口），并丢到results里面。
```
private static void dfs(
  int[] candidates,  
  int target,  
  int begin,  
  int sum,  
  List<Integer> combination) {
```
	- 拆解）如果当前sum小于或等于target，继续dfs，否则中止。
	- 并且下一个可以加入combination中的数 至少从candidate的startIdx + 1开始，因为结果不允许重复，由于数组可能有重复元素，我们需要再每次选择sum时去重 `if(i > begin && candidates[i] == candidates[i-1]) continue;`
### [216. Combination Sum III(Medium)](https://leetcode.com/problems/combination-sum-iii/)
- 定义递归DFS为找到所有以combination开头的那些和为target的组合（出口），并丢到results里面。
	```
	private static void dfs(
		int k, 
		int target, 
		int begin, 
		int sum, 
		List<Integer> combination)
	```
	- 拆解）如果当前sum小于或等于target，继续dfs，否则中止。
	- 并且下一个可以加入combination中的数 至少从candidate的startIdx + 1开始，因为结果不允许重复
##### Time: O(C(M/k) * k) 本题中M固定为9, 一共有C(M/k)个组合，每次判断需要的时间代价是O(k)
##### Space:O(M) 递归的栈深度
### [114. Flatten Binary Tree to Linked List(Medium)](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/)
#### PreOrder & Stack
-  前序遍历的具体做法是，每次从栈内弹出一个节点作为当前访问的节点，获得该节点的子节点，  
- 如果子节点不为空，则依次将右子节点和左子节点压入栈内（注意入栈顺序）。  
- 展开为单链表的做法是，维护上一个访问的节点 prev，  
	* 每次访问一个节点时，令当前访问的节点为 curr，将 prev 的左子节点设为 null 以及将 prev 的右子节点设为 curr，  
	* 然后将 curr 赋值给 prev，进入下一个节点的访问，直到遍历结束。  
	* 需要注意的是，初始时 prev 为 null，只有在 prev 不为 null 时才能对 prev 的左右子节点进行更新。  
##### Time: O(n)  
##### Space:O(n)
### [438. Find All Anagrams in a String(Medium)](https://leetcode.com/problems/find-all-anagrams-in-a-string/)
####  滑动窗口 + 双指针  
* 除了直接比较数组是否相等外，其实还可以用双指针来表示滑动窗口的两侧边界，  
1. 定义滑动窗口的左右两个指针left，right  
2. right一步一步向右走遍历s字符串  
3. right当前遍历到的字符加入s_cnt后不满足p_cnt的字符数量要求，将滑动窗口左侧字符不断弹出，也就是left不断右移，直到符合要求为止。  
4. 当滑动窗口的长度等于p的长度时，这时的s子字符串就是p的异位词。  
- 其中，left和right表示滑动窗口在字符串s中的索引，cur_left和cur_right表示字符串s中索引为left和right的字符在数组中的索引  
##### Time: O(n)  
##### Space:O(1)
### [297. Serialize and Deserialize Binary Tree(Hard)](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)
### [127. Word Ladder(Hard)](https://leetcode.com/problems/word-ladder/)
### [126. Word Ladder II(Hard)](https://leetcode.com/problems/word-ladder/)
### [139. Word Break(Hard)](https://leetcode.com/problems/word-break/)
### [140. Word Break II(Hard)](https://leetcode.com/problems/word-break-ii/)

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTk2OTIzNTc3NSw1MTA2OTA2MTcsMTc2OD
IyMDk1LC0xMjg3OTMwNjQwLC0xMjI1MDg1MjQyLC01MjczMDAy
OTAsLTE4MjYxMTUyNDQsLTEzOTI4NzI5NCwtMTQ4MTQxMzg0NS
wtNTcxMTcwMjIzLC02Mzg2MDU1OTIsLTE3NDkxNDg1MTYsLTg1
ODY5MDU0OCwtMTc3NTg0NDI5MSwtMTkyOTQxODc2NCwxMDQ0Nz
ExOTU4LC0xODg5ODc0NjI2LC0xNTYwMjkxODY2LDExNTcwMTQ3
MjgsMTY5Mzg0MjgxNl19
-->