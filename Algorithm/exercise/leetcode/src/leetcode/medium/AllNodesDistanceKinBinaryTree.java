package leetcode.medium;

import java.util.LinkedList;
import java.util.List;

import leetcode.utils.TreeNode;

/**
 * Created by Ran on Jun 1, 2020.
 */
public class AllNodesDistanceKinBinaryTree {
  /**
   * LC. 863 All Nodes Distance K in Binary Tree
   * 
   * We are given a binary tree (with root node root), a target node, and an integer value K.
   * Return a list of the values of all nodes that have a distance K from the target node.  
   * The answer can be returned in any order.
          3
        5   1
      6 2   0  8
        7  4

    Example 1:
    Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
    Output: [7,4,1]
    Explanation: 
    The nodes that are a distance 2 from the target node (with value 5)
    have values 7, 4, and 1.

    Note that the inputs "root" and "target" are actually TreeNodes.
    The descriptions of the inputs above are just serializations of these objects.
   */

  /**
   * Intuition
    From root, say the target node is at depth 3 in the left branch. 
    It means that any nodes that are distance K - 3 in the right branch should be added to the answer.

    Algorithm
    Traverse every node with a depth first search dfs. We'll add all nodes x to the answer such that node is the node 
    on the path from x to target that is closest to the root.
    To help us, dfs(node) will return the distance from node to the target. Then, there are 4 cases:

    If node == target, then we should add nodes that are distance K in the subtree rooted at target.
    If target is in the left branch of node, say at distance L+1, then we should look for nodes that are distance K - L - 1 in the right branch.
    If target is in the right branch of node, the algorithm proceeds similarly.
    If target isn't in either branch of node, then we stop.

    In the above algorithm, we make use of the auxillary function subtree_add(node, dist) which adds the nodes in the subtree rooted 
    at node that are distance K - dist from the given node.
   * 
   */
  List<Integer> ans;
  TreeNode target;
  int K;
  public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
      ans = new LinkedList();
      this.target = target;
      this.K = K;
      dfs(root);
      return ans;
  }

  // Return vertex distance from node to target if exists, else -1
  // Vertex distance: the number of vertices on the path from node to target
  public int dfs(TreeNode node) {
      if (node == null)
          return -1;
      else if (node == target) {
          subtree_add(node, 0);
          return 1;
      } else {
          int L = dfs(node.left), R = dfs(node.right);
          if (L != -1) {
              if (L == K) ans.add(node.val);
              subtree_add(node.right, L + 1);
              return L + 1;
          } else if (R != -1) {
              if (R == K) ans.add(node.val);
              subtree_add(node.left, R + 1);
              return R + 1;
          } else {
              return -1;
          }
      }
  }

  // Add all nodes 'K - dist' from the node to answer.
  public void subtree_add(TreeNode node, int dist) {
      if (node == null) return;
      if (dist == K)
          ans.add(node.val);
      else {
          subtree_add(node.left, dist + 1);
          subtree_add(node.right, dist + 1);
      }
  }
}