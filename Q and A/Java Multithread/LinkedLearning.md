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
- Multiple threads can share a single Runnable object, which has the benefit of reducing memory usage comparing to creating separate Thread objects for each Thread
### Daemon Thread
#### Garbage Collector
- A form of automatic memory managemet that runs in the background and attemps to relcaim garbage or memory that's no longer being used by the program.
- While a function thread is executing, it spaws a separate child thread to provide the garbage collection service, the function thread would work fine until it's ready to finish executing.
- When the function thread is ready to exit the program but it **can't because it's child thread, the GC is still running**. the function thread will be stuck forever and
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwMDYwNjUzMTEsNzczMDQ3NTE1LDIxMD
Q1NjE5OTUsMTM0NTgzMDAwMSwyMTIyOTg5ODM2LC0xNDAwODEx
OTU1LC0xMzQxNzc3MzY5LC0xNTQxODMzODcyXX0=
-->