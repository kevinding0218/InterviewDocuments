### Interface vs Abstract Class
#### Variable Type
- The interface has only static and final variables.
- Abstract class can have final, non-final, static and non-static variables. 
#### Method Type
- Interface can have only abstract methods. 
- An abstract class can have abstract and non-abstract/concrete methods
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
- It is better to use interface when various implementations share only method signature. Polymorphic hierarchy of value types.
- It should be used when various implementations of the same kind share a common behavior.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTU1NTc2NDU4MiwxNzk0OTY4NTIyXX0=
-->