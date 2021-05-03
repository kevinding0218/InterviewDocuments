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
- Inefficient for la
### Cursor-based Pagination
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE2OTYzNTQ4NjZdfQ==
-->