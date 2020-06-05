## Annotation
#### @Produces
```
@Produces(MediaType.APPLICATION_JSON)
@Produces("application/json")
@Produces({"image/jpeg,image/png"})
```
- The  @Produces  annotation is used to specify the MIME media types or representations a resource can produce and send back to the client. If  @Produces  is applied at the class level, all the methods in a resource can produce the specified MIME types by default. If applied at the method level, the annotation overrides any  @Produces  annotations applied at the class level.
- If no methods in a resource are able to produce the MIME type in a client request, the JAX-RS runtime sends back an HTTP “406 Not Acceptable” error.
#### @Consumes
```
@Consumes(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTAxNDU3ODI5Ml19
-->