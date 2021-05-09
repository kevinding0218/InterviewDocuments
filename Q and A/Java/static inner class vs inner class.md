### Inner class vs Inner Static class
- Inner class require instance of outer class for initialization and they are always associated with instance of enclosing class. On the other hand nested static class is not associated with any instance of enclosing class.  
```
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
```
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxMDA0MTI3Nyw3MzA5OTgxMTZdfQ==
-->