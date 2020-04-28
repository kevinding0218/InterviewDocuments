package leetcode.easy;

import leetcode.utils.ListNode;

/**
 * Created by Ran on Apr 24, 2020.
 */
public class ReverseLinkedList {
    /**
     * LC 206. Reverse Linked List (Google)

     Example: 
     Input: 1->2->3->4->5->NULL
     Output: 5->4->3->2->1->NULL

     time : O(n);
     space : O(1);

     * @param head
     * @return
     */

     /**
      * While you are traversing the list, change the current node's next pointer to point to its previous element. 
      * 
      * Since a node does not have reference to its previous node, you must store its previous element beforehand. 
      * You also need another pointer to store the next node before changing the reference. 
      * 
      * Do not forget to return the new head reference at the end!
      */
    public ListNode reverseList(ListNode head) {
        // step 1: error boundry check
        if (head == null || head.next == null) return head;

        // declare a dummy/res of init value
        ListNode res = null;
        // traverse the list from head
        while(head != null) {
            // step 2 (important!) : store the current node's next node
            ListNode headNext = head.next;
            
            // step 3(important!): here is how reverse happens
            /**            
             * 1 -> 2 -> 3 -> null
             * head = 1 
             * [head.next = res] = null => [head] becomes (1 -> {null})
             * [res = head] then [res] becomes same as [head] as (1 -> null)
             * reset [head = head.next] => [head] becomes (2 -> 3 -> null)
             * 
             * head = 2 
             * [head.next = res] = (1 -> null) => [head] becomes (2 -> {1 -> null})
             * [res=head] then [res] becomes same as [head] as (2 -> 1 -> null)
             * reset [head=head.next] => [head] becomes (3 -> null)
             * 
             * head = 3
             * [head.next = res] = (2 -> 1 -> null) => [head] becomes (3 -> {2 -> 1 -> null})
             * [res=head] then [res] becomes same as [head] as (3 -> 2 -> 1 -> null)
             * reset [head=head.next] => [head] becomes (null)
             * 
             * exit
             */
            head.next = res;
            res = head;
            
            head = headNext;

        }
        return res;
    }
}