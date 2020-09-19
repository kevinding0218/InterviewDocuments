#### What is GraphQL
- Is a query language for your API, not a library, not a product and not a database
- Traditional way of not using GraphQL is we request the API endpint while expect to receive a massive JSON object from response
	- Client -> {endpoint}  -> API -> database -> API -> {JSON object} -> Client
- GraphQL lets you write a query to request the exactly the data that you want ad receive the result back with just what you want
	- Client/Mobile -> {Query} -> GraphQL Server -> database -> {JSON object} -> Client/Mobile
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
- Increase multi-team productivity
- Rapid product development
- Reduced Cost, would be tested only when there is a change in the schema or if there is a french shcema. 
#### RESt vs GraphQL
- Taken example of getting author information related to author, course, rating and topics
- For REST
	- **Multiple round trips** to collect the information from multiple resources
	-  May **over fetching or under fetching** data resources, meaning we may just want one thing but there is no way to filter out the request, so end up with a whole bunch of data
	- **Frontend** teams **rely on backend** teams heavily to deliver the APIs
	- **Caching** is built into **HTTP spec**
		- Example:
	- 1. we need an endpoint with `/ps/author/<id>`, that fetches the author information for a given id
	- 2. we need a secondary endpoint with `/ps/author/<id>/courses` to access the courses in the library
	- 3. we need a third endpoint with `/ps/author/<id>/rating` to access the rating
	- 4. we need a forth endpoint with `/ps/author/<id>/topics` to access the topic covered.
	- **For GraphQL**
	- **One single** request to collect the infomration by aggregation of data
	- Only **get what you ask for**, trailor made queries to your exact needs.
	- Frontend and backend teams can **work independently**.
		- Example:
		- we need compose one single request in the form of a GraphQL query and ask for exactly what I need.
	```
	{
		author(id: 2100) {
			name,
			course { title },
			rating,
			topic(last: 3) {
				name
			}
		}
	}
	```
	- Don't use HTTP spec for caching, (libraries like Apollo comes with caching options)
#### Core Concept
- Types
	- Int
	- Float
	- String
	- Boolean
	- ID: unique identifier, used to re-fetch an object or as the key for a cache
	- enum
	- Query and Mutation types
		- query represents what the client is asking for
		- and the mutation is when client's going to add or delete data from the API
		- both query and mutation types are the same as any other object type
		- Every GraphQL service has a query type. It may or may not have a mutation type. They act as an entry point into the schema
		```
		schema {
			query: Query
			mutation: Mutation
		}
		type Query {
			author_details: [Author]
		}
		type Mutation {
			addAuthor(firstName: String, lastName: String): Author
		}
		```
	- By default, each of the types can be set to null, to override the default behavior and ensure that a field cannot be null, add `!`
- Queries:
	- https://developer.github.com/v4/explorer/
	- Fields
		```
		query { 
		  viewer { 
		    login,
		    bio,
		    id,
		    name
		  }
		}
		```
	- Argument
		- Every field and nested object can get its own set of arguments. This gets rid of multiple API fetches.
		```
		query { 
		  viewer {  
		    id,
		    followers (last:3) {
		      nodes {
		        id
		        bio
		      }
		    }
		  }
		}
		```
	- Alias
		- You can't query for the same field with different arguments. Hence you need alias, they let you rename the result of a field with anything you want
```

```
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTIxNzAwMDcxMSwtMTkxNTgyNTY1OSwtMz
MyMjUzMzk4LDE0MDUzMDQzMTksLTEwNTkyNTQzNzIsLTU5ODg3
NTAzMiw3MzA5OTgxMTZdfQ==
-->