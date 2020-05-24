package leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ran on May 18, 2020.
 */
public class SpiralOrderMatrix {
  /**
   * LC. 54 Spiral Matrix (Amazon, Microsoft) **
   * 
   * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

    Example 1:

    Input:
    [
    [ 1, 2, 3 ],
    [ 4, 5, 6 ],
    [ 7, 8, 9 ]
    ]
    Output: [1,2,3,6,9,8,7,4,5]
    Example 2:

    Input:
    [
      [1, 2, 3, 4],
      [5, 6, 7, 8],
      [9,10,11,12]
    ]
    Output: [1,2,3,4,8,12,11,10,9,5,6,7]
   */

  /**
   * time : O(n * m)     n * m : total number in the matrix
     space : O(n * m)

   * @param matrix
   * @return
   */
  public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> res = new ArrayList<>();
    // Error boundry check
    if (matrix == null || matrix.length == 0 || 
      matrix[0] == null || matrix[0].length == 0) {
        return res;
    }
    // define row and column count
    int rowBegin = 0;
    int rowEnd = matrix.length - 1;
    
    int colBegin = 0;
    int colEnd = matrix[0].length - 1;

    while (rowBegin <= rowEnd && colBegin <= colEnd) {
      // print first row from left to right horizontally
      /**
       * print 1 2 3 4 5
       *|1  2   3  4  5|
       * 6  7   8  9 10
       * 11 12 13 14 15
       * 16 17 28 19 20
       */
      for (int i = colBegin; i <= colEnd; i++) {
          res.add(matrix[rowBegin][i]);
      }
      // when first row finished, go to 2nd row
      // reset starting point to be 2nd row of last column
      rowBegin++;

      // print last column from top to bottom vertically
      /**
       * print -> 10 15 20
       * 1  2   3  4  5 
       * 6  7   8  9 10   ^
       * 11 12 13 14 15
       * 16 17 28 19 20
       */
      for (int i = rowBegin; i <= rowEnd; i++) {
          res.add(matrix[i][colEnd]);
      }
      // when last column top-bottom finished, 
      // reset starting point to be last second column
      colEnd--;

      if (rowBegin <= rowEnd) {
          /**
           * print -> 19 28 17 17
           * 1  2   3  4  5 
           * 6  7   8  9 10   ^
           * 11 12 13 14 15
           * 16 17 28 19 20
           */
          for (int i = colEnd; i >= colBegin; i--) {
              res.add(matrix[rowEnd][i]);
          }
      }
      rowEnd--;

      if (colBegin <= colEnd) {
          /**
           * print -> 11 6
           * 1  2   3  4  5 
           * 6  7   8  9 10 
           * 11 12 13 14 15
           * 16 17 28 19 20
           */
          for (int i = rowEnd; i >= rowBegin; i--) {
              res.add(matrix[i][colBegin]);
          }
      }
      colBegin++;
    }

    return res;
  }
}