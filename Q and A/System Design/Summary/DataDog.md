### Metrics Data
- service name
- instance (instance id or ip address)
- code (http code)
- method (http method)
- path (url path)
- duration (how long API request takes)
- count (number of requests in an interval)
- Because most of time we query by time range to list service instances’ API methods (sample picture below), the sharding key can be **“time bucket” + “service” **, where the time bucket is a timestamp rounded to some interval.
- This gives us a known, time-based value to use as a means of further partitioning our data, and also allows us to easily find keys that can be safely archived. 
- The time bucket is an example of a **sentinel**, and is a useful construct in a number of models where you need better distribution than your natural key provides. When choosing values for your time buckets, a rule of thumb is to select an interval that allows you to perform the bulk of your queries using only two buckets. The more buckets you query, the more nodes will be involved to produce your result. It’s also worth noting that this would be an excellent time to use time-window compaction.
```
SELECT * FROM metric_readings
WHERE service_name = "Service A"
AND time_bucket IN (1411840800, 1411844400)
AND timestamp >= 1411841700
AND timestamp <= 1411845300;
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTY4NDIwOTA3NSwxNjIwNTI0ODIsLTQ4MT
M4MjY4M119
-->