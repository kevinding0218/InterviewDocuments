### Data Storage
- SQL Database (MySQL, PostgreSQL) 
- NoSQL database (Cassandra, DynamoDB)
- Cache (Redis)
- Stream processing (Kafka + Spark)
- Cloud native stream processing (Kinesis)
- Batch processing (Hadoop MapReduce)

### What questions we must think or ask ("Count of videos" design)
- Users/Customers : help us understand what data we store in the system
	- Who will use the system?
		- is this all Youtube viewers who will see the total views count for a video?
		- is this a per-hour statistics available to a video owners only
		- is this used by some machine learning models to generate recommendations
	- How the system will be used?
		- data may be used by marketing department only to generate some monthly reports,
			- meaning data is retrieved not often
		- data is sent to recommendation service in real-time
			- meaning data may be retrieved from the system with a very high pace
		- questions in this category 
- Scale (read and write): give us a clue how much data is coming to the system and how much data is retrieved from the system
	- How many read queries per second the system needs to process?
	- How much data is queried per request?
	- How many video views are processed per second?
	- Can there be spikes in traffic and how big they may be?
- Performance: help us quickly evaluate different design options
	- What is expected write-to
- Cost: budget constraint
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQ3OTMzMjU0NF19
-->