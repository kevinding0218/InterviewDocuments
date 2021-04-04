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
		- use `maxEnd = Math.max(maxEnd, interval.end)` to maintain current interval end, and compare with each incoming interval's start `interval.start < maxEnd`
	- Merge if conflict
		- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTMzNDA5ODI3MiwtMTg1MzUxNDg2NCwtMj
MzNjYzOTc1LDI5MDQ2Mzk1LC0xNTYyNTkyODcwLC01MDAzNTgx
MTVdfQ==
-->