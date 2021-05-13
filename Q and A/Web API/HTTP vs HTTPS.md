### What is HTTP?
- Full form of HTTP is Hypertext Transfer Protocol. HTTP offers set of rules and standards which govern how any information can be transmitted on the World Wide Web. HTTP provides standard rules for web browsers & servers to communicate.
- HTTP is an application layer network protocol which is built on top of TCP. HTTP uses Hypertext structured text which establishes the logical link between nodes containing text. It is also known as "stateless protocol" as each command is executed separately, without using reference of previous run command.

### What is HTTPS?
- HTTPS stands for Hyper Text Transfer Protocol Secure. It is highly advanced and secure version of HTTP. It uses the port no. 443 for Data Communication. It allows the secure transactions by encrypting the entire communication with SSL. It is a combination of SSL/TLS protocol and HTTP. It provides encrypted and secure identification of a network server.
- HTTP also allows you to create a secure encrypted connection between the server and the browser. It offers the bi-directional security of Data. This helps you to protect potentially sensitive information from being stolen.

## KEY DIFFERENCE

-   HTTP lacks security mechanism to encrypt the data whereas HTTPS provides SSL or TLS Digital Certificate to secure the communication between server and client.
-   HTTP operates at Application Layer whereas HTTPS operates at Transport Layer.
-   HTTP by default operates on port 80 whereas HTTPS by default operates on port 443.
-   HTTP transfers data in plain text while HTTPS transfers data in cipher text (encrypt text).
-   HTTP is fast as compared to HTTPS because HTTPS consumes computation power to encrypt the communication channel.

## Advantages of HTTP:

-   HTTP can be implemented with other protocol on the Internet, or on other networks
-   HTTP pages are stored on computer and internet caches, so it is quickly accessible
-   Platform independent which allows cross-platform porting
-   Does not need any Runtime support
-   Usable over Firewalls! Global applications are possible
-   Not Connection Oriented; so no network overhead to create and maintain session state and information

## Advantages of HTTPS

-   In most cases, sites running over HTTPS will have a redirect in place. Therefore, even if you type in HTTP:// it will redirect to an https over a secured connection
-   It allows users to perform secure e-commerce transaction, such as online banking.
-   SSL technology protects any users and builds trust
-   An independent authority verifies the identity of the certificate owner. So each SSL Certificate contains unique, authenticated information about the certificate owner.

### Limitations of HTTP

-   There is no privacy as anyone can see content
-   Data integrity is a big issue as someone can alter the content. That's why HTTP protocol is an insecure method as no encryption methods are used.
-   Not clear who you are talking about. Anyone who intercepts the request can get the username and password.

### Limitations of HTTPS
-   HTTPS protocol can't stop stealing confidential information from the pages cached on the browser
-   SSL data can be encrypted only during transmission on the network. So it can't clear the text in the browser memory
-   HTTPS can increase computational overhead as well as network overhead of the organization
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTcwMjY5OTk1MV19
-->