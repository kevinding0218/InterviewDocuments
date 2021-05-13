### Volatile
- In Java, each thread has its own stack, including its own copy of variables it can access. When the thread is created, it copies the value of all accessible variables into its own stack. The  `volatile`  keyword basically says to the JVM “Warning, this variable may be modified in another Thread”.
- In all versions of Java, the  `volatile`  keyword guarantees global ordering on reads and writes to a variable. **This implies that every thread accessing a volatile field will read the variable’s current value instead of (potentially) using a cached value**.
- `volatile` reads and writes establish a happens-before relationship, much like acquiring and releasing a mutex.
- One common example for using  `volatile`  is for a flag to terminate a thread. If you’ve started a thread, and you want to be able to safely interrupt it from a different thread, you can have the thread periodically check a flag (i.e., to stop it, set the flag to  `true`). 
- By making the flag volatile, you can ensure that the thread that is checking its value will see that it has been set to  `true`  without even having to use a synchronized block. For example:
```
public class Foo extends Thread {
    private volatile boolean close = false;
    public void run() {
        while(!close) {
            // do work
        }
    }
    public void close() {
        close = true;
        // interrupt here if needed
    }
}
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTEwNzE5MDE4MF19
-->