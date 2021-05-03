### How to optimize a React App
#### Ask Questions!
- How big is the application
- What does it do
- Which version of react it's running
- What things are we specifically trying to optimize, is it slow? or is it crashing? or is the bundle size too big?
#### Performance related instead of speed
- First Profiler to the app and then run the app to see which areas of the app is behaving badly, make sure you disable other plugin because it can interfere in the performance
#### Speed
- You might be rendering components that are not supposed to be re-rendering, for example if you are not changing a state but you're still setting the same state over and over that  would result in updating the virtual DOM which you don't need to do that.
- We can stop rendering of certain components by using `shouldComponentUpdate` lifecycle method or if you have a newer version you can use react.memo or react pure components
#### Package size huge
- it may take too long to load you can use lazy loading, to lazy load on certain routes that you or the client may be using later on or may not be even using it
#### Something else
- you may find out the api's
are slow so there may be some
improvement needed on the server side
that may be a bottleneck as well it's
not always a react app is slow it could
be something else
<!--stackedit_data:
eyJoaXN0b3J5IjpbODk2MDU4Mzk2XX0=
-->