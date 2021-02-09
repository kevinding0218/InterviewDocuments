## Design a WhatsApp
### Scenario
- What functions do we need
	- User Register & Login
	- Contact Book
	- Message communication between 2 user
	- Message communication between group
	- user online status
- Else
	- History Message
	- Multi Device Login
- QPS Estimation
	- DAU 100M
	- Suppose one user sends 20 message per day
	- Average QPS =  100M * 20/86400 ~ 20K
	- Peek QPS = 20k * 5 = 100k
- Storage Estimation
	- Suppose one user sends 10 messages per day
	- 100M users needs 1B messages daily, if we take 30 bytes per message, total would be 30G of storage

### Service
- Message Service
	- for message management
- Real-time Service
	- for sending message to receiver at real-time

### Storage
#### "Message" Table (SQL)
	- if we design like
		- id
		- from_user_id
		- to_user_id
		- content
		- created_at
	- What issue would we have
		- if we need to fetch communications between A & B, we need following sql
			- SELECT * FROM message_table WHERE (from_user_id = A and to_user_id = B) or (to_user_id = A and from_user_id = B) ORDER by created_at DESC
		- Issue 1: where clause is complex and low SQL performance
		- Issue 2: if this is a group chat, the structure is unable to extend
####  Add a "Thread" Table (SQL)
		- Thread vs Message
			- Inbox has a list of "Threads"
			- Thread has a list of "Messages"
		- What to store in Thread
			- owner_id	- int - who owns this thread
				- if there is a thread conversation betwee A, B & C, we store 3 copies of the threads with same thread_id and different owner_id of A, B & C, why because some info inside a thread would be private, such as is_muted, nickname 
			- thread_id - int - shared across multiple owners
			- participant_ids	- text [1,2] means conversation between 1 & 2
			- is_muted - bool
			- nickname - string
			- created_at - timestamp
			- updated_at - timestamp		index = true
		- Primary Key (combination of owner_id & thread_id)
		- Need indexing on 
			- owner_id + thread_id (primary key)
			- owner_id + updated_time (sort by updated time) so we can take lastUpdated thread at top of chat window
			- NoSQL doesn't have good support for secondary index
		-
| uid | tid | is_muted | nickname | participant_ids |
|--|--|--|--|--|
| 1 | 1 | 0 | sb | 1, 2, 3 |
| 2 | 1 | 1 | nb | 1, 2, 3 |
| 3 | 1 | 1 | xx | 1, 2, 3 |
		- why store the shared info like participant_ids as private?
			- to improve query retrieving performance
			- otherwise we need to separate into a different table that has to use foreign key to join info together
			- we would like to get as much info as possible in one simple query
			- risk of inconsistency
#### Update "Message" Table (NoSQL)
		- Because of large data volume and no need to update, one chat message is like one log
			- id - int
			- thread_id - int （could be combination of user_id or owner_id + created timestamp）
			- user_id - int
			- content - text
			- created_at - timestamp
		- Sharding Key (decide which machine stores my data)
			- thread_id
			- cannot use user_id, because in that way, two messages in one chat thread may stores in different database server
		- Row Key:
#### Work Solution
- How would user send out message?
	- Client sends message content and receiver into fo server
	- Server create a thread for every receiver (including sender himself) if there hasn't bee
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTIyNDgxOTY4MywtMTY4ODg5NjI1NCwxNz
AxNTg5OTYxLDM5NDI4MDI0MiwtMTUzMDg3NDM2OSwtMjA4ODc0
NjYxMl19
-->