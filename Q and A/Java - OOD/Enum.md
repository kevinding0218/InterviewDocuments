### Enum
- Enums are essentially final classes with a fixed number of instances. They can implement interfaces but cannot extend another class.
- This flexibility is useful in implementing the strategy pattern, for example, when the number of strategies is fixed
- Singleton
Singleton using Enum in Java: By default creation of the Enum instance is thread-safe, but any other Enum method is the programmer’s responsibility.
```
public enum EasySingleton{
  INSTANCE;
}
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbODEyOTMxMTEzXX0=
-->