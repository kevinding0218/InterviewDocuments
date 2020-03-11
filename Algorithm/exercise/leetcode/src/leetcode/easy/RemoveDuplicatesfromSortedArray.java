package leetcode.easy;

/**
 * Created by Ran on Mar 10, 2020.
 */
public class RemoveDuplicatesfromSortedArray {
    /**
     * LC 26. Remove Duplicates from Sorted Array
     * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.

     Do not allocate extra space for another array, you must do this in place with constant memory.

     Example 1: Given input array nums = [1,1,2],
     Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively. It doesn't matter what you leave beyond the new length.

     Example 2: Given nums = [0,0,1,1,1,2,2,3,3,4],
     Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.

     Clarification:
    Confused why the returned value is an integer but your answer is an array?
    Note that the input array is passed in by reference, which means modification to the input array will be known to the caller as well.
    Internally you can think of this:

    // nums is passed in by reference. (i.e., without making a copy)
    int len = removeDuplicates(nums);

    // any modification to nums in your function would be known by the caller.
    // using the length returned by your function, it prints the first len elements.
    for (int i = 0; i < len; i++) {
        print(nums[i]);
    }

     time : O(n);
     space : O(1);

     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        // step 1: error boundary check
        if (null == nums || nums.length == 0) return 0;
        // declare and initialize result
        int res = 1;
        // step 2: loop from the second element in array
        for (int i = 1; i < nums.length; i++) {
            // step 3(important!): Two pointers - fast and slow
            // two pointers i and j/(i-1), where j is the slow-runner while i is the fast-runner. 
            // As long as nums[i] = nums[j], we increment j/i-1 to skip the duplicate
            // 
            // if iterator value does not equal to its prev value in array
            // assign it to its prev value and increase result count
            // so that when iterator i moves faster than result count,
            // it won't make duplicate count as different number
            // e.g: 1 2 2 3 3 4   res/j i   compare
            //        i           2     1   1<>2
            //      1 2 2 3 3 4   
            //          i         2     2   2==2
            //      1 2 2 3 3 4
            //            i       3     3   2<>3
            //      1 2 3 3 3 4
            //              i     3     4   3==3
            //      1 2 3 3 3 4
            //                i   4     5   3<>4
            //      1 2 3 3 4 4             
            if (nums[i-1] != nums[i]) {
                nums[res++] = nums[i];
            }
        }
        return res;
    }
}
