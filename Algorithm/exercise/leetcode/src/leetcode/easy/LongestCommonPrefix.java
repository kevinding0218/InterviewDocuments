package leetcode.easy;

/**
 * Created by Ran on Mar 9, 2020.
 */
public class LongestCommonPrefix {
  /**
   * LC 14. Longest Common Prefix Write a function to find the longest common
   * prefix string amongst an array of strings.
   * 
   * Example 1: Input: ["flower","flow","flight"] Output: "fl"
   * 
   * Example 2: Input: ["dog","racecar","car"] Output: "" Explanation: There is no
   * common prefix among the input strings.
   *
   * All given inputs are in lowercase letters a-z.
   * 
   * case : "edwardshi", "edward", "edwar", "edwardshidd"
   * 
   * time : O(n); space : O(1);
   *
   * @param strs
   * @return
   */
  public static String longestCommonPrefix(String[] strs) {
    // step 1: error boundary check
    if (strs == null || strs.length == 0)
      return "";
    // step 2: declare and initialize result with first string in array
    String res = strs[0];
    // step 3: compare if rest strings in array contains first string
    for (int i = 1; i < strs.length; i++) {
      while (strs[i].indexOf(res) != 0) {
        // step 4 (important!): if not contained, decrease res by 1 character from end
        res = res.substring(0, res.length() - 1);
      }
    }
    return res;
  }
}
