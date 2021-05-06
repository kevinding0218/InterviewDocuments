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
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTUzOTAwMjcyOF19
-->