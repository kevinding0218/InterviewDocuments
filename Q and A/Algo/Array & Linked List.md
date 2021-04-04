### Summary
#### Validation
- input as `String`, check if `null or length == 0`
#### HashMap
- deal with `Character` or `String.toCharArray()`
	- use `int count = int[26]` to store 
	- use `chars = char[26] or ` **array** to store character appearance count (``chars[c - 'a'] ++``)
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
eyJoaXN0b3J5IjpbODExMjEwNTE3LC0yMzM2NjM5NzUsMjkwND
YzOTUsLTE1NjI1OTI4NzAsLTUwMDM1ODExNV19
-->