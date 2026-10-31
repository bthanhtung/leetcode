package RangeSumOfBST;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 938. Range Sum of BST — https://leetcode.com/problems/range-sum-of-bst/
 * Given the root of a binary search tree and two integers low and high, return the sum of all node values in [low, high].
 * Input: root = [10,5,15,3,7,null,18], low = 7, high = 15 -> 32 (7 + 10 + 15)
 * Explanation: README.md
 */
public class RangeSumOfBST {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(10, 5, 15, 3, 7, null, 18);
        System.out.println(rangeSumBST(root, 7, 15));            // 32
        System.out.println(rangeSumBSTIterative(root, 7, 15));   // 32
    }

    /* Approach 1 - DFS that skips subtrees which cannot contain values in range (BST property). Time O(n) worst, space O(h). */
    public static int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }
        if (root.val < low) {
            return rangeSumBST(root.right, low, high);     // the whole left subtree is even smaller
        }
        if (root.val > high) {
            return rangeSumBST(root.left, low, high);      // the whole right subtree is even larger
        }
        return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
    }

    /* Approach 2 - the same pruning with an explicit stack (no recursion). Time O(n), space O(h). */
    public static int rangeSumBSTIterative(TreeNode root, int low, int high) {
        int sum = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.val >= low && node.val <= high) sum += node.val;
            if (node.left != null && node.val > low) stack.push(node.left);
            if (node.right != null && node.val < high) stack.push(node.right);
        }
        return sum;
    }

    /* Approach 3 - visit every node, ignore the BST property. Time O(n). Reference for tests. */
    public static int rangeSumAllNodes(TreeNode root, int low, int high) {
        if (root == null) return 0;
        int self = root.val >= low && root.val <= high ? root.val : 0;
        return self + rangeSumAllNodes(root.left, low, high) + rangeSumAllNodes(root.right, low, high);
    }
}
