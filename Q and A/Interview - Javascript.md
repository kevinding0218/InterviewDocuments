- [difference between call, apply, and bind](#difference-between-call--apply--and-bind)
- [Javacript types](#javacript-types)
- [difference between var, let and const](#difference-between-var--let-and-const)
  * [var](#var)
  * [let/const](#let-const)
  * [For example](#for-example)
- [difference between block-scoped or function scoped](#difference-between-block-scoped-or-function-scoped)
  * [Javascript is both block-scope and function scoped](#javascript-is-both-block-scope-and-function-scoped)
  * [block-scoped](#block-scoped)
  * [function-scoped](#function-scoped)
- [Closure](#closure)
- [What is function hoisting](#what-is-function-hoisting)
- [Event loop](#event-loop)
  * [callback](#callback)
  * [Event loop](#event-loop-1)
  * [Javascript is single thread](#javascript-is-single-thread)
- [When not to use arrow function](#when-not-to-use-arrow-function)
  * [click handler](#click-handler)
  * [object method](#object-method)
- [difference between == and ===](#difference-between----and----)
- [difference between encryption and hashing](#difference-between-encryption-and-hashing)
  * [Encryption is two way](#encryption-is-two-way)
  * [Hashing is one way, can't be undone](#hashing-is-one-way--can-t-be-undone)

## difference between call, apply, and bind
- At a very high level, call and apply execute a function immediately. Bind returns a new function.
- Call and apply are very similar in that they allow you to invoke a function.
	- call takes arguments one by one 
	- apply takes in arguments as an array. Remember A for apply, A for array.
	- For example
	```javascript
	var john = {
	  favoriteFood: 'pizza'
	}

	var bob = {
	  favoriteFood: 'spaghetti'
	}

	var favFood = function(eatAction, afterEatAction) {
	  console.log('It\'s time to ' + eatAction + ' ' + this.favoriteFood + '! Then ' + afterEatAction + '.')
	}

	bob.favFood('scarf down', 'sleep')
	// bob.favFood is not a function...
	// Results in error, favFood is not a method on bob
	// In order to user this method for bob, I need to use call or apply

	favFood.call(bob, 'scarf down', 'sleep') //It's time to scarf down spaghetti! Then sleep.

	favFood.apply(john, ['scarf down', 'sleep']) //It's time to scarf down pizza! Then sleep.

	favFood.call(john, ['scarf down', 'sleep']) //It's time to scarf down,sleep pizza! Then undefined.
	// Notice this is not what we want, but doesn't hard error out.
	// On the other hand...

	favFood.apply(bob, 'scarf down', 'sleep') //Uncaught TypeError... hard error
	```
- Bind is used to return a function that can invoke at a later time.
	- For example
	```
	var eatThenSomething = favFood.bind(bob)
	eatThenSomething('gobble', 'nap') //It's time to gobble spaghetti! Then nap.
	```
## Javacript types
- Javascript has **5 data types** that are passed by **_value_**: `Boolean`, `null`, `undefined`, `String`, and `Number`. We’ll call these **primitive types**.
- Javascript has **3 data types** that are passed by **_reference_**: `Array`, `Function`, and `Object`. These are all technically Objects, so we’ll refer to them collectively as **Objects**.
	- **Variables** that are assigned a **non-primitive value** are given a **_reference_** to that value. That **reference points to the object’s location in memory**. The **variables don’t actually contain the value**.
	- When a reference type value, an object, is copied to another variable using `=`, the address of that value is what’s actually copied over as if it were a primitive.**Objects are copied by reference** instead of by value.
- 
## difference between var, let and const
### var
- _var_ can be reassigned and updated
- _var_ is **function scoped**, means 
	- they are only available inside the function they're created in, if try to call it outside the function, will throw reference error.
	- if not created inside a function, they are 'globally scoped'
### let/const
- _let_ and _const_ is **block-scoped**
### For example
	```javascript
	var testFunc = function() {

	  // printed it defined in the "for loop" block
	  for (var n = 0; n < 10; n++) {
	    var printed = n
	    // let printed = n or const printed = n will both throw ReferenceError
	    // printed is not defined
	  }

	  // logging printed outside of block
	  console.log(printed)
	}

	// logs 9 - printed is available outside of block
	testFunc()
	```
## difference between block-scoped or function scoped
### Javascript is both block-scope and function scoped
### block-scoped
- Block-scoped exists when a declared variable has inside a block of code (usually enclosed between curly brackets {}) is only visible/accessible within that block of code.
### function-scoped
- Within function
## Closure
- A closure is a function that retains access to variables of the context it was created in even after said function call has returned.
- **For example** : 
	- Typically after a function has returned, variables that were created inside the context of that function are no longer accessible.
		```javascript
		var awFunc = function(first) {
		  var someFunc = function(second) {
		    return (first + second)
		  }

		  return someFunc('some')
		}

		var someNewFunc = awFunc('awe') // returns awesome
		someNewFunc() // Uncaught ReferenceError

		// Let's try to access the variables first and second
		first // Uncaught ReferenceError
		second // Uncaught ReferenceError
		```
	- However we can refactor a little and come up with the following code
		```javascript
		var awFunc = function(first) {
		  var someFunc = function(second) {
		    return (first + second)
		  }

		  return someFunc // notice function is not invoked
		}

		var someMoreFunc = awFunc('awe') // At this point awFunc has finished running

		// We can wait a long time right here if we want

		someMoreFunc('somer') // returns awesomer
		// I still have access to variables of awFunc even after it's finished running
		```
## What is function hoisting
- Function (and also variable) hoisting is when **a function (or variable)** is available **before** it's actual **declaration** statement.
- For example
	- Behind the scenes 'var a' is hoisted to the top. It is not declared, and so remains undefined, but no longer has a reference error.
	```javascript
	// returns a is not defined - reference error
	console.log(a)
	
	// returns undefined
	console.log(a)
	var a = "hello"
	console.log(a)
	```
	- Similarly, if a function is declared via functional declaration (using the keyword **function**) it is hoisted to the top
	```javascript
	// returns reference error - hard crash
	console.log(foo())

	// returns 9
	console.log(foo())
	function foo() {
	  return 9
	}
	```
- This does not work for function expressions (assigning to a **variable**)
	```javascript
	// returns foo is not a function
	console.log(foo())
	var foo = function() {
	  return 9
	}
	```
## Event loop
### callback
- **Definition**
	- _A callback is a function that is to be executed_ **_after_** _another function has finished executing — hence the name ‘call back’._
	- _In JavaScript, **functions are objects**. Because of this, functions can take functions as arguments, and can be returned by other functions. Functions that do this are called_ **_higher-order functions_**_. Any function that is passed as an argument is called a_ **_callback function_**_._
- Why do we need callback?
	- JavaScript is an event driven language. This means that instead of waiting for a response before moving on, JavaScript will keep executing while listening for other events, e.g:
		```javascript
		function first() { console.log(1); }
		function second() { console.log(2); }
		first();
		second()
		// output
		// 1
		// 2
		``` 
	- what if function `first` contains some sort of code that can’t be executed immediately?
		```javascript
		function first() { 
			setTimeout ( function() { console.log(1); }, 500 
		};
		function second() { console.log(2); }
		first();
		second()
		// output
		// 2
		// 1
		```
	- It’s not that JavaScript didn’t execute our functions in the order we wanted it to, it’s instead that **JavaScript didn’t wait for a response from** `**first()**`**before moving on to execute** `**second()**`
	- Because you can’t just call one function after another and hope they execute in the right order. **Callbacks are a way to make sure certain code doesn’t execute until other code has already finished execution**
- A real world example
	- When you make requests to an API, you have to wait for the response before you can act on that response. This is a wonderful example of a real-world callback.
		```javascript
		T.get('search/tweets', params, function(err, data, response) {  
		  if(!err){  
		    // This is where the magic will happen  
		  } else {  
		    console.log(err);  
		  }  
		})
		```
	- There are three parameters in this request:  
		- `‘search/tweets’`, which is the route of our request,  			
		- `params`  which are our search parameters,  
		- an anonymous function which is our callback.
		- A callback is important here because we need to wait for a response from the server before we can move forward in our code. 
		- We don’t know if our API request is going to be successful or not so after sending our parameters to search/tweets via a get request, we wait. 
		- Once Twitter responds, our callback function is invoked. Twitter will either send an `err` (error) object or a `response` object back to us. 
		- In our callback function we can use an `if()`statement to determine if our request was successful or not, and then act upon the new data accordingly.
### Event loop
- event loop is responsible for how this asynchronous behavior happens.
### Javascript is single thread
- This means Javascript will process each line of code after the previous line of code.
- In order to evaluate when/how to run lines of code, Javascript keeps track of two things.
	- **Event Table (Hash)** and **Event Queue**
		- When the Javascript engine comes across a line of code that needs to be run, it places the 'trigger' of that execution and the action of it in an event table. 
		- Later on, when the engine checks the event table, any events that have transpired, the corresponding action is moved into an event queue.
		- Later on, when the engine checks the event queue, if there is an event there to execute it will do so. 
		- After executing, it will check the event table again to see if any new events have transpired.
	- **Callbacks**
		- a callback is a function that is passed as an argument to another function
		- callbacks are regularly used with in asynchronous functions. This is a typical practice to extract some result when we are waiting for some action to come back before proceeding.
		- This is especially common when dealing with ajax calls or reading/writing actions.
## When not to use arrow function
### click handler
- **For example**
	- If you use an arrow function, the keyword `this` is not bound to that element. 
	- If we use a regular function, the keyword `this` will be bound to the element we clicked!
	```javascript
	button.addEventListener('click', () => {
		console.log(this); // this refers to 'window'
	    this.classList.toggle('on');
	});
	button.addEventListener('click', function() {
	    console.log(this); // this refers to 'button'
	    this.classList.toggle('on');
	});
	```
### object method
- **For example**
	- when using an arrow function `this` is not bound to anything and it just inherits it from the parent scope which in this case is the window.
```javascript
const person = {
    points: 23,
    score: () => {
        this.points++; // this refers to 'window'
    }
    score_1: function() {
        this.points++; // this refers to 'person'
    }
}
person.score();
person.score();
// person.prints is still 23
```
## difference between == and ===
- When the equality operators, `==` and `===`, are used on reference-type variables, they check the reference.
- === will not only check the value but of object type
- **For example**:
	```javascript
	var arrRef = [’Hi!’];  
	var arrRef2 = arrRef;
	console.log(arrRef === arrRef2); // -> true
	var arr1 = ['Hi!'];  
	var arr2 = ['Hi!'];
	console.log(arr1 === arr2); // -> false
	```
## difference between encryption and hashing
### Encryption is two way
- When we say encryption is two way, it means that something can be decrypted after it has been encrypted. 
- The idea is that you transfer encrypted information, and the receiever can then decrypt it and read the contents.
### Hashing is one way, can't be undone
- The concept between hashing is that it can't be undone. It is a one way operation, and there is no way to go from the hash to the original contents.
## What happens when type url in browser and enter
- The **browser checks the cache for a DNS record** to find the corresponding IP address of your url
	- DNS(Domain Name System) is a database that maintains the name of the website (URL) and the particular IP address it links to. Every single URL on the internet has a unique IP address assigned to it.
	- You can easily access a website by typing the correct IP address, but **it is easier to remember the name of the website using an URL and let DNS do the work for us** with mapping it to the correct IP.
	- First, it checks the **browser cache**. The browser maintains a repository of DNS records for a fixed duration for websites you have previously visited.
	- Second, the browser checks the **OS cache**. If it is not found in the browser cache, the browser would make a system call
	- Third, it checks the **router cache**. If it’s not found on your computer, the browser would communicate with the router that maintains its’ own cache of DNS records.
	- Fourth, it checks the **ISP (Internet Service Provider) cache**. If all steps fail, the browser would move on to the ISP. Your ISP maintains its’ own DNS server which includes a cache of DNS records which the browser would check with the last hope of finding your requested URL.
- If the requested URL is not in the cache, **ISP’s DNS server initiates a DNS query to find the IP address of the server** that hosts maps.google.com.
- Browser **initiates a TCP connection** with the server.
	- In order to transfer data packets between your computer(client) and the server, it is important to have a TCP connection established. 
	- This connection is established using a process called the **TCP/IP three-way handshake**. This is a three step process where the client and the server exchange SYN(synchronize) and ACK(acknowledge) messages to establish a connection.
		- Client machine sends a SYN packet to the server over the internet asking if it is open for new connections.
		- If the server has open ports that can accept and initiate new connections, it’ll respond with an ACKnowledgment of the SYN packet using a SYN/ACK packet.
		- The client will receive the SYN/ACK packet from the server and will acknowledge it by sending an ACK packet.
		- Then a TCP connection is established for data transmission
- The **browser sends an HTTP request** to the web server
	- The browser will send a GET request asking for maps.google.com web page. 
	- If you’re entering credentials or submitting a form this could be a POST request. 
	- This request will also contain additional information such as browser identification (_User-Agent_ header), types of requests that it will accept (_Accept_ header), and connection headers asking it to keep the TCP connection alive for additional requests. 
	- It will also pass information taken from cookies the browser has in store for this domain.
- The server handles the request and sends back a response.
	- it will assemble a response in a particular format (JSON, XML, HTML).
- The server sends out an HTTP response
	- The server response contains the web page you requested as well as the status code, compression type (_Content-Encoding)_, how to cache the page (_Cache-Control_), any cookies to set, privacy information, etc.
- The browser displays the HTML content (for HTML responses which is the most common)
[Jqeury related 1](https://www.toptal.com/jquery/interview-questions)

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwNzg4NDg2MzBdfQ==
-->