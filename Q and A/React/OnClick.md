### What is OnClick in React
- e.g: `<div onClick={clickHandler} />`
- The `onClick` is not an event but a wrapper around the actual DOM events.
### Why do we need a wrapper?
- A wrapper itself does multiple things, one of the most important things it does is, it handles event for any browser. So for example sometimes the IE handles differently on the click event than Firefox, similar way how jQuery does.
- Also synthetic events are pooled. A pool means for events they're reused, similar to like car pools. So you have one event, once it's executed, it's recycled and you can use it again for different purposes. 
- There is a way to use native events, 
### How event delegation works in React? How does react handles events in a list element? 
- for example, you have a list of element where each has a `onClick` event.

<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxMTExMDQ3NDBdfQ==
-->