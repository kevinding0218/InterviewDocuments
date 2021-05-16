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
Queue -> Build Services -> BlobStorage/S3/Google Cloud Storage
				|						|
			GuardService		replication monitor service
```
- First design build the code
- Then deploy the code
#### How queue is structure
- Data Structure in memory on some server?
- If server goes down you lose all the state for all your jobs
- typically you want to have all historical builds or jobs that avoid losing that state
#### SQL db Table for the queue
- Columns
	- job Id	(**ClusteredIndex PK**)
	- versionId	(refereced in GCS)
	- SHA merge commit
	- createdAt    	(**Non-ClusteredIndex**)
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
#### How Builder Service works
- Every 5 seconds, execute the Lookup Process like above
##### What to do if work power goes off?
- That Job will remain in a running state basically forever, you have nothing that's monitoring running jobs to make sure that they eventually finish
- Some sort of health check
	- A complete separate service like ZooKeeper or GuardService, **This component knows about all the builder service hosts, as those hosts constantly send heartbeats to it.**
	- Add **extra column** in table like **Last_HeartBeat**
	- **Builder Service can send heart beat to the Jobs Table** whenever they're performing the build job **periodically** like every 3 mins.
	- **GuardService check Jobs table** to figure out **when the last heart beat time,** not the waiting jobs, not the succeeded or failed jobs but **status as running jobs**, determine if a job has unexpected behavior if there is time period more than a certain minutes
	```
	UPDATE BuildJobs SET STATUS = 'READY' WHERE STATUS = 'RUNNING' AND TIME_DIFFER(now, last_hb) > 3 mins
	```
##### Measure how many works we need per day
- 5000 builds/d
- 100 builds/d/worker (from builder takes 15 mins each)
- results = 5000/100
- Consider busy hour and lazy hour
- **Horizontally Scale Services**: Consider our system can scale as needed, we can add more workers during peak hours and remove during lazy hours
- **Vertically Scale Service**: We can get more powerful machines that could improve time to build down from 15 mins to 10 mins.
### Blob Storage
- Once build job done, we can store the binary into blob storage, **after saving successfully we will then update the table status to be Success**
#### Storage Scale
- We can have regional clusters like regional GCS. 
- Async replicate from main Storage to regional Storage, which won't take extra time after copying the binary into storage or update Jobs table as we mentioned above.
##### How to check that all regions completed replication?
- Only allow deploy once all regions data storage are replicated
- Add extra service to check replication status of the binary. 
- A table with versionId and replicate status/count
-
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTkzNTI5NzI1NSwtMjA4ODc0NjYxMl19
-->