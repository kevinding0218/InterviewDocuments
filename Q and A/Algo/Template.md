## LinkedList
### reverse a linked list
```
private static ListNode reverseList(ListNode head) {  
  ListNode prev = null;  
    ListNode cur = head;  
  
    while (cur != null) {  
  ListNode next = cur.next;  
        cur.next = prev;  
        prev = cur;  
        cur = next;  
    }  
  return prev;  
}
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxMDcyMjk1N119
-->