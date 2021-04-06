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
- Ask Kth Largest then use minHeap, so that top of MinHeap stores the min element and all its children nodes stores element greater than itself
- Ask Kth Smallest then use maxHeap, so that top of MaxHeap stores the max element and all its children nodes stores element smaller than itself
```
// minHeap
PriorityQueue<P> minHeap = new PriorityQueue<>();
// maxHeap
PriorityQueue<P> minHeap = new PriorityQueue<>( Comparator.reverseOrder());
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
PriorityQueue<P> minHeap = new PriorityQueue<>(comparator);
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
### HashSet/HashMap
#### When asked about find duplicate element or given a known target and known element A, find if there is another elment B that can formulized with A to be target
#### When asked about frequency of character/string appearances, usually combined usage with Priority Queue to sort by appearance count
### Sliding Window
#### Find something/scenario in continuing sub string 

### Remove Duplicate that only can appeark K times
```
public static int removeDuplicatesMoreThanKTimes(int[] nums, int k) {  
  if (nums == null || nums.length == 0) return 0;  
    if (nums.length < k + 1) return nums.length;  
    int count = k;  
    for (int read = k; read < nums.length; read++) {  
	  if (nums[read] > nums[count - k]) {  
		  nums[count] = nums[read];  
          count ++;  
      }  
    }  
    return count;  
}
```
### Matrix
#### 基础
- 设一维数组下标为index，二维数组长度为m * n，则：
- 一维数组转换为二维数组
```
row = index / n 
col = index % n
```
- 二维数组转换为一维数组
```
index = col + row * n
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTM2MDYyOTQ5MywtOTgwNDc3NjQ5LDE3NT
YwMTY4NDUsMjAzNDU0NzkzNCwtNjAxNzc0NTg3LDg5NDY1MDgz
NSwtMTUwNTg0NzA1NCwtMTYyNTMwMTA0Nl19
-->