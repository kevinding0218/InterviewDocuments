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
	- needs 1B daily, 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjAyMTY2MzQ3MiwtMjA4ODc0NjYxMl19
-->