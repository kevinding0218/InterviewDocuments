##### How Are Strings Represented in Memory?
- A  _String_  instance in Java is an object with two fields: a  _char[] value_  field and an  _int hash_  field. The  _value_  field is an array of chars representing the string itself, and the  _hash_  field contains the  _hashCode_  of a string which is initialized with zero, calculated during the first  _hashCode()_  call and cached ever since.
##### What Is a Stringbuilder and What Are Its Use Cases?
- When concatenating two  _String_  instances, a new object is created, and strings are copied. This could bring a huge garbage collector overhead if we need to create or modify a string in a loop.  _StringBuilder_  allows handling string manipulations much more efficiently.
- _StringBuffer_  is different from  _StringBuilder_  in that it is thread-safe. If you need to manipulate a string in a single thread, use  _StringBuilder_  instead.

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyMDAzMTIzMThdfQ==
-->