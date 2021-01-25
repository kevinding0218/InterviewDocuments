## Consistent Hashing & Design Tiny Url
- Consistent Hashing
- Replica
	- How SQL does replica
	- How NoSQL does replica
- Design a tiny url
	- 4S
	- No Hire/Weak Hire/Hire/Strong Hire
- What happened when visit www.google.com

### Consistent Hashing
#### What is consistent hashing
- when base mod number changes, data after mod changed a lot
- server pressure when data migration during hashing
- data might change during migration, consistency is not guranteed
- 3 machines: DB1 --> [0,3,6,9], DB2 --> [1,4,7,10], DB3 --> [2,5,8,11]
- 4 machines: DB1 --> [0,4,8], DB2 --> [1,5,9], DB3 --> [2,6,10], DB4 --> [3,7,11]
	- DB1: only 0 remain there, [3,6,9] 3 data migrated somewhere else
	- DB2: only 1 remain there, [4,7,10] 3 data migrated somewhere else
	- DB3: only 2 remain there, [6,10] 2 data migrated somewhere else
	- total of 9/12 = 75% data migrated
#### Horizontal
##### consider as a circle and data can be distributed into 360 pieces/degress in the circle
- 2 machines: DB1 --> [0, 179], DB2 --> [180, 359]
- 3 machines: DB1 --> [0, 119], DB2 --> [240, 359], DB3 --> [120,239]
	- DB1: [0,119] data remain there, [120,179] 60 data migrated to DB3
	- DB2: [240, 359] data remain there, [180, 239] 60 data migrated to DB3
	- total of 120/360 = 33% data migrated
- 4 machines: DB1 --> [0, 79], DB2 --> [240, 359], DB3 --> [160, 239], DB4 --> [80, 159]
- find max closet two section and divided into 3 sections, move part of data from both into new section
- other section might remain same
	- DB1: [0,79] data remain there, [80, 110] 40 data migrated to DB4
	- DB2: [240, 359] all data remain there
	- DB3: [160, 239] data remain there, [120, 159] 40 data migrated to DB4
	- total of 80/360 = 22% data migrated
- disadvantage: 
	- data distribution not shared equaly, e.g DB2 remained same for 120 data, while other 3 DB shared 60 each
	- pressure on those 2 servers might got heavy during migration, e.g: suppose we have 100 server, only those 2 servers got higher pressure, while the other 98 server didn't shared the pressure
##### more practical way
- still consider data storage as a circle
- instead of thinking 360 pieces, consider as 2^64 - 1 pieces,  all big data will be allowed
- each machine and data can be considered as a point in the circle by their hashing value
- import the concept of micro shards/virtual nodes
	- one physical machine refers 1000 micro shards/virtual nodes
- each virtual node refers to a point in the circle
- each time when adding a new machine, placing 1000 virtual nodes randomly in the circle
- when adding a new data
	- calculate the hashing key --> get the number of [0, 2^64]
	- clockwise find the first virtual nodes
	- the physical machine which represents the virtual node will be the final data storage
	- use TreeMap/RBT --> O(logN) finds minimum value that's greater than X
- when adding a new machine
	- 1000 virtual nodes clockwise asking data of its next virtual node
- leetcode consistent-hashing

### Replica
#### difference between backup and replica
- backup: schedule job
	- normaly schedule periodically
	- when data lost, usually can only recover at certain time stamp
	- not used as online data service, not for distribute data reading
- replicate: real-time executing
	- store multiply copies
	- when data lost, can restore through other copy
	- used as online data service, used to distribute data reading
- Why still needs backup
	- money cost
#### SQL database Replica
- usually self-contained Master -Slave replica
	- Master = write, Slave = read
	- Slave sync data from master
- write ahead log
	- all operations in SQL database will have a record in the form of log, e.g:
	- data A updated from C to D at time B
	- when Slave get activated, will inform Master it's ready
	- everytime when operation happened in Master will inform Slave to read log
	- so data in Slave will have a "delay"
- what if master is down?
	- promote one Slave to be Master, which accept read + write
	- might cause data lost and inconsistency
#### NoSQL database Replica
- usually store data "clockwise" as 3 virtual nodes as consistent hashing circle
#### SQL vs NoSQL in Replica
- SQL
	- "self-contained" is Master-Slave
	- "manual" can also be storing 3 copies in consistent hashing in circle
- NoSQL
	- "self-contained" is storing 3 copies in consistent hashing in circle
	- "manual", no need! NoSQL is for saving between sharding and replica

### Design Tiny URL
#### Scenario
- Requirement
	- generate a Short URL based on Long URL
		- http://www.jiuzhang.com => http://bit.ly/1UIoQB6
	- Restore Long URL based on Short URL
		- http://bit.ly/1UIoQB6 => http://www.jiuzhang.com
- Daily Usage (QPS + Storage)
	- Ask interviewer daily active user (DAU)
		- nearly 100M
	- Estimate QPS for generating one tiny URL
		- suppose each user post 0.1 twitter that includes a tiney URL
		- Average Write QPS = 100M * 0.1/86400 ~ 100
		- Peak Write QPS = 100 * 2 = 200
	- Estimate QPS for clicking on one tiny URL
		- suppose each user click on 1 tiny URL
		- Average Read QPS = 100M * 1 / 86400 ~ 1k
		- Peak Read QPS = 2k => a SSD supported MySQL can deal with it
	- Estimate Storage for new URL daily
		- 100M * 0.1 ~ 10M URL daily
		- average storage for a tiny url like 100 bytes, total of 1G
		- 1T hard disk be used for 3 years
#### Service
- TinyURL only needs one UrlService
- Function Design
	- UrlService.encode(long_url)
	- UrlService.decode(short_url)
- Endpoint Design
	- Get /<short_url>
		- Return a Http redirect response
	- Post /data/shorten/
		- Data body = {url: http://xxxxxx }
		- Return short url
#### Storage
- How to store and visit data
	- Select storage structure (SQL or NoSQL)
		- Does it need to support Transaction?
			- NoSQL doesn't support Transaction
			- For tiny url, no need (NoSQL + 1)
		- Does it need a lot SQL Query?
			- NoSQL doesn't enrich in Query
			- Some NoSQL support simple SQL Query
			- For tiny url, no need (NoSQL + 1)
		- Save time in infrustructure?
			- Most web framework has good compatibility with SQL Database
			- Less code when use SQL vs NoSQL
			- For tiny url, no need for complex code (NoSQL + 1)
		- Does it need Sequential ID?
			- SQL supports auto-increment Sequential ID, like 1,2,3,4,5 ...
			- NoSQL ID is not sequential
			- For tiny url, depends on your algo
		- How much expectation in QPS?
			- NoSQL has better performance
			- 2k is not a high expectation, can use Cache for reading, much less write (SQL + 1)
		- How much expectation in scalability/
			- SQL needs developer to scale (consistent hashing with horizontal virtual node)
			- NoSQL does these for you
			- For tiny url, not high expectation
	- Schema data list in detail

#### Algorithm
1. Hashing function (**No** because of conflict)
	- get long URL last 6 digets of MD5
	- advantage: fast
	- disadvantage: hard to design a non-conflict hashing algorithm
2. Randomly generate a short URL + remove replicate in database
	- Generate a 6 diget short url randomly, if not has been used, bind it to long url
	```
	public String longToShort(String url) {
		while(true) {
			String shortURL = randomShortURL();
			if (!database.filter(shortURL=shortURL).existed()) {
				database.create(shortURL=shortURL, longURL = url);
				return shortURL;
			}
		}
	}
	```
	-	advantage: simple implementation
	-	disadvantage: with more data coming, speed become slower
3. Convert to Base62
- Base 62
	- consider a 62-digit number (0-9, a-z, A-Z) for the 6 digit short url
	- every short url refers to an Integer
	- this integer can be mapping as primary key in database - Sequential ID
	```
	int shortURLtoID(String shortURL) {
		int id = 0;
		for(int i = 0; i < shortURL.length(); ++i) {
			id = id * 62 + toBase62(shortURL.charAt(i));
		}
		return id;
	}
	String idToShortURL(int id) {
		String chars = "0123456789abc..xyzABC..XYZ";
		String short_url = "";
		while (id > 0){
			short_url = chars.charAt(id%62) + short_url;
			id = id/62;
		}
		while (short_url.length() < 6){
			short_url = "0" + short_url;
		}
		return short_url;
	}
	```
- how many URL can the 6 digit short url represent?
	- 5 digits = 62 ^ 5 = 0.9B
	- 6 digits = 62 ^ 6 = 57B
	- 7 digits = 62 ^ 7 = 3.5T
- advantage: better performance
- disadvantage: rely to auto-increment global ID (need a single database table for auto-increment id)

### database design
1. random generator + check existence
- need query Short url based on Long url, also need to query Long url based on Short url
- if choose SQL database, table will 
|shortKey| longUrl |
|--|--|
| a0B4Lb | http://www.facebook.com |
| Df523P| http://www.google.com |
	- need to indexing on both shortKey and longUrl
- if choose NoSQL database, but will need two tables
	- 1st table, query Short based on Long
		- row_key=longURL, column_key=shortURL, value=null or timestamp
	- 2nd table, query Long based on Short
		- row_key=shortURL, column_key=longURL, value=null or timestamp
2. base62
- because this will be used of sequential ID, can only choose SQL database
- shortURL doesn't need to store in table because it can be calculated based on id
|id| longUrl(index=true |
|--|--|
| 1| http://www.facebook.com |
| 2| http://www.google.com |

### Interviewr: how to reduce response time
- Scale
1. Read more than write
	- use cache aside to improve the speed
	- cache needs to store two types of data
		- long to short (for usage when generating short url)
		- short to long (for usage when query short url)
	- workflow:
		1. get http://bit.ly/1Us49DS
		2. request sends to web server, check this short url in Memcached and if found, return long url
		3. if not found in Memcached, check this short url in SQL database and return
		4. server return to user with http 301 redirect
2. Geo info usage
	- improve service visit time
		- use different web servers in different districts
		- use DNS to decode users across regions to different servers
		- e.g if user's long url is an asia website, your server is in U.S, putting it in U.S server meaning user's request has to decode the url in U.S server then redirect to asia
	- improve data visit time
		- use Centralized SQL database + Distributed Memcached
		- One SQL database with many Memcache, distributed across regions
		USA user -> DNS -> Web Server <--> Memcached
                                             Shared DB (Centralized SQL DB)
        CN user   -> DNS -> Web Server <--> Memcached
        - User visit a webserver would be much slower than webserver visit another webserver, because of less redirection
    
### Interviewer: what if one SQL database is out of capacity? (Sharding)
- When would we need more database server?
	- Cache resource is out of capacity
	- Write operation become much more, too busy
	- More requestes cannot get resolved through Cache
- What can we improve by adding more database server
	- solve the out of capacity storage (Storage)
	- solve the too busy (QPS)
	- What is main issue of TinyURL
		- too busy (like mallicious attack)
- Vertical Sharding
	- Distribute many data tables to many machines
		- Not applicable for TinyURL
- Horizontal Sharding
	- How to make Sharding Key?
		- Solution 1:save as 2 copies
			- when reading Short to get Long, use Short as Sharding Key
			- when reading Long to get Short, use Long as Sharding Key
			- Long could have more than one Short, but not the other way around
		- Solution 2: Extend short key
 			- If at beginning, short key is 6 digit, adding a prefix diget in short key
	 			- AB1234 -> 0AB1234
	 			- another way is to pick first digit 'A' as sharding key
 			- the prefix value was getting from Hash(long_url)%62
 			- devided the circle into 62 sections, each machine would be responsible for one section
 			- this way we don't need broadcast, either find short2long or long2short can use sharding key
	- How to query Long Url if using ID?
	- How to query ID if using Long URL?
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTExMDI1NzQ2MzksMTE3NzI2MjYwOCw1Nz
U2NjgyNTAsMTc1OTgzNDc3NywtMjAyMjIxNTIwMCwtMTI5NjEx
MDIwOSwyNDk2ODAzNDgsOTIyMjAxMzEyLC0zMjg2MzM4OTksLT
E3MzUyNTA1NSwyNTYzMTc0NDAsNjIyNDIwNDEsLTUzNTI1NjA2
LC0yMTQ2MTYwMDU5LC05MTcyNTYzMDcsNzM2NzQyMjgsLTEzMT
U4MDAwOTUsLTE1NDcyNjgyMjIsNzk0MTI1MjYzLC0yOTg1NjM5
MzNdfQ==
-->