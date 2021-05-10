### What is ConcurrentHashMap
-   **ConcurrentHashMap:**  It allows concurrent access to the map. Part of the map called  _Segment (internal data structure)_  is only getting locked while adding or updating the map. So ConcurrentHashMap allows concurrent threads to read the value without locking at all.
    -   **Concurrency-Level:**  Defines the number which is an estimated number of concurrently updating threads. The implementation performs internal sizing to try to accommodate this many threads.
    -   **Load-Factor:**  It’s a threshold, used to control resizing.
    -   **Initial Capacity:**  The implementation performs internal sizing to accommodate these many elements.
- A concurrentHashMap is divided into number of segments [default 16] on initialization.
- ConcurrentHashMap allows similar number (16) of threads to access these segments concurrently so that each thread work on a specific segment during high concurrency.
- This way, if when your key-value pair is stored in segment 10; code does not need to block other 15 segments additionally. This structure provides a very high level of concurrency.
- In other words, ConcurrentHashMap uses a multitude of locks, each lock controls one segment of the map.
	- When setting data in a particular segment, the lock for that segment is obtained. So essentially update operations are synchronized.
	- When getting data, a volatile read is used without any synchronization. If the volatile read results in a miss, then the lock for that segment is obtained and entry is again searched in synchronized block.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTI3MTM5NTE0Nl19
-->