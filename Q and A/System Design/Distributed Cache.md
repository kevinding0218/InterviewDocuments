### How cache works
- When client request comes, we first check the cache and try to retrieve information from memory. And only if data is unavailable or stale, we then make a call to the datastore.
- And why do we call it a distributed cache? Because amount of data is too large to be stored in memory of a single machine and we need to split the data and store it across several machines.
### 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzcxNTk5NDcwLC0yMDg4NzQ2NjEyXX0=
-->