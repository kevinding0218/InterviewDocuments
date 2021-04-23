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
##### Increase system processing number 
- 你可以把系统的处理核心数增加为两个，并且增加一个进程，让这两个进程跑在不同的核心上。这样从理论上，你系统的吞吐量可以增加一倍。
- 是不是无限制地增加处理核心数就能无限制地提升性能，从而提升系统处理高并发的能力呢？很遗憾，随着并发进程数的增加，并行的任务对于系统资源的争抢也会愈发严重。在某一个临界点上继续增加并发进程数，反而会造成系统性能的下降，这就是性能测试中的拐点模型。
##### Decrease response time in single task
- 想要减少任务的响应时间，首先要看你的系统是 CPU 密集型还是 IO 密集型的，因为不同类型的系统性能优化方式不尽相同。
- CPU 密集型系统中，需要处理大量的 CPU 运算，那么选用更高效的算法或者减少运算次数就是这类系统重要的优化手段。比方说，如果系统的主要任务是计算 Hash 值，那么这时选用更高性能的 Hash 算法就可以大大提升系统的性能。
	- 发现这类问题的主要方式，是通过一些 Profile 工具来找到消耗 CPU 时间最多的方法或者模块
- IO 密集型系统指的是系统的大部分操作是在等待 IO 完成，这里 IO 指的是磁盘 IO 和网络 IO。我们熟知的系统大部分都属于 IO 密集型，比如数据库系统、缓存系统、Web 系统, 发现这类性能瓶颈的手段主要有两类。
	- 第一类是采用工具, 比如说 Java 语言就有其专属的内存分析工具。
	- 另外一类手段就是可以通过监控来发现性能问题。在监控中我们可以对任务的每一个步骤做分时的统计，从而找到任务的哪一步消耗了更多的时间
#### High availability
##### MTBF & MTTR
- MTBF（Mean Time Between Failure）是平均故障间隔的意思，代表两次故障的间隔时间，也就是系统正常运转的平均时间。这个时间越长，系统稳定性越高。
- MTTR（Mean Time To Repair）表示故障的平均恢复时间，也可以理解为平均故障时间。这个值越小，故障对于用户的影响越小
- Availability = MTBF / (MTBF + MTTR)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTU0MjMyMDY3XX0=
-->