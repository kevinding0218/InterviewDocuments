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
// ToUseIt
<ErrorBoundary>
  <MyWidget />
</ErrorBoundary>
```
- Note: Error boundaries do  **not**  catch errors for:
	-   Event handlers ([learn more](https://reactjs.org/docs/error-boundaries.html#how-about-event-handlers)): use `try..catch` inside itself
	-   Asynchronous code (e.g.  `setTimeout`  or  `requestAnimationFrame`  callbacks)
	-   Server side rendering
	-   Errors thrown in the error boundary itself (rather than its children)
### Best Lifecycle Method for making API calls
- componentDidMount: to make sure your component is finished rendering which means your DOM is ready
### React Pattern
- **context-api pattern**: Context lets us pass a value deep into the component tree without explicitly threading it through every component.
- E.g
```
// Create a context for the current theme (with "light" as the default).const ThemeContext = React.createContext('light');
class App extends React.Component {
  render() {
    // Use a Provider to pass the current theme to the tree below.    // Any component can read it, no matter how deep it is.    // In this example, we're passing "dark" as the current value.    return (
      <ThemeContext.Provider value="dark">        <Toolbar />
      </ThemeContext.Provider>
    );
  }
}

// A component in the middle doesn't have to// pass the theme down explicitly anymore.function Toolbar() {
  return (
    <div>
      <ThemedButton />
    </div>
  );
}

class ThemedButton extends React.Component {
  // Assign a contextType to read the current theme context.  // React will find the closest theme Provider above and use its value.  // In this example, the current theme is "dark".  static contextType = ThemeContext;
  render() {
    return <Button theme={this.context} />;  }
}
```
- **render props**: using children as functions, inside the class component you have other components as functions
- **presentational component pattern**: 
### Why would you use react
- The decision has to be made based on the project itself, e.g if your project needs to be completed within 6 months while there might be a huge change of version in any front-end framework
- Angular is easy to configure but hard to use, they come as a huge package 
- Use of Javascript 
### What is css-in-js pattern
- “CSS-in-JS” refers to a pattern where CSS is composed using JavaScript instead of defined in external files.
- You can do inline css which you can conditional render without polluting other parts
- a good starting point is to define your styles in a separate  `*.css`  file as usual and refer to them using  [`className`](https://reactjs.org/docs/dom-elements.html#classname).
### What is react hooks
- Hooks are functions that lets you "hook into" React state and lifecycle features from function component
### Why use react hooks
- different way of doing the same thing
- no more complex lifecycle methods
- simpler code, no more mapStateToProps, mapDispatchToProps with redux
- hooking into component is much easier
### Rules of react hooks
- only call hooks at the top level
- don't call hooks inside loops, conditions, or nested functions
- call hooks from react components or custom hooks
### Map lifecycle class methods to lifecyle hooks
- Stage of lifecycle
	- initial render
		- `getDerivedStateFromProps` => `useEffect(() => {}, [prop1, prop2])`
			- foreach prop, you could have separate `useEffect`
		- `componentDidMount` => `useEffect(() => {}, [])`
			- if provided empty array, this would run only one time after initial render
			- if no array argument provided, this would run at initial mount as well as every update
	- updates
		- `getDerivedStateFromProps` => `useEffect(() => {}, [prop1, prop2])`
		- `shouldComponentUpdate()` => `useMemo()`
		- `componentDidUpdate` => `useEffect(() => {})`
			- no array defined means it'll only run at initial render as well as every updates
			- if any state defined inside the array argument, means only run that `useEffect` when the defined state gets changed
		- `getSnapshotBeforeUpdate` => custom Hook to hold previous state
	- unmount
		- `useEffect(() => { // some code; return () => {// cleanup code}}, [])`
			- instead of executing a function, returns a function which would do the clean up job
			- **clean up** will be executed before **some code**
- error boundary (not available for hooks)
	- getDerivedStateFromError()
	- componentDidCatch()
### how to upgrade class project to hooks project
- update react, react-dom version
- update code on route at a time
- `this.state` => `useState`
- `lifecycle event` => `useEffect`
### how to debug hooks
- `console.log` or `debugger` statement
- react developer tool(chome plugin)
- useDebugValue
### Hook API
#### useState
```
const [state, setState] = useState(initialState);
```
- Returns a stateful value, and a function to update it.
- During the initial render, the returned state (`state`) is the same as the value passed as the first argument (`initialState`).
- The  `setState`  function is used to update the state. It accepts a new state value and enqueues a re-render of the component.
```
setState(newState);
```
- During subsequent re-renders, the first value returned by `useState` will always be the most recent state after applying updates.
- Functional updates
	- If the new state is computed using the previous state, you can pass a function to  `setState`. The function will receive the previous value, and return an updated value. Here’s an example of a counter component that uses both forms of  `setState`:
```
function Counter({initialCount}) {
  const [count, setCount] = useState(initialCount);
  return (
    <>
      Count: {count}
      <button onClick={() => setCount(initialCount)}>Reset</button>
      <button onClick={() => setCount(prevCount => prevCount - 1)}>-</button>
      <button onClick={() => setCount(prevCount => prevCount + 1)}>+</button>
    </>
  );
}
```
- Note: Unlike the  `setState`  method found in class components,  `useState`  does not automatically merge update objects. You can replicate this behavior by combining the function updater form with object spread syntax:
```
setState(prevState => {
  // Object.assign would also work
  return {...prevState, ...updatedValues};
});
```
- Lazy initial state
	- The  `initialState`  argument is the state used during the initial render. In subsequent renders, it is disregarded. If the initial state is the result of an expensive computation, you may provide a function instead, which will be executed only on the initial render:

```
const [state, setState] = useState(() => {
  const initialState = someExpensiveComputation(props);
  return initialState;
});
```
#### useEffect
- Check knowledge before about second argument
- Unlike `componentDidMount` and `componentDidUpdate`, the function passed to `useEffect` fires **after** layout and paint, during a deferred event. This makes it suitable for the many common side effects, like setting up subscriptions and event handlers, because most types of work shouldn’t block the browser from updating the screen.
- Although  `useEffect`  is deferred until after the browser has painted, it’s guaranteed to fire before any new renders. React will always flush a previous render’s effects before starting a new update.
- **Why did we return a function from our effect?**  This is the optional cleanup mechanism for effects. Every effect may return a function that cleans up after it. This lets us keep the logic for adding and removing subscriptions close to each other. They’re part of the same effect!
- **When exactly does React clean up an effect?**  React performs the cleanup when the component unmounts. However, as we learned earlier, effects run for every render and not just once. This is why React  _also_  cleans up effects from the previous render before running the effects next time.
#### useContext
```
const value = useContext(MyContext);
```
- Accepts a context object (the value returned from  `React.createContext`) and returns the current context value for that context. The current context value is determined by the  `value`  prop of the nearest  `<MyContext.Provider>`  above the calling component in the tree.
- When the nearest  `<MyContext.Provider>`  above the component updates, this Hook will trigger a rerender with the latest context  `value`  passed to that  `MyContext`  provider. Even if an ancestor uses  [`React.memo`](https://reactjs.org/docs/react-api.html#reactmemo)  or  [`shouldComponentUpdate`](https://reactjs.org/docs/react-component.html#shouldcomponentupdate), a rerender will still happen starting at the component itself using  `useContext`.
- A component calling `useContext` will always re-render when the context value changes. If re-rendering the component is expensive, you can [optimize it by using memoization](https://github.com/facebook/react/issues/15156#issuecomment-474590693).
- Example
```
const themes = {
  light: {
    foreground: "#000000",
    background: "#eeeeee"
  },
  dark: {
    foreground: "#ffffff",
    background: "#222222"
  }
};

const ThemeContext = React.createContext(themes.light);

function App() {
  return (
    <ThemeContext.Provider value={themes.dark}>
      <Toolbar />
    </ThemeContext.Provider>
  );
}

function Toolbar(props) {
  return (
    <div>
      <ThemedButton />
    </div>
  );
}

function ThemedButton() {
  const theme = useContext(ThemeContext);  return (    <button style={{ background: theme.background, color: theme.foreground }}>      I am styled by theme context!    </button>  );
}
```
#### useReducer
```
const [state, dispatch] = useReducer(reducer, initialArg, init);
```
- An alternative to [`useState`](https://reactjs.org/docs/hooks-reference.html#usestate). Accepts a reducer of type `(state, action) => newState`, and returns the current state paired with a `dispatch` method
- `useReducer` is usually preferable to `useState` when you have complex state logic that involves multiple sub-values or when the next state depends on the previous one. `useReducer` also lets you optimize performance for components that trigger deep updates because [you can pass  `dispatch`  down instead of callbacks](https://reactjs.org/docs/hooks-faq.html#how-to-avoid-passing-callbacks-down).
```
const initialState = {count: 0};

function reducer(state, action) {
  switch (action.type) {
    case 'increment':
      return {count: state.count + 1};
    case 'decrement':
      return {count: state.count - 1};
    default:
      throw new Error();
  }
}

function Counter() {
  const [state, dispatch] = useReducer(reducer, initialState);
  return (
    <>
      Count: {state.count}
      <button onClick={() => dispatch({type: 'decrement'})}>-</button>
      <button onClick={() => dispatch({type: 'increment'})}>+</button>
    </>
  );
}
```
- Specifying the initial state
	- There are two different ways to initialize  `useReducer`  state. You may choose either one depending on the use case. The simplest way is to pass the initial state as a second argument:
```
  const [state, dispatch] = useReducer(
    reducer,
    {count: initialCount}  );
```
#### useCallback
```
const memoizedCallback = useCallback(
  () => {
    doSomething(a, b);
  },
  [a, b],
);
```
- Returns a  [memoized](https://en.wikipedia.org/wiki/Memoization)  callback.
- Pass an inline callback and an array of dependencies.  `useCallback`  will return a memoized version of the callback that only changes if one of the dependencies has changed. This is useful when passing callbacks to optimized child components that rely on reference equality to prevent unnecessary renders (e.g.  `shouldComponentUpdate`).
- `useCallback(fn, deps)`  is equivalent to  `useMemo(() => fn, deps)`.
#### useMemo
```
const memoizedValue = useMemo(() => computeExpensiveValue(a, b), [a, b]);
```
- Returns a  [memoized](https://en.wikipedia.org/wiki/Memoization)  value.
- Pass a “create” function and an array of dependencies.  `useMemo`  will only recompute the memoized value when one of the dependencies has changed. This optimization helps to avoid expensive calculations on every render.
- It could also return a sub component depends on specific variable changes
```
const memorizedComp = useMemo(() => {return <ChildComponent />}, [currentCompProperty]);
```
#### useRef and forwardRef
```
const refContainer = useRef(initialValue);
```
- `useRef`  returns a mutable ref object whose  `.current`  property is initialized to the passed argument (`initialValue`). The returned object will persist for the full lifetime of the component.
- A common use case is to access a child imperatively:
```
function TextInputWithFocusButton() {
  const inputEl = useRef(null);
  const onButtonClick = () => {
    // `current` points to the mounted text input element
    inputEl.current.focus();
  };
  return (
    <>
      <input ref={inputEl} type="text" />
      <button onClick={onButtonClick}>Focus the input</button>
    </>
  );
}
```
- Essentially, `useRef` is like a “box” that can hold a mutable value in its `.current` property.
- You might be familiar with refs primarily as a way to [access the DOM](https://reactjs.org/docs/refs-and-the-dom.html). If you pass a ref object to React with `<div ref={myRef} />`, React will set its `.current` property to the corresponding DOM node whenever that node changes.
- However,  `useRef()`  is useful for more than the  `ref`  attribute. It’s  [handy for keeping any mutable value around](https://reactjs.org/docs/hooks-faq.html#is-there-something-like-instance-variables)  similar to how you’d use instance fields in classes.
- This works because  `useRef()`  creates a plain JavaScript object. The only difference between  `useRef()`  and creating a  `{current: ...}`  object yourself is that  `useRef`  will give you the same ref object on every render.
- Keep in mind that  `useRef`  _doesn’t_  notify you when its content changes. Mutating the  `.current`  property doesn’t cause a re-render. If you want to run some code when React attaches or detaches a ref to a DOM node, you may want to use a  [callback ref](https://reactjs.org/docs/hooks-faq.html#how-can-i-measure-a-dom-node)  instead.

### [](https://reactjs.org/docs/hooks-reference.html#useimperativehandle)
#### useImperativeHandle
```
useImperativeHandle(ref, createHandle, [deps])
```
- `useImperativeHandle`  customizes the instance value that is exposed to parent components when using  `ref`. As always, imperative code using refs should be avoided in most cases.  `useImperativeHandle`  should be used with  [`forwardRef`](https://reactjs.org/docs/react-api.html#reactforwardref):
```
function FancyInput(props, ref) {
  const inputRef = useRef();
  useImperativeHandle(ref, () => ({
    focus: () => {
      inputRef.current.focus();
    }
  }));
  return <input ref={inputRef} ... />;
}
FancyInput = forwardRef(FancyInput);
```
- In this example, a parent component that renders  `<FancyInput ref={inputRef} />`  would be able to call  `inputRef.current.focus()`.
#### useLayoutEffect
- The signature is identical to  `useEffect`, but it fires synchronously after all DOM mutations. Use this to read layout from the DOM and synchronously re-render. Updates scheduled inside  `useLayoutEffect`  will be flushed synchronously, before the browser has a chance to paint.
- Prefer the standard  `useEffect`  when possible to avoid blocking visual updates.
#### useDebugValue
```
useDebugValue(value)
```
- `useDebugValue`  can be used to display a label for custom hooks in React DevTools.
- For example, consider the  `useFriendStatus`  custom Hook described in  [“Building Your Own Hooks”](https://reactjs.org/docs/hooks-custom.html):
```
function useFriendStatus(friendID) {
  const [isOnline, setIsOnline] = useState(null);
  // ...
  // Show a label in DevTools next to this Hook  // e.g. "FriendStatus: Online"  useDebugValue(isOnline ? 'Online' : 'Offline');
  return isOnline;
}
```
### [Custom Hook]([https://reactjs.org/docs/hooks-custom.html](https://reactjs.org/docs/hooks-custom.html))
#### Extracting a Custom Hook
- When we want to share logic between two JavaScript functions, we extract it to a third function. Both components and Hooks are functions, so this works for them too!
- **A custom Hook is a JavaScript function whose name starts with ”`use`” and that may call other Hooks.**  For example,  `useFriendStatus`  below is our first custom Hook
```
import { useState, useEffect } from 'react';

function useFriendStatus(friendID) {  const [isOnline, setIsOnline] = useState(null);

  useEffect(() => {
    function handleStatusChange(status) {
      setIsOnline(status.isOnline);
    }

    ChatAPI.subscribeToFriendStatus(friendID, handleStatusChange);
    return () => {
      ChatAPI.unsubscribeFromFriendStatus(friendID, handleStatusChange);
    };
  });

  return isOnline;
}
```
- The purpose of our `useFriendStatus` Hook is to subscribe us to a friend’s status. This is why it takes `friendID` as an argument, and returns whether this friend is online
#### Using a Custom Hook
- In the beginning, our stated goal was to remove the duplicated logic from the `FriendStatus` and `FriendListItem` components. Both of them want to know whether a friend is online.
```
function FriendStatus(props) {
  const isOnline = useFriendStatus(props.friend.id);
  if (isOnline === null) {
    return 'Loading...';
  }
  return isOnline ? 'Online' : 'Offline';
}
```

```
function FriendListItem(props) {
  const isOnline = useFriendStatus(props.friend.id);
  return (
    <li style={{ color: isOnline ? 'green' : 'black' }}>
      {props.friend.name}
    </li>
  );
```
- All we did was to extract some common code between two functions into a separate function. **Custom Hooks are a convention that naturally follows from the design of Hooks, rather than a React feature.**
- **Do I have to name my custom Hooks starting with “`use`”?** Please do. This convention is very important. Without it
- **Do two components using the same Hook share state?** No. Custom Hooks are a mechanism to reuse _stateful logic_ (such as setting up a subscription and remembering the current value), but every time you use a custom Hook, all state and effects inside of it are fully isolated.
- **How does a custom Hook get isolated state?**  Each  _call_  to a Hook gets isolated state. Because we call  `useFriendStatus`  directly, from React’s point of view our component just calls  `useState`  and  `useEffect`. And as we  [learned](https://reactjs.org/docs/hooks-state.html#tip-using-multiple-state-variables)  [earlier](https://reactjs.org/docs/hooks-effect.html#tip-use-multiple-effects-to-separate-concerns), we can call  `useState`  and  `useEffect`  many times in one component, and they will be completely independent.
#### Tip: Pass Information Between Hooks
- Since Hooks are functions, we can pass information between them.
- To illustrate this, we’ll use another component from our hypothetical chat example. This is a chat message recipient picker that displays whether the currently selected friend is online:
```
const friendList = [
  { id: 1, name: 'Phoebe' },
  { id: 2, name: 'Rachel' },
  { id: 3, name: 'Ross' },
];

function ChatRecipientPicker() {
  const [recipientID, setRecipientID] = useState(1);  const isRecipientOnline = useFriendStatus(recipientID);
  return (
    <>
      <Circle color={isRecipientOnline ? 'green' : 'red'} />      <select
        value={recipientID}
        onChange={e => setRecipientID(Number(e.target.value))}
      >
        {friendList.map(friend => (
          <option key={friend.id} value={friend.id}>
            {friend.name}
          </option>
        ))}
      </select>
    </>
  );
}
```
- We keep the currently chosen friend ID in the  `recipientID`  state variable, and update it if the user chooses a different friend in the  `<select>`  picker.
- Because the  `useState`  Hook call gives us the latest value of the  `recipientID`  state variable, we can pass it to our custom  `useFriendStatus`  Hook as an argument:
```
  const [recipientID, setRecipientID] = useState(1);
  const isRecipientOnline = useFriendStatus(recipientID);
```
- This lets us know whether the  _currently selected_  friend is online. If we pick a different friend and update the  `recipientID`  state variable, our  `useFriendStatus`  Hook will unsubscribe from the previously selected friend, and subscribe to the status of the newly selected one.
### [Client Side vs Server Side Rendering](https://www.youtube.com/watch?v=Y2spCNZDt84)
#### Client side rendering
- Your intial request loads the page, layout, CSS, Javascript.
- Some of all of the content isn't included
- Instead, the Javascript makes another request, get a response(likely in JSON), and generates the appropriate HTML)likely using a templating library, such as React)
- For subsequent updates to the page, the client-side rendering approach repeats the steps it used to get the initial content
	- Javascript is used to get some JSON data and templating is used to create the HTML
- Work flow
	1. Server sending response to Browser
	2. Browser Downloads JS
	3. Brownser executes React
	4. Plage Now Viewable and Interactable
#### Server side rendering
- With server-side rendering, your initial request loads the page, layout, CSS, Javascript and **content - our HTML**
- Setup server-side rendering example
	```
	const React = require('react');
	const ReactDOM = require('react-dom');
	ReactDOM.render(<App />, document.getElementById('app'));
	// if you're using Server side rendering
	const React = require('react');
	const { renderToString } = require('react-dom/server');
	renderToString(<App />_
	// result must be inserted into target div
	```
- Updates using server-side rendering is where a lot of developers start going off the deep end. They actually think page refresh. Instead, what we can do is some form of
	```
	// example using jQuery
	$('#loadTweets').on('click', function(e) {
		$.get('/tweets/person', {last_id: 233}, function(r) {
			$('#tweets').prepend(r);
		};
		// stop the page from re-rendering
		e.preventDefault();
	});
	```
- In other words, we are still only doing a partial update, but letting the server do the rendering and inserting that finalized output into our DOM.
- Workflow:
	1. Server Sending Ready to be rendered HTML Response to Browser
	2. Browser Renders the page, Now Viewable and Browser Downloads JS
	3. Browser executes React
	4. Page Now Interactable
#### Why server side rendering initial render faster?
- Client side rendering requires more JS to be downloaded, which is more JS to parse. It requires a 2nd HTTP request to load the content, and then more JS to generate the template. Even if the initial JS gets cached, it still needs to get parsed, and the 2nd request(loading data) isn't going to happen until the docment is loaded
- Server side rendering's response to the browser is the HTML of your page that is ready to be rendered
#### SSR advantages over CSR
- The initial page load is faster
- The blank page flicker that happens with CSR also doesn't really happen with SSR (although most people mask this problem with a loading image)
- SSR is great for SEO. Your content is present before you get it, so search engines are able to index it and crawl it efficiently, something that is not so with client-side rendering. At least not simply
- Great for static sites or text-based sites.
#### SSR cons over CSR
- Frequent server requests
	-  Possible Bottlenecking with site that are very interactive
	- Throughput of your server is significantly less than CSR throughput.
	- For react in particular, the throughput impact is extremely large
	- `ReactDOMServer renderToString` is a **synchronus** CPU bound call, which holds the event loop**strong text**, which means the server will not be able to process any other request till `ReactDOMServer.renderToString` completes.
		- Let's say that it takes you 500ms to SSR your page, that means you can at most do at most 2 requests per second
- An overall slow page rendering
	- While the page is rendered earlier and the customer can see the page sooner, they can't really interact with it until react is done executing.
		- If custom is ready fast and clicks a button, the action won't be executed until React is done executing
- Non-rich site interactions

<!--stackedit_data:
eyJoaXN0b3J5IjpbODAwNjk5NTc4LC01MzIwMjE4OTUsLTE4Mj
Y1NzQ4MTYsMjA1MzQxOTM1MSwtNDgwODU1OTc4LC05Nzk3MzY4
ODIsMTk5MTk2MzI1OSwtNDU2Nzk5MjE4LC01NTY2MjM1ODcsNT
g4NDkyNjE2LC0yMTMwOTk4MjE3LC0xMjgyMDI4NjAwLC0xOTA3
MzA2NTQ1LC0xNDc1NDEzMzk5LDI3MTEzOTkzNSwtMTI2NjAyMz
A3MCw3NTk1ODA2NzksNzIzOTEwNzA3LDIwNzU5ODA2NDksMTUz
NzcyNTk0MV19
-->