### Offset-based Pagination
#### Client provides
- Limit: the max number of items in a batch AKA page
- Offset: the starting position in the list of items
- e.g: `https://www.myWebsite.com/products?limit=50&&offset=100`
- This can be easily queried from SQL database like
```
SELECT * FROM products
ORDER BY id asc
LIMIT 50 OFFSET 100;
```
### Cursor-based Pagination
<!--stackedit_data:
eyJoaXN0b3J5IjpbNTIzMzQ3MzAzXX0=
-->