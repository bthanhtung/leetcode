package ValidateBinarySearchTree;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 98. Validate Binary Search Tree — https://leetcode.com/problems/validate-binary-search-tree/
 * Is the tree a valid BST: every node's left subtree has only smaller keys, right subtree only larger keys?
 * Input: [2,1,3] -> true · [5,1,4,null,null,3,6] -> false (3 is in the right subtree of 5)
 * Explanation: README.md
 */
public class ValidateBinarySearchTree {
    public static void main(String[] args) {
        System.out.println(isValidBST(TreeNode.of(2, 1, 3)));                         // true
        System.out.println(isValidBST(TreeNode.of(5, 1, 4, null, null, 3, 6)));       // false
    }

    /* Approach 1 - pass the open interval every node must lie in. long bounds handle Integer.MIN/MAX keys. O(n). */
    public static boolean isValidBST(TreeNode root) {
        return valid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean valid(TreeNode n, long low, long high) {
        if (n == null) return true;
        if (n.val <= low || n.val >= high) return false;
        return valid(n.left, low, n.val) && valid(n.right, n.val, high);
    }

    /* Approach 2 - iterative inorder traversal; a BST yields strictly increasing values. */
    public static boolean isValidBSTInorder(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Long prev = null;
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (prev != null && cur.val <= prev) return false;
            prev = (long) cur.val;
            cur = cur.right;
        }
        return true;
    }
}
