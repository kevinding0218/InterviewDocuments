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
## Service
- What service do we need?
	- Query Service
	- Data Collection Service
						request						Log data
QueryService						Tries (in memory)
											Serialized Trie (on disk)		<-			DataCollection Service
											    response
## Storage
- What kind of data do we need to store?
	- The naive way
	- keyword (e.g: "amazon", "apple", "adidas")
	- hit_count (e.g: 20b, 15b 7b)
### Interviewer: what's the problem with this approach
- when we do a query search like
```
SELECT * FROM hit_stats
WHERE keyword LIKE '${key}%'
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjQ3NTg2NTY3LDIxMDg4ODcyMjgsODQ4MD
g4MzIwXX0=
-->