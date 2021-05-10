### What is the `ThreadLocal` class? How and why would you use it?
- A single [`ThreadLocal`](http://docs.oracle.com/javase/7/docs/api/java/lang/ThreadLocal.html) instance can store different values for each thread independently. Each thread that accesses the `get()` or `set()` method of a `ThreadLocal` instance is accessing its own, independently initialized copy of the variable. `ThreadLocal` instances are typically private static fields in classes that wish to associate state with a thread (e.g., a user ID or transaction ID)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTI1NTU3NTUyXX0=
-->