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
- refers to a specific form of decoupling software modules, **helps us to develop loosely couple code by ensuring that high-level modules depend on abstractions rather than concrete implementations of lower-level modules and make unit testing easier for mocking**
	- **High-level modules should not depend on low-level modules. Both should depend on abstractions.**
	- **Constructor injection**/Property(Setter) injection/Method injection
#### Benefit of DI
	- Dependency Injection is a design pattern allows us to write loose coupled code
	- Dependency Injections helps us to manage future changes in code i.e. code maintainability
	- Dependency Injection uses a builder object to initialize objects and provide the required dependencies to the object means it allows you to ‘inject’ a dependency from outside the class
### For Example
- You have a class User which depends on a MySQLDatabase, every time you initiate the User object it binds with MySqlDatabase as a private property. it's going to be really really hard to test the internals of this code because we can't pass in a mock instance of a database second if we wanted to actually rip ourselves away from the internal implementation of connecting to MySQL and utilize another database we can't do that because we've hard-coded our Association of this user to a MySQL  database instance
- we need to actually invert the control flow to say instead of me handling the creation and lifecycle of this database object I'm just gonna have somebody else do it for me and they're just gonna pass it in to my method and therefore I don't care about it anymore I just do what I was told to do and I use the instance of a database that was given to me
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTkzMjUxNjI2NywtMTM2MTMxNTE4NF19
-->