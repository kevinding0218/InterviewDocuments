### NoSQL
#### When to use NoSQL
- NoSQL is ideal for using for static, document-oriented data. Caching of data is an example of where you'd use NoSQL. Another example is for self-contained data (email templates, event details, etc). If data requires a lot of dependencies that cannot (or should not) be flattened, a relational datastore may be a better choice.
- NoSQL is "eventually consistent" for indexes, so it's possible that your query may not return your recent inserts. This can be worked around with Views (stale=false) but that's very expensive. N1QL can do Read Your Own Writes, which is much more performant than views with less impact. Still, this will add latency to your requests.
- Couchbase checks every 5 seconds and if at least one document has changed couchbase updates the view.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM1Nzc2NzYwOF19
-->