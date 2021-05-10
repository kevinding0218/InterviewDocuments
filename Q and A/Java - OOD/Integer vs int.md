### int vs Integer
- int is a primitive data type while Integer is a Wrapper class
- int as other **primitive type was stored in stack**, so we usually use `int` for temp value, while Integer as other **reference type was stored in heap**, we usually use `Integer` for POJO so that we can serialize or deserialize by telling if we have the value
- int was designed for JVM cost and performance perpective, it is not designed for Object Oriented. 
- int default value is 0 while Integer default as null
- Integer was a class , when it was initialized will have a reference in JVM. Variables of type Integer store references to Integer objects, just as with any other reference (object) type
- we can't assign a String value to an int value directly or even by casting, but we can do this with Integer
- Integer can be directly converted to other types like binary or hex
- int can be compared by using `==` , while Integer usually compares by using `equals`, if number ranges is within ~127 - 128 then we can use "because when using `=="` to compare Integer as during this data range, it uses primitive type as cache in memory, otherwise it, it's actually a reference type so the comparison was happening at pointer which referenced to the object memory address, Integer also have a mechanism like cache so it can be comparesd by address and value (reference type)using `==` when value between -128 ~ 127
<!--stackedit_data:
eyJoaXN0b3J5IjpbODU4NDEwNzAxXX0=
-->