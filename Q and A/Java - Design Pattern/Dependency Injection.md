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
<!--stackedit_data:
eyJoaXN0b3J5IjpbOTQzMTMwMDIyXX0=
-->