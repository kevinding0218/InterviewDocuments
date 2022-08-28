### Server
#### HTTP Request Flow
1. Users/Client access websites through domain nammes. such as api.ebay.com. Usually the Domain Name System (DNS) is a paid service provided by 3rd parties and not hosted by our server
2. Internet Protocal (IP) address is returned to the browser or mobile app.  For example, IP address 15.125.23.234 is returned from DNS
3. Once the IP address is obtained at Client, Hypertext Transfer Protocol (HTTP) requests are sent directly to your web server using IP address 15.125.23.234
4. The web server returns HTML pages or JSON response for rendering
#### HTTPS Request Flow
- The S in HTTPS stands for "secure". HTTPS uses TLS(or SSL) to encrypt HTTP requests and responses
- Just like an ID card confirms a person's identity, a private key confirms server identity. When a client opens a channel with an origin server (e.g. when a user navigates to a website), possession of the private key that matches with the public key in a website's SSL certificate proves that the server is actually the legitimate host of the website. This prevents or helps block a number of attacks that are possible when there is no authentication, such as:
##### TSL
TLS uses a technology called  public key cryptography](https://www.cloudflare.com/learning/ssl/how-does-public-key-encryption-work/): there are two  [keys](https://www.cloudflare.com/learning/ssl/what-is-a-cryptographic-key/), a public key and a private key, and the public key is shared with client devices via the server's SSL certificate. When a client opens a connection with a server, the two devices use the public and private key to agree on new keys, called  [session keys](https://www.cloudflare.com/learning/ssl/what-is-a-session-key/), to encrypt further communications between them.

All HTTP requests and responses are then encrypted with these session keys, so that anyone who intercepts communications can only see a random string of characters, not the plaintext.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQ1MjU2MDg3LDU5MjA5MDc2NiwxOTM2ND
kyNjcsNzMwOTk4MTE2XX0=
-->