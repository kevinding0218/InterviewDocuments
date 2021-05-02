### Consistent Hashing function
- **Consistent hashing is based on mapping each object to a point on a circle**. We pick an arbitrary point on this circle and assign a 0 number to it. We move clockwise along the circle and assign values.
```
Circle of 0 ~ 2^32
	 2^32|0
		 \
			Host 2
				|
Host 4		Host 5
			/
	   Host 1
```
- We then take a list of cache hosts and calculate a hash for each host based on a host identifier, for example IP address or name. The hash value tells us where on the consistent hashing circle that host lives. And the reason we do all that, is that we want to assign a list of hash ranges each cache host owns. Specifically, each host will own all the cache items that live between this host and the nearest clockwise neighbor.
- for a particular item, when we need to look up what cache host stores it, we calculate a hash and move backwards to identify the host. In this case, host 4 is storing the item.
#### what happens when we add new host to the cache cluster?
- Same as before, **we calculate a hash for a new host, and this new host becomes responsible for its own range of keys on the circle**. While its counter clockwise neighbor (host 4 in this case) becomes responsible for a smaller range.
- In other words, host 6 took responsibility for a subset of what was formerly owned by host 4. And nothing has changed for all the other hosts. Which is exactly what we wanted, to minimize a number of keys we need to re-hash.
- Consistent hashing is much better than MOD hashing, as significantly smaller fraction of keys is re-hashed when new host is added or host is removed from the cache cluster.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE2NzUwNTI3MzJdfQ==
-->