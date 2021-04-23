### Singleton
https://dzone.com/articles/java-singletons-using-enum
https://www.geeksforgeeks.org/advantages-and-disadvantages-of-using-enum-as-singleton-in-java/#:~:text=Creation%20of%20Enum%20instance%20is,some%20line%20of%20code%20enum.
**Advantages of using Enum as Singleton:**
**1. Enum Singletons are easy to write**
**2. Enum Singletons handled Serialization by themselves**
Another problem with conventional Singletons is that they are no longer Singleton once you implement a serializable interface because the method readObject() always returns a new instance just like the Java constructor. By using the readResolve() method and discarding newly created instances, you can avoid that by substituting Singleton, as shown in the example below:

 private Object readResolve(){
      return INSTANCE;
  }
**3. Creation of Enum instance is thread-safe**

By default, the Enum instance is thread-safe, and you don’t need to worry about double-checked locking.

In summary, the Singleton pattern is the best way to create Singleton in Java 5 world, given the Serialization and thread-safety guaranteed and with some line of code enum.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTk2MDQ5NDkzMiwtNDk2OTIwMTQzXX0=
-->