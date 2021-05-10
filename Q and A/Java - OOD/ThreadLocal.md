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
- `ThreadLocal` instances are typically private static fields in classes that wish to associate state with a thread (e.g., a user ID or transaction ID)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTQyMTI4ODE0MywxOTMwNTI1MzY5XX0=
-->