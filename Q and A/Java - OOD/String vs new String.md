### What is the difference between `String s = "Test"` and `String s = new String("Test")`? Which is better and why?
- In general,  `String s = "Test"`  is more efficient to use than  `String s = new String("Test")`.
- In the case of  `String s = "Test"`, a String with the value “Test” will be **created in the String pool**. **If another String with the same value is then created** (e.g.,  `String s2 = "Test"`), **it will reference this same object in the String pool.**
- However, if you use  `String s = new String("Test")`, in addition to creating a String with the value “Test” in the String pool, **that String object will then be passed to the constructor of the String Object** (i.e.,  `new String("Test")`) and will create another String object (not in the String pool) with that value. Each such call will therefore create an additional String object (e.g.,  `String s2 = new String("Test")`  would create an addition String object, rather than just reusing the same String object from the String pool).
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEzMjkxNjM4MV19
-->