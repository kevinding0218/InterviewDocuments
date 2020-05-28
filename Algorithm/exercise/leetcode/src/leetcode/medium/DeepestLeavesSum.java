package leetcode.medium;

import java.util.LinkedList;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on May 28, 2020.
 */
public class DeepestLeavesSum {
  /**
   * LC. 1302 Deepest Leaves Sum
   * 
   * Given a binary tree, return the sum of values of its deepest leaves.
    Example 1:
            1
          2   3
        4  5    6
      7           8
          
    Input: root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
    Output: 15
   *
   */
  
  
  /**
   *  q are node in the current same level.
   * 
   *  Reset for calculating the sum of elements of the next level
   *  res -> calculate each level node sum
   *  last res will be sum of leave level
   * 
   *  Time O(N)
      Space O(N)
   * @param root
   * @return
   */
   public int deepestLeavesSum(TreeNode root) {
    int res = 0, i;
    LinkedList<TreeNode> q = new LinkedList<TreeNode>();
    q.add(root);
    while (!q.isEmpty()) {
        for (i = q.size() - 1, res = 0; i >= 0; --i) {
            TreeNode node = q.poll();
            res += node.val;
            if (node.right != null) q.add(node.right);
            if (node.left  != null) q.add(node.left);
        }
    }
    return res;
}
}