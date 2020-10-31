### int vs Integer
- int is a primitive data type while Integer is a Wrapper class
- int as other **primitive type was stored in stack**, so we usually use `int` for temp value, while Integer as other **reference type was stored in heap**, we usually use `Integer` for POJO so that we can serialize or deserialize by telling if we have the value
- int was designed for JVM cost and performance perpective, it is not designed for Object Oriented. 
- int default value is 0 while Integer default as null
- Integer was a class , when it was initialized will have a reference in JVM. Variables of type Integer store references to Integer objects, just as with any other reference (object) type
- we can't assign a String value to an int value directly or even by casting, but we can do this with Integer
- Integer can be directly converted to other types like binary or hex
- int can be compared by using `==` , while Integer usually compares by using `equals`, because when using `==` to compare Integer, it's actually a reference type so the comparison was happening at pointer which referenced to the object memory address, Integer also have a mechanism like cache so it can be compared by using `==` when value between -128 ~ 127
### JVM
- JVM is a virtual machine that understands and runs java bytecodes, works for memory management in heap space and stack memory
#### Java Heap Space
- Java **Heap space is used by java runtime to allocate memory to Objects and JRE classes**. Whenever we create an object, it’s always created in the Heap space.
- **Garbage Collection runs on the heap memory to free the memory** used by objects that don’t have any reference. Any object created in the heap space has global access and can be referenced from anywhere of the application.
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
	} // Line 8 }
```
-   As soon as we run the program, it loads all the Runtime classes into the Heap space. When the **main()** method is found at line 1, Java Runtime creates **stack** memory to be used by **main() method thread**.
-   We are creating **primitive local variable** at line 2, so it’s created and stored in the **stack** memory of main() method.
-   Since we are creating an **Object** in the 3rd line, it’s created in heap memory and **stack** memory contains the **reference** for it. A **similar** process occurs when we create **Memory** object in the 4th line.
-   Now when we call the **foo() method** in the 5th line, **a block in the top of the stack is created** to be used by the foo() method. Since **Java is pass-by-value, a new reference to Object is created in the foo() stack block** in the 6th line.
-   A string is created in the 7th line, it goes in the  [String Pool](https://www.journaldev.com/797/what-is-java-string-pool "What is Java String Pool?")  in the heap space and a reference is created in the foo() stack space for it.
-   foo() method is terminated in the 8th line, at this time memory block allocated for foo() in stack becomes free.
-   In line 9, main() method terminates and the stack memory created for main() method is destroyed. Also, the program ends at this line, hence Java Runtime frees all the memory and ends the execution of the program.
#### Difference between Java Heap Space and Stack Memory
1.  Heap memory is used by all the parts of the application whereas stack memory is used only by one thread of execution.
2.  Whenever an object is created, it’s always stored in the Heap space and stack memory contains the reference to it. Stack memory only contains local primitive variables and reference variables to objects in heap space.
3.  Objects stored in the heap are globally accessible whereas stack memory can’t be accessed by other threads.
4.  Memory management in stack is done in LIFO manner whereas it’s more complex in Heap memory because it’s used globally. Heap memory is divided into Young-Generation, Old-Generation etc, more details at  [Java Garbage Collection](https://www.journaldev.com/2856/java-jvm-memory-model-memory-management-in-java).
5.  Stack memory is short-lived whereas heap memory lives from the start till the end of application execution.
6.  We can use  **-Xms**  and  **-Xmx**  JVM option to define the startup size and maximum size of heap memory. We can use  **-Xss**  to define the stack memory size.
7.  When stack memory is full, Java runtime throws  `java.lang.StackOverFlowError`  whereas if heap memory is full, it throws  `java.lang.OutOfMemoryError: Java Heap Space`  error.
8.  Stack memory size is very less when compared to Heap memory. Because of simplicity in memory allocation (LIFO), stack memory is very fast when compared to heap memory.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjM2NTY5NTY0XX0=
-->