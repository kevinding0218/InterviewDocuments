### Requirements/Goals
#### Function Requirements
- while user are typing in the search bar, we should have service with response of suggested top N (10 or 20) terms matching with whatever user typed
#### Non-functional requirements
- QPS discussion
- -   DAU: 500m
-   Search: 4 * 6 * 500m = 12b (every user searches 6 times, types 4 letters)
-   QPS: 12b/86400 ~ 138k
-   Peak QPS: QPS * 2 = 276k
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1MTUyOTQ5MTQsNzMwOTk4MTE2XX0=
-->