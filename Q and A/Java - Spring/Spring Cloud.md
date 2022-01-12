### Why need a Spring Cloud Config Server
- Externalized (property files)
- Environment specific (Spring profiles)
- Consistent (All microservice instances needs to ensure receiving latest config)
- Version history (meaningless if not consistent, you might see a change in the config but don't know if that change deployed to all instance)
- Real-time management

#### Local Spring Cloud Access
> http://localhost:8888/application/default
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTgwOTMxNDcyMSwtMjA2MTgyNzU3Ml19
-->