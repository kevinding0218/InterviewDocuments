


#### Java Heap Space [Link](https://www.journaldev.com/4098/java-heap-space-vs-stack-memory)
- Java **Heap space is used by java runtime to allocate memory to Objects and JRE classes**. Whenever we create an object, it’s always created in the Heap space.
- **Garbage Collection runs on the heap memory to free the memory** used by objects that don’t have any reference. Any object created in the heap space has global access and can be referenced from anywhere of the application.
#### GC
- Garbage collection is the process of looking at heap memory, identifying which objects are in use and which are not, and deleting the unused objects.
- An in-use object, or a referenced object, means that some part of your program still maintains a pointer to that object. An unused object, or unreferenced object, is no longer referenced by any part of your program. So the memory used by an unreferenced object can be reclaimed.
- The biggest advantage of garbage collection is that it removes the burden of manual memory allocation/deallocation from us so that we can focus on solving the problem at hand.
##### How does it work?
- The heap is divided up into smaller spaces or generations. These spaces are Young Generation, Old or Tenured Generation, and Permanent Generation. 
- The  **young generation hosts most of the newly created objects**. The term **“age”** in generational garbage collection **refers to the number of collection cycles the object has survived**.
- During the next minor GC, the same thing happens to the Eden space. Unreferenced objects are deleted and referenced objects are moved to a survivor space.
- After every minor garbage collection cycle, the age of each object is checked.
- Eventually, a major garbage collection will be performed on the old generation which cleans up and compacts that space. For each major GC, there are several minor GCs.
##### When Does an Object Become Eligible for Garbage Collection?
- An object becomes eligible for Garbage collection or GC if it is not reachable from any live threads or by any static references.
- The most straightforward case of an object becoming eligible for garbage collection is if all its references are null or when a parent object is set to null.
##### How Do You Trigger Garbage Collection
- **as Java programmer, can not force garbage collection in Java**; it will only trigger if JVM thinks it needs a garbage collection based on Java heap size.
- Before removing an object from memory garbage collection thread invokes finalize()method of that object and gives an opportunity to perform any sort of cleanup required. You can also invoke this method of an object code, however, there is no guarantee that garbage collection will occur when you call this method.
- Additionally, there are methods like System.gc() and Runtime.gc() which is used to send request of Garbage collection to JVM but it’s not guaranteed that garbage collection will happen.
##### What Happens When There Is Not Enough Heap Space
- If there is no memory space for creating a new object in Heap, Java Virtual Machine throws _OutOfMemoryError_ or more specifically **java.lang.OutOfMemoryError_  heap space.**
- 
##### How Are Strings Represented in Memory?
- A  _String_  instance in Java is an object with two fields: a  _char[] value_  field and an  _int hash_  field. The  _value_  field is an array of chars representing the string itself, and the  _hash_  field contains the  _hashCode_  of a string which is initialized with zero, calculated during the first  _hashCode()_  call and cached ever since.
##### What Is a Stringbuilder and What Are Its Use Cases?
- When concatenating two  _String_  instances, a new object is created, and strings are copied. This could bring a huge garbage collector overhead if we need to create or modify a string in a loop.  _StringBuilder_  allows handling string manipulations much more efficiently.
- _StringBuffer_  is different from  _StringBuilder_  in that it is thread-safe. If you need to manipulate a string in a single thread, use  _StringBuilder_  instead.
####  Java Stack Memory
- Java **Stack memory is used for the execution of a thread**. They contain method-specific values that are short-lived and references to other objects in the heap that is getting referred from the method.
- Stack memory is always referenced in LIFO (Last-In-First-Out) order. Whenever a method is invoked, a new block is created in the stack memory for the method to hold local primitive values and reference to other objects in the method.
- As soon as the method ends, the block becomes unused and becomes available for the next method.  
- Stack memory size is very less compared to Heap memory.
#### Example
```
public  class  Memory { 
	public static void main(String[] args) { // Line 1  				    
		int i=1; // Line 2 
		Object obj = new Object(); // Line 3 
		Memory mem = new Memory(); // Line 4 	
		mem.foo(obj); // Line 5 
	} // Line 9  
	private void foo(Object param) { // Line 6 
		String str = param.toString(); // Line 7 	
		System.out.println(str); 
	} // Line 8 
}
```
-   As soon as we **run the program**, it **loads all the Runtime classes into the Heap space**. When the **main()** method is found at line 1, **Java Runtime creates stack memory to be used by main() method thread**.
-   We are creating **primitive local variable** at line 2, so it’s **created and stored in the stack** memory of main() method.
-   Since we are creating an **Object** in the 3rd line, it’s created in **heap memory and stack memory contains the reference for it**. A **similar** process occurs when we create **Memory object** in the 4th line.
-   Now when we call the **foo() method** in the 5th line, **a block in the top of the stack is created** to be used by the foo() method. Since Java is pass-by-value, **a new reference to Object is created in the foo() stack block** in the 6th line.
-   A **string** is created in the 7th line, it goes in the **String Pool** in the **heap space and a reference is created in the foo() stack space** for it.
-   foo() method is terminated in the 8th line, **at this time memory block allocated for foo() in stack becomes free**.
-   In line 9, **main() method terminates** and the **stack memory created for main() method is destroyed**. Also, the **program ends** at this line, hence **Java Runtime frees all the memory and ends the execution of the program**.
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
eyJoaXN0b3J5IjpbMTcyMDM2ODU1MiwxMDg0MDgxNjY0LC0zNj
E5MDE4MDUsMTcwNzU3MzU4MywtNTkzNzYyNzE4LC02OTE3NDQz
NTUsMTg4NDE3MzEyNiwyMDQ4MzA1NTAyLDM1OTY4NjE3NiwxMT
EzOTU3ODkxLDE1MjIyMzUyNTAsMTYxNDUzNzg2MCwtMTczMDQ5
NTAzOSwtMTQwMjY2NDc5MywxODc0MDIxMjU0XX0=
-->