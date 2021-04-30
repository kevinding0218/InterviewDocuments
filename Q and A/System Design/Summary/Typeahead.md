### Requirements/Goals
#### Function Requirements
- while user are typing in the search bar, we should have service with response of suggested terms matching with whatever user typed
- Discuss: are we only returning the result or maybe Hot words like top N (10 or 20) , should we consider uppercase or just assume lowercase
#### Non-functional requirements
- QPS discussion or data entry size billion level?
	- DAU: 500m
	- Search: 4 * 6 * 500m = 12b (every user searches 6 times, types 4 letters)
	- QPS: 12b/86400 ~ 138k
	- Peak QPS: QPS * 2 = 276k
- Obviously we'd be looking forward a large scale data collection with query
- When we talked about designing it as distributed system, which I assume it's something we're looking forward in today's design, we need to consider of
- **High scalability**: supports an arbitrarily large number of data collection as well as query to **handle load increase**
- **High availability**: **survives hardware/network failures**
- **High performant**: keep end-to-end **latency as low as possible** GEO might be a considration
-  **CAP(Consistency, Availability and Partition tolerance)** theorem tells us that we should be choosing between Availability and Consistency, **discuss with interviewer**
	- If we choose **Availability** over Consistency, it simply means we prefer to **show stale (not up-to-date) data than no data at all**.
- **Data Durable** is also something we need to think of, which refers to the **system being highly reliable**, any uploaded **feed should never be lost**
- **Cost**(hardware, development, maintenance), but we might ignore that for now
- **Let's revisit those once we finialize more details with our design**
### High level Architecture
#### What services do we need to have?
- Query Service
- DataCollection Service
```
User typing -> Browser -> LB -> Router - Query Service - DataCollection Service - (MapReduce) - Metadata Database/Logs
```
### Detail Analysis of each component in architecture
#### LB/Load Balancer
- All requests coming from our clients will go through a load balancer first. This will **ensure requests are equally distributed among processing servers**.
#### API gateway/Router
- An API management tool that sits between a client and a collection of backend services, acts as a reverse proxy to accept all, aggregate the various services required to fulfill them, and return the appropriate result, in a microservices architecture, in which case a single request could require calls to dozens of distinct applications.
#### Query Service (Function refer to FrontEnd Service)
#### SQL Table
-   What kind of data do we need to store?
    -   The naive way
    -   keyword (e.g: “amazon”, “apple”, “adidas”)
    -   hit_count (e.g: 20b, 15b 7b)
    - 
| keyword | hitcount |
|--|--|
| "amazon" | 20b |
| "apple" | 15b |
| "adidas" | 7b |
```
SELECT * FROM hit_stats
WHERE keyword LIKE '${key}%'
ORDER BY hit_count DESC
LIMIT 10
```
- Pros
	- If search range is fixed or small
	- real time data persistence
	- fast to implement
- Cons
	-   Like Operation is expensive (Use Cache to help with that)
		- If your filter criteria uses equals = and the field is indexed, then most likely it will use an INDEX/CLUSTERED INDEX SEEK
		- If your filter criteria uses LIKE, with no wildcards, it is about as likely as #1 to use the index. The increased cost is almost nothing
		- If your filter criteria uses LIKE, but with a wildcard it's much less likely to use the index
		- the SQL engine still might not use an index the way you're expecting, depending on what else is going on in your query and what tables you're joining to
### Trie (ImplementTrie)
#### key value store
- e.g: "amazon", we can traverse the Trie to find the path with "amazon", then store the hit_count of 20b in ending char of "n" (a -> m -> a -> z -> o -> n[20b])
##3# how to get hot keywords
- e.g: user type "a", we need to track every node with "a" and find the hit_count, 
##### Note: we're only considering alphebt lower case character (26), but in real case scenario, we could have input as multi langauge combination, number diget, special character combination, etc, so our trie with array[26] might not work, we need to use hashtable with unicode
#### Time Complexity
- O(26^n), very slow
#### How to improve
- instead of just storing the hit_count of exact ending char node, we can store a **Top K collection of key as words and value as hit_count in every char node** if they're in the middle of the wording path
	- e.g
		- "a" -> [{adidas: 7b},{airbnb:3b},{amazon: 20b},{apple: 15b},...]
		- "a" - "d" -> [{adidas: 7b},{adobe: 1b},{adele: 2b},{adblock: 1b},...]
#### Search
- when search happens, just return the value collection as hot keywords suggestions
#### New Data (TopKFrequentWordsII)
- when new data comes in
	- e.g: {axx: 10b}, 
		- check "a" lists, see if the value collection reaches capacity, if not, inserted it, otherwise, replace the one with lower least entry
		- continue to "ax", then "axx"
#### Trie can only be stored in memory
- but what if electronic cut off, memeory will be lost, so we still **need to serialize into disk**, like convert a Tree into a character string and store in disk (**TriesSerialization**)
### DataCollectionService (offline build/Serialize/Map Reduce)
- build from bottom to up by using merge sort for parent node (PriorityQueue/TreeSet) offline
- If we try to update our trie for every query it’ll be extremely resource intensive and this can hamper our read requests, too. One solution to handle this could be to update our trie offline after a certain interval.
- create raw data based on user search from web request and generate the result of keyword with hit_count
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
	- **Deserialize** disk in machine B into Trie in machine B
	- Update Tries in machine B from DataCollectionService
	- Switch machine B with machine A
#### Map Reduce Solution (same as top K)
- Processing the logging data peirodically like every hour
- These MR jobs will calculate frequencies of all searched terms in the past hour. We can then update our
trie with this new data. We can take the current snapshot of the trie and update it with all the new terms
and their frequencies. We should do this offline as we don’t want our read queries to be blocked by
update trie requests. We can have two options:
1. We can make a copy of the trie on each server to update it offline. Once done we can switch to
start using it and discard the old one.
2. Another option is we can have a master-slave configuration for each trie server. We can update
slave while the master is serving traffic. Once the update is complete, we can make the slave our
new master. We can later update our old master, which can then start serving traffic, too.
#### How can we remove a term from the trie?
- We can completely remove such terms from the trie when the
regular update happens, meanwhile, we can add a filtering layer on each server which will remove any
such term before sending them to users.
### Ranking criteria for suggestions? In addition to a simple count, for terms
ranking, we have to consider other factors too, e.g., freshness, user location, language, demographics,
personal history etc.
### Interviewer: what if the trie gets too large for one machine?
- We could have multiple QueryService based on splitting on character (Sharding)
#### How is trie stored across multiple machines?
- We use **consistent hashing** to decide which machine a particular string belongs 2
- e.g 1: when "ad" comes, we calculate its hashing value, assuming it's 1, then we would go to Query Service 1 to either read/write/update in the Service 1 Tries for the "ad". At this time, even though other Query Service Tries also contains "ad" node, we won't store anything there
- e.g 2: when "adi" comes, we calculate its hashing value, assuming it's 0,  then we would go to Query Service 0 to either read/write/update in the Service 0 Tries for the "adi". At this time, even though other Query Service Tries also contains "adi" node, we won't store anything there
### Interviewer: Distribution is skewed, e.g: 's' has much more queries than 'z'..
- We split based on statistics/hit rates, e.g, each machine at most evenly N bytes, count bytes by prefix: 'aa', 'ab', 'ac', ......
### Interviewer: Trending queries/Hot words
- Because those hot words might not have large amount volumn, so we can leave some space in each service to insert these hot words with a higher weight by online update (don't need to go through offline update)
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
- Store in localstorage
#### Input debounce: delay 50ms ~ 100ms to catch last word of user input
#### Dedupilcate
- No duplicate request 
	- suppose when we type "123", we would request 3 times already (1, 12 & 123), now if we remove 3 it comes "12", we don't need to make a new request because we already get the response earlier
#### PreFetch
- Filter on client side with more data response at initial request
	- suppose when we type "a", instead of just return the top 4 keywords with a, we can return keywords of other combination such as "ab", "ac", ... "az", so we can filter the result on client side when user type the next word but also request for "ab"
### Stopwords
- Skip words like "I", "the", "a" as those even appear more than often but doesn't have real meaning
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM0ODU0NzQxMywtNDc4MDMwNTU4LDE0MD
MxOTk1NTcsMjA2OTY3ODg3NiwtNDYxNTk5NDM1LC01NTIwODQw
MTUsODc2MjkwMzYxLDE3MjQ1MjYyMTAsMTAyOTk3NDI1MSw3Mz
A5OTgxMTZdfQ==
-->