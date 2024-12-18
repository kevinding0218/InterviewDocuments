### Singleton
https://dzone.com/articles/java-singletons-using-enum
https://www.geeksforgeeks.org/advantages-and-disadvantages-of-using-enum-as-singleton-in-java/#:~:text=Creation%20of%20Enum%20instance%20is,some%20line%20of%20code%20enum.
**Advantages of using Enum as Singleton:**
**1. Enum Singletons are easy to write**
Compared to double-checked locking with synchronization, Enum singletons are very easy. If you don’t think that the following code for traditional singleton with double-checked locking and Enum Singletons are compared:

Singleton using Enum in Java: By default creation of the Enum instance is thread-safe, but any other Enum method is the programmer’s responsibility.
```
public enum EasySingleton{
  INSTANCE;
}
```
**2. Enum Singletons handled Serialization by themselves**
Another problem with conventional Singletons is that they are no longer Singleton once you implement a serializable interface because the method readObject() always returns a new instance just like the Java constructor. By using the readResolve() method and discarding newly created instances, you can avoid that by substituting Singleton, as shown in the example below:
```
 private Object readResolve(){
      return INSTANCE;
  }
```
**3. Creation of Enum instance is thread-safe**
By default, the Enum instance is thread-safe, and you don’t need to worry about double-checked locking.
In summary, the Singleton pattern is the best way to create Singleton in Java 5 world, given the Serialization and thread-safety guaranteed and with some line of code enum.
**Disadvantages of using Enum as a singleton:**
**1. Coding Constraints**
In regular classes, there are things that can be achieved but prohibited in enum classes. Accessing a static field in the constructor
**2. Serializability**
For singletons, it is very common to be stateful. In general, those singletons should not be serializable. There is no real example where transporting a stateful singleton from one VM to another VM makes sense; a singleton means “unique within a VM” not “unique in the universe”
If serialization really makes sense for a stateful singleton, the singleton should specify explicitly and accurately what it means in another VM to deserialize a singleton where there may already be a singleton of the same type.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1ODEzMTA0NF19
-->