In the last wiki, we introduced what is Kotlin Coroutine, why we need Kotlin Coroutine and how we get start with Kotlin Coroutine, at the end of the guidance, we get the chance to meet our new friend -"Suspend Function", but what exactly is async suspend, we would continue in this wiki

Given below example, which part of our code has been suspended with?
```
1 launch(Dispatchers.Main) {
2    ...
5    val image = suspendingGetImage(imageId)
6    avatarImg.setImageBitmap(image)
7    ...
9 }
```
[] Thread
[] Function
[x] Coroutine
Do we remember what "Coroutine" refers here? 
(Expand) Yes, the "Coroutine" refers to all lines of code inside our "launch()" block
(Expand) Except "launch", there is another function called "async" that also is used for creating a coroutine, async is.... for now let's focus on "launch()"

When the line of code executes at any suspend function, current coroutine will be suspended from current Thread, in another way, our coroutine is detached from the thread that was executing it. Note though the word "suspension" might mean some process paused in the middle,  our coroutine has not paused, it means from that line of code, the Thread that our conroutine sits in would not execute it, in above example, the "Main" thread. Alright, now we may wonder, what would happen to our "Main Thread" and "Coroutine" here, let's continue diving into 2 different aspects: Thread vs Coroutine

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
2    ...
5    val image = suspendingGetImage(imageId)
6    avatarImg.setImageBitmap(image)
7    ...
9 }
```
Now we can summarize what suspend means
Switch off thread for temporarily while switch back after 
When the code reaches at suspend function, that function will be suspended, the so-caled suspended in fact is just switching that function into a different thread, the specialy here is when such function completes, Coroutine will automatically switch code back to original Thread
btw, the action of switch back to original Thread is called "resume" in Coroutine (https://stackoverflow.com/questions/47871868/what-does-the-suspend-function-mean-in-a-kotlin-coroutine)
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
Of course there are other API like "withContext" that can do the suspend  work, and we can also customize our suspend API
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
- how to write customization
add suspend to function, wrap with withContext inside
Why withContext() here?
this is the most common way to pick up, as it's the easiet and most starightforward way
there are also other suspend function as "delay", what it does is to wait for a period of time then execute the code after, 
What is suspend
In fact, it is the same as the coroutine, it is thread Switch , but it is a thread Switch that can be automatically switched back
Next we will continue to talk about what is Async in coroutine and some intersting topic comparing Coroutine vs Thread



在语法代码上，kt协程还是简洁一点易读。然后场景上他们两个其实并不是同一个概念。虚拟线程适用于CPU密集型的计算任务，但是协程的话，它其实更适合io型的任务。虚拟线程其实可以理解为了代替线程，对线程进行更好的优化，而携程它只是一种编程模式，它实际上与线程是没什么关系的，它只是对任务的线程所属进行一个调度的管理。底层上虚拟线程其实还是gvm中的一种新线程，但是协程程的话它并与jvm没什么关系，它是由kt编译器进行管理的。像kt和Java是完全可以混写的，你完全也可以把携程和虚拟线程混起来一起用，不过当他们两个功能差不多的时候，协程程更简洁更易读，其实学kt也不必担心，虚拟线程会把kt的协程优势给压下去。这kt的优势不只是协程这一点。
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwNjYwMTE3MTMsLTQ0OTkwMzgzNiwtMT
k3NzE5MzAwMCwtMTg0NDA4NjI0NSwtMTE1OTI1MTE4NiwyMjc5
MTY5MjcsLTUwNjQ4MjkwNywxNzUwMzQ5NTEyXX0=
-->