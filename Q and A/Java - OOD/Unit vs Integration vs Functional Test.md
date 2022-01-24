### Difference between unit, integration and functional testing
- A unit test is to test a small isolated piece of code such as method. It doesn't interact with any other systems such as database or another application
	- We would mock the database, network call or some other inner method if already covered in another unit test
- An integration test is how your code play with other systems, such as a database, a cache or third-party application
	- This would be calling the real endpoint that your code would actually execute with testing data that can inserted into database, etc
- A functional test is the testing of the complete functionality of an application. This may involve the use of an automated tool to carry out more complex user interactions with your system. It tests certain flows/use cases of your application
	- This would execute on multiple business scenario to ensure all use cases/test cases are covered
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQxMTkxMTc1OV19
-->