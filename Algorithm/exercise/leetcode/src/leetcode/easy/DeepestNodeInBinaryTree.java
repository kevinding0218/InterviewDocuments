package leetcode.easy;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on May 28, 2020.
 */
public class DeepestNodeInBinaryTree {
  /**
   * LC.104 - similar Deepest Node In Binary Tree (Google)
   * You are given the root of a binary tree. Return the deepest node (the furthest node from the root).

    Example:

        a
      / \
      b   c
    /
    d

    The deepest node in this tree is d at depth 3.
   */

  int deepestlevel;
  TreeNode deepestNode;

  public TreeNode deepestNodeInBinaryTree(TreeNode root) {
    findDeepestNodeAndLevel(root, 0);
    return deepestNode;
  }
  
  private void findDeepestNodeAndLevel(TreeNode root, int level) {
		if (root != null) {
			findDeepestNodeAndLevel(root.left, ++level);
			if (level > deepestlevel) {
				deepestNode = root;
				deepestlevel = level;
			}
			findDeepestNodeAndLevel(root.right, level);
		}
	}
}