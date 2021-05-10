### Reflection
- Reflection allows programmatic access to information about a Java program’s types. Commonly used information includes: methods and fields available on a class, interfaces implemented by a class, and the runtime-retained annotations on classes, fields and methods.
- Examples given are likely to include:
	- Annotation-based serialization libraries often map class fields to JSON keys or XML elements (using annotations). These libraries need reflection to inspect those fields and their annotations and also to access the values during serialization.
	- Model-View-Controller frameworks call controller methods based on routing rules. These frameworks must use reflection to find a method corresponding to an action name, check that its signature conforms to what the framework expects (e.g. takes a  `Request`  object, returns a  `Response`), and finally, invoke the method.
	- Dependency injection frameworks lean heavily on reflection. They use it to instantiate arbitrary beans for injection, check fields for annotations such as  `@Inject`  to discover if they require injection of a bean, and also to set those values.
	- Object-relational mappers such as Hibernate use reflection to map database columns to fields or getter/setter pairs of a class, and can go as far as to infer table and column names by reading class and getter names, respectively.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5NTczMzUwNjVdfQ==
-->