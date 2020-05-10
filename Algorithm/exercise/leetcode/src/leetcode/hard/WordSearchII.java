package leetcode.hard;

import java.util.ArrayList;
import java.util.List;

import leetcode.utils.TrieNode;

/**
 * Created by Ran on May 7, 2020.
 */
public class WordSearchII {
  /**
   * LC.212 Word Search II
   * Given a 2D board and a list of words from the dictionary, find all words in the board.

    Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. 
    The same letter cell may not be used more than once in a word.

    Example:

    Input: 
    board = [
      ['o','a','a','n'],
      ['e','t','a','e'],
      ['i','h','k','r'],
      ['i','f','l','v']
    ]
    words = ["oath","pea","eat","rain"]

    Output: ["eat","oath"]
     
    time : O(m * n * TrieNode)
    space : TrieNode

   * @param board
   * @param words
   * @return
   */
  public static List<String> findWords(char[][] board, String[] words) {
    List<String> res = new ArrayList<>();
    // e.g TrieNode for 'eat' would be
    // ****e***...***
    //      a***...***
    //      ***...t...***
    //            word: eat
    TrieNode root = buildTrie(words);
    // i loop through top to bottom, j loop through left to right
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs(board, i, j, root, res);
        }
    }
    return res;
  }

  public static void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
    // boundry check
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
    char c = board[i][j];
    // boundry check
    if (c == '#' || p.next[c - 'a'] == null) return;
    p = p.next[c - 'a'];
    if (p.word != null) {
        res.add(p.word);
        p.word = null;
    }
    // when we find board[i][j] equals to start character of word, 
    // we mark current board[i][j] of '#' so that next time when dfs meet at '#', 
    // just return because it's already been visited and it's a loop
    // keep loop through its four direction for matching next character
    board[i][j] = '#';
    dfs(board, i - 1, j, p, res);
    dfs(board, i + 1, j, p, res);
    dfs(board, i, j + 1, p, res);
    dfs(board, i, j - 1, p, res);
    board[i][j] = c;
  }

  public static TrieNode buildTrie(String[] words) {
    TrieNode root = new TrieNode();
    for (String word : words) {
        TrieNode p = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (p.next[i] == null) {
                p.next[i] = new TrieNode();
            }
            p = p.next[i];
        }
        p.word = word;
    }
    return root;
  }
}
