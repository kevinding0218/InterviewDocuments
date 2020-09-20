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
#### REST vs GraphQL
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
		    login
		    bio
		    id
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
		query { 
		  viewer { 
		    id,
		    firstFollwers: followers (first : 3) {
		      nodes {
		        id
		        bio
		      }
		    },
		    lastFollwers: followers (last : 5) {
		      nodes{
		        id
		        bio
		      }
		    }
		  }
		}
		```
	- Fragments
		- Fragments are GraphQL's reusable units, they let you build sets of fields and then include them in multiple queries.
		```
		query { 
		  viewer { 
		    id
		    firstFollwers: followers (first : 3) {
		      nodes {
		        ...userInfo
		      }
		    }
		    lastFollwers: followers (last : 5) {
		      nodes{
		        ...userInfo
		      }
		    }
		  }
		}

		fragment userInfo on User {
		  id
		  bio
		  avatarUrl
		  bioHTML
		}
		```
	- Operation Name
		- A meaningful and explicit name for your operation. Think of it like a function name in a programming language
		```
		query operation_name { ... }
		```
	- Variables
		- Arguments to fields can be dynamic. GraphQL uses variables for factor dynamic values out of the query, and pass them as a separate dictionary.
		```
		query viewerInfo($isOwner: Boolean!) { 
		  viewer { 
		    ...userInfo
		    starredRepositories(ownedByViewer: $isOwner, last: 5) {
		      nodes {
		        id
		        name
		      }
		    }
		  }
		}

		fragment userInfo on User {
		  id
		  name
		}

		// QUERY VARIABLES
		{
		  "isOwner": false
		}
		```
	- Mutations
		- Mutations are used to make changes to the data (Create, Update, Delete data)
		- GraphQL assumes side-effects after mutations and changes the dataset after every mutation
		- While query fields are executed in parallel, mutation fields run in series, one after the other
		```
		mutation NewStatus($input:ChangeUserStatusInput!) {
		  changeUserStatus(input:$input) {
		    clientMutationId
		    status {
		      message
		    }
		  }
		}

		query viewerInfo { 
		  viewer { 
		    login
		    name
		    status {
		      id
		      message
		    }
		  }
		}
		// QUERY VARIABLES
		{
		  "input": {
		    "clientMutationId": "1010101",
		    "message": "Enjoy nowadays"
		  }
		}
		```
#### Why GraphQL?
- Benefit of Declarative Data Fetching
	- Avoid round-trips to fetch data
	- If using REST, we'll need some endpoints like
		- `/ps/author/<id>`
		- `/ps/author/<id>/courses`
		- `/ps/author/<id>/rating`
		- `/ps/author/<id>/topics`
	- If using GraphQL, we can avoid multiple round-trip request by using one query data
		- ``
{
	author (id: 2100) {
		name
		courses {
			title
		}
		rating
		topic (last: 3) {
			name
		}
	}
}
``
	- No more over-fetching or under-fetching of data
		- If using REST, calling `/api/user` that would return a full user object including additional field data we don't need
		- If using GraphQL, we can query only the data fields we need
		- ``
user {
	firstName
	lastName
	gender
}
``
- GraphQL is a strongly-typed language, and its schema should have types of all objects that it uses, the schema serves as a contract between client and server relies on a query language with a type system
	- code is predicatable which is clean and maintainable
	- frontend and backend teams work independently
	- ealier detection of errors and speed development
- Superior Developer Experience
	- GraphQL offers a lot of flexibility
	- No versioning
	- Non-breaking changes
#### GraphQL Ecosystem and Tools
- REST Architecture without GraphQL
	- Client <-> Server <-> Database
- REST Architecture with GraphQL
	- Client <-> GraphQL Client <-> Server <-> GraphQL Server <-> Database
- GraphQL Client
	- Handles sending requests(queries) to the server and receiving the response(JSON) from the server
	- Integrates with your view components and updates the UI
	- Caching Query Results
	- Error handling and schema validation
	- Local State Management
	- Pagination
- GraphQL Server
	- Receives the query from the client and responds back
	- Schema and Resolvers function: a function that resolves a value for a type/field in the GraphQL Schema, resolvers can return objects and also can resolve values from another REST API, database or cache
	- Network layer: the queries from client needs to be transported to the server through the network over HTTPS.
	- GraphQL Execution Engine: responsible for parses query from client and validating schema and return JSON response, executes resolvers for each field
	- Batched Resolving:  by making batch requests, we ensure that we do only one fetch to the backend. 
- Database to GraphQL Server (Prisma)
- Tools
	- GraphiQL: an in-browser IDE for writing, validating and testing GraphQL queries
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzU1ODcyNzg0LC0xNTY2OTc1OTkzLDIxMj
kxNTczNDcsMTc2MDE1NTYyOCwtMTkxNTgyNTY1OSwtMzMyMjUz
Mzk4LDE0MDUzMDQzMTksLTEwNTkyNTQzNzIsLTU5ODg3NTAzMi
w3MzA5OTgxMTZdfQ==
-->