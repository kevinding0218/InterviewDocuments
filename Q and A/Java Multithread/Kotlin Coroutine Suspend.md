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
So what happened to the rest of code after suspend function as part of coroutine, they have not been executed right? Let's take a look at what Coroutine does after it's been detached from current Thread



<!--stackedit_data:
eyJoaXN0b3J5IjpbMTczNjQ0ODQ4OSwyMjc5MTY5MjcsLTUwNj
Q4MjkwNywxNzUwMzQ5NTEyXX0=
-->