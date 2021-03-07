### Data Storage
- SQL Database (MySQL, PostgreSQL) 
- NoSQL database (Cassandra, DynamoDB)
- Cache (Redis)
- Stream processing (Kafka + Spark)
- Cloud native stream processing (Kinesis)
- Batch processing (Hadoop MapReduce)

### What questions we must think or ask ("Count of videos" design)
- Think along 4 categories mentioned here, think about data, what data, how data gets in and out of the system, and do not worry about time too much, you better spend additional 5 minutes clarifying requirements and scope than find yourself solving a different or more complex problem than the interewer actually asked
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
	- What is expected write-to-read data delay? 
		- if we can count views several hours later than these views actually happened, both batch data processing and stream processing design options can be considered
		- but if time matters and we need to count views on the fly in real-time, batch data processing might be slow and not considered as an option,
	- What is exepcted p99 latency for read queries? how fast data must be retrieved from the system
		- if interviewer tells us that response time must be as small as possible,  it's a hint that we must count views when we write data, and we should do minimal or no counting calculation when we read data, in other words, data must already be aggregated.
- Cost: help us evaluate technology stack
	- Should the design minimize the cost of development?
		- if asked to minimize development cost, we should be leaning towards well-regarded open-source frameworks
	- Should the design minimize the cost of maintenance?
		- if future maintenance cost is a primary concern, we should consider publich cloud services
- The end goal of requirements clarification discussion is to get us closer to defining both FUNCTIONAL and NON-FUNCTIONAL requirements
	- Functional requirements, we mean system behavior, or more specifically APIs, a set of operations the system will support, basically how system will do
	- Non-functional requirements, we mean system qualities, such as fast, fault-tolerant, secure, basically how system is supposed to be

### Functional Requirements - API
- After figureing out what the system should do, you write it down on the whiteboard in a few sentences, help you identifiy name of APIs, input parameters and if needed, make several iteration to generalize APIs. e.g
	- The system has to count video view events
		- count view events is the actual action the system performs and `videoId` becomes the input
		``countViewEvent(videoId)``
		- if we want the system to calculate not just views, but a broader set of events, let's say `likes` and `share`, we may generalize our API a bit and introduce `eventType` parameter, this parameter indicates type of the event we process
		``countEvent(videoId, eventType) where eventType is enum of view/like/share``
		- we can go one step further and make the system calculate not only count function, but other functions as well, like sum and average. 
			- By supporting sum function we can calculate such metric as "total watch time" for a video
			- By supporting avg function, we can calculate average view duration
		``processEvent(videoId, eventType, function) where function can be delegate to count/sum/average``
		- we can further generalize the API and say that system will not just process events one by one, but a list of events as a single batch, where each event is an object that contains information about a video, type of event, time when event happened and so forth
		``processEvents(List<Event>)``
		- Similar thought process can be applied for data retrieval API.
	- The system has to return video views count for a time period
		- GetViewsCount becomes an action, while videoId, start and end time become input parameters.
		``getViewsCount(videoId, startTime, endTime)``
		- if we want to retrieve count not only for video views, but for likes and dislikes, we can introduce `eventType` parameter
		``getCount(videoId, eventType, startTime, endTime) where eventType is enum of view/like/dislike/share``
		- if we want our API return not just count statistics, but also for sum and average, we should specify function as a parameter and rename our API in a more generic way, like `getStats`
		``getStats(videoId, eventType, function, startTime, endTime) where function can be delegate to count/sum/average``

### Non-Functional APIs
- Usually interviewers will not tell us specific non-function requirements, most likely will challenge use with mentioning big scale and high performance, and we will need to find trade offs.
- Focus on scalability, performance and availability as top priority requirements, e.g
	- Scalable: we need to design such should handle tens of thousands of requests per second
	- Highly Performant: we want view count statistic to be returned in a matter of few tends of milliseconds.
	- Highly Available: we want statistics to be shown to users all the time, survives hardware/network failures, no single point of failure
- CAP theorem tells me I should be choosing between Availability and Consistency
	- Consistency
	- Cost(hardware, development, maintenance)
		
### High-level architecture
- Start with something SIMPLE
	- we need a database to store data
	- we will have a web service that processing incoming video view events and stores data in the database - "Processing Service"
	- we will have another web service that to retrieve view counts from the database - "Query Service"
``User -> Browser -> Processing Service -> Database -> Query Service -> Browser -> User``
- Interviewers may start asking questions about any component we outlined in the high-level architecture, but we may not feel comfortable discussing any component just yet, we need to start with something simple and construct the puzzle/frame with the outside pieces.
- What is the frame of outside pieces of a system design puzzle? DATA!!!
	- more specifically, we need to think what data we want to store and how, we need to define a data model.
- What we store
	- we have 2 options of how we want to store
		- we may store each individual video view event
		- or we may calcuate views on the fly and store aggregated data.
	- Invidual events (every click)
		- we need capture all attributes of the event: videoId, timestamp, user related information such as country, device type, operating system and so on	
| videoId |  |
|--|--|
|  |  |




	- Aggregate data (e.g per minute) in real-time
<!--stackedit_data:
eyJoaXN0b3J5IjpbNTcyMzgzODU5LC0xNTkwOTE1NDcwLC0xMz
Q2MzM3ODk0LDQ2NDYzOTQ4M119
-->