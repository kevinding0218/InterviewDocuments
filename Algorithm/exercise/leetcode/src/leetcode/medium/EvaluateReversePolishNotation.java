package leetcode.medium;

import java.util.Stack;

/**
 * Created by Ran on May 23, 2020.
 */
public class EvaluateReversePolishNotation {
  /**
   * LC. 150 Evaluate Reverse Polish Notation (Microsoft)
   * Evaluate the value of an arithmetic expression in Reverse Polish Notation.

    Valid operators are +, -, *, /. Each operand may be an integer or another expression.

    Note:

    Division between two integers should truncate toward zero.
    The given RPN expression is always valid. That means the expression would always evaluate 
    to a result and there won't be any divide by zero operation.
    Example 1:
    Input: ["2", "1", "+", "3", "*"]
    Output: 9
    Explanation: ((2 + 1) * 3) = 9

    Example 2:
    Input: ["4", "13", "5", "/", "+"]
    Output: 6
    Explanation: (4 + (13 / 5)) = 6

    Example 3:
    Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
    Output: 22
    Explanation: 
      ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
    = ((10 * (6 / (12 * -11))) + 17) + 5
    = ((10 * (6 / -132)) + 17) + 5
    = ((10 * 0) + 17) + 5
    = (0 + 17) + 5
    = 17 + 5
    = 22
   */

  /**
   * Idea:
   * Keep pushing element to stack if it's a number
   * when meet with operator, popup last second from stack and do the operation
   * then put the result back to stack and keep going
   * 
     time : O(n)
     space : O(n)
   * @param tokens
   * @return
   */
  public int evalRPN(String[] tokens) {
    Stack<Integer> stack = new Stack<>();
    for (String s : tokens) {
        if (s.equals("+")) {
            stack.push(stack.pop() + stack.pop());
        } else if (s.equals("-")) {
            int b = stack.pop();
            int a = stack.pop();
            stack.push(a - b);
        } else if (s.equals("*")) {
            stack.push(stack.pop() * stack.pop());
        } else if (s.equals("/")) {
            int b = stack.pop();
            int a = stack.pop();
            stack.push(a / b);
        } else {
            stack.push(Integer.parseInt(s));
        }
    }
    return stack.pop();
  }
}