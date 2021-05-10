


- 
##### How Are Strings Represented in Memory?
- A  _String_  instance in Java is an object with two fields: a  _char[] value_  field and an  _int hash_  field. The  _value_  field is an array of chars representing the string itself, and the  _hash_  field contains the  _hashCode_  of a string which is initialized with zero, calculated during the first  _hashCode()_  call and cached ever since.
##### What Is a Stringbuilder and What Are Its Use Cases?
- When concatenating two  _String_  instances, a new object is created, and strings are copied. This could bring a huge garbage collector overhead if we need to create or modify a string in a loop.  _StringBuilder_  allows handling string manipulations much more efficiently.
- _StringBuffer_  is different from  _StringBuilder_  in that it is thread-safe. If you need to manipulate a string in a single thread, use  _StringBuilder_  instead.


#### Difference between Java Heap Space and Stack Memory
1.  **Heap** memory is used by **all the parts of the application** whereas **stack** memory is used only by **one thread of execution**.
2.  Whenever an **object** is created, it’s always **stored in the Heap space and stack memory c ontains the reference to it. Stack memory only contains local primitive variables and reference variables to objects in heap space**.
3.  Objects stored in the heap are globally accessible whereas stack memory can’t be accessed by other threads.
4.  Memory management in stack is done in LIFO manner whereas it’s more complex in Heap memory because it’s used globally. Heap memory is divided into Young-Generation, Old-Generation etc, more details at  [Java Garbage Collection](https://www.journaldev.com/2856/java-jvm-memory-model-memory-management-in-java).
5.  Stack memory is short-lived whereas heap memory lives from the start till the end of application execution.
6.  We can use  **-Xms**  and  **-Xmx**  JVM option to define the startup size and maximum size of heap memory. We can use  **-Xss**  to define the stack memory size.
7.  When stack memory is full, Java runtime throws  `java.lang.StackOverFlowError`  whereas if heap memory is full, it throws  `java.lang.OutOfMemoryError: Java Heap Space`  error.
8.  Stack memory size is very less when compared to Heap memory. Because of simplicity in memory allocation (LIFO), stack memory is very fast when compared to heap memory.
##### Stack vs Heap
- The stack is a part of memory that contains information about **nested method** calls down to the current position in the program. It also contains all **local variables and references to objects on the heap** defined in currently executing methods.
- This structure allows the runtime to return from the method knowing the address whence it was called, and also clear all local variables after exiting the method. Every thread has its own stack.
- The heap is a large bulk of memory intended for allocation of objects. When you create an object with the  **new**  keyword, it gets allocated on the heap. **However, the reference to this object lives on the stack.**
### Java main method [Link](https://www.journaldev.com/12552/public-static-void-main-string-args-java-main-method)
### Java Static [Link](https://www.javainterviewpoint.com/top-10-java-interview-questions-on-static-keyword/)
### Java Default Method
https://www.baeldung.com/java-static-default-methods
### Thread implementation
1. Extends from Thread class and override run method
	```
	public  class MyThread extends Thread { 
		public MyThread(String name) { 
			super(name); 
		} 
		@Override  public void run() { 
			// logic
		} 
	}
	Thread t3 = new MyThread("t3");
	t3.start();
	```
2. Implement Runnable Interface and override run method
	```
	public class MyThread implements Runnable { 
		@Override  public void run() { 
			// logic 
		} 
	}
	Thread t3 = new MyThread("t3");
	t3.start();
	```
3. Implement Callable Interface and override run method (if you have a return value)
	```
	import java.util.concurrent.Callable; 
	import java.util.concurrent.ExecutionException; 
	import java.util.concurrent.ExecutorService; 
	import java.util.concurrent.Executors; 
	import java.util.concurrent.Future;
	public class MyCallable implements Callable<String> {
		@Override  public String call() throws Exception { 	
			Thread.sleep(1000); 
			//return the thread name executing this callable task  
			return Thread.currentThread().getName(); 
		} 
		public static void main(String args[]){
			//Get ExecutorService from Executors utility class, thread pool size is 10 
			ExecutorService executor = Executors.newFixedThreadPool(10);
			//Create MyCallable instance 
			Callable<String> callable = new MyCallable();
			for(int i=0; i< 100; i++){ 
				//submit Callable tasks to be executed by thread pool 
				Future<String> future = executor.submit(callable); 	
				//add Future to the list, we can get return value using Future 
				list.add(future); 
			} 
			for(Future<String> fut : list){ 
			try { 
				//print the return value of Future, notice the output delay in console  
				// because Future.get() waits for task to get completed 
				System.out.println(new Date()+ "::"+fut.get()); 
			} 
			catch (InterruptedException | ExecutionException e) { 
				e.printStackTrace(); } 
			} //shut down the executor service now 
			executor.shutdown();
		}
	}
	```
4. Initiate Thread object with implementing run method
	```
	var thread = new Thread(new Runnable() {
		public void run() {
			// logic
		}
	});
	var thread = new Thread(() => {
	});
	// OR
	var t = new Thread(new Bar()::baz)
	class Bar {
		public void baz() {}
	}
	```



### How concurrenthashmap works
- **ConcurrentHashMap:** It allows concurrent access to the map. Part of the map called _Segment (internal data structure)_ is only getting locked while adding or updating the map. So ConcurrentHashMap allows concurrent threads to read the value without locking at all.
	- **Concurrency-Level:** Defines the number which is an estimated number of concurrently updating threads. The implementation performs internal sizing to try to accommodate this many threads.
	- **Load-Factor:** It's a threshold, used to control resizing.
	- **Initial Capacity:** The implementation performs internal sizing to accommodate these many elements.
- A ConcurrentHashMap has internal final class called Segment so we can say that ConcurrentHashMap is internally divided in segments of size 32, so at max 32 threads can work at a time. It means each thread can work on a each segment during high concurrency and atmost 32 threads can operate at max which simply maintains 32 locks to guard each bucket of the ConcurrentHashMap.
#### Inserting (Put) Element in ConcurrentHashMap:
- 1st calculate the hash of key
- decide the Segment, Since it's all about concurrency, we need synchronized block on the above Segment
#### Size of ConcurrentHashMap
- return the sum of all segments' count
#### Getting (get) Element From ConcurrentHashMap
- When we are getting an element from ConcurrentHashMap we are simply passing key and hash of key is getting calculated
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5NjMwNDQ3MjIsMTA4NDA4MTY2NCwtMz
YxOTAxODA1LDE3MDc1NzM1ODMsLTU5Mzc2MjcxOCwtNjkxNzQ0
MzU1LDE4ODQxNzMxMjYsMjA0ODMwNTUwMiwzNTk2ODYxNzYsMT
ExMzk1Nzg5MSwxNTIyMjM1MjUwLDE2MTQ1Mzc4NjAsLTE3MzA0
OTUwMzksLTE0MDI2NjQ3OTMsMTg3NDAyMTI1NF19
-->