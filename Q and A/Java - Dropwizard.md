### static initializer / static block
- The static initializer is a `static {}` block of code inside java class, and run only one time before the constructor or main method is called.
- is a block of code  `static { ... }`  inside any java class. and executed by virtual machine when class is called.
- No  `return`  statements are supported.
- No arguments are supported.
- No  `this`  or  `super`  are supported.
- most of the time it is used when doing database connection, API init, Logging and etc.
- similar as static constructor in C#
#### why static constructor is not allowed
- Static Belongs to Class, Constructor to Object
	- Whereas a Constructor belongs to the object and called when we use the new operator to create an instance. 
	- Since a constructor is not class property, it makes sense that it’s not allowed to be static.
- Static Block/Method can’t access non-static variables
	- static methods can’t access non-static variables. Same is true for static block also.
	- if we make constructor as static then it won’t be able to initialize the object variables. That will defeat the whole purpose of having a constructor for creating the object. So it is justified to have the constructor as non-static.
	- we can’t use `this` inside a static method to refer to the object variable.
- Static Constructor will break inheritance
	- In Java, every class implicitly extends Object class. We can define a class hierarchy where subclass constructor calls the superclass constructor. This is done by `super()` method call.
	- Most of the times JVM automatically calls the superclass constructor but sometimes we have to manually call them if there are multiple constructors in the superclass.
	- If you look at the `super()` method, it’s not static. So if the constructor becomes static, we won’t be able to use it and that will break [inheritance in java](https://www.journaldev.com/644/inheritance-java-example).
- Java Static Constructor Alternative
	- If you want to initialize some  [static](https://www.journaldev.com/1365/static-keyword-in-java)  variables in the class, you can use static block. 
	- Note that we can’t pass arguments to the static block, so if you want to initialize static variables then you can do that in the normal constructor too.
### Nested classes
- Nested classes are divided into two categories: **static** and **non-static**. 				
	- Nested classes that are declared static are simply called static nested classes. 
	- Non-static nested classes are called inner classes.
#### static nested class
- Static nested classes are accessed using the enclosing class name
- For example, to create an object for the static nested class, use this syntax:
```
OuterClass.StaticNestedClass nestedObject = new OuterClass.StaticNestedClass();
```
#### inner class
- Objects that are instances of an inner class exist within an instance of the outer class
```
class OuterClass {
    ...
    class InnerClass {
        ...
    }
}
```
- An instance of InnerClass can exist only within an instance of OuterClass and has direct access to the methods and fields of its enclosing instance.
- To instantiate an inner class, you must first instantiate the outer class. Then, create the inner object within the outer object with this syntax
```
OuterClass outerObject = new OuterClass()
OuterClass.InnerClass innerObject = outerObject.new InnerClass();
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzYyMDA0NDYyLDEyMjgyMTE3ODgsMTU3Mz
g4NjY4MiwyMDA1NzYxNjQwLC0xMjAwNjQ1MDA3LC0zNjM2MDA1
OTUsMTE3MjUyOTEyMSwtMTIzNTk5NTMwNF19
-->