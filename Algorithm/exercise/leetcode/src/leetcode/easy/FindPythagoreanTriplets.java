package leetcode.easy;

import java.util.Arrays;

/**
 * Created by Ran on May 3, 2020.
 */
public class FindPythagoreanTriplets {
  /**
   * LC. ??? (Uber)
   * Given a list of numbers, find if there exists a pythagorean triplet in that list. 
   * A pythagorean triplet is 3 variables a, b, c where a*a + b*b = c*c

    Example:
    Input: [3, 5, 12, 5, 13]
    Output: True
    Here, 5^ + 12^ = 13^

   * @param nums
   * @return
   */
  public static Boolean findPythagoreanTriplets(int[] arr) {
    int n = arr.length;
    // Square array elements -> o(n)
    for (int i = 0; i < n; i++) 
    arr[i] = arr[i] * arr[i]; 

    // Sort array elements -> O(nlogn)
    Arrays.sort(arr); 

    // Now fix one element one by one and find the other two 
    // elements -> O(n^2)
    // we starts from rightmost which would be the largest element
    for (int i = n - 1; i >= 2; i--) { 
        // To find the other two elements, start two index 
        // variables from two corners of the array and move 
        // them toward each other 
        int l = 0; // index of the first element in arr[0..i-1] 
        int r = i - 1; // index of the last element in arr[0..i-1] 
        while (l < r) { 
            // A triplet found 
            if (arr[l] + arr[r] == arr[i]) 
                return true; 

            // Else either move 'l' or 'r' 
            if (arr[l] + arr[r] < arr[i]) 
                l++; 
            else
                r--; 
        } 
    } 

    // If we reach here, then no triplet found 
    return false;
  }
}