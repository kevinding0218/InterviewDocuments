Options for Interservice Communication
- REST: stands for REpresentational State Transfer and goes with the concept of sending resources back and forth, and manipulating those resources
	- focus on resources, e.g: `GET /employee/42`, server recognize that request, it's going to locate that resource and send it back to client if it's available.
	- embrace with HTTP protocal, expecting a GET/POST/PUT/DELETE call
	- loose coupling, as we've got the resource focus, so the server only has to expose the resources that are being asked for, and there's a relatively small well-defined set of actions that can be done on those.'
	- text based, 
- RPC: Remote Procedure Call, 
	- focus on actions or functions, e.g: `Employee getEmployee(42)`, server sees this call and will do some internal processing
	- embrace programming semantics, expecting a normal function or method call
	- tightly coupling, as client doesn't just need to know about the resource that the server has to offer, it also needs to know about the methods that can be called, the arguments those methods need in order to function properly, and how to deal with the data that's going to be coming back from those calls
	- 

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEzMTc4NjU5NjhdfQ==
-->