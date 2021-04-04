### Summary
#### Validation
- input as `String`, check if `null or length == 0`
#### HashMap
- deal with `Character` or `String.toCharArray()`, use `char[26]` **array** to store character appearance count (``chars[c - 'a'] ++``)
###  Anagrams
- Check if Anagrams
	I. **Sort** String Characters 
	II. using **char[26] as map** to store number of character appearance count in a String
- Result as HashMap
	- Put sorted result into Map as Key and value as List<String>
- Time: K is count of input Strings
	- O(K * nLogN)  if sorted
	- O(K* N) if using char[] as dictionary
- Space: (K * N)
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIzMzY2Mzk3NSwyOTA0NjM5NSwtMTU2Mj
U5Mjg3MCwtNTAwMzU4MTE1XX0=
-->