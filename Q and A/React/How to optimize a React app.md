- Ask Questions!
	- How big is the application
	- What does it do
	- Which version of react it's running
	- What things are we specifically trying to optimize, is it slow? or is it crashing? or is the bundle size too big?
- Performance related instead of speed
	- First Profiler to the app and then run the app to see which areas of the app is behaving badly, make sure you disable other plugin because it can interfere in the performance
- Speed
	- You might be rendering components that are not supposed to be re-rendering, for example if you are not changing a state but you're still setting the same state over and over that  would result in updating the virtual DOM which you don't need to do that.
	- We can stop rendering of certain components by using `shouldComponentUpdate`
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEyNDk2MTk3ODRdfQ==
-->