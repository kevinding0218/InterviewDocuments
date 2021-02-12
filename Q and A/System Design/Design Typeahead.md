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
	- 
| keyword | hitcount |
|--|--|
| "amazon" | 20b |
| "apple" | 15b |
| "adidas" | 7b |

### Interviewer: what's the problem with this approach
- when we do a query search like
```
SELECT * FROM hit_stats
WHERE keyword LIKE '${key}%'
ORDER BY hit_count DESC
LIMIT 10
```
- Like Operation is expensive
- To reduce query time, we can change our design in a tries way like
	- whenever a new words comes in, if keywordssize is not full, put the new words in
	- otherwise, compare the hit_count of new words with each word in keywords, replace the one having lower least hit_count
	- 
|prefix| keywords |
|--|--|
| "a" | ["amazon", "apple", ...] |
| "am" | ["amazon", "amc", ...] |
| "ad" | ["adidas", "adobe", ...] |

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEzMzI1NDk5MTAsLTcxMzg4NzkxMywyMT
A4ODg3MjI4LDg0ODA4ODMyMF19
-->