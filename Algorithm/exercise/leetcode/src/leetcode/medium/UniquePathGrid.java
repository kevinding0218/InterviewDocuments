package leetcode.medium;

/**
 * Created by Ran on May 9, 2020.
 */
public class UniquePathGrid {
    /**
     * LC. 62  Unique Paths (Microsoft)
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

     // time : O(n * m) space : (n * m)
    public int uniquePathsI(int m, int n) {
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
    public int uniquePathsII(int m, int n) {
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
    public int uniquePathsIII(int m, int n) {
        int count = m + n - 2;
        int k = m - 1;
        double res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (count - k + 1) / i;
        }
        return (int)res;
    }
}