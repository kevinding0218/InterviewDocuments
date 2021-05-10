### Enum
- Enums are essentially final classes with a fixed number of instances. They can implement interfaces but cannot extend another class.
- This flexibility is useful in implementing the strategy pattern, for example, when the number of strategies is fixed
#### Singleton
Singleton using Enum in Java: By default creation of the Enum instance is thread-safe, but any other Enum method is the programmer’s responsibility.
```
public enum EasySingleton{
  INSTANCE;
}
```
#### Strategy 
- This flexibility is useful in implementing the strategy pattern, for example, when the number of strategies is fixed. 
- Consider an address book that records multiple methods of contact. We can represent these methods as an enum and attach fields, like the filename of the icon to display in the UI, and any corresponding behaviour, like how to initiate contact via that method:
```
public  enum ContactMethod { 
	PHONE("telephone.png") { 
		@Override  public void initiate(User user) { 
			Telephone.dial(user.getPhoneNumber()); 
		} 
	}, 
	EMAIL("envelope.png") { 
		@Override  public void initiate(User user) { 
			EmailClient.sendTo(user.getEmailAddress()); 
		} 
	}, 
	SKYPE("skype.png") { ... }; ContactMethod(String icon) { this.icon = icon; } private  final String icon; public abstract void initiate(User user); public String getIcon() { return icon; } }
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTEwNDA4Mzc5OTRdfQ==
-->