### Inner class vs Inner Static class
####
- Inner class require instance of outer class for initialization and they are always associated with instance of enclosing class. 
- On the other hand nested static class is not associated with any instance of enclosing class.  
```
class OuterClass {
    ...
    static class StaticNestedClass {
        ...
    }
    class InnerClass {
        ...
    }
}
// To instantiate an inner class, you must first instantiate the outer class. 
// Then, create the inner object within the outer object with this syntax:
OuterClass outerObject = new OuterClass()
OuterClass.InnerClass innerObject = outerObject.new InnerClass();
// Static nested classes are accessed using the enclosing class name:
OuterClass.StaticNestedClass nestedObject = new OuterClass.StaticNestedClass();
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE0MzUyMTcxMzYsLTIxMDA0MTI3Nyw3Mz
A5OTgxMTZdfQ==
-->