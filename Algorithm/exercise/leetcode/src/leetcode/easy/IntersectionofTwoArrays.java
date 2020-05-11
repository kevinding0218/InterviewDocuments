package leetcode.easy;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Ran on May 10, 2020.
 */
public class IntersectionofTwoArrays {
    /**
     * LC. 349 Intersection of Two Arrays (Facebook)
     * 
     * Given two arrays, write a function to compute their intersection.

        Example 1:

        Input: nums1 = [1,2,2,1], nums2 = [2,2]
        Output: [2]
        Example 2:

        Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
        Output: [9,4]
        Note:

        Each element in the result must be unique.
        The result can be in any order.

        What if the given array is already sorted? How would you optimize your algorithm? // Check 2nd solution
        What if nums1's size is small compared to nums2's size? Which algorithm is better? // HashMap
        What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
     */

    // Arrays.sort time : O(nlogn) space : O(n);
    public static int[] intersectionI(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[]{};
        }
        HashSet<Integer> set = new HashSet<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0;
        int j = 0;
        /**
         * nums1 = [4,9,5], nums2 = [9,4,9,8,4]
         * after sort:
         * nums1 = [4,5,9], nums2 = [4,4,8,9,9]
         */
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                // when nums1[i]  == nums[j], found intersection and add into set
                set.add(nums1[i]);
                i++;
                j++;
            }
        }
        int k = 0;
        int[] res = new int[set.size()];
        // Convert map into result array
        for (Integer num : set) {
            res[k++] = num;
        }
        return res;
    }

    // time : O(n) space : O(n);
    public static int[] intersectionII(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[]{};
        }
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> ret = new HashSet<>();
        // set contains all element of nums1
        for (Integer num : nums1) {
            set.add(num);
        }
        // if any element in nums2 found in set, meaning it's an intersection, added into result
        for (Integer num : nums2) {
            if (set.contains(num)) {
                ret.add(num);
            }
        }
        int k = 0;
        int[] res = new int[ret.size()];
        // Convert map into result array
        for (Integer num : ret) {
            res[k++] = num;
        }
        return res;
    }

    // binary search time : O(nlogn) space : O(n)
    public static int[] intersectionIII(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[]{};
        }
        HashSet<Integer> set = new HashSet<>();
        Arrays.sort(nums2);
        for (Integer num : nums1) {
            if (binarySearch(nums2, num)) {
                set.add(num);
            }
        }
        int k = 0;
        int[] res = new int[set.size()];
        for (Integer num : set) {
            res[k++] = num;
        }
        return res;
    }

    public static boolean binarySearch(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (nums[start] == target || nums[end] == target) return true;
        return false;
    }
}