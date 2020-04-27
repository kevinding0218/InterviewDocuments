package leetcode.easy;

import java.util.HashMap;

/**
 * Created by Ran on Mar 8, 2020.
 */
public class TwoSum {
    /**
     *  LC 1. Two Sum (Facebook)

     Given an array of integers, return indices of the two numbers such that they add up to a specific target.

     You may assume that each input would have exactly one solution, and you may not use the same element twice.

     Given nums = [2, 7, 11, 15], target = 9,

     Because nums[0] + nums[1] = 2 + 7 = 9,

     return [0, 1].

     time : O(n)
     space : O(n)
     * @param nums
     * @param target
     * @return
     */

    public static int[] twoSum(int[] nums, int target) {
        // step 1: declare and initialize result
        int[] res = new int[]{-1,-1};
        // step 2 (important!): error boundary check
        if (null == nums || nums.length < 2) {
            return res;
        }
        /* 
        step 3: result is looking for the index, so we created
        a Map with <key> as <nums[index]> and <value> as <index> in array
        Given example the Map would be like
            (2, 0)
            (7, 1)
            (11, 2)
            (15, 3) 
        */
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // step 5(important!): think about the match case
            // match case: iterative value + any num saved in Map = target
            // if found, we need to retrieve the index to result
            if (map.containsKey(target - nums[i])) {
                res[0] = map.get(target - nums[i]);
                res[1] = i;
            }
            // step 4(important!): while loop through the array, we always need to 
            // put num[i] as default/init value in the Map
            // also think about this as non-match case, if there is no match
            // we expect the Map would contain all values from the array
            map.put(nums[i], i);
        }
        return res;
    }
}