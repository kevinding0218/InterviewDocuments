### What is the `ThreadLocal` class? How and why would you use it?
- A single `ThreadLocal` instance can store different values for each thread independently, a way to isloate variable across threads.
	- e.g: when our program is processing user requests, normally there is a thread pool in the backend server to distribute each request to one thread. ThreadLocal is to avoid data cross over during high currency multithread requests, like Thread A was supposed to process user A, and Thread B was supposed to process user B but somehow Thread A visited the user B's data, in that case, we need to bind user A info with Thread A by using ThreadLocal, and remove binding when Thread A finished processing
```
//存放用户信息的ThreadLocal  
private  static  final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();  
  
public Response handleRequest(UserInfo userInfo) {  
Response response = new Response();  
try {  
// 1.用户信息set到线程局部变量中  
userInfoThreadLocal.set(userInfo);  
doHandle();  
} finally {  
// 3.使用完移除掉  
userInfoThreadLocal.remove();  
}  
  
return response;  
}  
  
//业务逻辑处理  
private void doHandle () {  
// 2.实际用的时候取出来  
UserInfo userInfo = userInfoThreadLocal.get();  
//查询用户资产  
queryUserAsset(userInfo);  
}
```
- Each thread that accesses the `get()` or `set()` method of a `ThreadLocal` instance is accessing its own, independently initialized copy of the variable.
### How does it work?
- 首先我们通过`ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal()`  初始化了一个Threadlocal 对象，就是上图中说的Threadlocal 引用，这个引用指向堆中的ThreadLocal 对象；
- 然后我们调用`userInfoThreadLocal.set(userInfo);`  这里做了什么事呢？
    我们把源代码拿出来，看一看就清晰了。
-**我们知道 Thread 类有个 ThreadLocalMap 成员变量，这个Map key是Threadlocal 对象，value是你要存放的线程局部变量。**
    - 这里是在当前线程对象的ThreadlocalMap中put了一个元素(Entry)，key是**Threadlocal对象**，value是userInfo。
    理解二件事就都清楚了：
    ThreadLocalMap 类的定义在 Threadlocal中。
-   第一，Thread 对象是Java语言中线程运行的载体，每个线程都有对应的Thread 对象，存放线程相关的一些信息，
-   第二，Thread类中有个成员变量ThreadlocalMap，你就把他当成普通的Map，key存放的是Threadlocal对象，value是你要跟线程绑定的值（线程隔离的变量），比如这里是用户信息对象（UserInfo）
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTI4NDcxMjM5NiwxOTMwNTI1MzY5XX0=
-->