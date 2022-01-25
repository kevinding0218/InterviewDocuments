### Broker
- Where Kafka stores the data on a server
#### Cluster
= Contains a group of brokers for storing huge amount of data

### Difference between topic and Queue
- Topic send data to all consumers
- Topic store data so that it can be read later again (if there is failure in one of the consumers)
- Queue send data to only one consumer
- Queue will delete the data as soon as it is consumed by one of the consumers

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTY4OTI5NDMwOSwtMTI1MTM4MjM3NV19
-->