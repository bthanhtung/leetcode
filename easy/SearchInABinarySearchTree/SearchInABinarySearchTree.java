package SearchInABinarySearchTree;

import com.tungdadev.common.TreeNode;

/*
 * 700. Search in a Binary Search Tree — https://leetcode.com/problems/search-in-a-binary-search-tree/
 * Given the root of a BST and a value, return the subtree rooted at the node with that value, or null.
 * Input: root = [4,2,7,1,3], val = 2 -> [2,1,3] · val = 5 -> []
 * Explanation: README.md
 */
public class SearchInABinarySearchTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(4, 2, 7, 1, 3);
        System.out.println(TreeNode.toList(searchBST(root, 2)));             // [2, 1, 3]
        System.out.println(TreeNode.toList(searchBSTRecursive(root, 5)));    // []
    }

    /* Approach 1 - recursion: go left if val is smaller, right if larger. Time O(h), space O(h). */
    public static TreeNode searchBSTRecursive(TreeNode root, int val) {
        if (root == null || root.val == val) {
            return root;
        }
        return val < root.val ? searchBSTRecursive(root.left, val) : searchBSTRecursive(root.right, val);
    }

    /* Approach 2 - the same walk with a loop. Time O(h), space O(1). */
    public static TreeNode searchBST(TreeNode root, int val) {
        while (root != null && root.val != val) {
            root = val < root.val ? root.left : root.right;
        }
        return root;
    }
}
