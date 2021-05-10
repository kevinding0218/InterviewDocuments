[Link](https://howtodoinjava.com/gang-of-four-java-design-patterns/)
### Creational Design Patterns
- Creational patterns often used in place of direct instantiation with constructors. They make the creation process more adaptable and dynamic. In particular, they can provide a great deal of flexibility about which objects are created, how those objects are created, and how they are initialized.
#### Builder
- Builder design pattern is an alternative way to construct complex objects and should be used only when we want to build different types of immutable objects using same object building process.
#### Factory
- Factory design pattern is most suitable when complex object creation steps are involved. To ensure that these steps are centralized and not exposed to composing classes.
#### Singleton
- Singleton enables an application to have one and only one instance of a class per JVM.
#### Abstract factory
- Abstract factory pattern is used whenever we need another level of abstraction over a group of factories created using factory pattern.
### Structural Design Patterns
- Structural design patterns show us how to glue different pieces of a system together in a flexible and extensible fashion. These patterns help us guarantee that when one of the parts changes, the entire application structure does not need to change.
#### Composite
- Composite design pattern helps to compose the objects into tree structures to represent whole-part hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.
#### Facade
- Facade design pattern provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.
#### Decorator
- Decorator design pattern is used to add additional features or behaviors to a particular instance of a class, while not modifying the other instances of same class.
### Behavioral Design Patterns
- Behavioral patterns abstract an action we want to take on the object or class that takes the action. By changing the object or class, we can change the algorithm used, the objects affected, or the behavior, while still retaining the same basic interface for client classes.
#### Command
- Command design pattern is useful to abstract the business logic into discrete actions which we call commands. These command objects help in loose coupling between two classes where one class (invoker) shall call a method on other class (receiver) to perform a business operation.
#### Observer
- Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. It is also referred to as the publish-subscribe pattern.
#### State
-   
In state pattern allows an object to alter its behavior when its internal state changes. The object will appear to change its class. There shall be a separate concrete class per possible state of an object.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1Nzc2NDgxMjldfQ==
-->