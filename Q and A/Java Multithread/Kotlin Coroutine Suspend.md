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
We found out that our suspend function still executes inside Main Thread, it's not been switched to a different Thread. Why? Because the function itself did not know which Thread that it should switch to, bec

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTExNjUyMzQ0MDUsLTE4NDQwODYyNDUsLT
ExNTkyNTExODYsMjI3OTE2OTI3LC01MDY0ODI5MDcsMTc1MDM0
OTUxMl19
-->