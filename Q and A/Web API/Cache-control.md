### What is cached response?
- _Caching_  refers to **storing server response in client itself** so that a client **needs not to make server request for same resource again and again**. A server response should have information about **how a caching is to be done** so that a client caches response for a period of time or never caches the server response.
### Cache-control
- A standard **Cache-control header** can help in attaining cache ability. Enlisted below is the brief description of the various cache-control header:
-   **Public:**  Resources that are marked as the public can be cached by any intermediate components between the client and the server.
-   **Private:**  Resources that are marked as private can only be cached by the client.
-   No cache means that a particular resource cannot be cached and thus the whole process is stopped.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTc5ODY3Mzk5N119
-->