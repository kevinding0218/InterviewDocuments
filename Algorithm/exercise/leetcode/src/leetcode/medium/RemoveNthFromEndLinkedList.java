package leetcode.medium;

import leetcode.utils.ListNode;

/**
 * Created by Ran on May 13, 2020.
 */
public class RemoveNthFromEndLinkedList {
    /**
     * LC. 19 Remove Nth Node From End of List (AirBNB)
     * 
     * Given a linked list, remove the n-th node from the end of list and return its head.

        Example:

        Given linked list: 1->2->3->4->5, and n = 2.

        After removing the second node from the end, the linked list becomes 1->2->3->5.
        Note:

        Given n will always be valid.
     */

    /**
     *  time : O(n)
        space : O(1)
     * @param head
     * @param n
     * @return
     */
     public ListNode removeNthFromEnd(ListNode head, int n) {
         // define a dummy node pointing to head
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy;        
        /**
         * Solution: Two Pointers
         * 
         * 
         * 
         * slow would be from beginning
         * fast would be from n element from slower
         * 
         * E.g:
         * Given linked list: 1->2->3->4->5, and n = 2.
         * initially      s ->    
         *                          f
         *                          s        f    
         * 
         * initially s would be pointing to head, f would be at node 3         
         * then move slow and fast at same time until fast exceed the end of list
         * now, s would be at node 3, f == null, we relink s.next = s.next.next
         * which is equals to deleting the node between s and s.next.next which is node 4
         * so now 3 -> 5 and list become 1 -> 2 -> 3 -> 5
         *        
         */
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }
}