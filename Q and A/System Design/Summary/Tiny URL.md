### Requirements/Goals
#### Functional
- Having a service to generate a shorter and unique url link by given a URL input
- Redirect user to original link when client tries to access with provided shorten url
- **Discussion**: Should shorten url link be expire? Who would make the decision? User or Us?
- **Discussion**: Users should optionally be able to pick a custom short link for their URL.
#### Non Functional
- High Availablity: if our service is down, all the URL redirections will start failing.
- High Performant: with minimal latency
- **Daily Usage** (QPS + Storage)
	- Ask interviewer daily active user (DAU)
		- nearly 100M
	- Estimate QPS for generating one tiny URL
		- suppose each user post 0.1 twitter that includes a tiney URL
		- Average Write QPS = 100M * 0.1/86400 ~ 100
		- Peak Write QPS = 100 * 2 = 200
	- Estimate QPS for clicking on one tiny URL
		- suppose each user click on 1 tiny URL
		- Average Read QPS = 100M * 1 / 86400 ~ 1k
		- Peak Read QPS = 2k => a SSD supported MySQL can deal with it
	- Estimate Storage for new URL daily
		- 100M * 0.1 ~ 10M URL daily
		- average storage for a tiny url like 100 bytes, total of 1G
		- 1T hard disk be used for 3 years
<!--stackedit_data:
eyJoaXN0b3J5IjpbNzMyNzUxNzQ0LC0yMDg4NzQ2NjEyXX0=
-->