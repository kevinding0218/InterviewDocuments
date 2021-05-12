### Normalization
- Normalization is the process to remove or organize redundant data from the database and to store minimize  redundant and consistent data into it.
- Normalization uses optimized memory and hence faster in performance.
#### 1NF
- Each column contains atomic values and there are not repeating groups of columns, e.g: 
	- A column stores both Physical Address and Email Address needs to separate it out
	- A column stores repeating info needs to seraprate into multiple rows
#### 2NF
- Meet with 1NF requirement
- All columns depends on the table's primary key 
- Move redundant data to a separate tableCreate relationship between the tables using foreign keys.
#### 3NF
- Meet with 2NF requirement
- All of its columns are not transitively dependant on the primary key
#### 4NF
- Meet with 3NF requirement
- Does not contain two or more independent multi-valued facts about an entity
### Denormalization
- Denormalization is used to combine multiple table data into one so that it can be queried quickly. The process of attempting to optimize the performance of a database by adding redundant data.
- Focus on achieve the faster execution of the queries
- Denormalization introduces some sort of wastage of memory.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjAyMTU3ODE1MywyNjMzNTE1OV19
-->