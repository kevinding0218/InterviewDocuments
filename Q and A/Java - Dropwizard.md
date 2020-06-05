### static initializer / static block
- The static initializer is a `static {}` block of code inside java class, and run only one time before the constructor or main method is called.
- is a block of code  `static { ... }`  inside any java class. and executed by virtual machine when class is called.
- No  `return`  statements are supported.
- No arguments are supported.
- No  `this`  or  `super`  are supported.
- most of the time it is used when doing database connection, API init, Logging and etc.
- similar as static constructor in C#
#### why static constructor is not allowed
- Static Belongs to Class, Constructor to Object
	- Whereas a Constructor belongs to the object and called when we use the new operator to create an instance. 
	- Since a constructor is not class property, it makes sense that it’s not allowed to be static.
- Static Block/Method can’t access non-static variables
	- static methods can’t access non-static variables. Same is true for static block also.
	- if we make constructor as static then it won’t be able to initialize the object variables. That will defeat the whole purpose of having a constructor for creating the object. So it is justified to have the constructor as non-static.
	- we can’t use `this` inside a static method to refer to the object variable.
- Static Constructor will break inheritance
	- In Java, every class implicitly extends Object class. We can define a class hierarchy where subclass constructor calls the superclass constructor. This is done by `super()` method call.
	- Most of the times JVM automatically calls the superclass constructor but sometimes we have to manually call them if there are multiple constructors in the superclass.
	- If you look at the `super()` method, it’s not static. So if the constructor becomes static, we won’t be able to use it and that will break [inheritance in java](https://www.journaldev.com/644/inheritance-java-example).
- Java Static Constructor Alternative
	- If you want to initialize some  [static](https://www.journaldev.com/1365/static-keyword-in-java)  variables in the class, you can use static block. 
	- Note that we can’t pass arguments to the static block, so if you want to initialize static variables then you can do that in the normal constructor too.
### Nested classes
- Nested classes are divided into two categories: **static** and **non-static**. 				
	- Nested classes that are declared static are simply called static nested classes. 
	- Non-static nested classes are called inner classes.
#### static nested class
- 
## JavaEE Annotation
#### @Path [link]([https://docs.oracle.com/cd/E19798-01/821-1841/6nmq2cp26/index.html](https://docs.oracle.com/cd/E19798-01/821-1841/6nmq2cp26/index.html))
```
@Path("eventemail/v1")
```
- The @Path annotation identifies the URI path template to which the resource responds and is specified at the class or method level of a resource
- Variables are denoted by braces ({  and  }). For example, look at the following  @Path  annotation:
```
@Path("/users/{username}")
```
In this kind of example, a user is prompted to type his or her name, and then a JAX-RS web service configured to respond to requests to this URI path template responds. For example, if the user types the user name “Galileo,” the web service responds to the following URL:
```
http://example.com/users/Galileo
```
#### @PathParam
- the @PathParam annotation may be used on the method parameter of a request method
```
@Path("/users/{username}")
public class UserResource {

    @GET
    @Produces("text/xml")
    public String getUser(@PathParam("username") String userName) {
        ...
    }
}
```
- This variable may be customized by specifying a different regular expression after the variable name. For example, if a user name must consist only of lowercase and uppercase alphanumeric characters, override the default regular expression in the variable definition:
```
@Path("users/{username: [a-zA-Z][a-zA-Z_0-9]}")
```
- In this example the  username  variable will match only user names that begin with one uppercase or lowercase letter and zero or more alphanumeric characters and the underscore character. If a user name does not match that template, a **404 (Not Found)** response will be sent to the client.
- A URI path template has one or more variables, with each variable name surrounded by braces: { to begin the variable name and } to end it. In the preceding example, username is the variable name.
- For example, if you want to deploy a resource that responds to the URI path template  `http://example.com/myContextRoot/resources/{name1}/{name2}/`, you must deploy the application to a Java EE server that responds to requests to the  `http://example.com/myContextRoot`  URI and then decorate your resource with the following  @Path  annotation:
```
@Path("/{name1}/{name2}/")
public class SomeResource {
	...
}
```
- In this example, the URL pattern for the JAX-RS helper servlet, specified in  web.xml, is the default:
```
<servlet-mapping>
	  <servlet-name>My JAX-RS Resource</servlet-name>
	  <url-pattern>/resources/*</url-pattern>
</servlet-mapping>
```
#### @Produces
```
@Produces(MediaType.APPLICATION_JSON)
@Produces("application/json")
@Produces({"image/jpeg,image/png"})
```
- The  @Produces  annotation is used to specify the MIME media types or representations a resource can produce and send back to the client. If  @Produces  is applied at the class level, all the methods in a resource can produce the specified MIME types by default. If applied at the method level, the annotation overrides any  @Produces  annotations applied at the class level.
- If no methods in a resource are able to produce the MIME type in a client request, the JAX-RS runtime sends back an HTTP “406 Not Acceptable” error.
- The value of @Produces is an array of String of MIME types
#### @Consumes
```
@Consumes(MediaType.APPLICATION_JSON)
@Consumes("application/json")
@Consumes({"text/plain,text/html"})
```
- The  @Consumes  annotation is used to specify which MIME media types of representations a resource can accept, or consume, from the client. If  @Consumes  is applied at the class level, all the response methods accept the specified MIME types by default. If applied at the method level,  @Consumes  overrides any  @Consumes  annotations applied at the class level.
- If a resource is unable to consume the MIME type of a client request, the JAX-RS runtime sends back an HTTP 415 (“Unsupported Media Type”) error.
- The value of  @Consumes  is an array of  String  of acceptable MIME types.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyMDA2NDUwMDcsLTM2MzYwMDU5NSwxMT
cyNTI5MTIxLC0xMjM1OTk1MzA0XX0=
-->