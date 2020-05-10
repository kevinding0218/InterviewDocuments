package leetcode.medium;

/**
 * Created by Ran on May 9, 2020.
 */
public class UniquePathGrid {
    /**
     * LC. 62  Unique Paths (Microsoft)
     * https://www.hackerearth.com/practice/notes/dynamic-programming-problems-involving-grids/
     * You 2 integers n and m representing an n by m grid, 
     * determine the number of ways you can get from 
     * the top-left to the bottom-right of the matrix y going only right or down.

        Example 1:
        Input: m = 3, n = 2
        Output: 3
        Explanation:
        From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
        1. Right -> Right -> Down
        2. Right -> Down -> Right
        3. Down -> Right -> Right

        Example 2:
        Input: m = 7, n = 3
        Output: 28

     */

    /**
     * To reach a cell (i,j), one must first reach either the cell (i-1,j) or the cell (i,j-1) and then move one step down 
     * or to the right respectively to reach cell (i,j). 
     * 
     * After convincing yourself that this problem indeed satisfies the optimal sub-structure and overlapping subproblems properties, 
     * we try to formulate a bottom-up dynamic programming solution.
     * 
     * We first need to identify the states on which the solution will depend. To find the number of ways to reach to a position, 
     * what are the variables on which my answer depends? 
     * Here, we need the row and column number to uniquely identify a position.
     * 
     * let NumWays(i,j) be the number of ways to reach position (i,j). 
     * As stated above, number of ways to reach cell (i,j) will be equal to the sum of number of ways of reaching (i-1,j) 
     * and number of ways of reaching (i,j-1). 
     * 
     * Thus, we have our recurrence relation as :
     * numWays(i,j) = numWays(i-1,j) + numWays(i,j-1)
     * 
     * The base case, as in the previous question, 
     * are the topmost row and leftmost column. 
     * Here, each cell in topmost row can be visited in only one way, i.e. from the left cell.
     */
    
     // time : O(n * m) space : (n * m)
    public static int uniquePathsI(int m, int n) {/**
        * m = 4, n = 3
        * [
        *  *   *   *   *
        *  *   *   *   *
        *  *   *   *   *
        * ]
        * -->
        * [        i ->
        *   0  0   1   2   3
              ----------------
        *   1 |1   1   1   1
        *j  2 |1   *   *   *
        *|  3 |1   *   *   *
        * ]
        *
        * i = 1    j                                                    # of ways from (0,0) -> (i,j) = res[i][j]
        *          1       res[1][1]=res[0][1]+res[1][0] = 1 + 1 = 2    (0,0) -> (1,1) = res[1][1] = 2
        *          2       res[1][2]=res[0][2]+res[1][1] = 1 + 2 = 3    (0,0) -> (1,2) = res[1][2] = 3

        * i = 2    j
        *          1       res[2][1]=res[1][1]+res[2][0] = 2 + 1 = 3    (0,0) -> (2,1) = res[2][1] = 3
        *          2       res[2][2]=res[1][2]+res[2][1] = 3 + 3 = 6    (0,0) -> (2,2) = res[2][2] = 6

        * i = 3    j
        *          1       res[3][1]=res[2][1]+res[3][0] = 3 + 1 = 4    (0,0) -> (3,1) = res[3][1] = 4
        *          2       res[3][2]=res[2][2]+res[3][1] = 6 + 4 = 10   (0,0) -> (3,2) = res[3][2] = 10
        *
        * return res[4-1][3-1]=res[3][2]=10
        */
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            res[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            res[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                res[i][j] = res[i - 1][j] + res[i][j - 1];
            }
        }
        return res[m - 1][n - 1];
    }

    // time : O(n * m) space : O(n)
    public static int uniquePathsII(int m, int n) {
        /**
         * m = 4, n = 3
         * [
         *  *   *   *   *
         *  *   *   *   *
         *  *   *   *   *
         * ]
         * -->
        * [    i ->
        *      0   1   2   3
              ----------------
        *   0 |*   *   *   *
        *j  1 |*   *   *   *
        *|  2 |*   *   *   *
        * ]
         * i = 0    j                                               res = int[3] = [1,0,0]  # of ways from (0,0) -> (i,j) = res[j]
         *          1       res[1]=res[1]+res[0] = 0 + 1 = 1        [1,1,0]                 (0,0) -> (0,1) = res[0] = 1
         *          2       res[2]=res[2]+res[1] = 0 + 1 = 1        [1,1,1]                 (0,0) -> (0,2) = res[1] = 1
         * 
         * i = 1    j
         *          1       res[1]=res[1]+res[0] = 1 + 1 = 2        [1,2,1]                 (0,0) -> (1,1) = res[1] = 2
         *          2       res[2]=res[2]+res[1] = 1 + 2 = 3        [1,2,3]                 (0,0) -> (1,2) = res[2] = 3
         * 
         * i = 2    j
         *          1       res[1]=res[1]+res[0] = 2 + 1 = 3        [1,3,3]                 (0,0) -> (2,1) = res[1] = 3
         *          2       res[2]=res[2]+res[1] = 3 + 3 = 6        [1,3,6]                 (0,0) -> (2,2) = res[2] = 6
         * 
         * i = 3    j
         *          1       res[1]=res[1]+res[0] = 3 + 1 = 4        [1,4,6]                 (0,0) -> (3,1) = res[1] = 4
         *          2       res[2]=res[2]+res[1] = 6 + 4 = 10       [1,4,10]                (0,0) -> (3,2) = res[2] = 10
         * return res[3-1]=res[2] = 10
         */
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                res[j] = res[j] + res[j - 1];
            }
        }
        return res[n - 1];
    }

    /**
     * Combination(Count, k) = count! / (k!*(count - k)!)
     * C = (count - k + 1) * (count - k + 2) .../k!
     * time : O(m)
     * space : (1)
     * @param m
     * @param n
     * @return
     */
    public static int uniquePathsIII(int m, int n) {
        int count = m + n - 2;
        int k = m - 1;
        double res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (count - k + 1) / i;
        }
        return (int)res;
    }
}