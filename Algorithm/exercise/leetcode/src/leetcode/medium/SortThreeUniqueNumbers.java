package leetcode.medium;

import java.util.Arrays;

/**
 * Created by Ran on Apr 25, 2020.
 */
public class SortThreeUniqueNumbers {
    /**
     * LC 75. Sort Three Unique Numbers in O(n) (Google)
     * Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, 
     * with the colors in the order red, white and blue.
     * 
     * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
     * 
     * Example:
     * Input: [2,0,2,1,1,0]
     * Output: [0,0,1,1,2,2]
     * 
     * time : O(n)
     * space : O(1)
     * @param nums
     */
    public static void sortThreeUniqueNumbers(int[] nums) {
        // step 1: error boundry check
        if (nums == null || nums.length == 0) return;

        // step 2: traverse from left side
        int left = 0;
        int right = nums.length - 1;
        int index = 0;
        while (index <= right) {
            // step 3 (important!): since we know numbers can only be 0, 1 or 2
            // the idea to to keep 0 as left as possible, 1 in the middle and 2 as right as possible
            // if current number is 0, we know that should be placed as left as possible, 
            //  - so we swap it with current left, 
            //  - because we know after swap, left is already the min of 0
            //  - so we move left forward(left++)
            //  - also we move index forward, because we know after swap index should be 0 as well
            //    because index traverse from left and sync with left
            //  - right now it could be -> left = index -> right
            // if current number is 1, we know that should be placed as middle as possible, 
            //  - so we're not swap but move index forward
            //  - right now it could be => left -> index -> right
            // if current number is 2, we know that should be placed as right as possible, 
            //  - so we swap current with current right, then 
            //  - because we know after swapping, right is already the max of 2
            //  - so we move right backward => (right--)
            //  - but keep index same position, because we are not sure if current index after swap would be 0/1/2
            //  - right now it could be => left >> index -> right
            /**
             * e.g
             * [2,0,2,1,1,0]
             *  0 1 2 3 4 5
             * index    left     right      nums[index] op      nums(afterswap) index   left    right   
             * 0         0        5         nums[0]=>2  s(0,5)  [0,0,2,1,1,2]   0       0       4      (we're decrease right but keep index same pos because we are not sure if current index after swap would be 0/1/2) 
             * 0         0        4         nums[0]=>0  s(0,0)  [0,0,2,1,1,2]   1       1       4      (we're increase left and also increase index because we know index starts from left)
             * 1         1        4         nums[1]=>0  s(1,1)  [0,0,2,1,1,2]   2       2       4      (we're increase left and also increase index because we know index starts from left)
             * 2         2        4         nums[2]=>2  s(2,4)  [0,0,1,1,2,2]   2       2       3      (we're decrease right but keep index same pos because we are not sure if current index after swap would be 0/1/2)
             * 2         2        3         nums[2]=>1                          3       2       3      (we're only increase index because we want to keep 1 in middle and we know left would be 0)
             * 3         2        3         nums[3]=>1          [0,0,1,1,2,2]   4       2       3      (we're only increase index because we want to keep 1 in middle and we know left would be 0)
             * exit
             */
            if (nums[index] == 0) {
                swap(nums, index++, left++);
            } else if (nums[index] == 1) {
                index++;
            } else {
                swap(nums, index, right--);
            }
        }
        Arrays.stream(nums).forEach(System.out::println);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}