package leetcode.easy;

/**
 * Created by Ran on Mar 8, 2020.
 */
public class ReverseInteger {

    /**
     * LC 7. Reverse Integer
     * Reverse digits of an integer.

     Example1: x = 123, return 321
     Example2: x = -123, return -321
     Example3: x = 120, return 21

     Notes: Assume we are dealing with an environment which could only store integers within the 32-bit 
     signed integer range: [−231,  231 − 1]. For the purpose of this problem, 
     assume that your function returns 0 when the reversed integer overflows.

     int :
     -2147483648 ~ 2147483647
     time : O(n);
     space : O(1);
     * @param x
     * @return
     */

    public static int reverse(int x) {
        // step 1: declare and initialize result
        // note in Java int is 32 bit and long is 64 bit
        // define as long for boundary check
        long res = 0;
        // step 2(important!): think about 123 -> 321
        // everytime operation of % 10 we get the number from high digit to low digit in result
        // 123 % 10 = 3, original (123) can still be divided by 10, so our temp result is 3 and continue 123 / 10 = 12
        // 12(from above modified original value) % 10 = 2, (12) can still be divided by 10, so our temp result become 3 (from above) * 10 + 2 = 32 and continue 12 / 10 = 1
        // 1(from above modified original value) % 10 = 1, (1) can still be divided by 10, so our temp result become 32 (from above) * 10 + 1 = 321 and continue 1 / 10 = 0
        // modified original value is 0, return our temp result 321
        // 321 = 3 * 10 * 10 + 2 * 10 + 1
        //     = (123%10) * 10 * 10 + (123/10%10) * 10 + (123/10/10%10)
        while(x != 0) {
            res = res * 10 + x % 10;
            x = x / 10;
            // step 3: error boundary with 32 bit from notes
            if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) return 0;
        }
        return (int)res;
    }
}
