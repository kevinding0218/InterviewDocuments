  * [Why SPA is better](#why-spa-is-better)
  * [How did SPA work](#how-did-spa-work)
  * [What is difference between traditional web app](#what-is-difference-between-traditional-web-app)
  * [Advantage and Disadvantage](#advantage-and-disadvantage)
  * [What are the key components of Angular](#what-are-the-key-components-of-angular)
  * [What are directives?](#what-are-directives-)
  * [What are the various kinds of directives?](#what-are-the-various-kinds-of-directives-)
  * [What is the difference between constructor and ngOnInit?](#what-is-the-difference-between-constructor-and-ngoninit-)
  * [Angular Lifecycle](#angular-lifecycle)
  * [@Injectable decorator](#-injectable-decorator)
  * [dependency injection in Angular](#dependency-injection-in-angular)
  * [How do you categorize data binding types?](#how-do-you-categorize-data-binding-types-)
- [Pipe](#pipe)
  * [What are pipes?](#what-are-pipes-)
  * [What is a parameterized pipe?](#what-is-a-parameterized-pipe-)
  * [Example of custom pipe](#example-of-custom-pipe)
  * [What is the purpose of async pipe?](#what-is-the-purpose-of-async-pipe-)
  * [What is the difference between pure and impure pipe?](#what-is-the-difference-between-pure-and-impure-pipe-)
- [Observable](#observable)
  * [What are observables?](#what-are-observables-)
  * [How to use HttpClient with an example](#how-to-use-httpclient-with-an-example)
  * [How can you read full response?](#how-can-you-read-full-response-)
  * [What is the difference between promise and observable?](#what-is-the-difference-between-promise-and-observable-)
  * [How do you perform error handling in observables?](#how-do-you-perform-error-handling-in-observables-)
  * [What is the short hand notation for subscribe method?](#what-is-the-short-hand-notation-for-subscribe-method-)
- [Routing](#routing)
  * [What is the purpose of base href tag`<base href="/">`?](#what-is-the-purpose-of-base-href-tag--base-href-------)
  * [What is router outlet?](#what-is-router-outlet-)
  * [eager, lazy and preloading](#eager--lazy-and-preloading)
  * [What is activated route?](#what-is-activated-route-)
  * [How do you define routes?](#how-do-you-define-routes-)
  * [What is the purpose of Wildcard route?](#what-is-the-purpose-of-wildcard-route-)
- [Compilation](#compilation)
  * [What are different types of compilation in Angular?](#what-are-different-types-of-compilation-in-angular-)
  * [What is JIT?](#what-is-jit-)
  * [What is AOT?](#what-is-aot-)
  * [HttpInterceptor](#httpinterceptor)
- [Rxjs](#rxjs)
  * [Subject](#subject)
  * [BehaviorSubject](#behaviorsubject)
- [Rxjs Operator](#rxjs-operator)
- [Ngrx](#ngrx)
  * [Core Concept](#core-concept)
  * [Difference with two-way data    binding](#difference-with-two-way-data----binding)
  * [Advantages](#advantages)
  * [Dispatch](#dispatch)
  * [Sample Application](#sample-application)
  * [Taking Advantage of ChangeDetection.OnPush](#taking-advantage-of-changedetectiononpush)
- [Improve Angular Performancee](#improve-angular-performancee)
  * [Using OnPush](#using-onpush)
  * [Use of trackBy](#use-of-trackby)
  * [Avoid Computing Values in the Template](#avoid-computing-values-in-the-template)
  * [Disable Change Detection](#disable-change-detection)
  * [Using Lazy Loading](#using-lazy-loading)
  * [Use of native JavaScript or Lodash](#use-of-native-javascript-or-lodash)
  * [Chrome Dev-tool profiler to identify performance bottlenecks:](#chrome-dev-tool-profiler-to-identify-performance-bottlenecks-)
- [GraphQL - by Facebook](#graphql---by-facebook)
  * [Definition](#definition)
  * [Why use GraphQL](#why-use-graphql)
  * [Apollo GraphQL (Angular)](#apollo-graphql--angular-)
  * [What is a service worker and its role in Angular?](#what-is-a-service-worker-and-its-role-in-angular-)
  * [What is Angular Ivy?](#what-is-angular-ivy-)
  * [What are the features included in ivy preview?](#what-are-the-features-included-in-ivy-preview-)
 
### Why SPA is better
- A single is a web application which only load a single page to the browser.
	- It's different than server rending page which is using the concept of dynamical overloading the content of the page.
### How did SPA work
	- When the application launches and browser send the first request to sever, will response with a index.html, this is the only time the response will return a HTML file.
	- Later on, javascript will control the index.html page, all continuing call will only return JSON data, application will use the JSON data to refresh the page content, but page itself will never be loading again,the browser will take the job to handle transfer data to HTML, most SPA framework generates HTML by a template in browser.
### What is difference between traditional web app
	- In Traditional web app, every time when there is request to server, server will load the entire HTML page, client side will receive the HTML page and refresh it.
		- Client (Initial Request) --> Server (HTML Response) --> Client (Form Post Request) --> Server(HTML Response)
	- Single Application App
		- Client (Initial Request) --> Server (HTML Response) --> Client (AJAX Request) --> Server (JSON Response)
### Advantage and Disadvantage
	- **Advantage**: Client won't have to send any HTML to server which saves lots of time and resource,faster data refresh and less network resource because of less HTML tag, typically only one HTML, one CSS and one Javascript package will be used when construct SPA, these static content could be hosted by any server like NgInx, Apache, etc
	- **Disadvantage**: It cannot be imported into search engine index, because it doesn't have other HTML tag other than the initial index.html, 
### What are the key components of Angular
1.  **Component:**  These are the basic building blocks of angular application to control HTML views.
2.  **Modules:**  An angular module is set of angular basic building blocks like component, directives, services etc. An application is divided into logical pieces and each piece of code is called as "module" which perform a single task.
3.  **Templates:**  This represent the views of an Angular application.
4.  **Services:**  It is used to create components which can be shared across the entire application.
5.  **Metadata:**  This can be used to add more data to an Angular class.(1.  **Class decorators**, e.g. @Component and @NgModule)
### What are directives?
- Directives **add behavior to an existing DOM element or an existing component instance**.
- **For example**:
	```typescript
	import { Directive, ElementRef, Input } from '@angular/core';

	@Directive({ selector: '[myHighlight]' })
	export class HighlightDirective {
	    constructor(el: ElementRef) {
	       el.nativeElement.style.backgroundColor = 'yellow';
	    }
	}
	```
### What are the various kinds of directives?
There are mainly three kinds of directives.
1.  **Components**  — These are directives with a template.
2.  **Structural directives**  — These directives change the DOM layout by adding and removing DOM elements(**NgIf, NgFor and NgSwitch**)
3.  **Attribute directives**  — These directives change the appearance or behavior of an element, component, or another directive.
### What is the difference between constructor and ngOnInit?
- **Constructor**: 
	- The `Constructor` is a **default method of typescript class** that is **executed when the class is instantiated** and **ensures proper initialization of fields** in the class and its subclasses. 
	- **Use for Dependency Injector** (DI) .
	- **called first time before the ngOnInit()**
- **ngOnInit**
	- `ngOnInit` is **specific to Angular** and is **a life cycle hook** called by Angular to **indicate that Angular is done creating the component**.
	- **called after the constructor and called  after the first ngOnChanges()** 
### Angular Lifecycle
1. **Constructor** Typescript method
2.  **ngOnChanges:**  When the value of a data bound property changes, then this method is called.
3.  **ngOnInit:**  This is called whenever the initialization of the directive/component after Angular first displays the data-bound properties happens.
4.  **ngDoCheck:**  This is for the detection and to act on changes that Angular can't or won't detect on its own.
5.  **ngAfterContentInit:**  This is called in response after Angular projects external content into the component's view.
6.  **ngAfterContentChecked:**  This is called in response after Angular checks the content projected into the component.
7.  **ngAfterViewInit:**  This is called in response after Angular initializes the component's views and child views.
8.  **ngAfterViewChecked:**  This is called in response after Angular checks the component's views and child views.
9.  **ngOnDestroy:**  This is the cleanup phase just before Angular destroys the directive/component.
### @Injectable decorator
- The Injectable decorator is **required for dependency injection to work** 
- The [@Injectable()](https://angular.io/api/core/Injectable) decorator marks it as a service that can be injected, but Angular can't actually inject it anywhere until you configure an Angular dependency injector with a [provider](https://angular.io/guide/glossary#provider) of that service either `within service` or `@Component().provider or @NgModule().providers`
- **For example**
	```typescript
	import { Injectable } from '@angular/core';
	import { Http } from '@angular/http';

	@Injectable({ 
	  // providedIn option registers the service with a specific NgModule
	  providedIn: 'root',  // This declares the service with the root app (AppModule)
	})
	export class RepoService{
	  constructor(private http: Http){
	  }

	  fetchAll(){
	    return this.http.get('https://api.github.com/repositories');
	  }
	}
	```
### dependency injection in Angular
- Dependencies are services or objects that a class needs to perform its function. DI is a coding pattern in which **a class asks for dependencies from external sources rather than creating them itself**.
- **Angular has its own DI framework**, which is typically used in the design of Angular applications to increase their efficiency and modularity.
- In Angular, the DI framework provides declared dependencies to a class when that class is instantiated.
### How do you categorize data binding types?
| Data direction | Syntax | Type |
|---- | --------- | ---- |
| From the source-to-view(One-way)  | 1. {{expression}} 2. [target]="expression" 3. bind-target="expression" | Interpolation, Property, Attribute, Class, Style|
  From view-to-source(One-way) | 1. (target)="statement" 2. on-target="statement" | Event |
| View-to-source-to-view(Two-way)| 1. [(target)]="expression" 2. bindon-target="expression"| Two-way |
## Pipe
### What are pipes?
- A pipe **takes in data as input and transforms it to a desired output**.
- **For example** `template: <p>Birthday is {{ birthday | date }}</p>`
### What is a parameterized pipe?
- A pipe can **accept any number of optional parameters** to fine-tune its output. 
- The parameterized pipe can be created **by declaring the pipe name with a colon ( : ) and then the parameter value**. 
- If the pipe accepts multiple parameters, **separate the values with colons**.
- **For example** `template: <p>Birthday is {{ birthday | date:'dd/mm/yyyy'}}</p>// 18/06/1987` 
### Example of custom pipe
- You can create custom reusable pipes for the transformation of existing value. 
	- A pipe is a class decorated with **pipe metadata @Pipe decorator**
	- The pipe class **implements the PipeTransform interface's** transform method that **accepts an input value followed by optional parameters and returns the transformed value**
	- The @Pipe decorator allows you to **define the pipe name that you'll use within template expressions**. It must be a valid JavaScript identifier.
- For example, let us create a custom pipe for finding file size based on an extension
	```typescript
	import { Pipe, PipeTransform } from '@angular/core';

	@Pipe({name: 'customFileSizePipe'})
	export class FileSizePipe implements PipeTransform {
	  transform(size: number, extension: string = 'MB'): string {
	    return (size / (1024 * 1024)).toFixed(2) + extension;
	  }
	}
	// use it like
	<p>Size: {{288966 | customFileSizePipe: 'GB'}}</p>
	```
### What is the purpose of async pipe?
- The AsyncPipe **subscribes to an observable or promise** and **returns the latest value it has emitted**. 
- When a new value is emitted, the pipe marks the component to be checked for changes.
### What is the difference between pure and impure pipe?
- A **pure pipe** is **only called when Angular detects a change in the value or the parameters passed to a pipe**. 
	- For example, **any changes to a primitive input value** (String, Number, Boolean, Symbol) **or a changed object reference** (Date, Array, Function, Object). 
- An **impure pipe** is called **for every change detection cycle no matter whether the value or parameters changes**. 
	- For example, An **impure pipe** is called often, as often as every **keystroke or mouse-move**.
## Observable
### What are observables?
- **Observables** are declarative which **provide support for passing messages between publishers and subscribers** in your application. 
- They are mainly used for event handling, asynchronous programming, and handling multiple values. 
	- In this case, you define **a function for publishing values, but it is not executed until a consumer subscribes to it.** 
	- The **subscribed consumer then receives notifications until the function completes, or until they unsubscribe**.
### How to use HttpClient with an example
1. Import HttpClient into root module:
	```typescript
	import { HttpClientModule } from '@angular/common/http';
	@NgModule({
	  imports: [
	    BrowserModule,
	    // import HttpClientModule after BrowserModule.
	    HttpClientModule,
	  ],
	  ......
	})
	 export class AppModule {}
	```
2. Inject the HttpClient into the application
	```typescript
	import { Injectable } from '@angular/core';
	import { HttpClient } from '@angular/common/http';

	const userProfileUrl: string = 'assets/data/profile.json';
	const httpOptions =  { headers:  new HttpHeaders({  
					'Content-Type':  'application/json', 
					'Accept':  'application/json', 
					'Authorization':  'Bearer XXXX'  
					})  
				};

	@Injectable()
	export class UserProfileService {
	  constructor(private http: HttpClient) { }
	  
	  getUserProfile() {
	    return this.http.get(this.userProfileUrl);
	  }
	  /** POST: add a new hero to the database */ 
	  addHero (user:  User):  Observable<User>  {  
		  return  this.http.post<User>(this.userProfileUrl, user, httpOptions)  
			  .pipe( 
				  catchError(this.handleError('addUser', user))  
				);  
		}	
	}
	```
3. Create a component for subscribing service
	```typescript
	fetchUserProfile() {
	  this.userProfileService.getUserProfile()
	    .subscribe((data: User) => this.user = {
	        id: data['userId'],
	        name: data['firstName'],
	        city:  data['city']
	    });
	}
	```
### How can you read full response?
- Inorder to get full response, you should **use observe option from HttpClient**,
	```typescript
	getUserResponse(): Observable<HttpResponse<User>> {
	  return this.http.get<User>(
	    this.userUrl, { observe: 'response' });
	}
	```
- Now HttpClient.get() method **returns an Observable of typed HttpResponse rather than just the JSON data**.
### What is the difference between promise and observable?
| Observable | Promise |
|---- | --------- |
| Declarative: Computation does not start until subscription so that they can be run whenever you need the result | Execute immediately on creation|
| Provide multiple values over time | Provide only one |
| Subscribe method is used for error handling which makes centralized and predictable error handling| Push errors to the child promises |
| Provides chaining and subscription to handle complex applications | Uses only .then() clause |
### How do you perform error handling in observables?
- You can handle errors by specifying an  **error callback**  on the observer instead of relying on try/catch which are ineffective in asynchronous environment. 
- **For example**, you can define error callback as below,
	```
	myObservable.subscribe({
	  next(num) { console.log('Next num: ' + num)},
	  error(err) { console.log('Received an errror: ' + err)}
	});
	```
### What is the short hand notation for subscribe method?
- The subscribe() method can accept callback function definitions in line, for **next, error, and complete handlers is known as short hand notation** or Subscribe method with positional arguments. 
- **For example**, you can define subscribe method as below,
	```
	myObservable.subscribe(
	  x => console.log('Observer got a next value: ' + x),
	  err => console.error('Observer got an error: ' + err),
	  () => console.log('Observer got a complete notification')
	);
	```
## Routing
### What is the purpose of base href tag`<base href="/">`?
- The routing application should add element to the index.html as the first child in the tag inorder to indicate how to compose navigation URLs.
### What is router outlet?
- The RouterOutlet is a directive from the router library and it acts as a placeholder that marks the spot in the template where the router should display the components for that outlet.
### eager, lazy and preloading
- The application module i.e. `AppModule` is loaded eagerly before application starts. But the feature modules can be loaded either eagerly or lazily or preloaded.
- **Eager loading**: 
	- To load a feature module eagerly we need to import it into application module using `imports`metadata of `@NgModule` decorator. 
	- Eager loading is useful in small size applications. In eager loading, all the feature modules will be loaded before the application starts. 
	- Hence the subsequent request to the application will be faster.
- **Lazy loading**: 
	- To load a feature module lazily we need to load it using `loadChildren` property in route configuration and that feature module must not be imported in application module. 
	- Lazy loading is useful when the application size is growing. In lazy loading, feature module will be loaded on demand and hence application start will be faster.
- **Preloading**: 
	- In preloading, **feature modules are loaded in background asynchronously**. In preloading, **modules start loading just after application starts**.
	- When we hit the application, first `AppModule` and modules imported by it, will be loaded eagerly. Just after that modules configured for preloading is loaded asynchronously.
	- By default we could set `PreloadAllModules  ` with `preloadingStrategy`
		```typescript
		@NgModule({ 
			imports:  [  
				RouterModule.forRoot(routes,  
				{ preloadingStrategy:  PreloadAllModules  })  
			],  ------  
		})  
		export  class  AppRoutingModule  {  }
		```
	- To customize a preload strategy
		- Create a service that implements Angular's built-in PreloadingStrategy abstract class
			```typescript
			import { Injectable } from  '@angular/core';
			import { PreloadingStrategy, Route } from  '@angular/router';
			import { Observable, of } from  'rxjs';
			@Injectable({
				providedIn: 'root'
			})
			// Since we are creating a Custom Preloading Strategy, this service
			// class must implement PreloadingStrategy abstract class
			export  class CustomPreloadingService implements PreloadingStrategy {
				constructor() { }
				// PreloadingStrategy abstract class has the following preload()
				// abstract method for which we need to provide implementation
				preload(route: Route, fn: () => Observable<any>): Observable<any> {
					// If data property exists on the route of the lazy loaded module
					// and if that data property also has preload property set to
					// true, then return the fn() which preloads the module
					if (route.data && route.data['preload']) {
						return fn();
					// If data property does not exist or preload property is set to
					// false, then return Observable of null, so the module is not
					// preloaded in the background
					} else {
					return of(null);
					}
				}
			}
			```
		- Import  CustomPreloadingService and set it as the Preloading Strategy
			```typescript
			import { CustomPreloadingService } from './custom-preloading.service';

			RouterModule.forRoot(appRoutes, {  
				preloadingStrategy: CustomPreloadingService  
			})
			```
		- Modify the  **'employees'**  route, and set preload property to true or false. 
			- Set it to true if you want the EmployeeModule to be preloaded else false.
			```
			const appRoutes: Routes = [
				{ path: 'home', component: HomeComponent },
				{ path: '', redirectTo: '/home', pathMatch: 'full' },
				{ path: 'employees',
					// set the preload property to true, using the route data property
					// If you do not want the module to be preloaded set it to false
					data: { preload: true },
					loadChildren: './employee/employee.module#EmployeeModule'
				},
				{ path: '**', component: PageNotFoundComponent }
			];
			```
### What is activated route?
- ActivatedRoute contains the information about a route associated with a component loaded in an outlet. It can also be used to traverse the router state tree. 
- The ActivatedRoute will be injected as a router service to access the information. 
- In the below example, you can access route path and parameters
	```typescript
	@Component({...})
	class MyComponent {
	  constructor(route: ActivatedRoute) {
	    const id: Observable<string> = route.params.pipe(map(p => p.id));
	    const url: Observable<string> = route.url.pipe(map(segments => segments.join('')));
	    // route.data includes both `data` and `resolve`
	    const user = route.data.pipe(map(d => d.user));
	  }
	}
	```
### How do you define routes?
- A router must be configured with a list of route definitions. 
- You configures the router with routes via the `RouterModule.forRoot()` method, and adds the result to the AppModule's `imports` array.
	```typescript
	const appRoutes: Routes = [
	 { path: 'todo/:id',      component: TodoDetailComponent },
	 {
	   path: 'todos',
	   component: TodosListComponent,
	   data: { title: 'Todos List' }
	 },
	 { path: '',
	   redirectTo: '/todos',
	   pathMatch: 'full'
	 },
	 { path: '**', component: PageNotFoundComponent }
	];

	@NgModule({
	 imports: [
	   RouterModule.forRoot(
	     appRoutes,
	     { enableTracing: true } // <-- debugging purposes only
	   )
	   // other imports here
	 ],
	 ...
	})
	export class AppModule { }
	```
### What is the purpose of Wildcard route?
- If the URL doesn't match any predefined routes then it causes the router to throw an error and crash the app. In this case, you can use wildcard route. 
- A wildcard route has a path consisting of two asterisks to match every URL. 
- For example, you can define PageNotFoundComponent for wildcard route as below
	```
	{ path: '**', component: PageNotFoundComponent }
	```
## Compilation
### What are different types of compilation in Angular?
- Angular offers two ways to compile your application,
	- Just-in-Time (**JIT**)
	- Ahead-of-Time (**AOT**)
### What is JIT?
- Just-in-Time (**JIT**) is a type of compilation that **compiles** your app in the browser **at runtime**. 
- JIT compilation is the default when you run the **ng build (build only) or ng serve (build and serve locally)** CLI commands.
### What is AOT?
- Ahead-of-Time (**AOT**) is a type of compilation that **compiles** your app **at build time**. - For AOT compilation, include the  `--aot`  option with the ng build or ng serve command as below,
	```
	ng build --aot
	ng serve --aot
	```
    **Note:**  The ng build command with the --prod meta-flag (`ng build --prod`) compiles with AOT by default.
### HttpInterceptor
- By extending the HttpInterceptor class you can create a custom interceptor to modify http requests before they get sent to the server.
- `intercept(req:  HttpRequest<any>, next:  HttpHandler):  Observable<HttpEvent<any>>`
	- **req**: The outgoing request to handle
	- **next**: The next interceptor in the chain, or the backend if no interceptors in the chain.
- For example: add a JWT auth token to the Authorization header if the user is logged in.
	```typescript
	@Injectable()
	export class JwtInterceptor implements HttpInterceptor {
	    constructor(private authenticationService: AuthenticationService) { }

	    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
	        // add authorization header with jwt token if available
	        let currentUser = this.authenticationService.currentUserValue;
	        if (currentUser && currentUser.token) {
	            request = request.clone({
	                setHeaders: {
	                    Authorization: `Bearer ${currentUser.token}`
	                }
	            });
	        }

	        return next.handle(request);
	    }
	}
	```
## Rxjs
### Subject
- Subjects are both `Observables` and `Observers`, meaning you can **subscribe to a Subject**, but can **also subscribe a Subject to a source**. 
- At a high-level Subjects can be thought of as **messengers**, or **proxies**.
- Because Subjects are Observers, you can **'next',** or pass values into the stream directly.
- **Subscribers of that Subject** will then **be notified of emitted values**. In the context of Store, these subscribers could be a Angular service, component, or anything requiring **access to application state**.
- **For example**: Subscribing to a Subject
	```typescript
	//create a subject
	const mySubject = new Rx.Subject();

	//add subscribers
	const subscriberOne = mySubject.subscribe(val => {
	  console.log('***SUBSCRIBER ONE***', val);
	});

	const subscriberTwo = mySubject.subscribe(val => {
	  console.log('***SUBSCRIBER TWO***', val);
	});

	//publish values to observers of subject
	mySubject.next('FIRST VALUE!'); '***SUBSCRIBER ONE*** FIRST VALUE! ***SUBSCRIBER TWO*** FIRST VALUE!'
	mySubject.next('SECOND VALUE!'); '***SUBSCRIBER ONE*** SECOND VALUE! ***SUBSCRIBER TWO*** SECOND VALUE!'
	```
### BehaviorSubject
- **When subscribing to a Subject, only values emitted after the subscription are received**. This is unacceptable in an environment where components will be consistently added and removed, requiring the latest, on-demand sections of state from the application store at the time of subscription.
- **For example**:
	```typescript
	/*
	Now that we have a dispatcher, let's create our store to receieve dispatched actions.
	*/
	class FirstStore extends Rx.Subject{}
	const myFirstStore = new FirstStore();
	//add a few subscribers
	const subscriberOne = myFirstStore.subscribe(val => {
	  console.log('***SUBSCRIBER ONE***: ' + val);
	});
	//For now, lets surpass dispatcher and manually publish values to store
	myFirstStore.next('FIRST VALUE!');
	/* 
	Let's add another subscriber.
	Since our first implementation of store is a subject, 
	subscribers will only have visibility into values emitted *AFTER* they subscribe. In this case, subscriber two will have no knowledge of 'FIRST VALUE!'
	*/
	const subscriberTwo = myFirstStore.subscribe(val => {
	  console.log('***SUBSCRIBER TWO***: ' + val);
	});
	// Output:
	// "***SUBSCRIBER ONE***: FIRST VALUE!"
	```
- BehaviorSubject's **encapsulate all of the functionality of Subject**, but also **return the last emitted value to subscribers** upon subscription. 
- This means **components and services will always have access to the latest** (or initial) application state and all future updates.
- **For example**:
```typescript
/*
Because our components will need to query current state, 
BehaviorSubject is a more natural fit for Store. 
BehaviorSubjects have all the functionality of Subject, 
but also allow for an initial value to be set, 
as well as outputting the last value received to all observers upon subscription.
*/
class Store extends Rx.BehaviorSubject{
  constructor(initialState : any){
    super(initialState);
  }
}
const store = new Store('INITIAL VALUE!');
//add a few subscribers
const storeSubscriberOne = store.subscribe(val => {
  console.log('***STORE SUBSCRIBER ONE***: '+ val);
});
//For demonstration, manually publish values to store
store.next('FIRST STORE VALUE!');

//Add another subscriber after 'FIRST VALUE!' published
//output: ***STORE SUBSCRIBER THREE*** FIRST STORE VALUE!
const storeSubscriberTwo = store.subscribe(val => {
  console.log('***STORE SUBSCRIBER TWO***: ' + val);
});
// Output
// "***STORE SUBSCRIBER ONE***: INITIAL VALUE!"    
// "***STORE SUBSCRIBER ONE***: FIRST STORE VALUE!" 
// "***STORE SUBSCRIBER TWO***: FIRST STORE VALUE!"
```
## Rxjs Operator
- **of(null)** : return a null observable
- **switchMap**: 
	- provides an easy mechanism to cancel these in-flight network requests.
	- Consider the case where you selected a Netflix video to watch and just after few seconds, you changed your mind and quickly selected another video. 
	- Even though you are not interested in the first video, the API call to fetch the first video will still continue and complete its operation in the background.
	- **For example**:
		```typescript
		import { fromEvent, interval } from 'rxjs';
		import { switchMap } from 'rxjs/operators';
		// When subscribed, obs$1 will emit response for every user click on the page and obs$2 will incrementally emit numbers for every 1 sec. 
		// Without switch map, when combining these two observables, obs$2 will continue its execution even when the user clicks in the document. 
		// But in the case of switch map, obs$2 will cancel its previous execution and starts new execution for every user click in the document.
		const obs$1 = fromEvent(document, 'click');
		const obs$2 = interval(1000);
		const finalObs$ = obs$1.pipe(
		switchMap(event => obs$2));
		const subscription = finalObs$.subscribe((value) => console.log(value));
		```
- **mergeMap**: 
	- to handy when the requirement is to merge response from two observables. 
	- The only thing to notice here is that the final observable subscription will have to wait until the merged observable to emit some response.
	- **For example**:
		```typescript
		import { of } from 'rxjs';
		import { mergeMap, map } from 'rxjs/operators';

		const firstNameObs$ = of('Naveen');
		const lastNameObs$ = of('Chandupatla');
		const finalObs$ = firstNameObs$.pipe(
		mergeMap(event1 => lastNameObs$.pipe(map(event2 => event1+' '+event2))));
		const subscription = finalObs$.subscribe((value) => console.log(value));
		// outputs: Naveen Chandupatla
		```
- **map**: similar to JS map
- **pluck**:  when we just need to pass single field value to the subscription instead of sending entire JSON object.
	- **For example**:
		```typescript
		import { from } from 'rxjs';
		import { pluck } from 'rxjs/operators';

		const data = [{id:1, value:'one'}, {id:2, value:'two'}, {id:3, value:'three'}];
		const obsPluck$ = from(data).pipe(
			pluck('value')
		).subscribe(x => console.log(x));

		const obsMap$ = from(data).pipe(
			map(data => data.value)
		).subscribe(x => console.log(x));
		// Both prints the same below output:
		/*
		one
		two
		three
		*/
		```
- **tap**: 
	- Tap operator is more of a utility operator which can be used to perform transparent actions such as logging.
	- **For example**:
		```typescript
				import { of } from 'rxjs';
				import { tap, map } from 'rxjs/operators';

				const obs$ = of(1, 2, 3, 4, 5);
				obs$.pipe(
					tap(val => console.log(`BEFORE MAP: ${val}`)),
					map(val => val + 10),
					tap(val => console.log(`AFTER MAP: ${val}`))
				).subscribe(val => console.log(val));
				```
- **DEBOUNCE TIME & DISTINCT UNTIL CHANGED**
	- These two operators are commonly used together but they do not have to be piped together always. 
	- These operators are a perfect choice in scenarios **such as type-ahead where the rate of user input must be controlled**.  
	- *debounceTime(1000)* will **make observable to wait for 1 sec before it can emit users input** and *distinctUntilChanged* will **only output distinct values, based on the last emitted value.**
	- **For example**:
		```typescript
		import { fromEvent } from 'rxjs';
		import { pluck,map, debounceTime, distinctUntilChanged } from 'rxjs/operators';

		const input = document.querySelector('input');
		const obs$ = fromEvent(input, 'input');
		obs$.pipe(
			map(event => event.target.value),
			debounceTime(1000),
			distinctUntilChanged()
			).subscribe((value) => console.log(value));
		```
- **filter**: similar like **WHERE in LINQ**
- **reduce**: Reduces the values from source observable to a single value that's emitted when the source completes.
	- For example:
	```javascript
	// RxJS v6+
	import { of } from 'rxjs';
	import { reduce } from 'rxjs/operators';

	const source = of(1, 2, 3, 4);
	const example = source.pipe(reduce((acc, val) => acc + val));
	//output: Sum: 10'
	const subscribe = example.subscribe(val => console.log('Sum:', val));
	```
- **COMBINE LATEST**
	- used to emit only the latest values from multiple observable sources
	- while using this operator is that, combine latest will not emit an initial value until each observable emits at least one value.
- **zip**: will wait for all observable to emit and then it zips those values into an array as an output.
	- **For example**:
		```typescript
		import { timer, zip } from 'rxjs';

		//timerOne emits first value at 1s, then once every 4s
		const timerOne$ = timer(1000, 4000);
		//timerTwo emits first value at 2s, then once every 4s
		const timerTwo$ = timer(2000, 4000);
		//timerThree emits first value at 3s, then once every 4s
		const timerThree$ = timer(3000, 4000);

		//when one timer emits, emit the latest values from each timer as an array
		zip(timerOne$, timerTwo$, timerThree$).subscribe(
		(items) => {
			console.log(
			`Timer One Latest: ${items[0]},
			Timer Two Latest: ${items[1]},
			Timer Three Latest: ${items[2]}`);
			}
		);

		/* Outputs:
		Timer One Latest: 1,
		Timer Two Latest: 1,
		Timer Three Latest: 1

		Timer One Latest: 2,
		Timer Two Latest: 2,
		Timer Three Latest: 2

		Timer One Latest: 3,
		Timer Two Latest: 3,
		Timer Three Latest: 3
		*/
		```
## Ngrx
### Core Concept
- Each application built around store will contain three main pieces, **reducers**, **actions**, and a **single application store**.
	- **Store**: 
		- Like a traditional database represents the point of record for an application, your **store** can be thought of as a **client side** _‘single source of truth’_, or **database**. 
		- By adhering to the  _store contract_ when designing your application, a snapshot of store at any point will supply a complete representation of relevant application state.
		- **Store encompasses our application state**
	- **Reducers**
		- A reducer is a pure function, accepting two arguments, the previous state and an action with a type and optional data (payload) associated with the event. 
		- Using the previous analogy, if store is to be thought of as your client side database, reducers can be considered the tables in said database. 
		- Reducers represent sections, or slices of state within your application and should be structured and composed accordingly.
		- **reducers output sections of that state**
		- **For example**
			```typescript
			export const counter: Reducer<number> = (state: number = 0, action: Action) => {
			    switch(action.type){
			        case 'INCREMENT':
			            return state + 1;
			        case 'DECREMENT':
			            return state - 1;
			        default:
			            return state;
			    }
			};
			```
	- **Actions**
		- Within a store application, all user interaction that would cause a state update must be expressed in the form of actions. 
		- All relevant user events are dispatched as actions, flowing through the action pipeline defined by store, before a new representation of state is output. 
		- This process occurs each time an action is dispatched, leaving a complete, serialize representation of application state changes over time.
		- **action communicate to our reducers when state needs to be updated**
		- **For example**
			```typescript
			//simple action without a payload
			dispatch({type: 'DECREMENT'});

			//action with an associated payload
			dispatch({type: ADD_TODO, payload: {id: 1, message: 'Learn ngrx/store', completed: true}})
			```
	- **Projecting Data**
		- Because store itself is an observable, we have access to the typical **JS collection operations** you are accustom to (**map, filter, reduce**, etc.) along with a wide-array of extremely powerful **RxJS** based **observable operators**. 
		- This makes slicing up store data into any projection you wish quite easy.
			```typescript
			//most basic example, get people from state
			store.select('people')
			//combine multiple state slices
			Observable.combineLatest(
			  store.select('people'),
			  store.select('events'),
			  (people, events) => {
			    //projection here
			})
			```
### Difference with two-way data 	binding
- Store promotes the idea of one-way data flow and explicitly dispatched actions. All state updates are handled above your components in store, delegated to reducers. 
- The only way to initiate a state update in your application is through dispatched actions, corresponding to a particular reducer case. 
- This not only makes reasoning about state changes in your application easier, as updates are centralized, it leaves a clear audit trail in case of error.
	- Non-store example:
		```typescript
		@Component({
		    selector: 'counter',
		    template: `
		 <div class="content">
		 <button (click)="increment()">+</button>
		 <button (click)="decrement()">-</button>
		 <h3>{{counter}}</h3>
		 </div>
		 `
		})
		export class Counter{
		    counter = 0;

		    increment(){
		        this.counter += 1;
		    }

		    decrement(){
		        this.counter -= 1;
		    }
		}
		```
	- Store example:
		```typescript
		@Component({
		    selector: 'counter',
		    template: `
		 <div class="content">
		 <button (click)="increment()">+</button>
		 <button (click)="decrement()">-</button>
		 <h3>{{counter$ | async}}</h3>
		 </div>
		 `,
		    changeDetection: ChangeDetectionStrategy.OnPush
		})
		export class Counter{
		    counter$: Observable<number>;

		    constructor(
		        private store : Store<number>
		    ){
		        this.counter$ = this.store.select('counter')
		    }

		    increment(){
		        this.store.dispatch({type: 'INCREMENT'});
		    }

		    decrement(){
		        this.store.dispatch({type: 'DECREMENT'});
		    }
		}
		```
### Advantages
- Centralized, Immutable State
	- All relevant application state exists in one location. 
	- This makes it easier to track down problems, as a snapshot of state at the time of an error can provide important insight and make it easy to recreate issues.
- Performance
	- Since state is centralized at the top of your application, data updates can flow down through your components relying on slices of store. 
	- Angular is built to optimize on such a data-flow arrangement, and can disable change detection in cases where components rely on Observables which have not emitted new values.
- Testability
	- All state updates are handled in reducers, which are pure functions. 
	- Pure functions are extremely simple to test, as it is simply input in, assert against output.
	- This enables the testing of the most crucial aspects of your application without mocks, spies, or other tricks that can make testing both complex and error prone.
- Tooling and Ecosystem
### Dispatch
- In Store (and Redux), to maintain this API, the `Dispatcher`extends Subject, adding a `dispatch` method as a passthrough to the classic `next` method. 
- This is used to pass values into the Subject before emitting these values to subscribers.
- **For example**:  Extending Subject as Dispatcher
```typescript
/*
redux/ngrx-store has a concept of a dispatcher, or method to send actions to application store
lets extend Rx.Subject with our Dispatcher class to maintain familiar terms.
*/

//inherit from subject
class Dispatcher extends Rx.Subject{
  dispatch(value : any) : void {
    this.next(value);
  }
}

//create a dispatcher (just a Subject with wrapped next method)
const dispatcher = new Dispatcher();

//add subscribers
const subscriberOne = dispatcher.subscribe(val => {
  console.log('***SUBSCRIBER ONE***', val);
});

const subscriberTwo = dispatcher.subscribe(val => {
  console.log('***SUBSCRIBER TWO***', val);
});

//publish values to observers of dispatcher
dispatcher.dispatch('FIRST DISPATCHED VALUE!');
dispatcher.dispatch('SECOND DISPATCHED VALUE!');
```
### Sample Application
1. Setup the Reducer
	- Reducers are the foundation to your store application. 
	- As the application _store_ maintains state, reducers are the workhorse behind the manipulation and output of new state representations as actions are dispatched. 
	- Each reducer should be focused on a specific section, or slice of state, similar to a table in a database.
	- **Never mutate previous state and always return a new representation of state when a relevant action is dispatched**. 
		- If you are new to store or the Redux pattern this takes some practice to feel natural. **Instead of using mutative methods** like `push`, or reassigning values to previously existing objects, you will instead lean on none mutating methods like **`concat`** and **`Object.assign`** **to return new values**.
		- **Object.assign(target, source)**: The **`Object.assign()`** method is used to copy the values of all enumerable own properties from one or more source objects to a target object. It will return the target object.
		- **Triple dot(...)**:
			- When three dots (…) occurs in a function call or alike, it's called a "spread operator" and expands an array into a list. Think of it as a replacement for **Array.prototype.concat**
				```javascript
				const numbers1 = [1, 2, 3, 4, 5];
				const numbers2 = [ ...numbers1, 1, 2, 6,7,8]; 
				// numbers2 will be [1, 2, 3, 4, 5, 1, 2, 6, 7, 8]			
				```
			- When three dots (…) is at the end of function parameters, it's "rest parameters", When it is used like that, the rest operator enables the developer to create functions that can take an indefinite number of arguments
				```javascript
				function sum(...numbers) {
				    return numbers.reduce((accumulator, current) => {
				        return accumulator += current
				    });
				};
				sum(1, 2) // 3
				```
			
	- **For example**, People Reducer
		- The people reducer needs to handle five actions, _adding a person_, _removing a person_, _adding guests to a person_, _removing guests from a person_, and _toggling whether they are attending the event_.
		- To do this, we will create a reducer function, accepting previous state and the currently dispatched action. We then need to implement a case statement that performs the correct state recalculation when a relevant action is dispatched.
			```typescript
			//remember to avoid mutation within reducers
			export const people = (state = [], action) => {
			  switch(action.type){
			    case ADD_PERSON:
			      return [
			        ...state,
			        Object.assign({}, {id: action.payload.id, name: action.payload.name, guests:0, attending: false})
			      ];
			    case REMOVE_PERSON:
			      return state
			        .filter(person => person.id !== action.payload);
			     //to shorten our case statements, delegate detail updates to second private reducer 
			    case ADD_GUEST:
			      return state.map(person => details(person, action));
			    case REMOVE_GUEST:
			      return state.map(person => details(person, action));
			    case TOGGLE_ATTENDING:
			      return state.map(person => details(person, action));
			     //always have default return of previous state when action is not relevant 
			    default:
			      return state;
			  }
			}
			// People Detail
			const details = (state, action) => {
			  switch(action.type){
			    case ADD_GUEST:
			      if(state.id === action.payload){
			          return Object.assign({}, state, {guests: state.guests + 1});
			      }
			      return state;
			    case REMOVE_GUEST:
			      if(state.id === action.payload){
			          return Object.assign({}, state, {guests: state.guests - 1});
			        }
			      return state;
			    case TOGGLE_ATTENDING:
			      if(state.id === action.payload){
			          return Object.assign({}, state, {attending: !state.attending});
			      }
			      return state;
			    default:
			      return state;
			  }
			}
			```
2. Configuring Store Actions
	- The only way to modify state within a store application is by dispatching actions. 
	- Because of this, a log of actions should present a clear, readable, history of user interaction. 
	- Actions are generally defined as string constants or as static string values on services encapsulating a particular action type.
		```typescript
		//Person Action Constants
		export const ADD_PERSON = 'ADD_PERSON';
		export const REMOVE_PERSON = 'REMOVE_PERSON';
		export const ADD_GUEST = 'ADD_GUEST';
		export const REMOVE_GUEST = 'REMOVE_GUEST';
		export const TOGGLE_ATTENDING = 'TOGGLE_ATTENDING';
		```
3. Utilizing Container Components
	- **Smart**, or **Container** Components
		- Components should be your **root level**, **routable components**. These components generally have **direct access to store or a derivative**.
		- Smart components **handle view events** and the **dispatching of actions**, whether through a service or directly. 
		- Smart components also **handle the logic behind events emitted up from child components** within the same view.
	- **Dumb**, or **Child** Components
		- Components generally for presentation only, relying exclusively on `@Input` parameters, acting on the received data in an appropriate manner. 
		- When relevant events occur in dumb components, they are emitted up to be handled by a parent smart component. 
		- Dumb components will make up the majority of your application, as they should be small, focused, and reusable.
	- **For example**: The party planner application will need a single container component. This component will be responsible for passing appropriate state down to each child component and dispatching actions based on events emitted by our dumb components, `person-input`, `person-list`, and in the future `party-stats`
		```typescript
		// Container Component
		@Component({
		    selector: 'app',
		    template: `
		 <h3>@ngrx/store Party Planner</h3>
		 <person-input
		 (addPerson)="addPerson($event)"
		 >
		 </person-input>
		 <person-list [people]="people" (addGuest)="addGuest($event)" (removeGuest)="removeGuest($event)" (removePerson)="removePerson($event)" (toggleAttending)="toggleAttending($event)">
		 </person-list>
		 `,
		    directives: [PersonList, PersonInput]
		})
		export class App {
		    public people;
		    private subscription;
		    
		    constructor(
		     private _store: Store
		    ){
		      this.subscription = this._store
		        .select('people')
		        .subscribe(people => {
		          this.people = people;
		      });
		    }
		    //all state-changing actions get dispatched to and handled by reducers
		    addPerson(name){
		      this._store.dispatch({type: ADD_PERSON, payload: {id: id(), name})
		    }
		    addGuest(id){
		      this._store.dispatch({type: ADD_GUEST, payload: id});
		    }
		    removeGuest(id){
		      this._store.dispatch({type: REMOVE_GUEST, payload: id});
		    }
		    removePerson(id){
		      this._store.dispatch({type: REMOVE_PERSON, payload: id});
		    }
		    toggleAttending(id){
		      this._store.dispatch({type: TOGGLE_ATTENDING, payload: id})
		    }
		    /*
		 if you do not use async pipe and create manual subscriptions
		 always remember to unsubscribe in ngOnDestroy
		 */
		    ngOnDestroy(){
		      this.subscription.unsubscribe();
		    }
		}
		// Dumb Component - PersonList
		@Component({
		    selector: 'person-list',
		    template: `
		 <ul>
			 <li  *ngFor="let person of people" [class.attending]="person.attending">
				 {{person.name}} - Guests: {{person.guests}}
				 <button (click)="addGuest.emit(person.id)">+</button>
				 <button *ngIf="person.guests" (click)="removeGuest.emit(person.id)">-</button>
			 Attending?
				 <input type="checkbox" [(ngModel)]="person.attending" (change)="toggleAttending.emit(person.id)" />
				 <button (click)="removePerson.emit(person.id)">Delete</button>
			 </li>
		 </ul>
		 `
		})
		export class PersonList {
		    /*
		 "dumb" components do nothing but display data based on input and 
		 emit relevant events back up for parent, or "container" components to handle
		 */
		    @Input() people;
		    @Output() addGuest = new EventEmitter();
		    @Output() removeGuest = new EventEmitter();
		    @Output() removePerson = new EventEmitter();
		    @Output() toggleAttending = new EventEmitter();
		}
		// Dumb Component - PersonInput
		@Component({
		    selector: 'person-input',
		    template: `
		 <input #personName type="text" />
		 <button (click)="add(personName)">Add Person</button>
		 `
		})
		export class PersonInput {
		    @Output() addPerson = new EventEmitter();

		    add(personInput){
		      this.addPerson.emit(personInput.value);
		      personInput.value = '';
		    }
		}
		```
4. Utilizing the AsyncPipe
	- The `AsyncPipe` is a unique, stateful pipe in Angular 2 meant for handling both **Observables** and **Promises**. 
	- When using the `AsyncPipe` in a template expression with Observables, **the supplied Observable is subscribed to, with emitted values being displayed within your view**. 
	- This pipe also handles unsubscribing to the supplied observable, **saving you the mental overhead of manually cleaning up subscriptions in `ngOnDestroy`**. 
	- In a Store application, you will find yourself leaning on the `AsyncPipe`heavily in nearly all of your component views
		```typescript
		// Refactoring to Async Pipe
		@Component({
		    selector: 'app',
		    template: `
		 <h3>@ngrx/store Party Planner</h3>
		 <person-input
		 (addPerson)="addPerson($event)"
		 >
		 </person-input>
		 <person-list
		 [people]="people | async"
		 (addGuest)="addGuest($event)"
		 (removeGuest)="removeGuest($event)"
		 (removePerson)="removePerson($event)"
		 (toggleAttending)="toggleAttending($event)"
		 >
		 </person-list>
		 `,
		    directives: [PersonList, PersonInput]
		})
		export class App {
		    public people;
		    private subscription;
		    
		    constructor(
		     private _store: Store
		    ){
		      /*
		 Observable of people, utilzing the async pipe in our templates this will be subscribed to, with new values being dispayed in our template.
		 Unsubscribe wil be called automatically when component is disposed. 
			 */
		      this.people = _store.select('people');
		    }
		    //all state-changing actions get dispatched to and handled by reducers
		    addPerson(name){
		      this._store.dispatch({type: ADD_PERSON, payload: name})
		    }
		    addGuest(id){
		      this._store.dispatch({type: ADD_GUEST, payload: id});
		    }
		    removeGuest(id){
		      this._store.dispatch({type: REMOVE_GUEST, payload: id});
		    }
		    removePerson(id){
		      this._store.dispatch({type: REMOVE_PERSON, payload: id});
		    }
		    toggleAttending(id){
		      this._store.dispatch({type: TOGGLE_ATTENDING, payload: id})
		    }
		    //ngOnDestroy to unsubscribe is no longer necessary
		}
		```
### Taking Advantage of ChangeDetection.OnPush
- Utilizing a centralized state tree in Angular can not only bring benefits in predictability and maintability, but also performance. To enable this performance benefit we can utilize the `changeDetectionStrategy` of `OnPush`.
- The concept behind `OnPush` is straightforward, when components rely solely on inputs, and those input references do not change, **Angular can skip running change detection on that section of the component tree**.
- To utilize `OnPush` change detection in our components, we need to set the `changeDetection` propery in the `@Component`decorator to `ChangeDetection.OnPush`. **Angular will now ignore change detection on our _dumb_ components and children of these components until there is a change in their input references**.
- **For example**:
	```typescript
	// Updating to ChangeDetection.OnPush
	@Component({
	    selector: 'person-list',
	    template: `
	 <ul>
		 <li *ngFor="let person of people" [class.attending]="person.attending">
		 {{person.name}} - Guests: {{person.guests}}
		 <button (click)="addGuest.emit(person.id)">+</button>
		 <button *ngIf="person.guests" (click)="removeGuest.emit(person.id)">-</button>
		 Attending?
		 <input type="checkbox" [(ngModel)]="person.attending (change)="toggleAttending.emit(person.id)" />
		 <button (click)="removePerson.emit(person.id)">Delete</button>
		 </li>
	 </ul>
	 `,
	    changeDetection: ChangeDetectionStrategy.OnPush
	})
	/*
	 with 'onpush' change detection, components which rely solely on 
	 input can skip change detection until those input references change,
	 this can supply a significant performance boost
	*/
	export class PersonList {
	    /*
	 "dumb" components do nothing but display data based on input and 
	 emit relevant events back up for parent, or "container" components to handle
	 */
	    @Input() people;
	    @Output() addGuest = new EventEmitter();
	    @Output() removeGuest = new EventEmitter();
	    @Output() removePerson = new EventEmitter();
	    @Output() toggleAttending = new EventEmitter();
	}
	```
## Improve Angular Performancee
### Using OnPush
- By default, Angular runs change detection on all components every time something changes in your app.
	- DOM events (click, hover over, etc.)
	- AJAX requests(xhr, promises, etc)
	- Timers (setTimer(), setInterval())
- **When using OnPush, meaning we tell Angular to only trigger changedetection when the object reference changes but not the property within the object changes.
	- By doing that you should treat all inputs to its component **as immutable as value type(string, number, boolean, null, undefined)**, `e.g: this.movie[0] = {title: ‘UPDATED’}`, which is to replace the movie object with new object, 
	- **Otherwise, if you are passing a reference type(object, function, arrays), angular will not change**, `e.g: this.movie[0].title = ‘UPDATED’`
- The main idea behind the OnPush strategy manifests from the realization that **if we treat reference types as immutable objects, we can detect if a value has changed much faster. When a reference type is immutable, this means every time it is updated, the reference on the stack memory will have to change.**
- The OnPush strategy basically asks two questions instead of one. Has the reference of the reference type changed? If yes, then have the values in heap memory changed?
- For example, assume we have an immutable array with 30 elements and we want to know if there are any changes. We know that, in order for there to be any updates to the immutable array, the reference (on the stack) of it would have to have changed. This means we can initially check to see if the reference to the array is any different, which would potentially save us from doing 30 more checks (in the heap) to determine which element is different. This is called the OnPush strategy.
### Use of trackBy
- By default, when iterating over a list of objects, **Angular will use object identity to determine if items are added, removed, or rearranged**. This works well for most situations. 
- However, with the introduction of immutable practices, **changes to the list’s content generates new objects.** In turn, **ngFor will generate a new collection of DOM elements to be rendered.** 
- If the list is long or complex enough, this will increase the time it takes the browser to render updates. To mitigate this issue, it is possible to use trackBy to indicate how a change to an entry is determined.
	```html
	<ul>
		<li *ngFor="let instructor of instructorList: trackBy: trackByName" >
		<span>Instructor Name {{ instructor.name }}</span>
		</li>
	</ul>

	trackByName(index, instructor) {
		return instructor.name;
	}
	```
### Avoid Computing Values in the Template
- Sometimes you need to transform a value that comes from the server to something you can display in the UI, **don't include computed function in ngFor**
- Instead, use [pure](https://angular.io/guide/pipes#pure-pipes) pipes - 
	- Angular executes a _pure pipe_ only when it detects a _pure change_ to the input value. A pure change is either a change to a primitive input value (`String`, `Number`, `Boolean`, `Symbol`) or a changed object reference (`Date`, `Array`, `Function`, `Object`).
	- Creates a new property and set the value once, for example:
### Disable Change Detection
- Imagine that you have a component that depends on data that changes constantly, many times per second.
- Updating the user interface whenever new data arrives can be expensive. A more efficient way would be to check and update the user interface every X seconds.
- We can do that by detaching the component’s change detector and conducting a local check every x seconds.
```typescript
@Component({
	selector: 'giant-list',
	template: `<li *ngFor="let d of dataProvider.data">Data {{d}}</lig>`,
})
class GiantList {
	constructor(private ref: ChangeDetectorRef, private dataProvider: DataProvider) {
		ref.detach();
		setInterval(() => {
			this.ref.detectChanges();
		}, 5000);
	}
}
```
### Using Lazy Loading
- In my opinion, lazy loading is one of Angular’s most powerful features, and yet the least used.
- **By default, Webpack will output all your app’s code into one large bundle**. Lazy loading gives you the ability to **optimize your application load time** by **splitting the application to feature modules and loading them _on-demand_**.
- Angular makes this process almost transparent, for example:
	```
	{
		path: 'admin',
		loadChildren: 'app/admin/admin.module#AdminModule',
	}
	```
- We can even prevent whole modules from being loaded based on some condition. For example, don’t load the admin module if the user is not an admin. ( see the [canLoad](https://angular.io/guide/router#canload-guard-guarding-unauthorized-loading-of-feature-modules) guard )
### Use of native JavaScript or Lodash
### Chrome Dev-tool profiler to identify performance bottlenecks:
- This one is a handy tool which gives you the option to select which profile type you want to create. Record Allocation Timeline, Take Heap Snapshot, Record Allocation Profile are used for memory profiling.
## GraphQL - by Facebook
### Definition
- **GraphQL** is a query language that lets you write queries using an object structure rather than a text string, Graph QL gives you a simple declarative way to retrieve data.
- So rather than use an SQL query like:
	```
	SELECT name, id, description FROM projects
	```
	You would simply describe the object and the fields you’d like from that object you like so:
		```
		{  
		  projects {  
		    id  
		    name  
		    description  
		  }  
		}
		```
- A **GraphQL query** is **a strongly typed string that is sent to a server to be interpreted and fulfilled, which then returns JSON back to the client.**
### Why use GraphQL
- all off of the requests are sent to a single `/graphql` endpoint
	- _Why_ does this matter? The actual transmission of requests and data is _abstracted_ away. 
	- We no longer have to worry about things like response codes and planning out our urls like `/project/item/somethingElse/youGetThePoint`
	- This follows a fundamental programming principle of **reducing complexity through abstraction**.
	- In addition, we also don’t have to worry about all of the `POST/GET/UPDATE/DELETE`calls,`200 OK` server response codes, and even some caching on certain clients.
### Apollo GraphQL (Angular)
- Install Apollo GraphQL
	```bash
	npm install apollo-angular apollo-angular-link-http apollo-client apollo-cache-inmemory graphql-tag graphql
	```
- In Angular project, import the necessary module into `app.module.ts`
	```typescript
	import { HttpClientModule } from '@angular/common/http';
	import { ApolloModule, Apollo } from 'apollo-angular';
	import { HttpLinkModule, HttpLink } from 'apollo-angular-link-http';
	imports: [
		BrowserModule,
		// import HttpClientModule after BrowserModule.
		HttpClientModule,
		ApolloModule,
		// import HttpLinkModule after HttpClientModule.
		HttpLinkModule
	],
	```
- Make `Apollo` and `HTTP Link` services available in our application by using DI 
	```typescript
	import { ApolloModule, Apollo } from 'apollo-angular';
	import { HttpLinkModule, HttpLink } from 'apollo-angular-link-http';
	import { InMemoryCache } from  'apollo-cache-inmemory';

	export class AppModule {
		constructor(
			apollo: Apollo,httpLink: HttpLink) {
			// Make use of Apollo service to establish a connection to a graph QL server endpoint
			apollo.create({
				link: httpLink.create({ 
					uri: 'https://vm8mjvrnv3.lp.gql.zone/graphql'
				}),
				cache: new InMemoryCache()
			});
		}	
	}
	```
- **Create a GraphQL server** with `Node.js and Express` or using Apollo Launch Pad at `https://launchpad.graphql.com`
	- Create a **schema** using GraphQL schema language
		```typescript
		const typeDefs = `
		    type Query {
						allCourses: [Course]
		        course(id: Int!): Course
		    },
		    type Course {
		        id: Int
		        title: String
		        author: String
		        description: String
		        topic: String
		        url: String
		    }
		`;
		```
	- Provide **resolver** functions for your schema fields
		```typescript
		const resolvers = {
		  Query: {
		    allCourses: getAllCourses,
		    course: getCourse,
		  },
		};

		```
   - **Required**: Export the GraphQL.js schema object as "schema"
		```
		export const schema = makeExecutableSchema({
		  typeDefs,
		  resolvers,
		});
		```
	- Test Data and Functions
		```typescript
		var coursesData = [
		  	  {
		        id: 1,
		        title: 'Angular - The Complete Guide',
		        author: 'Maximilian Schwarzmüller',
		        description: 'Master Angular (Angular 2+, incl. Angular 5) and build awesome, reactive web apps with the successor of Angular.js',
		        topic: 'Angular',
		        url: 'http://codingthesmartway.com/courses/angular2-complete-guide/'
		    }, // ...
		];

		var getCourse = function(root, {id}) { 
		    return coursesData.filter(course => {
		        return course.id === id;
		    })[0];
		};

		var getAllCourses = function() {
		  return coursesData;
		}
		```
- Expand the angular application to include the code which is needed to retrieve data from our end point.
	- add a `type.ts` for ** type definition** and on Angular side
		```typescript
		export type Course = {
			id: number;
			title: string;
			author: string;
			description: string;
			topic: string;
			url: string;
		}

		export type Query = {
			allCourses: Course[];
		}
		```
	- create a new component to fetch the data from Apollo
		```typescript
		import { Component, OnInit } from '@angular/core';
		import { Apollo } from 'apollo-angular';
		import { Observable } from 'rxjs/Observable';
		import { map } from 'rxjs/operators';

		import gql from 'graphql-tag';

		import { Course, Query } from '../types';

		@Component({
		  selector: 'app-list',
		  templateUrl: './list.component.html',
		  styleUrls: ['./list.component.css']
		})
		export class ListComponent implements OnInit {
		  courses: Observable<Course[]>;
		  constructor(private apollo: Apollo) { }

		  ngOnInit() {
		    this.courses = this.apollo.watchQuery<Query>({
		      query: gql`
		        query allCourses {
		          allCourses {
		            id
		            title
		            author
		            description
		            topic
		            url
		          }
		        }
		      `
		    })
		      .valueChanges
		      .pipe(
		        map(result => result.data.allCourses)
		      );
		  }
		}
		```
### What is a service worker and its role in Angular?
- A service worker is a script that runs in the web browser and manages caching for an application. 
- Starting from 5.0.0 version, Angular ships with a service worker implementation. 
- Angular service worker is designed to optimize the end user experience of using an application over a slow or unreliable network connection, while also minimizing the risks of serving outdated content.
### What is Angular Ivy?
- Angular Ivy is a new rendering engine for Angular. You can choose to opt in a preview version of Ivy from Angular version 8.
	 1. You can enable ivy in a new project by using the --enable-ivy flag with the `ng new command`
		 ```javascript
		 ng new ivy-demo-app --enable-ivy
		 ```
	 2. You can add it to an existing project by adding `enableIvy` option in the `angularCompilerOptions` in your project's `tsconfig.app.json`.
		 ```javascript
		 {
		   "compilerOptions": { ... },
		   "angularCompilerOptions": {
		     "enableIvy": true
		   }
		 }
		 ```
### What are the features included in ivy preview?
1.  Generated code that is easier to read and debug at runtime
2.  Faster re-build time
3.  Improved payload size
4.  Improved template type checking
