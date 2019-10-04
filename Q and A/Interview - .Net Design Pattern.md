- [Repositor-Pattern](#repositor-pattern)
  * [Definition](#definition)
  * [Benefit](#benefit)
- [Ioc-DI-SL](#ioc-di-sl)
  * [Ioc vs DI vs SL](#ioc-vs-di-vs-sl)
  * [DI Definition](#di-definition)
    + [Benefit of DI](#benefit-of-di)
  * [SL Definition](#sl-definition)
  * [Difference between DI & SL](#difference-between-di---sl)
- [CQRS-Pattern](#cqrs-pattern)
  * [Link (https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs)](#link--https---docsmicrosoftcom-en-us-azure-architecture-patterns-cqrs-)
  * [Definition of Command and Query Responsibility Segregation](#definition-of-command-and-query-responsibility-segregation)
  * [Benefit](#benefit-1)
  * [When to use this pattern](#when-to-use-this-pattern)
- [Singleton Pattern](#singleton-pattern)
  * [Definition](#definition-1)
  * [Implementation](#implementation)
  * [Uses of Singleton Pattern](#uses-of-singleton-pattern)
    + [Logger :](#logger--)
    + [Cache :](#cache--)
  * [What is the difference between Static class and Singleton instance?](#what-is-the-difference-between-static-class-and-singleton-instance-)
- [Factory Pattern](#factory-pattern)
  * [Definition](#definition-2)
  * [Implementation](#implementation-1)
  * [Uses of Factory pattern](#uses-of-factory-pattern)
  * [Factory pattern vs Service Locator](#factory-pattern-vs-service-locator)
- [Facade Pattern](#facade-pattern)
  * [Definition](#definition-3)
  * [Implementation](#implementation-2)
- [SOLID Design Principles](#solid-design-principles)
  * [Benefit](#benefit-2)
  * [S: Single Responsibility Principle (SRP)](#s--single-responsibility-principle--srp-)
  * [O: Open closed Principle (OSP)](#o--open-closed-principle--osp-)
  * [L: Liskov substitution Principle (LSP)](#l--liskov-substitution-principle--lsp-)
  * [I: Interface Segregation Principle (ISP)](#i--interface-segregation-principle--isp-)
  * [D: Dependency Inversion Principle (DIP)](#d--dependency-inversion-principle--dip-)

 
## Repositor-Pattern
### Definition
	- Mediates between the domain and data mapping layers, acting like an **in-memory collection** of domain objects.
### Benefit
	- Minimizes the duplicate query logic
	- Decouples your application from persistence frameworks ( a new ORM every 2 years)
	-  Promotes test ability
## Ioc-DI-SL
### Ioc vs DI vs SL
	- **Dependency Injection** (DI) and **Service Locator** (SL) are the ways of Implementing **Inversion Of Control** (IOC)
### DI Definition
	- refers to a specific form of decoupling software modules, **helps us to develop loosely couple code by ensuring that high-level modules depend on abstractions rather than concrete implementations of lower-level modules and make unit testing easier for mocking**
	- **Principle**: High-level modules should not depend on low-level modules. Both should depend on abstractions.
	- Constructor injection/Property(Setter) injection/Method injection
#### Benefit of DI
	- Dependency Injection is a design pattern allows us to write loose coupled code
	- Dependency Injections helps us to manage future changes in code i.e. code maintainability
	- Dependency Injection uses a builder object to initialize objects and provide the required dependencies to the object means it allows you to ‘inject’ a dependency from outside the class
### SL Definition
	- It introduces a **locator object** that is used to resolve dependencies within a class.
### Difference between DI & SL
- The `Service Locator` allows you to “resolve” a dependency **within a class** and the `Dependency Injection` allows you to **“inject” a dependency from outside the class**.
- When you use a service locator, every class will have a dependency on your service locator but in dependency injection,the dependency injector will typically be called only once at startup, to inject dependencies into the main class.
- The Service Locator pattern is easier to use in an existing codebase as it makes the overall design loosely coupled without forcing changes to the public interface. Code that is based on the Service Locator pattern is less readable than the equivalent code that is based on Dependency Injection.
- **For example**: The client class has a dependency on the Service class.
	```
	public class Service
	{
		public void Serve()
		{
			Console.WriteLine("Service Called"); 
		}
	} 
	public class Client
	{
		private Service _service; 
		public Client()
		{
			this._service = new Service();
		} 
		public void Start()
		{
			Console.WriteLine("Service Started");
			this._service.Serve(); 
		}
	}
	```
	- **Using DI**
		- The Injection happens in the constructor, by passing the Service that implements the IService-Interface.
		- The dependencies are assembled by a “Builder” and Builder responsibilities are as follows:
			-	_knowing the types of each IService_
			- according to the request, send the abstract IService to the Client
			```
			public interface IService
			{
				void Serve();
			}
			public class Service : IService
			{
				public void Serve()
				{
					Console.WriteLine("Service Called");
				}
			} 
			public class Client
			{
				private IService _service;
				public Client(IService service)
				{
					this._service = service;
				} 
				public void Start()
				{
					Console.WriteLine("Service Started");
					this._service.Serve(); 
				}
			}
			```
	- **Using SL**
		- The Inversion happens in the constructor, by locating the Service that implements the IService-Interface.
		- The dependencies are assembled by a “LocateService” class.
		```
		public interface IService
		{
			void Serve();
		} 
		public class Service : IService
		{
			public void Serve()
			{
				Console.WriteLine("Service Called");
			}
		} 
		public static class LocateService
		{
			public static IService _Service { get; set; } 
			public static IService GetService()
			{
				if (_Service == null)
				_Service = new Service();
				return _Service;
			}
		} 
		public class Client
		{
			private IService _service; 
			public Client()
			{
				this._service = LocateService.GetService();
			} 
			public void Start()
			{
				Console.WriteLine("Service Started");
				this._service.Serve(); 
			}
		}
		```
## CQRS-Pattern
### Link (https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs)
### Definition of Command and Query Responsibility Segregation 
- Segregate operations that read data from operations that update data by using separate interfaces. This can maximize performance, scalability, and security.
- CQRS addresses separates reads and writes into separate models, using **commands to update data, and queries to read data**.
	- **Commands** should be **task based**, rather than data centric. ("Book hotel room", not "set ReservationStatus to Reserved").
	-  **Commands** may be placed on **a queue for asynchronous processing**, rather than being processed synchronously.
	-  **Queries never modify the database**. A query returns a DTO that does not encapsulate any domain knowledge.
- For greater isolation, you can physically **separate the read data from the write data**.
	- For example, the **write database** might be **relational**, while the **read database** is a **document database**.
	- If separate read and write databases are used, they must be **kept in sync.** Typically this is accomplished by **having the write model publish an event whenever it updates the database. Updating the database and publishing the event must occur in a single transaction.**
### Benefit
-   **Independent scaling**. CQRS allows the read and write workloads to scale independently, and may result in fewer lock contentions.
-   **Optimized data schemas**. The read side can use a schema that is optimized for queries, while the write side uses a schema that is optimized for updates.
-   **Security**. It's easier to ensure that only the right domain entities are performing writes on the data.
-   **Separation of concerns**. Segregating the read and write sides can result in models that are more maintainable and flexible. Most of the complex business logic goes into the write model. The read model can be relatively simple.
-   **Simpler queries**. By storing a materialized view in the read database, the application can avoid complex joins when querying.
### When to use this pattern
- Collaborative domains where many users access the same data in parallel.
- Task-based user interfaces where users are guided through a complex process as a series of steps or with complex domain models.
	- The write model has a full command-processing stack with business logic, input validation, and business validation. The write model may treat a set of associated objects as a single unit for data changes (an aggregate, in DDD terminology) and ensure that these objects are always in a consistent state.
	- The read model has no business logic or validation stack, and just returns a DTO for use in a view model. The read model is eventually consistent with the write model.
- Scenarios where performance of data reads must be fine tuned separately from performance of data writes, especially when the number of reads is much greater than the number of writes.
## Singleton Pattern
### Definition
- The Singleton pattern ensures that a class has only one instance and provides a global point of access to it
### Implementation
- To ensure the class has only one instance, we **mark the constructor as private**.  So, we can only instantiate this class from within the class.
- We create a **static variable** that will hold the **instance of the class**.
Then, we create a **static method** that *provides the instance of the singleton class*. This method checks if an instance of the singleton class is available. It creates an instance, if its not available; Otherwise, it returns the available instance.
### 1st version
	```
	public class Singleton  {
		private  static  Singleton _instance;
		private  Singleton()  {}
		// call with Singleton.GetInstance().AnyMethod
		public  static  Singleton GetInstance(){
			if(_instance  ==  null){
				_instance  =  new  Singleton();
			}
			return  _instance;
		}
		or
		// call with Singleton.Instance.AnyMethod
		public  static  Singleton Instance{
			get{
				 if(_instance  ==  null){
					_instance  =  new Singleton();
				 }
				return  _instance;
			}
		}
	}
	```
#### Pros and Cons
- **Pros**: Working Singleton for Single-Threaded Model
- **Cons**: Not Thread-safe (cannot be used in concurrent system)
### 2nd version	
	```
	public class SimpleThreadSafeSingleton {
		private static SimpleThreadSafeSingleton _instance = null:
		private static readonly object padlock = new object();
		
		SimpleThreadSafeSingleton() {}
		
		public static SimpleThreadSafeSingleton Instance
		{
			get {
				lock(padlock)
				{
					if (_instance = null)
						_instance = new SimpleThreadSafeSingleton();
					return _instance;
				}
			}
		}
	}
	```
#### Pros and Cons
- **Pros**: This implementation is thread-safe. The thread takes out a lock on a shared object, and then checks whether or not the instance has been created before creating the instance. This takes care of the memory barrier issue (as locking makes sure that all reads occur logically after the lock acquire, and unlocking makes sure that all writes occur logically before the lock release) and ensures that only one thread will create an instance (as only one thread can be in that part of the code at a time - by the time the second thread enters it,the first thread will have created the instance, so the expression will evaluate to false)
- **Cons**: performance suffers as a lock is acquired every time the instance is requested.
### 3rd version	
	```
	public sealed class Singleton
    	{
    		private static readonly Singleton instance = new Singleton();

    		// Explicit static constructor to tell C# compiler
    		// not to mark type as beforefieldinit
		static Singleton()
		{
		}

		private Singleton() {}

		public static Singleton Instance
		{
		    get
		    {
		    	return instance;
		    }
		}
    	}
	```
#### Use of static constructor
- **Pros**: static constructors in C# are specified to execute only when an instance of the class is created or a static member is referenced, and to execute only once per AppDomain. Given that this check for the type being newly constructed needs to be executed whatever else happens, it will be faster than adding extra checking as in the previous examples.
- **Cons**: It's not as lazy as the other implementations. In particular, if you have static members other than Instance, the first reference to those members will involve creating the instance. This is corrected in the next implementation.
### 4th version	
	```
	public sealed class Singleton
    	{
    		private Singleton() {}

    		public static Singleton Instance { get { return Nested.instance; } }

    		private class Nested
		{
		    // Explicit static constructor to tell C# compiler
		    // not to mark type as beforefieldinit
		    static Nested() {}

		    internal static readonly Singleton instance = new Singleton();
		}
    	}
	```
- **Pros**:  instantiation is triggered by the first reference to the static member of the nested class, which only occurs in Instance. This means the implementation is fully lazy, but has all the performance benefits of the previous ones. Note that although nested classes have access to the enclosing class's private members, the reverse is not true, hence the need for instance to be internal here
### 5th version	
	```
	public sealed class Singleton
    	{
    		private static readonly Lazy<Singleton> lazy = new Lazy<Singleton>(() => new Singleton());
	
            	public static Singleton Instance { get { return lazy.Value; } }

            	private Singleton(){ }
        }
	```
#### Using .Net 4's Lazy<T> type
- If you're using .NET 4 (or higher), you can use the System.Lazy<T> type to make the laziness really simple
- All you need to do is pass a delegate to the constructor which calls the Singleton constructor - which is done most easily with a lambda expression.
- The code above implicitly uses LazyThreadSafetyMode.ExecutionAndPublication as the thread safety mode for the Lazy<Singleton>.
	
### Uses of Singleton Pattern
#### Logger :
- Singleton pattern is a good option for the Logger class when we want to create one log file with the logged messages. If we have more than one instances of Logger in the application, a new log will be created with every instance.
#### Cache :
- We can use Singleton pattern to implement caches as that provides a single global access to it.
### What is the difference between Static class and Singleton instance?
- In c# a static class cannot implement an interface. When a single instance class needs to implement an interface for some business reason or IoC purposes, you can use the Singleton pattern without a static class.  
- You can clone the object of Singleton but, you can not clone the static class object  
- Singleton object stores in Heap but, static object stores in stack  
- A singleton can be initialized lazily or asynchronously while a static class is generally initialized when it is first loaded
## Factory Pattern
### Definition
- The Factory method is a creational design pattern which provides an interface for creating objects without specifying their concrete classes. 
- It defines a method which we can use to create an object instead of using its constructor. 
- Subclasses can override this method and create objects of different types.
### Implementation
- For example, Our app will receive an input from a user and based on that input will trigger a required action (cooling or warming the room).
	- let’s start with an interface
		```
		public  interface  IAirConditioner{
			void  Operate();
		}
		```
	- **Factory Classes**: We are going to start with the `AirConditionerFactory` **abstract class**, this abstract class provides an interface for object creation in derived classes.
		```
		public abstract class AirConditionerFactory{
			public abstract IAirConditioner Create(double  temperature);
		}
		```
	- let’s implement our concrete creator classes
		```
		public class CoolingFactory : AirConditionerFactory {
			public override IAirConditioner Create(double temperature)  =>  new  Cooling(temperature);
		}
		public class WarmingFactory : AirConditionerFactory{
			public override IAirConditioner Create(double temperature)  =>  new  Warming(temperature);
		}
		```
	- Now we are ready to start using our Factory methods. 
		- We are using a **public factory method** to **replace our constructor** while creating objects
		- Note here our constructor becomes **private constructor**
		- In many examples, we can see the switch statement which switches through the user’s input and selects the required factory class, but in larger application with complex  we can use Reflection which will improve code readability
			```
			public class AirConditioner
			{
				private readonly Dictionary<Actions, AirConditionerFactory> _factories;
				
				public  AirConditioner(){
					_factories  =  new  Dictionary<Actions,  AirConditionerFactory> {
						{ Actions.Cooling, new CoolingFactory() },
						{ Actions.Warming, new WarmingFactory() }
					};
				}
				// Use factory method to replace our constructor while creating an object
				public static AirConditioner InitializeFactories() => new AirConditioner();
				
				// the method which is going to execute appropriate creation
				public IAirConditioner ExecuteCreation(Actions action, double temperature)  =>_factories[action].Create(temperature);
			}
			```
		- Finally we can call the factory class to initialize the object
			```
			static void Main(string[] args){
				AirConditioner
					.InitializeFactories()
					.ExecuteCreation(Actions.Cooling,  22.5)
					.Operate();
			}
			```
### Uses of Factory pattern
1.  Factory design pattern provides approach to code for interface rather than implementation.
2.  Factory pattern removes the instantiation of actual implementation classes from client code. Factory pattern makes our code more robust, less coupled and easy to extend.
3.  Factory pattern provides abstraction between implementation and client classes through inheritance.
### Factory pattern vs Service Locator
- A factory creates objects for you, when requested.
- Service locator returns objects that may already exist, that is services that may already exist somewhere for you.
	- Factory: is a place where objects are created.
	- Service: is something that can do something for you as a service.
	- Service locator: is something that can find something that can perform a service.
## Facade Pattern
### Definition
- Provide a unified interface to a set of interfaces in a subsystem.
- Facade defines a higher-level interface that makes the subsystem easier to use
### Implementation
- instead of create multiply set of interface in one process, create a higher-level interface that involves all sub-systems, the main method would just call with that facade interface.
## SOLID Design Principles 
- [Video](https://www.youtube.com/watch?v=HLFbeC78YlU&list=PL6n9fhu94yhXjG1w2blMXUzyDrZ_eyOme)
### Benefit
- Achieve reduction in complex of code
- Increase readability, extensibility and maintenance
- Reduce error and implement reusability
- Better testability
- Reduce tight coupling
### S: Single Responsibility Principle (SRP) 
- [Video](https://www.youtube.com/watch?v=hGf2upfDpdo&list=PL6n9fhu94yhXjG1w2blMXUzyDrZ_eyOme&index=2)
- Every class or module should focus on a single task at a time
- Class become smaller and cleaner
- **For example**
	- Here `LogError()` AND `SendEmail()` are not necessary be part of the high level User interface, so we need to extract them out.
		```
		interface IUser
		{
			void Login();
			void Register();
			void LogError();
			void SendEmail();
		}
		```
	- Improve the code
		```
		interface IUser
		{
			void Login();
			void Register();	
		}
		interface ILogger
		{	void LogError(); }
		interface IEmail
		{	void SendEmail(); }
		```
### O: Open closed Principle (OSP)  
- [Video](https://www.youtube.com/watch?v=wo06oCBuYYI&list=PL6n9fhu94yhXjG1w2blMXUzyDrZ_eyOme&index=4)
- Implement new functionality on new derived class
- Allow client to access the original class with abstract interface
- **For example**: 
	- We have a class `Employee` which has a method to `CalculateBonus`
		```
		public class Employee
		{
			public decimal CalculateBonus(decimal salary)
			{
				return salary;
			}
		}
		```
	- Now we would like to add an *enhancement* so that we need to add a `EmployeeType` and calcuate different bonus based on `EmployeeType`. 
	- We could do this by adding a new property in `Employee` class, modify the `constructor` and `CalculateBonus` method, but this is **too much code change**.
	- What we need to do is to **Abstract** the `Employee` class, make CalculateBonus as **abstract method** and **implement different case in Derived class by override the method**.
		```
		public abstract class Employee
		{
			public abstract decimal CalculateBonus(decimal salary);
		}
		public class TemporaryEmployee : Employee
		{
			public TemporaryEmployee(string Name) : base(name)
			public override decimal CalculateBonus(decimal salary)
			{
				//implementation
			}
		}
		public class PermanentEmployee : Employee
		{
			public PermanentEmployee (string Name) : base(name)
			public override decimal CalculateBonus(decimal salary)
			{
				//implementation
			}
		}
		```
### L: Liskov substitution Principle (LSP) 
- [Video](https://www.youtube.com/watch?v=gnKx1RW_2Rk&list=PL6n9fhu94yhXjG1w2blMXUzyDrZ_eyOme&index=5)
- No new exception can be thrown by the sub-type
- Clients should not know which specific sub-type they are calling
- New derived classes just extend without replacing the functionality of old classes
- **For example**:
	- From last example, now if we would like to add a new type of `ContractEmployee` class but doesn't want this `ContractEmployee` class implement method of `CalculateBonus` method. 
		```
		public class ContractEmployee: Employee
		{
			public ContractEmployee(string Name) : base(name)
			public override decimal CalculateBonus(decimal salary)
			{
				throw NotImplementException();		
			}
		}
		```
	- So if we do by following, when create a new object from `ContractEmployee` and call `CalculateBonus`, an exception would be thrown.
	- What we should do is create two other interfaces to differentiate what method needs to be implemented by `PermanentEmployee` or `ContractEmployee`.
		```
		interface IEmployeeBonus
		{
			decimal CalculateBonus(decimal salary);
		}
		interface IEmployee
		{
			string Name { get; set;}
			decimal GetMinimumSalary();
		}
		```
	- Our base class `Employee` needs to inherit both interface, but `PermanentEmployee` or `ContractEmployee` would inherit from different interface or abstract class based on its needs.
		```
		public abstract class Employee : IEmployee, IEmployeeBonus {...}
		// Because Pernament Employee needs implement all methods
		public class PernamentEmployee : Employee {...}
		// Because Contract Employee does not need implement CalculateBonus
		public class ContractEmployee : IEmployee
		```
	- When we need to create list of `Employee` object, we can create list of `IEmployee` instead of `Employee`
		```
		List<IEmployee> employees = new List<IEmployee>();
		employees.Add(new PermanentEmployee());
		employees.Add(new ContractEmployee());
		```
### I: Interface Segregation Principle (ISP)
- One large job class is segregated to multiple interfaces depending on the requirement.
- If we have a interface containing methods, however, at some point we need to create a new class which should inherit from that interface but doesn't want to implement some of the methods.
- We need to split the large interface into sub-interfaces.
### D: Dependency Inversion Principle (DIP)
- High-level modules should not depend on low-level modules. Both should depend on abstractions.
- Refer to DI
