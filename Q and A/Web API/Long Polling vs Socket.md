﻿- [Web Real-time](#web-real-time)
  * [Websocket](#websocket)

## Web Real-time
### Websocket
- Before websocket, HTTP could only be requested from the Client which is a single-way request.
- Polling vs long-polling vs streaming
	- Polling: Periodically client send request to server for requesting data, the drawback is each request would contain the HttpHeader which is steam cost and CPU memory cost, also user may not get real-time data if time interval is too long, server side will be under pressure if time interval is too short.
	- Long Polling: After client send HTTP request to server, it will keep waiting until there is new message coming back, it decrease the CPU usage and stream usage but because HTTP Header data normally is large size, and the real usage may only to 3%, which is a waste of resource usage.
	- iFrame(stream): insert a hidden iframe and use its `src` attribute to create a long pulling between client and server, this would be benefit for real-time and browser compatibility but increase the resource usage of server, browser will keep spinning if loading is not completed.
- Websocket
	- it uses the socket from TCP on webpages, once web server and client establish a TCP protocol connection, all communication will be rely on the particular protocol. Data could be sent as JSON/XML/HTML/Image or any format.
	- Because it's a protocol based on HTTP, once communication establishes, either client/server side could send content to the other.
	- Websocket supports bi-directional protocol
	- It can send data in any format
	- It decrease the communication data.
- Limitation of HTTP
	- HTTP is part of bi-direction protocol, which means at same time data will transfer in one direction
	- Server cannot actively send data to client, which makes it hard to implement chat functionality.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwMDg4MDAwOTVdfQ==
-->