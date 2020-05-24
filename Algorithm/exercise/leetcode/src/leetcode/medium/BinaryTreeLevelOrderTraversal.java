package leetcode.medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on May 23, 2020.
 */
public class BinaryTreeLevelOrderTraversal {
  /**
   * LC. 102 Binary Tree Level Order Traversal (Microsoft)
   * Check 545 PrintBoundaryOfBinaryTree
   * 
   * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

    For example:
    Given binary tree [3,9,20,null,null,15,7],
       3
      / \
      9  20
        /  \
      15   7
    return its level order traversal as:
    [
      [3],
      [9,20],
      [15,7]
    ]
   * 
   */
  public static List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    helper(res, root, 0);
    return res;
  }

  /**
   * BFS iterator each level 
   * init a new list in result once found a new level
   * insert node from left to right
   * 
   */
  public static void helper(List<List<Integer>> res, TreeNode root, int level) {
    if (root == null) return;
    // init a new list for new level if there is node
    if (level >= res.size()) {
        res.add(new ArrayList<>());
    }
    res.get(level).add(root.val);
    helper(res, root.left, level + 1);
    helper(res, root.right, level + 1);
  }

  public static List<List<Integer>> levelOrder2(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      List<Integer> list = new ArrayList<>();
      for (int i = 0; i < size; i++) {
        /**
         *   3
            / \
            9  20
              /  \
             15   7
         * 
         * queue: 3
         * poll 3, insert 9 then 20 to queue
         * add 3 to list
         * list: 3
         * queue: 20 -> 9
         * 
         * poll 9, add 9 to list as a new entry of list
         * list: [3], [9]
         * 
         * poll 20, insert 15 then 7 to queue
         * add 20 to list in second list
         * list [3], [9, 20]
         * queue: 15 -> 7
         * 
         * poll 15, add 15 to list as a new entry of list
         * list: [3], [9, 20] [15]
         * 
         * poll 7, add 7 to in third list
         * list: [3], [9, 20] [15, 7]
         */
          TreeNode cur = queue.poll();
          if (cur.left != null) queue.offer(cur.left);
          if (cur.right != null) queue.offer(cur.right);
          list.add(cur.val);
      }
      res.add(list);
    }

    return res;
  }
}