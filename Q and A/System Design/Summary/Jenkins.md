### Functional
- Building Code
- Deploying Code
### Non Functional
- High Scalability : ask scale like how many machines across world
- High Availability: Two to Three 9 like anywhere between half a day and three days of downtime per year
- High Performance: deploy time (within 30 mins?
- How often are we going to deploy code and how big are these binaries that we're going to be getting,  (1000s deploy per day, 10GB per binary)

### High Level
```
Queue -> Build Services -> S3/Google Cloud Storage
```
- First design build the code
- Then deploy the code
#### How queue is structure
- Data Structure in memory on some server?
- If server goes down you lose all the state for all your jobs
- typically you want to have all historical builds or jobs that avoid losing that state
#### SQL db Table for the queue
- Columns
	- job Id	(ClusteredIndex PK)
	- versionId	(refereced in GCS)
	- SHA merge commit
	- createdAt    	(Non-ClusteredIndex)
	- Status (Ready/Processing/Succeeded/Failed/Cancel)
- Not only we have the queue very clear structure here,
##### How to work with multiple works concurrently?
-  **ACID trasactions**. which is the key to having like hundreds of workers be able to **go into the database and perform updates or read in this database all at once without worrying about concurrency**, **because we can use Transaction executed as if they were sequential.** 
```
BEGIN TRANSACTION;
SELECT @JobId = JobId FROM BuildJobs WHERE STATUS = "READY"
ORDER BY createdAt AT DESC
LIMIT 1;
IF (@JobId <> NULL)
BEGIN
UPDATE JOBS SET STATUS = "PROCESSING" WHERE JobId = @JobId
END
ELSE
BEGIN
ROLL_BACK;
END
COMMIT TRANSACTION
```


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE4NjAxNjEyODMsLTIwODg3NDY2MTJdfQ
==
-->