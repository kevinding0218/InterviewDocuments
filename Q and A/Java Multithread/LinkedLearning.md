## Sequential vs Parallel Computing
### Making salad as an example
- Chopping lettuce, tomato cucumber, onion and put dressing
###  Sequential
- Program is broken down of sequatial instruction that I execute one after another
- Can only execute one at a given moment
### Parallel
- Program is broken down into independent parts that can be executed simultaneously by different processors
- Doesn't necessarily mean we'll make the process twice as fast, because we've to add extra effort like in kitchen to communicate with each other to corrdinate our actions.
	- There might be times that one of us having to wait for the other cook to finish a certain step before we continue on
	- Parallel execution will increase the overall throughput of a program.
		- Accomplish a single task faster
		- Accomplish more tasks in a given time

## Thread vs Process
### Process
- Includes code, data and state information
- Independent instance of a running program
- Separate address space
- A computer can have hundreds of active processes at once, and an operating system's job is to manage all of them.
### Thread
- WIthin every process, there are one or more smaller sub elements called threads, kinda like tiny processes.
- Each of those threads is an independent path of execution, a different sequential of instructions
- It can only exist as part of a process, 
- Operating system schedules threads for execution
### Common
- Threads that belong to the same process share the process's address space
- Given same resource in memory including the program executable code and data.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjU0NDM2Nzc0LC0xMzQxNzc3MzY5LC0xNT
QxODMzODcyXX0=
-->