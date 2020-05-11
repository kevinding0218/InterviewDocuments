package leetcode.easy;

import leetcode.utils.ListNode;

/**
 * Created by Ran on May 10, 2020.
 */
public class IntersectionofTwoLinkedLists {
    /**
     * LC. 160 Intersection of Two Linked Lists (Apple)
     * Write a program to find the node at which the intersection 
     * of two singly linked lists begins.
     * 
     * For example, the following two linked lists:

        A:          a1 → a2
                            ↘
                            c1 → c2 → c3
                            ↗
        B:     b1 → b2 → b3
        begin to intersect at node c1.

        If the two linked lists have no intersection at all, return null.
        The linked lists must retain their original structure after the function returns.
        You may assume there are no cycles anywhere in the entire linked structure.
        Your code should preferably run in O(n) time and use only O(1) memory.
     */

    /**
     *
    Two pinters:
     - Maintain two pointers pA and pB initialized at the head of A and B, respectively. 
       Then let them both traverse through the lists, one node at a time.
     - When pA reaches the end of a list, then redirect it to the head of B (yes, B, that's right.); 
       similarly when pB reaches the end of a list, redirect it the head of A.
     - If at any point pA meets pB, then pA/pB is the intersection node.
     - To see why the above trick would work, consider the following two lists: 
        A = {1,3,5,7,9,11} and B = {2,4,9,11}, which are intersected at node '9'. 
        Since B.length (=4) < A.length (=6), pB would reach the end of the merged list first, 
        because pB traverses exactly 2 nodes less than pA does. 
        By redirecting pB to head A, and pA to head B, 
        we now ask pB to travel exactly 2 more nodes than pA would.
         So in the second iteration, they are guaranteed to reach the intersection node at the same time.
     - If two lists have intersection, then their last nodes must be the same one. 
       So when pA/pB reaches the end of a list, record the last element of A/B respectively. 
     - If the two last elements are not the same one, then the two lists have no intersections.

    A:            a1 → a2
                         ↘
                         c1 → c2 → c3
                         ↗
     B:     b1 → b2 → b3
     begin to intersect at node c1.
     A : a1 → a2 → c1 → c2 → c3 → b1 → b2 → b3 → c1 → c2 → c3
     B : b1 → b2 → b3 → c1 → c2 → c3 → a1 → a2 → c1 → c2 → c3

     time : O(m + n);
     space : O(1);

     * @param headA
     * @param headB
     * @return
     */
     public ListNode getIntersectionNodeI(ListNode headA, ListNode headB) {
        // error boundry check
        if (headA == null || headB == null) return null;
        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }

    /**
     * For example, the following two linked lists:

     - Store the size of ListA and ListB as len1 and len2. 
     - Then I reset the pointers to headA and headB and find the difference between len1 and len2, 
       and then let the pointer of the longer list proceed by the difference between len1 and len2. 
     - Finally, traverse through the lists again, the intersection node can be easily found.
     A:          a1 → a2
                         ↘
                         c1 → c2 → c3
                         ↗
     B:     b1 → b2 → b3
     begin to intersect at node c1.

     time : O(m+n);
     space : O(1);

     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNodeII(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        int lenA = len(headA);
        int lenB = len(headB);
        if (lenA > lenB) {
            // If A is longer than B, move node A len(A) - len(B) steps forward
            while (lenA != lenB) {
                headA = headA.next;
                lenA--;
            }
        } else {
            // If B is longer than A, move node B len(B) - len(A) steps forward
            // in above example, headB now is b2
            while (lenA != lenB) {
                headB = headB.next;
                lenB--;
            }
        }
        // move headA and headB forward at same time
        // e.g A: a1 -> a2 -> c1; B: b2 -> b3 -> c1, headA == headB now
        while (headA != headB && headA != null && headB != null) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }

    private int len(ListNode head) {
        int len = 1;
        while (head != null) {
            head = head.next;
            len++;
        }
        return len;
    }
}