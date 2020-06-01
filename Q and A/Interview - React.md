### What is virtual DOM
- Actual URI DOM is really slow at accessing or re-rendering
- React has a virtual representation of the actual DOM written in Javascript (the 'Render' function) because Javascript is really fast.
- E.g, when you click a button, React actually kind of recreates the virtual DOM in a second time and it recreates the way that would look like after this state change, it will then compare the new virtual DOM to the old virtual DOM, if there is no difference, actual DOM never gets touched but if there is change, it will only render the part which gets affected.
- It's fast because it uses the virtual DOM for comparison instead of the actual DOM which would have been slow to begin with, and then when it has to re-render something, it only renders the part which changed and not everything
- Development tools --> Rendering tab, enable paint flashing where you could see which part of the DOM gets affected and updated
### Props
- Changes in `props` will trigger React to re-render your components and potentially update the DOM in the browser
- `props` allow you to pass data from a parent (wrapping) component to a child (embedded) component.
- E.g:
	- **AllPosts Component:**
		```javascript
		const posts =  ()  =>  {
			return  (
			<div>
				<Post title="My first Post"  />
			</div>
			);
		}
		```
	- **Post Component:**
		```javascript
		const post =  (props)  =>  {
			return  (
				<div>
					<h1>{props.title}</h1>
				</div>
			);
		}
		```
### State
- Whilst props allow you to pass data down the component tree (and hence trigger an UI update), state is used to change the component, well, state from within. Changes to state also trigger an UI update.
- Only class-based components can define and use `state` . You can of course pass the `state` down to functional components, but these then can't directly edit it.
- `state` simply is a property of the component class, you have to call it  `state` though - the name is not optional. You can then access it via  `this.state` in your class JSX code (which you return in the required  `render()` method).
- Whenever  `state` changes (taught over the next lectures), the component will re-render and reflect the new state. The difference to  `props` is, that this happens within one and the same component - you don't receive new data (`props` ) from outside!
- E.g:
	- **NewPost Component:**
```javascript
class  NewPost  extends  Component  {  
	// state can only be accessed in class-based components!
	state =  {
		counter:  1
	};

	render ()  {  
	// Needs to be implemented in class-based components! Needs to return some JSX!
		return  (
			<div>{this.state.counter}</div>
		);
	}
}
```
### Hooks (Available in React versions >16.8)
- useState() returns an array with exactly two elements: 
	- 1) Your current state value 
	- 2) A function that allows us to update this state such that React is aware of if and will re-render this component
- useState('anything'): Any data of your choice, can be an object, an array, a string etc!
- setSomeState('new state'): Mutate state & trigger re-render, Replaces old state (NO automatic merging)! 
- It means whenever you update state, you have to manually make sure including all old state data. Also you could have multiple separated state slices.
- e.g
```javascript
const [someState, setSomeState] = useState('anything')
const someFunction = () => { setSomeState('new state') }
```
### Lifecycle Methods 
#### render
- Class component
1. constructor(props)
    - call `super(props);`
    - `super()` vs `super(props)`: only one reason when one needs to pass  `props`  to  `super()`:
**When you want to access  `this.props`  in constructor.**, Note that passing or not passing `props` to `super` has **no effect** on later uses of `this.props` outside `constructor`. That is `render`, `shouldComponentUpdate`, or event handlers **always** have access to it.
	- only place to set initial state by using `this.state = {};`
	- after this you have to use this.setState to change state
2. static getDerivedStateFromProps(nextProps, prevState) - (rarely used) 
- this method is actually static
- it runs right after constructor
- it has role in initial render and also re-render face
- it tries to get the derived state from the change in props that it would return the `newState` if there is change in state or `null` if there is no change in state
##### Why it's a static method? 
- static method will prevent user to access `this` keyword directly inside the method, because static method is actually class method, not instance method
- in the vast majority of cases  **you don't need  `getDerivedStateFromProps`  at all**.
If you just want to compute some derived data, either:
	- Do it right inside  `render`
	-  Or, if re-calculating it is expensive, use a memoization helper like  `memoize-one`.
3. render
- only method that's mandatory to have
- you cannot set state here, otherwise you would go an infinite loop
4. componentDidMount
- notify that `DOM` is ready now, e.g: if you're using a third-party chart component, you need to have the DOM ready before component uses it
#### re-render
1. static getDerivedStateFromProps(nextProps, prevState)
- (rarely used) does the same thing as intial render
2. shouldComponentUpdate(nextProps, nextState)
- make a decision if this component really needs to be updated or not by returning `true` or `false`
- e.g, whenever setState will trigger re-render, but it could be setting the same state again, there might not be need to re-render
- E.g
```
shouldComponentUpdate(nextProps, nextState) {
    if (this.props.color !== nextProps.color) {
      return true;
    }
    if (this.state.count !== nextState.count) {
      return true;
    }
    return false;
  }
```
3. render
4. getSnapshotBeforeUpdate(prevProps, prevState)
- pre commit phase, render doesn't mean it has rendered, `mount` really happens after this method (replace for componentWillUpdate)
- This method can be used when there is delay between if you render a component and in its next phase, if user does something in between like scoll or change size of the window, then you need to remember where the scroll was before so you can do something after the render.
- E.g
```
getSnapshotBeforeUpdate(prevProps, prevState) {
    // Are we adding new items to the list?
    // Capture the scroll position so we can adjust scroll later.
    if (prevProps.list.length < this.props.list.length) {
      const list = this.listRef.current;
      return list.scrollHeight - list.scrollTop;
    }
    return null;
  }

  componentDidUpdate(prevProps, prevState, snapshot) {
    // If we have a snapshot value, we've just added new items.
    // Adjust scroll so these new items don't push the old ones out of view.
    // (snapshot here is the value returned from getSnapshotBeforeUpdate)
    if (snapshot !== null) {
      const list = this.listRef.current;
      list.scrollTop = list.scrollHeight - snapshot;
    }
  }
```
5. componentDidUpdate(prevProps, prevState, snapshot)
- similar as componentDidMount
- E.g
```
componentDidUpdate(prevProps) {
  // Typical usage (don't forget to compare props):
  if (this.props.userID !== prevProps.userID) {
    this.fetchData(this.props.userID);
  }
}
```
6. componentWillUnmout
- `componentWillUnmount()` is invoked immediately before a component is unmounted and destroyed. Perform any necessary cleanup in this method, such as invalidating timers, canceling network requests, or cleaning up any subscriptions that were created in `componentDidMount()`.
- You **should not call  `setState()`** in `componentWillUnmount()` because the component will never be re-rendered. Once a component instance is unmounted, it will never be mounted again.
### Why do we use arrow function in react?
#### class method vs class property
- `render()` is a class method, but any function declared is actually a class property, 
#### arrow function
- Allows you to access class property via `this` keyword inside arrow function, arrow function doesn't have its own `this`, it automatically takes `this` from its immediate parent which is `class` here
- E.g, In React you could have onClick handles which is not actually a class method but a class property, if you have a function inside a function where you have the `this` keyword, `this` key word belongs to inner function but not the class outside, arrow function doesn't have its own `this`, it automatically takes `this` from its immediate parent which is `class` here
- Alternative way to bind this method back to class inside the constructor 
### How to prevent components from re-rendering
refer: [Official Doc](https://reactjs.org/docs/optimizing-performance.html#shouldcomponentupdate-in-action)
1. shouldComponentUpdate(nextProps, nextState)
2. [`React.PureComponent`](https://reactjs.org/docs/react-api.html#reactpurecomponent)
- you can use  `React.PureComponent`  instead of writing your own  `shouldComponentUpdate`. 
- It only does a shallow comparison, so you can’t use it if the props or state may have been mutated in a way that a shallow comparison would miss.
- This can be a problem with more complex data structures. For example,
```
class ListOfWords extends React.PureComponent {
  render() {
    return <div>{this.props.words.join(',')}</div>;
  }
}

class WordAdder extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      words: ['marklar']
    };
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    // This section is bad style and causes a bug
    const words = this.state.words;
    words.push('marklar');
    this.setState({words: words});
  }

  render() {
    return (
      <div>
        <button onClick={this.handleClick} />
        <ListOfWords words={this.state.words} />
      </div>
    );
  }
}
```
- How to fix this ? See below
- Only extend `PureComponent` when you expect to have simple props and state, or use [`forceUpdate()`](https://reactjs.org/docs/react-component.html#forceupdate) when you know deep data structures have changed. Or, consider using [immutable objects](https://facebook.github.io/immutable-js/) to facilitate fast comparisons of nested data.
3. React.memo
- `React.memo` is a [higher order component](https://reactjs.org/docs/higher-order-components.html). It’s similar to [`React.PureComponent`](https://reactjs.org/docs/react-api.html#reactpurecomponent) but for function components instead of classes.
- `React.memo` only checks for prop changes. If your function component wrapped in `React.memo` has a [`useState`](https://reactjs.org/docs/hooks-state.html) or [`useContext`](https://reactjs.org/docs/hooks-reference.html#usecontext) Hook in its implementation, it will still rerender when state or context change.
- Unlike the [`shouldComponentUpdate()`](https://reactjs.org/docs/react-component.html#shouldcomponentupdate) method on class components, the `areEqual` function returns `true` if the props are equal and `false` if the props are not equal. This is the inverse from `shouldComponentUpdate`.
- E.g
```
function MyComponent(props) {
  /* render using props */
}
function areEqual(prevProps, nextProps) {
  /*
  return true if passing nextProps to render would return
  the same result as passing prevProps to render,
  otherwise return false
  */
}
export default React.memo(MyComponent, areEqual);
```
### The Power Of Immutating Data
1. The simplest way to avoid this problem is to avoid mutating values that you are using as props or state. For example, the  `handleClick`  method above could be rewritten using  `concat`  as:
```
handleClick() {
  this.setState(state => ({
    words: state.words.concat(['marklar'])
  }));
}
```
2. ES6 supports a  [spread syntax](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Spread_operator)  for arrays which can make this easier. If you’re using Create React App, this syntax is available by default.
```
handleClick() {
  this.setState(state => ({
    words: [...state.words, 'marklar'],
  }));
};
```
3. You can also rewrite code that mutates objects to avoid mutation, in a similar way. For example, let’s say we have an object named  `colormap`  and we want to write a function that changes  `colormap.right`  to be  `'blue'`. We could write:
```
function updateColorMap(colormap) {
  colormap.right = 'blue';
}
```
4. To write this without mutating the original object, we can use  [Object.assign](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/assign)  method:
```
function updateColorMap(colormap) {
  return Object.assign({}, colormap, {right: 'blue'});
}
```
`updateColorMap`  now returns a new object, rather than mutating the old one.  `Object.assign`  is in ES6 and requires a polyfill.
5. There is a JavaScript proposal to add  [object spread properties](https://github.com/sebmarkbage/ecmascript-rest-spread)  to make it easier to update objects without mutation as well:
```
function updateColorMap(colormap) {
  return {...colormap, right: 'blue'};
}
```
### Explain Error Boundaries
- Error boundaries are React components that **catch JavaScript errors anywhere in their child component tree, log those errors, and display a fallback UI** instead of the component tree that crashed
- A class component becomes an error boundary if it defines either (or both) of the lifecycle methods [`static getDerivedStateFromError()`](https://reactjs.org/docs/react-component.html#static-getderivedstatefromerror) or [`componentDidCatch()`](https://reactjs.org/docs/react-component.html#componentdidcatch). 
	- Use `static getDerivedStateFromError()` to render a fallback UI after an error has been thrown. 
	- Use `componentDidCatch()` to log error information.
- Create an ErrorBoundaryComponent and wrap with your component
- E.g
```
class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error) {    // Update state so the next render will show the fallback UI.    return { hasError: true };  }
  componentDidCatch(error, errorInfo) {    // You can also log the error to an error reporting service    logErrorToMyService(error, errorInfo);  }
  render() {
    if (this.state.hasError) {      // You can render any custom fallback UI      return <h1>Something went wrong.</h1>;    }
    return this.props.children; 
  }
}

```
<ErrorBoundary>
  <MyWidget />
</ErrorBoundary>
```
```


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3NTQyMzYxNTAsLTE0NzU0MTMzOTksMj
cxMTM5OTM1LC0xMjY2MDIzMDcwLDc1OTU4MDY3OSw3MjM5MTA3
MDcsMjA3NTk4MDY0OSwxNTM3NzI1OTQxLDY1NDM2OTIxNl19
-->