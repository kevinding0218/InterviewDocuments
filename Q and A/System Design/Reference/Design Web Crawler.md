## Example
- Interviewed by: Dropbox, Google, Alibaba
- For collecting data/information from the web
	- table columns definition and example
		- url: "http://www.wiki.org"
		- page_content: "<!DOCTYPE html><html>...</html>"
- How to use this
	- when user search for "John Smith"
	- Google does a string search on page_content column and return the urls in an collection of those whose page_content contain "John Smith"

## Scenario
### How many web pages? How long? How large?
	- crawl 1.6m web pages per second
		- 1 trillion web pages
		- crawl all of them every week
	- 10p (petabyte) web page storage
		- aeverage size of web page: 10k
- A simplistic news crawler
- A simplistic web crawler
- A single-threaded web crawler
- A multi-threaded web crawler

### A simplistic News Crawler
- How will it work
	- given the URL of news list page
	- Send an HTTP request and grab the content of the news list page
		```
		import urllib2
		request = urllib2.Request(requestUrl);
		response = urllib2.urlOpen(request);
		page = response.read();
		```
	- Extract all the news titles from the news list page
		- Regular Expression "<h3> <a>[*?]</a></h3>" for matching html content only for header or anchor link
		- Outputs: a list of news titles 

### A simplistic Web Crawler
- How will it work
	- Input: url seeds
	- Output: list of urls

### A single-threaded Web Crawler
- use BFS
- starting seeds can be Url Queue
	- Url Queue will be stored in memory
	- Url goes to web page loader
	- Save web page content into storage
	- If there are additional link url in current web page content, extract them as a list then put into the Url Queue (The Queue would be increasing larger)
	- typically, one Url contains many other Url
```
thread crawler
	function run
		while (url_queue not empty)
			url = url_queue.dequeue();
			html = web_page_loader.load(url)	//consume
			url_list = url_extractor.extract(html)	// produce
			url_queue.enqueue_all(url_list)
		end
```
- Producer Consumer Pattern
	- Producer -> Buffer -> Consumer
	- why need buffer? because speed or producer and consuer is different, usually producer is faster than consumer
- might be slow

### A multi-threaded Web Crawler
- we can open multi-thread of Crawler but sharing with same Url Queue
- why multi-thread is better than single-thread
	- for a single thread, there would be a waiting time period between sending the request to server and waiting for the response, the waiting time could because of DNS, CDN or Firewall connection during the waiting time (~200ms), CPU is not doing anything efficint
	- for multi thread, during the waiting time, it can send a 2nd request to web server, during same time period, multi thread can do multiple jobs
- How different threads work together? (how to we solve sharing resource(Url Queue) write problem among multi-thread)
- we wouldn't want multi-thread to write at same time of sharing resource
	- sleep
		- sleep for a random period then go back every time and check if resource is available again
	- condition variable
	- semaphore
- However, more threads doesn't necessarily mean more performance
	- context switch cost (CPU number limitation)
	- thread (port) number limitation
	- network bottleneck for single machine
- Url Queue Issue
	- Size concern
		- 1000000000000 * 20 (chars) * 2 (bytes per char)  / 1000(kb) / 1000 (mb) / 1000 (gb) / 1000 (tb) ~ 40tb
		- we cannot store in memory for 40tb size, we have to store in database
		- Task table design
			- id
			- url
			- state (idle/working)
			- priority (0, 1, 2)
			- available time (if current time is earlier than available time, we can wait until available time to process this url)

### Interviewer: how to improve slow select
- Table sharding
	- we can split Task table into multiple Task tables
	- after splitting the task table, we are not only request data from one machine but multiple machines,
	- so we need a scheduler 
WebPageStorage -> [Crawler Machine 1 & Crawler Machine 2 & Crawler Machine 3](Web) -> Scheduler -> [Task Table 1 & Task Table 2 & Task Table 3]
### Interviewer: how often should we do crawler
- Exponential back-off
	- e.g: if currently we would crawler on www.sina.com every 1 week (7 days), by the next time we found the content changed a lot, we can set the next period to be 7/2 = 3 days, then after 3 days we found same behavior, we can set the period to be 3/2 = 1 days, and so on...
	- e.g: if currently we would crawler on www.sina.com every 1 week (7 days), by the next time we found no content changed or url deactivate (404), we can set the next period to be 1 * 2 = 2 weeks, then after 2 weeks, we found same behavior, we can set the period to be 2 * 2 =4 weeks, and so on...
### Interviewer: how to handle dead cycle
- Too many web pages (url) in sina.com, the crawler keeps crawling sina.com and don't crawl other websites
- Use Quota
	- e.g: scehduler can arrage no more than 10% task for sina.com
### Multi-region
- Set a web crawler service in China and periodically sync the data with global data
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjEyMDA4ODcxXX0=
-->