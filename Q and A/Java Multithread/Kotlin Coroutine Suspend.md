In the last wiki, we introduced what is Kotlin Coroutine, why we need Kotlin Coroutine and how we get start with Kotlin Coroutine, at the end of the guidance, we get the chance to meet our new friend -"Suspend Function", but what exactly is async suspend, we would continue in this wiki

Given below example, which part of our code has been suspended with?
```
launch(Dispatchers.Main) {
  ...
  val image = suspendingGetImage(imageId)
  avatarImg.setImage
}
```
[] Thread
[] Function
[x] Coroutine


<!--stackedit_data:
eyJoaXN0b3J5IjpbMTcyNDYwMTI2OV19
-->