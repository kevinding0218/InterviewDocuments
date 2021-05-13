#### Difference between Java Heap Space and Stack Memory
1.  **Heap** memory is used by **all the parts of the application** whereas **stack** memory is used only by **one thread of execution**.
2.  Whenever an **object** is created, it’s always **stored in the Heap space and stack memory c ontains the reference to it. Stack memory only contains local primitive variables and reference variables to objects in heap space**.
3.  Objects stored in the heap are globally accessible whereas stack memory can’t be accessed by other threads.
4.  Memory management in stack is done in LIFO manner whereas it’s more complex in Heap memory because it’s used globally. Heap memory is divided into Young-Generation, Old-Generation etc, more details at  [Java Garbage Collection](https://www.journaldev.com/2856/java-jvm-memory-model-memory-management-in-java).
5.  **Stack memory is short-lived** whereas **heap memory lives from the start till the end of application execution**.
6.  We can use  **-Xms**  and  **-Xmx**  JVM option to define the startup size and maximum size of heap memory. We can use  **-Xss**  to define the stack memory size.
7.  When stack memory is full, Java runtime throws  `java.lang.StackOverFlowError`  whereas if heap memory is full, it throws  `java.lang.OutOfMemoryError: Java Heap Space`  error.
8.  Stack memory size is very less when compared to Heap memory. Because of simplicity in memory allocation (LIFO), stack memory is very fast when compared to heap memory.
##### Stack vs Heap
- The stack is a part of memory that contains information about **nested method** calls down to the current position in the program. It also contains all **local variables and references to objects on the heap** defined in currently executing methods.
- This structure allows the runtime to return from the method knowing the address whence it was called, and also clear all local variables after exiting the method. Every thread has its own stack.
- The heap is a large bulk of memory intended for allocation of objects. When you create an object with the  **new**  keyword, it gets allocated on the heap. **However, the reference to this object lives on the stack.**
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTg0NTU5NzY0MF19
-->