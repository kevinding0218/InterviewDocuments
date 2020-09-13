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
- Client <--> Server
	- Server: the component that having the responsibility of accepting requests, performing the processing that's required in order to honor that request, and then generate what response might be required
	- Client: making request to server and waiting server to response back
- Client(GeneratedCode) <--> TransportProtocol <--> (Generated Code)Server
	- gRPC doesn't expect client and server to talk directly to one another, instead it uses a message definitions that created using Protocol Buffers to generate some code
	- Server + Generated Code: has the responsibility of clients are going to interact with when they make requests of us
	- Client + Generated Code: has the responsibility of providing the tunnel that is going to be used in order to prepare the messages that we generate on the client to be sent over to the server.
	- Since we have generated code on both sides, all of the underlying details about serializing and deserializing messages and how they get transported back and forth is really abstracted away from us
	- Transport Protocol: in order to communicate back and forth, has the responsibility of sending and receiving the messages back and forth, it doesn't have the responsibility to understand the message that it's sending, that's what `generated code` does on both sides.
	- Protocol Buffers are not the only transfer protocol in gRPC, the transport layer is pluggable, JSON-based or XML-based protocol or your own transport protocol are accepted as well.
#### Design Cycle (3 steps)
1. Step 1: Define Messages: those messages are going to be defined as Protocol Buffers
2. Step 2: Generate Source Code: use Proto C compiler to generate the client and server source code that we need in order to generate the interfaces that we're going to use Protocol Buffers to send messages back and forth.
3. Step 3: Write Client/Server
#### RPC Life Cycle
1. Create Channel: this channel is going to wrap the actual wire protocol that's used in order to send the messages back and forth, create once and use through life cycle
	- For example: if we have our server and client communicating using HTTP/2, then that channel's going to wrap a TCP connection between the client and server. 
2. Create Client: client takes in the channel, and that client is normally provided to us
3. Client Send Request (with optional Metadata): the client always has to initiate, the Metadata client send is data about the request but not the request object itself
4. (optional) Server Send Metadata: it's possible but not required that server send metadata back as a pre-conversation between the client and server, this is even before the server starts processing the request.
5. Send/Receive Message: when the server sends the response back.
- Metadata is useful for checking `user`credentials, authentication and authorization is going to be done on the RPC request level
#### Authentication
 - Insecure communication
	 - the client and server are by default commnicated using HTTP/1 with no special security involved, required no special handling, no custom certification needs to be generated.
 - SSL/TLS communication
	 - using HTTP/2 by default, if gRPC recognize it's on a HTTP/2 secure connection, it's going to try and upgrade those connections to use HTTP/2, so communication speed would be fast as the benefit of HTTP/2, client will validate certificate. So if you work with generated certifcates, you might have to do some additional work on the client in order to make sure it recognizes those server certifcates as valid.
 - Google Token-Based: as a layer on top of SSL and TLS, requires Secure connection
 - Custom(OAuth2): it's language specific, so creating a custom authentication provider using Java is going to be a little bit different than Go
#### Message Types
- **Unary RPC:** send a single request and expect a single response in our procedure call.
	- rpc Method(RequestType) returns (ResponseType)
	- always need a request type and response type in gRPC, even if you don't have any data in the request, you still have to send an empty request object along.
- **Server Streaming RPC**: send a single request and then after the server is done, or while the server is generating its responses, it's actually going to send them back a piece at a time, so we're streaming response back.
	- e.g: **watching a streaming video**, send request like `watch video x`, then the server is going to send back a buffered stream of the video data so that client doensn't have to wait for all the video to get across at one time but a chunk at a time.
	- rpc Method(RequestType) returns (stream ResponseType)
	- with the `stream` keyword, it means instead of sending an array of response at one time, **we're send only one at a time, which is much smaller messages across the network, and we'll continue sending more of them**.
- **Client Streaming RPC**: send the request a piece at a time, here the server waits until the entire request is received, there is no processing going on the server until everything's done, then a single response will be sent back.
	- e.g: **uploading a file**
	-  rpc Method(stream RequestType) returns (ResponseType)
- **Bidirectional Streaming RPC**: client continuing send request and server continuing send response back, this can happen in a very asynchronous way,  
	- rpc Method(stream RequestType) returns (stream ResponseType)
	- e.g: **Chat application**
#### Protocol Buffers
- Highly optimized serializers and deserializers: protocol buffers separate the message definition from the data that's actually tranmitted.
- We're creating a message definitino file and use that to generate source code that our clients and servers are going to take advantages of. That means we don't have to send all the structures along with every message. We just need to send some data and some small piece of metadata in order to help the deserializer figure out which data goes with which field.
- e.g:
	```
	syntax = "proto3";
	// tell the protocol buffer compuler where this packages is, in relative to other packages in our protocol buffer message definitions.
	package user;
	// when you generate java source code, please put it under "com.cvent.grpc"
	option java_package = "com.cvent.grpc";
	// message with identifiers to version a message over time so older clients continues working property
	message Login {
		string name = 1;
		types password = 2;
	}
	// Moving beyond JSON and XML with Protocol Buffers
	```
- gRPC Message Type
```
syntax = "proto3";
...
// use "service" keyword in order to define a gRPC service
service Employee {
	// define the rpc call that's going to be enabled
	// which is what the client asks the server to do for it.
	rpc GetByBadgeNumber
}
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwNTE0Nzc2NDAsLTE4NjU2ODc2NDgsLT
EzNTA3NTYwODksLTExMDUyOTMxNzIsLTE2NTk4NDY3NTMsLTEz
NzUzMDUyMjYsLTIwNDYzNjE1ODUsLTE4MTM1MDg2NDUsMTQ0Mj
YyOTU5NCwtNjU4NDE1MDEwLC0xNTA2NzEzMDUwXX0=
-->