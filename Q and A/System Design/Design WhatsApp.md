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
- Message Table
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
	- How to improve?
		-  Add a "Thread" Table
		- Thread vs Message
			- Inbox has a list of "Threads"
			- Thread has a list of "Messages"
		- What to store in Thread
			- id	- int
			- participant_ids	- text [1,2] means conversation between 1 & 2
			- created_at - timestamp
			- updated_at - timestamp		index = true
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTU5ODgzNjc0NSwtMTUzMDg3NDM2OSwtMj
A4ODc0NjYxMl19
-->