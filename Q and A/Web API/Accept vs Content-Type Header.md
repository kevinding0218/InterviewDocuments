### What is the us
- the  **Accept header**  is **used by HTTP clients to tell the server what content types they'll accept**. The server will then send back a response, which will include a  **Content-Type** header  **telling the client what the content type of the returned content actually is.**
- However, as you may have noticed, HTTP requests  can also contain  Content-Type headers. Why? Well, think about  POST  or  PUT requests. With those request types, the client is actually sending a bunch of data to the server as part of the request, and the Content-Type header tells the server what the data actually is (and thus determines how the server will parse it).
- In particular, for a POST request resulting from an HTML form submission, the Content-Type of the request will (normally) be one of the standard  [form content types](http://www.w3.org/TR/html401/interact/forms.html#h-17.13.4)  below, as specified by the  `enctype`  attribute on the  `<form>`  tag:

-   `application/x-www-form-urlencoded`  (default, older, simpler, slightly less overhead for small amounts of simple ASCII text, no file upload support)
-   `multipart/form-data`  (newer, adds support for file uploads, more efficient for large amounts of binary data or non-ASCII text)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTkwNzkzMDA5OF19
-->