### Interface vs Abstract Class
#### Variable Type
- The interface has only static and final variables.
- Abstract class can have final, non-final, static and non-static variables. 
#### Method Type
- Interface can have only abstract methods. 
- An abstract class has protected and public abstract methods.
#### Implementation
- A Class can implement multiple interfaces
- A Class can inherit only one Abstract Class because Java does not support multiple inheritances
#### Multiple Implementation
- An interface can extend another Java interface only
- an abstract class can extend another Java class and implement multiple Java interfaces.
#### Default Implementation
- While adding new stuff to the interface, it is a nightmare to find all the implementors and implement newly defined stuff.
- In case of Abstract Class, you can take advantage of the default implementation.
#### When to use
- It is better to use interface when various implementations share only method signature. Polymorphic hierarchy of value types. (Future enhancement)
- It should be used when various implementations of the same kind share a common behavior. (To avoid independence)
#### Constructor
- An interface cannot declare constructors or destructors.
- An abstract class can declare constructors and destructors.
### When designing an abstract class, why should you avoid calling abstract methods inside its constructor?
- This is a problem of initialization order. The subclass constructor will not have had a chance to run yet and there is no way to force it to run it before the parent class. Consider the following example class:
```
public  abstract  class Widget { 
	private  final  int cachedWidth; 
	private  final  int cachedHeight; 
	public Widget() { 
		this.cachedWidth = width(); 
		this.cachedHeight = height(); 
	} 
	protected abstract int width(); 
	protected abstract int height(); 
}
```
This seems like a good start for an abstract Widget: it allows subclasses to fill in  `width`  and  `height`, and caches their initial values. However, look when you spec out a typical subclass implementation like so:

```java
        public class SquareWidget extends Widget {
	        private final int size;
	
	        public SquareWidget(int size) {
	            this.size = size;
	        }
	
	        @Override
	        protected int width() {
	            return size;
	        }
	
	        @Override
	        protected int height() {
	            return size;
	        }
	    }

```

Now we’ve introduced a subtle bug:  `Widget.cachedWidth`  and  `Widget.cachedHeight`  will always be zero for  `SquareWidget`  instances! This is because the  `this.size = size`  assignment occurs  _after_  the  `Widget`  constructor runs.

Avoid calling abstract methods in your abstract classes’ constructors, as it restricts how those abstract methods can be implemented.
<!--stackedit_data:
eyJoaXN0b3J5IjpbNDY4NDg3MjExXX0=
-->