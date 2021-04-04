### Summary
#### Validation
- input as `String`, check if `null or length == 0`
###  Anagrams
- HashMap and Sort
	- Sort String Characters or using char[26] to store number of character appearance count in a String
	- Put sorted result into Map as Key and value as List<String>
	- Time: K is count of input Strings
		- O(K * nLogN)  if sorted
		- O(K* N) if using char[] as dictionary
	- Space: (K * N)
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjExNzg3MjY0LDI5MDQ2Mzk1LC0xNTYyNT
kyODcwLC01MDAzNTgxMTVdfQ==
-->