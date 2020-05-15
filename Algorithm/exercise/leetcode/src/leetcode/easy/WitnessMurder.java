package leetcode.easy;

/**
 * Created by Ran on May 14, 2020.
 */
public class WitnessMurder {
    /**
     * LC. ? Witness of The Tall People (Google)
     * 
     * There are n people lined up, and each have a height represented as an integer. A murder has happened right in front of them, and only people who are taller than everyone in front of them are able to see what has happened. How many witnesses are there?
        Example:
        Input: [3, 6, 3, 4, 1]  
        Output: 3
        Explanation: Only [6, 4, 1] were able to see in front of them.
        #
        #
        # #
       ####
       ####
       #####
       36341                                 x (murder scene)
        '''
     */
    public int witnessMurder(int[] heights) {
       /**
        * 
         Traverse the array from end to start
         when iterator found higher value, total + 1, otherwise keep going
        */
        int maxHeight = Integer.MIN_VALUE;
        int result = 0;
        for (int i = heights.length - 1; i > -1; i --) {
          if (heights[i] > maxHeight) {
            result += 1;
          }
          maxHeight = Integer.max(heights[i], maxHeight);
        }
        return result;
    }
}