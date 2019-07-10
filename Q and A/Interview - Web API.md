- [REST](#rest)
  * [What is REST?](#what-is-rest-)
  * [REST constraints](#rest-constraints)
  * [compare REST vs SOAP](#compare-rest-vs-soap)
- [Serialization](#serialization)
  * [Can you serialize hashtable and Why?](#can-you-serialize-hashtable-and-why-)
- [Asp.net web api](#aspnet-web-api)
  * [What is ASP.NET Web API?](#what-is-aspnet-web-api-)
  * [Advantages](#advantages)
  * [what is OData](#what-is-odata)
  * [what would a method return](#what-would-a-method-return)
  * [Controller vs ControllerBase](#controller-vs-controllerbase)
  * [Attributes](#attributes)
    + [`[ApiController]`](#--apicontroller--)
    + [`[ProducesResponseType(StatusCodes.Status201Created)]`](#--producesresponsetype-statuscodesstatus201created---)
- [Ways of passing parameters to method](#ways-of-passing-parameters-to-method)
  * [HttpHeader info](#httpheader-info)
- [Program.cs](#programcs)
- [Startup.cs](#startupcs)
- [Options pattern/configuration](#options-pattern-configuration)
- [CORS](#cors)
- [Authentication](#authentication)
  * [Cookie-based authentication](#cookie-based-authentication)
  * [Token-based authentication](#token-based-authentication)
  * [HttpContext.User](#httpcontextuser)
  * [JWT token](#jwt-token)
- [Authorization](#authorization)
  * [`[Authorize]`/`[Authorize(Roles = Role.Admin)]`](#--authorize-----authorize-roles---roleadmin---)
  * [`[AllowAnonymous]`](#--allowanonymous--)
- [[Filter](https://docs.microsoft.com/en-us/aspnet/core/mvc/controllers/filters?view=aspnetcore-2.2)](#-filter--https---docsmicrosoftcom-en-us-aspnet-core-mvc-controllers-filters-view-aspnetcore-22-)
  * [Definition](#definition)
  * [How filter works](#how-filter-works)
  * [where to place the filter](#where-to-place-the-filter)
  * [ServiceFilterAttribute](#servicefilterattribute)
  * [filter vs middleware](#filter-vs-middleware)
- [[Middleware](https://docs.microsoft.com/en-us/aspnet/core/fundamentals/middleware/?view=aspnetcore-2.2)](#-middleware--https---docsmicrosoftcom-en-us-aspnet-core-fundamentals-middleware--view-aspnetcore-22-)
  * [ErrorLog](#errorlog)
  * [Request&Response Logging](#request-response-logging)
  * [Authorization](#authorization-1)
- [[Routing](https://medium.com/quick-code/routing-in-asp-net-core-c433bff3f1a4)](#-routing--https---mediumcom-quick-code-routing-in-asp-net-core-c433bff3f1a4-)
- [HttpHeader](#httpheader)
- [HttpStatusCode](#httpstatuscode)
- [.Net Core Dependency Injection](#net-core-dependency-injection)
  * [[**.Net Core self dependency injection lifetime types**](https://devblogs.microsoft.com/cesardelatorre/comparing-asp-net-core-ioc-service-life-times-and-autofac-ioc-instance-scopes/):](#---net-core-self-dependency-injection-lifetime-types----https---devblogsmicrosoftcom-cesardelatorre-comparing-asp-net-core-ioc-service-life-times-and-autofac-ioc-instance-scopes---)
- [Web Api with other technology](#web-api-with-other-technology)
  * [compare web api vs mvc](#compare-web-api-vs-mvc)
  * [compare web api vs  wcf vs web service](#compare-web-api-vs--wcf-vs-web-service)
- [State Managment](#state-managment)
- [[Asp.net Core Module]([https://docs.microsoft.com/en-us/aspnet/core/host-and-deploy/aspnet-core-module?view=aspnetcore-2.2#aspnet-core-module-description](https://docs.microsoft.com/en-us/aspnet/core/host-and-deploy/aspnet-core-module?view=aspnetcore-2.2#aspnet-core-module-description))](#-aspnet-core-module---https---docsmicrosoftcom-en-us-aspnet-core-host-and-deploy-aspnet-core-module-view-aspnetcore-22-aspnet-core-module-description--https---docsmicrosoftcom-en-us-aspnet-core-host-and-deploy-aspnet-core-module-view-aspnetcore-22-aspnet-core-module-description--)
- [What's the difference between SDK and Runtime in .NET Core?](#what-s-the-difference-between-sdk-and-runtime-in-net-core-)
- [Why use ForwardedHeaders](#why-use-forwardedheaders)
- [Security](#security)
  * [CSRF Attack](#csrf-attack)

## REST
### What is REST?
- `REST` is for **Representational state transfer** which is an **abstraction of architecture** of networked application. 
- REST architectural pattern treats each **service as a resource** and a client can access these resources by using HTTP protocol methods like GET, POST, PUT, and DELETE.
### REST constraints
- **Client-Server constraint**
	- That means client application and server application should be developed separately without any dependency on each other. A client should only know resource URIs and that’s all.
- **Stateless constraint**
	- This means that we should not be storing anything on the server related to the client. The request from the client should contain all the necessary information for the server to process that request. This ensures that each request can be treated independently by the server.
- **Cacheable constraint**
	- This constraint says that let the client know how long this data is good for so that the client does not have to come back to the server for that data over and over again.
- **Uniform Interface constraint**
	- Each resource is identified by a specific URI with HTTP verbs
- **Layered System**
	- we deploy the APIs in server A, and store data on server B and authenticate requests in server C.
### compare REST vs SOAP
1.  The SOAP is an XML based protocol whereas REST is not a protocol but it is an architectural pattern i.e. resource-based architecture.
2.  SOAP has specifications for both stateless and state-full implementation whereas REST is completely stateless.
3.  SOAP enforces message format as XML whereas REST does not enforce message format as XML, JSON or text.
4.  The SOAP message consists of an envelope which includes SOAP headers and body to store the actual information we want to send whereas REST uses the HTTP build-in headers (with a variety of media-types) to store the information and uses the HTTP GET, POST, PUT and DELETE methods to perform CRUD operations.
5.  SOAP uses interfaces and named operations to expose the service whereas to expose resources (service) REST uses URI and methods like (GET, PUT, POST, DELETE).
6.  SOAP Performance is slow as compared to REST.
## Difference between GET and POST
|| GET | POST |
|--|--|--|
|back or refresh click|no harm|data will be resend(browser will inform)|
|bookmark|can|cannot|
|cache|can|cannot|
|history|param will be saved in browser history|will not save|
|data length restriction|max 2048(not true depend on browser/server limitation)|no restriction|
|security|worse because data is part of url, don't use when send password|safer because data will not be saved in browser history or web server log|
## Difference between POST, PUT and PATCH
|POST|PUT|PATCH|
|--|--|--|
| **creates a child resource** at a _server defined_ URL(POST /questions) | **creates/replaces the resource** in its entirety at the _client defined_ URL(PUT /questions/{question-id}) |**updates  _part_  of the resource** at that client defined URL|
|`POST` is NOT idempotent. So if you retry the request N times, you will end up having N resources with N different URIs created on server.|`PUT` method is [idempotent](https://restfulapi.net/idempotent-rest-apis/). So if you send retry a request multiple times, that should be equivalent to single request modification.|


## Serialization
### Can you serialize hashtable and Why?
- **No**, You **can’t** Serialize Hash table.Because, the .NET Framework does **not allow serialization** of any object that implements the `IDictionary` interface
## Asp.net web api
### What is ASP.NET Web API?
- ASP.NET Web API is a framework that we can create non-SOAP based services like plain XML or JSON strings, etc. with many other advantages including:
	- Create resource-oriented services using the full features of HTTP
	- Exposing services to a variety of clients easily like browsers or mobile devices, etc.
### Advantages
- It works the HTTP way using standard HTTP verbs like  `GET`,  `POST`,  `PUT`,  `DELETE`, etc. for all CRUD operations.
- Complete support for routing
- Response generated in JSON, text or XML format
- Supports Model binding and Validation
- Support for OData
### what is OData
- `OData` defines parameters that can be used to modify an OData query. 
- The client sends these parameters in the query string of the request URI(`$filter`, `$sort`, and `$page`). 
- **For example**: to sort the results, a client uses the `$orderby` parameter:
	```
	http://localhost/Products?$orderby=Name
	```
### what would a method return
- Specific type
	- `string/Product/IEnumerable<Product>`
- IActionResult
	- `NotFound()/OK(Product)/BadRequest()`
- ActionResult<T> type
	- `ActionResult<Product>/Task<ActionResult<Product>>`
### Controller vs ControllerBase
- `Controller` derives from `ControllerBase` and adds support for views, so it's for handling web pages, not web API requests(**MVC**)
- A `ControllerBase` class for an MVC controller without view support(**web api**).
### Attributes
#### `[ApiController]`
- Enables a few new features such as automatic model state validation and binding source parameter inference.(get rid of `FromBody`)
- Makes `[Route]` as required
- makes model validation errors automatically trigger an HTTP 400 response.
#### `[ProducesResponseType(StatusCodes.Status201Created)]`
- `ProducesResponseType` attribute is necessary if all possible response status codes cannot be ascertained based on the method definition. For example, 200 is obvious, but if you actually need to return 201, that would need to be specified.
## Ways of passing parameters to method
- `[FromBody]`
	- handles an action parameter comes only from the entity body of the incoming request, one method could only have one `[FromBody]` parameter.
- `[FromQuery]`
	- handles query parameters, i.e. key-value pairs coming after "?" in URI.
- `[FromRoute]`
	- handles route parameters coming before "?" in URI, i.e. path parameters.
- `[FromUri]`
	- in .Net core works like `[FromQuery]`-
- For example: 
	```
	//.../api/controllerName/{shelfID}/BookCollection?ID="123"&Name="HarryPotter"
	[HttpGet]
	[Route("api/controllerName/{shelfID}/BookCollection")]
	public async Task<IActionResult> GetAllBooks(string shelfID, [FromQuery] string ID, [FromQuery] string Name)
	```
### HttpHeader info
- HTTP headers are additional and optional pieces of information like Authorization in the **form of name/value pairs** that travel between the client and the server with the request and/or the response.
- For example:
	```
	// In middleware
	public override void OnActionExecuting(ActionExecutingContext actionContext) {
		// Retrieve from Request Header
		var authorizationToken = actionContext.HttpContext.Request.Headers.TryGetValue("Authorization", out authorizationToken);
		// Set data to Response Header
		actionExecutedContext.Response.Content.Headers.Add("totalHeader", count);
	}
	```
## Program.cs
- CreateDefaultBuilder extension method will create a default configuration which will look first into `appsettings.json` files then will look for Environment variables and at the end, it will use command line arguments.
- This part will also set up default logger sources (debug and console) and load the settings for logging from appsettings.json.
- After the `CreateDefaultBuilder` finishes, then `Startup` class is executed
	- First, the constructor code is executed. 
	- After that, services are added to DI container via `AddServices` method that lives in Startup class. 
	- After that, an order of middleware that will handle every incoming request is set up.
```C#
public static void Main(string[] args)
{
    BuildWebHost(args).Run();
}
 
public static IWebHost BuildWebHost(string[] args) =>
    WebHost.CreateDefaultBuilder(args)
        .UseStartup<Startup>()
        .Build();
```
## Startup.cs
- The `Startup` class is specified to the app when the app's `host` is built. The app's host is built when `Build` is called on the host builder in the `Program` class.
- `public  void  ConfigureServices(IServiceCollection services)`
	- Use this method to add services to the container via Dependency Injection
		- DbContext/Cors/Service Layer/JWT Authentication
- `public  void  Configure(IApplicationBuilder app)`
	- Use this method to configure the HTTP request pipeline like middleware in order.
- Both are called by the ASP.NET Core runtime when the app starts:	
## Options pattern/configuration
- General Options Configuration
	- An options class must be non-abstract with a public parameterless constructor.
		```
		public  class  MyOptions 
		{ 
			public MyOptions() 
			{ // Set default value. 
				Option1 = "value1_from_ctor"; 
			} 
			public  string Option1 { get; set; } 
			public  int Option2 { get; set; } = 5; 
		}
		```
	- The `MyOptions` class is added to the service container with `Configure` and bound to configuration:
		```
		// Example #1: General configuration  
		// Register the Configuration instance which MyOptions binds against. services.Configure<MyOptions>(Configuration);
		```
	- The sample's `appsettings.json` file specifies values for `option1` and `option2`:
		```
		{ 
			"option1": "value1_from_json",  
			"option2": -1,   
			"Logging": { "LogLevel": { "Default": "Warning" } }, 
			"AllowedHosts": "*" 
		}
		```
- Suboptions configuration
	```
	// Example #3: Suboptions  
	// Bind options using a sub-section of the appsettings.json file.
	services.Configure<MySubOptions>(Configuration.GetSection("subsection"));
	// appsettings.json
	{ 
		"option1": "value1_from_json", 
		"option2": -1, 
		"subsection": 
		{  
			"suboption1": "subvalue1_from_json",  
			"suboption2": 200  
		}
	}
	```
## CORS
- Browser security prevents a web page from making requests to a different domain than the one that served the web page. This restriction is called the `same-origin policy`.
- CORS Middleware handles cross-origin requests.
- `UseCors`  must be called before  `UseMvc`
- The URL must  **not**  contain a trailing slash (`/`). If the URL terminates with  `/`, the comparison returns  `false`  and no header is returned.
	- For example: In Startup.cs
		```
		// Sets the policy name to "_myAllowSpecificOrigins".
		// The policy name is arbitrary.
		readonly  string MyAllowSpecificOrigins = "_myAllowSpecificOrigins";
		public void ConfigureServices(IServiceCollection services) 
		{ 
			// Calls `AddCors` with a `lambda expression`. 
			services.AddCors(options => 
				{ options.AddPolicy(MyAllowSpecificOrigins,  builder => 
					{ 
						builder.WithOrigins("http://example.com",  "http://www.contoso.com"); 
					});  
				});
		}
		public void Configure(IApplicationBuilder app, IHostingEnvironment env) {
			// Calls the `UseCors` extension method, which enables CORS.
			app.UseCors(MyAllowSpecificOrigins);
		}
		```
- Enable CORS with attributes
	- The `EnableCors` attribute provides an alternative to applying CORS globally.
	- The `[EnableCors]` attribute can be applied to:
		- Razor Page  `PageModel`
		- Controller
		- Controller action method
## Authentication
### Cookie-based authentication
- When a user authenticates using their username and password, they're issued a `token`, containing an authentication ticket that can be used for `authentication` and `authorization`. 
- The token is stored as a `cookie` that accompanies `every request` the client makes. 
- Generating and validating this cookie is performed by the `Cookie Authentication Middleware`.
- The middleware `serializes a user principal` into an `encrypted cookie`. 
- On subsequent requests, the middleware validates the cookie, recreates the principal, and assigns the principal to the  `User` property of [`HttpContext`.
### Token-based authentication
- When a user is authenticated, they're issued a `token` (not an antiforgery token). 
- The token contains user information in the `form of claims` or a reference token that points the app to user state maintained in the app. 
- When a user attempts to access a resource requiring authentication, the token is sent to the app with an additional `authorization header` in form of `Bearer token`. This makes the app `stateless`. 
- In each subsequent request, the token is passed in the request for server-side validation. This token isn't  `encrypted`; it's  `encoded`. 
- On the server, the token is decoded to access its information. To send the token on subsequent requests, store the token in the browser's `local storage`. 
- Don't be concerned about CSRF vulnerability if the token is stored in the browser's local storage. CSRF is a concern when the token is stored in a cookie.
### HttpContext.User
- Allows you to get current User in asp.net core
	- First, add DI in ConfigureServices from Startup.cs
		```
		public void ConfigureServices(IServiceCollection services)
		{
		     ...
		     services.AddSingleton<IHttpContextAccessor, HttpContextAccessor>();
		}
		```
	- Then add to Controller constructor DI
		```
		private readonly IHttpContextAccessor _httpContextAccessor;
		public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options, IHttpContextAccessor httpContextAccessor) : base(options)
		{
		  _httpContextAccessor = httpContextAccessor;
		}
		```
		- Finally call it by `using System.Security.Claims;`
		```
		var userId = _httpContextAccessor.HttpContext.User.FindFirst(ClaimTypes.NameIdentifier).Value;
		```
### JWT token
- **Definition**
	- **Json Web Token** is a Json object that is defined in RFC 7519 as a safe way to represent a set of information between two parties.
	- JWT is **composed of**
		- **header**(ALGORITHM< alg> & TOKEN TYPE< typ>)
			- contains information about how the JWT signature should be computed 
		- **payload**(DATA)
			- **Self-contained**: The payload contains all the required information about the user (referred as "claims"), to avoid querying the database more than once.
			- the data inside a JWT is **encoded** and **signed**, not **encrypted**, so better to encrypted identifier claims like uid.
			-  size of the data will affect performance and cause latency
				- **Registered (Predefined) Claims**: iss(Issuer), sub(Subject), aud(Audience), exp(Expiration Time), nbf(Not Before), iat(Issue At)
				- **Public Claims**: Custom claim names that are required to be collision resistant globally. Their names should be UUIDs or prefixed by a URL to create a safe namespace for them and avoid collisions.
				- **Private Claims**: Custom claim names that are not required to be be collision resistant.
		- **signature**
			- To create the signature part you have to take the `encoded header`, the `encoded payload`, a `secret`, the `algorithm` specified in the header, and sign that.
				```
				HMACSHA256(
					base64UrlEncode(header) + "." + 
					base64UrlEncode(payload), 
					secret)
				```
		- **How it verifying**
			- only the authentication server and the application server know the secret key.
			- The application server receives the secret key from the authentication server when the application sets up its authentication process.
			- Since the application knows the secret key, when the user makes a JWT-attached API call to the application, the application can **perform the same signature algorithm on the JWT**.
			- The application can then verify that the **signature obtained from it’s own hashing operation matches the signature on the JWT itself** (i.e. it matches the JWT signature created by the authentication server)
		- **How to identify if token is expired**
			- One way to do it is to add a property to your user object in the server database, to reference the datetime the token was created at.
			- A token automatically stores this value in the  `iat`  property.  
			- Every time you check the token, you can compare its  `iat`  value with the server-side user property.  
			- To invalidate the token, just update the server-side value, and if  `iat`  is older than this, you can reject the token.
			- For example:
				```
				services.AddAuthentication(options => {
				    options.DefaultAuthenticateScheme = "JwtBearer";
				    options.DefaultChallengeScheme = "JwtBearer";            
				 })
				 .AddJwtBearer("JwtBearer", jwtBearerOptions =>
				 {                        
				    jwtBearerOptions.TokenValidationParameters = new TokenValidationParameters
				    {     
					    // The signing key must match!                       
				        ValidateIssuerSigningKey = true,
				        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("your secret goes here")),

						 // Validate the JWT Issuer (iss) claim
				         ValidateIssuer = true,
				         ValidIssuer = "The name of the issuer",

						 // Validate the JWT Audience (aud) claim
				         ValidateAudience = true,
				         ValidAudience = "The name of the audience",

				         // Validate the token expiry
				         ValidateLifetime = true, //validate the expiration and not before values in the token

						 // If you want to allow a certain amount of clock drift, set that here:
				         ClockSkew = TimeSpan.FromMinutes(5) //5 minute tolerance for the expiration date
				    };
				});
				```
		- **Where to store token on client side**
			- **Don't store it inside localStorage/sessionStorage**, it's accessible by any script inside your page (which is as bad as it sounds as an XSS attack can let an external attacker get access to the token)
			- **stored inside an HttpOnly cookie**, a special kind of cookie that's only sent in HTTP requests to the server, and it's never accessible (both for reading or writing) from JavaScript or other scripting language running in the browser.
				- [Using cookie authentication](https://stormpath.com/blog/token-authentication-asp-net-core)
					- create a custom `ISecureDataFormat` implementation that validates a JWT string
					- Since you’re only validating tokens, not creating them, you only need to implement the `Unprotect`method
						```
						public  class  CustomJwtDataFormat  :  ISecureDataFormat<AuthenticationTicket>
						{
							private  readonly string  algorithm;
							private  readonly TokenValidationParameters validationParameters;
							public  CustomJwtDataFormat(string  algorithm,  TokenValidationParameters validationParameters)
							{
								this.algorithm  =  algorithm;
								this.validationParameters  =  validationParameters;
							}
							public  AuthenticationTicket Unprotect(string  protectedText,  string  purpose)
							{
								var  handler  =  new  JwtSecurityTokenHandler();
								ClaimsPrincipal principal  =  null;
								SecurityToken validToken  =  null;

								try
								{
									principal  =  handler.ValidateToken(protectedText,  this.validationParameters,  out validToken);

									var  validJwt  =  validToken as  JwtSecurityToken;
									if  (validJwt  ==  null)
									{
										throw  new  ArgumentException("Invalid JWT");
									}
								}
								catch  (SecurityTokenValidationException) {
									return  null;
								}
								catch  (ArgumentException)
								{
									return  null;
								}
								
								// Validation passed. Return a valid AuthenticationTicket:
								return  new  AuthenticationTicket(principal,  new  AuthenticationProperties(),  "Cookie");
							}
						}
						```
						- Wire this class up with `UseCookieAuthentication` in your `Startup.cs` file
						```
						app.UseCookieAuthentication(new  CookieAuthenticationOptions
						{
							AutomaticAuthenticate = true,
							AutomaticChallenge = true,
							AuthenticationScheme = "Cookie",
							CookieName = "access_token",
							TicketDataFormat = new CustomJwtDataFormat(nSecurityAlgorithms.HmacSha256,
							tokenValidationParameters)
						});
						```
			- **How to send the JWT**
				- typically in the **Authorization** header using **Bearer** schema, the content of the header should look like `Authorization: Bearer <yourJwtToken>`
## Authorization
### `[Authorize]`/`[Authorize(Roles = Role.Admin)]`
- secured with JWT using the `[Authorize]` attribute
### `[AllowAnonymous]`
- Skip authorization
## [Filter](https://docs.microsoft.com/en-us/aspnet/core/mvc/controllers/filters?view=aspnetcore-2.2)
### Definition
- `Filters` in ASP.NET Core allow code to be run before or after specific stages in the request processing pipeline. First middleware run then filter like a `russian doll`.
- Built-in filters handle tasks such as:
	- Authorization (preventing access to resources a user isn't authorized for).
	- Response caching (short-circuiting the request pipeline to return a cached response).
- Custom filters can be created to handle cross-cutting concerns. Examples of cross-cutting concerns include error handling, caching, configuration, authorization, and logging. 
- If a filter cancelled during pipeline, all its continued filter will not be invoked.
### How filter works
- Filters run within the `ASP.NET Core action invocation pipeline`, sometimes referred to as the `filter pipeline`. 
- The filter pipeline runs after ASP.NET Core selects the action to execute.
Each filter type is executed at a different stage in the filter pipeline:
	- `[Authorization filters]`
		- Are the first filters run in the filter pipeline.
		- Control access to action methods.
		- Have a before method, but no after method.
		- Do **not** throw exceptions within authorization filters:
			- The exception will not be handled.
			- Exception filters will not handle the exception.
	- `[Resource filters]`
	    - Run after authorization.
	    - `[OnResourceExecuting]` can run code before the rest of the filter pipeline. 
	    - For example,  `OnResourceExecuting`  can run code before model binding.
	    - `[OnResourceExecuted]`  can run code after the rest of the pipeline has completed.
	 - `[Exception filters]` 
		 - are used to apply global policies to unhandled exceptions that occur before anything has been written to the response body. (`OnException`)
		 - Don't have before and after events.
		 - not as flexible as error handling middleware.
	- `[Action filters]`  
		- do not apply on Razor Pages
		- can run code immediately before (`OnActionExecuting`) and after an individual action method is called(`OnActionExecuted`). 
		- They can be used to manipulate the arguments passed into an action and the result returned from the action or logging
	- `[Result filters]` 
		- can run code immediately before (`OnResultExecuting` add http header) and after the execution of individual action results(`OnResultExecuted`). 
		- They run only when the action method has executed successfully. 
		- They are useful for logic that must surround view or formatter execution.
### where to place the filter
- A filter can be added to the pipeline at one of three  _scopes_:
	- Using an attribute on an action.
	- Using an attribute on a controller.
	- Globally for all controllers and actions as shown in the following code:
### ServiceFilterAttribute
- Service filter implementation types are registered in `ConfigureServices`. 
	```
	public void ConfigureServices(IServiceCollection services) {
		// AddHeaderResultServiceFilter is added to the DI container 
		services.AddScoped<AddHeaderResultServiceFilter>();
	}
	```
- A `ServiceFilterAttribute` retrieves an instance of the filter from Dependency Injection.
	```
	[ServiceFilter(typeof(AddHeaderResultServiceFilter))]
	public IActionResult Get() {}
	```
### filter vs middleware
- both seem like something that you would use to alter the request that's coming in.
- filters 
	- part of the ASP.NET Core run-time, which means that they have access to ASP.NET Core context(controller/method name, routing) and constructs.
- middleware 
	- participates in the application pipeline allowing us to exit when we don't need to continue execution for any number of reasons.
	- it doesn't have context of controller/method pipeline
## [Middleware](https://docs.microsoft.com/en-us/aspnet/core/fundamentals/middleware/?view=aspnetcore-2.2)
- **Definition**
	- Every request sent to an ASP.NET Core application runs through the pipeline of configured middleware before it is processed to generate a response.
	- Each middleware can be programmed to perform some work in two distinct steps: before and after the request gets processed. One middleware is **only called once by the runtime**
	- When the request enters in the processing phase, request pipeline consists of a sequence of request delegates, called one after the other **in the order they have been registered in startup-Configure with IApplicationBuilder**.
		```
		// Startup.cs
		public  void  Configure(IApplicationBuilder app)
		{  
			// middleware are configured in order to the pipeline.
			app.UseExceptionHandler("/Error");
			app.UseStaticFiles();
			app.UseAuthentication();
			app.UseMvc();
		}
		```
- **Built-in middleware**
	- app.UseExceptionHandler("/Error"): Handles exceptions.
	- app.UseCors(MyCustomPolicy): Configures Cross-Origin Resource Sharing.
	- app.UseHttpsRedirection(): Enforce to redirect all HTTP requests to HTTPS (ASP.NET Core 2.1 or later)
	- app.UseStaticFiles(): Access static file(js/css/image) via a relative path to the web root(wwwroot)
	- app.UseCookiePolicy(): Tracks consent from users for storing personal information and enforces minimum standards for cookie fields, such as `secure` and `SameSite`.
	- app.UseAuthentication(): Provides authentication support.
		- extend for jwt token
	- app.UseSession(): Provides support for managing user sessions.
		- not working as it was in asp.net traditional
		- session object under **HttpContext.Session** backed by a **cache**.
	- app.UseMvc: Processes requests with MVC/Razor Pages
### ErrorLog
### Request&Response Logging
### Authorization
- For example:
	```
	public async Task Invoke(HttpContext context)
	{
	    var authHeader = context.Request.Headers.Get("Authorization");
	    if (authHeader != null && authHeader.StartsWith("basic", StringComparison.OrdinalIgnoreCase))
	    {
	        var token = authHeader.Substring("Basic ".Length).Trim();
	        System.Console.WriteLine(token);
	        var credentialstring = Encoding.UTF8.GetString(Convert.FromBase64String(token));
	        var credentials = credentialstring.Split(':');
	        if(credentials[0] == "admin" && credentials[1] == "admin")
	        {
	            var claims = new[] { new Claim("name", credentials[0]), new Claim(ClaimTypes.Role, "Admin") };
	            var identity = new ClaimsIdentity(claims, "Basic");
	            context.User = new ClaimsPrincipal(identity);
	        }
	    }
	    else
	    {
	        context.Response.StatusCode = 401;
	        context.Response.Headers.Set("WWW-Authenticate", "Basic realm=\"dotnetthoughts.net\"");
	    }
	    await _next(context);
	}
	```
## [Routing](https://medium.com/quick-code/routing-in-asp-net-core-c433bff3f1a4)
- **Conventional routing**
	- Definition
		- during application **startup**, process will try to map to a controller and a method inside it. 
		- If no route is found for the incoming request, an HTTP error of 404 (Not Found) will be returned to the caller.
		`"{controller=Home}/{action=Index}/{id?}"`
	- Define a new route template
		- use the `UseMvc` method, instead of `UseMvcWithDefaultRoute` inside the `Configure` method in your startup class.
		- **For example**: if we want our application to support not only the default route, but also expose the RESTful API with an `api` prefix
			```
			public void Configure(IApplicationBuilder app, IHostingEnvironment env)
			{
				...
				app.UseMvc(routes =>
					{
						routes
						   .MapRoute(name: "default", template: "{controller=Home}/{action=Index}/{id?}")
						   .MapRoute(name: "api", template: "api/{controller}/{action}/{id?}");
					});
			}
			```
- **Attribute-based routing**
	- allows you to control the exact route that each controller and action takes part in by using the attributes that decorate your controllers and methods.
		- **For example**: The **`RouteAttribute`** attribute that decorates the `ProductController`contains the URL template that maps to this controller. In this case, every request with a URL prefixed with `/api/products/` will be routed to this controller.
			```
			[Route("api/Products")] 
			[ApiController] 
			public class ProductsController : Controller { ... }
			```
	- The **`Http[Verb]Attribute`** and `RouteAttribute` attributes can be assigned multiple times to define multiple(overloaded URI) routes, and are hierarchical
		- **For example**: here is how you can configure that the `ProductsController.GetProducts` method will be mapped to a `HttpGet` request to the URL `/api/products/all`, in addition to the URL `/api/products`
			```
			[HttpGet] 
			[HttpGet("all")] 
			public string[] GetProducts() { ... }
			```
	- **predefined tokens** that are placed in square brackets (`[` and `]`), 
		- **For example**: `[controller]`: This will be replaced with the controller name. `[action]`: This will be replaced with the method name.
			- `[Route("api/[controller]")]` on Controller level 
			- `[Route("[action]/{country}")]` on Method level
	- **Tokens** within curly braces (`{}`) define route parameters that will be bound to the method parameters if the route is matched.
		- **For example**: suppose you wish to expose an API to search for products, based on a keyword, in the form of a `GET`request to a URL formatted as `/api/products/search/keyword`
			```
			[HttpGet("search/{keyword}")] 
			public string[] SearchProducts(string keyword) 
			{ ... }
			```
	- **Default Values**
		- Default values are defined by placing an equals sign next to the route parameter. 
		- Note that placing default values on the method parameters will not work.
		- **For example**: allows the user to search for products by specifying a category and a sub-category; however, the sub-category is optional, and if it is omitted, the default `subcategory` will be `all`.
			```
			[HttpGet("searchcategory/{category}/{subcategory=all}/")] 
						public string[] SearchByProducts(string category,string subcategory) { 
							return new[] { $"Category: {category}, Subcategory: {subcategory}" }; 
						}
			```
	- **Constraints**
		- Inline constraints inside the routing attributes are used by placing a colon with the constraint name `:constraint-name` after the route parameter name
		- For example: The URL for this action must constrain, id parameter to id>1 and the date parameter to `datetime` formats only; therefore, we will use the`datetime`constraint
			```
			[HttpGet("search/{id:int:min(1)}/{date:datetime}/{keyword}/")] 
			public string[] Search(int id, string date, string keyword) 
			{ 
				return new[] { $"Date: {date}, keyword: {keyword}" }; 
			}
			```
## HttpHeader
## HttpStatusCode
- 200: Ok
- 400: Bad request.  User ID must be an integer and larger than 0.
- 401: Authorization information is missing or invalid.
- 404: Routing info is invalid
- 5XX: Unexpected error/Internal server error
## .Net Core Dependency Injection
### [**.Net Core self dependency injection lifetime types**](https://devblogs.microsoft.com/cesardelatorre/comparing-asp-net-core-ioc-service-life-times-and-autofac-ioc-instance-scopes/):
1. **AddSingleton** - creates a single instance throughout the application. It creates the instance for the first time and reuses the same object in the all calls.
	- **Autofac -> InstancePerDependency()**
		- A unique instance will be returned from each object request.
2. **AddTransient** - created each time they are requested, a new instance is provided to every controller and every service. This lifetime works best for lightweight, stateless services.
	- **Autofac -> InstancePerLifetimeScope()**
		- A component with per-lifetime scope will have at most a single instance per nested lifetime scope.
		- This is useful for objects specific to a single unit of work that may need to nest additional logical units of work. Each nested lifetime scope will get a new instance of the registered dependency.
		- For example, this type of lifetime scope is useful for Entity Framework DbContext objects (Unit of Work pattern) to be shared across the object scope so you can run transactions across multiple objects.
	- **Autofac -> InstancePerRequest()**
		- Application types like ASP.NET Core naturally lend themselves to “request” type semantics. You have the ability to have a sort of “singleton per request.”
		- Instance per request builds on top of instance per matching lifetime scope by providing a well-known lifetime scope tag, a registration convenience method, and integration for common application types. Behind the scenes, though, it’s still just instance per matching lifetime scope.
3. **AddScoped** - same within a request, but different across different requests lifetime services are created once per request within the scope. It is equivalent to Singleton in the current scope.
	- **Autofac -> SingleInstance()**
		- One instance is returned from all requests in the root and all nested scopes
	> eg. in MVC it creates 1 instance per each http request but uses the same instance in the other calls within the same web request.
## Web Api with other technology
### compare web api vs mvc
- web api (`ApiController`) is used as a REST to generate HTTP services that reach more clients by generating data in raw format, for example, plain XML or JSON string. It does not have a view engine (Razor View Engine)
- mvc (`Controller`) is to generate Views (HTML) as well as data.
- in .Net core, there is no distinction between those(`Controller` vs `ControllerBase`).
### compare web api vs  wcf vs web service
-  **Web API**  
	- it is a framework for building non-SOAP based services over **HTTP only**. 
	- it use the full featues of HTTP (like URIs, request/response headers, caching, versioning, various content formats)
	- Responses are formatted into JSON, XML or OData
- **WCF**  
	- It is designed to exchange standard SOAP-based messages 
	- It uses variety of transport protocols like **HTTP, TCP, NamedPipes or MSMQ, etc.**
	- its tedious and extensive configuration.
- **Web Service**/web method
	- It is based on SOAP and return data in XML form.
	- It support **only HTTP**.
	- It can be hosted **only on IIS**.
- **WCF Rest**
	- `WebHttpBinding` to be enabled for WCF Rest.
	- For each method, there have to be attributes like – `“WebGet”` and `“WebInvoke”`
	- For GET and POST verbs respectively.
## State Managment
- **Cookie**: 
	- **Definition**:
		- Cookies store data across requests. 
		- Because cookies are sent with every request, their size should be kept to a minimum. 
		- Ideally, only an identifier should be stored in a cookie with the data stored by the app. 
		- Most browsers restrict cookie size to 4KB.
	- **When would we use**
		- Cookies are often used for personalization, where content is customized for a known user. The user is only identified and not authenticated in most cases. 
		- The cookie can store the user's name, account name, or unique user ID (such as a GUID).
		- You can then use the cookie to access the user's personalized settings, such as their preferred website background color.
- **Session state**
	- **Definition**:
		- ASP.NET Core maintains session state by providing a cookie to the client that contains a session ID, which is sent to the app with each request. The app uses the session ID to fetch the session data.
	- **Behaviors**:
		- Because the session cookie is specific to the browser, sessions aren't shared across browsers.
		- Session cookies are deleted when the browser session ends.
		- The app retains a session for a limited time after the last request. The app either sets the session timeout or uses the default value of 20 minutes. 
		- Session state is ideal for storing user data that's specific to a particular session but where the data doesn't require permanent storage across sessions.
		- The in-memory cache provider stores session data in the memory of the server where the app resides.
	- **Note**:
		- Session isn't supported in `SignalR` apps because may execute independent of an HTTP context.
		- Don't store sensitive data in session state. 
			- The user might not close the browser and clear the session cookie. Some browsers maintain valid session cookies across browser windows. 
			- A session might not be restricted to a single user—the next user might continue to browse the app with the same session cookie.
- **Query strings**
	- **Definition**
		- A limited amount of data can be passed from one request to another by adding it to the new request's query string. 
		- This is useful for capturing state in a persistent manner that allows links with embedded state to be shared through email or social networks. 
	- **Note**
		- Because URL query strings are public, never use query strings for sensitive data.
		- Including data in query strings can create opportunities for `Cross-Site Request Forgery (CSRF)` attacks, which can trick users into visiting malicious sites while authenticated
- **Hidden fields**
	- **Definition**
		- Data can be saved in **hidden form fields** and **posted back** on the **next request**.
- **HttpContext.Items**
	- **Definition**
		- The `HttpContext.Itemscollection` is used to store data while processing a single request. 
		- The collection's contents are discarded after a request is processed. 
		- The `Items` collection is often used to allow components or middleware to communicate when they operate at different points in time during a request and have no direct way to pass parameters.
	- **For example**
		- In the following example, middlewareadds `isVerified` to the `Items` collection.
			```
			app.Use(async (context, next) => 
			{ 
				// perform some verification 
				context.Items["isVerified"] = true; await next.Invoke(); 
			});
			```
		- Later in the pipeline, another middleware can access the value of `isVerified`:
			```
			app.Run(async (context) => 
			{ 
				await context.Response.WriteAsync($"Verified: {context.Items["isVerified"]}"); });
			```
- **Cache**
	- **Definition**
		- The app can control the lifetime of cached items.
		- Cached data isn't associated with a specific request, user, or session. 
		- **Be careful not to cache user-specific data that may be retrieved by other users' requests.**
## [Asp.net Core Module]([https://docs.microsoft.com/en-us/aspnet/core/host-and-deploy/aspnet-core-module?view=aspnetcore-2.2#aspnet-core-module-description](https://docs.microsoft.com/en-us/aspnet/core/host-and-deploy/aspnet-core-module?view=aspnetcore-2.2#aspnet-core-module-description))
-   Host an ASP.NET Core app inside of the **IIS worker** process (`w3wp.exe`), called the  **in-process hosting model**. When hosting in-process, the module uses an in-process server implementation for IIS, called IIS HTTP Server (`IISHttpServer`).
```
<PropertyGroup>  
	<AspNetCoreHostingModel>InProcess</AspNetCoreHostingModel>  
</PropertyGroup>
```
-   Forward web requests to a backend ASP.NET Core app running the  **Kestrel server**, called the  **out-of-process hosting model**. When hosting out-of-process, the module only works with Kestrel. The module is incompatible with _HTTP.sys_
```
<PropertyGroup>  
	<AspNetCoreHostingModel>OutOfProcess</AspNetCoreHostingModel>  
</PropertyGroup>
```
- If the `<AspNetCoreHostingModel>` property isn't present in the file, the default value is `OutOfProcess`.
- For Startup Error like `500.0 In-Process/Out-Of-Process Handler Load Failure`, use **Event Viewer->Windows Logs->Application** to find cause. 
## What's the difference between SDK and Runtime in .NET Core?
-  The SDK is all of the stuff that is needed/makes developing a .NET Core application easier, such as the CLI and a compiler.
-  The runtime is the "virtual machine" that hosts/runs the application and abstracts all the interaction with the base operating system.
## Why use ForwardedHeaders
- In the recommended configuration for ASP.NET Core, the app is hosted using IIS/ASP.NET Core Module, Nginx, or Apache. Proxy servers, load balancer, and other network appliances often obscure information about the request before it reaches the app:
        - When HTTPS requests are proxied over HTTP, the original scheme (HTTPS) is lost and must be forwarded in a header.
        - Because an app receives a request from the proxy and not its true source on the Internet or corporate network, the originating client IP address must also be forwarded in a header.
## Security
### CSRF Attack
- Cross-site request forgery is an attack against web-hosted apps whereby a malicious web app can influence the interaction between a client browser and a web app that trusts that browser. This form of exploit is also known as a _one-click attack_ or _session riding_ because the attack takes advantage of the user's previously authenticated session.
- CSRF attacks are possible against web apps that use cookies for authentication
- For example:
	- A user signs into `www.good-banking-site.com` using forms authentication. The server authenticates the user and issues a response that includes an authentication cookie. The site is vulnerable to attack because it trusts any request that it receives with a valid authentication cookie.
	- The user visits a malicious site, `www.bad-crook-site.com`.
	- Notice that the form's `action` **posts to the vulnerable site**, not to the malicious site. This is the "cross-site" part of CSRF.
		```
		<form  action="http://good-banking-site.com/api/account"  method="post"></form>
		``` 
	- The user selects the submit button. The browser makes the request and automatically includes the authentication cookie for the requested domain,  `www.good-banking-site.com`.
	- The request runs on the  `www.good-banking-site.com`  server with the user's authentication context and can perform any action that an authenticated user is allowed to perform.
	- In addition to the scenario where the user selects the button to submit the form, the malicious site could:
		- Run a script that automatically submits the form.
		- Send the form submission as an AJAX request.
		- Hide the form using CSS.


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3MDQ3MjA5NzEsMTc2ODQ3NjYxMSw5ND
U1NTg2NjUsLTc4MjcwMjM3NSwxMTY4OTgyNDQsLTE5OTAwMzA0
NzVdfQ==
-->