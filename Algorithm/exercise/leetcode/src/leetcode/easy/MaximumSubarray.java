package leetcode.easy;

/**
 * Created by Ran on May 23, 2020.
 */
public class MaximumSubarray {
  /**
   * LC. 53 Maximum Subarray (Twitter)
   * 
   * Find the contiguous subarray within an array (containing at least one number) 
   * which has the largest sum.

     For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
     the contiguous subarray [4,-1,2,1] has the largest sum = 6.

     For example, given the array [34, -50, 42, 14, -5, 86]
     the contiguous subarray [42, 14, -5, 86] has the largest sum = 137.
   */

   // time : O(n) space : O(n);
   public int maxSubArray(int[] nums) {
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    int res = nums[0];
    for (int i = 1; i < nums.length; i++) {
      /**
       * [34, -50, 42, 14, -5, 86]
       * i    nums[i]   dp[i]                         res                 dp
       * 0    34        34                            34                  [34]
       * 1    -50       -50 + (34 < 0 -> 34) = -16    max(34, -16) = 34   [34, -16]
       * 2    42         42 + (-16 < 0 -> 0) = 42     max(34, 42) = 42    [34, -16, 42]
       * 3    14         14 + (42 < 0 -> 42) = 56     max(42, 56) = 56    [34, -16, 42, 56]
       * 4    -5        -5 + (56 < 0 -> 56) = 51      max(56, 61) = 56    [34, -16, 42, 56, 51]
       * 5    86        86 + (51 < 0 -> 51) = 137     max(56, 137) = 137  [34, -16, 42, 56, 51, 137]
       * 
       * dp idea: [a, b, c, d, e, f]
       * 
       * when comes to b, two possible ways to get the max that involves b
       * i. b itself
       * ii.b + max sum of subarray that be prior to b (dp[a]), , 
       * and if dp[a] < 0, actually we would consider as case i as (b + 0) 
       * because we can have a new starting point from b
       * 
       * when comes to c, two possible ways to get the max that involves c
       * i. c itself
       * ii.c + max sum of subarray that be prior to c (dp[b]), 
       * and if dp[b] < 0, actually we would consider as case i as (c + 0)
       * because we can have a new starting point from c
       * 
       * so we can come up with a pattern that dp[i] = nums[i] + dp[i-1] < ? (0 : dp[i-1]) <case1 : case2>
       */
        dp[i] = nums[i] + (dp[i - 1] < 0 ? 0 : dp[i - 1]);
        res = Math.max(res, dp[i]);
    }
    return res;
}

  // time : O(n) space : O(1);
  public int maxSubArray2(int[] nums) {
    int res = nums[0];
    int sum = nums[0];
    for (int i = 1; i < nums.length; i++) {
      /**
       * [34, -50, 42, 14, -5, 86]
       * i  nums[i]     sum                       res
       * 0  34          34                        34
       * 1  -50         max(-50, 34+(-50)) = -16  max(34, -16)= 34  (we can pick 34)
       * 2  42          max(42, -16+42) = 42      max(34, 42) = 42  (we can pick 42)
       * 3  14          max(14, 42+14) = 56       max(42, 56) = 56  (we can pick 42 + 14)
       * 4  -5          max(-5, 56+(-5)) = 51     max(56, 51) = 56  (we can pick 42 + 14)
       * 5  86          max(51, 51 + 86) = 137    max(56, 137) = 142(we can pick 42, 14, -5, 86)
       */
        sum = Math.max(nums[i], sum + nums[i]);
        res = Math.max(res, sum);
    }
    return res;
}
}