package leetcode.easy;

/**
 * Created by Ran on Mar 8, 2020.
 */
public class RomanToInteger {
    /**
     * 13. Roman to Integer
     * Given a roman numeral, convert it to an integer.
     * 
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

        Symbol       Value
        I             1
        V             5
        X             10
        L             50
        C             100
        D             500
        M             1000
        For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.

        Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

        I can be placed before V (5) and X (10) to make 4 and 9. 
        X can be placed before L (50) and C (100) to make 40 and 90. 
        C can be placed before D (500) and M (1000) to make 400 and 900.
        Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.

        Example1: III -> 3
        Example2: IV -> 4
        Example3: IX -> 9
        Example4: LVIII -> 58 (L = 50, V= 5, III = 3)
        Example5: MCMXCIV -> 1994 (M = 1000, CM = 900, XC = 90 and IV = 4)

        规律：左边的数字如果小于右边的数字 = 右 - 左

     time : O(n);
     space : O(1);
     * @param s
     * @return
     */

    // reverse string and iterate through start to end, normal add/substract
    public static int romanToInt1(String s) {
        // step 2: declare and initialize result
        int res = 0;
        // step 3 (important!): error boundary check
        if (null == s || s.isEmpty()) return res;
        // reverse string e.g: IV -> VI or loop from end to start
        s = new StringBuilder(s).reverse().toString();
        // we initial result from left most character
        res = symbolValueConverter(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            // step 5 (important!): conditional case
            // when s[i] < s[i - 1], e.g 
            // IV(4) => VI => s[1] < s[0] -> V(5) - I(1) = 4
            // MCM(1900) => MCM -> M(1000) - C(100) + M(1000) = 1900
            int cur = symbolValueConverter(s.charAt(i));
            int prev = symbolValueConverter(s.charAt(i - 1));
            if (cur < prev) {
                res -= prev;
            } else {
                // step 4: normal case if s[i] >= s[i-1], res = s[i-1] + s[i]
                // e.g II(2) -> I(1) + I(1), IX(6) -> I(1) + X(5), etc
                res += cur;
            }
        }
        return res;
    }

    // iterator through end to start, normal add/substract
    public static int romanToInt2(String s) {
        // step 2: declare and initialize result
        int res = 0;
        // step 3 (important!): error boundary check
        if (null == s || s.isEmpty()) return res;
        // we initial result from left most character
        int length = s.length();
        res = symbolValueConverter(s.charAt(length - 1));
        for (int i = length - 2; i >= 0; i--) {
            // step 5 (important!): conditional case
            // when s[i] < s[i + 1]->current < prev iterator value e.g 
            // IV(4) => cur|s[1]V > left|s[0]|I -> res|V(5) - cur|I(1) = 4
            // MCD(1400) => -> res|D(500) - i=1|C(100) + i=0|M(1000) = 1400
            int cur = symbolValueConverter(s.charAt(i));//I 1
            int prev = symbolValueConverter(s.charAt(i + 1));// V 5
            if (cur < prev) {
                res -= cur;
            } else {
                // step 4: normal case if s[i] >= s[i-1], res = s[i-1] + s[i]
                // e.g III(2) -> res|s[2]|I(1) + s[1]|I(1) + s[0], 
                // IX(6) -> res|X(5) + s[0]|I(1), etc
                res += cur;
            }
        }
        return res;
    }

    // iterator through start to end, substract twice current conditionallyz`
    public static int romanToInt3(String s) {
        // step 2: declare and initialize result
        int res = 0;
        // step 3 (important!): error boundary check
        if (null == s || s.isEmpty()) return res;
        // because Roman numeral can be calculated from left side by add or substract
        // we initial result from left most character
        res = symbolValueConverter(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            // step 5 (important!): conditional case
            // when s[i-1] < s[i], e.g 
            // IV(4) -> V(5) - I(1) 
            // => here V(5) == <I(1) res> + V(5) - <I(1) prev>,
            // => I(1) == I(1)
            // IV(4) => res + V(5)- I(1)  - I(1)  = res + V(5) - 2*I(1)
            // MCM(1900) -> res = M(1000) + C(100) = 1100 
            // => res + M(1000) - C(100) - C(100) = 1100 + 1000 - 100 - 100 = 1900
            int cur = symbolValueConverter(s.charAt(i));
            int prev = symbolValueConverter(s.charAt(i - 1));
            if (cur > prev) {
                res = res + cur - prev - prev;
                // res += cur - 2 * prev;
            } else {
                // step 4: normal case if s[i-1] >= s[i], res = s[i-1] + s[i]
                // e.g II(2) -> I(1) + I(1), IX(6) -> I(1) + X(5), etc
                res = res + cur;
            }
        }
        return res;
    }

    // step 1: create a helper method for mapping purpose of Space O(1)
    private static int symbolValueConverter(char c) {
        int res = 0;
        switch(c) {
            case 'I' : return 1;
            case 'V' : return 5;
            case 'X' : return 10;
            case 'L' : return 50;
            case 'C' : return 100;
            case 'D' : return 500;
            case 'M' : return 1000;
        }
        return res;
    }
}