- [Benefit of OO](#benefit-of-oo)
- [Access Modifier](#access-modifier)
  * [private vs protected](#private-vs-protected)
  * [What has access to a protected variable](#what-has-access-to-a-protected-variable)
  * [When to use protected:](#when-to-use-protected-)
  * [internal modifier](#internal-modifier)
  * [protected internal modifier](#protected-internal-modifier)
- [Interface](#interface)
  * [Definition](#definition)
- [Abstract Class](#abstract-class)
  * [Definition](#definition-1)
  * [Abstract Property](#abstract-property)
  * [Abstract Method](#abstract-method)
  * [Difference between virtual and abstract methods](#difference-between-virtual-and-abstract-methods)
  * [What’s the use of a constructor in an abstract class](#what-s-the-use-of-a-constructor-in-an-abstract-class)
  * [Can you call an abstract method from an abstract class constructor?](#can-you-call-an-abstract-method-from-an-abstract-class-constructor-)
- [Abstract Class VS Interface](#abstract-class-vs-interface)
  * [Basic knowledge](#basic-knowledge)
  * [Why can't interface method have access modifiers?](#why-can-t-interface-method-have-access-modifiers-)
  * [When to use Interface over Abstract class](#when-to-use-interface-over-abstract-class)
  * [When to use Abstract class over Interface](#when-to-use-abstract-class-over-interface)
- [Static Class and Static member](#static-class-and-static-member)
- [Nested Private Class](#nested-private-class)
- [private constructor vs static constructor](#private-constructor-vs-static-constructor)
- [Overriding VS Overhiding VS Overloading](#overriding-vs-overhiding-vs-overloading)
- [Difference between Stack and Heap](#difference-between-stack-and-heap)

## Benefit of OO
- **Encapsulation** – Reduce complexity + increase re-usability
	- private string name public string GetName(){ return name;}
- **Abstraction** – Reduce complexity + isolate impact of changes
	- access modifier: public/private/protected, etc
- **Inheritance** – Eliminate redundant code
	- public class DerivedClass : BaseClass {  }
- **Polymorphism** – Refactor ugly switch/case statement(overloading)
	- method override/overloading
## Access Modifier
### private vs protected
- private
	- Cannot be accessed by derived classes
	- Cannot be accessed by object of the class
- protected
	-   Can be accessed by derived classes
	-   Cannot be accessed by object of the class
### What has access to a protected variable
1. using derived class object
2. using this keyword
3. using base keyword
### When to use protected:
- Use it when you need to do some internal stuff that is not exposed in public API but still needs to be overridden by sub classes.
### internal modifier
- In same assembly/dll (public)
	- Can be accessed by objects of the class or by derived classes
- In other assembly/dll (internal)
	- Cannot be accessed by object of the class or by derived classes
### protected internal modifier
- Can be accessed by any code in the assembly/dll in which it is declared
- Can be accessed from within a derived class in another assembly/dll.
## Interface
### Definition
- Just like classes interfaces also contains properties, methods, delegates or events, but only declarations and no implementations.
- If a class or a struct inherits from one or more interface, it must provide implementation for all interface members.
- Interface can inherit from other interfaces.
- A class inherits from 2 or more interfaces and both the interfaces have the same method name could implement explicitly
## Abstract Class
### Definition
 - An abstract class can only be used as base class. We cannot create object of an abstract class. 
 - An abstract class cannot be sealed.(*Once a class is defined as a **sealed class,** the class cannot be inherited.*) 
 - An abstract class can have implementation for some of its members except abstract method. 
 - A non-abstract class derived from an abstract class must provide implementations for all inherited abstract property/method. (using **override**!)
 ```
	int myProp;
	public override int MyProperty { Get { return myProp;} Set { myProp = value } }
	public override int GetMonthlySalary()
```
### Abstract Property
Use an abstract property when you have no default implementation and when derived classes must implement it. You are basically saying, "here is a data point that all subclasses must have, but I don't know how to implement it"
### Abstract Method
Abstract methods, similar to methods within an interface, are declared without any implementation. They are declared with the purpose of having the child class provide implementation.(using **override**!)
```
public  abstract  class  Animal 
{ 
	//all classes that implement Animal must have a sound method
	public abstract string sound();  
} 
public  class  Cat : Animal 
{ 
	//implemented sound method from the abstract class & method 
	public override string sound() { return  "Meow!"; } 
}
```
### Difference between virtual and abstract methods
- **Virtual methods** must always have a default implementation and also provide the derived classes with the option of overriding it. 
- **Abstract methods** should **not have an implementation** and force the derived classes to override the method.
```
public abstract class Animal 
{
    public abstract void AbstractMethod(int i);

    public virtual void VirtualMethod(int i)
    {
        // Can have code
        // Default implementation which can be overridden by subclasses.
    }
}
public class Cat : Animal 
{
    public override void AbstractMethod(int i)
    {
        // You HAVE to override this method
    }
    public override void VirtualMethod(int i)
    {
        // You are allowed to override this method.
    }
}
```
### What’s the use of a constructor in an abstract class
When an instance of derived class is created, the parent abstract class constructor is automatically called. Abstract classes can’t be directly instantiated, usually use protected access modifier with abstract class constructor, this prevents duplicate code.
### Can you call an abstract method from an abstract class constructor?
Yes, if you want the abstract method to be invoked automatically whenever an instance of the class that is derived from the abstract class is created, then we would call it in the constructor of abstract class.
## Abstract Class VS Interface
### Basic knowledge
- **Abstract classes** can have **implementations** for some of its methods **except abstract methods**, but the interface can’t have implementation for any of its members.
- **Interfaces** cannot have **fields** where as an abstract class can have fields. (int, string)
- An **interface** can **inherit** from another interface only and cannot inherit from an abstract class, where as an abstract class can inherit from another abstract class or another interface.
- **Abstract class** members can have **access modifiers** whereas interface members cannot have access modifiers. (no public, private for interface)
### Why can't interface method have access modifiers?
- Interface **by default** is to **internal** access and can be changed to public. 
- Interface **members** are **always public**, we have **virtual methods** that do not have method definition. All the methods are there to **be overridden in the derived class**. That's why they **all are public**.
### When to use Interface over Abstract class
- We can do multiply **inheritance** of interfaces in C# for one class
- **Force** developer to **implement** interface methods when inherits
- Most of the **Design Patterns**(Factory/Repository) and **Principles** are based on interfaces rather than class inheritance. Interfaces allow us to develop **loosely coupled system**,
- Interfaces are very useful for **Dependency Injection** and make **unit testing and mocking** easier.
### When to use Abstract class over Interface
- Abstract classes provide you the flexibility to have certain concrete methods and some other methods that the derived classes should implement. 
- if you use interfaces, you would need to implement all the methods in the class that extends the interface. An abstract class is a good choice if you have plans for future expansion.
- Use an abstract class to define a **common base class** for a family of types or to provide **default behavior**
## Static Class and Static member
- **Static Class**
	- Static classes can only have static methods.
	- Static classes can be thought of as being both abstract and sealed, meaning that cannot be instantiated or derived from.
- **Difference between static class and abstract class**
	- An `abstract class` is usually `intended to model something in a type hierarchy`. 
		- For example, a truck is a kind of vehicle, and an airplane is a kind of vehicle, so you might have a base class Vehicle and derived classes Truck and Airplane. But "Vehicle" is abstract; there are no vehicles which are just vehicles without being some more specific kind of thing. `You represent that concept with an abstract class`.
	- A `static class` by contrast is not intended to model anything at all. It's just a convenient way of storing a bunch of code, often used as containers of extension methods or utility methods.
- **Static Member (method/field/property/event)**
	- Static member must be **called on the class itself**, not on the instances of the class.
	- Static members are **initialized before** the static member is accessed for the first time and before the static constructor
	- Static **methods** can be overloaded but **not overridden**, because they belong to the class, and not to any instance of the class.
- Can you use ‘**this**’ in static method?
	- No, the keyword 'this' returns a reference to the current instance of the class containing it. Static methods (or any static member) do not belong to an instance.
- **Is static method more efficient?**
	- Usually yes, there is no need to pass the "this" reference so there is no additional stack space required.
- **Private Static Method**
	- After you mark the methods as static, the compiler will emit non-virtual call sites to these members. Emitting non-virtual call sites will prevent a check at runtime for each call that ensures that the current object pointer is non-null. **This can result in a measurable performance gain for performance-sensitive code.**
## Nested Private Class
- The main feature of nested classes is that they can **access private members of the outer class** while having the full power of a class itself. 
	- they can be private which allows for some pretty powerfull **encapsulation** in certain circumstances.
		- e.g: Here we lock the setter completely down to the factory since the class is private no consumer can downcast it and access the setter, and we can control completely what is allowed.
			```
			public interface IFoo 
			{
			    int Foo{get;}      
			}
			public class Factory
			{
			    private class MyFoo : IFoo
			    {
			        public int Foo{get;set;}
			    }
			    public IFoo CreateFoo(int value) => new MyFoo{Foo = value};
			}
			```
	- it is useful for implementing third-party interfaces in a controlled environment where we can still access private members.
		- **e.g**: If we for example were to provide an instance of some interface to some other object but we don't want our main class to implement it we could let an inner class implement it.
			```
			public class Outer
			{
			    private int _example;
			    private class Inner : ISomeInterface
			    {
			        Outer _outer;
			        public Inner(Outer outer){_outer = outer;}
			        public int DoStuff() => _outer._example;
			    }
			    public void DoStuff(){_someDependency.DoBar(new Inner(this)); }
			}
			```
## private constructor vs static constructor
- **private constructor**
	- A private constructor is generally used in classes that contain static members only. 
	- If a class has one or more private constructors and no public constructors, other classes (except nested classes) cannot create instances of this class.
- **Static constructor**
	- A static constructor is used to initialize any **static data**, or to perform a particular action that needs to be performed **once only**. 
	- It is called automatically **before the first instance** is created or any static members are referenced
	- A static constructor does **not** take **access modifiers** or have any **parameters**
## Overriding VS Overhiding VS Overloading
- **Overriding** 
	- usually come with **virtual** in parent class and **override** in derived class*, override is a object-type specific.
	- **Cannot** use the `virtual` modifier with the `static`, `abstract`, `private`, or `override` modifiers
	- allows a derived  class to provide a specific implementation of a method that is already provided by one of its parent classes. 
	- When base class reference variable pointing to the object of the derived class, then it will call the overridden method in the derived class.
- **Overhiding** 
	- “new” keyword, is a reference type specific
	- allows you to redefine the method of the base class in the derived class by using the new keyword. 
	- When base class reference variable pointing to the object of the derived class, then it will call the hidden method in the base class.
- **Overloading** means we will declare methods with same name but different signatures
## Difference between Stack and Heap
- Stack
	- Static memory, stored directly to the memory
	- variables cannot be resized
	- fast access, especially in recursion calls
	- mostly contains local variables which gets wiped off once they lost scope
	- only used by one thread of execution
	- will throws StackOverflowException in .NET runtime
- Heap
	- Dynamic memory, variables memory allocated at run time
	- variables can be resized.
	- relatively slower access
	- objects created on heap are visible to all threads
	- Garbage collector only collo
![enter image description here](https://www.csharpstar.com/wp-content/uploads/2016/05/Stack_Heap.jpg)

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwMDg2MDcxNDldfQ==
-->