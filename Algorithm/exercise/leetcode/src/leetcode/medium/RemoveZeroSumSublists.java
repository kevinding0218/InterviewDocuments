package leetcode.medium;

import java.util.HashMap;
import java.util.Map;

import leetcode.utils.ListNode;

/**
 * Created by Ran on May 12, 2020.
 */
public class RemoveZeroSumSublists {
  /**
   * LC. 1171 Remove Zero Sum Consecutive Nodes from Linked List (Uber)
    Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no such sequences.

    After doing so, return the head of the final linked list.  You may return any such answer.

    (Note that in the examples below, all sequences are serializations of ListNode objects.)

    Example 1:
    Input: head = [1,2,-3,3,1]
    Output: [3,1]
    Note: The answer [1,2,1] would also be accepted.
    
    Example 2:
    Input: head = [1,2,3,-3,4]
    Output: [1,2,4]
    
    Example 3:
    Input: head = [1,2,3,-3,-2]
    Output: [1]
    

    Constraints:

    The given linked list will contain between 1 and 1000 nodes.
    Each node in the linked list has -1000 <= node.val <= 1000.
   * 
   * @param head
   * @return
   */
  public ListNode removeZeroSumSublists(ListNode head) {
    /**
     * Similar to target sum = 0, use a hashtable to store the first ListNode* that has a given prefix sum. 
     * Whenever the same prefix sum occurs, skip all the elements between the first occurrence and current one,
     * like deleting all nodes between first_sum_x and curr_sum_x by connecting first_sum_x.next = curr_sum_x.next

      Time complexity: O(n)
      Space complexity: O(n)
     * 
     */
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev= dummy;
    ListNode curr = prev.next;

    Map<Integer, ListNode> map = new HashMap<>();
    map.put(0, prev);

    int sum = 0;
    while(curr != null)
    {
      /**
       * [1, 2, 3, -3, 4]       sum   map (before)                              map (after)                   
       *p c                     1     {[0,1->2]}                                {[0,1->2], [1,1->2]}
       *  p  c                  3     {[0,1->2], [1,1->2]}                      {[0,1->2], [1,1->2], [3,2]}
       *     p  c               6     {[0,1->2], [1,1->2], [3,2->3]}            {[0,1->2], [1,1->2], [3,2->3], [6,3->-3]}
       *        p  c            3     firstSumNode: 2                           {[0,1->2], [1,1->2], [3,2->4], [6,3->-3]} here we removed 3, -3 by connecting 2 and 4
       *           p   c        7     {[0,1->2], [1,1->2], [3,2->4], [6,3->-3]} {[0,1->2], [1,1->2], [3,2->4], [6,3->-3], [7, 4->null]}
       */
        sum += curr.val;
        if(map.containsKey(sum)) {
          ListNode firstSumNode = map.get(sum);
          firstSumNode.next = curr.next;
        } else {
          map.put(sum, curr);
        }
        prev = curr;
        curr = curr.next;
    }
    return dummy.next; 
  }
}