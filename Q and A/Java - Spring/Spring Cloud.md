### Why need a Spring Cloud Config Server
- Externalized (property files)
- Environment specific (Spring profiles)
- Consistent (All microservice instances needs to ensure receiving latest config)
- Version history (meaningless if not consistent, you might see a change in the config but don't know if that change deployed to all instance)
- Real-time management (when multiple different microservice listens to same source of truth of config server)

#### Local Spring Cloud Access
> http://localhost:8888/application/default

#### real time refresh without restarting service
> spring-boot-starter-actuator
- Provides an endpoint to tell current service that it's time to fetch all latest config again from config server
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5MDYyMjQ2NzQsLTE3MTQzNzg5MTgsLT
IwNjE4Mjc1NzJdfQ==
-->