
|  | Encoding | Hashing  | Encryption |
|--|--|--|--|
| What | In Encoding, data is transformed from one form to another | In hashing, data is converted to the hash using some hashing function, which can be any number generated from string or text. | In Encryption, message is encoded by using encryption algorithm in such a way that only authorized personnel can access the message or information.  |
| Usage | transform data into a form that is readable by most of the systems or that can be used by any external process. | Hash function can be any function that is used to map data of arbitrary size to data of fixed size. | It is a special type of encoding that is used for transferring private data |
| Reverse | **Yes**, can’t be used for securing data, various publicly available algorithms are used for encoding. | **No**, data once hashed is non-reversible. | **Yes** as long as you have the key |
| Example | **Reducing the size**  of audio and video files. Each audio and video file format has a corresponding coder-decoder (codec) program that is used to code it into the appropriate format and then decodes for playback. | When you send pictures and text messages over WhatsApp over StackOverflow(posting in questions), images are sent to different server and text is sent to a different server for efficiency purposes. So for verifying images that the images are not tampered in between data transfer over the internet, hashing algorithm like MD5 can be used. | Sending a combination of username and password over the internet for email login. |


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5MTgzNDc3MzFdfQ==
-->