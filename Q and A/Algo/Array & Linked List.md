### Summary
#### Validation
 - check if `null or length`
	 - if input as `String, Array`
 - 
#### HashMap
- deal with `Character Array` or `String.toCharArray()` that needs to check appearance
	- use `int count = int[26]` to store character appearance count `count[ch - 'a'] ++`
###  Anagrams
- Check if Anagrams
	I. **Sort** String Characters 
	II. using **int[26] count as map** to store number of character appearance count in a String, `count[ch - 'a'] ++`
- Result as HashMap
	- Put sorted result into Map as Key and value as List<String>
- Time: K is count of input Strings
	- O(K * nLogN)  if sorted
	- O(K* N) if using char[] as dictionary
- Space: (K * N)
### Interval/Sweepline
- Sort List by start `Arrays.sort(intervals, (a, b) -> a.start - b.start)`
- Interater the list
	- Check if conflict
		1. define`maxEnd = Math.max(maxEnd, interval.end)` to maintain current interval end
		2. compare with each incoming interval's start `interval.start < maxEnd`
	- Merge if conflict
		1. define`existedInterval` as 1st element/null in sorted list to maintain an interval that could be cut off
		2. compare with each incoming interval by using `prev == null || existedInterval.end < incomingInterval.start`
			- if true, meaning existedInterval can be cut off, add into result `result.add(existedInterval)` and update `existedInterval = incomingInterval`
			- if false, meaning those two intervals can be merged, update `existedInterval.end = Math.max(existedInterval.end, incomingInterval.end)`
				-  if `existedInterval` has already been added into result, update its end will also update the added interval's end in result, e.g(we're adding the 1st interval in result as `existedInterval is null` initially)
	- Insert if conflict
		1. Find insert index in List by comparing `while(idx < intervals.size() && intervals.get(idx).start < newInterval.start) { idx ++; }`
		2. Do merge again
	- Find missing interval
		1. use helper method `addRange(result, lower, upper)` to add each interval into result
		2. add head interval as lower to head `nums[0] - 1` as 
		3. iterator from 2nd element to end of list, add each `addRange(result, nums[i - 1] + 1, nums[i] - 1)`
		4. add tail interval as `addRange(ans, nums[nums.length - 1] + 1, upper)`
	- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwODM0MDk4OTQsMzc1NjI4MjE3LC0xOD
UzNTE0ODY0LC0yMzM2NjM5NzUsMjkwNDYzOTUsLTE1NjI1OTI4
NzAsLTUwMDM1ODExNV19
-->