### TCP and UDP
#### Why we talks about TCP vs UDP
- there are many protocols out there is http, there's http2 there's grpc, etc but those two are the building blocks and the fundamentals of everything that comes on top
- if you understand that you pick UDP over TCP then you will be okay to pick quick which is on top of UDP, otherwise you would pick for example http which is on top of tcp, or grpc which is on top of http 2 which is also on top of tcp
#### Difference
- 
- TCP protocol guarantees delivery of data and also guarantees that packets will be delivered in the same order in which they were sent.
- UDP protocol does not guarantee you are getting all the packets and order is not guaranteed. But because UDP throws all the error-checking stuff out, it is faster.
- udp is
udp you send a packet and you just
forget about it
right the only guarantee that udp gives
you is
i can tell you if the packet arrived
correctly or not
if i'm sending the letter a
right i can guarantee that i have sent
an a and then
i got an a right that's it i'm not gonna
retry
i'm not gonna check if you can handle
the letter a or not
i'm not to check any of that stuff right
i'm not going to do any floor control
i'm going to do any re-transmission if
it fails
i don't know if you even get it or not
i'm just as a client
i just send it that's it i don't do
anything else
### Which one to choose?
- If we want rate limiting solution to **be more accurate**, but with a little bit of **performance overhead**, we need to go with **TCP**.
- If we ok to have a bit **less accurate** solution, but the one that **works faster**, UDP should be our choice.
- For majority of clusters out there, where cluster size is less then several thousands of nodes and number of active buckets per second is less then tens of thousands, gossip communication over UDP will work really fast and is quite accurate.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjA2NTQ2Mzk0NCwtNjQ0NDYyNjAyLDE3Mj
MzNjY5NF19
-->