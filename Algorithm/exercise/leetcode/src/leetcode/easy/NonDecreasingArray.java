package leetcode.easy;

/**
 * Created by Ran on Apr 28, 2020.
 */
public class NonDecreasingArray {
  /**
   * LC. 665 Non-decreasing Array (Microsoft)
   * You are given an array of integers in an arbitrary order. 
   * Return whether or not it is possible to make the array non-decreasing by modifying at most 1 element to any value.
   * We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).
   * Example:
   * 
   * [13, 4, 7] should return true, since we can modify 13 to any value 4 or less, to make it non-decreasing.
   * 
   * [13, 4, 1] however, should return false, since there is no way to modify just one element to make the array non-decreasing.
   * 
   * @param nums
   * @return
   */
  public boolean checkPossibility(int[] nums) {
      /**
       * solution: we're trying to find a way to modify the first occurence of non-match condition,
       * after that we'll continue traverse, if we found second non-match occurence, return false
       * 
       * 2 4 2 6 -> 2 2 2 6
       *     i   -> once we found a[i-1]>a[i], we also need to check a[i-2] vs a[i]
       *            if a[i-2] <= a[i], we can make a[i-1] = a[i]
       * 3 4 2 6 0> 3 4 4 6
       *     i    -> once we found a[i-1]>a[i], we also need to check a[i-2] vs a[i]
       *            if a[i-2] > a[i], we can either make a[i] = a[i-1] or make two changes in a[i-2] and a[i-1]
       * once we made our change, if we found another similar case, that means we still need additional/second
       * modification to make array ascending order, we have to return false
       */
      if (nums == null || nums.length < 2)  return true;
      int count = 0;
      for (int i = 1; i < nums.length; i++) {
        if (nums[i - 1] > nums[i])
        {
          // here means we found the case where a[i-1]>a[i]
          count ++;
          if (count > 1) {
            return false;
          }
          // if i - 2 < 0 means nums[i-2] not existed
          // or case 1: a[i-2] >= a[i] -> make a[i-1] = a[i]
          // e.g: 2 4 2 6 -> 2 2 2 6
          //          i
          if (i - 2 <0 || nums[i - 2] <= nums[i]) {
            nums[i - 1] = nums[i];
          } else {
            // case 2: a[i-2] > a[i] -> make a[i] = a[i-1]
            // e.g: 3 4 2 6 0> 3 4 4 6
            //          i
            nums[i] = nums[i - 1];
          }
        }
      }
      return true;
  }
}