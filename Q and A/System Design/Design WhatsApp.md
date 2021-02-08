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
		- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTcwNTY1OTI3OCwtMjA4ODc0NjYxMl19
-->