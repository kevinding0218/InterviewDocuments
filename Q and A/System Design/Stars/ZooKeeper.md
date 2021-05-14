### How FrontEnd hosts know which Metadata service host to call.

1.  In the first option we introduce a component responsible for coordination(Configuration Service, e.g. ZooKeper). **This component knows about all the Metadata service hosts, as those hosts constantly send heartbeats to it. Each FrontEnd host asks Configuration service what Metadata service host contains data for a specified hash value.**
    -   Every time we scale out and add more Metadata service hosts, Configuration service becomes aware of the changes and re-maps hash key ranges, this is so called Central registry
    - **Configuration Service (e.g ZooKeeper)**, It would be great if we could somehow monitor cache server health and if something bad happens to the cache server, all service hosts are notified and stop sending any requests to the unavailable cache server. And if a new cache server is added, all service hosts are also notified and start sending requests to it. To implement this approach, we will need a new service, **configuration service, whose purpose is to discover cache hosts and monitor their health.**
	- Each cache server registers itself with the configuration service and sends heartbeats to the configuration service periodically. As long as heartbeats come, server is keep registered in the system. If heartbeats stop coming, the configuration service unregisters a cache server that is no longer alive or inaccessible. And every cache client grabs the list of registered cache servers from the configuration service.

```
		ConfigurationService	
		/					\\\
FrontEnd Host				[A-G][H-N][O-T][U-Z]

```

2.  In the second option we do not use any coordinator. Instead, we make sure that every FrontEnd host can obtain information about all Metadata service hosts. And every FrontEnd host is notified when more Metadata service hosts are added or if any Metadata host died due to a hardware failure.
    -   Gossip protocol: Computer systems typically implement this type of protocol with a form of random “peer selection”: with a given frequency, each machine picks another machine at random and shares data.

```
FrontEnd Host	---			[A-G]
				\			[H-N]
				\			[O-T][U-Z]
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwMTY0NDExMTAsNDQ1MjIwOTA2XX0=
-->