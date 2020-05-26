package leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ran on May 24, 2020.
 */
public class PalindromePartitioning {
  /**
   * LC. 131 Palindrome Partitioning (Microsoft)
   * 
   * Given a string s, partition s such that every substring of 
   * the partition is a palindrome.

    Return all possible palindrome partitioning of s.

    Example:

    Input: "aab"
    Output:
    [
      ["aa","b"],
      ["a","a","b"]
    ]
   */

   /**
    * time: O(2^n * n^2) 
    space O(n)

    * @param s
    * @return
    */
  public List<List<String>> partition(String s) {
    List<List<String>> res = new ArrayList<>();
    //error boundary check
    if (s == null || s.length() == 0) return res;
    dfs(res, new ArrayList<>(), s);
    return res;
  }
  public void dfs(List<List<String>> res, List<String> list, String s) {
    // recursive endpoint
    if (s.length() == 0) {
        res.add(new ArrayList<>(list));
        return;
    }
    for (int i = 0; i < s.length(); i++) {
      /**
       * ababc
       * 
       * backtracking and recursive
       * if the input is "aab", check if [0,0] "a" is palindrome. 
       * then check [0,1] "aa", then [0,2] "aab".
       * 
       * While checking [0,0], the rest of string is "babc", 
       * use babca as input to make a recursive call.
       * 
       * While checking [0,1], the rest of string is "abc", 
       * use abca as input to make a recursive call.
       * 
       * 
       * How to define a correct answer?
       * Think about DFS, if the current string to be checked (Palindrome) contains the last position, 
       * in this case "c", this path is a correct answer, otherwise, it's a false answer.
       */
      if (isPalindrome(s.substring(0, i + 1))) {
          list.add(s.substring(0, i + 1));
          dfs(res, list, s.substring(i + 1));
          list.remove(list.size() - 1);
      }
    }
  }

  // helper to check if string is palindrome
  public boolean isPalindrome(String s) {
      for (int i = 0; i < s.length() / 2; i++) {
          if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
              return false;
          }
      }
      return true;
      // return s.equals(new StringBuffer(s).reverse().toString());
  }
}