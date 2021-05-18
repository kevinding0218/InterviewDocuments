### When do we need this?
- When we need large amount of dataset to be processing, while we can't accumulate data in memory for that period. 
- Let's store all the data on disk and use batch processing framework to calculate a top K, Map Reduce can be one option. 
- Every time we introduce data partitioning, we need to deal with data replication, so that copies of each partition are stored on multiple nodes. 
- We need to think about rebalancing, when a new node is added to the cluster or removed from it.
- **Count Min** is a simple solution to the Top K problem, but we need to make some sacrifices along the way, which is accuracy. 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM0OTU5NzQ3OF19
-->