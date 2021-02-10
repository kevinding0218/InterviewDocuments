## Scenario
- for every visit of a url link, record as one visit
- the ability to query visit count per url link
- the ability to get most recent x hour/day/month/year visit chart
- assuming tiny url read request is 2k QPS

## Service
- as an independent application itself

## Storage
- Most operations are write, read is lower priority
- Need persistent storage, nothing to do with memcached
- Sql or NoSql or File System?
	- let's assume NoSql
	- key would be the short_key of tiny url, value would be all visit record data of that key
- Value (multi-level bucket)
	- for today's data, we can aggregate & store per minute as unit
	- for yesterday's data, we can aggregate & store per 5 minutes as unit
	- for last month's data, we can aggregate & store per hour as unit
	- for last year's data, we can aggregate & store per week as unit
	- user query is usually for a certain timestap to current timestamp chart data
	- which means, for last year's data, yuo don't have to store per minute
	- e.g
		- 2016/02/26 23 1h 200
		- ...
		- 2016/03/27 23:50 5m 40
		- 2016/03/27 23:55 5m 30
		- 2016/03/27 00:00 1m 10
		- 2016/03/27 00:01 1m 21
		- ...
		- 2016/03/27 16:00 5m 12
### How to calculate visit data?
- Q: for 2K QPS, do we need to write 2k in NoSql?
	- No, we can aggregate visit times for most recent 15 seconds and write into memory
	- For every 15 seconds, we write the record into NoSql, so our write QPS are 2k / 15
- How to aggregate yesterday's data as per 5 mins?
	- when doing the read operation and found one key has a lot of values, trigger a "retention" operation
	- retention will aggregate all old record
Twitter (Merge k Sorted Arrays)
• Tiny Url (Base62)
• Google Suggestion (Trie)
• Web Crawler (Multi Threading)
• Amazon Top 10 Products (Hash Heap)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTUwNDg1MjU2NSwtNDkxNjM0Mzk5LC00Mj
kyMzI4MDQsMTc2Mzg4NjAwNSw3MzA5OTgxMTZdfQ==
-->