package leetcode.easy;

/**
 * Created by Ran on May 2, 2020.
 */
public class ClimbStairs {
  /**
   * LC 70. Climbing Stairs (LinkedIn)
   * You are climbing a stair case. It takes n steps to reach to the top.

    Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

    Note: Given n will be a positive integer.

    Example 1:

    Input: 2
    Output: 2
    Explanation: There are two ways to climb to the top.
    1. 1 step + 1 step
    2. 2 steps
    Example 2:

    Input: 3
    Output: 3
    Explanation: There are three ways to climb to the top.
    1. 1 step + 1 step + 1 step
    2. 1 step + 2 steps
    3. 2 steps + 1 step
   */

   /**
    * DP  ~ Fibonacci Recursion
    * 
    * As we can see this problem can be broken into subproblems, 
      and it contains the optimal substructure property 
      i.e. its optimal solution can be constructed efficiently from optimal solutions of its subproblems, 
      we can use dynamic programming to solve this problem.

      One can reach i th step in one of the two ways:

      Taking a single step from (i−1) th step.

      Taking a step of 2 from (i−2) th step.

      So, the total number of ways to reach i th is equal to sum of ways of reaching 
      (i−1) th step and ways of reaching (i−2) th step.

      Let dp[i] denotes the number of ways to reach on i th step:

      dp[i]=dp[i-1]+dp[i-2]
    * @param n
    * @return
    */
  public int climbStairs(int n) {
    // Step 1: if stairs is 1 or 2, then ways are same as 1 or 2
    if (n <= 2) {
      return n;
    } else {
        // Step 2: step n = result of step(n-1) + step(n-2)
        return climbStairs(n - 1) + climbStairs(n - 2);
    }   
  }

  /**
   * Fibonacci Iterative
   * 
   * @param n
   * @return
   */
  public int climbStairs2(int n) {
    // Step 1: if stairs is 1 or 2, then ways are same as 1 or 2
    if (n <= 1) return 1;

    int oneStep = 1, twoStep = 1, res = 0;

    for (int i = 2; i <= n; i++) {
      /**       
       * i    oneStep    twoStep   res            (next)oneStep       (next)twoStep
       * 2    1          1         2              2 (1+1)             1
       * 3    2 (1+1)    1         3              3                   2
       * 4    3 (1+2)    2         5              5                   3
       * 5    5 (3+2)    3         8              8                   5
       * 6    8 (5+3)    5         13             13                  8
       * .
       * .
       * n (n-1)+(n-2)  (n-2)  (n-1)+(n-2)+(n-2)
       * sum(n) = sum(n-1) + prev(n-2) = [prev(n-2) + prev(n-3)] + prev(n-2)
       */
        // here we use an additional param to remember the current sum
        res = oneStep + twoStep;
        // assign previous step to be twoStep in next iterator
        // so that we have next iterator i, twoStep = i-2
        twoStep = oneStep;
        // assign current sum to be oneStep in next iterator
        // so that if we have next iterator i, 
        // oneStep=sum(i-1)=i+(i-1)
        // res(i)=sum(i-1)+(i-2)=i+(i-1)+(i-2)
        oneStep = res;
    }
    return res;
}
}