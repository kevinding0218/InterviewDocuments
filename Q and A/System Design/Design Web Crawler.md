## Example
- Dropbox, Google, Alibaba
- For collecting data/information from the web
	- table columns definition and example
		- url: "http://www.wiki.org"
		- page_content: "<!DOCTYPE html><html>...</html>"
- How to use this
	- when user search for "John Smith"
	- Google does a string search on page_content column and return the urls in an collection of those whose page_content contain "John Smith"

### Scenario
- How many web pages? How long? How large?
	- crawl 1.6m web pages per second
		- 1 trillion web pages
		- crawl all of them every week
	- 10p (petabyte) web page storage
		- aeverage size of web page: 10k
- A simplistic news crawler
- A simplistic web crawler
- A single-threaded web crawler
- A multi-threaded web crawler
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTQ4OTk0Nzc0MywtMTY4NjQ4MjE1LC0xNT
Y1ODY2ODE4XX0=
-->