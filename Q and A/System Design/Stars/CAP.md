**The CAP Theorem for distributed computing**  was published by Eric Brewer. This states that it is not possible for a distributed computer system to simultaneously provide all three of the following guarantees:

1.  _Consistency_  (all nodes see the same data even at the same time with concurrent updates )
2.  _Availability_  (a guarantee that every request receives a response about whether it was successful or failed)
3.  _Partition tolerance_  (the system continues to operate despite arbitrary message loss or failure of part of the system)

The CAP acronym corresponds to these three guarantees. This theorem has created the base for modern distributed computing approaches. Worlds most high volume traffic companies (e.g. Amazon, Google, Facebook) use this as basis for deciding their application architecture. It's important to understand that only two of these three conditions can be guaranteed to be met by a system.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjYwNDg1ODMyXX0=
-->