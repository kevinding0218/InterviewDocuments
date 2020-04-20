package leetcode.medium;

import leetcode.utils.ListNode;

/**
 * Created by Ran on Apr 19, 2020.
 */
public class AddTwoNumbers {
    /* LC 2. Add Two Numbers (Microsoft)
     *
        You are given two non-empty linked lists representing two non-negative integers. 
        The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

        You may assume the two numbers do not contain any leading zero, except the number 0 itself.

        Example:

        Input: (7 -> 4 -> 3) + (5 -> 6 -> 4)
        Output: 2 -> 1 -> 8
        Explanation:    347
                     +  465 
                     =  812
    */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // step 1 (important!): for List question,
        // declare a dummy/head of init value
        // declare a cur which is the copy of the init head node
        ListNode dummy = new ListNode(0);
        int sum = 0;
        ListNode cur = dummy;
        ListNode p1 = l1, p2 = l2;
        // step 2: loop through l1 and l2 at same time from beginning
        // until the longest list of l1 or l2
        while(p1 != null || p2 != null) {
            // add l1 node value to sum if there is
            if (p1 != null) {
                sum += p1.val;
                p1 = p1.next;
            }
            // add l2 node value to sum if there is
            if (p2 != null) {
                sum += p2.val;
                p2 = p2.next;
            }
            // step 3 (important!): assign current node value to be the calculation of current node sum
            // (remember for List question, finally the result of cur would always return from the 2nd node, head node is dummy which is 0)
            // if sum is greater than 10, take the sum%10 value to be sumed value of current node set
            // e.g: if sum is 7 + 5 = 12, then consider 12%10=2 as the value of current node set
            cur.next = new ListNode(sum % 10);
            // step 4 (important!): reassign sum as the carried value
            // since sum of current node set could be greater than 10,
            // meaning we need carry the value of sum/10 to the next calculation of node set sum
            // e.g: if sum of first node set is 7 + 5 = 12 then the value to be carried over to next sum of second node
            // would be 12/10=1, next time when we add up the second node set in the while loop, sum needs to be added from '1' + l1 or l2
            sum /= 10;
            // move cur forward
            cur = cur.next;
        }
        // step 5 (important!): if the carry value or sum/=10 of final node sets sum calculation is greater than 1
        // create a new node and assign 1
        // e.g: 7 + 5 = 12 -> 1(new node) + 2(existed sum of nodes)
        if (sum == 1) {
            cur.next = new ListNode(1);
        }
        return dummy.next;
    }
}