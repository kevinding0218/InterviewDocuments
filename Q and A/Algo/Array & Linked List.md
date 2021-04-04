### Summary
#### Validation
- input as `String`, check if `null or length == 0`
#### HashMap
- deal with `Character Array` or `String.toCharArray()` that needs to check appearance
	- use `int count = int[26]` to store character appearance count `count[ch - 'a'] ++`
###  Anagrams
- Check if Anagrams
	I. **Sort** String Characters 
	II. using **int[26] count as map** to store number of character appearance count in a String
- Result as HashMap
	- Put sorted result into Map as Key and value as List<String>
- Time: K is count of input Strings
	- O(K * nLogN)  if sorted
	- O(K* N) if using char[] as dictionary
- Space: (K * N)
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTE3Njc1OTE0NiwtMjMzNjYzOTc1LDI5MD
Q2Mzk1LC0xNTYyNTkyODcwLC01MDAzNTgxMTVdfQ==
-->