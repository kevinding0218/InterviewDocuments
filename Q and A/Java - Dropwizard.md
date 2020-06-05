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
- 
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
eyJoaXN0b3J5IjpbMTY5NTczNDI3Nl19
-->