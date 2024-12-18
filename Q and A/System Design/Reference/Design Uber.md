## Design Uber
- Design Facebook Nearby
- Design Yelp
- Design Pokemon Go

### Uber related Tech
#### Ring-Pop
- Similar as Consistent Hashing, services are in a circle, Load Balancer will randomly assign a service machine to accep the request, then the initial service will check the data and decide which would be the next exact service machine.
- The exact service machine can have the webserver and DB in same machine, so it will be faster
- Unlike Master-Slave mode, while DB and server might not be in one region, DB request connection could be delayed

#### TChannel
- a high-efficient RPC

#### Google S2
- an algorithm for geo location query and storage
- we would focus on this design

### Scenario
#### Feature
- MVP (minimum value product), what minimum featured need can make out this product
- 1st Stage
	- Driver report locations
	- Rider request Uber, match a driver with rider
- 2nd Stage
	- Driver deny / accept a request
	- Driver cancel a matched request
	- Rider cancel a request
	- Driver pick up a rider / start a trip
	- Driver drop off a rider / end a trip
- 3rd Stage (no need bonus)
	- Uber Pool
	- Uber Eat
#### QPS/Storage
- Assuming 200k driver online at same time
	- Driver QPS = 200k / 4 = 50k
		- Driver report locations by every 4 seconds
	- Peak Driver QPS = 50k * 3 = 150k
	- !!! 150K is not a small QPS, must find a write fast storage
	- Rider QPS can be ignored
		- No need to report at any time
		- Must be far less than Driver QPS
	- Storage Estimation
		- If every location need to be recorded, 200k * 86400 / 4 * 100 bytes ~ 0.5T / day
		- if only need to recrod current location info: 200k * 100 bytes = 20M

### Service
- Basically Uber app does 2 things
	- record current car's location (GeoService)
	- dispatch riding request (DispatchService)
	- How would driver get Rider request
		- in the mean time of report location, server can also return matched rider request
	```
	      Report Location every 4s					 Driver: Save Location
	Car      ->						DispatchService		->					GeoService
			<-					  (Trip Table|R>W)	    <-				(Location Table|W>R)
		  Return matched rider						  Rider: Find drivers nearby
		  Request Uber
	User	->	
			<-
		  Return matched driver
	``` 
- What does DispatchService do?
	- Business Logic + Data Storage
#### Service Oriented Architectore

### Storage
#### Schema
- Trip Table
	- id					pk
	- rider_id			fk			User Id
	- driver_id		fk			User Id
	- lat				    float	Latitude
	- lng				float	Longitude
	- status			int		NewRequest/WaitingForDriver/OMWPickup/InTrip/Cancelled/Ended
	- create_at		timestamp
- Location Table
	- driver_id		fk
	- lat					fk
	- lng				fk		
	- updated_at	timestamp	store the last updated time to see if driver is offline
#### How to query current location such as all drivers within 5 miles of user
- Such query will be too slow and complex
```
SELECT * 
FROM LOCATION 
WHERE lat < myLat + delta
	AND lat > myLat - delta
	AND lng < myLng + delta
	AND lng > myLng - delta
```
- Google S2
	- Map a location of latitude and longitude as a 2^64 Integer
	- So if there is two location closed to each other, their mapped value should be closed as well
	- e.g (-30.4, -51) => 10748121851329123
- Geohash
	- 
### Scale
#### Robust
#### Feature
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE0NjQ2MDc4ODRdfQ==
-->