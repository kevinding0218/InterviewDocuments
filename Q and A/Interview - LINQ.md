- [What is Expression Trees and how they used in LINQ?](#what-is-expression-trees-and-how-they-used-in-linq-)
- [Eager Loading vs Lazy Loading](#eager-loading-vs-lazy-loading)
- [IEnumerable vs IQueryable vs ICollection vs IList](#ienumerable-vs-iqueryable-vs-icollection-vs-ilist)
- [Return type of Linq](#return-type-of-linq)
- [Deferred Execution vs Immediate Execution](#deferred-execution-vs-immediate-execution)
- [select vs where](#select-vs-where)
- [select vs selectmany](#select-vs-selectmany)
- [LINQ vs Store Proc](#linq-vs-store-proc)

## What is Expression Trees and how they used in LINQ?
- An `Expression Tree` is a **data structure that contains Expressions**, which is basically code. So it is a tree structure that represents a calculation you may make in code.
- In LINQ, expression trees are used to represent structured queries that target sources of data that implement `IQueryable<T>`.
## Eager Loading vs Lazy Loading
- **Eager Loading**: related objects (child objects) are loaded automatically with its parent object. it loads data with **one "more heavy" call** (with joins/subqueries).
	- *When to use eager loading*
		- you are sure that certain **related data will be used** quite often. 
		- Generally When **relations are not too much** and eager loading will be good practice to reduce further queries on server.(**Using Include**)
- **Lazy Loading**: related objects (child objects) are **not** loaded automatically with its parent object **until** they are requested, it will **produce several SQL calls**. By default LINQ supports lazy loading.
	- *When to use lazy loading*
		- Use lazy loading when you know you will **rarely use the related data**.
## IEnumerable vs IQueryable vs ICollection vs IList
- **IEnumerable**
	- `IEnumerable` exists in the **System.Collections** namespace, with any LINQ operators (extended methods) that require predicates or anonymous functions taking **Func<T, bool>**.
	- `IEnumerable` is suitable for querying data from **in-memory collections** like List, Array and so on.
	- While querying data from the database, I`Enumerable` executes "select query" on the server-side, **loads data in-memory on the client-side** and **then filters** the data.
	- **When to use IEnumerable**
		- when you want to pass **an entire list** to the client **in one go**. They can still add linq clauses, but the client does not benefit from deferred execution.
		- **doesn’t support lazy loading** so **not** recommend for **paging**, it’s eager loading
- **IQueryable**
	- `IQueryable` exists in the **System.Linq** Namespace.
	- `IQueryable` is suitable for querying data from **out-memory** (like remote database, service) **collections**.
	- While querying data from a database, `IQueryable` executes a "select query" on server-side **with all filters**.
	- `IQueryable` **implements IEnumerable**
	- `IQueryable` implements the same LINQ standard query operators, but accepts **Expression<Func<T, bool>>** for **predicates** and anonymous functions.
	- **When to use IQueryable**
		- Support **lazy loading** so use in **paging**
		- when you want to **extend deferred querying** capabilities to the client, by allowing the client to add their own linq clauses. This defers execution of the entire query until output is required.
		- e.g:
			```
			IEnumerable<Person> people = GetEnumerablePeople();
			Person person = people.Where(x => x.Age > 18).FirstOrDefault();

			IQueryable<Person> people = GetQueryablePeople();
			Person person = people.Where(x => x.Age > 18).FirstOrDefault();
			```
		- In the first block,  `x => x.Age > 18`  is an anonymous method (`Func<Person, bool>`), which can be executed like any other method.  `Enumerable.Where`  will execute the method once for each person,  `yield`ing values for which the method returned  `true`.
		- In the second block,  `x => x.Age > 18`  is an expression tree (`Expression<Func<Person, bool>>`), which can be thought of as "is the 'Age' property > 18".
- **ICollection**
	- The ICollection Interface is inherited from the IEnumerable interface which means that any class that implements the ICollection Interface can also be enumerated using a foreach loop.
	- In the IEnumerable Interface we don’t know how many elements there are in the collection whereas the ICollection interface gives us this extra property for getting the count of items in the collection
- **IList**
	- IList implements  _ICollection_  and  _IEnumerable_.
	- IList provides method definitions for **adding** and **removing** elements and to **clear** the collection.
	- IList provides methods for **handling the positioning** of the elements within the collection.
	- IList provides an object indexer to allow the user to **access the collection** with square brackets
- **When to use IEnumerable/ICollection/IList**
	- **IEnumerable**
		- The only thing you want is to **iterate over** the elements in a collection.
		- You only need **read-only** access to that collection.
	- **ICollection**
		- You want to **modify** the collection or you care about its **size**.
	- **IList**
		- You want to **modify** the collection and you care about the **ordering** and / or **positioning** of the elements in the collection.
## Return type of Linq
- If you are querying a database(**Linq to SQL**) then the return type is **IQueryable**< T>
- If the database query includes an `OrderBy` clause, the type is **IOrderedQueryable**< T> .It is derive from IQueryable<T>
- If the `data source` is an `IEnumerable`, the result type is **IEnumerable**< T>
## Deferred Execution vs Immediate Execution
- **Deferred Execution**
	- Deferred execution means that the evaluation of an expression is delayed until its _realized_ value is actually required. 
	- Deferred execution can greatly improve performance when you have to manipulate large data collections, especially in programs that contain a series of chained queries or manipulations. 
	- `Deferred execution` is supported directly in the C# language by the **yield** keyword (in the form of the `yield-return` statement) when used within an iterator block. Such an iterator must return  **IEnumerator** .
		```
		var query = from customer in context.Customers
							where customer.City  ==  "Delhi"
							select customer;  // Query does not execute here
		foreach (var Customer in query)  // Query executes here
		{
			Console.WriteLine(Customer.Name);
		}
		```
- **Immediate Execution**
	- In case of immediate execution, a query is executed at the point of its declaration.
	- You can force a query to execute immediately of by calling ToList, ToArray methods.
	- Immediate execution doesn't provide the facility of query re-usability, since it always contains the same data which is fetched at the time of query declaration.
		```
		var query =  (from customer in context.Customers
							where customer.City  ==  "Delhi"
							select customer).Count();  // Query execute here
		```
## select vs where
- `Select` is a projection, use `Select` when you want to keep all results, but change their type (project them).
- `Where` filters the results, returning an enumerable of the original type (no projection). Use `Where` when you want to filter your results, keeping the original type.
## select vs selectmany
- Select operator produces one result value for every source value, return `IEnumerable<IEnumerable<T>>`
- SelectMany produces a single result that contains a concatenated value for every source value. Actually, SelectMany operator flatten `IEnumerable<IEnumerable<T>>` to `IEnumrable<T> i.e. list of list to list.
- For example
	```
	public class PhoneNumber
	{
	    public string Number { get; set; }
	}

	public class Person
	{
	    public IEnumerable<PhoneNumber> PhoneNumbers { get; set; }
	    public string Name { get; set; }
	}
	IEnumerable<Person> people = new List<Person>();
	// Select gets a list of lists of phone numbers
	IEnumerable<IEnumerable<PhoneNumber>> phoneLists = people.Select(p => p.PhoneNumbers);
	// SelectMany flattens it to just a list of phone numbers.
	IEnumerable<PhoneNumber> phoneNumbers = people.SelectMany(p => p.PhoneNumbers);
	// And to include data from the parent in the result: 
	// pass an expression to the second parameter (resultSelector) in the overload:
	var directory = people
	   .SelectMany(p => p.PhoneNumbers,
	               (parent, child) => new { parent.Name, child.Number });
	```
## LINQ vs Store Proc
- Advantage that LINQ over SP
	- **Type safety**
	- **Debugging support**
	- **Portable between difference types of databases**
	- 
- Advantage that SP over LINQ
	- **Network traffic**:  sprocs need only serialize sproc-name and argument data over the wire while LINQ sends the entire query. This can get really bad if the queries are very complex.
	- **Less flexible**: Sprocs can take full advantage of a database's featureset.
	- **Recompiling**: If you need to make changes to the way you do data access, you need to recompile, version, and redeploy your assembly.
	
