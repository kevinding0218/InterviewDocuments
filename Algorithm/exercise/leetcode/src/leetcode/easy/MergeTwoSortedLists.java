package leetcode.easy;

import leetcode.utils.ListNode;

/**
 * Created by Ran on Mar 9, 2020.
 */
public class MergeTwoSortedLists {
  /**
   * LC 21. Merge two sorted linked lists and return it as a new list. The new
   * list should be made by splicing together the nodes of the first two lists.
   * 
   * Example:
   * 
   * Input: 1->2->4, 1->3->4 Output: 1->1->2->3->4->4 time : O(m + n) space : O(1)
   * 
   *
   */
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    // step 1 (important!): for List question,
    // declare a dummy/head of init value
    // declare a cur which is the copy of the init head node
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;
    // step 2: loop through l1 and l2 at same time from beginning
    while (null != l1 && null != l2) {
      if (l1.val < l2.val) {
        // step 3 (important!): assign l1 to cur.next if l1 is smaller, then move l1
        // forward
        cur.next = new ListNode(l1.val);
        l1 = l1.next;
      } else {
        // step 3 (important!): assign l2 to cur.next if l2 is smaller, then move l2
        // forward
        cur.next = new ListNode(l2.val);
        l2 = l2.next;
      }
      // move cur forward as long as its next value being assigned from either l1 or
      // l2
      cur = cur.next;
    }
    // step 4 (important!): the shorter list in either l1 or l2 could be exhausted
    // whichever left in either l1 or l2 would have all bigger value
    if (null != l1) {
      cur.next = l1;
    } else {
      cur.next = l2;
    }
    // step 5 (imporant!)L for List question, always return dummy/head.next as new
    // header ListNode
    return dummy.next;
  }
}
