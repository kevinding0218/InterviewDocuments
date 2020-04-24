package leetcode.medium;

/**
 * Created by Ran on Apr 23, 2020.
 */
public class FindFirstLastElementInSortedArray {
	/**
     * LC 34. Search for a Range (AirBNB)
     * Given an array of integers sorted in ascending order, find the starting and ending
     * position of a given target value.

     Your algorithm's runtime complexity must be in the order of O(log n).

     If the target is not found in the array, return [-1, -1].

     For example,
     Given [5, 7, 7, 8, 8, 10] and target value 8,
		 return [3, 4].
		 
     Input: A = [1,3,3,5,7,8,9,9,9,15], target = 9
     Output: [6,8]

     Input: A = [100, 150, 150, 153], target = 150
     Output: [1,2]

     Input: A = [1,2,3,4,5,6,10], target = 9
     Output: [-1, -1]

     time : O(logn)
     space : O(1);
     * @param nums
     * @param target
     * @return
     */

	public int[] searchRange(int[] nums, int target) {
          // step 1 : error boundry check
          if (nums == null || nums.length == 0) return new int[]{-1, -1};
          // step 2: find start
          int start = findFirst(nums, target);
          // if start cannot be found, means there is no such number in array, 
          // return not found result
          if (start == -1) return new int[]{-1, -1};
          // step 3: find last
          int end = findLast(nums, target);
          return new int[]{start, end};
     }
     
     // step 2 (important!) - when question mentioned O(logn)
     // we need to use binary search, therefore, we need start, end and mid
     private int findFirst(int[] nums, int target) {
          int start = 0;
          int end = nums.length - 1;
          while (start + 1 < end) {
               // !important, this is to prevent overflow
               int mid = (end - start) / 2 + start;
               // Key: compare nums[mid] < target first because we want to get First
               // so we need to go with smaller set first
               // if mid element in array is less than target
               // means target is on right side of the array
               // we should move start position forward to where mid currently is
               // otherwise, means target is on left side of the array
               // we should move end position backward to where mid currently is
               if (nums[mid] < target) {
                    start = mid;
               } else {
                    end = mid;
               }
          }
          /**
           * 0 1 2 3 4 5
           * 1 2 5 5 5 9
           * start  end  mid  nums[mid]      nums[mid] < 5
           * 0      5    2    nums[2] -> 5   false     mid is not less than target, meaning there could be any number smaller than mid and close to target on current left side for First, move end backward
           * 0      2    1    nums[1] -> 1   true      mid is now less than target, meaning there could be any number greater than mid and close to target on current right side for First move start forward
           * 1      2                                  jump out of while
           * now check from nums[start] firstly
           * num[1] -> 2 == 5 ? false
           * then check from nums[end] secondly
           * num[2] -> 5 == 5 ? true
           * return end -> 2
           */
          if (nums[start] == target) return start;
          if (nums[end] == target) return end;
          return -1;
     }

     // step 3 (important!)- when question mentioned O(logn)
     // we need to use binary search, therefore, we need start, end and mid
     private int findLast(int[] nums, int target) {
          int start = 0;
          int end = nums.length - 1;
          while (start + 1 < end) {
               // !important, this is to prevent overflow
               int mid = (end - start) / 2 + start;
               // Key: compare nums[mid] > target first because we want to get Last
               // so we need to go with larger set first
               // if mid element in array is greater than target
               // means target is on left side of the array
               // we should move end position backward to where mid currently is
               // otherwise, means target is on right side of the array
               // we should move start position forward to where mid currently is
               if (nums[mid] > target) {
                    end = mid;
               } else {
                    start = mid;
               }
          }
          /**
           * 0 1 2 3 4 5
           * 1 2 5 5 5 9
           * start  end  mid  nums[mid]      nums[mid] > 5
           * 0      5    2    nums[2] -> 5   false     mid is not greater than target, meaning there could be any number greater than mid and close to target on current right side for Last move start forward
           * 2      5    3    nums[3] -> 5   false     mid is not greater than target, meaning there could be any number greater than mid and close to target on current right side for Last move start forward
           * 3      5    4    nums[4] -< 5   false     mid is not greater than target, meaning there could be any number greater than mid and close to target on current right side for Last move start forward
           * 4      5                                  jump out of loop
           * now check from nums[end] firstly
           * num[5] -> 9 == 5 ? false
           * then check from nums[start] secondly
           * num[4] -> 5 == 5 ? true
           * return start -> 4
           */
          if (nums[end] == target) return end;
          if (nums[start] == target) return start;          
          return -1;
     }
}