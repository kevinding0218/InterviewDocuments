## Sequential vs Parallel Computing
### Making salad as an example
- Chopping lettuce, tomato cucumber, onion and put dressing
###  Sequential
- Program is broken down of sequatial instruction that I execute one after another
- Can only execute one at a given moment
### Parallel
- Program is broken down into independent parts that can be executed simultaneously by different processors
- Doesn't necessarily mean we'll make the process twice as fast, because we've to add extra effort like in kitchen to communicate with each other to corrdinate our actions.
	- There might be times that one of us having to wait for the other cook to finish a certain step before we continue on
	- Parallel execution will increase the overall throughput of a program.
		- Accomplish a single task faster
		- Accomplish more tasks in a given time
## Quiz
1. In most modern multi-core CPUs, cache coherency is usually handled by the **processor hardware**
2. A key advantage of distributed memory architectures is that they are **more scalable** than shared memory systems.
3. The four classifications of Flynn's Taxonomy are based on the number of concurrent **instruction** streams and **data** streams available in the architecture.
4. Parallel computing can increase the speed at which a **program executes a set number of tasks, number of tasks a program executes in a set of time and scale of problems a program can tackle**.

## Thread vs Process
### Process
- Includes code, data and state information
- Independent instance of a running program
- Separate address space
- A computer can have hundreds of active processes at once, and an operating system's job is to manage all of them.
### Thread
- WIthin every process, there are one or more smaller sub elements called threads, kinda like tiny processes.
- Each of those threads is an independent path of execution, a different sequential of instructions
- It can only exist as part of a process, 
- Operating system schedules threads for execution
### Inter-Thread Communication
- Threads that belong to the same process share the process's address space
- Given same resource in memory including the program executable code and data.
### Inter-Process Communication (IPC)
- Ways to communicate and share data between processes, as it requires more work than communicating between threads.
	- Sockets and pipes
	- Shared memory
	- Remote procedure calls (RPC)
### Threads vs Processes
- Threads are considered as light-weight than processors, require less overhead to create and terminate
- Operating system can swtich between threads belong to single process faster than different processes

## Java Virtual Machine (JVM)
- Each Java application executes within its own instance of the JVM
	- If you run multiple Java application at same time, they'll each execute in a separate JVM process with their own independent memory space
- Each JVM instance is a separate, independent process
### Demo
- create additional 6 threads and execute
- we see there are 7 threads in runtime, but in resource monitor of our process **java.exe**, there are 24 threads display, this is because JVM creates additional threads in the background to handle things like garbage collection and runtime compilation.
	- From the program perspective, running within the JVM, it only had 7 threads.
	- From the operating system that's running the JVM, it sees those background threads for a total of 24

## Concurrent vs Parallel
### Concurrency
- Ability of a program to be broken into parts that can run independently of each other
	- These parts can be executed out of order or partially out of order without affecting the end result.
- About how a program is structured and the composition of independently executing processes.
#### Example
- Salad Receipt
	1. Chop Lettuce
	2. Chop Cucumbers
	3. Chop Tomatoes
	4. Chop Onions
	5. Mix Vegs
	6. Add dressing
- Decompose some steps into a collection of concurrent tasks, because the relative order in which we do them doesn't matter, they're order independent
	- Chop Lettuce
	- Chop Cucumbers
	- Chop Tomatoes
	- Chop Onions
#### Single Processor
- Like one knife and one cutting board, for two threads like chopping lettuce and cumcumbers, we need to swtich the knife between two threads
- This is concurrent execution, because our two independent processes overlap in time
- If it swaps places and take turns more frequently, it'll create the illusion that we're executing simultaneously on our single processor, but this is **NOT TRUE Parallel Execution**
#### Parallel Hardware
- In order to make it as true parallel execution, like we need another set of knife and cutting board. In computer system, we have couple of options
	- Multi-Core Processors
	- Graphics Processing Unit
	- Computer Cluster
### Concurrency vs Parallelism
#### Concurrency 
- The Program structure
- Figure out a way to be able to **DEAL** with multiple things at once.
#### Parallelism
- Simultaneous Execution
- **Actually DOING** multiple things at once
### I/O Devices
- Mouse, Keyboard and GPU needs to execute concurrently
- In multi-core system, the execution of those drivers might get split amongst the available processors.
- However, since I/O operations occurs rather infrequently, relative to the speed at which computer operates, we don't really gain anything from parallel execution. Those separate tasks can run just fine on a single processor and we wouldn't feel difference

## Execution Scheduling
### Scheduler
- Operating system function that assigns processors and threads to run on available CPUs
- Scheduler makes it possible for multiple programs to run concurrently on a single processor.
- When a process is created and ready to run, it gets loaded into memory and placed in the ready queue.
#### Example
- Scheduler is like a head chef to tell other chef what and when to execute a chopping task.
### Context Switch
- The Operating System has to store the state of a process or thread that was running, so it can be resumed later.
- It has to load the saved state for the new process or thread to run
### Scheduling Algorithms
- First come, first served
- Shortest job next
- Priority
- Shortest remaining time
- Round-robin
- Multiple-level queues
### Scheduling Goals
- Maximize throughput
- Maximize fairness
- Minimize wait time
- Minimize latency
### We cannot control
- Scheduling is not always consistent, we shouldn't rely on it for threads to be executed in equal amount of time or in a particular order

## Thread Lifecyle
### Main Thread
- When a new process or program begins running, it will start with just one thread, the Main Thread.
- Main thread can then start or spawn additional threads to help out, referred to as its child threads, which are part of the same process, but execute independently to do other tasks.
	- Those child threads can then spawn their own child threads if needed.
- As each of those threads finish executing, they'll notify their parent and terminate, with the Main thread usually the last to finish execution.
### 4 Thread Status
#### New
- Assigning the code it's going to execute.
- Doesn't take any CPU resource
- Some language explicity requires you to start a thread after creating it.
#### Runnable
- The Operating System can schedule the thread to execute
- Through context switch, the thread will get swapped outwith other threads to run on one of the available processors.
#### Blocked
- When a thread needs to wait for an event to occur, like an external input or a timer, it goes into a block state while it waits.
- When a thread is blocked, it's not using any CPU resources.
- The Operating system will return it to the runnable state when condition gets resolved, and that will free up the processor for other threads to use.
##### join()
- Some thread may eventually reach a point where it needs to wait until one of its children threads has finished for me to continue on
- When called with `join()`, my thread will enter a block state, waiting until another thread is done.
- For example, below program shows we started both threads, and then use the `join()` method to wait until they're both done.
```java
Thread barron = new Shopper();
Thread olivia = new Shopper();
barron.start();
olivia.start();
barron.join();
olivia.join
```
#### Java Thread States (additional 2)
##### WAITING
- A thread is waiting indefinitely for another thread to perform a particular action.
##### TIMED_WAITING
- A thread will wait for another thread to perform a action for up to a specific waiting time
#### Terminated
- Thread goes to terminated state when it either completed its execution, or is abnormally aborted.
## Java Thread
### Basic Property
- Get Current Thread	: 
	- `currentThread()`
	- returns a reference to the currently executing thread object
- Thread ID
	- Postivie, long number
	- Unique identifier for every thread
	- `long getID()`
	- After a thread terminates, that ID number will be available for other new threads to reuse.
- Thread Name
	- String for identification
	- Multiple threads can have the same name but not same thread ID
	- Default name structure: Thread-N, where N is a postive integer
	- `void setName(String name)` you can change it after it's been created and get it using `String getName()`
- Thread Priority
	- Tells the JVM thread scheduler when each thread should run relative to other threads.
	- Priority values range from 1 to 10 as lowest to highest
	- `void setPriority(int newPriority)`
	- `int getPriority()`
- No Parent Thread Reference
	- Threads **do not** maintain a reference to their parent thread
	- Reason is it can enable the parent thread to be garbage collected by the JVM to reclaim memory, which would not be possible if the child thread was holding a reference to it.
### Creating Threads 
#### Extend the Thread class 
- By extending the Thread class, you can override its run method to provide your own custom code for the Thread to execute.
```java
public class ChefOlivia extends Thread {
	@Override
	public void run() {}
}
```
- Then you can create instance of your subclass, and run them as threads.
```java
Thread olivia = new ChefOlivia();
```
#### Runnable interface
- Interface for a class that will be executed by a thread
	- The **Class Thread** implements the Runnable Interface
```java
public class ChefOlivia implements Runnable {
	public void run() {}
}
```
- Defining a class that implements the Runnable interface simply requires you to have a `void run()` method
	- Similar to the `run()` we override when extending the Thread class
- Then you can create a instance of Thread by passing your subclass instance as the target for the Thread class constructor
```java
Thread olivia = new Thread(new ChefOlivia());
```
#### Thread vs Runnable
##### Extending Thread class
- Cannot extend additional classes as Java does not allow multiple inheritance
- When instantiate a class itself, each instance is a unique separate object
##### Implementing Runnable interface (Recommend)
- Can implement other interfaces and extend another class
- Can initiate the runnable object once, and then create multiple Threads from it. 
	- Any instance variable in the Runnable will be used and affected by all of the Threads.
- Multiple threads can share a single Runnable object, which has **the benefit of reducing memory usage comparing to creating separate Thread objects for each Thread**
### Daemon Thread
#### E.g: Garbage Collector
- A form of automatic memory managemet that runs in the background and attemps to relcaim garbage or memory that's no longer being used by the program.
#### What if GC was created/spawed from current thread
- While a function thread is executing, it spaws a separate child thread to provide the garbage collection service, the function thread would work fine until it's ready to finish executing.
- When the function thread is ready to exit the program but it **can't because it's child thread, the GC is still running**. the function thread will be stuck forever if that child thread is not terminating, and process will never terminate
#### Daemon Thread
- Threads that are performing background tasks, like garbage collection, can be detached from the main program by making them what's called a daemon thread.
- A thread that **does not prevent the process from terminating**
- By default, threads are created/spawned as **non-daemon**.
- When New Thread is created, it will **inherit daemon status from their parent**.
- use `setDaemon(true)` on Thread object to change status before starting that thread
- When JVM halts, any remaining daemon threads are **abandoned abruptly**
#### Why need Daemon Thread
- So that when main thread is finishing executing and there aren't any non-daemon threads left running, this process can be terminated, and the daemon thread will terminate with it.
- Since that Daemon Thread may terminate abruptly with the process, it **doesn't have a chance to gracefully shut down** and stop from what it was doing.
- That's fine from a garbage collection routine because all of the memory this process was using will **get cleared as part of terminating it**
- However, if the daemon thread is doing some I/O operation, like writing to a file, then terminating in the middle of that operation could end up corruptting data
	- If you detach a thread to make it a background task, make sure it won't have any negative side-effects!
## Quiz
1. A Java thread can be turned into a daemon thread after it has been started. **False**
2. If a daemon thread in Java creates another thread, that child thread will **inherit the daemon status of its parent thread.**
3. Why would you use daemon threads to handle continuous background tasks?
- The daemon thread will **not prevent the program from terminating when the main thread is finished**.
4. Why can it be risky to use a daemon thread to perform a task that involves writing data to a log file?
- The log file could be corrupted.
- A daemon thread will be abruptly terminated when the main thread finishes. If that occurs during a write operation the file could be corrupted.
5. Which method must you include when defining a class that implements the Runnable interface?
- void run()
6. When defining a new Java class, which of these is NOT an advantage of implementing Java's Runnable interface rather than inheriting from the Thread class and overriding the run() method?
- The class can inherit from multiple other classes.
- Java classes can only inherit from one other class. Java does not support multiple inheritance.
7. What two states do Java threads have in addition to the standard four thread states? (NEW, RUNNABLE, BLOCKED, TERMINATED)
-   WAITING and TIMED_WAITING
8. Why would ThreadA call the ThreadB.join() method?
- **ThreadA needs to wait until after ThreadB has terminated to continue**.
- For example, Thread A could be Main Thread and Thread B would be a new instance of Thread Object
9. A thread that calls the join method on another thread will enter the **BLOCKED** state until the other thread finishes executing.
10. Why do you have to start a thread after creating it?
- Threads do not automatically run when instantiated.
11. You can safely expect threads to execute in the same relative order that you create them. **FALSE**
12. In most operating systems, the **operating system** determines when each of the threads and processes gets scheduled to execute.
13. It is possible for two tasks to execute **concurrently** using a single-core processor.
- Concurrent tasks can take turns to execute on the same processor.
- Cannot execute in parallel
14. Which of these applications would benefit the most from parallel execution?
-  math library for processing large matrices
	- Mathematical operations on matrices are computationally intensive and therefore well suited to benefit from parallel execution.
- Following are **NOT** benefit from **parallel** execution
	- The graphical user interface (**GUI**) should be structured for **concurrency**, but **as an I/O bound task**, it will generally run as well with parallel execution as without.
	- System logging application that frequently writes to a database, The logging application should execute concurrent to other programs, but the access times to write to the database will be a limiting factor in it's execution speed. It is **I/O bound and therefore will not benefit significantly from parallel execution**.
15. Concurrent tasks execute at the same time. **False**
- Concurrency describes the structure that enables a program to execute in parallel (given the necessary hardware), but a concurrent program is not inherently parallel.
16. The operating system assigns each process a unique **process ID number**.
17. A hyperthreaded processor with eight logical cores will usually provide **lower** performance compared to a regular processor with eight physical cores.
- Hyperthreading takes advantage of unused parts of the processor, so if one thread is paused or isn't using a certain resource, then the other thread may be able to use it. 
- Under certain workloads, that can create performance improvements, but it’s highly application dependent.
18. If you run multiple Java applications at the same time, they will execute in **separate JVM processes**
- When you run a Java application, it executes within its own instance of the Java Virtual Machine (or JVM), and the operating system treats that instance of the JVM as its own independent process.
19. Processes **require more overhead** to create than threads.
20. A **process** contains one or more **threads**.
21. Every thread is independent and has its own separate address space in memory. **FALSE**
- Threads that belong to the same process share that process's address space.

## Data Race
### Problem occurs when
- Two or more concurrent threads access the same memory location
- At least one thread is modifying it
### Read-Modify-Write
- As concurrent threads, it's up to the operating system to schedule when each thread get to execute.
- Right after Thread I read the value from that shared memory, Thread I gets paused. Thread II becomes active and changed the number, then Thread I become active and at this point, Thread  I was operating with old data in its local memory and overrite the value that was updated by Thread  II in the shared memory
### Preventing Data Races
- Pay attention whenever two or more threads access the same resource
## Mutual Exclusion
### Critical Section/Region
- Part of a program that accesses a shared resource, such as a data structure memory, or an external device.
- It may not operate correctly, if multiple threads concurrently access it.
- This critical section needs to be protected, so that it only allows one thread or process to execute in it at a time.
### Lock
- A mechanism to implement mutual exclusion
- Only one thread or process can possess at a time
- Used to prevent multiple threads from simultaneously accessing a shared resource, **forcing them to take turns**
- For example, like we only have one Lock, called pencil between two threads who tries to update a single garlicCount 
	- Now, Thread II will wait to execute until Thread I finishes its `run()` method at 5 times.
	- Totally the program takes about 500ms * 10 = 5 seconds to finish executing
	- Since each thread does its thinking while holding onto the pencil, the other thread is waiting outside the critical section during that time, 
```java
public class Shopper extends Thread {
	static int garlicCount = 0;
	static Lock pencil = new ReentrantLock();
	public void run() {
		pencil.lock();
		for (int i = 0; i < 5; i++) {
			garlicCount++;
			System.out.println(Thread.currentThread().getName() + " is thinking.");
			try {
			    Thread.sleep(500);
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
		pencil.unlock();
	}
}
```
- For another example, since above code the critical section is way bigger than it needs to be, I only really need to protect `garlicCount++`
	- Now program will run twice as fast, because the threads aren't holding onto the pencil while they're busy thinking
	- Thinking messages appear in paris, and only takes about 500ms * 5 = 2.5 seconds to finish
```java
public class Shopper extends Thread {
	static int garlicCount = 0;
	static Lock pencil = new ReentrantLock();
	public void run() {
		for (int i = 0; i < 5; i++) {
			pencil.lock();
			garlicCount++;
			pencil.unlock();
			System.out.println(Thread.currentThread().getName() + " is thinking.");
			try {
			    Thread.sleep(500);
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}
```
### Atomic Operations
- The operation to acquire a lock, **uninterruptible**
- It's always executed as a single, indivisible action, relative to other threads
- Appears to happen instantaneously, **cannot** be interrupted by other concurrent threads.
- For example
```java
public class Shopper extends Thread {
	static AtomicInteger garlicCount = new AtomicInteger(0);
	static Lock pencil = new ReentrantLock();
	public void run() {
		for (int i = 0; i < 5; i++) {
			 garlicCount.incrementAndGet();
		}
	}
}
```
### Acquiring a Lock
- If lock is already taken, block/wait for it to be available
- Don't forget to unblock
### Tips
- Keep protected sections of code as short as possible
- Only protect the code sections which needs to use the shared resource, not anything else.
## Sychronized
### Intrinsic Locks
- Every Java Object has an intrinsitc lock associated with it
- A thread that needs exclusive access to an object's fields has to acquire that object's lock before accessing them and then release that intrinsic lock when it's done
### Sychronized Method
- Add **synchronized** keyword to method declaration
- When a thread **invokes a synchronized method, it automatically acquires the intrinstic lock for that method object, that will prevent other threads that invoke a synchronized method on the same object from interleaving exection**
- When a thread tries to invoke a synchronized method while another thread is executing a synchronized method for that same object, **the second thread will suspend execution until the first thread is done and exits**.
- For example, below that I mark synchronized of the method with **static**, 
	- this method is associated with the shopper class, and not a specific instance of shopper.
	- By doing so, when either thread invokes the synchonized addGarlic method, it will acquire the intrinstic lock that's associated with the class object
	- If removed the static keyword, then each shopper instance will invoke their own instance of the `addGarlick()` method,  which associated with their own object's intrinstic lock, which would not be working as expected
```java
public class Shopper extends Thread {
	static int garlicCount = 0;
	private synchronized void addGarlic() {
		garlicCount++;
	}
	public void run() {
		for (int i = 0; i < 10_000_000; i++) {
			addGarlic();
		}
	}
}
```
### Sychronized Statement
- Speicfy the object to provide the intrinsic lock 
```java
synchronized (object) {
	// protected code
}
```
- Before a thread can execute the code contained within the synchronized statement, it must first acquire the intrinsic lock associated with the specific object
- Then when the thread is done, it will release its hold on that lock.
- For example, below both threads will be acquiring and releasing the same intrinsic locks assocaited witht the shopper class before and after they increment the garlicCount.
```java
public class Shopper extends Thread {
	static int garlicCount = 0;
	public void run() {
		for (int i = 0; i < 10_000_000; i++) {
			synchronized (Shopper.class) {
				garlicCount++;
			}
		}
	}
}
```
- For another example, if I replace the synchonized object from `Shopper.class` with **this**,  I'll get incorrect result.
	- Because each of the shopper threads is acquiring and releasing the intrinsic lock associated with their own instance.
	- They are not synchronized to the same object now so the data race occurs.
```java
public class Shopper extends Thread {
	static int garlicCount = 0;
	public void run() {
		for (int i = 0; i < 10_000_000; i++) {
			synchronized (this) {
				garlicCount++;
			}
		}
	}
}
```
- For another example, if I replace the synchonized object with **garlicCount ** and change its type from `int` to `Integer` because I need to use an object for synchronization, and `garlicCount` is currently just a primitive int variable,  I'll still get incorrect result.
	- Because `Integer` object is **Immutable**, once you create an Integer instance, you cannot change its value 
	- So that what happens is, every time a thread executes the `garlicCount++` operation, Java instantiate a new integer object which will have a different object ID.
	- So each time the thread loops back around and executes that synchronized statement, they're actually be using a different object for the intrinsic lock, which are not synchronized at all.
```java
public class Shopper extends Thread {
	static Integer garlicCount = 0;
	public void run() {
		for (int i = 0; i < 10_000_000; i++) {
			synchronized (garlicCount ) {
				garlicCount++;
			}
		}
	}
}
```
### Locks vs Atomic variables vs Synchronized methods vs Synchronized statements
#### Synchronized method and statements
- **Easier to implement and prevents many pitfalls** that can occur when using **Locks**
- Good default option
#### Locks
- There maybe times when you have to work with locks in more complex ways, perhaps acquiring and releasing a series of locks in a nested or hand over hand manner. which is not possible with synchronized statement.
- Provide more flexibility to be acquired and released in different scopes and to be acquired and released in any order.
## Quiz
1. Why should you avoid using Java's synchronized statement on an immutable object such as an Integer?
- If you change that variable's value you will be synchronized to a different object as Immutablility
2. Which of these is an advantage of using the "synchronized" methods in Java instead of creating explicit Locks?
- It is easier to implement and can prevent many common pitfalls of using Locks.
3. Why are Java's Atomic variable classes unique?
-   Their values update as a single, uninterrupted operation which makes them "thread-safe."
4. What happens if Thread A calls the lock() method on a Lock that is already possessed by Thread B?
- Thread A will block and wait until Thread B calls the unlock() method.
5. How many threads can possess a Lock at the same time?
- 1
6. What does it mean to protect a critical section of code with mutual exclusion?
- Prevent multiple threads from concurrently executing in the critical section.
7. Using the ++ operator to increment a variable in Java executes as `_____` at the lowest level.
- multiple instructions (Read & Update & Write)
8.  In the Java program to demonstrate a data race, why did the data race only occur when each of the threads were incrementing a shared variable a large number of time?
- The large number of write operations on the shared variable provided more opportunities for the data race to occur.
9. Why can potential data races be hard to identify?
- The data race may not always occur during execution to cause a problem. 
10. Which of these scenarios does NOT have the potential for a data race?
- Two threads are both reading the same shared variable.
## Lock
### Deadlock
- If a thread tries to lock a mutex that it's already locked, it'll enter into a waiting list for that mutex, which results in something called a deadlock
- All processes and threads are unable to continue executing
### Reentrant Lock
- There may be times when a program needs lock a mutex multiple times before unlocking it. In that case, you should use a reentrant lock
- A particulr type of mutex that can be locked multiple times by the same process or thread.
- Internally, reentrant mutex keeps track of how many times it's been locked by the owing thread.
- It has to be unlocked an equal number of times as it was locked to fully release it before another thread can lock it.
- Make things easier, you don't need to worry about as much about what's already been locked, and it maeks easier to retrofit locks into existing code.
- One use case that a reentrant lock are really needed is when writing recursive functions.
- Common Terms:
	- Reentrant mutex
	- Reentrant lock
	- Recursive mutex
	- Recursive lock
### Java Class Implementing Lock Interface
- ReentrantLock
	- `getHoldCount()` to check how many lock are performed on current reentrant lock
- ReentrantReadWriteLock.ReadLock
- ReentrantReadWriteLock.WriteLock
### TryLock
- A non-blocking lock/acquire method for mutex `boolean tryLock()`
- It returns immediately and one of two things will happen, let the thread know whether or not it was successful in acquiring the lock.
	- If the mutex is available, lock it and return TRUE
	- If the mutex is unavailable, immediately return FALSE
- Much faster than traditional `lock()` as when one of the threads gets its turn in the critical section with the `tryLock` method in place, the other thread is able to jump past that section of code and is freed up to accomplish other useful things.
- For example, it's like you have a party going on in your house but only one restroom, if you need to go to the restroom, knock the door and know it was occupied/locked, you can either stand there and wait until someone come out or you may save time and do something else. As long as the restroom is available, anyone can access it from their own activities.
### Read-write Lock/Shared Mutex
- It's Okay to allow multiple threads read from the same shared resource as long as no one else can change it
- When we use a basic lock or a mutex or protect the sahred resource, we limit access so that only one of the threads can use it at a time. Regardless whether that thread is reading, writing or both. This works but not efficient when most of the threads is only to read
- A thread trying to acquire the lock in write mode can't do so as long as it's still being held by any other threads in the read mode. so it has to be waited.
- Read-write lock can lock in two ways
	- It can be locked in a shared read mode that allows multiple threads that only need to read simultaneously to lock it
	- It can be locked in an exclusive write mode that limits access to only one thread at a time, allowing that thread to safely write to the shared resource.
#### ReadWriteLock Interface
- Implemented by ReentrantReadWriteLock class
- `readLock()` and `writeLock()`
	- both method returns a `Lock` oject which we can then call the lock and unlock methods on.
## Quiz
1. How many threads can possess the ReadLock while another thread has a lock on the WriteLock? **0**
2. What is the maximum number of threads that can possess the WriteLock of a ReentrantReadWriteLock at the same time? **1**
3. What is the maximum number of threads that can possess the ReadLock of a ReentrantReadWriteLock at the same time? **not limit**
4. Which of these scenario describes the best use case for using a ReadWriteLock?
- Lots of threads need to read the value of a shared variable, but only a few thread need to modify its value.
5. What happens when a thread calls Java's tryLock() method on a Lock that is NOT currently locked by another thread?
- The method immediately returns true
6. What is the difference between the tryLock() and the regular lock() method in Java?
- `tryLock()` is a **non-blocking version of the lock()** method
7. Why is the tryLock() method useful?
- It enables a thread to execute alternate operations if the lock it needs to acquire is already taken.
8. Which statement describes the relationship between Lock and ReentrantLock in Java?
- ReentrantLock is a class that implements the Lock interface.
9. How many times must a thread unlock a ReentrantLock before another thread can acquire it?
- As many times as that thread locked it
10. A ReentrantLock can be locked **multiple times by the same thread**
## Deadlock
### How did it happen
- Each member is waiting for another member to take action
- For example Dining Philosipher, you and I shared one order of sushi, and each of us only has one chopstick, at same time both of us wants to grab a sushi so we are picking up the closet chopstick to us and waiting for the other to release one.
#### How to fix
- we can prioritize the two chopstick so if the 1st/higher priority chopstick is taken/locked, the other thread cannot continue access and acquire the 2nd/lower priority chopstick
```java
public void run() {
        while(sushiCount > 0) { // eat sushi until it's all gone

            // pick up chopsticks
            firstChopstick.lock();
            secondChopstick.lock();

            // take a piece of sushi
            if (sushiCount > 0) {
                sushiCount--;
                System.out.println(this.getName() + " took a piece! Sushi remaining: " + sushiCount);
            }

            // put down chopsticks
            secondChopstick.unlock();
            firstChopstick.unlock();
        }
    }
public static void main(String[] args) {
        Lock chopstickA = new ReentrantLock();
        Lock chopstickB = new ReentrantLock();
        Lock chopstickC = new ReentrantLock();
        new Philosopher("Barron", chopstickA, chopstickB).start();
        new Philosopher("Olivia", chopstickB, chopstickC).start();
        new Philosopher("Steve", chopstickA, chopstickC).start();
    }
```
#### Lock Ordering
- Ensure locks are always taken in the same order by any thread
#### Lock Timeout
- Put a timeout on lock attempts
- If a thread is not able to succesfully acquire all of the locks it needs within a certain amount of time, it would 
	- back up, free all of the locks that it did take
	- then wait for a random amount of time'
	- try again!
#### Liveness
- A set of properties that require concurrent programs to make progress
- Some processes or threads may have to take turns in a critical section
### Abandoned Lock
#### How did it happen
- For example, one of us grab the two chopstick without grabbing the sushe, he went away to do something else
- If one thread or process acquires a lock, and then terminates **because of some unexpected reason**, it may not automatically release the lock before it disappears
- That leaves other task stuck waiting for a lock that will never be released
#### How to fix
- Put the **critical section in try block** and **release lock part in a finally block**
- For example
```java
    public void run() {
        int sushiEaten = 0;
        while(sushiCount > 0) { // eat sushi until it's all gone

            // pick up chopsticks
            firstChopstick.lock();
            secondChopstick.lock();

            try {
                // take a piece of sushi
                if (sushiCount > 0) {
                    sushiCount--;
                    sushiEaten++;
                    System.out.println(this.getName() + " took a piece! Sushi remaining: " + sushiCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // put down chopsticks
                secondChopstick.unlock();
                firstChopstick.unlock();
            }
        }
        System.out.println(this.getName() + " took " + sushiEaten);
    }
```
### Starvation
- Starvation occurs when a thread is unable to gain access to a necesssary resource therefore unable to make progress.
- If another greedy thread is frequently holding a lock on the shared resource, then the starved thread won't get a chance to execute.
#### How did it happen
- How different thread priorities get treated will depend on the operating system. But, generally higher priority threads will be scheduled to execute more often, and that can leave low priority thread feeling hungry
- Having too many concurrent threads. For example, a web server that created new threads to handle **a huge number of incoming requests, some of those requests may never get processed**, and that would lead to some impatient and angry user on the other end.
- For example, below Olivia would get more sushi because she only acuqire chopstick B and C, while other two people both needs chopstick A
```
Lock chopstickA = new ReentrantLock();
Lock chopstickB = new ReentrantLock();
Lock chopstickC = new ReentrantLock();
new Philosopher("Barron", chopstickA, chopstickB).start();
new Philosopher("Olivia", chopstickB, chopstickC).start();
new Philosopher("Steve", chopstickA, chopstickC).start();
```
- For another example, if we want sushi to be distributed more even, we can have all three people **acquire same lock as A & B**
```
new Philosopher("Barron", chopstickA, chopstickB).start();
new Philosopher("Olivia", chopstickA, chopstickB).start();
new Philosopher("Steve", chopstickA, chopstickB).start();
```
### Livelock
- Similar as deadlock, that multiple threads or processes are actively responding to each other to resolve conflict, but that prevents them from making progress, the program will never reach to an end.
- Difference is those threads are actively trying to resolve the problem.
#### How did it happen
- Often caused by argorithms that are intended to detect and recover from deadlock. 
- If one or more processor thread takes action to resolve the deadlock, then those threads can end up being overly polite and stuck in a livelock, 
#### How to fix
- Ensure that only one process takes action, chosen by priority or some other mechanism like random selection.
- For example, implement a randomized mechanism to determine which thread goes first.
```java
public void run() {
        while(sushiCount > 0) { // eat sushi until it's all gone

            // pick up chopsticks
            firstChopstick.lock();
            if (! secondChopstick.tryLock()) {
                System.out.println(this.getName() + " released their first chopstick.");
                firstChopstick.unlock();
                try {
                    Thread.sleep(rps.nextInt(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    // take a piece of sushi
                    if (sushiCount > 0) {
                        sushiCount--;
                        System.out.println(this.getName() + " took a piece! Sushi remaining: " + sushiCount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // put down chopsticks
                    secondChopstick.unlock();
                    firstChopstick.unlock();
                }
            }
        }
    }
```
## Quiz
1. The threads in your program are clearly not making progress. How might you determine if it is due to a deadlock or a livelock?
- Use the Resource Monitor to investigate the program's CPU usage to see if it is actively executing.
2. Which of these is a possible strategy to resolve a livelock between multiple threads?
- Implement a randomized mechanism to determine which thread goes first.
3. Unlike during a deadlock, the threads in a livelock scenario are  **actively executing without making useful progress**
4. Only the lowest priority threads will be at risk of resource starvation. **FALSE**
- If there is a large enough number of threads competing for resources (such as execution time), then some threads may still be starved even if they all have the same priority
5. Starvation occurs when a thread is **perpetually denied resources due to competition with other threads**
6. Why should you use Java's try/catch/finally blocks when using a Lock to protect a critical section of code?
- Putting the unlock() method within the finally-block guarantees the Lock will be unlocked even if the thread crashes.
7. What happens when a thread terminates while still holding onto a Lock?
- The Lock will be stuck in the locked state forever and no other threads will be able to acquire it.
8. Which of these is a possible strategy to prevent deadlocks when multiple threads will need to acquire multiple locks?
- Prioritize the locks so that all threads will acquire them in the same relative order.
9. The Dining Philosophers scenario demonstrates the deadlock situation that can occur when **multiple threads** need(s) to acquire **multiple locks**.
## Condition Variable
### Why need Condition Variable?
- Repeatly acquiring and releasing the lock to check for a certain condition to continue.
#### Limitation of using mutex
- The mutex restrict multiple threads from taking soup at the same time, but the mutex alone doesn't give our threads a way to signal each other to synchronzie our action.
- To do that, we need another mechanism called **Conditional Variable**
### What is Condition Variable
- Serves as a queue or container or threads that are waiting for a certain condition to occur.
- Thinking of it as a place for threads to wait and be notified.
- The conditional variable is associated with a mutex, and work together to implement a higher level construct called a monitor.
### Monitor
- Protects section of codes with mutual exclusion
- Provide ability for threads to wait or block until a certain condition has become true
- Along with mechanism to signal those waiting threads when their condition has been met.
- For example, thinking of a interview room as a mutex, and multiple candidate waiting outside in the waiting room until condition met (name called)
### Three Operations
#### wait
- Before using the conditional variable, I first need to acquire the mutex associated with it, check for my condition, see if it's not my turn for the condition, then becomes in **wait** operation
- Automatically release lock on the mutex
- Put current thread to sleep or paused state and place it into a queue waiting for another thread to signal that it's some other thread's turn
- Reacquire lock when woke up
#### signal
- Use the `signal` operation to wake up one thread from condition variable queue, so it can acquire the lock
- Also see this operation called `notify` or `wake`
#### broadcast
- Similar to `signal` operation except that it wakes up all of the threads in the waiting/condition variable queue
- Also called `notify all` or `wake all`
### Use case
- A more common use case that requires multiple condition variables, is implementing a shared queue or buffer.
#### Shared Queue or Buffer
- If multiple threads will be putting items in a queue and taking them out, it needs a mutex to ensure that only one thread can add or remove items from it at a time
- For that, we can use two condition variables
	- If a thread tries to add an item to the queue, which is already full, then it can wait on a condition variable to indicate when the **BufferNotFull**
	- If a thread treis to take an item but the queue's empty, then it can wait on another condition **BufferNotEmpty**
- These conditional variables enable threads to signal each other when the state of the queue changes.
### Using a Condition Variable
- Creating a new condition method on tha that mutex/lock object
- For example
```java
private static Lock slowCookerLid = new ReentrantLock();
private static Condition soupTaken = slowCookLid.newCondition();
```
- Basic condition pattern invovles
	- locking the mutex
	- using a while loop to check if the condition we're looking for is true
		- If not true, wait on the condition variable for another loop iteration
		- if true, we'll continue pass the loop to execute the critical section of code
	- finally release the lock
```java
mutex.lock();
while(some condition is not true) {
    conditionvariable.await();
}
// execute the critical section
mutex.unlock();
```
### Signaling a Condition Variable
- Making sure you have a lock on the mutex
- Do something to change the state of the condition
- unlock the thread so another mutex can proceed to use it.
```
mutex.lock();
// do something that changes state for condition
conditionalVariable.signal();
mutext.unlock()
```
#### Important
- If you only need to notify one of the waiting threads, and you don't care which one it is, then the basic signal method works fine
```java
class HungryPerson extends Thread {

    private int personID;
    private static Lock slowCookerLid = new ReentrantLock();
    private static int servings = 11;
    private static Condition soupTaken = slowCookerLid.newCondition();

    public HungryPerson(int personID) {
        this.personID = personID;
    }

    public void run() {
        while (servings > 0) {
            slowCookerLid.lock();
            try {
                while ((personID != servings % 2) && servings > 0) { // check if it's not your turn
                    System.out.format("Person %d checked... then put the lid back.\n", personID);
                    soupTaken.await();
                }
                if (servings > 0) {
                    servings--; // it's your turn - take some soup!
                    System.out.format("Person %d took some soup! Servings left: %d\n", personID, servings);
                    soupTaken.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                    slowCookerLid.unlock();
            }
        }
    }
}

public class ConditionVariableDemo {
    public static void main(String args[]) {
        for (int i=0; i<2; i++)
            new HungryPerson(i).start();
    }
}
```
- If you want a specific thread to wake up and see whether it's their turn, relying on the signal method to wake up the right thread, will lead the program getting stuck, then we need to use **signalAll** method
```java
class HungryPerson extends Thread {

    private int personID;
    private static Lock slowCookerLid = new ReentrantLock();
    private static int servings = 11;
    private static Condition soupTaken = slowCookerLid.newCondition();

    public HungryPerson(int personID) {
        this.personID = personID;
    }

    public void run() {
        while (servings > 0) {
            slowCookerLid.lock();
            try {
                while ((personID != servings % 5) && servings > 0) { // check if it's not your turn
                    System.out.format("Person %d checked... then put the lid back.\n", personID);
                    soupTaken.await();
                }
                if (servings > 0) {
                    servings--; // it's your turn - take some soup!
                    System.out.format("Person %d took some soup! Servings left: %d\n", personID, servings);
                    soupTaken.signalAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                    slowCookerLid.unlock();
            }
        }
    }
}

public class ConditionVariableDemo {
    public static void main(String args[]) {
        for (int i=0; i<5; i++)
            new HungryPerson(i).start();
    }
}
```
## Producer-Consumer
### Pattern
- Producer
	- One or more threads or processors act as producer
	- Add elements to shared data structure
- Consumer
	- One or more threads or processors act as consumer
	- Remove elements from shared data structure
### First-In-First-Out (FIFO)
- Items are removed in the same order that they're added to the queue
### Multi-thread with Producer-Consumer
- When multiple threads are operating in this type of producer-consumer situation, it poses several challenge for synchronization
	1. Queue is shared resource, so we'll need something to enforce mutual exclusion of producers and consumers
	2. Make sure that only one thread can use it at a time to add or remove items.
	3. Make sure to prevent producers from trying to add data to a full queue
	4. Make sure to prevent consumers from trying to remove data from an empty queue
- Consider Average rate of production < Average rate of consumption
#### Unbounded Queue
- Queue with an unlimited capacity
- It is still limited by memory
#### Pipeline
- A chain of processsing element arranged so that the output of each element is the input to the next one
- For example, Scoop Soup -> Add Spice -> eat Soup
- A series of producer-consumer pair with some buffer like a queue between each consecutive element, Produce -- Queue --> Consumer + Producer -- Queue --> Consumer
#### Java ArrayBlockingQueue
- A concrete class implements the blocking queue interface.
- It's a bounded queue that uses an array under the hood to hold a finite number of elements
- It's**thread safe** since it implements the blocking queue interface
### Java Example
```java
class SoupProducer extends Thread {

    private BlockingQueue servingLine;

    public SoupProducer(BlockingQueue servingLine) {
        this.servingLine = servingLine;
    }

    public void run() {
        for (int i=0; i<20; i++) { // serve 20 bowls of soup
            try {
                servingLine.add("Bowl #" + i);
                System.out.format("Served Bowl #%d - remaining capacity: %d\n", i, servingLine.remainingCapacity());
                Thread.sleep(200); // time to serve a bowl of soup
            } catch (Exception e) { e.printStackTrace(); }
        }
        servingLine.add("no soup for you!");
        servingLine.add("no soup for you!");
    }
}

class SoupConsumer extends Thread {

    private BlockingQueue servingLine;

    public SoupConsumer(BlockingQueue servingLine) {
        this.servingLine = servingLine;
    }

    public void run() {
        while (true) {
            try {
                String bowl = (String)servingLine.take();
                if (bowl == "no soup for you!")
                    break;
                System.out.format("Ate %s\n", bowl);
                Thread.sleep(300); // time to eat a bowl of soup
             } catch (Exception e) { e.printStackTrace(); }
        }
    }
}

public class ProducerConsumerDemo {
    public static void main(String args[]) {
        BlockingQueue servingLine = new ArrayBlockingQueue<String>(5);
        new SoupConsumer(servingLine).start();
        new SoupConsumer(servingLine).start();
        new SoupProducer(servingLine).start();
    }
}
```
## Semaphore
### What is it
- Another synchronization (signal) mechanism that can be used to control access to shared resources. Sort of like a mutex, but unlike a mutex
- A semaphore can allow multiple threads to access the resource at the same time
- It includes a counter to track how many times it's been acquired or released
#### acquire()
- As long as the semophore's count value is positive, any thread can acquire the semaphore, which then decrements the counter value.
- If counter reaches zero, then threads trying to acquire the semaphore will be blocked and placed in a queue to wait until it's available.
#### release()
- When a thread is done using the resource, it releases the semaphore which increasement the counter value
- If there are any other threads waiting to acquire the semaphore, they'll be signaled to wake up and do so.
#### Example
- A phone charger has two ports, that you can think of the semaphore has a value of two.
- When 2 people acquire both of these ports by plugging their phones, it decrements the semaphore's value to 0, meaning there is no more available ports to be used.
- A third person has to wait until one of the ports charging done and release the semaphore
### Counting Semaphore
- Value >= 0
- Used to track limited resources
	- Pool of connections
	- Item in a queue
### Binary Semaphore
- Value = 1 or 0
	- 0 represents locked
	- 1 represents unlocked
- Used similar to mutex by acquiring/releasing
### Mutex vs Semaphore
- A mutex can **only be unlocked by the same thread that originally locked it.**
- A semaphore can be acquired and released by different threads.
	- Any thread can increment the semaphore's value by releasing it or attempt to decrement of value by acquiring it.
### Product-Consumer Semaphore
- A **pair of semaphore** can be used in a similar way to condition variables to synchronze producer and consumer threads. Adding or removing elements from **a shared, finite queue or buffer**.
- One semaphore tracks the **number of items in the buffer**, shown here as `fillCount` as **0**
- Another semaphore tracks the **number of free spaces** as `emptyCount` as **6**
#### To add an element to the buffer
- Producer 
	1. First **acquire** the `emptyCount`, which **decrement** its value from **6 to 5**
	2. Then it **pushes** the item into the buffer
	3. Finally **release** the `fillCount` semaphore to **increment** its value from **0 to 1.**
- Consumer
	1. First **acquire** the `fillCount` to decrement its value from **1 to 0**
	2. Then it **removes** an item from the buffer
	3. Finally **increment** the `emptyCount` from **5 to 6** by **releasing** it
- Producer and Consumer **each acquire a different semaphore** as the first operation in their respective sequences,.
- If the Consumer tries to take an item when the buffer is empty, then when it tries to **acquire** that `fillCount` semaphore, **it'll block and wait until producer** adds an item and releases `fillCount`, which will then **signal the consumer to continue**.
- If the Producer tries to add an item to the buffer, then it goes to **acquire** the `emptyCount` semaphore, **it'll block and wait until a consumer** removes an item and **releases** the `emptyCount`.
### Java Example
#### Counting Semaphore
- At most 4 phones will be in charing
```java
class CellPhone extends Thread {

    private static Semaphore charger = new Semaphore(4);

    public CellPhone(String name) {
        this.setName(name);
    }

    public void run() {
        try {
            charger.acquire();
            System.out.println(this.getName() + " is charging...");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(this.getName() + " is DONE charging!");
            charger.release();
        }
    }
}

public class SemaphoreDemo {
    public static void main(String args[]) {
        for (int i =0; i < 10; i++)
            new CellPhone("Phone-"+i).start();
    }
}
```
#### Binary Semaphore
- Only one thread/phone will be charing, basically acting like a mutex
- Could replace with reentrant lock as well
```java
class CellPhone extends Thread {

    private static Semaphore charger = new Semaphore(1);

    public CellPhone(String name) {
        this.setName(name);
    }

    public void run() {
        try {
            charger.acquire();
            System.out.println(this.getName() + " is charging...");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(this.getName() + " is DONE charging!");
            charger.release();
        }
    }
}

public class SemaphoreDemo {
    public static void main(String args[]) {
        for (int i =0; i < 10; i++)
            new CellPhone("Phone-"+i).start();
    }
}
```
### Quiz
1. Which of these is a common use case for a counting semaphore?
- Track the availability of a limited resource.
2. In addition to modifying the counter value, what else does calling the semaphore's release() method do?
- Signal another thread waiting to acquire the semaphore.
3. What does the semaphore's release() method do to the counter value?
- Always increment the counter's value.
4. What does the semaphore's acquire() method do to the counter value?
- If the counter is positive, decrement its value.
5. What is the difference between a binary semaphore and a mutex?
- The binary semaphore can be acquired and released by different threads.
6. What happens if the producer puts elements into a fixed-length queue faster than the consumer removes them?
- The queue will fill up and cause an error.
7. Which architecture consists of a chained-together series of producer-consumer pairs?
- Pipeline
8. How should the average rates of production and consumption be related in a producer-consumer architecture?
- The consumption rate should be greater than or equal to the production rate.-
9. When should a thread typically signal a condition variable?
- After doing something to change the state associated with the condition variable but before unlocking the associated mutex
10. Why would you use the condition variable's signal() method instead of signalAll()?
- You only need to wake up one waiting thread and it does not matter which one.
11. Condition variables serve as a `holding place` for threads to `wait for a certain condition before continuing execution `.
12. Condition variables work together with which other mechanism serving as a monitor?
- A mutex
## Barriers
### Race
#### Data Race
- Can occur when two or more threads concurrently access the same memory location
- If at least one of those threads is writing to or changing that memory value, that can cause the threads to overwrite each other or ead wrong values.
- To prevent data race, we need to ensure mutual exclusion for the shared resource.
#### Race Condition
- A flaw in the **timing or ordering of a program's execution** that causes incorrect behavior
- The order of which threads get scheduled to execute, changes the final result.
- For example, even though we use a pencil as a mutex to protect against a data race, the potential of a race condition still exists, because the **order in which the threads exeute is not deterministic**.
	- Thread A runs before Thread B: (1 + 3) x 2 = 8
	- Thread B runs before Thread A: (1 x 2) + 3 = 5
- Unfortuantely, there is not a single catch all the way to detect race conditions.
- Searching for Race Condition
	- Sometimes putting sleep statemtns at different places throughout your code can help to uncover potential race conditions by changing the timing and therefore order in which threads can execute it.
### Java Example
```java
class Shopper extends Thread {

    public static int bagsOfChips = 1; // start with one on the list
    private static Lock pencil = new ReentrantLock();

    public Shopper(String name) {
        this.setName(name);
    }

    public void run() {
        if (this.getName().contains("Olivia")) {
            pencil.lock();
            try {
                bagsOfChips += 3;
                System.out.println(this.getName() + " ADDED three bags of chips.");
            } finally {
                pencil.unlock();
            }
        } else { // "Barron"
            pencil.lock();
            try {
                bagsOfChips *= 2;
                System.out.println(this.getName() + " DOUBLED the bags of chips.");
            } finally {
                pencil.unlock();
            }
        }
    }
}

public class RaceConditionDemo {
    public static void main(String args[]) throws InterruptedException  {
        // create 10 shoppers: Barron-0...4 and Olivia-0...4
        Shopper[] shoppers = new Shopper[10];
        for (int i=0; i<shoppers.length/2; i++) {
            shoppers[2*i] = new Shopper("Barron-"+i);
            shoppers[2*i+1] = new Shopper("Olivia-"+i);
        }
        for (Shopper s : shoppers)
            s.start();
        for (Shopper s : shoppers)
            s.join();
        System.out.println("We need to buy " + Shopper.bagsOfChips + " bags of chips.");
    }
}
```
### Barriers障碍
- To prevent our race condition from occurring, we need a way to synchronize our action so we execute our respective multiplication and addition operations in the correct order.
- A barriers is a stopping point for a group of threads from proceeding until enough threads have reached the barrier
### Java Example
#### CyclicBarrier
- It can be reused after the waiting threads are released
- Releases when certain number of threads are waiting
- Can be reset
- Constructor 
	- `new CyclicBarrier(value)`: takes an argument for the number of threads to wait on before the barrier releases.
- Method
	- `int getParties()` - Total number of threads needed to trip barrier
	- `int getNumberWaiting()` - Current number of threads waiting on barrier
	- `void reset` - Reset barrier to initial state
	- `boolean isBroken()` - Has a thread broken out since last reset 
- Use barriers to seperate those operations that 5 Olivia threads would execute before Barron threads start to execute
	- The Olivia threads will **execute before they await at the barrier**, whereas the Barron threads will **go straight to waiting at the barrier.**
	- Once all 10 threads have arrived at the barrier and are waiting on it, then the Barrier will release and the Barron threads will all execute their multiplication operations.
	- Result: (1 + 3 + 3 + 3 + 3 + 3) * 2 * 2 * 2 * 2 * 2 = 512
```java
class Shopper extends Thread {

    public static int bagsOfChips = 1; // start with one on the list
    private static Lock pencil = new ReentrantLock();
    private static CyclicBarrier fistBump = new CyclicBarrier(10);

    public Shopper(String name) {
        this.setName(name);
    }

    public void run() {
        if (this.getName().contains("Olivia")) {
            pencil.lock();
            try {
                bagsOfChips += 3;
                System.out.println(this.getName() + " ADDED three bags of chips.");
            } finally {
                pencil.unlock();
            }
            try {
                fistBump.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        } else { // "Barron"
            try {
                fistBump.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            pencil.lock();
            try {
                bagsOfChips *= 2;
                System.out.println(this.getName() + " DOUBLED the bags of chips.");
            } finally {
                pencil.unlock();
            }
        }
    }
}

public class BarrierDemo {
    public static void main(String args[]) throws InterruptedException  {
        // create 10 shoppers: Barron-0...4 and Olivia-0...4
        Shopper[] shoppers = new Shopper[10];
        for (int i=0; i<shoppers.length/2; i++) {
            shoppers[2*i] = new Shopper("Barron-"+i);
            shoppers[2*i+1] = new Shopper("Olivia-"+i);
        }
        for (Shopper s : shoppers)
            s.start();
        for (Shopper s : shoppers)
            s.join();
        System.out.println("We need to buy " + Shopper.bagsOfChips + " bags of chips.");
    }
}
```
#### CountDownLatch
- Allows one or more threads to wait until a set of operations being performed in other threads completes
- Releases when count value reaches zero
- Cannot be reset, you have to create a new latch object if you need to reset the value
- Constructor 
	- `new CountDownLatch(value)`: take an argument with initialize count down value.
- Method
	- `await()`: Wait for count value to reach zero, much like how threads are wait at CyclicBarrier
	- `countDown()`: Decrement count value
- Initialize value of 5 because there are 5 Olivia threads that needs to execute before other Barron threads.
	- If initialize more than 5, the program will get stuck waiting at the latch because there are only 5 Olivia threads invoking the `countDown` method, so it never waits to zero
- Common CountDownLatch Usage
	- Initialize count to 1 as a simple on/off gate. Any number of threads can wait at that gate until the controlling thread executes the countDown method once, which unleashes those waiting threads to continue on.
	- Initialize count to N can be used to 
		- make 1 thread wait until N other threads have completed some action and invoke the `countDown` method.
		- for some Actions to be completed N many times, perhaps by a single thread in a loop
	- Doesn't require the threads that are calling `countDown` to wait there until proceeding, they're free to continue.
	- It only prevents threads that call `await` from proceeding before the count reaches zero.
```java
class Shopper extends Thread {

    public static int bagsOfChips = 1; // start with one on the list
    private static Lock pencil = new ReentrantLock();
    private static CountDownLatch fistBump = new CountDownLatch(5);

    public Shopper(String name) {
        this.setName(name);
    }

    public void run() {
        if (this.getName().contains("Olivia")) {
            pencil.lock();
            try {
                bagsOfChips += 3;
                System.out.println(this.getName() + " ADDED three bags of chips.");
            } finally {
                pencil.unlock();
            }
            fistBump.countDown();
        } else { // "Barron"
            try {
                fistBump.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pencil.lock();
            try {
                bagsOfChips *= 2;
                System.out.println(this.getName() + " DOUBLED the bags of chips.");
            } finally {
                pencil.unlock();
            }
        }
    }
}

public class CountDownLatchDemo {
    public static void main(String args[]) throws InterruptedException  {
        // create 10 shoppers: Barron-0...4 and Olivia-0...4
        Shopper[] shoppers = new Shopper[10];
        for (int i=0; i<shoppers.length/2; i++) {
            shoppers[2*i] = new Shopper("Barron-"+i);
            shoppers[2*i+1] = new Shopper("Olivia-"+i);
        }
        for (Shopper s : shoppers)
            s.start();
        for (Shopper s : shoppers)
            s.join();
        System.out.println("We need to buy " + Shopper.bagsOfChips + " bags of chips.");
    }
}
```
### Quiz
1. Which scenario describes a potential use case for a CountDownLatch initialized to a count value of 1?
- Multiple threads need to wait until after one other thread completes some action to continue
2. What is true about Java's CountDownLatch?
- It releases when its counter value reaches zero.
3. A thread that needs to **execute a section of code before the barrier** should call the CyclicBarrier's **await()** method **after** executing the code.
4. Barriers can be used to control **the relative order in which threads execute certain operations .**
5. Which of these is responsible for causing a race condition?
- the OS execution scheduler, The order in which the OS schedules threads to execute is non-deterministic.
6. A **race condition** can **occur independently** of a **data race**.
7. Which scenario creates the potential for a race condition to occur?
- The order in which two threads execute their respective operations will change the output.
## Asychronous Tasks 异步
### Computational Graph
- The key to parallel programming is to determine which step within a program can be executed in parallel and then figureing out how to coordinate them.
- Directed Ascyclic Graph (DAG)
	- Directed: each edge is directed from one node or vertex to another, 
	- Acyclic: meaning it doesn't have any loop that cycle back on itself.
### Thread Pool
#### Why need it?
- Although threads are considered to be lightweight, everytime we spawn a new thread, it does required some amount of overhead, in terms of processor time and memory
#### What is it?
- Rather than creating a new thread for every single task, it can be more efficient to use a thread pool, which creates and maintains a collection of worker threads.
- As the program submits tasks to the thread pool, the thread pool reuses existing worker threads to execute tasks, submitting tasks to a thead pool is like adding them to a to-do list for the worker threads. After one worker thread finish executing current task, it'll take another one from the queue.
- Reusing threads in thread pool addresses the overhead involved with creating new threads, it'll become a real advantage when the time it takes to execute the task is less than the time required to create a new thread. Since our threads already exists, when a new task arrives, we eliminate the delay of thread creation which makes our program more responsive.
### Java Example
#### ExecutorService Interface
- Defined in `java.util.concurrent` package
- Served as a higher level interface for launching new runnable tasks rather than working with threads directly
- Included several features to manage the executor and task lifecycle
- The executor service manages a pool of threads, so we don't have to create new threads manually.
- The executor maintain a queue of the tasks that get submitted to it and then it uses the existing threads in its thread pool to run those tasks asynchronously. 
	- `newSingleThreadExecutor()`: creates an executor that uses a single thread to execute tasks
	- `newFixedThreadPool(int nThreads)`: creates a thread pool that reuses a fixed number of threads to execute tasks.
- `ThreadPoolDemoBefore`  will create 100 threads with 1 - 100, `ThreadPoolDemoAfter` will create 100 threads but only with 1 - 24 thread
```java
class VegetableChopper extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName() + " chopped a vegetable!");
    }
}
public class ThreadPoolDemoBefore {
    public static void main(String args[]) {
        for (int i=0; i<100; i++)
            new VegetableChopper().start();
    }
}
public class ThreadPoolDemoAfter {
    public static void main(String args[]) {
        int numProcs = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numProcs);
        for (int i=0; i<100; i++)
            pool.submit(new VegetableChopper());
        pool.shutdown();
    }
}
```
### Future
- Acts as a place holder for a result that initially unknown, but will be available at some point in the future.
- Provides a mechanism to access the result of an asynchronous operation
- The feature is `read-only`, so while it's not ready yet, we can wait until at some point the result comes back and writes into that future, which is called resolving, or fulfilling the results to give an answer
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzg1ODEwODU5LC0zNTM2OTAzMCwxNzg1OD
UzMjYyLC0xNDk4Mzk5Njk4LDg1MDAzNDk4LDEzODE3MTY3NzQs
MTQ3MDg2NDAxMyw4MDUxMjI5NywtMTY1MDM2MTc1NywxMDg3Nj
YxNTEsLTE0MzY2MzkxODEsMjI4NzA2NDU3LC05MjYxMzkwNzks
NzQwMjMwNTQ3LDExNjYyNTQ1MTIsLTEyNjkzNjIwNjYsLTIxMT
Q2ODc2NjUsMTI1MTI4OTM5MiwtMTYyMjk0MzIyNywtMjAzMDI0
MTY3N119
-->