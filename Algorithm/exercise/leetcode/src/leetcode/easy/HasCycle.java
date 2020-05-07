package leetcode.easy;

import leetcode.utils.ListNode;

/**
 * Created by Ran on May 6, 2020.
 */
public class HasCycle {
  /**
   * Given a linked list, determine if it has a cycle in it.

    To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

    

    Example 1:

    Input: head = [3,2,0,-4], pos = 1
    Output: true
    Explanation: There is a cycle in the linked list, where tail connects to the second node.


    Example 2:

    Input: head = [1,2], pos = 0
    Output: true
    Explanation: There is a cycle in the linked list, where tail connects to the first node.


    Example 3:

    Input: head = [1], pos = -1
    Output: false
    Explanation: There is no cycle in the linked list.

   */

  public boolean hasCycle(ListNode head) {
    /**
     * LC. 142 Linked List Cycle I
     * idea: 2 pointers: slower and fast pointer
     * 
     * a slow pointer and a fast pointer. The slow pointer moves one step at a time while the fast pointer moves two steps at a time.
     * If there is no cycle in the list, the fast pointer will eventually reach the end and we can return false in this case.
     * Now consider a cyclic list and imagine the slow and fast pointers are two runners racing around a circle track. 
     * The fast runner will eventually meet the slow runner. Why? Consider this case (we name it case A) 
     * - The fast runner is just one step behind the slow runner. 
     * In the next iteration, they both increment one and two steps respectively and meet each other.
     */
    ListNode slower = head;
    ListNode faster = head;
    
    while (faster != null)
    {
        if (faster.next == null)
            return false;
        
        faster = faster.next.next;
        slower = slower.next;

        if (faster == null)
            return false;
        
        if (slower.val == faster.val)
            return true;
    }
    
    return false;
  }
}