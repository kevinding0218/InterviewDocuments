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
### Deal with large amount of data
- **Steaming**
	- Let's imagine the 100 MB file is received by the service A which transfers it to service B, which, in turn, uses service C to do the actual parsing of the proprietary format.
	- The wrong approach would be for the services A and B to start sending the file to the underlying service only  _after_  they completely received the file from the client:
	- Instead, as soon as they _start_ receiving the file, they should stream it to the underlying service.
![
](https://i.stack.imgur.com/MPEZQ.png)
	- This means that you're not waiting the time it takes to transfer 100 MB three times, but only one time, plus the latency...
- **Latency by location** that services are hosted


## Docker
