


- 





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
eyJoaXN0b3J5IjpbMTc4MTk4MjEwMCwxMDg0MDgxNjY0LC0zNj
E5MDE4MDUsMTcwNzU3MzU4MywtNTkzNzYyNzE4LC02OTE3NDQz
NTUsMTg4NDE3MzEyNiwyMDQ4MzA1NTAyLDM1OTY4NjE3NiwxMT
EzOTU3ODkxLDE1MjIyMzUyNTAsMTYxNDUzNzg2MCwtMTczMDQ5
NTAzOSwtMTQwMjY2NDc5MywxODc0MDIxMjU0XX0=
-->