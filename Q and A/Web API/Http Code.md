### Http Status Code
#### 1xx informational response
#### 2xx Success
- 200 OK
- 201 Created: Request is completed, new resource is created and usually work with Post
- 202 Accepted: The request is accepted for processing, but the processing is not complete.
- 204 No Content: A status code and a header are given in the response, but there is no entity-body in the reply.
#### 3xx Redirection
- 301 Moved Permanently: make sure that search engines or application and users are sent to the correct page. A 301 status code is used when any page has been permanently moved to another location
#### 4xx Client Error
- 400 Bad Request: The Request can not be fulfilled because of some bad syntax.
- 401 Unauthorized: The request has not been applied because it lacks valid authentication credentials for the target resource.
- 403 Forbidden: The request is a legal request, but the server is refusing to respond to it due to some factor.
- 404 Page Not Found: Requested page is not found.
- 408 Request Timeout: The Server time out waiting for the request
- 415 Unsupported Media Type: If media type is not supported, it won't accept the request.
- 422 Unprocessable
#### 5xx Server Error
- 500 Internal Server Error: The request was not completed. The server met an unexpected condition.
- 502 Bad Gateway: The server, while acting as a gateway or proxy, received an invalid response from an inbound server it accessed while attempting to fulfill the request.
- 503 Service Unavailable: The server is currently unavailable
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjA2ODkxNzM0MCwxNjk3MzAxMTM0LDczMD
k5ODExNl19
-->