### SQL
- SQL stores data in tables where each row represents an entity and each column represents a data point about that entity, each record conforms to a fixed schema, meaning the columns must be decided and chosen before data entry and each row must have data for each column
### NoSQL
- In NoSQL, schemas are dynamic. Columns can be added on the fly and each ‘row’ (or equivalent) doesn’t have to contain data for each ‘column
- **Key-Value** Stores: Data is stored in an array of key-value pairs. The ‘key’ is an attribute name which is linked to a ‘value’. e.g: **Redis**
- **Document Databases**: data is stored in documents (instead of rows and columns in a table) and these documents are grouped together in collections, Each document can have an entirely different structure, e.g: **Couchbase and MongoDB**
- **Wide-Column Databases**: Unlike relational databases, we don’t need to know all the columns up front and each row doesn’t have to have the same number of columns, e.g: **Cassandra**
- **Graph Databases**: These databases are used to store data whose relations are best represented in a graph
### Difference
#### Scalability
- In most common situations, SQL databases are vertically scalable, i.e., by increasing the horsepower (higher Memory, CPU, etc.) of the hardware, which can get very expensive
- NoSQL databases are horizontally scalable, meaning we can add more servers easily in our NoSQL database infrastructure to handle a lot of traffic. Any cheap commodity hardware or cloud instances can host NoSQL databases, thus making it a lot more cost-effective than vertical scaling.
#### Reliability or ACID Compliancy (Atomicity, Consistency, Isolation, Durability)
- when it comes to data reliability and safe guarantee of performing transactions, SQL databases are still the better bet.
- Most of the NoSQL solutions sacrifice ACID compliance for performance and scalability.
### Which one to choose
#### Reasons to use SQL database
- We need to ensure ACID compliance. ACID compliance reduces anomalies and protects the integrity of your database by prescribing exactly how transactions interact with the database
	- **Atomic**  – Transaction acting on several pieces of information complete only if all pieces successfully save. Here, “all or nothing” applies to the transaction.
	- **Consistent**  – The saved data cannot violate the integrity of the database. Interrupted modifications are rolled back to ensure the database is in a state before the change takes place.
	- **Isolation**  – No other transactions take place and affect the transaction in question. This prevents “mid-air collisions.”
	- **Durable**  – System failures or restarts do not affect committed transactions.
- Your data is structured and unchanging
#### Reasons to use NoSQL database
- Storing large volumes of data that often have little to no structure
- Making the most of cloud computing and storage. Cloud-based storage is an excellent cost-saving solution but requires data to be easily spread across multiple servers to scale up.
- Rapid development
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTgyMjI4NzMwOV19
-->