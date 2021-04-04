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
		- define`maxEnd = Math.max(maxEnd, interval.end)` to maintain current interval end
		- compare with each incoming interval's start `interval.start < maxEnd`
	- Merge if conflict
		- define`existedInterval` as 1st element/null in sorted list to maintain an interval that could be cut off
		- compare with each incoming interval by using `prev == null || existedInterval.end < incomingInterval.start`
			- if true, meaning existedInterval can be cut off, add into result `result.add(existedInterval)` and update `existedInterval = incomingInterval`
			- if false, meaning those two intervals can be merged, update `existedInterval.end = Math.max(existedInterval.end, incomingInterval.end)`, if `existedInterval` has already been added into result, update its end will also update the added interval's end in result, e.g(we're adding the 1st interval in result as existedInterval is null)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTgxODAwODc2MiwzNzU2MjgyMTcsLTE4NT
M1MTQ4NjQsLTIzMzY2Mzk3NSwyOTA0NjM5NSwtMTU2MjU5Mjg3
MCwtNTAwMzU4MTE1XX0=
-->