### Offset-based Pagination
#### Client provides
- Limit: the max number of items in a batch AKA page
- Offset: the starting position in the list of items
- e.g: `https://www.myWebsite.com/products?limit=50&&offset=100`, **skip the first offset** - 100 items and **return the next limit** 50 items which in this case is item 101 ~ 150
- This can be easily queried from SQL database like
```
SELECT * FROM products
ORDER BY id asc
LIMIT 50 OFFSET 100;
```
#### Pros
- Simple implementation for client and server
- Possible to jump to arbitrary pages instead of being forced to scroll through each page
- e.g: if user wants to skip to the 6th page from the frist page, they can just directly jump to the 6th page without having to scroll through 2, 3, 4, 5
#### Cons
- Can be unreliable at times, if there are frequent changes to your data as the pagination, then you might run into missing items or duplicate items
- Inefficient for large or distributed datasets, this is because your database query will most likely have to count and skip rows the offset before returning the actual results. The larger your offset the more inefficient your query becomes.
- e.g, offset from 1000 to 2000 to 3000, the first 1000 records were read three tims, and first 2000 were read twice.
- e.g, distributed storage, your data storage scan might include a number of shards before the desired results are received.
### Cursor-based Pagination
#### Client provides
- Limit: `https://www.myWebsite.com/products?limit=50`
#### Server responses with results and a next-cursor
- next-cursor=12345678, in some cases, these are known as continuation tokens.
#### Client continue sending request that includes this cursor in subsequent
- e.g `https://www.myWebsite.com/products?limit=50&nextCursor=12345678`
#### Server will use these next cursors
- Implement efficient queries so as to avoid reading through the same records over and over again like in offset-based pagination
- It's basically a pointer that the server can use to improve performance. The value of the cursor is totally up to you as sometimes your database does the job by providing such information so you don't need to worry about it, or you can come up with your own cursor value as well, for example, you may go with a timestamp, the sample query would look something like this, indexing this column will always be a good idea to improve query performance
```
SELECT * FROM products
WHERE created_timestamp < 12345678
ORDER BY created_timestamp
LIMIT 50;
```
#### Pros
- Improved performance, with indexing on the cursor
- Return consistent results, the addition or removal of items during requests does not bring in consistent results as the offset-based pagination
#### Cons
- Clients need to traverse through each page one by one
- Records need to be added sequentially to the DB at a random positions
- Clients need to manage the next cursor value and send it along with each request so it's one extra things that client needs to do when you 
### Summary
#### Offset-based Pagination
- Best suited for apps that use small datasets
- Clients need to jump around pages and can tolerate duplicate or missed item
#### Cursor-based Pagination
- Best suited for apps that use large datasets
- Consistency of result is critical
- Traversing pags is acceptable
<!--stackedit_data:
eyJoaXN0b3J5IjpbNTU2MjcxNzE0XX0=
-->