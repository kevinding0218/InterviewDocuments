package leetcode.easy;

import java.util.Stack;

/**
 * Created by Ran on Mar 9, 2020.
 */
public class ValidParentheses {
  /**
   * LC 20. Valid Parentheses (Uber)
   * Given a string containing just the characters '(', 
   * ')', '{', '}', '[' and ']', determine if the input string is valid.
   * 
   * The brackets must close in the correct order, "()" and "()[]{}" are all valid
   * but "(]" and "([)]" are not.
   * 
   * case1 : ()[]{} true :
   * 
   * case2 : ([)] false :
   * 
   * case3 : } false :
   * 
   * time : O(n); space : O(n);
   * 
   * @param s
   * @return
   */
  public static boolean isValid(String s) {
    // step 1: error boundary check
    if (null == s || s.length() == 0)
      return true;
    // step 2 (important!): using a stack to store the value
    Stack<Character> stack = new Stack<>();
    for (Character ch : s.toCharArray()) {
      // for each character on the left mapping, store its right mapping into stack
      if (ch == '(') {
        stack.push(')');
      } else if (ch == '[') {
        stack.push(']');
      } else if (ch == '{') {
        stack.push('}');
      } else {
        // if next character is not left mapping, whatever at top of the stack should
        // be its right mapping, otherwise it's not parantheses
        // case: ()[]{} -> stack ) => ')' popup ')', stack ) => ']' popup ']',stack } =>
        // '}' popup '}', stack is empty
        // ([)] -> stack ) ] => ')' popup ']' return false
        // {[]} -> stack: } ] => ']' popup ']', '}' popup '}', stack is empty
        if (stack.isEmpty() || stack.pop() != ch) {
          return false;
        }
      }
    }
    // if stack still left any element, it's not a valid parentheses
    // valid parenthese should empty the stack because all paris are matched and
    // popuped before
    return stack.isEmpty();
  }
}
