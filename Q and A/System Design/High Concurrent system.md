#### General Method
- Scale out (horizontal scale), e.g: 4core 4G, 200 request/sec how to deal with 400 request/sec
	- Scale-up/Vertical scale: upgrade hardware to 8 code 8G (hardware improve may not be linear, here just a reference), usually we use this when in early stage that hardware improve the performance, but may not work when request is more than the limit of single machine
	- Sacle-out/Horizontal scale: use 2 machine of 4 core 4G, it combines more average performance machine into a distributed set to deal with high current request
		- how to ensure all instances availability when single node of instance goes down
		- how to persist consistancy across different nodes
		- how to increase/decrease instance nodes
- Cache
- Async
#### High concurrency
##### Response time
从用户使用体验的角度来看，200ms 是第一个分界点：接口的响应时间在 200ms 之内，用户是感觉不到延迟的，就像是瞬时发生的一样。而 1s 是另外一个分界点：接口的响应时间在 1s 之内时，虽然用户可以感受到一些延迟，但却是可以接受的，超过 1s 之后用户就会有明显等待的感觉，等待时间越长，用户的使用体验就越差。所以，健康系统的 99 分位值的响应时间通常需要控制在 200ms 之内，而不超过 1s 的请求占比要在 99.99% 以上。
##### Increase number of core
#### High availability
##### MTBF & MTTR
- MTBF（Mean Time Between Failure）是平均故障间隔的意思，代表两次故障的间隔时间，也就是系统正常运转的平均时间。这个时间越长，系统稳定性越高。
- MTTR（Mean Time To Repair）表示故障的平均恢复时间，也可以理解为平均故障时间。这个值越小，故障对于用户的影响越小
- Availability = MTBF / (MTBF + MTTR)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3Njg0MzY4MTZdfQ==
-->