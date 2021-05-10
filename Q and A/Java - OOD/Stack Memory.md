####  Java Stack Memory
- Java **Stack memory is used for the execution of a thread**. They contain method-specific values that are short-lived and references to other objects in the heap that is getting referred from the method.
- Stack memory is always referenced in LIFO (Last-In-First-Out) order. Whenever a method is invoked, a new block is created in the stack memory for the method to hold local primitive values and reference to other objects in the method.
- As soon as the method ends, the block becomes unused and becomes available for the next method.  
- Stack memory size is very less compared to Heap memory.
#### Example
```
public  class  Memory { 
	public static void main(String[] args) { // Line 1  				    
		int i=1; // Line 2 
		Object obj = new Object(); // Line 3 
		Memory mem = new Memory(); // Line 4 	
		mem.foo(obj); // Line 5 
	} // Line 9  
	private void foo(Object param) { // Line 6 
		String str = param.toString(); // Line 7 	
		System.out.println(str); 
	} // Line 8 
}
```
-   As soon as we **run the program**, it **loads all the Runtime classes into the Heap space**. When the **main()** method is found at line 1, **Java Runtime creates stack memory to be used by main() method thread**.
-   We are creating **primitive local variable** at line 2, so it’s **created and stored in the stack** memory of main() method.
-   Since we are creating an **Object** in the 3rd line, it’s created in **heap memory and stack memory contains the reference for it**. A **similar** process occurs when we create **Memory object** in the 4th line.
-   Now when we call the **foo() method** in the 5th line, **a block in the top of the stack is created** to be used by the foo() method. Since Java is pass-by-value, **a new reference to Object is created in the foo() stack block** in the 6th line.
-   A **string** is created in the 7th line, it goes in the **String Pool** in the **heap space and a reference is created in the foo() stack space** for it.
-   foo() method is terminated in the 8th line, **at this time memory block allocated for foo() in stack becomes free**.
-   In line 9, **main() method terminates** and the **stack memory created for main() method is destroyed**. Also, the **program ends** at this line, hence **Java Runtime frees all the memory and ends the execution of the program**.
<!--stackedit_data:
eyJoaXN0b3J5IjpbNjUyMDQ5MzMxXX0=
-->