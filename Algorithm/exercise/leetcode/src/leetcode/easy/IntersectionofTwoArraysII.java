package leetcode.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ran on May 10, 2020.
 */
public class IntersectionofTwoArraysII {
    /**
     * LC. 350 Intersection of Two Arrays II
     * 
     * Given two arrays, write a function to compute their intersection.

        Example 1:

        Input: nums1 = [1,2,2,1,2], nums2 = [2,2]
        Output: [2,2]
        Example 2:

        Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
        Output: [4,9]
        Note:

        Each element in the result should appear as many times as it shows in both arrays.
        The result can be in any order.
        Follow up:

        What if the given array is already sorted? How would you optimize your algorithm? // Two pointers, Check 2nd solution
        What if nums1's size is small compared to nums2's size? Which algorithm is better? // HashMap and use smaller array to store in Map
        What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
        - If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that fit into the memory, and record the intersections.
        - If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (external sort), 
        then read 2 elements from each array at a time in memory, record intersections.
        - Divide and conquer. Repeat the process frequently: Slice nums2 to fit into memory, process (calculate intersections), and write partial results to memory.
     */

    // HashMap, time : O(n), space : O(n);
    public static int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> ret = new ArrayList<>();
        // Store number and its count of appearance of nums1 into map
        for (int i = 0; i < nums1.length; i++) {
            if (map.containsKey(nums1[i])) {
                map.put(nums1[i],map.get(nums1[i]) + 1);
            } else {
                map.put(nums1[i], 1);
            }
        }
        // Travese through nums2, foreach nums2[i] contains in map, store into result list, and decrease the count of appearance in map
        // map will eventually contain the intersection of number of count 0 if (number in nums1 apperance count < num2), otherwise > 0
        for (int i = 0; i < nums2.length; i++) {
            if (map.containsKey(nums2[i])) {
                if (map.get(nums2[i]) > 0) {
                    ret.add(nums2[i]);
                    map.put(nums2[i], map.get(nums2[i]) - 1);
                }
            }
        }
        // ret stored all intersection with count of appearance in both arrays
        int[] res = new int[ret.size()];
        int k = 0;
        // Convert list into result array
        for (Integer num : ret) {
            res[k++] = num;
        }
        return res;
    }

     // Arrays.sort time : O(nlogn) space : O(n);
    public static int[] intersect2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        /**
         * nums1 = [4,9,5], nums2 = [9,4,9,8,4]
         * after sort:
         * nums1 = [4,5,9], nums2 = [4,4,8,9,9]
         */
        List<Integer> ret = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                // when nums1[i]  == nums[j], found intersection and add into list
                ret.add(nums1[i]);
                i++;
                j++;
            }
        }
        // ret stored all intersection with count of appearance in both arrays
        int[] res = new int[ret.size()];
        int k = 0;
        // Convert list into result array
        for (Integer num : ret) {
            res[k++] = num;
        }
        return res;
    }
}