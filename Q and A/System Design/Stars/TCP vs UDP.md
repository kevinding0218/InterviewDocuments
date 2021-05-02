### TCP and UDP
#### Why we talks about TCP vs UDP
- there are **many protocols out there is http, there's http2 there's grpc**, etc but **those two are the building blocks and the fundamentals of everything that comes on top**
- if you understand that you pick UDP over TCP then you will be okay to **pick quick which is on top of UDP**, otherwise you would pick for example **http which is on top of tcp, or grpc which is on top of http 2 which is also on top of tcp**
#### UDP
- UDP protocol **does not guarantee you are getting all the packets and order is not guaranteed**. But because UDP throws all the error-checking stuff out, it is faster.
- For example, **you send a packet and you just forget about it** right? The only guarantee that udp gives you is i can tell you if the packet arrived correctly or not, i'm not gonna retry,  i'm not gonna check if you can handle the letter a or not, i'm not going to do any flaw control, i'm going to do any re-transmission if it fails, i don't know if you even get it or not, it does the job that just send the package
#### TPC
- TCP protocol **guarantees delivery of data and also guarantees that packets will be delivered in the same order in which they were sent, it tries to solve the problems up in the udp**
- Let's taste how tcp does, it first **tries to establish a connection**, hey server are you even there? let me check oh i can't just send stuff, let me establish a connection first. TCP actually creates a physical connection between the two and it actually reserves some memory on the server side, says hey sen i am going to start my sequence with the within my packets with the number hundred right? and then the server say okay i acknowledge that that means i received that and then, **this is a stateful protocol there's a state on the server(HandShake)**
### Which one to choose?
- If we want rate limiting solution to **be more accurate**, but with a little bit of **performance overhead**, we need to go with **TCP**.
- If we ok to have a bit **less accurate** solution, but the one that **works faster**, **UDP** should be our choice.
### Building a Chat App using TCP
- Let's say we're building a chatting app right, we're building it peer-to-peer, like we need some some centralized place
where you're gonna send your message right and then another party will join the same server and then they can exchange
the messages between them
- So if you're going to use udp as its raw protocol to build a chatting app then you are in trouble as is you might send a "Hi" and you might be lucky and the server receives the "Hi" and then finds out who to send it to and then it sends it to the to the to the actual server to the actual um client. However, this is not guranteed, sometimes  you're gonna get a
completely different message, especially if you need to do some encryption,it's going to flip and then it cannot even be decrypted.
- so using tcp to build a chatting app is okay now when i say tcp feel free to use websockets which is on top of http which is on top of tcp, it's absolutely fine you use pure http if you want you don't want bi-directional but like use long polling
### Building a Game using UDP
- a game however on the other end if i am building a game multiplayer game between two parties you can still use tcp and websocket on http however **be be sure that there will be some lags** right? because **those lags are because of the expense of the acknowledgement or because of the expense of the flow control and other stuff as well**
### Building a Gossip Communication using UDP
- For majority of clusters out there, where cluster size is less then several thousands of nodes and number of active buckets per second is less then tens of thousands, gossip communication over UDP will work really fast and is quite accurate.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEzNDcwOTU5MCwtNjQ0NDYyNjAyLDE3Mj
MzNjY5NF19
-->