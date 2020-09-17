#### What is GraphQL
- Is a query language for your API, not a library, not a product and not a database
- Traditional way of not using GraphQL is we request the API endpint while expect to receive a massive JSON object from response
	- Client -> endpoint  -> API -> database -> API -> JSON object -> Client
- GraphQL lets you write a query to request the exactly the data that you want ad receive the result back with just what you want
	- Client/Mobile -> Query -> GraphQL Server -> database -> JSON object -> Client/Mobile
- GraphQL Query:
	- request people information from `allPeople` API and just want the name and gender, don't want other information even if it's available
	```
	{
		allPeople(last: 3) {
			people {
				name
				gender
			}
		}
	}
	```


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE4NTY1MDA5MTcsLTEwNTkyNTQzNzIsLT
U5ODg3NTAzMiw3MzA5OTgxMTZdfQ==
-->