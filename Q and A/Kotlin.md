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
#### Kotlin
Following is the example of Singleton class in Kotlin:
```
object Singleton
```

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTEzNjY4NjcyOV19
-->