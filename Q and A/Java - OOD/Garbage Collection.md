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
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxNDY4MjQ1NDRdfQ==
-->