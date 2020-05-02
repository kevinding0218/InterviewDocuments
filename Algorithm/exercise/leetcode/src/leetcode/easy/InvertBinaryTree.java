package leetcode.easy;

import java.util.LinkedList;
import java.util.Queue;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on Apr 30, 2020.
 */
public class InvertBinaryTree {
   /**
     * LC. 226 Invert Binary Tree (Twitter)
     * Invert a binary tree.

          4
        /   \
       2     7
      / \   / \
     1   3 6   9

     to
         4
        /   \
       7     2
      / \   / \
     9   6 3   1

     time : O(n)
     space : O(n);

     This problem was inspired by this original tweet by Max Howell:
     "Google: 90% of our engineers use the software you wrote (Homebrew), 
     but you can’t invert a binary tree on a whiteboard so f*** off."
     * @param root
     * @return
     */

    /**
     * The inverse of an empty tree is the empty tree. 
     * The inverse of a tree with root r, and subtrees {right} and {left}, 
     * is a tree with root r, whose right subtree is the inverse of {left}, 
     * and whose left subtree is the inverse of {right}.
     * 
     * Since each node in the tree is visited only once, the time complexity is O(n), 
     * where nn is the number of nodes in the tree. We cannot do better than that, 
     * since at the very least we have to visit each node to invert it.
     * 
     * Because of recursion, O(h) function calls will be placed on the stack in the worst case, 
     * where h is the height of the tree. Because h in O(n), the space complexity is O(n).
     * @param root
     * @return
     */
    public TreeNode invertTreeRecursive(TreeNode root) {
      if (root == null) return root;
      TreeNode left = invertTreeRecursive(root.left);
      TreeNode right = invertTreeRecursive(root.right);
      root.left = right;
      root.right = left;
      return root;
    }


    /**
     * The idea is that we need to swap the left and right child of all nodes in the tree. 
     * So we create a queue to store nodes whose left and right child have not been swapped yet. 
     * 
     * Initially, only the root is in the queue. 
     * As long as the queue is not empty, remove the next node from the queue, 
     * swap its children, and add the children to the queue. 
     * 
     * Null nodes are not added to the queue. Eventually, 
     * the queue will be empty and all the children swapped, 
     * and we return the original root.
     * 
     * Since each node in the tree is visited / added to the queue only once, 
     * the time complexity is O(n)O(n), where nn is the number of nodes in the tree.
     * 
     * Space complexity is O(n), since in the worst case, 
     * the queue will contain all nodes in one level of the binary tree. 
     * 
     * For a full binary tree, the leaf level has O(n) leaves.
     * @param root
     * @return
     */
    public TreeNode invertTreeIterative(TreeNode root) {
      if (root == null) return root;
      Queue<TreeNode> queue = new LinkedList<>();
      queue.offer(root);
      while (!queue.isEmpty()) {
          TreeNode cur = queue.poll();
          TreeNode temp = cur.left;
          cur.left = cur.right;
          cur.right = temp;
          if (cur.left != null) queue.offer(cur.left);
          if (cur.right != null) queue.offer(cur.right);
      }
      return root;
    }
}