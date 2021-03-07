### Data Storage
- SQL Database (MySQL, PostgreSQL) 
- NoSQL database (Cassandra, DynamoDB)
- Cache (Redis)
- Stream processing (Kafka + Spark)
- Cloud native stream processing (Kinesis)
- Batch processing (Hadoop MapReduce)

### What questions we must think or ask
- Taken example of "Count of videos" design
- Users/Customers
	- Who will use the system?
		- is this all Youtube viewers who will see the total views count for a video?
		- is this a per-hour statistics available to a video owners only
		- is this used by some machine learning models to generate recommendations
	- How the system will be used?
		- data may be used by marketing department only to generate some monthly reports,
			- meaning data is retrieved not often
		- data is sent to recommendation service in real-time
			- meaning data ma
- Scale (read and write)
- Performance
- Cost: budget constraint
<!--stackedit_data:
eyJoaXN0b3J5IjpbNDkzNzM0MjZdfQ==
-->