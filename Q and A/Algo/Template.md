## LinkedList
### Reverse a linked list
```
// Time: O(n)
// Space:O(1)
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
// Time: O(n)
// Space:O(n) the extra space comes from implicit stack // space due to recursion.
public static ListNode reverseListRecursive(ListNode head) {  
  if (head == null || head.next == null) return head;  
  
    ListNode last = reverseListRecursive(head.next);  
    // reverse - work backward with stacked pop node  
 // make current node next node points to itself, a cycle was found here  head.next.next = head;  
    // break the cycle so that current node not points to any node  
  head.next = null;  
    return last;  
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
### Heap/Priority Queue
#### Top K
##### Define a Heap
```
// minHeap
PriorityQueue<P> minHeap = new PriorityQueue<>(K);
// maxHeap
PriorityQueue<P> minHeap = new PriorityQueue<>(K, Comparator.reverseOrder());
// customHeap
Comparator<Integer> comparator = (o1, o2) -> {  
  if(o1 < o2) {  
	  return 1;  
  } else if(o1 > o2) {  
	  return -1;  
  } else {  
	  return 0;  
  }  
};
PriorityQueue<P> minHeap = new PriorityQueue<>(K, comparator);
```
##### Top K
```
for (var num: nums) {  
  heap.offer(num);  
  if (heap.size() > k) {  
	  heap.poll();  
  }  
}
```
##### Time: O(logK) offer/poll
##### Space: O(K)
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTYwMTc3NDU4Nyw4OTQ2NTA4MzUsLTE1MD
U4NDcwNTQsLTE2MjUzMDEwNDZdfQ==
-->