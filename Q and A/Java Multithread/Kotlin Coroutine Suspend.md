In the last wiki, we introduced what is Kotlin Coroutine, why we need Kotlin Coroutine and how we get start with Kotlin Coroutine, at the end of the guidance, we get the chance to meet our new friend -"Suspend Function", but what exactly is async suspend, we would continue in this wiki

Given below example, which part of our code has been suspended with?
```
launch(Dispatchers.Main) {
  ...
  val image = suspendingGetImage(imageId)
  avatarImg.setImageBitmap(image)
}
```
[] Thread
[] Function
[x] Coroutine
Do we remember what "Coroutine" refers here? 
(Expand) Yes, the "Coroutine" refers to all lines of code inside our "launch()" block
(Expand) Except "launch", there is another function called "async" that also is used for creating a coroutine, async is.... for now let's focus on "launch()"

When the line of code executes at any suspend function




<!--stackedit_data:
eyJoaXN0b3J5IjpbMjM1ODIwNTMyLDE3NTAzNDk1MTJdfQ==
-->