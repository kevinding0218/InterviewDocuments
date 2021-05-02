### Requirements/Goals
#### Functional
- Having a service to generate a shorter and unique url link by given a URL input - `UrlService.encode(long_url)`
- Redirect user to original link when client tries to access with provided shorten url - `UrlService.decode(short_url)`
- **Discussion**: Should shorten url link be expire or does it have a Time To Live? Who would make the decision? User or Us?
- **Discussion**: Users should optionally be able to pick a custom short link for their URL. `URlService.createCustomTinyURL(String longUrl, String customName);`
#### Non Functional
- High Availablity: if our service is down, all the URL redirections will start failing.
- High Performant: with minimal latency < 200ms
- Discussion High Secure: not predictable
- Discussion: Expose to external service or just internal application usage (SOAP & REST)
- **Daily Usage** (QPS + Storage)
	- Ask interviewer daily active user (DAU) or discuss that we assuming this would be high QPS
		- nearly 100M
	- Estimate QPS for **generating one tiny URL**
		- suppose each user post 0.1 twitter that includes a tiney URL
		- Average Write QPS = 100M * 0.1/86400 ~ 100
		- Peak Write QPS = 100 * 2 = 200
	- Estimate QPS for **clicking on one tiny URL**
		- suppose each user click on 1 tiny URL
		- Average Read QPS = 100M * 1 / 86400 ~ 1k
		- Peak Read QPS = 2k => a SSD supported MySQL can deal with it
	- Estimate **Storage for new URL daily**
		- 100M * 0.1 ~ 10M URL daily
		- average storage for a tiny url like 100 bytes, total of 1G
		- 1T hard disk be used for 3 years
#### Storage
- How to store and visit data
	- Select storage structure (SQL or NoSQL)
		- **Does it need to support Transaction?**
			- NoSQL doesn't support Transaction
			- For tiny url, **no need** (NoSQL + 1)
		- **Does it need a lot SQL Query?**
			- NoSQL doesn't enrich in Query
			- Some NoSQL support simple SQL Query
			- For tiny url, **no need** (NoSQL + 1)
		- **Save time in infrustructure?**
			- Most web framework has good compatibility with SQL Database
			- Less code when use SQL vs NoSQL
			- For tiny url, **no need** for complex code (NoSQL + 1)
		- **Does it need Sequential ID?**
			- SQL supports auto-increment Sequential ID, like 1,2,3,4,5 ...
			- NoSQL ID is not sequential
			- For tiny url, depends on your algo
		- **How much expectation in QPS?**
			- NoSQL has better performance
			- 2k is not a high expectation, can use Cache for reading, much less write (SQL + 1)
		- How much expectation in **scalability**?
			- SQL needs developer to scale (consistent hashing with horizontal virtual node)
			- NoSQL does these for you
			- For tiny url, not high expectation
	- Schema data list in detail

### Algorithm
#### Hashing function (**No** because of conflict)
- get long URL last 6 digets of MD5
- Pros: fast
- Cons: hard to design a **non-conflict hashing algorithm**
#### Randomly generate a short URL + remove replicate in database
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
- Pros: simple implementation
- Cons: with more data coming, speed become slower
#### Convert to Base62
- consider a **62-digit number (0-9, a-z, A-Z) for the 6 digit short url**
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
- **how many URL can the 6 digit short url represent?**
	- 5 digits = 62 ^ 5 = 0.9B
	- 6 digits = 62 ^ 6 = 57B
	- 7 digits = 62 ^ 7 = 3.5T
- Pros: better performance
- Cons: rely to auto-increment global ID (need a single database table for auto-increment id)

### Database design
#### random generator + check existence
- need query Short url based on Long url, also need to query Long url based on Short url
- if choose SQL database, table will 
- 
|shortKey| longUrl |
|--|--|
| a0B4Lb | http://www.facebook.com |
| Df523P| http://www.google.com |
	- need to **indexing on both shortKey and longUrl**
- if choose NoSQL database, but will need two tables
	- 1st table, query Short based on Long
		- row_key=longURL, column_key=shortURL, value=null or timestamp
	- 2nd table, query Long based on Short
		- row_key=shortURL, column_key=longURL, value=null or timestamp
#### base62
- because this will be used of sequential ID, can only choose SQL database
- shortURL doesn't need to store in table because it can be calculated based on id
|id| longUrl(index=true |
|--|--|
| 1| http://www.facebook.com |
| 2| http://www.google.com |

### Interviewr: how to reduce response time
#### Scale
1. **Read more than write**
	- use **cache** aside to improve the speed
	- cache needs to store two types of data
		- long to short (for usage when generating short url)
		- short to long (for usage when query short url)
	- **workflow**:
		1. get http://bit.ly/1Us49DS
		2. request sends to web server, check this short url in Memcached and if found, return long url
		3. if not found in Memcached, check this short url in SQL database and return
		4. server return to user with http 301 redirect
2. **Geo info usage**
	- improve service visit time
		- use different web servers in different districts
		- use DNS to decode users across regions to different servers
		- **e.g if user's long url is an asia website, your server is in U.S, putting it in U.S server meaning user's request has to decode the url in U.S server then redirect to asia**
	- improve data visit time
		- use **Centralized SQL database + Distributed Memcached**
		- One SQL database with many Memcache, distributed across regions
		```
		USA user -> DNS -> Web Server <--> Memcached
                                             Shared DB (Centralized SQL DB)
        CN user   -> DNS -> Web Server <--> Memcached
        ```
        - User visit a webserver would be much slower than webserver visit another webserver, because of less redirection
    
### Interviewer: what if one SQL database is out of capacity? (Sharding)
#### When would we need more database serve**r?
- Cache resource is out of capacity
- Write operation become much more, too busy
- More requestes cannot get resolved through Cache
#### What can we improve by adding more database server
- solve the out of capacity storage (Storage)
- solve the too busy (QPS)
- What is main issue of TinyURL
	- too busy (like mallicious attack)
#### Vertical Sharding
- Table has too many column
	-  we can separate partial columns of that table into a different table
#### Horizontal Sharding
- Table has too many rows
	- we can separate partial rows of data into another database server/machine
- What does Sharding Key do?
	- Determine which database server/machine it should select to retrieve the data, when data is distributed sotred
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
			```
	 USA user -> DNS -> Web Server <--> Memcached
                  Shared DB (1, 2, ..., 61)
    CN user   -> DNS -> Web Server <--> Memcached
       ```
- How to query Long Url if using ID?
- How to query ID if using Long URL?
### Scale - Multi Region
- we can continue improving the performance in **communication between web server and database**
- Communication between Centralized DB set and cross region Web Server is slow
	- e.g Server in India to hit database in U.S
- Think about user's experience and habits
	- **When India user hits the URL, will be distributed to Indian Server**
	- Because usually Indian user would visit Indian website
	- So we can have **Sharding key based on website GEO region info**
		- How to get the region info? 
			- use machine learning to train to find out and grouping top 10k visited website
			- consider rest of the website based on request region as they are not high demand
	- What if Indian user wants to hit U.S website?
		- Let Indian server hit U.S data
		- The major requirement is for **letting India to visit Indian website**, system improvement is based on
		solving for major requirements
		```
		USA user -> DNS -> Web Server <--> Memcached
	                        DB USA -> http://tiny.url/0AB1234
	                        DB CN -> http://tiny.url/1AB1234
	    CN user   -> DNS -> Web Server <--> Memcached
	```

### Interviewer: How to provide custom url?
- custom url example
	- http://tiny.url/google/ => http://www.google.com
	- http://tiny.url/systemdesign/ => http://www.jiuzhang.com/course/2/
- Create a new table to store custom URL (e.g: CustomURLTable)
- 
|custom_url| long_url(index=true) |
|--|--|
| gg | http://www.google.com |
| fb| http://www.facebook.com |
- Query for long url
	- first query if already existed in CustomURLTable
	- if not, then query URLTable
- Create Short url based on Long url
	- first query if already existed in CustomURLTable
	- If not, query in URLTable and insert if needed
- Create Custom Link Url
	- query if already exisetd in URLTable
	- if not, query in CustomURLTable and insert if needed
- Wrong idea
	- Insert a new column in URLTable as most of the data might be null

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyMDIzMTAyOTcsMTkyNzU3NjAxOCwyMD
Q2ODg2MjEsMTExODE1ODIwMSwtMjA4ODc0NjYxMl19
-->