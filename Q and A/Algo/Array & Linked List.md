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
### Interval
#### Sort List by start 
- if it's `int[][] intervals`, then use  `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]))` 
- if it's `List<Interval>` then use `intervals.sort(Comparator.comparing(i -> i.start));`
#### Interater the list
##### Check if conflict
1. define`maxEnd = Math.max(maxEnd, interval.end)` to maintain current interval end
2. compare with each incoming interval's start `interval.start < maxEnd`
##### Merge if conflict
1. define`existedInterval` as 1st element/null in sorted list to maintain an interval that could be cut off
2. compare with each incoming interval by using `prev == null || existedInterval.end < incomingInterval.start`
	- if true, meaning existedInterval can be cut off, add into result `result.add(existedInterval)` and update `existedInterval = incomingInterval`
	- if false, meaning those two intervals can be merged, update `existedInterval.end = Math.max(existedInterval.end, incomingInterval.end)`
		-  if `existedInterval` has already been added into result, update its end will also update the added interval's end in result, e.g(we're adding the 1st interval in result as `existedInterval is null` initially)
##### Insert if conflict
1. Find insert index in List by comparing `while(idx < intervals.size() && intervals.get(idx).start < newInterval.start) { idx ++; }`
2. Do merge again
##### Remove overlap interval
1. define `end` as 1st interval end in sorted list 
2. compare with each incoming interval from 2nd
	- if `incoming.start < end`, meaning incoming would be an overlapped interval, increment result count, and update the `end` as `end = Math.min(end, incoming.end)`
	- otherwise, meaning the incoming interval is not overlap, update `end = incoming.end`
##### Find missing interval
1. use helper method `addRange(result, start, upper)` to add each interval into result
2. add head interval as lower to head `nums[0] - 1` as 
3. iterator from 2nd element to end of list, add each `addRange(result, nums[i - 1] + 1, nums[i] - 1)`
4. add tail interval as `addRange(ans, nums[size - 1] + 1, end)`
##### Find right interval
- using TreeMap: Time:O(nlogN), Space:O(n)
	1. `map` to store all **interval.start as key** and its **index in array as value**
	2. iterative through the list, use `map.ceilingKey(interval.end)` to check if its right interval existed or not
		- if null, meaning there is no right interval, return -1
		- otherwise, use `map.get(interval.end)` to find its right interval's index
- using SweepLine + PriorityQueue
	1. define heap as `Queue<Point> minheap = new PriorityQueue<>(Point.PointComparator);`
	2. Enqueue when it's start point, check when it's end point
	3. Check current `minHeap.size() == 0` 
		- If true, meaning there is no right interval, `return -1`
		- otherwise meaning current heap top element would be the right interval, `return minheap.peek().index`
### SweepLine
#### Define `Point` template, 
- time as each interval's start or end, 
- flag using `1` as start and `0` as end
- if interval1.end = interval2.end, put **whichever is end in front of start**
```
static class Point{  
    int time;  
    int flag;  
  
    Point(int t, int s) {  
	    this.time = t;  
        this.flag = s;  
    }  
  
    public static Comparator<Point> PointComparator = (p1, p2) -> {  
	  if(p1.time == p2.time)  
		  return p1.flag - p2.flag;  
      else  
		  return p1.time - p2.time;  
    };  
}
```
#### Define a `List<Point> list` with **double size of the interval list**, then put **each interval's start & end as one Point** into list
```
List<Point> list = new ArrayList<>(intervals.size() * 2);
for (Interval i : intervals) {  
    list.add(new Point(i.start, 1));  
    list.add(new Point(i.end, 0));  
}
```
#### **Sort** the `list`
```
/*
input: [(1, 10), (2, 3), (5, 8), (4, 7)]
list:  [(1, 1), (10, 0), (2, 1), (3, 0), (5, 1), (8, 0), (4, 1), (7, 0)]
sorted:[(1, 1), (2, 1), (3, 0), (4, 1), (5, 1), (7, 0), (8, 0), (10, 0)]
*/
list.sort(Point.PointComparator);
```
#### Iterative over the list
- Check how many overlap
	```
  for (Point p : list) {  
	  if(p.flag == 1)  
		  count++;  
      else  
		  count--;  
      ans = Math.max(ans, count);  
	}
	```
### Median
#### Find Median of unsorted array
1. Using **maxHeap/PriorityQueue** :  Time:O(n), Space:O(n/2)
	- let `maxHeap` to maintain 1/2 of array sized smaller items, the top element in heap would be the largest element of 1/2 array smaller ones.
	- maxHeap is A (child) node can't have a value greater than that of its parent. Hence, in a _max-heap_, the root node always has the largest value.
	1.  calculate the mid length of array as `(nums.length + 1) / 2`
	2. initiate a max heap using PriorityQueue, note that in Java PriorityQueue by default is minHeap, so we need to take the reverse order `Queue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());`
	3. iterate over array, keep `maxheap.offer(nums[i])` items into the queue, whenever the queue reached its size, check if the current item is smaller than `maxheap.peek()`, if so, `poll` it out.
	4. return `maxheap.peek()`
2. Using **Partition**: Time:O(n), Space:O(1)
	- refer to Partition
#### Median of two sorted array
1. merge 2 sorted array and return median of merged array
	- Time:   O(n + m)
	- Space: O(n + m)
2. Two pointers
	- p1 with array A, p2 with array B, iterate `0 ~ (n + m) / 2 + 1` times
	- every time move forward pointer which element is smaller, if one pointer has reached the end of array, move another pointer
		```
		// p2 右移  
		if (p1 >= A.length || p2 < B.length && A[p1] > B[p2]){  
		  right = B[p2];  
		  p2 ++;
		}  
		// p1 右移  
		else {  
		  right = A[p1];  
		  p1 ++;
		}
		```
### Merge
#### Merge 2 sorted array
#### Merge k sorted arrays
#### Merge 2 sorted list
#### Merge k sorted list
#### Merge binary tree
### Partition
#### Idea
- choose a pivot (could be any item in the array, head or tail or middle), at the end of while loop is to partition items in array so that all numbers smaller than pivot will be on its left side and all numbers greater than pivot will be on its right side.
#### Template:
```
int start = 0;  
int end = nums.length - 1;  
int mid = start + (end - start) / 2;  
int pivot = nums[mid];  
while (start <= end) {  
  while (nums[start] < pivot && start <= end) {  
	  start++;  
  }  
  while (nums[end] > pivot && start <= end) {  
	  end--;  
  }  
  if (start <= end) {  //swap(start, end)
	    int temp = nums[start];  
        nums[start] = nums[end];  
        nums[end] = temp;  
        start++;  
        end--;  
    }   
}
```
### [PriorityQueue/Heap](https://www.baeldung.com/java-queue)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTY1ODQwNzIyNCwxMjExMDYyNjcwLDEyNz
AzOTQwMzUsMTM3MTYzODI2OCw2NTAyMDY1NjAsNTYxMzU4MTg4
LC0yMDM3NTE4MDYxLDcyNzQyODAyNywyMDQxMDYwMzcyLC04Nj
kyODM0MTIsLTE2MTQ1Njg2MjcsMTM2MzE0NzEyLC0zODAxMjk1
MzEsNzI4NjA1ODY4LC0xMDkyMTE0MDU1LDE2MTE1MDkwMDcsMT
AwMDc5NzE2NCwzNzU2MjgyMTcsLTE4NTM1MTQ4NjQsLTIzMzY2
Mzk3NV19
-->