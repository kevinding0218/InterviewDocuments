package leetcode.medium;

/**
 * Created by Ran on May 7, 2020.
 */
public class MinimumSizeSubarraySum {
  /**
   * LC. 209 Minimum Size Subarray Sum (Amazon)
   * 
   * Given an array of n positive integers and a positive integer s, 
   * find the minimal length of a contiguous subarray of which the sum ≥ s. 
   * If there isn't one, return 0 instead.

    Example: 

    Input: s = 7, nums = [2,3,1,2,4,3]
    Output: 2
    Explanation: the subarray [4,3] has the minimal length under the problem constraint.

    Follow up:
    If you have figured out the O(n) solution, 
    try coding another solution of which the time complexity is O(n log n). 
   */


   /**
    * 2 pointers

    we could move the starting index of the current subarray as soon as we know that no better could be done with this index as the starting index. 
    We could keep 2 pointer,one for the start and another for the end of the current subarray, 
    and make optimal moves so as to keep the sum greater than ss as well as maintain the lowest size possible.

    Algorithm
    - Initialize left pointer to 0 and sum to 0
    - Iterate over the nums:
      * Add nums[i] to sum
      * While sum is greater than or equal to ss:
        ^ Update ans=min(ans, i+1−left), where (i+1−left) is the size of current subarray
        ^ It means that the first index can safely be incremented, since, 
          the minimum subarray starting with this index with sum >= s has been achieved
        ^ Subtract nums[left] from sum and increment left

    * @param s
    * @param nums
    * @return
    */
  public int minSubArrayLen(int s, int[] nums) {
    // For max or min question, always define a min or max value
    // and replace it when we find better solution
    int minLen = nums.length +1;
    int left = 0, sum = 0;
    for (int right = 0; right < nums.length; right++) {
        // sum = current window of 0 ~ right
        sum += nums[right];
        // left would be the iterator of current window: 0 ~ right
        while (left <= right && sum >= s) {
            //decrease till get smallest subarray possible in current window
            minLen = Math.min(minLen, right - left + 1);
            sum -= nums[left++];
        }
    }
    return minLen == nums.length + 1 ? 0 : minLen;
  }
}