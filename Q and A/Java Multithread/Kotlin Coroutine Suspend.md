
### Overview

In the last wiki, we introduced what is "_Kotlin Coroutine_", what "Kotlin Coroutine" can help and how we get started with "_Kotlin Coroutine_".

At the end of Part I, we get the chance to meet a new component in "_Kotlin Coroutine_" -"_Suspend Function_", which claims it can help us "_suspend_" our method execution in an "_asynchorous_" way without blocking our original thread, let's take a deeper look of what it means.

### Warm Up

Given below example, which part of our code has been suspended with?

1 launch(Dispatchers.Main) {
2    ...
5    val image = suspendingGetImage(imageId)
6    avatarImg.setImageBitmap(image)
7    ...
9 }

  

When the line of code executes at any suspend function, current coroutine will be suspended from current Thread, in another way, our coroutine is detached from the thread that was executing it. Note though the word "suspension" might mean some process paused in the middle, our coroutine has not paused, it means from that line of code, the Thread that our conroutine sits in would not execute it, in above example, the "Main" thread. Alright, now we may wonder, what would happen to our "Main Thread" and "Coroutine" here, let's continue diving into 2 different aspects: Thread vs Coroutine

What happened to Thread that coroutine sits in  
When the program execute at line 5 which is a suspend function, code from line 5 and continue to the end of launch block would be considered as "completed", what would Thread do here?  
Just like any thread lifetcycle in Java executor service, it's either recycled or re-used  
If current thread is a backgroud thread (e.g using Dispatchers.IO), next it may not have anything to do or start to execute any background task.  
If current thread is a foreground thread (e.g. using Dispatchers.Main) like in Android, it might just work on refreshing UI related tasks  
So what happened to the rest of code after suspend function as part of coroutine, they have not been executed right? Let's take a look at what happened to Coroutine once we reached to suspend function and it's been detached from current Thread  
The rest of code will continue executing in specified Thread,  
Who determines its execution Thread? The suspend function  
in below example, the IO Thread which specified inside our suspend function in "withContext".

```  
suspend fun suspendingGetImage(imageId: String) {  
withContext(Dispatchers.IO) {  
getImage(imageId)  
}  
}  
```  
Wait a minute, what did we learn about "withContext" in last blog? Yes "withContext()" would automatically help us switch to original Thread once the suspend function has completed, which means line 6 will be executed back inside our Main Thread.  
Just imagine this is what happened in behind, after suspend function has been completed, "Coroutine" will help us post a new job to let code after suspend function execute back in Main Thread, now we might get some sense why the parameter inside "withContext" is called "Dispatchers", not "Threads", not only it can specificy the next Thread current suspend function should execute on, but also automatically switch back to previous Thread  
Well, we can also customize our own Dispatcher to make Coroutine not swtiching back to previous Thread once suspend function has complete, that is also one of our decisions,

```  
1 launch(Dispatchers.Main) {  
2 ...  
5 val image = suspendingGetImage(imageId)  
6 avatarImg.setImageBitmap(image)  
7 ...  
9 }  
```  
Now we can summarize what suspend means  
Switch off thread for temporarily while switch back after  
When the code reaches at suspend function, that function will be suspended, the so-caled suspended in fact is just switching that function into a different thread, the specialy here is when such function completes, Coroutine will automatically switch code back to original Thread  
btw, the action of switch back to original Thread is called "resume" in Coroutine ([https://stackoverflow.com/questions/47871868/what-does-the-suspend-function-mean-in-a-kotlin-coroutine](https://stackoverflow.com/questions/47871868/what-does-the-suspend-function-mean-in-a-kotlin-coroutine))  
So back to the error why suspend functio should ....  
First, a function gets suspended still needs to resume, to switch back. the "resume" is a job or function inside Coroutine, if our suspend function was not called inside Coroutine, then there is no way to "resume" our function, the error message indicate a suspend function will have to be either directly or indirectly executed inside Coroutine, in a way it is able to switch back from specified Thread during suspend function executes.  
How did "suspend" work  
Let's start with a simple example  
```  
suspend fun suspendingPrint() {  
println("Thread: ${Thread.currentThread().name}")  
}

launch(Dispatchers.Main) {  
suspendingPrint()  
}

output:  
System.out: Thread: main  
```  
We found out that our suspend function still executes inside Main Thread, it's not been switched to a different Thread. Why? Because the function itself did not know which Thread that it should switch to, because we didn't tell Coroutine which Thread we need our function to execute with.  
Compared to our previous example, we have "withContext" which itself is also a suspend function that received a Dispatcher parameter, with the help of this specified Dispatcher, Coroutine knows which thread it should switch to in order to execute function. Therefore, the declartion of suspend Thread does not happen when we call suspend function, but when we define the suspend function  
If we go a little deep inside, we would find the "withContext" is not the real point for thread switch, but some line of code implemented inside "withContext", but that's not in our discussion today  
What I try to express is, the keyword "suspend" would not do the magic of suspend any Coroutine or arrange the work of thread switch, The suspend operation relies on the actual code inside the suspend function, the real thread switching task would be determined inside another suspend function (withContext) of your suspend function (suspendingGetImage)  
Of course there are other API like "withContext" that can do the suspend work, and we can also customize our suspend API  
```  
suspend fun suspendingGetImage(imageId: String) {  
withContext(Dispatchers.IO) {  
getImage(imageId)  
}  
}  
```  
Now we might have another question, if the "suspend" keyword won't do actual suspend work, why we need it and what was it used for? Why Kotlin provide this keyword for us?  
Usage of suspend: Reminder, it provides a reminder from a method creator to method caller, indicatating "I am a time-consuming function, so that I was planned to be executed in backgroud thread by my creator, so whenver you are readlly to call me, please call with Coroutine", such reminder will make our Main Thread none blocking,  
Think about when we write code in Main Thread, we need to be extremely careful, whenever we make a call to a time-consuming function in Main Thread, the UI will get blocked, the user would also freeze during that time, Coroutines actually hand over the work of time-consuming task switching threads to the creator of the function instead of the caller through the hang-up function.  
For the caller, things are very simple, it will only receive a reminder - you need to put me in the coroutine, and don't care about the rest  
In this way, suspend forms a mechanism as a reminder, a mechanism that allows all time-consuming tasks to be executed in the background automatically. When these tasks are called reasonably, the main thread will not be stuck,  
Actually, compiler would provide a warning of "Redundant suspend modifer" if the method we defined as suspend does not contain any suspend action, Then our keyword has only one effect, that is, restricting this function to be called only in the coroutine, which is unnecessary  
How to customize suspend function  
- when do we need to customize  
The answer could be simple, whenver your method is time-consuming, make it suspend, what kinda of operation might be considered time-consuming, I/O operation or complex computation, in addition to some use case that the function may not take long, but it needs to wait then start after a period of time. Example, do XY after 5 seconds

what does async suspend mean
async means the current thread will not be blocking. once the method has been suspended, our current thread become non-blocking, why? because our method execution moved away from current thread. Some might think, we can also use Java executor pool to manually switch thread execution, in a way to mimic non-blocking thread, would that also be considered as non-blocking thread? Yes, it's the same effect. Wait, isn't non blocking suspension a specific character of Coroutine? Why would Thread have the same functionality
In practical, when we talk about thread blocking we usually refer to single thread execution, but in Coroutine, a single coroutine can also be non-blocking
java thread switch and kotlin coroutine are both a way to implement non-blocking, just that kotlin coroutine provides a coding style that read like blocking but execute non-blocking in behind. 


首先，所有代码本质上都是阻塞的，而只有比较耗时的代码才能导致人类可感知的等待
比如你的主线程做一个几十毫秒的操作，它可能导致你的节目卡掉几帧， 这是我们可以人为的观察并感知的，而这也同样上通常意义所说的阻塞，而耗时操作时一共分为两种，CPU的计算耗时和i/o的耗时，而网络就属于i/o, 它的性能瓶颈是i/o，也就是和网络的数据交互而不是cpu的计算速度，所以线程看似被网络交互所阻塞，但这个阻塞是不可避免的只要我们必须做这个i/o， 那它比较慢怎么办？没办法，你只能让线程在那里慢慢处理，但是要注意，它是在”慢慢处理“，而不是单纯地等待，它等待只是因为网络传输的性能低于cpu的性能，但它本质上是在工作的，所以再说一次，这种阻塞不可避免
那协程不是可以挂起，可以不用等待了？协程的挂起本质上是什么？协程的挂起本质上是切线程。 网络请求的挂起是什么？还是切线程，但是把主线程给空置出来，然后在后台线程去做网络交互，而不是先切到后台去做网络请求， 然后在这个网络请求到达所谓的等待阶段的时候再挂起一次，通过这种方式让后台的这个网络交互线程空出来，然后这个网络交互线程就能立即去做别的网络请求，就不用等待了。没这种好事的，我们把网络交互线程给空出来，它就能立即去做下一个网络请求了，那我们刚才那个正在等待的网络请求怎么办？它还是会有另一个线程来承载的呀，不然这个网络请求不会凭空的自己完成。要记得，挂起的本质就是切线程，只是它在完成之后能够自动的切回来，没有别的神奇之处了。
所谓协程的非阻塞式挂起只是用阻塞的方式写出了非阻塞的代码而已，并没有任何相比于线程更加高效的地方，那协程和线程到底是什么关系呢？撇开别的语言，在KOTLIN里面，协程就是基于线程而实现的一套更上层的工具api， 类似于java 自带的executor系列API，以及android的handler系列api， 
有人说协程师用户态的，它的切换不需要和操作系统进行交互，因此它的切换成本比线程低，还有说线程是协作式的，所以不需要线程的同步操作，这些描述对于有些语言是对的，但是对于kotlin协程来说就会有不同了，在kotlin里面，协程的本质还是线程
总结一下
协程就是切线程
挂起就是可以自动切回来的切线程
非阻塞式指的是协程可以用看起来阻塞的代码写出非阻塞的操作

First, all code is inherently blocking, and only time-consuming code can cause human-perceivable waits
For example, if your main thread does an operation of tens of milliseconds, it may cause your program to freeze a few frames, which we can observe and perceive artificially, and this is also called blocking in the usual sense, and it takes time There are two types of operations, CPU time-consuming calculation and I/O time-consuming, while the network belongs to I/O, and its performance bottleneck is I/O, that is, interacting with network data instead of CPU calculation Speed, so the thread seems to be blocked by network interaction, but this blocking is inevitable as long as we have to do this I/O, so what if it is slower? There is no way, you can only let the thread process slowly there, but it should be noted that it is "processing slowly", rather than simply waiting, it waits only because the performance of network transmission is lower than the performance of cpu, but its essence is working, so again, this blocking is unavoidable
Can't the coroutine be suspended, so you don't have to wait? What is the nature of suspension of coroutines? The suspension of the coroutine is essentially thread cutting. What is the hang of network requests? Still cut threads, but leave the main thread vacant, and then do network interaction in the background thread, instead of cutting to the background to make network requests first, and then hang up again when the network request reaches the so-called waiting stage, through This method frees up the network interaction thread in the background, and then the network interaction thread can immediately make other network requests without waiting. If there is no such good thing, we free up the network interaction thread, and it can immediately make the next network request. What about the network request we were waiting for just now? It will still be carried by another thread, otherwise this network request will not be completed out of thin air. Remember, the essence of suspension is to cut threads, but it can automatically switch back after completion, there is no other magic.
The so-called non-blocking suspension of coroutines is just writing non-blocking code in a blocking way, and there is nothing more efficient than threads. So what is the relationship between coroutines and threads? Leaving aside other languages, in KOTLIN, coroutines are a set of higher-level tool APIs based on threads, similar to the executor series APIs that come with java, and the handler series APIs of android.
Some people say that the user mode of the coroutineer does not need to interact with the operating system, so its switching cost is lower than that of the thread, and it is said that the thread is cooperative, so the synchronization operation of the thread is not required. These descriptions are for some The language is correct, but it will be different for kotlin coroutines. In kotlin, the essence of coroutines is threads
in conclusion
Coroutine is thread cutting
Suspend is a cut thread that can be automatically switched back
Non-blocking means that coroutines can write non-blocking operations with seemingly blocking code
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTg1MzI2NDQwMSwtMTgwODg0MDkyMCwtNj
Y2OTY4NjQ3LDE2MjE4MDExNDMsNDY1Mzk4OTU5LC0xMjg5MDIz
NjQ3LC0xMzI3MzE1Mzc0LDgyMDY5MTM3MywtMjA2NjAxMTcxMy
wtNDQ5OTAzODM2LC0xOTc3MTkzMDAwLC0xODQ0MDg2MjQ1LC0x
MTU5MjUxMTg2LDIyNzkxNjkyNywtNTA2NDgyOTA3LDE3NTAzND
k1MTJdfQ==
-->