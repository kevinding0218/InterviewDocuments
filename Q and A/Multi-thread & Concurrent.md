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
	- There are thre
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3ODg5OTIwODksLTIwODg3NDY2MTJdfQ
==
-->