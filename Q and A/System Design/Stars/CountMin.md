### When do we need this?
- When we need large amount of dataset to be processing, while we can't accumulate data in memory for that period. 
- Let's store all the data on disk and use batch processing framework to calculate a top K, Map Reduce can be one option. 
- Every time we introduce data partitioning, we need to deal with data replication, so that copies of each partition are stored on multiple nodes. 
- We need to think about rebalancing, when a new node is added to the cluster or removed from it.
- **Count Min** is a simple solution to the Top K problem using fixed sized memroy, but we need to make some sacrifices along the way, which is accuracy, so result may not be 100% accurate.
### Implementation
- a 2D array or matrix, we define the width and height,  width is usually in thousands, while height is small and represents a number of hash functions. 
### How to add data
- When new element comes, we calculate each hash function value and add 1 to the correspondent cell.
- For example, A comes, we calculate five hash functions based on A's identifier, and put 1 to each of 5 cells.
- When another A comes, we increment each cell value to 2.
- Then B arrives, we add 1 to each of B cells, Hash function h1 may produced a collision so that we incremenet A's value as well. Then C arrives, and produces collisions with both A and B. 
### How to retrieve data
- Among all the cells for A, we take the minimum value.
- Because of collisions, some cells will contain overestimated values, and by taking minimum we decrease the chance of overestimation, that's why we need several hash functions by decreasing the error.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbNDI5NTk3NjU5XX0=
-->