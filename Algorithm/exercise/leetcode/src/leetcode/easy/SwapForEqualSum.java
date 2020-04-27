package leetcode.easy;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SwapForEqualSum {
    /**
     * Swap For Equal Sum (Vmware)

     Given two unsorted arrays of integers, swap one element from each to make the sum of two arrays equal with each other

     Example:
     arr1: [4, 12, 8, 10]   (34)
     arr2: [5, 6, 7, 3, 9]  (30)

     swap 8 from arr1 with 6 from arr2
     arr1: [4, 12, 6, 10]   (32)
     arr2: [5, 8, 7, 3 9]   (32)

     return (8, 6)
     time : O(n)
     space : O(n)
     * @param nums
     * @param target
     * @return
     */
    public static int[] SwapForEqualSum(int[] arr1, int[] arr2) {
        int[] res = new int[]{};
        // step 1: error boundry check
        if ((arr1 == null && arr2 == null) ||
            (arr1 == null && arr2.length == 1) ||
            (arr1.length == 1 && arr2 == null)
        {
            return res;
        }

        // step 2: calculate sum of total element in both arrays
        // if sum is odd, meaning there won't be any chance to make both arrays' sum equal
        // if sum is even, meaning the two element we're going to swap should have a difference
        // of total sum / 2 from each array
        /**
         * arr1: [4, 12, 8, 10]   (34)
         * arr2: [5, 6, 7, 3, 9]  (30)
         * total of 2 arrays are 64, meaning if the final result of both arrays will be equal
         * sum(arr1) == sum(arr2) == 32
         * meaning the element from arr2 to be swapped inside arr1 needs to make arr1 as 34 - 2 = 32
         * same as the element from arr1 to be swapped inside arr2 needs to make arr2 as 30 + 2 = 32
         * therefore, the question becomes either find an element from arr1 so that each (element - 2) has a corresponding value in arr 2
         */ 
         int sum1 = Arrays.stream(arr1).sum();
         int sum2 = Arrays.stream(arr1).sum();
         int totalSum = sum1 + sum2;
         if (totalSum % 2 == 1) {
             return res;
         }
         
         int diff = totalSum / 2 - sum1;
         Set<Integer> possibleMatchInSum2 = new HashSet<>();
         possibleMatchInSum2 = Arrays.stream(arr1).map(ele -> ele - diff).Collectors.collect(Collectors.toSet());
         // possibleMatchInSum2: [2, 10, 6, 8]
         // we need to find if any of the number in possibleMatchInSum2 would existed in arr 2
         // if found, we just return the [found and found + diff] 
         // which would be the one from arr 1(because arr1 put value as element - diff)
         Integer swapInArr2 = Arrays.stream(arr2).findFirst(el -> possibleMatchInSum2.contains(el));
         return swapInArr2 == null ? res : new int[]{found + diff, found};
    }
}