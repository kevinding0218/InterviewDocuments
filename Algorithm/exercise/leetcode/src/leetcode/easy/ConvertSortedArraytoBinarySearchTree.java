package leetcode.easy;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on May 25, 2020.
 */
public class ConvertSortedArraytoBinarySearchTree {
  /**
   * LC. 108 Convert Sorted Array to BST (LinkedIn)
   * 
   * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

    For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

    Example:

    Given the sorted array: [-10,-3,0,5,9],

    One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

          0
        / \
      -3   9
      /   /
    -10  5
   */
  
  /**
   * time : O(n);
   * space : O(n);
   * 
   * @param nums
   * @return
   */
   public TreeNode sortedArrayToBST(int[] nums) {
    // error boundary check
    if (nums == null || nums.length == 0) return null;
    
    return helper(nums, 0, nums.length - 1);
  }

  private TreeNode helper(int[] nums, int left, int right) {  
      // space : O(logn);
      // recursive end point
      if (left > right) return null;
      // always pick the middle as root/subroot node
      int mid = (right - left) / 2 + left;
      TreeNode node = new TreeNode(nums[mid]);
      // node's left would be the middle of left sub array
      node.left = helper(nums, left, mid - 1);
      // node's right would be the middle of right sub array
      node.right = helper(nums, mid + 1, right);
      return node;
  }
}