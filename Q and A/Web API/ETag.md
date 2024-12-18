### What is an ETag and how does it work?
- An ETag is an opaque identifier assigned by a web server to a specific version of a resource found at an URL. If the resource content at that URL ever changes, a new and different ETag is assigned.
- In typical usage, when an URL is retrieved the web server will return the resource along with its corresponding ETag value, which is placed in an HTTP “ETag” field:
```
ETag: "unique_id_of_resource_version"
```
The client may then decide to cache the resource, along with its  `ETag`. Later, if the client wants to retrieve the same URL again, it will send its previously saved copy of the ETag along with the request in a  **"If-None-Match"**  field.
```
If-None-Match: "unique_id_of_resource_version"
```
On this subsequent request, the server may now compare the client’s  `ETag`  with the  `ETag`  for the current version of the resource. If the  `ETag`  values match, meaning that the resource has not changed, then the server may send back a very short response with a  `HTTP 304 Not Modified`  status. The 304 status tells the client that its cached version is still good and that it should use that.
However, if the  `ETag`  values do not match, meaning the resource has likely changed, then a full response including the resource’s content is returned, just as if  `ETag`  were not being used. In this case the client may decide to replace its previously cached version with the newly returned resource and the new  `ETag`.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTEzMzYzNzAyNV19
-->