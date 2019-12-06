
<![endif]-->

Normalization

Is the process of organizing data to minimize data redundancy(data duplication), which in trun ensures data consistency.

**1NF****:**

Data in each column should be atomic. No multiple values, separated by comma.

The table does not contain any repeating column groups.

Identify each record uniquely using primary key.

**2NF**

The table meet all condition of 1NF

Move redundant data to a separate table

Create relationship between the tables using foreign keys.

**3NF**

Meet condition of 2NF

Does not contain columns that are not fully dependent upon the primary key.

De-normalization

Is the process of attempting to optimize the performance of a database by adding redundant data.

BCNF**:** if there are non-trivial dependencies between candidate key attributes.
<![endif]-->

Difference between SCOPE_IDENTITY() AND @@IDENTITY:

SCOPE_IDENTITY() – Same session and the same scope

@@IDENTITY – Same session and across any scope

IDENT_CURRENT(‘TableName’) – Specific table across any session and any scope.

Different between Primary key and Foreign key

**Primary Key**

Primary key uniquely identify a record in the table.

Primary Key can't accept null values.

By default, Primary key is clustered index and data in the database table is physically organized in the sequence of clustered index.

We can have only one Primary key in a table.

**Foreign Key**

Foreign key is a field in the table that is primary key in another table.

Foreign key can accept multiple null value.

Foreign key do not automatically create an index, clustered or non-clustered. You can manually create an index on foreign key.

We can have more than one foreign key in a table.

Different Join

**INNER JOIN**: Only matching rows between join table

**LEFT JOIN**: Only matching rows plus non-matching rows of left table

**FULL JOIN**: All rows

**SELF JOIN:** can be replace with LEFT JOIN INNER JOIN CROSS JOIN  
CROSS JOIN: A cross join that does not have a WHERE clause produces the Cartesian product of the tables involved in the join. The size of a Cartesian product result set is the number of rows in the first table multiplied by the number of rows in the second table.

Different between Truncate and Delete

TRUNCATE doesn't generate any rollback data, which makes it lightning fast. It just deallocates the data pages used by the table.

However, if you are in a transaction and want the ability to "undo" this delete, you need to use DELETE FROM, which gives the ability to rollback.

In SQL Server, it is possible to rollback a truncate operation if you are inside a transaction and the transaction has not been committed.

What is an index? What are the types of indexes? How many clustered indexes can be created on a table? I create a separate index on each column of a table. what are the advantages and disadvantages of this approach?

Indexes in SQL Server are similar to the indexes in books. They help SQL Server retrieve the data quicker.

**Indexes are of two types**. Clustered indexes and non-clustered indexes.

When you create a clustered index on a table, all the rows in the table are stored in the order of the clustered index key. So, there can be only one clustered index per table.

Non-clustered indexes have their own storage separate from the table data storage. Non-clustered indexes are stored as B-tree structures (so do clustered indexes), with the leaf level nodes having the index key and it's row locater.

If you create an index on each column of a table, **it improves the query performance,** as the query optimizer can choose from all the existing indexes to come up with an efficient execution plan. **At the same t ime, data modification operations (such as INSERT, UPDATE, DELETE) will become slow**, as every time data changes in the table, all the indexes need to be updated. **Another disadvantage is that, indexes need disk space, the more indexes you have, more disk space is used.** Additional disk space. Clustered Index does not.

Unique & Non-Unique indexes

**Unique index** is used to enforce uniqueness of key values in the index.

**Note**: by default, **Primary Key constraint, creates a unique clustered index.**

Uniqueness is a property of an index, and **both CLUSTERED and NON-CLUSTERED indexes can be UNIQUE**.

When to use Index?

a. When there is large amount of data. For faster search mechanism indexes are appropriate.

b. To improve performance they must be created on fields used in table joins

Primary Key vs Unique Key

**PK**: Can be only one in a table, never allows null values. PK is a unique key identifier and cannot be null and must be unique.

**UK**: can be more than one unique key in one table; can have null value(only single null), can be a candidate key(FK)

Define candidate key, alternate key, composite key.

A **candidate** key is one that can identify each row of a table uniquely. Generally a candidate key becomes the primary key of the table.

**If the table has more than one candidate key, one of them will become the primary key, and the rest are called alternate keys**.

A key formed by combining at least two or more columns is called **composite** **key**.

How does a transaction work?

The **transaction** will group SQL commands into a single unit, and completes successfully only if all commands within it complete successfully. The whole thing fails if one command fails.

**e.g. with a store procedure, we have**

BEGIN TRANSACTION

/*** COMMANDS ***/

IF @@error <> 0

BEGIN

ROLLBACK TRANSACTION

END

COMMIT TRANSACTION

What is the difference between a HAVING CLAUSE and a WHERE CLAUSE?

Having Clause is basically used only with the GROUP BY function in a query. WHERE Clause is applied to each row before they are part of the GROUP BY function in a query.

DML triggers

triggers are sets of commands that get executed when an event(Before Insert, After Insert, On Update, On delete of a row) occurs on a table, views.

Disadvantage of trigger

Triggers execute invisible to client-application application. They are not visible or can be traced in debugging code.

It is hard to follow their logic as it they can be fired before or after the database insert/update happens.

It is easy to forget about triggers and if there is no documentation it will be difficult to figure out for new developers for their existence.

Triggers run every time when the database fields are updated and it is overhead on system. It makes system run slower.

What are cursors? Explain different types of cursors. What are the disadvantages of cursors?

**Cursors** allow row-by-row prcessing of the resultsets.

**Types of cursors**: Static, Dynamic, Forward-only, Keyset-driven. See books online for more information.

**Disadvantages of cursors**: Each time you fetch a row from the cursor, it results in a network roundtrip, where as a normal SELECT query makes only one rowundtrip, however large the resultset is. Cursors are also costly because they require more resources and temporary storage.

Because a cursor is an actual object inside the database engine, there is a little overhead involved in creating the cursor and destroying it. Also, a majority of cursor operations occur in tempdb, so a heavily used tempdb will be even more overloaded with the use of cursors.

**CTE**

CTE is a temporary result set, that can be referenced within a SELECT, INSERT, UPDATE or DELETE statement that immediately follows the CTE.

ROW_NUMBER() OVER(Partition By value,…) order by clause()

**Over** – specify the order of the rows.

**Order by** – Provide sort order for the records.

PARTITION BY Clause

When you specify a column or set of columns with PARTITION BY clause then it will divide the result set into record partitions and then finally ranking functions are applied to each record partition separately and the rank will restart from 1 for each record partition separately.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTUwOTA1Mzg5NF19
-->