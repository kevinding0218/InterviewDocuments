### Thread vs Process
- Both processes and threads are independent sequences of execution. The typical difference is that threads (of the same process) run in a shared memory space, while processes run in separate memory spaces.
- From the operating system's point of view, a process is an independent piece of software that runs in its own virtual memory space.
- a thread is a part of an application that shares a common memory with other threads of the same application. Using common memory allows to shave off lots of overhead, design the threads to cooperate and exchange data between them much faster.
### Thread Pool
- In Java, threads are mapped to system-level threads which are operating system's resources.
- The context switching between threads is done by the operating system as well – in order to emulate parallelism.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTg1MzIwMTMzN119
-->