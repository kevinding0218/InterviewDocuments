### What are static initializers and when would you use them?
- A static initializer gives you the opportunity to run code during the initial loading of a class and it guarantees that this code will only run once and will finish running before your class can be accessed in any way.
- They are useful for performing initialization of complex static objects or to register a type with a static registry, as JDBC drivers do.
- Suppose you want to create a static, immutable  `Map`  containing some feature flags. Java doesn’t have a good one-liner for initializing maps, so you can use static initializers instead:
```java
        public static final Map<String, Boolean> FEATURE_FLAGS;
        static {
            Map<String, Boolean> flags = new HashMap<>();
            flags.put("frustrate-users", false);
            flags.put("reticulate-splines", true);
            flags.put(...);
            FEATURE_FLAGS = Collections.unmodifiableMap(flags);
        }

```
Within the same class, you can repeat this pattern of declaring a static field and immediately initializing it, since multiple static initializers are allowed.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTg0MzczMzE2OV19
-->