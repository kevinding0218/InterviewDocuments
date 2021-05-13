### Deadlock
#### What is deadlock?
- a deadlock is a situation where **minimum two threads are holding the lock on some different resource, and both are waiting for other’s resource to complete its task. And, none is able to leave the lock on the resource it is holding**.
#### How deadlock happens?
- Resources are Mutually exclusive, meaning each time one resource can only be used by one thread
- When thread A was blocked during requesting a resource, the resource was not released and keep holding by thread B
- Reentrant Locks: **a thread A holds a key needed by a thread B, and B also holds the key needed by A**
#### How to prevent?
1. use `ThreadLocal` to make each thread not exclusive
2. use `volatile` which would refresh object from L1, L2 cache to main thread, so every thread can have visibility of the object
3. use distributed lock like [Optimistic Locking](https://www.baeldung.com/jpa-optimistic-locking) by database or redis, **optimistic locking is based on detecting changes on entities by checking their version attribute**.
	- Version attributes are properties with _@Version_ annotation. **They are necessary for enabling optimistic locking**
	- **We should know that we can retrieve a value of the version attribute via entity, but we mustn't update or increment it.** Only the persistence provider can do that, so data stays consistent.
4. **pessimistic locking** mechanism involves locking entities on the database level.
	- Each transaction can acquire a lock on data. As long as it holds the lock, no transaction can read, delete or make any updates on the locked data. We can presume that using pessimistic locking may result in deadlocks. However, it ensures greater integrity of data than optimistic locking.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTI2MDc0MTIzOV19
-->