package leetcode.medium;

import java.util.Stack;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on May 31, 2020.
 */
public class ValidateBinarySearchTree {
  /**
   * LC. 98 Validate Binary Search Tree (Facebook)
   * 
   * Given a binary tree, determine if it is a valid binary search tree (BST).

    Assume a BST is defined as follows:

    The left subtree of a node contains only nodes with keys less than the node's key.
    The right subtree of a node contains only nodes with keys greater than the node's key.
    Both the left and right subtrees must also be binary search trees.
    

    Example 1:

        2
      / \
      1   3

    Input: [2,1,3]
    Output: true
    Example 2:

        5
      / \
      1   4
        / \
        3   6

    Input: [5,1,4,null,null,3,6]
    Output: false
    Explanation: The root node's value is 5 but its right child's value is 4.
   * 
   */

  // Using Recursion
  public static boolean isValidBST1(TreeNode root) {
    // error boundary check
    if (root == null) return true;
    return helper(root, null, null);
  }

  /**
   * Let's traverse the tree and check at each step if node.right.val > node.val and 
   * node.left.val < node.val. This approach would even work for some trees
   * 
   * The problem is this approach will not work for all cases. 
   * Not only the right child should be larger than the node but all the elements in the right subtree. 
   * Here is an example :
   *     5
   *  1     6
   *      4   7
   * At each node the condtions: node.right.val > node.val, node.left.val < node.val are valid
   * But it's not BST because not all element's in the right subtree of node 5 is larger than 5: 4 < 5
   * 
   * That means one should keep both upper and lower limits for each node while traversing the tree, 
   * and compare the node value not with children values but with these limits.
   * 
   * Time: O(n) - since we visit each node exactly once
   * Space O(n) - since we keep up to the entire tree
   * @param node
   * @param lower
   * @param upper
   * @return
   */
  private static boolean helper(Integer lower, TreeNode node, Integer upper) {
    if (node == null) return true;

    // node.val should always be greater than lower if lower existed, otherwise return false
    if (lower != null && node.val <= lower) return false;
    // node.val should always be smaller than upper if upper existed, otherwise return false
    if (upper != null && node.val >= upper) return false;

    // it should be valid if val(node) < node.right < upper
    // right child node should be greater than its parent
    if (! helper(node.val, node.right, upper)) return false;
    // it should be valid if lower < node.left < val(node)
    // left child node should be smaller than its parent
    if (! helper(lower, node.left, node.val)) return false;
    return true;
  }

  // Using InOrder
  /**
   * Let's use the order of nodes in the inorder traversal Left -> Node -> Right
   *      4
   *    2   5
   *  1  3
   * In order: 1 -> 2 -> 3 -> 4 -> 5
   * 
   * - Compute inorder traversal list inorder.
   * - Check if each element in inorder is smaller than the next one.
   * - Do we need to keep the whole inorder traversal list?
   * Actually, no. 
   * The last added inorder element is enough to ensure at each step that the tree is BST (or not). 
   * Hence one could merge both steps into one and reduce the used space.
   * 
   * Time: O(n) - in the worst case when the tree is BST or the "bad" element is a rightmost leaf.
   * Space O(n) - to keep stack
   * @param root
   * @return
   */
  public boolean isValidBST(TreeNode root) {
    Stack<TreeNode> stack = new Stack();
    double inorder = - Double.MAX_VALUE;

    while (!stack.isEmpty() || root != null) {
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
      root = stack.pop();
      // If next element in inorder traversal
      // is smaller than the previous one
      // that's not BST.
      if (root.val <= inorder) return false;
      inorder = root.val;
      root = root.right;
    }
    return true;
  }
}