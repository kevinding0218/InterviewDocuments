## Design Typeahead
- Google Suggestion
	- prefix -> top n hot key words
- Twitter typeahead
	- suggestion + user + hashtag

## Scenario
Google Suggestion
- DAU: 500m
- Search: 4 * 6 * 500m = 12b (every user searches 6 times, types 4 letters)
- QPS: 12b/86400 ~ 138k
- Peak QPS: QPS * 2 = 276k
### Strcuture
						request						Log data
QueryService						Tries (in memory)
											Serialized Trie (on disk)		<-			DataCollection Service
											    response

<!--stackedit_data:
eyJoaXN0b3J5IjpbNDY0NzY5MDk1LDg0ODA4ODMyMF19
-->