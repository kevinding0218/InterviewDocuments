package leetcode.medium;

/**
 * Created by Ran on Apr 21, 2020.
 */
public class LongestPalindromicSubstring {
	/**
	 * LC 5. Longest Palindromic Substring (Twitter)
	 * Given a string s, find the longest palindromic substring in s.
	 * You may assume that the maximum length of s is 1000.

		Example:

		Input: "babad"

		Output: "bab"

		Note: "aba" is also a valid answer.
		Example:

		Input: "cbbd"

		Output: "bb"

		time : O(n^2) 
		space : O(1)	abbcb
		* @param s
		* @return
		*/
	static String res = "";
	public static String longestPalindrome(String s) {
			// step 1: error boundary check
			// return if string length less than 2
			if (s == null || s.length() < 2) return s;
			// step 1.5 (optional!): if s equals exactly same as its reverse, then s itself would be the longest palindromic
			if (s == new StringBuilder(s).reverse().toString()) return s;
			// step 2 (important!): consider all possible center from begining in two cases as 
			// 1) as [current]
			// 2) between [current]|[current+1]
			// here ignore head and tail because we're working on case where string length from 3
			for (int i = 1; i < s.length() - 1; i++) {
				// all possible as [current]
				helper(s, i, i);
				// all possible for middle as between [current]|[current+1]
				helper(s, i, i + 1);
			}
			return res;
	}
	private static void helper(String s, int left, int right) {
		// step 3 (important!): expand from `middle` 
		// which would be either current (left == right)
		// or the center in between [current]|[current+1]
		while (left == right || (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right))) {
				left--;
				right++;
		}
		// step 4 (important!): while the condition failed, retrieve the last palindromic string
		// here we use left + 1 because we need to retrieve last successful palindromic that current left is a failed case
		// here substring will take characters between left + 1 and right, so no need to do right - 1
		String cur = s.substring(left + 1, right);
		// step 5: assign to result if it's greater than result
		if (cur.length() > res.length()) {
				res = cur;
		}
	}

	/**
	 * 
	 * We observe that a palindrome mirrors around its center. 
	 * Therefore, a palindrome can be expanded from its center, and there are only 2n - 12n−1 such centers.

	 * You might be asking why there are 2n - 1 but not n centers? 
	 * The reason is the center of a palindrome can be in between two letters. 
	 * Such palindromes have even number of letters (such as "abba") and its center are between the two 'b's.

	 * abbcb
	 * 01234
	 * 								left		right		center		cur							res			note
	 * help(0, 0)	-> 	0				0				a																	take 'a' as center
	 * 								-1			1				(x)				sub(0,1)~a			a
	 * help(0, 1)	->	0				1				ab(x)			sub(1,1)~""			a				take middle between 'ab' as center
	 * ---------------------------------------------------------------------
	 * help(1, 1)	->	1				1				b																	take 'b' as center
	 * 								0				2				abb(x)		sub(1,2)~b			a
	 * help(1, 2)	->	1				2				bb																take middle between 'bb' as center
	 * 								0				3				abbc(x)		sub(1,3)~bb			bb
	 * ---------------------------------------------------------------------
	 * help(2, 2)	->	2				2				b																	take 'b' as center
	 * 								1				3				bbc(x)		sub(2,3)~b			bb
	 * help(2, 3)	->	2				3				bc				sub(3,3)~""			bb			take middle between 'bc' as center
	 * ---------------------------------------------------------------------
	 * help(3, 3)	->	3				3				c																	take 'b' as center
	 * 								2				4				bcb
	 * 								1				5				abbcb(x)	sub(2,5)~bcb		bcb
	 * help(3, 4)	->	3				4				cb				sub(4,4)~""			bcb			take middle between 'cb' as center
	 * ---------------------------------------------------------------------
	 * help(4, 4)	->	4				4				b																	take 'b' as center
	 * 								3				5				(x)				sub(4,5)~b			bcb
	 * help(4, 5)	->	4				5				(x)				sub(5,5)~""			bcb	(return)
	 * ---------------------------------------------------------------------
	 */
}