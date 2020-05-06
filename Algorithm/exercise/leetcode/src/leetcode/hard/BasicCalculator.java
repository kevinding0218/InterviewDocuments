package leetcode.hard;

import java.util.Stack;

/**
 * Created by Ran on May 5, 2020.
 */
public class BasicCalculator {
    /**
     * LC. 224 Basic Calculator (Google)
     * Implement a basic calculator to evaluate a simple expression string.

        The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

        Example 1:

        Input: "1 + 1"
        Output: 2
        Example 2:

        Input: " 2-1 + 2 "
        Output: 3
        Example 3:

        Input: "(1+(4+5+2)-3)+(6+8)"
        Output: 23

        Algorithm
        1) Iterate the expression string one character at a time. 
        Since we are reading the expression character by character, we need to be careful when we are reading digits and non-digits.
        2) The operands could be formed by multiple characters. 
        A string "123" would mean a numeric 123, which could be formed as: 123 >> 120 + 3 >> 100 + 20 + 3. 
        Thus, if the character read is a digit we need to form the operand by multiplying 10 to the previously formed continuing operand and adding the digit to it.
        3) Whenever we encounter an operator such as '+'' or '-'' we first evaluate the expression to the left and then save this sign for the next evaluation.
        4) If the character is an opening parenthesis '('', we just push the result calculated so far and the sign on to the stack (the sign and the magnitude) 
        and start a fresh as if we are calculating a new expression.
        5) If the character is a closing parenthesis '')'', we first calculate the expression to the left. 
        The result from this would be the result of the expression within the set of parenthesis that just concluded. 
        This result is then multiplied with the sign, if there is any on top of the stack. 
        Remember we saved the sign on top of the stack when we had encountered an open parenthesis 
        This sign is associated with the parenthesis that started then, thus when the expression ends or concludes, 
        we pop the sign and multiply it with result of the expression. It is then just added to the next element on top of the stack.

     * 
     */
    public int calculate(String s) {
        /**
         *  For example: expression (6 + 15 - 4) + 2
                                    01 2 34 5 67 8 9                    
            i   s[i]    res     sign    num     stack(bottom -> top)    after res        sign
            0   (       0       1               0 1                     0                1      
            1   6       0       1       6       0 1                     0 + 6 * 1 = 6    1
            2   +       6       1       X       0 1                     6                1
            3   1       6       1       15(I++) 0 1                     6 + 15 * 1 = 21  1
            5   -       21      -1      X       0 1                     21               -1
            6   4       21      -1      4       0 1                     21 + 4 * -1 = 17 -1
            7   )       17      -1      X       1 pop, 0 pop -> empty   17 * 1 + 0 = 17  -1
            8   +       17      1       X       empty                   17               1
            9   2       17      1       2       empty                   17 + 2 * 1 = 19  1
            return 19
         * 
         */
        Stack<Integer> stack = new Stack<>();
        int sign = 1;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            // If character is number, continue to next digit and check if its number or not
            // If it's not number, then result = previous result + most recent sign * current number
            // e.g A + 5 = A + 5 * 1
            // If it's number, then continue calcuate the number
            // e.g 12 = 1 * 10 + 2; 123 = (1 * 10 + 2)*10 + 3
            if (Character.isDigit(s.charAt(i))) {
                int num = s.charAt(i) - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                res += num * sign;
            } else if (s.charAt(i) == '+') {
                // save current sign as 1 so it can be used as next calculation
                sign = 1;
            } else if (s.charAt(i) == '-') {
                // save current sign as -1 so it can be used as next calculation
                sign = -1;
            } else if (s.charAt(i) == '(') {
                // push current result into stack first, then previous sign
                // e.g 
                // A + (2 +... -> push A into stack then 1 (from last sign), reset current result to 0
                // A - (2 +... -> push A into stack then -1 (from last sign), reset current result to 0
                stack.push(res);
                stack.push(sign);
                res = 0;
                sign = 1;
            } else if (s.charAt(i) == ')') {
                // calcualte current result = whatever calculated inside the parenthese * last sign before ( + last result before (
                // e.g
                // prevResult + (currentResult) => currentResult * 1 [last sign before '('] + prevResult [last result before '(']
                // prevResult - (currentResult) => currentResult * -1 [last sign before '('] + prevResult [last result before '('] => prevResult - currentResult
                res = res * stack.pop() + stack.pop();
            }
        }
        return res;
    }
}