### Interface Default Method vs Static Method [Link](https://www.journaldev.com/2752/java-8-interface-changes-static-method-default-method)
- Default Method
	- "default" keyword infront of the implementation method with in the interface. This is the new way of declaring the method body in Java 8 for an interface, default methods comes along with implementation.
	- help us in extending interfaces without having the fear of breaking implementation classes, it will help us in removing base implementation classes, we can provide default implementation and the implementation classes can chose which one to override.
- Static Method;
	- Java interface static method is similar to default method except that we can’t override them in the implementation classes. This feature helps us in avoiding undesired results incase of poor implementation in implementation classes, static method is visible to interface methods only
	- static methods are good for providing utility methods, for example null check, collection sorting etc.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE4NTYzNTY4NF19
-->