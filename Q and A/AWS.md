## AWS
- Providing a range of computing services with on-demand scalability and flexiable pricing
### Azure vs GCP vs AWS
- Azure: Best place to run Microsoft product
- GCP (Google Cloud Provider): Specializations in Big Data, Kubernates and AI
- AWS: Better web console
- Pluralsight: Understanding the Difference between Azure and AWS
### AWS Core Service
#### Elastic Cloud Compute (EC2)
-  Computing services operating in remote data servers around the world
-  Instances running computing operations can increase or decrease at will
-  An EC2 instance is essentially a virtual server which is operating system agnostic.
- Create an EC2 instance
	- Choose an Amazon Machine Image (AMI), which is a combination of an operating system and some application/software preinstalled, such like Java, Python or AWS CLI tools. Amazon provides, manages and updates a selection of images that are available.
	- Select an instance type, which is the spec of your instance, the number of CPUs, the amount of RAM, and the network performance.
	- Configure Instance Details, which is related to security roles, number of insntances, etc. You can select "Auto Scaling Group", which is for automatically scaling EC2 images up and down
		- Use EC2 for scaling AMIs
		- Use EB (Elastic Beanstalk) for scaling applications 
	- Add Storage, which refers to EBS (Elastic Block Storage)
		- Use EBS for EC2 file systems
		- Use S3 for file storage
	- Configure Security Group, which is IP-based communication rules for a single or group of server instances
		- control who can SSH into EC2 instance
		- allow access between EC2 instance
		- allow access to databases
		- accept HTTP requests
- Pricing
	- EC2 instances are charged by the hour
	- based on the instance type and image that you selected, e.g: Windows image will cost more than a Linux simply because image needs to be licensed and paid by Amazon
	- Elastic Block Storage: $0.1 USD per GB/month
	- Auto Scaling Groups: Free
	- Load Balancer: $0.0225 USD/hour
#### Simple Storage Service (S3)
-  widely used throughout AWS as the place to store files. 
- can store any type of file but only limitation is max file size is 5 TB
- Bucket: 
	- root resource to S3 which you can add, delete or modify objects
	- can trigger events when objects are added/modified/deleted
	- preserve older versions of objects
	- replicate objects across regions
	- can be access through URL
- Solve Latency in S3
	- Use CloudFront, you can cache your content
- Pricing
	- based on amount of data stored, number of requests, amount of data transferred
#### Relational Database Service (RDS)
- a collection of AWS services for managed relational databases, it's managed because AWS takes care of
	- Scheduled automated backups
	- Simple software updates
	- infrastructure of database
-  RDS databases run on EC2 Instances
-  RDS makes it easy to take DB snapshots or change the hardware
-  RDS instance <-> Security Group <-> EC2, BI but not external application
- Pricing
	- Type of database
	- Region
	- EC2 instance type
#### Route53
- DNS (Domain Name System): a system that translates human-readable URLs to IP addresses
- Route53 allows you to set URL between website and EC2 application such as S3
- Is the Core to let users interact with services in AWS
	- Setup a hosted zone, which is like a root domain name, like example.com or google.com
	- Using hosted zone you can setup subdomains like mail.example.com and configure them to route to AWS resources.
	- Healthcare check, allows you to set up regular checks for a given URL path, will send you alerts based on different rules.
- Pricing
	- Hosted Zone: $0.5 USD per month
	- Queries: $0.4 USD per 1M
	- DNS Entries: Free
	- Alarm: $0.5 UDS per Health Check
### Other services
#### Elastic Beanstalk (EB)
- Deploy your app with EB is easier with various tools than EC2
- EB structure would include many application
#### Lambda
#### DynamoDB
#### Virtual Private Cloud (VPC)
#### CloudWatch
#### CloudFront
### 
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTc4NjQzMjEyOSwtOTkxNzE1MzQ5LDg0NT
Q3MjExMiwtMzEzMjY0MDQ4LC02ODUwNzUzOTIsMTUwNDE5ODYw
NCw3MzQ3MzAwMzQsOTI3NTM2MzY1LDE4ODU2MTY1NTksMTQzMj
M5MDg0NiwtMjA4ODc0NjYxMiw3MzA5OTgxMTZdfQ==
-->