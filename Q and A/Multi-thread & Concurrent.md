#### What is a thread
- A thread is defined at the operation system level
- A thread is a set of instructions
- An application can be composed of several threads
- Different threads can be executed "at the same time"
- The JVM works with several threads (GC, JIT, ...)
#### What does "at same time" mean
- For example, you can write a text document, while there is a background spell check running, also print the document won't let you stop writing, you can also send/receive email while you're printing
- 1st case: CPU with Only One Core
	- Can only do one thing at a time, but task are switching very fast
- 2nd case: CPU with Multiple Cores
	- Can do two things at a time
- Who is responsible for the CPU sharing?
	- A special element called thread scheduler
	- There are three reasons for the scheduler to pause a thread:
		1. The CPU should be shared equally among thread: there might be some high priority stuffs that are taken into account to share equally the CPU as a resource
		2. The thread is waiting for some more data: think about a thread is doing some input, output, reading or writing data to disk or to a network, we know that reading/writing to disk is a slow process, if CPU is very fast, it may pause the thread waiting for the data to be available
		3. The thread is waiting for another thread to do something: like to release a resource
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
- How it work: synchronize means protecting this method by a fence, so that Java machine uses a **lock** object, every object in the Java language has this key, when a thread tries to enter this protected block of code, it will make a request on this lock object, like "give me your key", **if the object has the key available, it will give it to the thread, and this thread will be able to run the code freely**. If another thread comes and make the same request, this time the lock object has **no key to give** because it already gives its key to the previous thread, the lock object has only **one single key**, so this thread has to be waiting for the key to be available, at some time previous thread complete its executing and will give key to the object so that the waiting thread can now execute it. 
#### Synchronization over multiple methods
- Supposer we have synchronized on a class `Person` of `getName()` and `getAge()`, when a thread wants to access an instance of Person `getName()`, it will just take the key of the lock object which is the current instance itself, meaning at same time if another thread wants to access same instance's `getAge()`, it cannot because same lock key is using on two methods.
	- we might need to create two lock objects if we want synchronized in both `getName()` and `getAge()`
- If we have two instances of `Person`, lock in `getName()` of first instance won't stop another thread from trying to access `getAge()` in second instance
- If we want to allow only one thread to access one instance, we need to our lock object to be bound on class itself
- Using the `synchronized` keyword on a method declaration, uses an implicit lock object, which is the class object in the case of a static method or the instance object itself in the case of a non-static method
#### Reentrant Locks and Deadlocks
- Reentrant Locks: In Java, locks are reentrant, meaning when a thread holds a lock, it can enter a block synchronized on the lock it is holding
- Deadlocks: is a situation where **a thread T1 holds a key needed by a thread T2, and T2 also holds the key needed by T1**
	- The JVM is able to detect deadlock situation, and can log information help debug the application, unfortunately there is not much we can do if a deadlock situation occurs, besides rebooting the JVM
#### Runnable Pattern
- The most basic way to create thread in Java is to use Runnable Pattern
	- First create an instance of Runnable
	- Then pass it to the constructor of the Thread class
	- Then call the start() method of this thread object
		```
		Runnable runnable = () -> {
			String name = Thread.currentThread().getName();
			System.out.println("I am running in thread " + name);
		}
		Thread thread = new Thread(runnable);
		thread.start();
		```
#### Runnable Patt
<!--stackedit_data:
eyJoaXN0b3J5IjpbNTIyNDU4Mzg0LC00ODY2OTIwMTAsNTI0MT
k3NzgsLTY1MDY1OTI5MSwtMjA4ODc0NjYxMl19
-->