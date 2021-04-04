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
### Interval/Sweepline
- Sort List by start `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]))` or `intervals.sort(Comparator.comparing(i -> i.start));`
- Interater the list
	- Check if conflict
		1. define`maxEnd = Math.max(maxEnd, interval.end)` to maintain current interval end
		2. compare with each incoming interval's start `interval.start < maxEnd`
	- Merge if conflict
		1. define`existedInterval` as 1st element/null in sorted list to maintain an interval that could be cut off
		2. compare with each incoming interval by using `prev == null || existedInterval.end < incomingInterval.start`
			- if true, meaning existedInterval can be cut off, add into result `result.add(existedInterval)` and update `existedInterval = incomingInterval`
			- if false, meaning those two intervals can be merged, update `existedInterval.end = Math.max(existedInterval.end, incomingInterval.end)`
				-  if `existedInterval` has already been added into result, update its end will also update the added interval's end in result, e.g(we're adding the 1st interval in result as `existedInterval is null` initially)
	- Insert if conflict
		1. Find insert index in List by comparing `while(idx < intervals.size() && intervals.get(idx).start < newInterval.start) { idx ++; }`
		2. Do merge again
	- Find missing interval
		1. use helper method `addRange(result, start, upper)` to add each interval into result
		2. add head interval as lower to head `nums[0] - 1` as 
		3. iterator from 2nd element to end of list, add each `addRange(result, nums[i - 1] + 1, nums[i] - 1)`
		4. add tail interval as `addRange(ans, nums[size - 1] + 1, end)`
	- Find right interval
		- using TreeMap: Time:O(nlogN), Space:O(n)
			1. `map` to store all **interval.start as key** and its **index in array as value**
			2. iterative through the list, use `map.ceilingKey(interval.end)` to check if its right interval existed or not
				- if null, meaning there is no right interval, return -1
				- otherwise, use `map.get(interval.end)` to find its right interval's index
		- using SweepLine
	#### SweepLine
	1. Define `Point` template, 
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
2. Define a `List<Point> list` with **double size of the interval list**, then put **each interval's start & end as one Point** into list
```
List<Point> list = new ArrayList<>(intervals.size() * 2);
for (Interval i : intervals) {  
    list.add(new Point(i.start, 1));  
    list.add(new Point(i.end, 0));  
}
```
3. **Sort** the `list`
```
list.sort(Point.PointComparator);
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwNzU5NzM3MjQsLTEwOTIxMTQwNTUsMT
YxMTUwOTAwNywxMDAwNzk3MTY0LDM3NTYyODIxNywtMTg1MzUx
NDg2NCwtMjMzNjYzOTc1LDI5MDQ2Mzk1LC0xNTYyNTkyODcwLC
01MDAzNTgxMTVdfQ==
-->