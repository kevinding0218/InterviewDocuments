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
## Heap/Priority Queue
### Define a Heap
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
#### Top K
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
## HashSet/HashMap
- When asked about find duplicate element or given a known target and known element A, find if there is another elment B that can formulized with A to be target
- When asked about frequency of character/string appearances, usually combined usage with Priority Queue to sort by appearance count
## Binary Search
### Template 1 used for find a range
```
public int binarySearch(int[] nums, int target) {  
   if (nums == null || nums.length == 0) { return -1; }     
   int start = 0, end = nums.length - 1;  
    while (start + 1 < end) { 
       int mid = start + (end - start) / 2; 
       Element el = getElement(mid);
       if (el == target) { 
          end = mid; 
       } else if (el < target) { 
          start = mid;
       } else { 
	       end = mid;
	   } 
	}     
	if (getElement(start) == target) {  return start; } 
	if (getElement(end) == target) { return end; } 
	return -1;}
```
### Template 2 - used for less computing by getting the element
```
public int binarySearch(int[] nums, int target) {
    // 左右都闭合的区间 [l, r]
    int left = 0;
    int right = nums.length - 1;

    while(left <= right) {
        int mid = left + (right - left) / 2;
        Element el = getElement(mid);
        if(el == target) {
            return mid;
        }
        else if (el < target) {
			// 搜索区间变为 [mid+1, right]
            left = mid + 1;
        }
        else {
            // 搜索区间变为 [left, mid - 1]
            right = mid - 1;
        }
    }
    return notFound;
}
```
## Sliding Window
### Find something/scenario in continuing sub string 

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
## Matrix
### 基础
- 设一维数组下标为index，二维数组长度为rows * cols，则：
- 一维数组index转换为二维数组(x,y)
```
x = index / cols 
y = index % cols
```
- 二维数组(x,y)转换为一维数组index_
```
index = y + x * cols
```
## Tree
### BST
#### Inorder will be a sorted list
```
/**  
 * exit condition; left -> execution -> right 
 * */
private void dfs(TreeNode root, List<Integer> ansList) {  
  if (root == null) {  
	  return;  
  }  
  
  dfs(root.left, ansList);  
  ansList.add(root.val);  
  dfs(root.right, ansList);  
}
/**  
 * 先把最左边全都加进stack，然后开始pop，每pop一次检查：  
  * 1.右边为空就一直pop；  
  * 2.不为空就去右边，然后再一次将左边的一条下来全加进stack里。  
  * 重复该过程。  
  */  
public static List<Integer> iteration(TreeNode root) {  
  Stack<TreeNode> stack = new Stack<>();  
    List<Integer> list = new ArrayList<>();  
    while (root != null) {  
  stack.push(root);  
        root = root.left;  
    }  
  while (!stack.empty()) {  
  TreeNode curr = stack.pop();  
        list.add(curr.val);  
        if (curr.right != null) {  
  TreeNode node = curr.right;  
            while (node != null) {  
  stack.push(node);  
                node = node.left;  
            }  
 } }  return list;  
}
```
### Binary Tree
#### BFS like level order
```
public static List<List<Integer>> bfs(TreeNode root) {  
  List<List<Integer>> result = new ArrayList<>();  
    if (root == null) return result;  
    // 1. create a queue, enqueue starting node (lv1 node)  
    Queue<TreeNode> queue = new LinkedList<>();  
    queue.offer(root);  
    // 2. while queue is not empty, deal with nodes in queue, and expand new nodes  
    while (!queue.isEmpty()) {  
        List<Integer> level = new ArrayList<>();  
        // we are only iterating current level nodes at one while loop cycle  
        // for every node in current level visited, keep pushing its child nodes in queue or result  int queueSize = queue.size();  
        for (int i = 0; i < queueSize; i ++) {  
  TreeNode node = queue.poll();  
            level.add(node.val);  
            if (node.left != null) queue.offer(node.left);  
            if (node.right != null) queue.offer(node.right);  
        }  
        result.add(level);  
    }  
    return result;  
}
```
#### DFS Save Parent Node and Visited
- Save mapping of current node with its parent node
	```
	Map<Integer, TreeNode> parent = new HashMap<Integer, TreeNode>();
	public void dfs(TreeNode root) {
	    if (root.left != null) {
	        parent.put(root.left.val, root);
	        dfs(root.left);
	    }
	    if (root.right != null) {
	        parent.put(root.right.val, root);
	        dfs(root.right);
	    }
	}
	```
- Traverse with parent info
	```
	method() {
		dfs(root);
		Set<Integer> visited = new HashSet<Integer>();
		while(something) {
			visited.add(root.val);
			root = parent.get(root.val);
		}
	}
	```

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTE2MDcwNjk4MSwtMTE0NTEwMDcwNiwtMj
AwNzU3MzAyNSwyNzQwNDcyMjEsLTM2MDYyOTQ5MywtOTgwNDc3
NjQ5LDE3NTYwMTY4NDUsMjAzNDU0NzkzNCwtNjAxNzc0NTg3LD
g5NDY1MDgzNSwtMTUwNTg0NzA1NCwtMTYyNTMwMTA0Nl19
-->