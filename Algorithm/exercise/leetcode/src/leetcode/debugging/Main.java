package leetcode.debugging;

import leetcode.easy.FindCeilingFloor;
import leetcode.medium.*;
import leetcode.utils.TreeNode;

public class Main {
	public static void main (String[] args) { 
		// LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring("abcaabcdb");
		// LongestPalindromicSubstring.longestPalindrome("cbbd");
		// SortThreeUniqueNumbers.sortThreeUniqueNumbers(new int[]{2,0,2,1,1,0});
		var root = BuildBST();
		FindCeilingFloor.findCeilingFloor(root, 5);
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