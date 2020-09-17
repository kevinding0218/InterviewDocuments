#### What is GraphQL
- Is a query language for your API, not a library, not a product and not a database
- Traditional way of not using GraphQL is we request the API endpint while expect to receive a massive JSON object from response
	- Client -> endpoint  -> API -> database -> API -> JSON object -> Client
- GraphQL lets you write a query to request the exactly the data that you want ad receive the result back with just what you want
	- Client/Mobile -> Query -> GraphQL Server -> database -> JSON object -> Client/Mobile
- GraphQL Query:
	- request only the last 3 people information from `allPeople` API and just want the name and gender, don't want other information even if it's available
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
- GraphQL Response:
	- asdf
```
{
	"data": {
		"allPeople": {
			"people": [
				
				]
		}
	}
}
```


<!--stackedit_data:
eyJoaXN0b3J5IjpbMTg4NDMyODE3NiwtMTA1OTI1NDM3MiwtNT
k4ODc1MDMyLDczMDk5ODExNl19
-->