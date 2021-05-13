### A simple example of a MIME multipart message is as follows:

```
MIME-Version: 1.0
Content-Type: multipart/mixed; boundary=frontier

This is a message with multiple parts in MIME format.
--frontier
Content-Type: text/plain

This is the body of the message.
--frontier
Content-Type: application/octet-stream
Content-Transfer-Encoding: base64

PGh0bWw+CiAgPGhlYWQ+CiAgPC9oZWFkPgogIDxib2R5PgogICAgPHA+VGhpcyBpcyB0aGUg
Ym9keSBvZiB0aGUgbWVzc2FnZS48L3A+CiAgPC9ib2R5Pgo8L2h0bWw+Cg==
--frontier--
```
- Each MIME message **starts with a message header**. This header contains information about the **message content and boundary**. In this case  `Content-Type: multipart/mixed; boundary=frontier`  means that message contains multiple parts where each part is of different content type and they are separated by  `--frontier`  as their boundary.
- Each part consists of its own content header (zero or more  `Content-`  header fields) and a body. Multipart content can be nested. The  `content-transfer-encoding`  of a multipart type must always be  `7bit`,  `8bit`, or  `binary`  to avoid the complications that would be posed by multiple levels of decoding. The multipart block as a whole does not have a charset; non-ASCII characters in the part headers are handled by the  `Encoded-Word`  system, and the part bodies can have charsets specified if appropriate for their  `content-type`.
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTc3MTM4OTA0Nl19
-->