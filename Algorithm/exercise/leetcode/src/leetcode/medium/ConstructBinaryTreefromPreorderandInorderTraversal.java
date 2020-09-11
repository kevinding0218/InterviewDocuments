package leetcode.medium;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on June 4, 2020.
 */
public class ConstructBinaryTreefromPreorderandInorderTraversal {
  /**
   * LC. 105 Construct Binary Tree from Preorder and Inorder Traversal (Microsoft)
   * 
   * Given preorder and inorder traversal of a tree, construct the binary tree.

    Note:
    You may assume that duplicates do not exist in the tree.

    For example, given

    preorder = [3,9,20,15,7]
    inorder = [9,3,15,20,7]
    Return the following binary tree:

       3
      / \
     9   20
        /  \
       15   7
   */

  public TreeNode buildTree(int[] preorder, int[] inorder) {
    return helper(0, 0, inorder.length - 1, preorder, inorder);
  }

  public TreeNode helper(int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
    // error boundary
    if (preStart > preorder.length - 1 || inStart > inEnd) {
        return null;
    }
    // Tree root node must be first element in pre-order array
    // preorder = [3,9,20,15,7] root is 3
    TreeNode root = new TreeNode(preorder[preStart]);
    int inIndex = 0;
    /**
     * preorder = [3,9,20,15,7]
     * inorder  = [9,3,15,20,7]
     */
    for (int i = inStart; i <= inEnd; i++) {
        if (inorder[i] == root.val) {
            inIndex = i;
        }
    }
    /**
     * node = 3 <- preorder[0]
     * inIndex = 1 -> '3' was found at index 1 in inorder
     * 3.left = helper(1, 0, 0, pre, in) -> 3.left = 9
     * 3.right = helper(0 + 1 - 0 + 1, 2, 0, pre, in) -> 3.left = 9 (put in stack)
     * node = 9 <- preorder[1]
     * root.left = helper(2, 0, -1, pre, in) -> 9.left = null because inStart > inEnd
     * root.right = helper(1 + 1 - 0 + 1, )
     * 
     */
    root.left = helper(preStart + 1, inStart, inIndex - 1, preorder, inorder);
    root.right = helper(preStart + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder);
    return root;
  }
}