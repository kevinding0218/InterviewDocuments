### Thread vs Process
- Both processes and threads are independent sequences of execution. The typical difference is that threads (of the same process) run in a shared memory space, while processes run in separate memory spaces.
- From the operating system's point of view, a process is an independent piece of software that runs in its own virtual memory space.
- a thread is a part of an application that shares a common memory with other threads of the same application. Using common memory allows to shave off lots of overhead, design the threads to cooperate and exchange data between them much faster.
### Thread Pool
- In Java, threads are mapped to system-level threads which are operating system's resources.
- The context switching between threads is done by the operating system as well – in order to emulate parallelism.
- When you use a thread pool, you **write your concurrent code in the form of parallel tasks and submit them for execution to an instance of a thread pool**.
### How can you catch an exception thrown by another thread in Java?
- This can be done using **Thread.UncaughtExceptionHandler**
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTI2NDQzMzc5Ml19
-->