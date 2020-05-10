package leetcode.debugging;

import java.util.List;

import leetcode.easy.FindCeilingFloor;
import leetcode.hard.WordSearchII;
import leetcode.medium.*;
import leetcode.utils.TreeNode;

public class Main {
	public static void main (String[] args) { 
		// LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring('abcaabcdb');
		// LongestPalindromicSubstring.longestPalindrome('cbbd');
		// SortThreeUniqueNumbers.sortThreeUniqueNumbers(new int[]{2,0,2,1,1,0});
		// FindCeilingFloor.findCeilingFloor(BuildBST(), 5);
		List<String> result = WordSearchII.findWords(
			buildBoard(), new String[]{"oath","pea","eat","rain"});
	}

	private static char[][] buildBoard() {
		return new char[][]{
			new char[]{'o','a','a','n'},
			new char[]{'e','t','a','e'},
			new char[]{'i','h','k','r'},
			new char[]{'i','f','l','v'}
		};
	}

	private static TreeNode BuildBST() {
		TreeNode node4 = new TreeNode(10);
		TreeNode node3 = new TreeNode(3);
		TreeNode node2 = new TreeNode(6, node3, node4);
		TreeNode node1 = new TreeNode(1);
		TreeNode root = new TreeNode(2, node1, node2);

		return root;
	}
	
}