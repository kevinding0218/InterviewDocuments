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
- EB structure would include many application versions in one Unique Application (e.g: My Application), with the code of different application version, you can deploy to different environments, like Test/Prod, 
- An environment is the rules and configuration that manages actual EC2 instances, each envrionment can run in different platforms (e.g, Java or Node), and can be configured with certain EC2 instance types.
- Configuring the environment in EB is where you spent most of time, since you will set up the deployment, load balancing and scaling rules there.
- Application versions with the actual bits of files are stored in S3,  there is a limit number of each application versions of 1000
- Monitoring in EB will aggregate data across all applications, provided metrics such as CPU, Number of Requests, and Network Traffic.
- Pricing: EB is free in AWS, the only payment is for EC2 instances, load balancers and S2 separately
#### Lambda
- Provides a function code execution as a service
- Only pay when your code is running, it means you don't need to pay for idle servers or idle load balancer
- Pricing
	- First 1 million requests per month free
	- First 400k Gb-seconds per month free
	- $0.20 UDS per 1M
	- $0.18 USD per 24hrs 128mb Function Execution
#### DynamoDB
- A managed NOSQL DB
#### Virtual Private Cloud (VPC)
- commonly used when launch EC2 instance, whether in the service os Elastic Beanstalk, RDS or by themselves, in order to easily control and secure access to them
- Security groups secure single instances
- VPCs secure groups of instances, inside the VPC there are multiple Subnet, those subnets are a further way to group your resources and assign different rules to each
- One reason to use Subnet inside VPC is to set up both private and public subnets.
	- The Private Subnet might hold your databases and application instances, the private subnet would have no internet access at all, keep it quite secure, it may use a NAT gateway in the public subnet to securely access the internet.
	- The Public Subnet would have access to internet, and could utilize security groups to make it secure, you could also launch an EC2 instance in public subnet to act as a tunnel to SSH into your private EC2 instance
- Two keys VPC controls routing
	- Routing Table: allow you to override certain IP ranges and redirect the traffic elsewhere. A use case is if you want to direct all outgoing traffic to a NAT gateway that will filter traffic and mask the instance's IP address
	- Network ACL (Access Control List):  act as subnet-level firewalls, allowing or disallowing IP ranges for both incoming and outgoing connections.
- Free
#### CloudWatch
- A monitoring and alarm services for many other different services in AWS
- For each alarm you could choose from a specific metric examples, such as EC2 - CPU Utilization, 
#### CloudFront
### 
<!--stackedit_data:
eyJoaXN0b3J5IjpbNjYwNzQ2NDcsLTE3NDM1MTEzODcsLTEzNz
g1NjEzNTAsLTc2NjM0MjU3MSwxNzkyODczOTA1LC0xNDMyMDg4
MzMzLC05OTE3MTUzNDksODQ1NDcyMTEyLC0zMTMyNjQwNDgsLT
Y4NTA3NTM5MiwxNTA0MTk4NjA0LDczNDczMDAzNCw5Mjc1MzYz
NjUsMTg4NTYxNjU1OSwxNDMyMzkwODQ2LC0yMDg4NzQ2NjEyLD
czMDk5ODExNl19
-->