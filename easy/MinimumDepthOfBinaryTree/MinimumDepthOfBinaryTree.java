package MinimumDepthOfBinaryTree;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 111. Minimum Depth of Binary Tree — https://leetcode.com/problems/minimum-depth-of-binary-tree/
 * Return the minimum depth: the number of nodes on the shortest path from the root down to a leaf.
 * Input: [3,9,20,null,null,15,7] -> 2 · [2,null,3,null,4,null,5,null,6] -> 5
 * Explanation: README.md
 */
public class MinimumDepthOfBinaryTree {
    public static void main(String[] args) {
        System.out.println(minDepth(TreeNode.of(3, 9, 20, null, null, 15, 7)));       // 2
    }

    /* Approach 1 - BFS level by level; the first leaf met is the shallowest. Stops early. */
    public static int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        for (int depth = 1; ; depth++) {
            for (int size = queue.size(); size > 0; size--) {
                TreeNode n = queue.poll();
                if (n.left == null && n.right == null) return depth;
                if (n.left != null) queue.add(n.left);
                if (n.right != null) queue.add(n.right);
            }
        }
    }

    /* Approach 2 - recursion; a missing child is not a leaf, so use the other side. */
    public static int minDepthRecursive(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return 1 + minDepthRecursive(root.right);
        if (root.right == null) return 1 + minDepthRecursive(root.left);
        return 1 + Math.min(minDepthRecursive(root.left), minDepthRecursive(root.right));
    }
}
