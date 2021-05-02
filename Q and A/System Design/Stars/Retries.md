### Ways of Retries
- An **exponential backoff algorithm retries requests exponentially**, increasing the waiting time between retries up to a maximum backoff time. In other words, we retry requests several times, but wait a bit longer with every retry attempt.
- **Jitter adds randomness to retry intervals to spread out the load**. If we do not add jitter, backoff algorithm will retry requests at the same time. And jitter helps to separate retries.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE0MzgzNjU4OTVdfQ==
-->