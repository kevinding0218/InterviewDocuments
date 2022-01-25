### Broker
- Where Kafka stores the data on a server
#### Cluster
= Contains a group of brokers for storing huge amount of data
### Topic Replication
- Store multiple copies of partition in different machines
- Usually with 3 copies by default, as service failure is a very common scenario in data center, with 2 copies it is considered less, more than 3 would be wasting lots of resource
#### Leader and ISR
- One of the replicate will be leader, the broker which stores that leader partition will fulfill all the write and read requests from producer and consumer. Other broker will only cares about whether it has the right copy of leader partition.
- ISR stands for "in sync replica" 

### Fault Tolenrance
- 
### Difference between topic and Queue
- Topic send data to all consumers
- Topic store data so that it can be read later again (if there is failure in one of the consumers)
- Queue send data to only one consumer
- Queue will delete the data as soon as it is consumed by one of the consumers

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEzMzA2MzA4ODgsLTEyNTEzODIzNzVdfQ
==
-->