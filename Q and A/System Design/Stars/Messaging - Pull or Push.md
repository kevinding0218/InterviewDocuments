[Link](https://medium.com/@_JeffPoole/thoughts-on-push-vs-pull-architectures-666f1eab20c2)
### Messages Handling
How would we efficiently send/receive messages? To send messages, a user needs to connect to the server and post messages for the other users. To get a message from the server, the user has two options:
1. **Pull model**: Users can periodically ask the server if there are any new messages for them.
- This is when the server requests work, usually not directly from the client, but often through an intermediary. The most common approach here is when there is some kind of work queue that clients enqueue messages on, and the server pulls messages from that queue.
- If we go with our first approach, then the server needs to keep track of messages that are still waiting to be delivered, and as soon as the receiving user connects to the server to ask for any new message, the server can return all the pending messages. To minimize latency for the user, they have to check the server quite frequently, and most of the time they will be getting an empty response if there are no pending message. This will waste a lot of resources and does not look like an efficient solution.
3. **Push model**: Users can keep a connection open with the server and can depend upon the server
to notify them whenever there are new messages.
- This is when a client requests work from a server — the work is “pushed” to the server, which has no choice in the matter. This is probably the most common pattern, and the most common examples are requests to a REST API or RPC
- If we go with our second approach, where all the active users keep a connection open with the server, then as soon as the server receives a message it can immediately pass the message to the intended user. This way, the server does not need to keep track of the pending messages, and we will have minimum latency, as the messages are delivered instantly on the opened connection.
#### Cons
- You need to know where to send your request. You need a hard-coded list of addresses, a set of load balancer endpoints, DNS names that can be used with lookups config, or a whole service discovery system such as Zookeeper, or something else.
- 
### How will clients maintain an open connection with the server?
- We can use HTTP Long Polling or WebSockets. 
- In long polling, clients can request information from the server with the expectation that the server may not respond immediately. If the server has no new data for the client when the poll is received, instead of sending an empty response, the server holds the request open and waits for response information to become available. Once it does have new information, the server immediately sends the response to the client, completing the open request.
- This gives a lot of improvements in latencies, throughputs, and performance. The long polling request can timeout or can receive a disconnect from the server, in that case, the client has to open a new request.
### What will happen when the server receives a message for a user who has gone offline? 
- If the receiver has disconnected, the server can notify the sender about the delivery failure. 
- If it is a temporary disconnect, e.g., the receiver’s long-poll request just timed out, then we should expect a reconnect from the user. In that case, we can ask the sender to retry sending the message. This retry could be embedded in the client’s logic so that users don’t have to retype the message. The server can also store the message for a while and retry sending it once the receiver reconnects.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTExMjM5NTgzMjAsLTQwMTMyMDc5NV19
-->