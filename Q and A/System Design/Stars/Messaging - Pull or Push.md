### Messages Handling
How would we efficiently send/receive messages? To send messages, a user needs to connect to the server and post messages for the other users. To get a message from the server, the user has two options:
1. **Pull model**: Users can periodically ask the server if there are any new messages for them.
2. **Push model**: Users can keep a connection open with the server and can depend upon the server
to notify them whenever there are new messages.
- If we go with our first approach, then the server needs to keep track of messages that are still waiting to be delivered, and as soon as the receiving user connects to the server to ask for any new message, the server can return all the pending messages. To minimize latency for the user, they have to check the server quite frequently, and most of the time they will be getting an empty response if there are no pending message. This will waste a lot of resources and does not look like an efficient solution.
- If we go with our second approach, where all the active users keep a connection open with the server, then as soon as the server receives a message it can immediately pass the message to the intended user. This way, the server does not need to keep track of the pending messages, and we will have minimum latency, as the messages are delivered instantly on the opened connection.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM5NTI4MDAwOF19
-->