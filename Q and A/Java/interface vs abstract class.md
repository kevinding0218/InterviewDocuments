### Interface vs Abstract Class
#### Variable Type
- The interface has only static and final variables.
- Abstract class can have final, non-final, static and non-static variables. 
#### Method Type
- Interface can have only abstract methods. 
- An abstract class has protected and public abstract methods.
#### Implementation
- A Class can implement multiple interfaces
- A Class can inherit only one Abstract Class because Java does not support multiple inheritances
#### Multiple Implementation
- An interface can extend another Java interface only
- an abstract class can extend another Java class and implement multiple Java interfaces.
#### Default Implementation
- While adding new stuff to the interface, it is a nightmare to find all the implementors and implement newly defined stuff.
- In case of Abstract Class, you can take advantage of the default implementation.
#### When to use
- It is better to use interface when various implementations share only method signature. Polymorphic hierarchy of value types. (Future enhancement)
- It should be used when various implementations of the same kind share a common behavior. (To avoid independence)
#### Constructor
- An interface cannot declare constructors or destructors.
- An abstract class can declare constructors and destructors.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzcyNDgzNjgyLDE3OTQ5Njg1MjJdfQ==
-->