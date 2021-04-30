##### how FrontEnd hosts know which Metadata service host to call.

1.  In the first option we introduce a component responsible for coordination(Configuration Service, e.g. ZooKeper). This component knows about all the Metadata service hosts, as those hosts constantly send heartbeats to it. Each FrontEnd host asks Configuration service what Metadata service host contains data for a specified hash value.
    -   Every time we scale out and add more Metadata service hosts, Configuration service becomes aware of the changes and re-maps hash key ranges, this is so called Central registry

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
eyJoaXN0b3J5IjpbLTkzODIwNjQ1M119
-->