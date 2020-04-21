package leetcode.medium;

import java.util.HashMap;

/**
 * Created by Ran on Apr 20, 2020.
 */
public class LongestSubstringWithoutRepeatingCharacters {
    /**
     * LC 3. Longest Substring Without Repeating Characters (Microsoft)
     * Examples:

     Given "abcabcbb", the answer is "abc", which the length is 3.

     Given "bbbbb", the answer is "b", with the length of 1.

     Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring,
     "pwke" is a subsequence and not a substring.


		 If a substring s{i,j} from index i to j - 1 is already checked to have no duplicate characters. 
		 We only need to check if s[j] is already in the substring s{i,j}

		 A sliding window is an abstract concept commonly used in array/string problems. 
		 A window is a range of elements in the array/string which usually defined by the start and end indices, 
		 i.e. [i, j) (left-closed, right-open). 
		 
		 A sliding window is a window "slides" its two boundaries to the certain direction. 
		 For example, if we slide [i, j) to the right by 1 element, then it becomes [i+1, j+1) (left-closed, right-open).

		 Back to our problem. We use HashSet to store the characters in current window [i, j) (j = i initially). 
		 Then we slide the index j to the right. If it is not in the HashSet, we slide j further. 
		 Doing so until s[j] is already in the HashSet. 
		 At this point, we found the maximum size of substrings without duplicate characters start with index i. If we do this for all i, we get our answer.

		 we could define a mapping of the characters to its index. Then we can skip the characters immediately when we found a repeated character.
		 The reason is that if s[j] have a duplicate in the range [i, j) with index j'j
		 we don't need to increase i little by little. We can skip all the elements in the range [i, j'] and let i to be (j' + 1) directly.

     time : O(n)
     space : O(n)
     * @param s
     * @return
     */

    public static int lengthOfLongestSubstring(String s) {
				// step 1: error boundary check
				if(s == null || s.length() == 0) {
					return 0;
				}
				int result = 0;
				HashMap<Character, Integer> map = new HashMap<>();
				for (int windowLength = 0, slidingLength = 0; windowLength < s.length(); windowLength++) {
					// step 3 (important!): if the incoming character already existed in HashMap
					// skip all the elements in the range "map.get(s.charAt(windowLength)) + 1"
					if (map.containsKey(s.charAt(windowLength))) {
						slidingLength = Math.max(slidingLength, map.get(s.charAt(windowLength)) + 1);
					}
					// step 2: put each character into the HashMap with index
					map.put(s.charAt(windowLength), windowLength);
					// windowLength - slidingLength + 1 is always the next sliding window length of non-duplicate string
					result = Math.max(result, windowLength - slidingLength + 1);
				}
				return result;
		}
		
		/**
		 * a b c a a b c d b
		 * -----
		 *    
		 * 
		 * HashMap			windowLength		slidingLength									result									abcaabcdb		slidingWindow
		 * a, 0					0								0															max(0, 0 - 0 + 1) = 1		a						[0,1)
		 * 
		 * b, 1					1								0															max(1, 1 - 0 + 1) = 2		ab					[0,2)
		 * 
		 * c, 2					2								0															max(2, 2 - 0 + 1) = 3		abc					[0,3)
		 * 
		 * a, 0->3			3								max(0, map(a) -> 0 + 1) = 1		max(3, 3 - 1 + 1) = 3		 bca				[1,4)					slide 1 - 0 = 1 length from previous sliding window
		 * 
		 * a, 3->4			4								max(1, map(a) -> 3 + 1) = 4		max(3, 4 - 4 + 1) = 3       a				[5,5)					slide 4 - 1 = 3 length from previous sliding window
		 * 
		 * b, 1->5			5								max(4, map(b) -> 1 + 1) = 4		max(3, 5 - 4 + 1) = 3 			ab			[5,6)
		 * 
		 * c, 2->6			6  							max(4, map(c) -> 2 + 1) = 4		max(3, 6 - 4 + 1) = 3				abc			[5,7)
		 * 
		 * d, 7					7								4															max(3, 7 - 4 + 1) = 4				abcd		[5,8)
		 * 
		 * b, 5 ->8			8								max(4, map(b) -> 5 + 1) = 6		max(4, 8 - 6 + 1) = 4				  cdb 	[7,9)					slide 6 - 4 = 2 length from previous sliding window
		 */
}