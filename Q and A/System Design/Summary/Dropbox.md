### Functional Requirements
1. Users should be able to upload and download their files/photos from any device.
2. Users should be able to share files or folders with other users.
3. Our service should support automatic synchronization between devices, i.e., after updating a file
on one device, it should get synchronized on all devices.
4. The system should support storing large files up to a GB.
5. ACID-ity is required. Atomicity, Consistency, Isolation and Durability of all file operations
should be guaranteed.
6. Our system should support offline editing. Users should be able to add/delete/modify files while
offline, and as soon as they come online, all their changes should be synced to the remote
servers and other online devices.
### Non Functional Requirements
- Availability: The motto of cloud storage services is to have data availability anywhere, anytime. Users can access their files/photos from any device whenever and wherever they like.
- Reliability and Durability: Another benefit of cloud storage is that it offers 100% reliability and durability of data. Cloud storage ensures that users will never lose their data by keeping multiple copies of the data stored on different geographically located servers.
- Scalability: Users will never have to worry about getting out of storage space. With cloud storage you have unlimited storage as long as you are ready to pay for it.
### High Level Design
```
		/	Processing Service	\-	Cloud Storage
Client	-	Metadata Service	-	Metadata Storage
		\	Sync Service
```
- The user will specify a folder as the workspace on their device. Any file/photo/folder placed in this folder will be uploaded to the cloud, and whenever a file is modified or deleted, it will be reflected in the same way in the cloud storage.
- At a high level, we need to store files and their metadata information like File Name, File Size, Directory, etc., and who this file is shared with. So, we need some servers that can help the clients to upload/download files to Cloud Storage and some servers that can facilitate updating metadata about files and users. We also need some mechanism to notify all clients whenever an update happens so they can synchronize their files.
- Processing services will work with the clients to upload/download files from cloud storage 
- Metadata services will keep metadata of files updated in a SQL or NoSQL database. 
- Synchronization services will handle the workflow of notifying all clients about different changes for synchronization. High
#### How can clients efficiently listen to changes happening with other clients?
- A solution to the above problem could be to use HTTP long polling. With long polling the client requests information from the server with the expectation that the server may not respond immediately.
- If the server has no new data for the client when the poll is received, instead of sending an empty response, the server holds the request open and waits for response information to become available.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE1NTY4NjExMjIsLTE1NTY4NjExMjJdfQ
==
-->