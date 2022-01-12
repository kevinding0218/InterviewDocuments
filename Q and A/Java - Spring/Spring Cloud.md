### Why need a Spring Cloud Config Server
- Externalized (property files)
- Environment specific (Spring profiles)
- Consistent (All microservice instances needs to ensure receiving latest config)
- Version history (meaningless if not consistent, you might see a change in the config but don't know if that change deployed to all instance)
- Real-time management (when multiple different microservice listens to same source of truth of config server)

#### Local Spring Cloud Access
> http://localhost:8888/application/default
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3MTQzNzg5MTgsLTIwNjE4Mjc1NzJdfQ
==
-->