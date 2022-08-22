
|  | Encoding | Hashing  | Encryption |
|--|--|--|--|
| What | In Encoding, data is transformed from one form to another | In hashing, data is converted to the hash using some hashing function, which can be any number generated from string or text. | In Encryption, message is encoded by using encryption algorithm in such a way that only authorized personnel can access the message or information.  |
| Usage | transform data into a form that is readable by most of the systems or that can be used by any external process. | Hash function can be any function that is used to map data of arbitrary size to data of fixed size. | It is a special type of encoding that is used for transferring private data |
| Reverse | Yes, can’t be used for securing data, various publicly available algorithms are used for encoding. | No, data once hashed is non-reversible. | Yes as long as you have the key |
| Example | Encoding can be used for  **reducing the size**  of audio and video files. Each audio and video file format has a corresponding coder-decoder (codec) program that is used to code it into the appropriate format and then decodes for playback. | When you send pictures and text messages over WhatsApp over StackOverflow(posting in questions), images are sent to different server and text is sent to a different server for efficiency purposes. So for verifying images that the images are not tampered in between data transfer over the internet, hashing algorithm like MD5 can be used. | sending a combination of username and password over the internet for email login. |

### Encoding :
- In the Encoding method, data is transformed from one form to another. 
- The main aim of encoding is to transform data into a form that is readable by most of the systems or that can be used by any external process.  
- It can’t be used for securing data, various publicly available algorithms are used for encoding.
- For example: Encoding can be used for  **reducing the size**  of audio and video files. Each audio and video file format has a corresponding coder-decoder (codec) program that is used to code it into the appropriate format and then decodes for playback.
### Hashing : 
- In hashing, data is converted to the hash using some hashing function, which can be any number generated from string or text. Various hashing algorithms are MD5, SHA256. Data once hashed is non-reversible.
### Encryption :
- Encryption in encoding technique in which message is encoded by using encryption algorithm in such a way that only authorized personnel can access the message or information.
- It is a special type of encoding that is used for transferring private data, for example sending a combination of username and password over the internet for email login.


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTI5NDQ2NzkzNF19
-->