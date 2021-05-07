### Functional Req
- Should be able to catch a series of metrics data and store for every API request
- Should be able to fetch from our storage in an easy way 
### Non Functional Req

### Metrics Data
#### Application level
- service name
- instance (instance id or ip address)
- code (http code)
- method (http method)
- path (url path)
- duration (how long API request takes)
- count (number of requests in an interval)
#### Other spec related to host level
- CPU/memory/disk usage
- Avaialbility
- bytes of memory allocated or released
- heap size, etc
### Data Storage Schema
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
### How to store
- **We absolutely cannot just write individual data samples to files as they arrive**. That’ll be prohibitively inefficient because the monitoring system may end up collecting a million data samples or more every minute.
-  With batching in memory, there comes the risk of losing data. It may not be a big deal if we’re only batching for a short period of time, as typical time series use cases can tolerate the loss of a few data samples. But if we want to batch for a longer period of time, a durability safeguard should be put in place.
- **We can use Write-ahead-log (WAL)** to achieve what we're looking for. 
	- On a high level, WAL pipes the changes to a log file on disk that can be used to restore the system state after crashing. 
	- WAL doesn’t incur a big IO penalty because sequential file access is relatively fast.
	- We can buffer the data samples in memory for a while, which opens doors for better write efficiency.
- **How we structure the data in files**. One time series per file sounds like a simple solution, but unfortunately, it won’t scale. There are just too many time series to create individual files for. We have to store multiple time series in a file. On the other hand, we can’t just put everything in a monolithic file. That file will be too big to operate.
- We need to **cut the file in some way**. The natural choice here is to **cut the file by the time dimension**. We can create one file **per day or other configurable time window**. Let’s call the data in a time window a block. If the data volume in a block is too big for one file, we can shard it across a few files. We also need an index file in each block to tell us which file and what file position to look for in a particular time series. (**Check Datadog in Reference**)
- As data samples arrive in memory, we buffer them until we need to flush them to disk. When we flush them to disk, we organize them in blocks. **The block for the most recent data samples typically represents a small time window.**
```
Block 0 | Block 1 | Block 2, ...
15m		  1h		4h
```
#### Compression should be employed to reduce the overall data volume.
### Support Querying and Alerting
- querying is easy to envision. Time is a universal filtering criterion in all metrics searches. We use a time range to narrow the search down to a set of continuous blocks. If we know the time series ID, we can retrieve it from the files in those blocks. Otherwise, we’d need to add a reversed index to go from the search criteria to time series IDs and then follow the index files in blocks to locate the time series files.
- 
### Data Processing - Push vs Pull
- Interviewer: Do we need to get the metrics out of the server? 
	- If not, we can have our server expose an endpoint service with the metrics or it may just save the metrics to local disks and we can do that later
	- If yes, in above approatch, we’ll lose access to the metrics when the server is slow or down — just when we need them the most. We’d also want to put the metrics in a centralized place for better global monitoring and alerting.
#### Pull
- it is more convenient. Each server being monitored only needs to gather the metrics in memory and serve them through an endpoint. It doesn’t need to care where the monitoring system is. It doesn’t need to worry about overloading the monitoring system if they send too much and/or too frequently. A global configuration about what to collect and the collection interval can be tuned in the monitoring system.
- disadvantage of pulling that the blog post didn’t mention is that it’s challenging to offer high availability and scalability with a pull-only model. with a pull-only model, the metrics are collected directly by a monitoring system instance. We’ll have to shard the metrics deliberately when pulling and deploy backup instances explicitly to support replication and failover.
- monitoring systems usually have more than one layer of push/pull to aggregate metrics in a hierarchy.
#### Push
- If we’re using push, we can put a load balancer in front of a set of monitoring system replicas and have the servers being monitored send metrics through the load balancer.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjA1MDE4NzcxOCwxNjIwNTI0ODIsLTQ4MT
M4MjY4M119
-->