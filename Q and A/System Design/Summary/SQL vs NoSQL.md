### SQL
- SQL stores data in tables where each row represents an entity and each column represents a data point about that entity, each record conforms to a fixed schema, meaning the columns must be decided and chosen before data entry and each row must have data for each column
### NoSQL
- In NoSQL, schemas are dynamic. Columns can be added on the fly and each ‘row’ (or equivalent) doesn’t have to contain data for each ‘column
- **Key-Value** Stores: Data is stored in an array of key-value pairs. The ‘key’ is an attribute name which is linked to a ‘value’. e.g: **Redis**
- **Document Databases**: data is stored in documents (instead of rows and columns in a table) and these documents are grouped together in collections, Each document can have an entirely different structure, e.g: **Couchbase and MongoDB**
- **Wide-Column Databases**: Unlike relational databases, we don’t need to know all the columns up front and each row doesn’t have to have the same number of columns, e.g: **Cassandra**
- **Graph Databases**: These databases are used to store data whose relations are best represented in a graph
### Difference
- Scalability
	- In most common situations, SQL databases are vertically scalable, i.e., by increasing the horsepower (higher Memory, CPU, etc.) of the hardware, which can get very expensive
	- NoSQL databases are horizontally scalable, meaning we can add more servers easily in our NoSQL database infrastructure to handle a lot of traffic. Any cheap commodity hardware or cloud instances can host NoSQL databases, thus making it a lot more cost-effective than vertical scaling.
	- 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyMzIxOTg3OTFdfQ==
-->