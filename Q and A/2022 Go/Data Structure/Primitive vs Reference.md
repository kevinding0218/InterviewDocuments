### HTTP
#### HTTP Request Flow
1. Users/Client access websites through domain nammes. such as api.ebay.com. Usually the Domain Name System (DNS) is a paid service provided by 3rd parties and not hosted by our server
2. Internet Protocal (IP) address is returned to the browser or mobile app.  For example, IP address 15.125.23.234 is returned from DNS
3. Once the IP address is obtained at Client, Hypertext Transfer Protocol (HTTP) requests are sent directly to your web server using IP address 15.125.23.234
4. The web server returns HTML pages or JSON response for rendering
#### HTTPS Request Flow
- The S in HTTPS stands for "secure". HTTPS uses TLS(or SSL) to encrypt HTTP requests and responses
- Just like an ID card confirms a person's identity, a private key confirms server identity. When a client opens a channel with an origin server (e.g. when a user navigates to a website), possession of the private key that matches with the public key in a website's SSL certificate proves that the server is actually the legitimate host of the website. This prevents or helps block a number of attacks that are possible when there is no authentication, such as:
#### TSL
- TLS uses a technology called  **public key cryptography**, there are two  keys, a public key and a private key, and the public key is shared with client devices via the server's SSL certificate. When a client opens a connection with a server, the two devices use the public and private key to agree on new keys, called  session keys, to encrypt further communications between them
- All HTTP requests and responses are then encrypted with these session keys, so that anyone who intercepts communications can only see a random string of characters, not the plaintext.
#### How Fiddler capture network packets sent via HTTPS ([Link](https://blog.bytebytego.com/p/ep21-is-https-safe-also?utm_source=substack&utm_medium=email))
- Prerequisite: root certificate of the intermediate server is present in the trust-store
**Step 1** - The client requests to establish a TCP connection with the server. The request is maliciously routed to an intermediate server, instead of the real backend server. Then, a TCP connection is established between the client and the intermediate server.  
**Step 2** - The intermediate server establishes a TCP connection with the actual server.  
**Step 3** - The intermediate server sends the SSL certificate to the client. The certificate contains the public key, hostname, expiry dates, etc. The client validates the certificate.  
**Step 4** - The legitimate server sends its certificate to the intermediate server. The intermediate server validates the certificate.  
**Step 5** - The client generates a session key and encrypts it using the public key from the intermediate server. The intermediate server receives the encrypted session key and decrypts it with the private key.  
**Step 6** - The intermediate server encrypts the session key using the public key from the actual server and then sends it there. The legitimate server decrypts the session key with the private key.  
**Steps 7 and 8** - Now, the client and the server can communicate using the session key (symmetric encryption.) The encrypted data is transmitted in a secure bi-directional channel. The intermediate server can always decrypt the data.

### Database
#### When to use use Non-relational databases
- Your application requires super-low latency
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzc2NzQ0NDA5LDU5MjA5MDc2NiwxOTM2ND
kyNjcsNzMwOTk4MTE2XX0=
-->