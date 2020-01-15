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
- e.g
```javascript
const [someState, setSomeState] = useState('anything')
const someFunction = () => { setSomeState('new state') }
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzIzOTEwNzA3LDIwNzU5ODA2NDksMTUzNz
cyNTk0MSw2NTQzNjkyMTZdfQ==
-->