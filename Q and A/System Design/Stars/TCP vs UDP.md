### TCP and UDP
- TCP protocol guarantees delivery of data and also guarantees that packets will be delivered in the same order in which they were sent.
- UDP protocol does not guarantee you are getting all the packets and order is not guaranteed. But because UDP throws all the error-checking stuff out, it is faster.
### Which one to choose?
- If we want rate limiting solution to **be more accurate**, but with a little bit of **performance overhead**, we need to go with **TCP**.
- If we ok to have a bit **less accurate** solution, but the one that **works faster**, UDP should be our choice.
- For majority of clusters out there, where cluster size is less then several thousands of nodes and number of active buckets per second is less then tens of thousands, gossip communication over UDP will work really fast and is quite accurate.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTY0NDQ2MjYwMiwxNzIzMzY2OTRdfQ==
-->