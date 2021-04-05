## LinkedList
### Reverse a linked list
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
### Find Middle Node
- hint: when find middle node, use a dummy head that points next to head,  then slow and fast can both be started at dummy head, this is to avoid if list only has two nodes
```
private static ListNode findMiddleNode(ListNode head) {  
  ListNode dummy = new ListNode(-1);  
    dummy.next = head;  
    ListNode slow = dummy, fast = dummy;  
    while (fast != null && fast.next != null) {  
		slow = slow.next;  
        fast = fast.next.next;  
    }  
  return slow;  
}
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTc4OTEzMDQ0NSwtMTYyNTMwMTA0Nl19
-->