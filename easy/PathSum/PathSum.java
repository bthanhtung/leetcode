package PathSum;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 112. Path Sum — https://leetcode.com/problems/path-sum/
 * Return true if the tree has a root-to-leaf path whose values add up to targetSum.
 * Input: [5,4,8,11,null,13,4,7,2,null,null,null,1], 22 -> true (5+4+11+2) · [1,2,3], 5 -> false · [], 0 -> false
 * Explanation: README.md
 */
public class PathSum {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1);
        System.out.println(hasPathSum(root, 22));             // true
        System.out.println(hasPathSumIterative(root, 26));    // true (5+8+13)
    }

    /* Approach 1 - subtract the node's value from the target on the way down; at a leaf, check what remains. Time O(n). */
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        int rest = targetSum - root.val;
        if (root.left == null && root.right == null) return rest == 0;
        return hasPathSum(root.left, rest) || hasPathSum(root.right, rest);
    }

    /* Approach 2 - DFS with a stack of (node, sum so far). Time O(n). */
    public static boolean hasPathSumIterative(TreeNode root, int targetSum) {
        if (root == null) return false;
        record Item(TreeNode node, int sum) {}
        Deque<Item> stack = new ArrayDeque<>();
        stack.push(new Item(root, root.val));
        while (!stack.isEmpty()) {
            Item it = stack.pop();
            TreeNode n = it.node;
            if (n.left == null && n.right == null && it.sum == targetSum) return true;
            if (n.right != null) stack.push(new Item(n.right, it.sum + n.right.val));
            if (n.left != null) stack.push(new Item(n.left, it.sum + n.left.val));
        }
        return false;
    }
}
