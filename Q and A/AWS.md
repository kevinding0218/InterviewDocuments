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
	- Add Storage, which refers to 
##### Simple Storage Service (S3)
##### Relational Database Service (RDS)
##### Route53
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3MTAxMzQ1MjcsOTI3NTM2MzY1LDE4OD
U2MTY1NTksMTQzMjM5MDg0NiwtMjA4ODc0NjYxMiw3MzA5OTgx
MTZdfQ==
-->