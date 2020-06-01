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
3. static getDerivedStateFromProps(nextProps, prevState)
- this method is actually static
- it runs right after constructor
- it has role in initial render and also re-render face
- it tries to get the derived state from the change in props that it would return the `newState` if there is change in state or `null` if there is no change in state
- Why it's a static method? static method will prevent user to access `this` keyword directly inside the method, because static method is actually class method, not instance method
- in the vast majority of cases  **you don't need  `getDerivedStateFromProps`  at all**.
If you just want to compute some derived data, either:
4.  Do it right inside  `render`
5.  Or, if re-calculating it is expensive, use a memoization helper like  `memoize-one`.
6. render
- only method that's mandatory to have
- you cannot set state here, otherwise you would go an infinite loop
7. componentDidMount
- notify that `DOM` is ready now, e.g: if you're using a third-party chart component, you need to have the DOM ready before component uses it
#### re-render
1. static getDerivedStateFromProps(nextProps, prevState)
- does the same thing as intial render
2. shouldComponentUpdate
- make a decision if this component really needs to be updated or not
- e.g, whenever setState will trigger re-render, but it could be setting the same state again, there might not be need to re-render
3. render
4. getSnapshotBeforeUpdate
- pre commit phase, render doesn't mean it has rendered, `mount` really happens after this method (replace for componentWillUpdate)
- This method can be used when there is delay between if you render a component and in its next phase, if user does something in between like scoll or change size of the window, then you need to remember where the scroll was before so you can do something after the render.
6. componentDidUpdate
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjIzMzc0NzIxLC0xMjY2MDIzMDcwLDc1OT
U4MDY3OSw3MjM5MTA3MDcsMjA3NTk4MDY0OSwxNTM3NzI1OTQx
LDY1NDM2OTIxNl19
-->