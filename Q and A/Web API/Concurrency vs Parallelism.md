###  Concurrency vs Parallelism
- **Concurrency**  is when two or more tasks can start, run, and complete in overlapping time  **periods**. It doesn't necessarily mean they'll ever both be running  **at the same instant**. For example,  _multitasking_  on a single-core machine.
	- If two or more problems are solved by a single processor, Concurrency means executing multiple tasks at the same time but not necessarily simultaneously.
	- There’s a challenge that requires you to both eat a whole huge cake and sing a whole song. You’ll win if you’re the fastest who sings the whole song and finishes the cake. So the rule is that you sing and eat simultaneously, How you do that does not belong to the rule. You can eat the whole cake, then sing the whole song, or you can eat half a cake, then sing half a song, then do that again, etc.

- **Parallelism**  is when tasks  _literally_  run at the same time, e.g., on a multicore processor.
	- If one problem is solved by multiple processors.
		- the rule is still singing and eating concurrently**,** but this time, you play in a team of two. You probably will eat and let your friend sing. So this time, the two tasks are really executed simultaneously, and it’s called _parallel_.
```
I would recommend taking a look at  [ExecutorService]
```
ExecutorService EXEC = Executors.newCachedThreadPool();
List<Callable<Result>> tasks = new ArrayList<Callable<Result>>();
for (final Object object: objects) {
    Callable<Result> c = new Callable<Result>() {
        @Override
        public Result call() throws Exception {
            return compute(object);
        }
    };
    tasks.add(c);
}
List<Future<Result>> results = EXEC.invokeAll(tasks);
```
```
#### Concurrency is about dealing with lots of things at once. Parallelism is about doing lots of things at once.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTE0MDM1MTExN119
-->