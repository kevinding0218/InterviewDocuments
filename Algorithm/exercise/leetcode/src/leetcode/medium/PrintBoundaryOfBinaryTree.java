package leetcode.medium;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ran on May 23, 2020.
 */
public class PrintBoundaryOfBinaryTree {
  /**
   * LC. 545  Print Boundary Binary Tree Clockwise (Uber, Microsoft)
   * 
   * A perfect binary tree is a binary tree that satisfies two conditions:
    All non-leaf nodes have exactly 2 children
    All leaf nodes are at the same depth in the tree.

                1
            7         4
        14    8    3      6
      23  28 5 2  9 21  18 17 
   *
   * Print as 1 7 14 23 28 5 2 9 21 18 17 6 4
   * 
   * Input Format
    The input will be provided via stdin as a 
    list of integer values, separated by spaces. 
    The first integer will be the depth of the tree, 
    and the following numbers will be the integer values to be 
    stored in each node, specified in pre-traversal depth-first order.

    For example, the binary tree above would be represented by the following input:
    4 1 7 14 23 28 8 5 2 4 3 9 21 6 18 17  
   */


  /**
   * Time complexity: O(n).
   * Space complexity: O(n)
   * 
   * @param tree
   * @param depth
   * @return
   */
  public List<Integer> boundaryOfPerfectBinaryTree(Integer[] tree, int depth) {
    /**
     * [4 1 7 14 23 28 8 5 2 4 3 9 21 6 18 17], depth 4
     *            1
            7         4
        14    8    3      6
      23  28 5 2  9 21  18 17 
     * 
     */
    List<Integer> boundary = new ArrayList<>();
    // Error boundary
    if (tree == null || tree.length == 0) return boundary;
    
    /**
     *
     * levels would be list of list nodes
     * 
     * [1]
     * [7,4]
     * [14,8,3,6]
     * [23,28,5,2,9,21,18,17]
     * 
     * Idea:
     * store all nodes as level order print(LC. 102, level order print)
     * then we can 
     * print left boudary as first node from array that top to bottom 
     * print leave nodes as last array
     * print right boundary as last node from array that bottom to top 
     * 
     */
    List<List<Integer>> levels = new ArrayList<>(depth + 1);
    levels.add(new ArrayList<>());
    
    visit(new ArrayDeque<>(Arrays.asList(tree)), 1, depth, levels);
    
    collectLeftBoundary(levels, depth, boundary);
    collectLeaves(levels, depth, boundary);
    collectRightBoundary(levels, depth, boundary);
    
    return boundary;
  }

  private void visit(Queue<Integer> nodes, int depth, int maxDepth, List<List<Integer>> levels) {
    if (depth > maxDepth) return;
    
    if (depth == levels.size()) {
        levels.add(new ArrayList<>());
    }
    
    levels.get(depth).add(nodes.poll());
    
    visit(nodes, depth + 1, maxDepth, levels);
    visit(nodes, depth + 1, maxDepth, levels);
  }

  private void collectLeftBoundary(List<List<Integer>> levels, int maxDepth, List<Integer> boundary) {
    for (int depth = 1; depth <= maxDepth; depth++) {
        boundary.add(levels.get(depth).get(0));
    }
  }

  private void collectLeaves(List<List<Integer>> levels, int maxDepth, List<Integer> boundary) {
      List<Integer> lastLevel = levels.get(maxDepth);
      for (int i = 1; i < lastLevel.size() - 1; i++) {
          boundary.add(lastLevel.get(i));
      }
  }

  private void collectRightBoundary(List<List<Integer>> levels, int maxDepth, List<Integer> boundary) {
      for (int depth = maxDepth; depth > 1; depth--) {
          List<Integer> level = levels.get(depth);
          boundary.add(level.get(level.size() - 1));
      }
  }
}