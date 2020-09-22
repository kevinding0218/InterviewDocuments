#### What is a thread
- A thread is defined at the operation system level
- A thread is a set of instructions
- An application can be composed of several threads
- Different threads can be executed "at the same time"
- The JVM works with several threads (GC, JIT, ...)
#### What does "at same time" mean
- For example, you can write a text document, while there is a background spell check running, also print the document won't let you stop writing, you can also send/receive email while you're printing
- 1st case: CPU with Only One Core
	- Can only do one thing at a time, but task are switching very fast
- 2nd case: CPU with Multiple Cores
	- Can do two things at a time
- Who is responsible for the CPU sharing?
	- A special element called thread scheduler
	- There are three reasons for the scheduler to pause a thread:
		1. The CPU should be shared equally among thread: there might be some high priority stuffs that are taken into account to share equally the CPU as a resource
		2. The thread is waiting for some more data: think about a thread is doing some input, output, reading or writing data to disk or to a network, we know that reading/writing to disk is a slow process, if CPU is very fast, it may pause the thread waiting for the data to be available
		3. The thread is waiting for another thread to do something: like to release a resource
#### Race Condition
- We know that creating a thread is about doing several things at the same time, accessing data concurrently may lead to issues! For example, two different threads might be reading the same variable, the same field that defined in a Java class or the same array
- A race condition occurs w
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjkwMTYzMDQxLC0yMDg4NzQ2NjEyXX0=
-->