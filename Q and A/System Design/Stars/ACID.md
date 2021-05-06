### Reasons to use SQL database
- We need to ensure ACID compliance. ACID compliance reduces anomalies and protects the integrity of your database by prescribing exactly how transactions interact with the database
	- **Atomic**  – Transaction acting on several pieces of information complete only if all pieces successfully save. Here, “all or nothing” applies to the transaction.
	- **Consistent**  – The saved data cannot violate the integrity of the database. Interrupted modifications are rolled back to ensure the database is in a state before the change takes place.
	- **Isolation**  – No other transactions take place and affect the transaction in question. This prevents “mid-air collisions.”
	- **Durable**  – System failures or restarts do not affect committed transactions.
- Your data is structured and unchanging
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzk0Njg0ODE4XX0=
-->