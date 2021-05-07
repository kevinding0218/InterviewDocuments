### GET vs POST:
#### GET
- In GET method, values are visible in the URL.
- GET has a limitation on the length of the values, generally 255 characters.
- GET performs are better compared to POST because of the simple nature of appending the values in the URL.
- This method supports only string data types.
- GET results can be bookmarked.
- GET request is often cacheable.
- GET Parameters remain in web browser history.

#### POST
- In POST method, values are not visible in the URL.
- POST has no limitation on the length of the values since they are submitted via the body of HTTP.
- It has lower performance as compared to GET method because of time spent in including POST values in the HTTP body.
- This method supports different data types, such as string, numeric, binary, etc.
- POST results cannot be bookmarked.
- The POST request is hardly cacheable.
- Parameters are not saved in web browser history.

### Pros and Cons
#### Get Pros
- The GET method can retrieve information identified by the request-URl (Uniform Resource Identifier).
-  GET requests can be viewed in the browser history.
-  It enables you to save the results of a HTML form.
-  You can easily use GET method to request required data.
#### Get Cons
- GET can't be used to send word documents or images.
-  GET requests can be used only to retrieve data
-  The GET method cannot be used for passing sensitive information like usernames and passwords.
-  The length of the URL is limited.
#### Post Pros
-  You can send user-generated data to the web server.
- POST is a secure method as its requests do not remain in browser history.
- You can effortlessly transmit a large amount of data using post.
- You can keep the data private.
#### Post Cons
-   It is not possible to save data as the data sent by the POST method is not visible in the URL.
-   You cannot see POST requests in browser history.

### Post vs Put
#### Post 
- This method is not idempotent.So if you retry the request N times, you will end up having N resources with N different URIs created on server.
- POST method is call when you have to add a child resource under resources collection.
- You cannot cache PUT method responses.
- If you send the same POST request more than one time, you will receive different results.
#### Put
- This method is idempotent. So if you send retry a request multiple times, that should be equivalent to single request modification.
- PUT method is call when you have to modify a single resource, which is already a part of resource collection.
- Put method usually comes with a specific item Id
- PUT method answer can be cached.
- If you send the same request multiple times, the result will remain the same.





















<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQwOTQxMTkyMSwxMjg0NTEzNjU5XX0=
-->