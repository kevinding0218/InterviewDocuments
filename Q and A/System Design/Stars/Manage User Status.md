Managing user’s status
We need to keep track of user’s online/offline status and notify all the relevant users whenever a status
change happens. Since we are maintaining a connection object on the server for all active users, we can
easily figure out the user’s current status from this. With 500M active users at any time, if we have to
broadcast each status change to all the relevant active users, it will consume a lot of resources. We can
do the following optimization around this:
1. Whenever a client starts the app, it can pull the current status of all users in their friends’ list.
2. Whenever a user sends a message to another user that has gone offline, we can send a failure to
the sender and update the status on the client.
3. Whenever a user comes online, the server can always broadcast that status with a delay of a few
seconds to see if the user does not go offline immediately.
4. Client’s can pull the status from the server about those users that are being shown on the user’s
viewport. This should not be a frequent operation, as the server is broadcasting the online status
of users and we can live with the stale offline status of users for a while.
5. Whenever the client starts a new chat with another user, we can pull the status at that time.
Detailed
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3OTczOTc1OTFdfQ==
-->