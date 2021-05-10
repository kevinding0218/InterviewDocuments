### Volatile
- In Java, each thread has its own stack, including its own copy of variables it can access. When the thread is created, it copies the value of all accessible variables into its own stack. The  `volatile`  keyword basically says to the JVM “Warning, this variable may be modified in another Thread”.
- In all versions of Java, the  `volatile`  keyword guarantees global ordering on reads and writes to a variable. This implies that every thread accessing a volatile field will read the variable’s current value instead of (potentially) using a cached value.
- `volatile` reads and writes establish a happens-before relationship, much like acquiring and releasing a mutex.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE2NzM2NzY5ODJdfQ==
-->