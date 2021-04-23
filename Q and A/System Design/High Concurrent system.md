#### General Method
- Scale out (horizontal scale), e.g: 4core 4G, 200 request/sec how to deal with 400 request/sec
	- Scale-up/Vertical scale: upgrade hardware to 8 code 8G (hardware improve may not be linear, here just a reference), usually we use this when in early stage that hardware improve the performance, but may not work when request is more than the limit of single machine
	- Sacle-out/Horizontal scale: use 2 machine of 4 core 4G, it combines more average performance machine into a distributed set to deal with high current request
		- how to ensure all instances availability when single node of instance goes down
		- how to persist consistancy across different nodes
		- how to increase/decrease instance nodes
- Cache
- Async
#### Target
- High concurrency
- High availability
- Easy to te
#### Response time
从用户使用体验的角度来看，200ms 是第一个分界点：接口的响应时间在 200ms 之内，用户是感觉不到延迟的，就像是瞬时发生的一样。而 1s 是另外一个分界点：接口的响应时间在 1s 之内时，虽然用户可以感受到一些延迟，但却是可以接受的，超过 1s 之后用户就会有明显等待的感觉，等待时间越长，用户的使用体验就越差。所以，健康系统的 99 分位值的响应时间通常需要控制在 200ms 之内，而不超过 1s 的请求占比要在 99.99% 以上。
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTcyOTI2ODk1OV19
-->