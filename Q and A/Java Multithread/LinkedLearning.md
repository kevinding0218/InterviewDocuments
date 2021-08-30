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
```
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
```
public class ChefOlivia extends Thread {
	@Override
	public void run() {}
}
```
- Then you can create instance of your subclass, and run them as threads.
```
Thread olivia = new ChefOlivia();
```
#### Runnable interface
- Interface for a class that will be executed by a thread
	- The **Class Thread** implements the Runnable Interface
```
public class ChefOlivia implements Runnable {
	public void run() {}
}
```
- Defining a class that implements the Runnable interface simply requires you to have a `void run()` method
	- Similar to the `run()` we override when extending the Thread class
- Then you can create a instance of Thread by passing your subclass instance as the target for the Thread class constructor
```
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
```
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
```
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
```
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
```
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
```
synchronized (object) {
	// protected code
}
```
- Before a thread can execute the code contained within the synchronized statement, it must first acquire the intrinsic lock associated with the specific object
- Then when the thread is done, it will release its hold on that lock.
- For example, below both threads will be acquiring and releasing the same intrinsic locks assocaited witht the shopper class before and after they increment the garlicCount.
```
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
```
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
```
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
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTg2NjgzMTE2OCw3MDk2OTY3NjMsLTE4MT
g4MjM1MTksLTE4NzUyODc2MjgsNzczMDQ3NTE1LDIxMDQ1NjE5
OTUsMTM0NTgzMDAwMSwyMTIyOTg5ODM2LC0xNDAwODExOTU1LC
0xMzQxNzc3MzY5LC0xNTQxODMzODcyXX0=
-->