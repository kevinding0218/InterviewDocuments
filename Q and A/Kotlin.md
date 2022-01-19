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
- Only the property name `name` will be used inside the **toString(), equals(), hashCode(), and copy()** implementations. 
- While two Person objects can have different ages, they will be **treated as equal** for above example
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
eyJoaXN0b3J5IjpbNDI3NzU2MDk3LDE1MDM1NzEzOV19
-->