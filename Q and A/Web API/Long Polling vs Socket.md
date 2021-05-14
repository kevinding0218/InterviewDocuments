- [Web Real-time](#web-real-time)
  * [Websocket](#websocket)

## Web Real-time
### Websocket
- Before websocket, HTTP could only be requested from the Client which is a single-way request.
- **Polling vs long-polling vs streaming**
	- **Polling**: Periodically client send request to server for requesting data, the drawback is each request would contain the HttpHeader which is steam cost and CPU memory cost, also user may not get real-time data if time interval is too long, server side will be under pressure if time interval is too short.
	- **Long Polling**: After client send HTTP request to server, it will keep waiting until there is new message coming back, it decrease the CPU usage and stream usage but because HTTP Header data normally is large size, and the real usage may only to 3%, which is a waste of resource usage.
	- **iFrame(stream)**: insert a hidden iframe and use its `src` attribute to create a long pulling between client and server, this would be benefit for real-time and browser compatibility but increase the resource usage of server, browser will keep spinning if loading is not completed.
	- There are **two important drawbacks** that need to be considered when using long polling
		1.  Long polling requests are not different from any other HTTP request and web servers handle them the same way. This means that every long poll connection will **reserve server resources**, potentially **maxing out the number of connections the server can handle**. This can lead to **HTTP connection timeouts**.
	   2.  Each web browser will limit the maximum number of connections web application can make. This means that your application load time and performance may be degraded.
- **Websocket**
	- it uses the socket from TCP on webpages, once web server and client establish a TCP protocol connection, all communication will be rely on the particular protocol. Data could be sent as JSON/XML/HTML/Image or any format.
	- Because it's a protocol based on HTTP, once communication establishes, either client/server side could send content to the other.
	- Websocket supports bi-directional protocol
	- It can send data in any format
	- It decrease the communication data.
- Limitation of HTTP
	- HTTP is part of bi-direction protocol, which means at same time data will transfer in one direction
	- Server cannot actively send data to client, which makes it hard to implement chat functionality.

### Compare WebSocket vs REST/Ajax
**webSocket**
1.  Server sees that a price has changed and immediately sends a message to each client.
2.  Client receives the message about new price.
**Rest/Ajax**
1.  Client sets up a polling interval
2.  Upon next polling interval trigger, client creates socket connection to server
3.  Server receives request to open new socket
4.  When connection is made with the server, client sends request for new pricing info to server
5.  Server receives request for new pricing info and sends reply with new data (if any).
6.  Client receives new pricing data
7.  Client closes socket
8.  Server receives socket close
- As you can see there's a lot more going on in the Rest/Ajax call from a networking point of view because a new connection has to be established for every new call whereas the webSocket uses an already open call. In addition, in the webSocket cases, the server just sends the client new data when new data is available - the client doens't have to regularly request it.
- A webSocket can also be faster and easier on your networking infrastructure simply because fewer network operations are involved to simply send a packet over an already open webSocket connection versus creating a new connection for each REST/Ajax call, sending new data, then closing the connection. How much of a difference/improvement this makes in your particular application would be something you'd have to measure to really know.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTM3NDk5Njg4NiwtMjAwODgwMDA5NV19
-->