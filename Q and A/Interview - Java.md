### int vs Integer
- int is a primitive data type while Integer is a Wrapper class
- int was designed for JVM cost and performance perpective, it is not designed for Object Oriented. 
- Integer was a class , when it was initialized will have a reference in JVM. Variables of type Integer store references to Integer objects, just as with any other reference (object) type
- we can't assign a String value to an int value directly or even by casting, but we can do this with Integer
- Integer can be directly converted to other types like binary or hex
- int default value is 0 while Integer default as null
- int can be compared by using `==` , while Integer usually compares by using `equals`, because when using `==` to compare Integer, it's actually a reference type so the compar
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTA5ODAwNTE2Ml19
-->