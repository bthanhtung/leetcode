package DiameterOfBinaryTree;

/*
 * 543. Diameter of Binary Tree — https://leetcode.com/problems/diameter-of-binary-tree/
 * The diameter is the number of edges on the longest path between any two nodes (it may not pass the root).
 * Input: [1,2,3,4,5] -> 3 (4-2-1-3 or 5-2-1-3) · [1,2] -> 1
 * Explanation: README.md
 */
public class DiameterOfBinaryTree {
    public static void main(String[] args) {
        System.out.println(diameterOfBinaryTree(TreeNode.of(1, 2, 3, 4, 5)));         // 3
        System.out.println(diameterOfBinaryTreeNaive(TreeNode.of(1, 2)));             // 1
    }

    /* Approach 1 - at every node, height(left) + height(right); recomputes heights. Time O(n^2) worst case. */
    public static int diameterOfBinaryTreeNaive(TreeNode root) {
        if (root == null) return 0;
        int through = height(root.left) + height(root.right);
        return Math.max(through, Math.max(diameterOfBinaryTreeNaive(root.left), diameterOfBinaryTreeNaive(root.right)));
    }

    private static int height(TreeNode node) {
        return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
    }

    /*
     * Approach 2 - one postorder DFS: each call returns its height and updates the best path through this node
     * (left height + right height). Time O(n), space O(h).
     */
    public static int diameterOfBinaryTree(TreeNode root) {
        int[] best = new int[1];
        depth(root, best);
        return best[0];
    }

    private static int depth(TreeNode node, int[] best) {
        if (node == null) return 0;
        int left = depth(node.left, best);
        int right = depth(node.right, best);
        best[0] = Math.max(best[0], left + right);       // the longest path that bends at this node
        return 1 + Math.max(left, right);                // what the parent can extend
    }
}
