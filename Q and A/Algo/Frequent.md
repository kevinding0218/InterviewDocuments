### Max Points in one Line
1. loop from each Point I with another Point j in 2 for loop
2. in inner loop, the goal is to get the max point with Point i of same lines * when end of the inner loop
	- define maxPoints = 1, duplicates = 0, horizontalLines = 1
	- increment duplicates if Point i and Point j are same coordinates `duplic
	- increment horizontalLines if i.y == j.y, update maxPoint with `Max(maxPoint, horizontalLines)`
	- calculate slope then increment count in map, update maxPoint `Max(maxPoint, map.get(slope))`
3. in outer loop
- increment maxPoints with duplicates, `maxPoints += duplicates`
- update ans with `Max(ans, maxPoints)`
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTIxMjk4MDM3M119
-->