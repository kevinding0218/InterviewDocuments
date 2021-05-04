### Design Twitter
#### High Architecture
```	
			Search Service
		/							
client 	-	Tweet Service		-> Fanout (Search/Home)
		\		|
			User Timeline(read)
			Home Timeline(read)
			Social Graph(follow user)
```
- When user publish a tweet, request would go to Tweet Service then directly to go User Timeline, but would do a 

### Design Twitter Search
### Functional Requirement
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwODExMjY3MTQsLTkzOTgxMzY3Nl19
-->