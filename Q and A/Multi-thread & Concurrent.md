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
#### Reentrant Locks and Deadlocks
- **Reentrant Locks**: In Java, locks are reentrant, meaning when a thread holds a lock, it can enter a block synchronized on the lock it is holding
- **Deadlocks**: is a situation where **a thread T1 holds a key needed by a thread T2, and T2 also holds the key needed by T1**
	- The JVM is able to detect deadlock situation, and can log information help debug the application, unfortunately there is not much we can do if a deadlock situation occurs, besides rebooting the JVM
#### Runnable Pattern
- The most basic way to **create thread** in Java is to use **Runnable Pattern**
	- First create an instance of **Runnable**
	- Then **pass it to the constructor** of the Thread class
	- Then call the **start() method** of this thread object
		```
		Runnable runnable = () -> {
			String name = Thread.currentThread().getName();
			System.out.println("I am running in thread " + name);
		}
		Thread thread = new Thread(runnable);
		thread.start();
		```
#### How to stop a thread
- method `stop()` should not be used and it's there for legacy, backward compatibility reasons.
- should use `interrupt()` method, **it will not stop the thread but merely send a signal to the task the thread is running and telling it that it is time for this task to stop itself**.
- The code of the task should call `isInterrupted()` to terminate itself
	- if the thread was not interrupted, do what the task should do
	- if the thread was interrupted, then should stop the task, cleaning all the resource I have opened.
	- if the thread is blocked, or waiting, then the corresponding method will throw an InterruptedException, such as method `wait()/notify(),join()` throws the InterruptedException
	```
	Runnable task = () -> {
		while(!Thread.currentThread().isInterrupted()) {
			// the task itself
		}
	}
	``` 
#### Producer / Consumer Pattern
- A producer produces values in a buffer
- A consumer consumes the values from this buffer
- Most of the time, will have more than one producers and more than one consumers, and they all will be **executed in their own thread**, which means **these buffers will be shared among all the threads, maybe the object of a race condition if I do not properly synchronize my code.**
- Be careful, the buffer can be empty or full, if it's empty then consumer cannot consume any value and if it's full, then producer cannot write any values in it.
	```
	// Simple Producer
	int count = 0;
	int[] buffer = new int[BUFFER_SIZE];

	class Producer {
		public void produce() {
			while(isFull(buffer)) {}
			buffer[count++] = 1;
		}
	}
	// Simple Consumer
	class Consumer {
		public void consume() {
			while(isEmpty(buffer)) {}
			buffer[--count] = 0;
		}
	}
	```
- The above code was **wrong at race condition** here, if my producers and consumers are run in their own threads, it means that **several threads are reading and writing the buffer at the same time = race condition**, this will corrupt the array that **I will not be able to write or read values from array because of concurrent access to the array**
#### Synchronize the access to the array, 
- by **adding the synchronized keyword** on it like `public void synchronized produce ()` or `public void synchronized consume()` , 
	- however this will not fix our problem because **synchronization can fix our race condition problem**, but not if we write it like that.
	- why? because if we write synchronized like that, it means that the object holding the key that thread will need to run the `consume/produce` method is the `consumer/producer` instance itself, what we want is to avoid a thread from running the consume method when another thread is running the produce method
	- so we need a common synchronization object to all the instances of consumer and producer. This code will work if the lock object is the same for all the producers and consumers instances
		```
		private Object lock;
		class Producer {
			public void produce() {
				synchronized(lock) {
					while(isFull(buffer)) {}
					buffer[count++] = 1;
				}
			}
		}
		class Consumer {
			public void consume() {
				synchronized(lock) {
					while(isEmpty(buffer)) {}
					buffer[--count] = 0;
				}
			}
		}
		```
	- What happens if the buffer is empty?
		- The thread executing this consumer with running the `isEmpty()` method inside the infinite loop will be running forever. So this thread will be blocked inside the consume method, inside the synchronized block, while holding the key of the lock object, so the producer has no chance to add objects to the buffer since it's waiting for the lock key which is held by the consumer thread.
	- So we need a way to "park" a thread while he is waiting for some data to be produced, and when the thread is "parked", it should not block all other threads, so the key held by this thread should be released while this thread is "parked", and this is the wait/notify pattern
#### Wait / Notify Pattern
- `wait()` and `notify()` are two methods from the Object class
- They are **invoked on a given object**
- The thread executing the invocation should hold the key of that object, if the thread that is executing a `wait` method does not hold the key of the object on which it is executing this method, then an exception is raised
- Note: `wait()` and `notify()` cannot be invokved outside a synchronized block
- Calling `wait()`
	- will release the key held by this thread so that other thead can access the key
	- put that thread in a **WAIT** special state
	- The only way to release a thread from a WAIT state is to notify it
- Calling `notify()`
	- release a thread in WAIT state and puts it in **RUNNABLE** state
	- The **only way to release a waiting thread**
	- The released thread is chosen randomly
	- There is also a `notifyAll()` method that will awake all the threads in the WAIT state.
```
class Producer {
	public void produce() {
		synchronized(lock) {
			if(isFull(buffer))
				// put the thread in the WAIT state if buffer is full
				// at this time, the key held by running this method will be released and made available for the consumer
				lock.wait();
			// if buffer is not full, I added object into the buffer
			buffer[count++] = 1;
			// since I put objects in my buffer, I am going to notify all the consumers that may be in the WAIT state
			lock.notifyAll();
		}
	}
}
// Similar changes in consumer by using wait() and notifyAll
class Consumer {
	public void consume() {
		synchronized(lock) {
			if(isEmpty(buffer))
				lock.wait();
			buffer[--count] = 0;
			lock.notifyAll();
		}
	}
}
```
#### STATE DIAGRAM
- **NEW**: when we create a thread by `Thread T = new Thread(someTask);`
- **RUNNABLE**: once we call `T.start()`, it means that the thread scheduler is free to give a time slice of the CPU to the thread so that this thread can execute its task
	- **BLOCKED**: when the thread is blocked at the entrance of a synchronized block **because the key of a lock object is not available**, it is in a blocked state and the **it can only run again when the key is released**.
	-  **WAITING**: using `wait()` call, in this case the thread is **parked in a waitingn list and can be awakended only by a notify call**
	- **TIMED_WAITING**:  using a `sleep(timeout millisecond)` or `wait(timeout millisecond)` call, at the end of its timeout, the thread will be automatically modified by the system and in this case, this thread will be awakened without calling the `notify` method
- **TERMINATED**: once the task is completed, this thread enters the Terminated state, in which the thread scheduler knows that the thread should not be run anymore.

### Ordering Read and Write Operation on a multicore CPU
#### Visibility
```
Main memory - count: 0
Core 1 needs count
1) the variable is copied in L1 which is Core 1's cache
2) Core 1 can modify it
3) Core 2 also needs count
4) however, core 2 gets the count of 0 from main memory, rather than from L1 which is 1
```
- Visibility is about informing the other caches of my CPU that a variable has been modified and that the write value is in one of the cache of the CPU and should not be fetched from main memory
#### "Happens Before" Link
 - Multicore CPU brings new problems since the value can be stored in multiple places such as main memory and several caches, Read and Write can really happen at the same time
 - A given variable can be stored in more than one place
 - Visibility means "a read should return the value set by the **last** write"
 - We need a timeline to put read and write operations on
 - A "**happens before link**" exists between **all synchronized or volatile write operations AND all synchronized or volatile read operations that follow**
 - Example 1:
	```
	int index;
	void incremenet() {
		index++;
	}
	void print() {
		print(index);
	}
	```
	- if the `increment()` happens in thread T1 and `print()` happens in thread T2, then there is no synchronization or volatility, so it's hard to say what the value of index would be because the `print()` of index varialble is not bound to the last write operation in `increment()`
- Example 2: 
	```
	int index;
	void synchronized incremenet() {
		index++;
	}
	void synchronized print() {
		print(index);
	}
	```
	- now we have a synchronized write and sychronized read, so we have a "happens before" link between our write and our read operation, the correct value is always printed/read which is updated by our increment/write operation
- Example 3:
	```
	volatile int index;
	void incremenet() {
		index++;
	}
	void print() {
		print(index);
	}
	```
	- correct value will always be printed as well.
- **A more complex example**
	- `firstMethod()` is writing x and y
	- `secondMethod()` is read x and y
	- They are executed in threads T1 and T2
	- Question: what is the value or r2?
		- Due to the way the code is written, there is a happens-before link between:
			- x = 1 and y = 1
			- r1 = y and r2 = x
			- If T1 is the first to enter then synchronized block, then the execution is in the order, then there is a "happens-before link" before a synchronized write `y = 1` and a synchronized read `r1 = y`
				- x = 1	("happens before link")
				- y = 1
				- r1 = y	
				- r2 = x	("happens before link")
				- The value of r2 is 1 (a "happens before" link between x = 1 and r2 = x)
			- If T2 is the first to enter the synchronized block, then the execution is in this order, then No "happens-before link" before `r2 = x` or `x = 1`
				- r1 = y
				- r2 = x or x = 1? ("happens before link")
				- y = 1
				- The value or r2 may be 0 or 1
```
int x, y, r1, r2;
Object lock = new Object();

void firstMethod() {
	x = 1;
	synchronized(lock) {
		y = 1;
	}
}

void second Method() {
	synchronized(lock) {
		r1 = y;
	}
	r2 = x;
}
```
#### Synchronization and Volatility
- Synchroniztion: 
	- Guarantees the exclusive execution of a block of code
	- Only one thread can execute a special block of code at given time
- Visibility: 
	- Guarantees the consistency of the variable, 
	- If a variable is visible, then I have the guarantee when I read it I read a correctly updated value.
	- A weaker constraint of synchronization because two threads can execute the read and write operations at the same time, but I still have the consisntency variables guaranteed.
- Conclusion:
	- All "shared" (means shared among more than one thread) variables should be accessed in a synchronized or a volatile way
#### False Sharing
- Definition:
	- False sharing happens because of the way the CPU caches work
	- It is a side effect, that can have a tremendous effect on performance
- CPU Cache side:
	- The cache is organized in lines of data
	- Each line can hold 8 longs (64 bytes)
	- When a visible variable is modified in an L1 cache, all the line is marked "dirty" for the other caches
	- A read on a dirty line triggers a refresh on this line, not just on the variable that has just been modified
	- Example
		```
		volatile long a, b;
		void firstMethod() { a++; }
		void secondMethod() { b++; }
		```
		- Core 1 -> L1 | | a | | | | | 
		- Core 2 -> L1 | | | b | | | |
		- T1 runs firstMethod and T2 runs secondMethod
		- The T1 is only interested in variable "a" and the T2 is only interested in variable "b".
		- The first thread T1 is running in Core 1 and since it needs the variable "a", it loaded a line of cache from the main memory with this variable in this line.
		-  The second thread T2 does the same thing, loaded a line of cache from memory
		- Now because the way that memory is organized in our application, orgnized by the compiler and the JVM, it turns out that "a" and "b" are written in two contiguous areas of the main memory.
		- So while loading the line of cache, T1 also loaded the "b" variable, and T2 also loaded the "a" variable
		- Core 1 -> L1 | | a b | | | | 
		- Core 2 -> L1 | | a b | | | |
- Impact:
	- The thread T1 is going to increment the "a" variable, thus marking this line of cache as "dirty", and this mark as "dirty" will be broadcasted to the other caches of the CPU, including the cache of Core 2.
	- Then Core 2 wants to increment the "b" variable, but unfortunately, the line of cache it loaded from the main memory has been marked as "dirty" by Core 1, so when it tries to read the variable "b", it is a cache miss, it has to go back to the main memory to fetch the value of "b" and going to increment. Which is a bad effect as the variable "b" has not been touched by Core 1, the "b" variable has been made "dirty" by the side effect of the fact that the CPU cache is organized in lines.
	- False sharing happens in an invisible way, because when we write a class, we have no idea of how the class and its fields are laid out in memory, it's hard to predict, hitting performance of your application
#### Thread Safe Singleton on Multicore CPU
- Problems with this code
	- we have a Read operation - `if (instance == null)`
	- we have a Write operation - `instance = new Singleton()`
	- If they occur in different threads, it is a race condition
	- No happens before link between them so no gurantee that first thread will create the new instance and second thread will see this new instance
		```
		public class Singleton {
			private static Singleton instance;
			private final Singleton() {}
			public static Singleton getInstance() {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		```
- **1st solution: use synchronization****
	- Make the read and write operation "synchronized"
	- Just adding the "**synchronized**" keyword in `getInstance()` method
		```
		public class Singleton {
			private static Singleton instance;
			private final Singleton() {}
			public static Singleton synchronized getInstance() {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		```
		- this fix will prevent two threads from executing the `getInstance()` method, so we have gurantee that only one Singleton class is going to be created
	- **Execution on a Single Core CPU**
		- suppose that two thread T1 and T2 are calling the getInstance() method
		- T1 is the first to enter the synchronized block `getInstance()` method
		- T1 executes the test
		- The thread scheulder gives the hand to T2
		- T2 tries to enter `getInstance()` method, but since the key is not available, T1 is still holding it, it realize it cannot access it
		- The thread scheduler gives the hand to T1 again very quickly so T1 can finish its execution and return a fresh new created instance object
		- The thread scheduler gives the hand to T2 so that T2 can enter the `getInstance()` method read the instance just created in T1, and return with it.
		- Since the write to this instance object was a synchronized write under the read made by T2, T2 has the gurantee to read the correct value of instance
	- **Execution on a Two Cores CPU**
		- T1 is the first to enter the synchronized block `getInstance()` method
		- T1 executes the test
		- The thread scheulder gives the hand to T2 on the second core of my CPU at the same time T1 is running
		- T2 tries to enter `getInstance()`, of course it cannot because T1 is in it, holding the key of the synchronized block.
		- However, something different is happening here because I am on a two cores CPU, T2 knows that some other threads is running on the other cores, so there is little chance that the key might be released without T2 leaving the core of my CPU, so it's going to wait a little for the key to be released.
		- There might be some timeout in running, at some point it will realize that the key is not released, so maybe the thread scheduler will give the hand to another thread in my application, so T1 will finish to execute.
		- T2 can then enter the `getInstance()` and read instance object , once again this is a synchronized write followed by a sychonized read, everything is fine, T2 is going to read the correct value of the variable
	- **Execution on a Multiple Cores CPU**, e.g 4 cores
		- T1 is the first to enter the synchronized block `getInstance()` method
		- T2 needs to wait for T1 to leave the synchronized method to be able to read instance
		- If on my two other cores I have two other threads, T3 and T4 which also wants to read my instance object, well those threads will have to wait for T2 to leave the `getInstance()` method
		- At this point, the instance has been created, so all the reads could happen at the exact same time, but **since my method is sychronized, no more than one thread can enter it at a given time , so no more than one thread can read instance at the same time**, and this is really a performance hit because the more cores/threads I have, the more time I am going to wait or lose since the reads cannot be made in parallel.
		- **Since the read is synchronized, it cannot be made in parallel**
- **2nd solution: double check locking singleton pattern**
	- if instance has been created, then I just return it, it is not in the synchronized block then all my reads will be made in parallel.
	- if instance has not been created, I have ths synchronized blcok on a special key object which will be static of course
		```
		public class Singleton {
			private static Singleton instance;
			private final Singleton() {}
			public static Singleton getInstance() {
				if (instance != null) {
					return instance;
				}
				sychronized(key) {
					if (instance == null) {
						instance = new Singleton();
					}
					return instance;
				}
			}
		}
		```
	- Buggy
		- If instance is not null, we read it and return it
			- Is it a sychonized read or volatile read?
			- No, instance is not a volatile variable, and our first if statement is outside synchornized blcok	
		- If instance is null, we create it and return it
			- obviously there is a write operation
			- Is it a sychonized write or volatile write?
			- It is a sychronized write, because it's been wrapped inside a sychronized block.
		- So we have a non sychronized read that supposed to return the value set by a sychonized write
		- Do we have the guarantee that the read will get the value set by the write? No
		- We need a "happens before link" between the write operation and read operation outside the synchronized, we don't have this "happens before link" because "happens before link" is created between sychronized or vilotile writes and sychronized or vilotile reads
	- Possible issue
		- This is a concurrent bug, and cannot be easily observed on a single core CPU because there is no visibility issue on a single core CPU, visibility issues can only be seen on a multi core CPUs due to the different caches on each core of the CPU.
		- The effect can be very weird, one can observe an object that is not fully built
- **3rd solution: make it volatile**
	- since the problem comes from the non-synchronized/volatile read, let's make it volatile
		```
		public class Singleton {
			private static volatile Singleton instance;
			private final Singleton() {}
			public static Singleton getInstance() {
				if (instance != null) {
					return instance;
				}
				sychronized(key) {
					if (instance == null) {
						instance = new Singleton();
					}
					return instance;
				}
			}
		}
		```
		- here the double check locking is fixed
		- but with the same performance issues as in the synchronized case
- **The Right solution: using enum**
	- it's been used in several places of the JDK
		- e.g: Comparable interface NaturalOrderOrderComparator
		```
		public enum Singleton {
			INSTANCE
		}
		```
#### How to write correct concurrent code
1) **Check for race conditions**
	- they occur on fields (race condition cannot occur on variables / parameters)
	- if you would have 2 threads are reading/writig a given field, means you would have a race condition on that field
2) **Check for happens-before link**
	- need to have "happens before link" between your read operation and write operation
	- Are the read/write volatile? If the field you're checking has been declared as `volatile`, they are synchronized if they occur inside the boundary of a synchronized block. 
	- Are they synchronized?
	- If not, there is a possible bug like double lock singleton example
3) **Synchronzied or volatile?**
	- Synchronized = atomicity, if you have **a certain portion of code that should not be interrupted between threads, then you need to have a synchronized block** to protect this portion of code (refer to LongWrapper.java)
	- Volatile = visibility, **if it's not above case, then volatile is enough**
		```
		/**  
		 * buggy: this getValue() is not synchronized, so it does not guarantee that it would return the last value * from incrementValueWithLock(), we also need to wrap the return l in a synchronized (key) * so now it has a "happens before link" between the synchronized read and write * @return  
		  */  
		public long getValue() {  
		  synchronized (key) {  
		  return l;  
		    }  
		}

		public void incrementValueWithLock() {  
		  synchronized (key) {  
		  l = l + 1;  
		    }  
		}
		```
<!--stackedit_data:
eyJoaXN0b3J5IjpbOTQ2MjI2NTE3LC0xMzEyOTAyNzQ5LC0xMT
Y1NzQ1MzczLC0yNzUxNTEzMjUsLTEzMDgzNjYwMTAsLTE4MTEy
ODk4NDMsMTE2OTQ3NDU2NywtMTY2MTAxNzY1NCwtMTUyOTcwOD
MxNSwtMjAwMjkwNDA0OSw2ODQxMDg0MTUsMTEyNTAzMTU3NSwt
MTE3NDc1MTYyNSwxMTkxNDA4NDgzLDIxMjU0MzAzNCwtMTY1Nj
Y0NzQ2MSw1MTAxNjIzNzEsMzU4NzIxNTczLC00ODQ1MjczNzEs
LTE4OTE2MDg3NzldfQ==
-->