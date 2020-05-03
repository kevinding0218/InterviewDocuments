package leetcode.easy;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on Apr 29, 2020.
 */
public class FindCeilingFloor {
  /**
   * LC ??? Find Ceiling Floor in BST (Apple)
   * 
   * Given an integer k and a binary search tree, find the floor (less than or equal to) of k, 
   * and the ceiling (larger than or equal to) of k. 
   * 
   * Ceil value node:
   * node with smallest data such that node.value >= k
   * 
   * Floor value node:
   * node with largest data such that node.value <= k
   * 
   * If either does not exist, then print them as None.
   * 
   * e.g:
   *      2              A
   *    1   6          B   C
   *       3 10           D E
   * 
   * k = 5
   * ceil from bst: 6
   * floor from bst: 3
   * 
   * BST: left node is always less than parent node and right node is always greater than parent node
   *    node
   *  sm    lg
   * 
   */

   public static void findCeilingFloor(TreeNode root, int k)
   {
     int ceiling = findCeiling(root, k);
     int floor = findFloor(root, k);
     System.out.printf("ceiling: %d", ceiling);
     System.out.printf("floor: %d", floor);
   }

   /**
    * Solution:
    * Ceil: node with smallest data such that node.value >= k
    * - Start from root, 
    * if root.val == k, then root is the ceil value
    * if root.val < k, then proceed to traverse right sub tree
    * if root.val > k, 1. either root.val is the ceil OR
                        2. a smaller node with node.val >= k in the left subtree
                          then that node is the ceil
    * if root == null, should return Integer.MIN_VALUE (or -1) because we would like to return to previous node
    *
    * e.g:
    *      2              A
    *    1   6          B   C
    *       3 10           D E
    * 
    * k = 5
    * - 1) findCeiling(A, 5)
           2 < 5 return findCeiling(C, 5)
      - 2) findCeiling(C, 5)
           6 > 5 ceil = findCeiling(D, 5), return findCeiling(D, 5) >= 5 ? ceil : C
      - 3) findCeiling(D, 5)
           3 < 5 return findCeiling(null, 5)
      - 4) findCeiling(null, 5)
           return MIN as result of findCeiling(null, 5)
      - reverse back 3) so that in findCeiling(D, 5), and we just return findFloor(null, 5) -> MIN
      - reverse back 2) so that in findCeiling(C, 5), we know current ceil = result of findFloor(D, 5) = MIN, so we return (MIN >= 5 ? MIN : C) C = 6
      - reverse back 1) so that in findCeiling(A, 5), and we just return findFloor(C, 5) -> 6
      - return 6
      @param root
    * @param k
    * @return
    */
   private static int findCeiling(TreeNode root, int k) {
      // Base case
      if (root == null)
        return Integer.MIN_VALUE;
      
      // we found equal value
      if (root.getVal() == k)
        return root.getVal();
      
      // If root's val is less than k, ceil must be in right subtree
      if (root.getVal() < k) {
        return findCeiling(root.getRight(), k);
      }

      // Else, either left subtree or root has the ceil value
      int ceil = findCeiling(root.getLeft(), k);
      return ceil >= k ? ceil : root.getVal();
   }

   /**
    * Solution
    * Floor: node with largest data such that node.value <= k

    * Solution: (mirror as oppsed to ceil)
    - Start from root, 
    * if root.val == k, then root is the floor value
    * if root.val > k, then proceed to traverse left sub tree
    * if root.val < k, 1. either root.val is the floor OR
                       2. a greater node with node.val <= k in the right subtree
                          then that node is the ceil
    * if root == null, then return Max Value because we want to retrive back to previous node/step
    *
    * e.g:
    *      2              A
    *    1   6          B   C
    *       3 10           D E
    * 
    * k = 5
    * - 1) findFloor(A, 5)
           2 < 5 floor = findFloor(C, 5), return findFloor(C, 5) <= k ? floor : C
      - 2) findFloor(C, 5)
           6 > 5 return findFloor(D, 5)
      - 3) findFloor(D, 5)
           3 < 5 floor = findFloor(null, 5), return findFloor(null, 5) <= k ? floor : D
      - 4) findFloor(null, 5)
           return MAX as result of findFloor(null, 5)
      - reverse back 3) so that in findFloor(D, 5), we know current floor = result of findFloor(null, 5) = MAX, so we return (MAX <= 5 ? MAX : D) D = 3
      - reverse back 2) so that in findFloor(C, 5), and we just return findFloor(D, 5) -> 3
      - reverse back 1) so that in findFloor(A, 5), we know current floor = result of findFloor(C, 5) = 3, so we return (3 <= 5 ? 3 : A)
      - return 3
    *
    * @param root
    * @param k
    * @return
    */
   private static int findFloor(TreeNode root, int k) {
      // Base case Important!, should return MAX here so that we could go back to previous node and compare
      if (root == null) {
        return Integer.MAX_VALUE;
      }

      // We found equal value
      if (root.getVal() == k) {
        return root.getVal();
      }

      // If root's value is greater than k, floor must be in left subtree
      if (root.getVal() > k) {
        return findFloor(root.getLeft(), k);
      }

      // Else, either right subtree or root has the floor value
      int floor = findFloor(root.getRight(), k);
      return floor <= k ? floor : root.getVal();
   }
}