### Summary
#### Validation
- input as `String`, check if `null or length == 0`
###  Anagrams
- HashMap and Sort
	- Sort String Characters or using char[26] to store number of character appearance count in a String
	- Put sorted result into Map as Key and value as List<String>
	- Time: O(K * nLogN)  if sorted, O(K* N) if using char[] as dictionary
		- K is count of input Strings
		- nLogN is sorted time
	- Space: (K * N)
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTY0NzMwOTc3NiwyOTA0NjM5NSwtMTU2Mj
U5Mjg3MCwtNTAwMzU4MTE1XX0=
-->