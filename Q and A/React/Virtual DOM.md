### Explain Virtual DOM
- Before react came along with virtual dome, updating the dome is really expensive because every time you update something you have to repaint that on the dome every time and that can be time-consuming
- React has a **virtual representation of the actual DOM written in Javascript (the 'Render' function) because Javascript is really fast.**, Any updates that virtual DOM tree happens through the change in state and props
- E.g, when you click a button, **React actually kind of recreates the virtual DOM in a second time and it recreates the way that would look like after this state change**, it will then compare the new virtual DOM to the old virtual DOM, if there is no difference, **actual DOM never gets touched but if there is change, it will only render the part which gets affected.**
- It's fast because it **uses the virtual DOM for comparison instead of the actual DOM** which would have been slow to begin with, and then when it has to re-render something, **it only renders the part which changed and not everything**
- Development tools --> Rendering tab, enable paint flashing where you could see which part of the DOM gets affected and updated
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTUwNzk0MjQ5MiwxODAyNjE2MzIxLC0xNT
kxMzU1ODEyXX0=
-->