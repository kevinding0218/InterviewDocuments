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
		- is this used by some machine learning models to generate recommendation
- Scale (read and write)
- Performance
- Cost: budget constraint
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTcwNTY5ODUzMl19
-->