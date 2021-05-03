### What is OnClick in React
- e.g: `<div onClick={clickHandler} />`
- The `onClick` is not an event but a wrapper around the actual DOM events.
### Why do we need a wrapper?
- A wrapper itself does multiple things, one of the most important things it does is, it handles event for any browser. So for example sometimes the IE handles differently on the click event than Firefox, similar way how jQuery does.
- Also sync
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyNzMyMDAwNjNdfQ==
-->