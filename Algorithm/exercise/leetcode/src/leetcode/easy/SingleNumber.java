package leetcode.easy;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ran on Apr 27, 2020.
 */
public class SingleNumber {
    /**
     * LC. 136 Single Number within twice appear array (Facebook)
     * Given an array of integers, every element appears twice except for one. Find that single one.
     *
     * Example 1:
     * Input: [2,2,1]
     * Output: 1
     * 
     * Example 2:
     * Input: [4,1,2,1,2]
     * Output: 4
     *
     * time : O(n) space : O(1)
     *
     * @param nums
     * @return
     */
    public int singleNumberI(int[] nums) {
      /**
       * Using a hashset
       * time : O(n) space : O(n)
       */
      Set<Integer> mySet = new HashSet<>();
      for(int num : nums) {
        mySet.add(Integer.valueOf(num));

        if (mySet.contains(num)) {
          mySet.remove(num);
        }
      }
      return mySet.iterator().next().intValue();
    }

    public int singleNumberII(int[] nums) {
      /**
       * Using XOR (相同为0，不同为a)
       * time : O(n) space : O(1)
       * If we take XOR of zero and some bit, it will return that bit
         a ^ 0 = a
         If we take XOR of two same bits, it will return 0
         a ^ a = 0
         a ^ b ^ a = (a ^ a)  ^ b = 0 ^ b = b
       * 
       * e.g: [4,1,2,1,2]
       * 0 ^ 4 ^ 1 ^ 2 ^ 1 ^ 2
       *     4 ^ 1 ^ 2 ^ 1 ^ 2
       *     4 ^ 1 ^ 1 ^ 2 ^ 2
       *     4 ^ 0 ^ 0
       *     4 ^ 0
       *     4         
       */
      int res = 0;
      for (int i = 0; i < nums.length; i++) {
          res ^= nums[i];
      }
      return res;
    }
}