### AWS - a provider of cloud services
- Providing a range of computing services with on-demand scalability and flexiable pricing
#### Azure vs GCP vs AWS
- Azure: Best place to run Microsoft product
- GCP (Google Cloud Provider): Specializations in Big Data, Kubernates and AI
- AWS: Better web console
- Pluralsight: Understanding the Difference between Azure and AWS
#### AWS Core Service
##### Elastic Cloud Compute (EC2)
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
	- 
##### Simple Storage Service (S3)
##### Relational Database Service (RDS)
##### Route53
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1NTQwMjkzNDUsNzM0NzMwMDM0LDkyNz
UzNjM2NSwxODg1NjE2NTU5LDE0MzIzOTA4NDYsLTIwODg3NDY2
MTIsNzMwOTk4MTE2XX0=
-->