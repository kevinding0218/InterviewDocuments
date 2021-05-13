### HashSet vs TreeSet
- At first glance,  `HashSet`  is superior in almost every way: O(1)  `add`,  `remove`  and  `contains`, vs. O(log(N)) for  `TreeSet`.
- However,  `TreeSet`  is indispensable when you wish to **maintain order over the inserted elements** or **query for a range of elements within the set.**
- HashSet internally uses HashMap for storing data, what it means is,  
- When HashSet,  **add("data")**  method is called, add method internally calls HashMap  **put(key, value)**  method.
- Once HashMap put method is invoked, it stores the data(key-value pair) in bucket, by first evaluating hashcode of key, then identifies exact bucket(array index) and then do equals comparison of the key to store and the key of already stored key-value pairs in linked list present in that bucket.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTk2ODExNTA4Ml19
-->