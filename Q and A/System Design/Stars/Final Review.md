### Final review
- Let's take one final look at our high-level architecture and evaluate whether all non-functionalrequirements are met.
#### Did we design a scalable system?
- Yes. Every component is horizontally scalable. Sender service also has a great potential for vertical scaling, when more powerful hosts can execute more delivery tasks.
#### Did we design a highly available system?
- Yes. There is no single point of failure, each component is deployed across several data centers.
#### Did we design a highly performant system?
- FrontEnd service is fast. We made it responsible for several relatively small and cheap activities. We delegated several other activities to agents that run asynchronously and does not impact request processing.
- Metadata service is a distributed cache. It is fast as we store data for active topics in memory.
- We discussed several Temporary Storage design options and mentioned 3-rd party solutions that are fast.
- Sender service splits message delivery into granular tasks, so that each one of them can be optimally performed.
#### Did we design a durable system?
- Yes. Whatever Temporary Storage solution we choose, data will be stored in the redundant manner, when several copies of a message is stored across several machines, and ideally across several data centers. We also retry messages for a period of time to make sure they are delivered to every subscriber at least once.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTg4NDg1OTc4M119
-->