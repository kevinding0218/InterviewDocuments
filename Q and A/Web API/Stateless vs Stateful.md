### Explain the difference between stateless and stateful protocols. Which type of protocol is HTTP? Explain your answer.
- A  **stateless**  communications protocol treats each request as an independent transaction. It therefore does not require the server to retain any session, identity, or status information spanning multiple requests from the same source. Similarly, the requestor can not rely on any such information being retained by the responder.
- In contrast, a  **stateful**  communications protocol is one in which the responder maintains “state” information (session data, identity, status, etc.) across multiple requests from the same source.
- **HTTP is a stateless protocol**. HTTP does not require server to retain information or status about each user for the duration of multiple requests.
- Some web servers implement states using different methods (using cookies, custom headers, hidden form fields etc.). However, in the very core of every web application everything relies on HTTP which is still a stateless protocol that is based on simple request/response paradigm.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjQxMzU2NDMzXX0=
-->