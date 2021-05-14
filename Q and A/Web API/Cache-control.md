### What is cached response?
- _Caching_  refers to **storing server response in client itself** so that a client **needs not to make server request for same resource again and again**. A server response should have information about **how a caching is to be done** so that a client caches response for a period of time or never caches the server response.
### Cache-control
- Cache-control is an **HTTP header** used to specify **browser caching policies** in both client requests and server responses. Policies include **how a resource is cached, where it’s cached and its maximum age before expiring**
-   **Public:**  Resources that are marked as the public can be cached by any intermediate components between the client and the server.
-   **Private:**  Resources that are marked as private can only be cached by the client.
-  **Max-Age:** The max-age request directive defines, in seconds, the amount of time it takes for a cached copy of a resource to expire. After expiring, a browser must refresh its version of the resource by sending another request to a server.
	- For example, `cache-control: max-age=120` means that the returned resource is valid for 120 seconds, after which the browser has to request a newer version.
-   **No cache** means The no-cache directive means that a browser may cache a response, but must first submit a validation request to an origin server
#### Additional HTTP Cache Headers
In addition to cache-control, notable HTTP cache headers include:
-   **Expires** – This header specifies a fixed date/time for the expiration of a cached resource. For example, `Expires: Sat, 13 May 2017 07:00:00 GMT` signals that the cached resource expires on May 13, 2017 at 7:00 am GMT. The expires header is ignored when a cache-control header containing a max-age directive is present.
-   **ETag** – A response header that identifies the version of served content according to a token – a string of characters in quotes, e.g., `"675af34563dc-tr34"` – that changes after a resource is modified. If a token is unchanged before a request is made, the browser continues to use its local version.
-   **Vary** – A header that determines the responses that must match a cached resource for it to be considered valid. For example, the header `Vary: Accept-Language, User-Agent` specifies that a cached version must exist for each combination of user agent and language.
- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTkyMTIyOTM1MF19
-->