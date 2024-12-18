#### Race Condition
- We know that creating a thread is about doing several things at the same time, accessing data concurrently may lead to issues! For example, two different threads might be reading the same variable, the same field that defined in a Java class or the same array
- A race condition occurs when two **different** threads are trying to **read** and **write** the **same** variable or same field at the **same** time, this read and write is called a **race condition**
- "same time" does not mean the same thing on a single core and on a multi core CPU
- For example in a singleton pattern
	```
	public static Singleton getInstance() {
		if (instance == null)
			instance = new Singleton();
		return instance
	}
	```
	- Thread T1 starts -> check if instance is null ? -> yes -> enters the if block -> Thread Scheduler pauses T1
	- Thread T2 starts -> check if instance is null ? -> yes -> enters the if block -> creates an instance of Singleton and copy the instance in the private static instance field -> Thread Scheduler pauses T2
	- Thread T1 continues -> create an instance of Singleton and copy the instance in the private static instance field -> thus erasing the instance that has been created by the T2
#### How to prevent that?
- **Synchronization**: Prevent a block of code to be executed by more than one thread at the same time, it will prevent the thread schedular to give the hand to a thread that wants to execute the synchronized portion of code that has already been executed by another thread
- in Java we can do it by using the keyword **synchronized**
	```
		public static synchronized Singleton getInstance() {
			if (instance == null)
				instance = new Singleton();
			return instance
		}
		```
- **How it work**: synchronize means **protecting this method by a fence**, so that Java machine uses a **lock** object, every object in the Java language has this key, when a thread tries to enter this protected block of code, it will make a request on this lock object, like "give me your key", **if the object has the key available, it will give it to the thread, and this thread will be able to run the code freely**. If another thread comes and make the same request, this time the lock object has **no key to give** because it already gives its key to the previous thread, the lock object has only **one single key**, so this thread has to be waiting for the key to be available, at some time previous thread complete its executing and will give key to the object so that the waiting thread can now execute it. 
#### Synchronization over multiple methods
- Supposer we have synchronized on a class `Person` of `getName()` and `getAge()`, when a thread wants to access an instance of Person `getName()`, it will just take the key of the lock object which is the current instance itself, meaning at same time if another thread wants to access same instance's `getAge()`, **it cannot because same lock key is using on two methods.**
	- we might need to **create two lock objects** if we want synchronized in both `getName()` and `getAge()`
- If we have two instances of `Person`, lock in `getName()` of first instance won't stop another thread from trying to access `getAge()` in second instance
- If we want to allow **only one thread to access one instance**, we need to our **lock object to be bound on class itself**
- Using the `synchronized` keyword on a method declaration, uses an implicit lock object, which is the class object in the case of a static method or the instance object itself in the case of a non-static method
<!--stackedit_data:
eyJoaXN0b3J5IjpbODI3NDAxMjU2XX0=
-->