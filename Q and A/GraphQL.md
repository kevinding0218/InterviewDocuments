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
	- response is a JSON object structured just like the request
```
{
	"data": {
		"allPeople": {
			"people": [
				{
					"name": "Tom",
					"gender": "male"
				},
				...	
			]
		}
	}
}
```
##### Advantages:
- Provides clients the power to ask for exactly what they need and nothing more or less
- GraphQL APIs get all the data your app needs in a single request
- Language agnostic, plenty of client and server libraries are available
#### RESt vs GraphQL
- Taken example of getting author information related to author, course, rating and topics
- For REST
	- 1. we need an endpoint with `/ps/author/<id>`, that fetches the author information for a given id
	- 2. we need a secondary endpoint with `/ps/author/<id>/courses` to access the courses in the library
	- 3. we need a third endpoint with `/ps/author/<id>/rating` to access the rating
	- 4. we need a forth endpoint with `/ps/author/<id>/topics` to access the topic covered.

<!--stackedit_data:
eyJoaXN0b3J5IjpbNzEwNjQ5NzYyLC0xMDU5MjU0MzcyLC01OT
g4NzUwMzIsNzMwOTk4MTE2XX0=
-->