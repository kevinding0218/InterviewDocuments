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
			- Split line by line and send request to Machine 1: { 0: abacdd }, Machine 2: { 1: abcccdb }
		3. Step 3: Map	
			- Machine 1: { a: 1, b: 1, a: 1, c: 1, d: 1, d: 1 }
			- Machine 2: { a: 1, b: 1, b: 1, c: 1, c: 1, d: 1 }
			- Why not use HashMap to store like { a: 2, b: 1, c:1 }
				- Memeory limitation for HashMap because of processing big data
				- No need to wait all words be calculated then transfer, we can transfer as long as "a" appears
		4. Step 4: Transfer & Manage via Master machine
			- Decide to send a & b data to Machine 3
			- Decide to send c & d data to Machine 4
		5. Step 5: Reduce
			- Machine 3 Receive {a: {1, 1, 1}, b: {1,1,1}} and aggregate to be {a: 3, b: 3}
			- Machine 4 Receive {c: {1, 1, 1}, d: {1,1,1}} and aggregate to be {c: 3, d: 3}
		6. Step 6: Output
			- Output {a: 3, b: 3, c: 3, d: 3}
			- 
#### Map & Reduce implement
- Map and Reduce function
	- Map: how to split an article into words
	- Reduce: how to aggregate words
- Function Interface
	- Input and output must be Key-Value pair
	- Map Function Input
		- Key: article storage address
		- Value: article content
	- Reduce Function Input
		- Key: key of Map Function output - word content
		- Value: value of Map Function output - word occurrence number
		```
				public class WordCount{
					public static class Map {
						public void map(String key, String value, OutputCollector<String, Integer> output) {
							String[] tokens = value.split(" ");
							for (String word : tokens) {
								output.collect(word, 1);
							}
						}
					}
					public static class Reduce {
						public void reduce(String key, Iterator<Integer> values, 
							OutputCollection<String, Integer> output) {
							int sum = 0;
							while(values.hasNext()) {
								sum += values.next();
							}
							output.collect(key, sum);
						}
					}
				}
			```
		
#### Interviewer Question
1. Q1: How many machine does Map or Reduce need?
	- 1000 map & 1000 reduce for 10 PB data
2. Q2: More machine the better?
	- Advantage: More machine, the data processing at each machine would be less and total time could be less
	- Disadvantage: Starting time could be longer
3. Q3: If starting time is not into consideration, would more machine for Reduce the better?
	- No, the limitation of Key/Word would be the limitation of reduce machine sizes

#### Transfer & Manage
- After transfer & manage, words are sorted and partitioned
	- Why sorted? while doing reduce, we can easily treat all same words at one time, no need to create additional hashmap to store
- Design the sorting
	- e.g: request looks like { a: 1, b: 1, a: 1 } & { a: 1, b: 1, b: 1 }, how to sorted as { a: {1, 1, 1} , b: {1, 1, 1} }
	- Design 1: Quick/bucket sort based on key
		- Disadvantage: these sorting algorithms are processing through memory, which is against Big Data in Map Reduce
	- Design 2: 
		- partition and sort, e.g:
			- { a: 1, b: 1, a: 1, c: 1, d: 1, d: 1 } => { { a: 1, a: 1, b: 1 } { c: 1, d: 1, d: 1 } }
			- { a: 1, b: 1, b: 1, c: 1, c: 1, d: 1 } => { { a: 1, b: 1, b: 1 } { c: 1, c: 1, d: 1 } }
		- Fetch
			- M3: {{ a: 1, a: 1, b: 1 }  { a: 1, b: 1, b: 1 }}
			- M4: {{ c: 1, d: 1, d: 1 }, { c: 1, c: 1, d: 1 }}
		- Merge Sort
			- Works on any sorted collections
			- Merge K sorted list/array => Leetcode In Memory
			- Merge K sorted list/array => On Harddisk [External Sorting](https://en.wikipedia.org/wiki/External_sorting)
				- Paritially sort element in smaller memory, then do a merge k sort
	
	### Apple: Build inverted index with MapReduce
	- 正排索引
		- 0 -> harry potter (car, river)
		- 1 -> one piece (deer, wolf)
		- 2 -> red hat (cat, car)
		- when we try to find which book contains word "car", we would first go to harry potter, do a for loop through all its word and filter that out
	- inverted index
		- car -> (0, 2)
		- river -> (0)
		- wolf -> (1)
		- deer -> (1)
		- cat (2)
		- when we do the same search, we can just return (0, 2)
		- Similar like when we google a word, that it shows N result. As all the result have been already prepared in inverted index
	- Input
		- 0: Deer Bear River
		- 1: Car River
		- 2: Deer Car Bear
	- Output
		- Bear: 0, 2
		- Car: 1, 2
		- Deer: 0, 2
		- River: 0, 1
	- From input to output in MapReduce, we need to go through same steps as Input, Split, Map, Reduce, Output
	- Split: we can split the input into 3 different machine
		- the input of Split can be each entry of the Input, such as
			- Machine 1: 0: Deer Bear River
			- Machine 2: 1: Car River
			- Machine 3: 2: Deer Car Bear
		- the output of Split will be a key-value pair
			- key: String word
			- value: int index
				- Machine 1: [Deer: 0], [Bear, 0], [River, 0]
				- Machine 2: [Car: 1], [River: 1]
				- Machine 3: [Deer: 2], [Car: 2], [Bear: 2]
		- Map:
			- the input of Map is the output of Split
			- the Map will manage which word sent to which server machine of Reduce
				- Machine 4(Reduce - B & C): [Bear, 0], [Bear: 2] + [Car: 1], [Car: 2]
				- Machine 5(Reduce - D & R): [Deer: 0], [Deer: 2] + [River, 0], [River, 1]
		- Reduce
			- Aggregate the input from Map output
				- Machine 4: [Bear: 0, 2], [Car: 1, 2]
				- Machine 5: [Deer: 0, 2], [River: 0, 1]
	```
	public class InvertedIndex {
		public static class Map {
			public void map(String key /*file address*/, Document value, OutputCollector<String, Integer> output) {
				int id = value.id;
				String[] words = value.content.split(" ");
				for (String word : words) {
					if (!word.equals("")) {
						output.collect(word, id);
					}
				}
			}
		}
		public static class Reduce {
			public void reduce(String key, Iterator<Integer> values, OutputCollector<String, List<Integer>> output) {
				List<Integer> results = new ArrayList<Integer>();
				int previous = -1;
				while (values.hasNext()) {
					int now = values.next();
					// remove duplication
					if (previous != now) {
						results.add(now);
					}
					previous = now;
				}
				output.collect(key, value);
			}
		}
	}
	```
### Apple: Anagram - Map Reduce
- Given ["lint", "intl", "init", "code"], return ["lint", "init", "intl"], ["code"]
- Given ["ab", "ba", "cd", "dc", "e"], return ["ab", "ba"], ["cd", "dc"], [
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTM0NDI3NzM5LC0xNTUyNjcxOTA2LC0yND
gzMjk4NDgsMTIwMjQxMTQ3NCwtMTM2ODQxNTAxNCwtMTg3Nzk1
NDU2M119
-->