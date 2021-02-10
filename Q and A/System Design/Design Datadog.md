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
- Value
	- for today's data, we can store per minute as unit
	- for yesterday's data, we can store per 5 minute
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTU5Mzc1NDcwMiw3MzA5OTgxMTZdfQ==
-->