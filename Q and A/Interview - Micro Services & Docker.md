- [MicroServices](#microservices)
  * [**Definition**](#--definition--)
  * [**Advantage**](#--advantage--)
  * [**Disadvantage**](#--disadvantage--)
  * [Micro service vs API](#micro-service-vs-api)
  * [[Challenges](https://docs.microsoft.com/en-us/dotnet/standard/microservices-architecture/architect-microservice-container-applications/distributed-data-management)](#-challenges--https---docsmicrosoftcom-en-us-dotnet-standard-microservices-architecture-architect-microservice-container-applications-distributed-data-management-)
    + [How to define the boundaries of each microservice](#how-to-define-the-boundaries-of-each-microservice)
    + [How to create queries that retrieve data from several microservices](#how-to-create-queries-that-retrieve-data-from-several-microservices)
  * [Deal with large amount of data](#deal-with-large-amount-of-data)
- [Docker](#docker)

## MicroServices
### **Definition**
- microservices as an **architectural pattern** for creating applications. Under this pattern, applications are structured as **a collection of loosely coupled services**.
- This is distinct from **traditional applications** that are structured as **single self-contained artifacts**
### **Advantage**
- It is _so_ much **easier** to **develop**, **integrate**, and **maintain** applications since **individual functionalities are treated separately**, 
	- allowing you to build applications step-by-step or Allow small independent teams that take ownership of their services.
- **independently deployable and scalable, be more agile**
	- If a service needs changed, you just change that one, none of its kin. 
	- If you want to try a new framework or language, just make a drop-in replacement for that one service.
	- If you suddenly need 100x capacity, spin up some new machines with that service to handle that influx.
### **Disadvantage**
- **Developing** distributed systems can be complex. Since everything is now an independent service, you have to carefully handle requests traveling between your modules. 
	- In one such scenario, developers may be forced to **write extra code to avoid disruption**.
	- Over time, complications will arise when **remote calls experience latency**.
- **Testing** a microservices-based application can be cumbersome.
	- With microservices, each dependent service needs to be confirmed before testing can occur.
	- With traditional approach, we would just need to launch our WAR on an application server and ensure its connectivity with the underlying database.
- **Deploying** microservices can be complex.
	- They may need coordination among multiple services
	- The best way to deploy microservices-based applications is within **containers**, which are _complete virtual operating system environments_ that provide processes with isolation and dedicated access to underlying hardware resources.
### Micro service vs API
- API is usually a _portion_ of a microservice and focus on how to expose micro service
- API serves as a contract for interactions within the micro service
### [Challenges](https://docs.microsoft.com/en-us/dotnet/standard/microservices-architecture/architect-microservice-container-applications/distributed-data-management)
#### How to define the boundaries of each microservice
- Focus on the application's logical domain models and related data. 
- Try to identify decoupled islands of data and different contexts within the same application. 
- Each service has a single responsibility.
- There are no chatty calls between services.
- Each service is small enough that it can be built by a small team working independently
- Your service boundaries will not create problems with data consistency or integrity.
#### How to create queries that retrieve data from several microservices
- API Gateway
- CQRS
- SignalR
#### Communication between Micro Service
- **Synchronous communication**:
	- *Client -> API Gateway  -> Service 1 -> Service 2 - Service 1 -> API Gateway -> Client*
		- **Pros**: Easy and realtime
		- **Cons**: Service availability (Service 2) and Response time latency
- **Asynchronous communication**: 
	- *Client -> API Gateway -> Service 1 -> Q1 (consumed by Service A) = Q2 (consumed by Service B) = Q3 (consumed by Service C)*
		- **Pros**: 
			- Faster APIs (as it's not sync and doesn't need to wait for response)
			- Decoupled Services (Service A, B & C are independent)
			- Works even when services are down
			- No need for service discovery (Service 1 doesn't need to know the protocol of Service A, B or C, all it has to know is the address of distributed queue/queue or topic name)
		- **Cons**: 
			- Complex design
			- Process latency (if queue is overloaded and service is not scalable, expectation of response are not real-time)
			- Monitoring costs
### Deal with large amount of data
- **Steaming**
	- Let's imagine the 100 MB file is received by the service A which transfers it to service B, which, in turn, uses service C to do the actual parsing of the proprietary format.
	- The wrong approach would be for the services A and B to start sending the file to the underlying service only  _after_  they completely received the file from the client:
	- Instead, as soon as they _start_ receiving the file, they should stream it to the underlying service.
![
](https://i.stack.imgur.com/MPEZQ.png)
	- This means that you're not waiting the time it takes to transfer 100 MB three times, but only one time, plus the latency...
- **Latency by location** that services are hosted
## gRPC
### Disadvantage of REST Api
- Need to think about data model
	- JSON
	- XML
	- Something Binary?
- Need to think about the endpoint
	- GET /api/v1/user/123/post/456
- Need to think about how to invoke it and handle errors
- Need to think about efficiency of the API
	- How much data do I get out of one call?
	- Too much data or too little data
- How about latency?
- How about scalability to 1000s of clients?
- How about load balancing?
- How about interoperability with many language?
- How about authentication, monitoring and logging?
### What's gRPC?
- at higher level, it allows you to define REQUEST and RESPONSE for RPC(Remote Procedure Calls) and handles all the rest for you
- on top of it, it's modern, fast and efficient, build on top of HTTP/2, low latency, supports streaming, language independent, and make it super easy to plug in authentication, load balancing, logging and monitoring
### What's an RPC?
- An RPC is a Remote Procedure Call.
- In your client code, it looks like you're just calling a function directly on the server
	```
	// Client Code (any language)
	{code}
	...
	server.CreateUser(user)
	...
	{code}

	// Server Code (any language)
	// function creating users
	def CreateUser(User user) {...}
	```
### Protocol Buffers
- extensible mechanism for serializing structured data and defined RPC as contract, think XML, but smaller, faster, and simpler.
- Code can be generated for pretty much any language
- Data is binary and efficiently serialized (small payloads)
- Very convenient for transporting a lot of data
	```
	//example.proto
	syntax = "proto3";
	message Greeting {
		string first_name = 1;
	}
	message GreetRequest {
		Greeting greeting = 1;
	}
	message GreetResponse {
		string result = 1;
	}
	service GreetService {
		rpc Greet(GreetRequest) returns (GreetResponse) {}
	}
	```


## Docker
### How I used to work without Docker
- when docker and container are not such popular, we had issue that once we finish some application and want to maintain this, in order to make it maintainable, I have to create a snapshot of the virtual machine, and that snapshot contains all my current infrastructure, all applications had to be part of whole system and each application had to be exact version because of dependency and for one day, it bumps and had some breaking changes, once you upgrade this, your application will be broken because inside we would have some errors that we need to resolve. 
- The typical way is to create the whole snapshot of the virtual machine and then when you want to change something, you had to change the whole virtual machine which was kind of slow because virtual machine is slow and not efficient.
- Another scenario is one had to install everything every dependency with proper version on his local machine, sometime it led issue that things works on one machine but not another.
### Why using Docker
- Docker containers are isolated, but share OS and where appropriate, bins/libraries, result is fast deployment, much less overhead, easier migration, faster restart.
- You will use your system kernel and you'll be able to very quickly deploy your applications reusing your local operating system, and these applications will think like they're in their own environment.
### Dockerfile
- Base layer: Get the asp.net core run time
```DOCKER
FROM mcr.microsoft.com/dotnet/core/aspnet:2.2
```
- Provide published files with `COPY` to source destination
```DOCKER
# COPY  FROM bin/docker to here
COPY ./bin/docker .
```
- Specify Entry Point
```DOCKER
ENTRYPOINT dotnet NextDoor.Services.Identity.dll
```
### Local Deployment (Run-time)
#### First Try
- publish our application into bin/docker
```BASH
dotnet publish -c Release -o bin/docker
``` 
- Build Docker Image
```BASH
docker build -t nextdoor.services.identity .
```
- Check created images
```BASH
docker images
```
- Run the image
```BASH
docker run nextdoor.services.identity (or ImageID)
```
#### Second Try
- Create docker settings file for RabbitMQ config
	- Create a new file `appsettings.docker.json`
	- Copy settings of `RabbitMQ` and change hostnames to be **host.docker.internal**
- Declare environment variables in DockerFile
```DOCKER
# Load appsettings.docker.json
ENV ASPNETCORE_ENVIRONMENT docker
# You can use same port for different containers because each container had their own IP address 
ENV ASPNETCORE_URLS http://*:5000
# Previously if you don't use EXPOSE, you couldn't reach your container even within the private docker network from the other container, but now don't have to do it since your container will be available in a private local network
#EXPOSE 5000
```
- Re-build dotnet project since we add a docker setting
```
dotnet publish -c Release -o bin/docker
```
- Rebuild our image
```DOCKER
docker build -t nextdoor.services.identity .
```
- Re-run the docker image and specify 5010 as public port and 5000 as private port, --rm as remove container once it stops
```DOCKER
docker run -p 5010:5000 --name identity --rm nextdoor.services.identity:latest
```
#### Kill the container
- Stop the running container
```
docker ps
docker stop (Name of the Container` or first few characters of the container ID)
```
- Remove the container
```
docker rm (Name of the Container` or first few characters of the container ID)
```
### Docker MultiStage File (Release-time)
- To create a pipeline like a build service on your own using the docker, to define different steps to do different things.
	- First step is that I want to use this SDK image and call it `build`, and I want to put my files into this working directory called app, I will copy all these files in a discount directory which is a src , and run `dotnet publish` command with the releass flag.
	- Second step is to use aspnetcore runtime and in `COPY` command `from`, within which I can specify that I want to use the output from my previous step which is `build` in above case,  And because I have the `out` working directory of published file in my last step, so I can  copy things from that `out` directory.
```DOCKER
FROM mcr.microsoft.com/dotnet/core/sdk AS build
WORKDIR /app
COPY . .
RUN dotnet publish src/NextDoor.Services.Identity -c Release -o out

FROM mcr.microsoft.com/dotnet/core/aspnet:2.2
WORKDIR /app
COPY --from=build /app/src/NextDoor.Services.Identity/out .
ENV ASPNETCORE_URLS http://*:5000
ENV ASPNETCORE_ENVIRONMENT docker
ENTRYPOINT dotnet NextDoor.Services.Identity.dll
```
### Include container in specific network
- Suppose we've already had a container named `nextdoor-network` which holds up some services like redis/rabbitMQ/mongo
```
docker run -p 5010:5000 --name identity --rm -it --network nextdoor-network nextdoor.services.identity:latest
```
- We can inspect our network's container and see our application is included there
```
docker inspect nextdoor-network
```
### Send image to dockerhub
- Create a new repository in dockerhub
- run command
```
docker tag {imageName} {username}/{repository_name}
docker push {username}/{repository_name}
```
### docker compose (yml)
- a tool which you can define the images in container and run those services in single command
### docker start vs docker run
- **docker run**: create a new container of an image, and execute the container. You can create N clones of the same image. The command is:  `docker run IMAGE_ID`  **and not**  `docker run CONTAINER_ID`
- **docker start**: Launch a container previously stopped. For example, if you had stopped a database with the command  `docker stop CONTAINER_ID`, you can relaunch the same container with the command  `docker start CONTAINER_ID`, and the data and settings will be the same.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1ODUxMzk4MjUsLTEwNDA0Njk0OCwtMT
gzODM4MTQyOSwxMjI5MTg5NjgwLC05OTAzNzQwNzYsMTI3MjYx
OTU2NCwzOTI3NjgyOSwxNDY5Nzg5NDcxLDYxODg2MjIxNiw1Mj
k5MTQzMjAsLTEwMjcxODczMjIsNDMzNTM5MzQyLDgxNTMyNzk3
OSwxMDU0MzkyNDUwLC0xNTU0ODA4MjE1XX0=
-->