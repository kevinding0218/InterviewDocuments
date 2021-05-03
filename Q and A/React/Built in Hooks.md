### List all the inbuilt hooks in React 
#### Main Hooks
- useState: allows you to define state 
- useEffect: allows you to handle life cycle method
- useRef: allows you to set a reference to specific DOM element or others
- useContext: so you can set something globally similar to redux.
#### Other Hooks
- useReducer: 
- useMemo
- useImparativeHandle:
- useLayoutEffect
- useDebugValue
### What are some of the Best Practises using React Hooks?
- Follow the Hook's rules
- Use linter and follow linter (ES-lint)
- for example you have some states
```
const[a, setA] = useState();
const[b, setB] = useState();
setA();
setB();
```
- when you call `setA()`, this is an async event which means it's going to read under the page, and later comes to setB, so you can create a setAB that update both in a same statement.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTMyNDAyMDE4NF19
-->