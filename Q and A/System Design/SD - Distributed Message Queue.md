### What is distributed message queue
- Let's say there are two web-services called producer and consumer, and they need to communicate with each other.
- One option is to setup a synchronous communication, when producer makes a call to a consumer and waits for a response.
	- This approach has its own pros and cons. Synchronous communication is easier and faster to implement. At the same time synchronous communication makes it harder to deal with consumer service failures. 
	- We need to think when and how to properly retry failed requests, how not to overwhelm
consumer service with too many requests and how to deal with a slow consumer service host.
Another option is to introduce a new component that helps to setup asynchronous communication.
Producer sends data to that component and exactly one consumer gets this data a short
time after.
Such component is called a queue.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTcyMTQyMDA5MF19
-->