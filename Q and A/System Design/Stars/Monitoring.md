### Monitoring (Datadog)
- Monitoring is critical for every system. With regards to distributed message queue, we need to monitor components (or microservices) that we built: fronted, metadata and backend services.
- As well as provide visibility into customer’s experience. In other words, we need to monitor health of our distributed queue system and give customers ability to track state of their queues.
- Each service we built has to emit metrics and write log data. As operators of these services we need to create dashboards for each microservice and setup alerts. And customers of our queue have to be able to create dashboards and set up alerts as well. For this purpose, integration with monitoring system is required.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE0MTM4NTY3MjBdfQ==
-->