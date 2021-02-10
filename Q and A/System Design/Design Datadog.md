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
- Sql or N
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1MTYxODYzOTEsNzMwOTk4MTE2XX0=
-->