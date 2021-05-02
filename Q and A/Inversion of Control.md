### What Is Inversion of Control
- IOC transfers the control of objects or portions of a program to a container or framework.
- In contrast with traditional programming, in which our custom code makes calls to a library, IoC enables a framework to take control of the flow of a program and make calls to our custom code. To enable this, frameworks use abstractions with additional behavior built in. **If we want to add our own behavior, we need to extend the classes of the framework or plugin our own classes.**
#### Pros
-   decoupling the execution of a task from its implementation
-   making it easier to switch between different implementations
-   greater modularity of a program
-   greater ease in testing a program by isolating a component or mocking its dependencies, and allowing components to communicate through contracts
- We can achieve Inversion of Control through various mechanisms such as: **Strategy design pattern, Service Locator pattern, Factory pattern, and Dependency Injection (DI).**
### What Is Dependency Injection
- Dependency injection is a pattern we can use to implement IoC, where the control being inverted is setting an object's dependencies.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTc0MzU2ODEzXX0=
-->