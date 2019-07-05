- [const vs readonly vs private set](#const-vs-readonly-vs-private-set)
- [== VS Equals](#---vs-equals)
  * [Difference](#difference)
  * [When to use “==” operator and when to use “.Equals()” method?](#when-to-use------operator-and-when-to-use--equals----method-)
- [value type vs reference type](#value-type-vs-reference-type)
- [String VS StringBuilder](#string-vs-stringbuilder)
  * [Why use Enum](#why-use-enum)
- [What is delegates in C# and uses of delegates](#what-is-delegates-in-c--and-uses-of-delegates)
  * [What is multicast delegate](#what-is-multicast-delegate)
- [What is Func<T, TResult> in C#](#what-is-func-t--tresult--in-c-)
- [Difference between Expression<Func< T> and Func< T>](#difference-between-expression-func--t--and-func--t-)
- [Predicate vs Action vs Func](#predicate-vs-action-vs-func)
- [Async and Await](#async-and-await)
  * [how it works](#how-it-works)
  * [Benefit](#benefit)
  * [AsyncCallback](#asynccallback)
  * [How to cancel an async operation](#how-to-cancel-an-async-operation)
- [Yield keyword](#yield-keyword)
  * [Definition](#definition)
  * [Implement the "Where" method](#implement-the--where--method)
- ['this' keyword](#-this--keyword)
- ['base' keyword](#-base--keyword)
- [**new() constraint keyword**](#--new---constraint-keyword--)
- [Generic class & method](#generic-class---method)
- [What is difference between the “throw” and “throw ex” in .NET?](#what-is-difference-between-the--throw--and--throw-ex--in-net-)
- [Can we have only “try” block without “catch” block in C#?](#can-we-have-only--try--block-without--catch--block-in-c--)
- [in/ref/out keyword](#in-ref-out-keyword)
- [Using statement](#using-statement)
- [Garbage collection](#garbage-collection)
  * [Which method is used to enforce garbage collection in .NET](#which-method-is-used-to-enforce-garbage-collection-in-net)
  * [What is the difference between dispose() and finalize ()](#what-is-the-difference-between-dispose---and-finalize---)
- [Difference between object.GetType() and typeof(object)](#difference-between-objectgettype---and-typeof-object-)
- [for vs foreach](#for-vs-foreach)
  * [performance](#performance)
  * [When to use for or foreach](#when-to-use-for-or-foreach)
- [var vs dynamic](#var-vs-dynamic)
- [Array vs ArrayList vs List vs LinkedList](#array-vs-arraylist-vs-list-vs-linkedlist)
- [Dictionary vs Hashtable](#dictionary-vs-hashtable)
- [boxing vs unboxing](#boxing-vs-unboxing)
- [IS, AS and CASTING](#is--as-and-casting)
- [Method Extension](#method-extension)
  * [Definition](#definition-1)
  * [Example](#example)
  * [Why method extension/main method has to be static class](#why-method-extension-main-method-has-to-be-static-class)
  * [Can you add extension methods to an existing static class?](#can-you-add-extension-methods-to-an-existing-static-class-)
- [params keyword](#params-keyword)
- [@ keyword](#--keyword)
- [Convert.ToString VS ToString](#converttostring-vs-tostring)
- [What is the difference between string and String in C# ?](#what-is-the-difference-between-string-and-string-in-c---)
- [Write a Regular expression to validate email address?](#write-a-regular-expression-to-validate-email-address-)

## const vs readonly vs private set
- **const** - specifies that the value of the field or the local variable cannot be modified.
- **readonly** 
	- declare as a `readonly` field. It mean that the object can't even modify its own field's value, once the object has been instantiated, and others can never modify it. A `readonly` field can only be assigned to at declaration or in the constructor. 
	- The value assigned to a `readonly` field cannot be changed and it is guaranteed that **every thread will see the correctly, initialized value after the constructor returns**. Therefore, a `readonly` field is **inherently thread-safe**
	- Use `readonly` when you want to set the property only once. In the constructor or variable initialization.
- **private set** 
	- A `private set` is declared as a method that gets compiled, it mean that object can modify the value of its field after it's been instantiated, but others can never modify it.
	- Use `private set` when you want setter can't be accessed from outside
		```
		public  class  Configuration
		{
			public  Color  BackgroundColor { get; private  set; }
			public  Configuration()
			{
				BackgroundColor = Color.Black;
			}
			public  void  ResetConfiguration()
			{
				BackgroundColor = Color.Black; // Object itself can modify it
			}
		}
		public  class  ConfigurationReadOnly
		{
			public  readonly  Color  BackgroundColor;
			public  ConfigurationReadOnly()
			{
				BackgroundColor = Color.Black;
			}
			public  void  ResetConfiguration()
			{
				BackgroundColor = Color.Black; // compile error: due to readonly keyword
			}
		}
		```
## == VS Equals
### Difference
- **==** performs an identity comparison, i.e. it will only return true if both references point to the same object. 
- **Equals**() method is expected to perform a value comparison, i.e. it will return true if the references point to objects that are equivalent.
- there is an exception of this rule, i.e. when you use “==” operator with **string** class it compares **value** rather than identity.
### When to use “==” operator and when to use “.Equals()” method?
- For value comparison, with Value Type use “==” operator and use “Equals()” method while performing value comparison with Reference Type.
## value type vs reference type
- **value types** hold their value in memory where they are declared, Value types are destroyed immediately after the scope is lost, memory allocated on the stack
	- e.g: int/float/bool/enum/structs
- **reference types** hold a reference to an object in memory. reference types only the reference variable is destroyed after the scope is lost. The object is later destroyed by garbage collector, memory allocated on the heap
	- e.g: string/StringBuilder/array/class/interface/delegate
## String VS StringBuilder
- **String**
	- It’s an **immutable**(cannot change) object that hold string value.
	- Performance wise string is **slow** because its’ create a new instance to override or change the previous value.
- **StringBuilder**
	- StringBuilder is a **mutable**(can change) object.
	- Performance wise StringBuilder is very **fast** because it will use same instance of StringBuilder object to perform any operation like insert value in existing string.
### Why use Enum
-   Code becomes more readable.    
-   Strong-typed, easy to change constants without affecting throughout the project. Easy maintenance.
## What is delegates in C# and uses of delegates
- C# delegates are same as pointers to functions, consider a delegate Object is a reference type variable that use to holds the reference to a static method..
- Delegates are especially used for implementing events and the call-back methods, async processing and multi casting.
### What is multicast delegate
- certain delegate to hold and invoke multiple methods.
	- The **return** type of the delegate **must be void**.
	- Multicast delegate instance is created by combining two delegates
	- Delegates are invoked in the order they are added.
		```
		public delegate void MyDelegate(int a, int b);
		public static void Main()
		{
			// Multicast delegate
		    MyDelegate myDel = new MyDelegate(AddNumbers);
		    myDel += new MyDelegate(MultiplyNumbers);
		    myDel(10, 20);
		}
		public void AddNumbers(int x, int y)
		{
		    int sum = x + y;
		}
		public void MultiplyNumbers(int x, int y)
		{
		    int mul = x * y;
		}
		```
## What is Func<T, TResult> in C#
- In simple terms, `Func<T, TResult>` is just a **generic delegate**. 
	- For example, `Func<Employee, string>` is a delegate that represents a function expecting Employee object as an input parameter and returns a string.
## Difference between Expression<Func< T> and Func< T>
- **Expression<Func< T>>**
	- `Expression<Func<T>>` denotes a _tree data structure_ for a lambda expression. This tree structure **describes what a lambda expression does** rather than doing the actual thing. It basically holds data about the composition of expressions, variables, method calls, and you can use this description to convert it to an actual method (with `Expression.Compile`) or do other stuff (like the `LINQ to SQL` example) with it.
	```
		Expression<Func<int>> myExpression = () => 10;
		// This will be converted to a data structure that describes an expression that gets no parameters and returns the value 10.
	```
- **Func< T>**
	- `Func<T>` is a `delegate` which is pretty much a pointer to a method
	```
		Func<int> myFunc = () => 10; // similar to: int myAnonMethod() { return 10; }
		// This will effectively compile to an IL method that gets nothing and returns 10.
	```
## Predicate vs Action vs Func
-   **Predicate**: `delegate Predicate<in T>` it's an extension to `Func` similar to  `Func<T, bool>`; Represent the method that defines a set of criteria and determine whether the specified object meets these criteria. (e.g, List.Find/FindAll, etc)
-   **Action**: `delegate Action<in T>` Only input no expecting of any output.
-   **Func**: `delegate Func<in T, out TResult>`Used extensively in LINQ, usually to transform the argument, e.g. by projecting a complex structure to one property.
## Async and Await
### how it works
- Async method uses await keyword to label suspension point. **The await operator signals the compiler that the async method can’t continue past that point until the awaited asynchronous process is complete.**
- An async method can have one or more await operator but if there is no await operator, then the method gets executed as synchronous method.
### Benefit
- suppose there is an action method that takes a long time to execute, implementing the action method asynchronously will not reduce the time much as compared to the synchronous version of the same method. The difference is that the asynchronous approach will just **not keep the action method/main thread blocked** for the time period it is **waiting** for the call to the **external resource** like a `network call` or a `database call` or `file stream` to complete.
- So if you have an application that has a lot of concurrent calls that are **network** or **CPU bound** using the asynchronous approach will definitely result in performance improvement.
### AsyncCallback
- The `AsyncCallback` delegate represents a callback method that is called when the asynchronous operation completes. With this technique there is no need to poll or wait for the async thread to complete.
	```
	// Create the delegate that will process the results of the asynchronous request.
	AsyncCallback callBack = new AsyncCallback(ProcessDnsInformation);
	// The following method is called when each asynchronous operation completes.  
	static void ProcessDnsInformation(IAsyncResult result) { ... }
	```
### How to cancel an async operation
- Use `CancellationToken` from `CancellationTokenSource.Token`
- A task that has a CancellationToken object needs to periodically inspect it to see what the token’s state is.
-   A CancellationToken is nonreversible and can only be used once
	```
	CancellationTokenSource cts = new CancellationTokenSource();
	CancellationToken token = cts.Token;
	cts.Cancel(); //cancel the operation.
	```
## Yield keyword
### Definition
- Yield keyword helps us to do deferred execution 
	- It helps to provide custom iteration without creating temp collections.  
	- It helps to do state-full iteration
	- yield return a single object as part of the `IEnumerable<Type>` which is the return of  the function, using yield could get rid of a temp list storage.
	- A `yield return` statement can't be located in a try-catch block. A `yield return` statement can be located in the try block of a try-finally statement.
	e.g:
```
	// Not using yield
	static IEnumerable<int> FilterWithoutYield()
	{
        List<int> temp = new List<int>();
        foreach (int i in MyList)
        {
            if (i > 3)
            {
                temp.Add(i);
            }
         }
         return temp;
	}
	// Using yield (don't need to create temp storage)
	static IEnumerable<int> FilterWithYield()
	{
        foreach (int i in MyList)
        {
            if (i > 3) yield return i;
        }
	}
	// Stateful iteration
	// we would like to iterate through the collection and as we iterate would like to maintain running total state and return the value to the caller ( i.e. console application).
	// The “runningtotal” variable will have the old value every time the caller re-enters the function.
	static IEnumerable<int> RunningTotal()
    {
        int runningtotal=0;
        foreach(int i in MyList)
        {
             runningtotal += i;
             yield return (runningtotal);   
        }
     }
```
### Implement the "Where" method
```
public static IEnumerable<T> Where<T>(this IEnumerable<T> items, Predicate< T> prodicate)
{
  foreach(var item in items)
  {
      if (predicate(item))
      {
           // for lazy/defer execution plus avoid temp collection defined
           yield return item;
      }
  }
}
```
## 'this' keyword
- The `this` keyword refers to the current instance of the class and is also used as a modifier of the first parameter of an extension method.
## 'base' keyword
- The  `base`  keyword is used to access members of the base class from within a derived class:
	-  Call a method on the base class that has been overridden by another method.
    -  Specify which base-class constructor should be called when creating instances of the derived class.
    - **can't use** within a **static** method.
 ## **new() constraint keyword**
 - The `new` constraint specifies that any type argument in a generic class declaration must have a public parameterless constructor. To use the new constraint, the type cannot be abstract.
- For example:
	```
	// Apply the `new` constraint to a type parameter when your generic class creates new instances of the type
	class  ItemFactory<T> where  T : new() 
	{ 
		public T GetNewItem() 
		{ 
			return  new T(); 
		} 
	}
	```
 - When you use the `new()` constraint with other constraints, it must be specified last
- For example:
	```
	public  class  ItemFactory2<T> where  T : IComparable, new() { }
	```
## Generic class & method

## What is difference between the “throw” and “throw ex” in .NET?
- throw ex throws the same exception, but *resets the stack trace* to that method.
- throw re-throws the exception that was caught, and *preserves the stack trace*. throw is generally the **better** choice, since you can see where the exception originated.
```
private static void Method2()
{
    try
    {
        Method1();
    }
    catch (Exception ex)
    {
        //throw ex resets the stack trace Coming from Method 1 and propogates it to the caller(Main)
        throw ex;
    }
}
private static void Method1()
{
    try
    {
        throw new Exception("Inside Method1");
    }
    catch (Exception)
    {
        throw;
    }
}
```
## Can we have only “try” block without “catch” block in C#?
- Yes we can have only try block without catch block but we have to have finally block.
## in/ref/out keyword
- **in**
	- Cannot be modified by the called method
	- Pass by reference
- **ref**
	- requires that the variable be initialized before it is passed and method may or may not modify the value.
	- Pass by reference(in and out)
- **out**
	- doesn't require the value to be set before it is passed but method must set it before returning. 
	- Pass by reference(out only)
## Using statement
- "using" statement is to **ensure** that the object is **disposed** as soon as it goes out of scope, and it doesn't require explicit code to ensure that this happens.
- e.g. when the SomeType class implements **IDisposable**. File Stream, Email server.
## Garbage collection
- Garbage collection is used to prevent memory leaks during execution of programs.
### Which method is used to enforce garbage collection in .NET
```
System.GC.Collect ( ) method.
```
### What is the difference between dispose() and finalize ()
- The `Finalize` method is called **automatically by the run-time** while the `Dispose` method is **called by the programmer**.
- There is **no performance** costs associated with `Dispose` method but it has **performance costs** with `Finalize` method since it doesn’t clean the memory immediately and called by GC automatically.
## Difference between object.GetType() and typeof(object)
- `GetType` gets resolved at **runtime**
	- when you want to obtain the type from **an instance of your class**, use GetType
- `typeof` is resolved at **compile time**.
	- If you don’t have an instance, but you know the type name, you would use typeof.
## for vs foreach 
### performance
- `foreach` is **slower** because 
	- it's a data structure called `enumerator`, an enumerator is a data structure with a `Current` property, a `MoveNext` method, and a `Reset` method.
	- Enumerators are great because they can handle any iterative data structure. In fact, they are so powerful that all of `LINQ` is built on top of enumerators.
	- But the **disadvantage** of enumerators is that they **require calls to Current and MoveNext for every element** in the sequence. All those method calls add up, especially in mission-critical code.
- `for` is **faster** because
	- it only has to call `get_Item` for every element in the list. That’s one method call less than the foreach-loop, and the difference really shows.
### When to use for or foreach
- When you’re using **LINQ**, use **foreach**
- When you’re working with **very large** computed sequences of values, use **foreach**
- When **performance isn’t an issue**, use **foreach**
- But if you want **top performance**, use a **for-loop** instead
## var vs dynamic
- var
	- variables are required to be initialized at the time of declaration
	- statically strong typed variable, data type decided on compile-time, so does not allow type to be changed after assign to
	- Intellisense helped
- dynamic
	- No need to initialize at the time of declaration
	- dynamically typed variable, data type decided on run-time, so allow type to be changed.
	- Intellisense not help
	- 
## Array vs ArrayList vs List vs LinkedList
- **Array**: strong type with **fixed length**, particular type of int, String or Object, no type casting required. Can store primitives.
- **ArrayList**: **dynamic** in increase or decrease the size. Can store all datatype value, cannot store premitives.
- **List** is basically backed by an array which is usually bigger than the current number of items. The elements are put in an array, and **a new array is created when the old one runs out of space.** This is fast for access by index, but slow at removing or inserting elements within the list or at the start.
- **LinkedList**: is a doubly-linked list - each node knows its previous entry and its next one. This is fast for inserting after/before a particular node (or the head/tail), but slow at access by index. LinkedList<T> will usually take more memory than List<T> because it needs space for all those next/previous references - and the data will probably have less locality of reference, as each node is a separate object.
## Dictionary vs Hashtable
- **Dictionary** 
	- is a **generic** type, means you get **type safety** with Dictionary, because you can’t insert any random object into it, and you *don’t have to cast the values* you take out.
	- **no thread safety**
	- It **returns error** if we try to find a key which does not exist.  
	- It is **faster** than a Hashtable because there is no boxing and unboxing.
- **Hashtable**
	- **not generic** type
	- All the members in a Hashtable are **thread safe**.
	- It **returns null** if we try to find a key which does not exist.  
	- It is **slower** than dictionary because it requires **boxing** and **unboxing**.
		```
		// Get value of Area with indexer by unboxing
	    int value = (int)hashtable["Key"];
	    ```
## boxing vs unboxing
- boxing
	- the process of converting a `value type` to the type `object`
		```
		int i = 123; // The following line boxes i.  
		object o = i;
		```
- unboxing
	- extracts the value type from the object.
		```
		o = 123; i = (int)o; // unboxing
		```
## IS, AS and CASTING
- IS: checks if an object can be cast to a specific type.
- AS: attempts to cast an object to a specific type, and returns null if it fails.
- Cast: attempts to cast an object to a specific type, and throws an exception if it fails.
## Method Extension
### Definition
- Extension methods enables you to add new capabilities to an existing type without making any modifications to the existing type.
	- Extension methods need to be declared in a non-generic, non-nested, static class.
	- Be able to overload but not override
	- Extension method cannot be declared on a class or struct.
	- It can also be declared on an interface (such as IEnumerable). Normally, an interface wouldn’t have any implementation. With extension methods, however, you can add methods that will be available on every concrete implementation of the interface
### Example
```
public static class Extensions
{
	public static string Underscore(this  string  value)
		=> string.Concat(value.Select((x,i)=>i>0&&char.IsUpper(x)?  "_"+x.ToString():x.ToString()));
}
```
### Why method extension/main method has to be static class
- A static method can be called without instantiating an object.
### Can you add extension methods to an existing static class?
- No. Extension methods require an instance of an object.
## params keyword
- By using the `params` keyword, you can specify a method parameter that takes a variable number of arguments.
- You can send a comma-separated list of arguments of the type specified in the parameter declaration or an array of arguments of the specified type. You also can send no arguments. If you send no arguments, the length of the  `params`  list is zero.
- No additional parameters are permitted after the  `params`  keyword in a method declaration, and only one  `params`  keyword is permitted in a method declaration.
```
public static void UseParams(params int[] list) {}
UseParams();
UseParams(1, 2, 3, 4);
```
## @ keyword
- Help to define any parameter name which are referring closed to system.object name
	- e.g: MethodName(string @string, int @params)
## Convert.ToString VS ToString
- Convert.ToString() handles null and return empty string, while ToString() doesn’t, and throws a NULL Reference exception.
## What is the difference between string and String in C# ?
- String stands for System.String and it is a .NET Framework type. string is an alias in the C# language for System.String. 
- Both of them are compiled to System.String in IL (Intermediate Language), so there is no difference.
## Write a Regular expression to validate email address?
```
^[a-zA – Z0 – 9. _%+-]+@[a-zA – Z0-9 . _%+-]+\.[a-zA-Z]{2,4}$  
```
In the above example:  
– the sequence `^[a-zA – Z0 – 9. _%+-]` matches letters,digits,underscores,%,+ and -.  
– The `+ (plus) sign` after the 1st sequence means the string must include one or more of those characters  
– Next the pattern matches `@`  
– Then the pattern matches another letter one or more times followed by a . and then between two to four letters
<!--stackedit_data:
eyJoaXN0b3J5IjpbODk2MTU0NTQ2LDIwMzA5Mzg3NjZdfQ==
-->