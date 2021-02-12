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
	- since "amazon" we know has hit_count of 20b, we can save the table with prefix of "a", "am", "ama", ... etc
	- next we see "apple" has hit_count of 15b, we check each prefix and see if we can put "apple" in or need to create new prefix entry
	- whenever a new words comes in, if keywordssize is not full, put the new words in
	- otherwise, compare the hit_count of new words with each word in keywords, replace the one having lower hit_count than new words
	- 
|prefix| keywords |
|--|--|
| "a" | ["amazon", "apple", ...] |
| "am" | ["amazon", "amc", ...] |
| "ad" | ["adidas", "adobe", ...] |

<!--stackedit_data:
eyJoaXN0b3J5IjpbOTE0MDMzNDE5LC0xMzMyNTQ5OTEwLC03MT
M4ODc5MTMsMjEwODg4NzIyOCw4NDgwODgzMjBdfQ==
-->