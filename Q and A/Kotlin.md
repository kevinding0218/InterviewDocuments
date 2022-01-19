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
```
ou can use an object in Kotlin to extend some class or implement some interface just like a normal class. Let’s have an example of the same:

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
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTc4MjA3MjQyM119
-->