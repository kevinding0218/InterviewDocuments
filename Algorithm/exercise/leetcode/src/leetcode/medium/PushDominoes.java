package leetcode.medium;

/**
 * Created by Ran on May 11, 2020.
 */
public class PushDominoes {
  /**
   * LC.838 Push Dominoes (Twitter)
   * 
   * . represents that the domino is standing still
    L represents that the domino is falling to the left side
    R represents that the domino is falling to the right side

    Figure out the final position of the dominoes. If there are dominoes that get pushed on both ends, the force cancels out and that domino remains upright.

    Example 1:
    Input:  ..R...L..R.     ||R|||L||R|
    Output: ..RR.LL..RR     ||//|\\||//

    Example 2:
    Input: ".L.R...LR..L.."     |L|R|||LR||L||
    Output: "LL.RR.LLRRLL.."    \\|//|\\//\\||

    Example 3:
    Input: "RR.L"               RR.L
    Output: "RR.L"              //|\
    Explanation: The first domino expends no additional force on the second domino.
    Note:

    0 <= N <= 10^5
    String dominoes contains only 'L', 'R' and '.'

   */
  
   public String pushDominoes(String dominoes) {
      /**
       * Intuition
       * Between every group of vertical dominoes ('.'), we have up to two non-vertical dominoes bordering this group. 
       * Since additional dominoes outside this group do not affect the outcome, 
       * we can analyze these situations individually: there are 9 of them (as the border could be empty). 
       * Actually, if we border the dominoes by 'L' and 'R', there are only 4 cases. 
       * We'll write new letters between these symbols depending on each case.
       * 
       * Algorithm
       * Continuing our explanation, we analyze cases:
       * - Case 1: If we have say "R....R" or "L....L", then it will become "LLLLLL" or "RRRRRR"
       * - Case 2: If we have "R....L", then we will write "RRRLLL", or "RRR.LLL" if we have an odd number of dots. 
       * -         If the initial symbols are at positions i and j, we can check our distance k-i and j-k to decide at position k whether to write 'L', 'R', or '.'.
       * - Case 3: (If we have "L....R" we don't do anything. We can skip this case.)
       * 
       * Time: O(n)
       * Space: O(n)
       */
      int N = dominoes.length();
      int[] indexes = new int[N+2];
      char[] symbols = new char[N+2];
      int len = 1;
      indexes[0] = -1;
      symbols[0] = 'L';

      /**
       * 0 1 2 3 4 5 6 7 8 9 10
       * . . R . . . L . . R .
       * 
       * i        0 2 6 9
       * indexes: 0 2 6 9
       * symbols: L R L R
       * len      1 2 3 4
       * 
       */
      for (int i = 0; i < N; ++i) {
        if (dominoes.charAt(i) != '.') {
            indexes[len] = i;
            symbols[len++] = dominoes.charAt(i);
        }
      }

      /**
       * 
       * 0 1 2 3 4 5 6 7 8 9 10
       * . . R . . . L . . R .
       * 
       * indexes: 0 2 6 9 (10)
       * symbols: L R L R (R)
       * 
       * 0   1 2 3 4 5 6 7 8 9 10
       * (L) . R . . . L . . R (R)
       */
      indexes[len] = N;
      symbols[len++] = 'R';

      char[] ans = dominoes.toCharArray();
      for (int index = 0; index < len - 1; ++index) {
          int i = indexes[index], j = indexes[index+1];
          char x = symbols[index], y = symbols[index+1];
          /**
           * indexes: 0 2 6 9 (10)
           * symbols: L R L R (R)
           * index  i  j   x   y
           * 0      0  2   L   R  this means from index 0 to 2, the format is like Case 3: L...R (x < y), do nothing
           * 1      2  6   R   L  this means from idnex 2 to 6, the format is like Case 2: R...L (x < y), we will write "RRRLLL", or "RRR.LLL" if we have an odd number of dots. 
           * 2      6  9   L   R  this means from index 6 to 9, the format is like Case 3: L...R (x < y), do nothing
           */
          if (x == y) {   // L...L or R...R -> Case 1 -> make it "LLL" or "RRR"
              for (int k = i+1; k < j; ++k)
                  ans[k] = x;
          } else if (x > y) { // R...L -> Case 2  -> make it "RRRLLL" or "RRR.LLL" depends on odd number of dots
              for (int k = i+1; k < j; ++k)
                  ans[k] = k-i == j-k ? '.' : k-i < j-k ? 'R' : 'L';
          }
      }

      return String.valueOf(ans);
  }
}