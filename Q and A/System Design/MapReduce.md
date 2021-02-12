## Map Reduce
- Why Map Reduce?
	- Distributed System is built for fast computing

### Interviewer: Count the word frequency of a web page
- Solution 1: Single Machine with For Loop
	- Use a hashmap and iterative with every word: HashMap<String, Integer> worldCount: O(N)
	- Issue:
		- Slow: How to improve
- Solution 2: Multiple Machine with For Loop
	- Line 1: abacdd => Machine 1 (a: 2, b: 1, c: 1, d: 2)
	- Line 2: abccdb => Machine 2 (a: 1, b: 2, c: 2, d: 1)
	- Aggregate in => Machine 3 (a: 3, b: 3, c: 3, d: 3)
	- Issue:
		- Aggregation is slow because it's still doing in one machine
- Solution 3: Map Reduce
		- Line 1: abacdd => Machine 1 (a: 2, b: 1, c: 1, d: 2)
	- Line 2: abccdb => Machine 2 (a: 1, b: 2, c: 2, d: 1)
	- Aggregate in => Machine 3 (a: 3, b: 3, c: 3, d: 3)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQyMTI0NjY4NywxOTI5MDQ1MDAyLDczMD
k5ODExNl19
-->