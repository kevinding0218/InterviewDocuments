### What is  the difference between val and var?  
- _var_ is like a general variable and can be assigned multiple times and is known as the mutable variable in Kotlin.
- _val_ is a constant variable which can be Initialized only single time and is known as the immutable variable in Kotlin.
### What is the difference between val and const val?
- _const_ and _val_ both represents the immutability and read only values and act as **final** keyword in java.
- _val_ keyword must be used to declare for **run time** values
- _const_ keyword must be used to declare **compile time** values.
### When to use lateinit and lazy keywords in kotlin?
- Lateinit is used with mutable, while lazy is used with immutable
	- lateinit: if you use below without initializing, it will throw UinitializedPropertyAccessException
	```
	private lateinit var display : DisplayAdapter
	// if you use above without initializing, 
	it will throw UinitializedPropertyAccessException
	```
	- lazy: It initializes variable only when it is required for the first time. There are certain classes whose object initialization is very heavy and so much time taking that it results in the delay of the whole class creation process.
	```
	private val githubApiService : GithubApiService by lazy {
		RetrofitClient.getGithubApiService()
	}
	```
### How to check if lateinit property is initialized or not?
- You can check if the lateinit variable has been initialized or not before using it with the help of  **isInitialized()** method. It returns  **true**  if the lateinit property has been initialized otherwise  **false**.
```
this::variableName.isInitialized
```
### What is Difference between setValue() and PostValue() in MutableLiveData?
- `setValue()` method must be called from the main thread. But if you need set a value from a background thread, `postValue()` should be used.
### What is difference between companion object and object?
- _Companion Object_ is initialized when class is loaded. 
- _Object_ is initialized lazily by default — when accessed for the first time.
### Difference between safe calls(?.) and Non-null Assertion(!!)?
- Safe Call Operator (?.) is used when you want to make sure that your app shouldn’t crash even if variable reference you are holding is null.
> var variable: String? = null  
> variable?.replace(“x”, “z”)
Please note we have not initialized  _variable_ above, but it will not throw NullPointerException as Safe call operator is used.
- in case of Non-Null Assertion, if you call any method on its reference it will throw KotlinNullPointerException.
> variable!!.replace(“x”, “z”)
### What are data classes in kotlin?
```java
data class Person(val name: String) {
	var age: Int = 0
}
```
- Only the property name (e.g: `name`) will be used inside the **toString(), equals(), hashCode(), and copy()** implementations. 
- While two Person objects can have different ages, they will be **treated as equal** for above example
### Kotlin classes are final by default
- If classes were **open** by default and we would forget to mark class as **final** — (troubles might happen), but when we forget to mark class as **open** and try to extend it — we will be notified (no trouble).
### Difference between == operator and === operator?
- The `==` operator is used to compare the values of variables but `===` operator is used to check whether references of the variable is equal or not.  
- And in the case of primitive types, the `===` operator also checks for the value and not reference.  
Please note both will result in same in case primitive data types.
> val number = Integer(1)
> val anotherNumber = Integer(1)
> number == anotherNumber // true (structural equality)
> number === anotherNumber // false (referential equality)
### Access/Visibility Modifiers in Kotlin
Four types of access modifiers
-   **protected:**  visible inside that **particular class or file** and also in the **subclass** of that particular class where it is declared.
-   **private:**  visible inside that **particular class or file** containing the declaration.
-   **internal:**  visible everywhere in that **particular module.**
-   **public:**  visible to **everyone**.
Note: By default, the visibility modifier in Kotlin is  **public**.
### What are extension functions in Kotlin?
- **Extension Function** provides an option to “add” methods to class without inheriting a class. 
- The created extension functions are used as a regular function inside that class
### What are inline functions ?
- Inline function instruct compiler to insert complete body of the function wherever that function got used in the code. 
### What are scope functions in Kotlin ?
- Scoped functions are functions that execute a block of code within the context of an object. There are five scoped functions in kotlin :  **let**,  **run**,  **with**,  **also**  and  **apply.**
The scope functions differ by the result they return:
-   `apply`  and  `also`  return the context object. So they can be used in chaining function calls on the same object after them. They also can be used in return statements of functions
-   `let`,  `run`, and  `with`  return the lambda result.
### What are sealed classes in kotlin?
- Sealed classes are similar to `[enum](https://kotlinlang.org/docs/enum-classes.html)` classes: the set of values for an enum type is also restricted, but each enum constant exists only as a _single instance_, whereas a subclass of a sealed class can have _multiple_ instances, each with its own state.
```java
sealed interface Error
sealed class IOError(): Error
class FileReadError(val f: File): IOError()
class DatabaseError(val source: DataSource): IOError()
object RuntimeError : Error
``` 
### **What is significance of annotations : @JvmStatic, @JvmOverloads, and @JvmFiled in Kotlin?**  
- **@JvmStatic**: This annotation is used to tell the compiler that the method is a static method and can be used in Java code.  
- **@JvmOverloads**: To use the default values passed as an argument in Kotlin code from the Java code.  
- **@JvmField**: To access the fields of a Kotlin class from Java code without using any getters and setters.
### What are infix functions?
- An infix function is used to call the function without using any bracket or parenthesis. You need to use the infix keyword to use the infix function.  
```
infix fun Int.add(b : Int) : Int = this + b
val y = 10 add 20 // infix function call
```
### What are advantages to  _when_  over  _switch_  in kotlin?
- It is more concise and powerful than a traditional switch. when can be used either as an expression or as a statement.
```java
when(number) {
	1 -> println("A")
	2 -> println("B")
	3 -> println("C")
	in 4..7 -> println("Number between 4 to 7")
	!in 8..10 -> println("Number is not in range of 8 to 10")
	else -> println("Out of Range") // default case
}
```
### What are primary and secondary constructors in Kotlin?
#### Primary Constructor
- The primary constructor is initialized in the class header, goes after the class name, using the **constructor** keyword. The parameters are optional in the primary constructor.  
- The primary constructor cannot contain any code, the initialization code can be placed in a separate initializer block prefixed with the **init** keyword.
```java
class employee(emp_id : Int , emp_name: String) {
	val id: Int
	var name: String
	// initializer block
	init {
		id = emp_id
		name = emp_name
		println("Employee id is: $id")
		println("Employee name: $name")
	}
}
```
#### Secondary Constructor
- Kotlin may have one or more secondary constructors. Secondary constructors allow initialization of variables and allow to provide some logic to the class as well. They are prefixed with the **constructor** keyword.
```java
class Add {

	constructor(a: Int, b: Int) {
		var c = a + b
		println("Sum of 5, 6 = ${c}")
	}

	constructor(a: Int, b: Int, c: Int) {
		var d = a + b + c
		println("Sum of 5, 6, 7 = ${d}")
	}
	constructor(a: Int, b: Int, c: Int, d: Int) {
		var e = a + b + c + d
		println("Sum of 5, 6, 7, 8 = ${e}")
	}
}
```
### Singleton class
#### Rules for making a class Singleton
The following rules are followed to make a Singleton class:
1.  A private constructor
2.  A static reference of its class
3.  One static method
4.  Globally accessible object reference
5.  Consistency across multiple threads

#### Singleton Example
Following is the example of Singleton class in Java:
```java
public class Singleton {

    private static Singleton instance = null;

    private Singleton() {
        
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```
#### Singleton class in Kotlin:
```
object Singleton
```
##### Object
- In Kotlin, we need to use the **object** keyword to use Singleton class. The **object** class can have functions, properties, and the **init** method. The constructor method is not allowed in an object so we can use the init method if some initialization is required and the object can be defined inside a class. The object gets instantiated when it is used for the first time.
```java
object Singleton{ 
	init { 
		println("Singleton class invoked.") 
	} 
	var variableName = "I am Var"  
	fun printVarName(){ 
		println(variableName) 
	} 
}
fun main(args: Array<String>) { 
	Singleton.printVarName() 
	Singleton.variableName = "New Name" 
	var a = A() 
} 
class  A { 
	init { 
		println("Class init method. Singleton variableName property : ${Singleton.variableName}") 			     
		Singleton.printVarName() 
	} 
}
```
Here, in the above example, we are having one function named  **printVarName()**  and one property named  **“variableName”.** When  **A** class is instantiated, then changes can be reflected in the  **object**  class. So, the output of the above code will be:

```java
Singleton class invoked.
I am Var
Class init method. Singleton variableName property : New Name
New Name
```
##### Object extending a class
- We can use an object in Kotlin to extend some class or implement some interface just like a normal class. Let’s have an example of the same:
```java
fun main(args: Array<String>) {
    var a = A()
    Singleton.printVarName()
}

open class A {
    open fun printVarName() {
        print("I am in class printVarName")
    }
    init {
        println("I am in init of A")
    }
}

object Singleton : A() {
    init {
        println("Singleton class invoked.")
    }
    var variableName = "I am Var"
    override fun printVarName() {
        println(variableName)
    }
}
```
- And the output of the above code will be:
```java
I am in init of A
I am in init of A
Singleton class invoked.
I am var
```
#### Singleton class with Argument in Kotlin
- If we particularly talk about Android, we know that in Android we generally need to pass a  **context**  instance to  **init**  block of a singleton. This can be done using  **Early initialization**  and  **Lazy initialization**. 
	- In early initialization, all the components are initialized in the  **Application.onCreate()**  using the  **init()**  functions. But this results in slowing down the application startup by blocking the main thread. So, it is generally advised to use the  **lazy initialization**  way. 
	- In lazy initialization, we use the context as an argument to a function returning the instance of the singleton. We can achieve this by using a  **SingletonHolder**  class. Also, to make it thread-safe, we need to have a way of 
synchronization and double-checked locking.

```java
open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
```

The above code is the most efficient code for double-checked locking system and the code is somehow similar to the  **lazy()**  function in Kotlin and that’s why it is called lazy initialization. So, whenever you want a singleton class with arguments then you can use the  **SingletonHolder**  class.
- Here, in the above code, in place of the  **creator**  function which is passed as an argument to the  **SingletonHolder**, a custom lambda can also be declared inline or we can pass a reference to the private constructor of the singleton class. So, the code will be:
```java
class YourManager private constructor(context: Context) {
    init {
        // do something with context
    }

    companion object : SingletonHolder<YourManager, Context>(::YourManager)
}
```
Now, the singleton can be easily invoked and initialized by writing the below code and this is lazy as well as thread-safe :)
```java
YourManager.getInstance(context).doSomething()
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTUxODIxNTE2MSwtMTc4ODE5NDg0NCwxNT
AzNTcxMzldfQ==
-->