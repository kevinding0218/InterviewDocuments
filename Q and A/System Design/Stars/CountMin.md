### When do we need this?
- When we need large amount of dataset to be processing, while we can't accumulate data in memory for that period. 
- Let's store all the data on disk and use batch processing framework to calculate a top K, Map Reduce can be one option. 
- Every time we introduce data partitioning, we need to deal with data replication, so that copies of each partition are stored on multiple nodes. 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1ODA4MzcxNDJdfQ==
-->