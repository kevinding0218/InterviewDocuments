### What is the `ThreadLocal` class? How and why would you use it?
- A single `ThreadLocal` instance can store different values for each thread independently, a way to isloate variable across threads.
	- e.g: when 
- Each thread that accesses the `get()` or `set()` method of a `ThreadLocal` instance is accessing its own, independently initialized copy of the variable.
- `ThreadLocal` instances are typically private static fields in classes that wish to associate state with a thread (e.g., a user ID or transaction ID)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTkzMDUyNTM2OV19
-->