## Annotation
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
eyJoaXN0b3J5IjpbLTE4NTk5MTEzMDNdfQ==
-->