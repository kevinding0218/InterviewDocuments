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
		- Aggregation is slow because it's still doing in one machine that it could have bottleneck
- Solution 3: Map Reduce
	- Map
		- Line 1: abacdd => Machine 1 (a: 2, b: 1, c: 1, d: 2)
		- Line 2: abccdb => Machine 2 (a: 1, b: 2, c: 2, d: 1)
	- Reduce
		- Aggregate in  2 machines => 
			- Machine 3 aggregate for a & b {a: 3, b: 3}
			- Machine 4 aggregate for c & d {c: 3, d: 3}
		- Then finally aggreagate Machine 3 & 4
	#### Map Reduce Steps
	- Map Reduce is a framework for implementing distributed calculation
		1. Step 1: Input	
			- { 0: abacdd, 1: abcccdb }
		2. Step 2: Split   
			- Machine 1: { 0: abacdd }, Machine 2: { 1: abcccdb }
		3. Step 3: Map	
			- Machine 1: { a: 1, b: 1, a: 1, c: 1, d: 1, d: 1 }
			- Machine 2: { a: 1, b: 1, b: 1, c: 1, c: 1, d: 1 }
		4. Step 4: Transfer & Manage
			- Send a & b data to Machine 3
			- Send c & d data to Machine 4
		5. Step 5: Reduce
			- Receive {a: {1, 1, 1}, b: {1,1,1}} to Machine 3 and aggregate to be {a: 3, b: 3}
			- Receive {c: {1, 1, 1}, d: {1,1,1}} to Machine 4 and aggregate to be {c: 3, d: 3}
		6. Step 6: Output
			- Output
	
<!--stackedit_data:
eyJoaXN0b3J5IjpbODMyNzcxMTU5LDE5MjkwNDUwMDIsNzMwOT
k4MTE2XX0=
-->