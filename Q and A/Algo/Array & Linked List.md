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
- Check if conflict
	- define `maxEnd = Math.max(m
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE0OTAwNzgzMDMsLTE4NTM1MTQ4NjQsLT
IzMzY2Mzk3NSwyOTA0NjM5NSwtMTU2MjU5Mjg3MCwtNTAwMzU4
MTE1XX0=
-->