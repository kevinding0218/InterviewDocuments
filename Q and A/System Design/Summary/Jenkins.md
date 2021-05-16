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
	- job Id
	- versionId	(refereced in GCS)
	- SHA merge commit
	- createdAt
	- Status (

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTk4MzcyMTMxNiwtMjA4ODc0NjYxMl19
-->