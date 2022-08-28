



### Load Balancer
- A load balancer **evenly distributes incoming traffic among web servers** that are defined in a load-balanced set
- This is to **resolve issue such as failover and redundancy**
- Clients/Users **connect to the public IP of the load balancer directly.** With this setup, web servers are unreachable directly by clients anymore. 
#### Private IP
- For better security, private IPs are used for communication between servers. **A private IP is an IP address reachable only between servers in the same network; however, it is unreachable over the internet**. The load balancer communicates with web servers through private IPs.


### When to use use Non-relational databases
- Your application requires super-low latency
- Your data are unstructured, or you do not have any relational data
- You only need to serialize and deserialize data (JSON, XML, YAML, etc)
- You need to store a massive amount of data
### Database Replication/Master-Slave
- A master database generally only supports write operations. 
- A slave database gets copies of the data from the master database and only supports read operations
- Most applications require a much higher ratio of reads to writes; thus, the number of slave databases in a system is usually larger than the number of master databases
#### Pros
- **Better performance**: In the master-slave model, all writes and updates happen in master nodes; whereas, read operations are distributed across slave nodes. **This model improves performance because it allows more queries to be processed in parallel.**
- **Reliability**: If one of your database servers is **destroyed by a natural disaster,** such as a typhoon or an earthquake, data is still preserved. You do not need to worry about data loss **because data is replicated across multiple locations.**
- **High availability**: By r**eplicating data across different locations**, your website remains in operation even if a database is offline as **you can access data stored in another database server**.
#### What if a Slave Database goes down
- If only one slave database is available and it goes offline, **read operations will be directed to the master database temporarily**. 
	- In case multiple slave databases are available, **read operations are redirected to other healthy slave databases**.
- As soon as the issue is found, **a new slave database will replace the old one**. 
#### What if a Master Database goes down
- If the master database goes offline, a slave database will be promoted to be the new master. All the database operations will be temporarily executed on the new master database.
- A new slave database will replace the old one for data replication immediately.
	- In production systems, promoting a new master is more complicated as **the data in a slave database might not be up to date**. The missing data needs to be updated by **running data recovery scripts**
<!--stackedit_data:
eyJoaXN0b3J5IjpbNTk2MTAzNzMyLC05Mjg4ODMyNDEsNDYzOT
A4MTUsMTc2MTEzMTQ3MSw3ODU5MTg2MzAsNTkyMDkwNzY2LDE5
MzY0OTI2Nyw3MzA5OTgxMTZdfQ==
-->