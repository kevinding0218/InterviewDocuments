- [Cross-origin resource sharing (CORS)](http://www.w3.org/TR/cors/)  is a mechanism that allows many resources (e.g., fonts, JavaScript, etc.) on a web page to be requested from another domain outside the domain from which the resource originated. It’s a mechanism supported in HTML5 that manages  `XMLHttpRequest`  access to a domain different.
- An example of a cross-origin request is a web application served from  `http://mydomain.com`  that uses AJAX to make a request for  `http://yourdomain.com`.
- CORS adds new HTTP headers that provide access to permitted origin domains. For HTTP methods other than GET (or POST with certain MIME types), the specification mandates that browsers first use an HTTP OPTIONS request header to solicit a list of supported (and available) methods from the server. The actual request can then be submitted. Servers can also notify clients whether “credentials” (including Cookies and HTTP Authentication data) should be sent with requests.
- CORS behavior is not an error,  it’s a **security mechanism to protect users**.
- CORS is designed to **prevent a malicious website that a user may unintentionally visit** from making a request to a legitimate website to read their personal data or perform actions against their will.
- res.header("**Access-Control-Allow-Origin**", "*");
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTczNzIyOTczMSwxMjgxNDkzOTgzXX0=
-->