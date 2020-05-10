package leetcode.medium;

import java.util.HashSet;
import java.util.Set;

import leetcode.utils.ListNode;

/**
 * Created by Ran on May 6, 2020.
 */
public class DetectCycleNode {
  /**
   * LC. 142 Linked List Cycle II
   * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

    To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

    Note: Do not modify the linked list.

    Example 1:

    Input: head = [3,2,0,-4], pos = 1
    Output: tail connects to node index 1
    Explanation: There is a cycle in the linked list, where tail connects to the second node.


    Example 2:

    Input: head = [1,2], pos = 0
    Output: tail connects to node index 0
    Explanation: There is a cycle in the linked list, where tail connects to the first node.


    Example 3:

    Input: head = [1], pos = -1
    Output: no cycle
    Explanation: There is no cycle in the linked list.
   */
  // Time: O(n)   Space: O(n)
  public ListNode DetectCycleI(ListNode head) {
      Set<ListNode> set = new HashSet<ListNode>();
      for (ListNode iterator = head; iterator != null; iterator = iterator.next)
      {
          if (set.contains(iterator))
              return iterator;
          else
              set.add(iterator);
      }
      return null;
  }

  // Time: O(n) Space: O(1)
  public ListNode DetectCycleII(ListNode head) {
      ListNode slower = head;
      ListNode faster = head;
      
      while (faster != null && faster.next != null)
      {
          faster = faster.next.next;
          slower = slower.next;
          
          if (faster == null)
              return null;
          
          if (slower.val == faster.val) {
              for (ListNode iterator = head; iterator != slower; iterator = iterator.next, slower = slower.next);
              return slower;
          } 
      }
      
      return null;
  }
}