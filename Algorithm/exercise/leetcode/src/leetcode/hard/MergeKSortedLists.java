package leetcode.hard;

import java.util.PriorityQueue;

import leetcode.utils.ListNode;

/**
 * Created by Ran on May 24, 2020.
 */
public class MergeKSortedLists {
  /**
   * LC. 23 Merge K Sorted Lists (Twitter)
   * 
   * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

    Example:
    Input:
    [
      1->4->5,
      1->3->4,
      2->6
    ]
    Output: 1->1->2->3->4->4->5->6
   */

   /**
    * Convert merge k lists problem to merge 2 lists (k-1) times. 
      Here is the merge 2 lists problem page.

      time: O(kn)
      space: O(1)
    * @param lists
    * @return
    */
    /**
     *
     * Merge with Divide and Conquer
     * list1, list2, list3, ..., listk
     * (list1, list2, list3, .., listk/2) and (listk/2+1, listk/2+2, ..., listk)
     * (list1, list2, ..listk/4), (listk/4+1, listk/4+2, ..., listk/2), ....
     * ...
     * till merge(list1, list2) + merge(list3, list4) + ... merge(listk-1, listk)
     * then up to top
     * 
     * time : O(nlogk) where k is the number of linked lists
     * space : O(n)
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) return null;
      return sort(lists, 0, lists.length - 1);
    }

    public ListNode sort(ListNode[] lists, int lo, int hi) {
      if (lo >= hi) return lists[lo];
      int mid = (hi - lo) / 2 + lo;
      ListNode l1 = sort(lists, lo, mid);
      ListNode l2 = sort(lists, mid + 1, hi);
      return merge(l1, l2);
    }

    public ListNode merge(ListNode l1, ListNode l2) {
      if (l1 == null) return l2;
      if (l2 == null) return l1;
      if (l1.val < l2.val) {
          l1.next = merge(l1.next, l2);
          return l1;
      }
      l2.next = merge(l1, l2.next);
      return l2;
    }


    /**
     * If Priority Queue is allowed, insert into PQ and output from PQ
     * time(n)
     * space(n)
     * @param lists
     * @return
     */
    public ListNode mergeKLists2(ListNode[] lists) {
      if (lists == null || lists.length == 0) return null;
      // Define a PQ of ListNode while the sorted order based on node's val
      PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val);
      // Define Dummy Node where the output would be dummy.next
      ListNode dummy = new ListNode(0);
      ListNode cur = dummy;

      // Insert List into PQ while node.value been sorted by predefined
      for (ListNode list : lists) {
          if (list != null) {
              queue.add(list);
          }
      }
      
      // Output from PQ into List
      while (!queue.isEmpty()) {
          cur.next = queue.poll();
          cur = cur.next;
          if (cur.next != null) {
              queue.add(cur.next);
          }
      }
      return dummy.next;
    }
}