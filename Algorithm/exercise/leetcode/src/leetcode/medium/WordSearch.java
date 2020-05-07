package leetcode.medium;

/**
 * Created by Ran on May 7, 2020.
 */
public class WordSearch {
  /**
   * LC. 79 (Amazon)
   * 
   * Given a 2D board and a word, find if the word exists in the grid.

    The word can be constructed from letters of sequentially adjacent cell, 
    where "adjacent" cells are those horizontally or vertically neighboring. 
    The same letter cell may not be used more than once.

    Example:

    board =
    [
      ['A','B','C','E'],    [00, 01, 02, 03]
      ['S','F','C','S'], -> [10, 11, 12, 13]    
      ['A','D','E','E']     [20, 21, 22, 23]
    ]

    Given word = "ABCCED", return true.
    Given word = "SEE", return true.
    Given word = "ABCB", return false.
    

    Constraints:

    board and word consists only of lowercase and uppercase English letters.
    1 <= board.length <= 200
    1 <= board[i].length <= 200
    1 <= word.length <= 10^3
   */
  public boolean exist(char[][] board, String word) {
    // i loop through top to bottom, j loop through left to right
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (exist(board, i, j, word, 0)) {
                return true;
            }
        }
    }
    return false;
  }

  private boolean exist(char[][] board, int i, int j, String word, int start) {
    // boundry check for word iterator
    if (start >= word.length()) return true;
    // boundry check for matrix iterator
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;
    // when we find board[i][j] equals to start character of word, 
    // keep loop through its four direction for matching next character
    if (board[i][j] == word.charAt(start++)) {
        char c = board[i][j];
        board[i][j] = '#';
        // what if question only allows top -> bottom, left -> right?
        // remove exist(board, i - 1, j, word, start) || exist(board, i, j - 1, word, start)
        boolean res = exist(board, i + 1, j, word, start) ||
                exist(board, i - 1, j, word, start) ||
                exist(board, i, j + 1, word, start) ||
                exist(board, i, j - 1, word, start);
        board[i][j] = c;
        return res;
    }
    return false;
  }
}