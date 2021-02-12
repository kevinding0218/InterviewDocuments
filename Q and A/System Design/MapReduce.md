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
	- Aggregate in one machine => Machine 3 (a: 3, b: 3, c: 3, d: 3)
	- Issue:
		- Aggregation is slow because it's still doing in one machine
- Solution 3: Map Reduce
	- Map
		- Line 1: abacdd => Machine 1 (a: 2, b: 1, c: 1, d: 2)
		- Line 2: abccdb => Machine 2 (a: 1, b: 2, c: 2, d: 1)
	- Reduce
		- Aggregate in  2 machines => 
		- Machine 3 aggregate for a & b {a: 3, b: 3}
		- Machine 4 aggregate for c & d {c: 3, d: 3}
		- Then finally aggreagate Machine 3 & 4
	- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTQ5MzI1NTYxNiwxOTI5MDQ1MDAyLDczMD
k5ODExNl19
-->