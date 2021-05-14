### What is cached response?
- _Caching_  refers to **storing server response in client itself** so that a client **needs not to make server request for same resource again and again**. A server response should have information about **how a caching is to be done** so that a client caches response for a period of time or never caches the server response.
### Cache-control
- Cache-control is an **HTTP header** used to specify **browser caching policies** in both client requests and server responses. Policies include **how a resource is cached, where it’s cached and its maximum age before expiring**
-   **Public:**  Resources that are marked as the public can be cached by any intermediate components between the client and the server.
-   **Private:**  Resources that are marked as private can only be cached by the client.
-  **Max-
-   No cache means that a particular resource cannot be cached and thus the whole process is stopped.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjEzMTM2Mzk0NV19
-->