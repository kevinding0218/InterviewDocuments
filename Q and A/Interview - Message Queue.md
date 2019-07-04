- [RabbitMQ](#rabbitmq)
  * [Definition](#definition)
  * [Core concepts](#core-concepts)
  * [Diagram](#diagram)
  * [Exchange Types](#exchange-types)
- [Azure Messaging Services](#azure-messaging-services)
  * [Some terms](#some-terms)
  * [Azure Service Bus](#azure-service-bus)
  * [Event Grids](#event-grids)
  * [Event Hubs](#event-hubs)
  * [Combined services](#combined-services)
  * [Compare those three](#compare-those-three)
- [Kalfka](#kalfka)

## RabbitMQ
### Definition
- A Message broker that implements Advanced Message Queuing Protocal **(AMQP)**
- **AMQP** standardized messaging using **Producers, Broker and Consumers**
- Messaging increase **loose coupling** and **scalability**
### Core concepts
- **Producer** emits a messages to **exchange**
- **Consumer** receives messages from **queue**
- **Binding** connects an exchange with a queue using **binding key**
- **Exchange** compares **routing key** with **binding key**
- Message distribution depends on **exchange type**: fanout, direct, topic and headers
- A special kind of exchange is **Default(nameless) exchange**
### Diagram
- **Basic**
	```
	Producer -->(message/routing key)--> Exchange--> (binding key) --> queue --> Consumer
	```
	- The **producer sends a message to a queue**, however producer never uses a queue directly, instead he **uses an exchange**
	- **Publishing** a message means that the **producer sends a message to the exchange** and that the **exchange forwards the message to the queue**
	- **Consuming** a message means that the **consumer picks up a message from a queue** and **consumes it**
- **Multiply Queues and Consumers**
	- An exchange would make much sense if we only had one queue, however, in a more complex application we can have multiple queues, each linked to its consumer
	- Now when an exchange receives a message it sends it to selected queues. 
	- **An Exchange connects to a queue via a binding and the binding key**
	- To send the message, the producer needs to specify a routing key how an exchange compares the two keys depends on the types of exchange.
### Exchange Types
- **Direct**
	- sends messages to choose whether **routing key equals exact the same as binding key**
- **Topic**
	- sends messages to choose whether **routing key equals partially as binding key**
- **Fanout**
	- sends the messages **to all the queue** it knows about, simply **ignores the routing queue**
- **Header**
	- uses the **message header instead of the routing key**
- **Default/Nameless exchange**
	- compares the **routing key with queue name, not the binding key**(special)
## Azure Messaging Services
### Some terms
- Dead-Lettering: 
	- Azure Service Bus queues and topic subscriptions provide a secondary sub-queue, called a _dead-letter_ queue (DLQ). 
	- The purpose of this queue is to hold messages that could not be delivered to any receiver or processed.
	- These kind of messages will then be removed from dead-lettering queue and inspect separately.
- At-Least-Once Delivery
	- In the event that the application crashes after processing the messages, but before the CompleteAsync request is issued, the message is redelivered after the application becomes available.
	- It is not considered that at the moment message had been processed so the message is re-delivered to the application as soon as it's up and running again when the application restarts, to ensure that this message has been processed at least one time.
- Temporal Buffer
	- Events on an Event Hub are neither acknowledged nor removed by subscribing clients. Instead, events remain intact until a predetermined time, usually 24 hours of access.
	- Event hubs are facilitator of big data in that primary purpose is to move large volume of data from one point to the other. 
	- For example, logging an audit system that consists widely distributed and diverse data structures that need to cenrralized and parsed.
- In-Order Messaging
	- In order to maintain processing order, order needs to be persisted in the order that they arrive, and the order, which is desired to be processed.
### Azure Service Bus
- Service Bus is  a brokered messaging system (it restores the messages in the broker and waits until the consuming body is ready to receive the messages)
	- Reliable asyc message delivery system and Requires Polling
	- Advanced Messaging like FIFO batch or sessions, dead lettering, temporal control, routing and filtering and duplicate detection, it has **At-Least-Once Delivery** feature.
	- In-Order Delivery
- An Azure Service Bus namespace can be considered as a host for queues holding business critical jobs. It allows for the creation message routes that need to travel between applications and application module.
### Event Grids
- Event Grid are event-driven and enables reactive programming, - it's an intelligent event routing service that enables you to react as notifications from the apps and services.
- It internally uses a publish/subscribe model with the publishers omit the events.
	- Publishers will publish the event but they have no expectations about which events are handled
	- Subscribers are the one to decide which events they want to handle, they only get the information for the reaction, they don't get the actual object.
	- For example, if a file is uploaded to the blob storage and event is occurred, now for that event, the event grid will received the notification that the event has occurred, what has happened but it never ever get the actual object which is a file.
	- Event Grid is not a data pipeline and doesn't deliver the actual object that was updated, it supports generating for events that are delivered to an end point.
	- Can be integrated with third-party services
	- No need for constant polling
	- Dynamically scalable
	- Low cost
	- Serverless
	- At-Least-Once Delivery
- Event Grid can be integrated within third-party services as well distributing the events to registered subscribe endpoints.
### Event Hubs
- Azure Event Hubs are big data pipeline
- It facilitates the capture retention and relay telemetry and event stream data.
- Support multiple concurrent sources, they allow the telemetry and event data to be made available to a variety of stream processing infrastructure and analytics services
- It's available either as a data streams or bundled event patches, these event hub provides single solution that enables rapid data retrieval for real-time processing as well as repeated relay of stored raw data.
	- Low latency
	- Capable of receiving and processing millions of events per second
	- At-Least-Once Delivery
### Combined services
- In certain scenarios, we can combine the services to achieve certain critical business objectives.
	- Use Event Grid to respond to events in the other services. 
	- For example of using Event Grid with Event Hubs to migrate data to a data warehouse.[Tutorial]([https://docs.microsoft.com/en-us/azure/event-grid/event-grid-event-hubs-integration](https://docs.microsoft.com/en-us/azure/event-grid/event-grid-event-hubs-integration))
### Compare those three
| Service | Purpose | Type | When to use |
|--|--|--|--|
|Event Grid|Reactive programming|Event distribution (discrete)|React to status changes|
|Event Hubs|Big data pipeline|Event streaming(series)|Telemetry and distributed data streaming|
|Service Bus|High-value enterprise messaging|Message|Order processing and financial transactions|
## Kalfka## Docker
