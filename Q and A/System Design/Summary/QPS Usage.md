### QPS vs Web Server
- QPS = 100, we can use our laptop to host the web server
- QPS = 1k, we need some better webserver and we might need to consider single point failure
- QPS = 1m, we probably need a cluster of 1k web servers, we need more consideration about maintainance such as what if one of the server goes down
### QPS vs Web Server vs Database
- Web Server can handle 1k QPS (considering of handling business logic or bottleneck of database query)
- SQL Database can handle 1k QPS (if we have more JOIN or INDEX query, this might be even smaller)
- NoSQL database (Cassandra) may handle 10K QPS
- NoSQL Database (Memcached) may handle 1M QPS
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzEzMDIwMTE1XX0=
-->