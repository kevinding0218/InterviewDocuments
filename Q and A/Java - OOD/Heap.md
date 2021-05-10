### Java Data Store
- primitive type was stored in stack, so we usually use `int` for temp value, 
- while Integer as other **reference type was stored in heap*
#### Java Heap Space [Link](https://www.journaldev.com/4098/java-heap-space-vs-stack-memory)
- Java **Heap space is used by java runtime to allocate memory to Objects and JRE classes**. Whenever we create an object, it’s always created in the Heap space.
- **Garbage Collection runs on the heap memory to free the memory** used by objects that don’t have any reference. Any object created in the heap space has global access and can be referenced from anywhere of the application.
##### What Happens When There Is Not Enough Heap Space
- If there is no memory space for creating a new object in Heap, Java Virtual Machine throws _OutOfMemoryError_ or more specifically **java.lang.OutOfMemoryError_  heap space.**
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTc5MDIzMDg3OV19
-->