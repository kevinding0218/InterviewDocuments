### Metrics Data
- service name
- instance (instance id or ip address)
- code (http code)
- method (http method)
- path (url path)
- duration (how long API request takes)
- count (number of requests in an interval)
- Because most of time we query by time range to list service instances’ API methods (sample picture below), the sharding key can be **“time bucket” + “service” **, where the **time bucket is a timestamp rounded to some interval**.
- This gives us a known, time-based value to use as **a means of further partitioning our data**, and also allows us to easily **find keys that can be safely archived**. 
- The time bucket is an example of a **sentinel**, and is a useful construct in a number of models where you need better distribution than your natural key provides. When choosing values for your time buckets, a rule of thumb is to select an interval that allows you to perform the bulk of your queries using only two buckets. **The more buckets you query, the more nodes will be involved to produce your result**. It’s also worth noting that this would be an excellent time to use time-window compaction.
```
SELECT * FROM metric_readings
WHERE service_name = "Service A"
AND time_bucket IN (1411840800, 1411844400)
AND timestamp >= 1411841700
AND timestamp <= 1411845300;
```
- For hot spot issue (even though very unlikely), we can append sequence number such as 1,2,3 … to it.
#### Other model
- In the previous example, we were looking for time-ordered data for a given object, in this case a service. But there are cases when what we really need is to **simply get a list of the latest readings from all services**. 
- We need a different model to address this, because our previous model required that we know which service we were querying. 
	- It would be tempting to simply remove service name from the primary key, using only time_bucket as the partition key. The problem with this strategy is that all writes and most reads would be against a single partition key. 
	- This would create a single hotspot that would move around the cluster as the interval changed. Keep in mind that a materialized view would result in the same problem, since the view itself would contain hotspots. 
- As a result, it is imperative that **we determine some sentinel value that can be used in place of the service name, and that is not time oriented**. **For example, API method or url path or instance ip address could be a good value**. In practice I have found that this use case is rare, or that the real use case requires a queue or cache.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxMjM2MzE4NjEsMTYyMDUyNDgyLC00OD
EzODI2ODNdfQ==
-->