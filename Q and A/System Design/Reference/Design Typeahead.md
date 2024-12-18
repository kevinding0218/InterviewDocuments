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
### Disk vs MemCached
- Above was around Table in Database which is stored in disk, the reading speed is 1/1000 of reading from memory
- So we definiately need a MemCached in front of Database
- but Memcached has overhead
### Trie
#### key value store
- e.g: "amazon", we can traverse the Trie to find the path with "amazon", then store the hit_count of 20b in ending char of "n" (a -> m -> a -> z -> o -> n[20b])
### how to get hot keywords
- e.g: user type "a", we need to track every node with "a" and find the hit_count, with O(26^n)
- very slow
#### how to improve
- instead of just storing the hit_count of exact ending char node, we can store a collection of key as words and value as hit_count in every char node if they're in the middle of the wording path
	- e.g
		- "a" -> [{adidas: 7b},{airbnb:3b},{amazon: 20b},{apple: 15b},...]
		- "a" - "d" -> [{adidas: 7b},{adobe: 1b},{adele: 2b},{adblock: 1b},...]
- when search happens, just return the value collection as hot keywords suggestions
- when new data comes in
	- e.g: {axx: 10b}, 
		- check "a" lists, see if the value collection reaches capacity, if not, inserted it, otherwise, replace the one with lower least entry
		- continue to "ax", then "axx"
#### Trie can only be stored in memory
- but what if electronic cut off, memeory will be lost, so we still need to serialize into disk, like convert a Tree into a character string and store in disk
### What does DataCollectionService do
- create raw data based on user search from web request and generate the result of keyword with hit_count
- 
|user| keyword | timestamp |
|--|--|--|
| xxx | "amazon" | 142523421
| yyy | "apple" | 142528465
| zzz | "amazon" | 142529861

- group by keyword to get "amazon" with hit_count 20b
- update periodically Tries in Query Service memory 
	- cannot update Query Service while it's live and used by user
	- cannot write and read at same time
	- Back up Query Service disk from live machine A in another machine B 
	- Deserialize disk in machine B into Trie in machine B
	- Update Tries in machine B from DataCollectionService
	- Switch machine B with machine A

### Interviewer: what if the trie gets too large for one machine?
- We could have multiple QueryService based on splitting on character (Sharding)
#### How is trie stored across multiple machines?
- We use consistent hashing to decide which machine a particular string belongs 2
- e.g 1: when "ad" comes, we calculate its hashing value, assuming it's 1, then we would go to Query Service 1 to either read/write/update in the Service 1 Tries for the "ad". At this time, even though other Query Service Tries also contains "ad" node, we won't store anything there
- e.g 2: when "adi" comes, we calculate its hashing value, assuming it's 0,  then we would go to Query Service 0 to either read/write/update in the Service 0 Tries for the "adi". At this time, even though other Query Service Tries also contains "adi" node, we won't store anything there

### Interviewer: how to reduce the size of log file
- what is log file?
	- log which user searched which keyword at when
- Use a random number like (1 ~ 10k), only when random number equals 1, then we log the record into log file
	- log file size would be reduced by 1/10k
- Will this impact hot keyword search?
	- No, because we don't care about exact keyword search number, we only care about relative keyword search number
	- e.g, if a keyword A has been searched over 10b for over two weeks, it would only be logged as 10k times,
	- e.g, if a keyword B has been searched over 100k for over two weeks, it would only be logged as 10 times,
	- both A and B appearance decreased by same times, and apparantly B won't become hot keyword

### Interviewer: how to reduce response time in front-end
#### Cache Control
- Store the result on client for 1 min
- Suppose we already did well on server side, what can we do at client/browser side
#### Dedupilcate
- No duplicate request 
	- suppose when we type "123", we would request 3 times already (1, 12 & 123), now if we remove 3 it comes "12", we don't need to make a new request because we already get the response earlier
#### PreFetch
- Filter on client side with more data response at initial request
	- suppose when we type "a", instead of just return the top 4 keywords with a, we can return keywords of other combination such as "ab", "ac", ... "az", so we can filter the result on client side when user type the next word but also request for "ab"

### Stopwords
- Skip words like "I", "the", "a" as those even appear more than often but doesn't have real meaning
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTkxNDAyNjYwNSwtMTI0NTE4NDExM119
-->