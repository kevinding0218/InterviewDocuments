### Why look for a change
- I'm an experienced web developer who is passionate about building great products with continuing chasing after cutting-edge technology 
- I love what I do, and I're always working to improve my skills. 
- I'd like to join a company that will actively invest in my growth as an engineer. 
- I want to work somewhere that will set I up for long term career success and have a meaningful impact on the business. 
- Larger companies sometimes offer those benefits, but I don't want to be another cog in the machine
- I're looking for a small- to mid-sized team of high-performers like myself.
### What is the output of the program below?
```
delegate void Printer();
static void Main()
{
        List<Printer> printers = new List<Printer>();
        int i=0;
        for(; i < 10; i++)
        {
            printers.Add(delegate { Console.WriteLine(i); });
        }
        foreach (var printer in printers)
        {
            printer();
        }
}
```
- output the number 10 ten times.
- Why: The delegate is added in the for loop and “pointer” to `i` is stored, rather than the value itself. Therefore, after we exit the loop, the variable `i` has been set to 10, so by the time each delegate is invoked, the value passed to all of them is 10.

1. [Linq/Method Extension/Deferred Execution/Generic/Delegates](https://medium.com/sears-israel/my-number-one-c-interview-question-39cdaac16c)
2. 
