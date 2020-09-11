### Options for Interservice Communication
- REST: stands for REpresentational State Transfer and goes with the concept of sending resources back and forth, and manipulating those resources
	- focus on resources, e.g: `GET /employee/42`, server recognize that request, it's going to locate that resource and send it back to client if it's available.
	- embrace with HTTP protocal, expecting a GET/POST/PUT/DELETE call
	- loose coupling, as we've got the resource focus, so the server only has to expose the resources that are being asked for, and there's a relatively small well-defined set of actions that can be done on those.'
	- text based message, it's not necessary but a lot of REST architecture are sending plain text message back and forth
	- works well in web-based application, when the domain that's being described can easily be modeled in terms of resources.
- RPC: Remote Procedure Call, 
	- focus on actions or functions, e.g: `Employee getEmployee(42)`, server sees this call and will do some internal processing
	- embrace programming semantics, expecting a normal function or method call
	- tightly coupling, as client doesn't just need to know about the resource that the server has to offer, it also needs to know about the methods that can be called, the arguments those methods need in order to function properly, and how to deal with the data that's going to be coming back from those calls
	- binary based message, this is an advantage because binary message are typically much smaller than text based messages when sending the same datal
	- make things clear what actions a client can invoke on the server, and potentially makes it a lot easier to program the clients, because the server is going to accept those specific method calls which can be very clearly named.
	- However, in order for the clients to be aware of what methods are going to be available, the server has to publish those in some way, so while RPC offers a lot more clarity about what potentially can be done, we are going to have to do a little bit more work in order to get our clients and servers talking to each other

### gRPC (inner service communication)
- Cross-platform
- Scalable
- Streaming
- fast and efficient
- strongly typed messaging
#### Basic structure
- Client(GeneratedCode) <--> adf <--> (Generated Code)Server
	- Server: the component that having the responsibility of accepting requests, performing the processing that's required in order to honor that request, and then generate what response might be required
	- Client: making request to server and waiting server to response back
	- gRPC doesn't expect client and server to talk directly to one another, instead it uses a message definitions that created using Protocol Buffers to generate some code

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTg1Mzk0ODI2NywtNjU4NDE1MDEwLC0xNT
A2NzEzMDUwXX0=
-->