package BinaryTreeMaximumPathSum;

/*
 * 124. Binary Tree Maximum Path Sum — https://leetcode.com/problems/binary-tree-maximum-path-sum/
 * A path is any sequence of adjacent nodes, each used at most once (it need not pass the root). Return the maximum sum
 * of node values over all non-empty paths.
 * Input: [1,2,3] -> 6 · [-10,9,20,null,null,15,7] -> 42 (15 + 20 + 7) · [-3] -> -3
 * Explanation: README.md
 */
public class BinaryTreeMaximumPathSum {
    public static void main(String[] args) {
        System.out.println(maxPathSum(TreeNode.of(-10, 9, 20, null, null, 15, 7)));       // 42
    }

    /*
     * Postorder DFS. gain(node) = node.val + max(0, gain(left), gain(right)) is the best path going DOWN from node
     * (what the parent can extend). The best path that bends at node is node.val + max(0, gainL) + max(0, gainR).
     * Time O(n), space O(h).
     */
    public static int maxPathSum(TreeNode root) {
        int[] best = {Integer.MIN_VALUE};
        gain(root, best);
        return best[0];
    }

    private static int gain(TreeNode node, int[] best) {
        if (node == null) return 0;
        int left = Math.max(0, gain(node.left, best));           // a negative branch is better left out
        int right = Math.max(0, gain(node.right, best));
        best[0] = Math.max(best[0], node.val + left + right);
        return node.val + Math.max(left, right);
    }

    /** Reference for tests: best path between every pair of nodes (and single nodes), via parent pointers. */
    static int bruteForce(TreeNode root) {
        java.util.List<TreeNode> nodes = new java.util.ArrayList<>();
        java.util.Map<TreeNode, TreeNode> parent = new java.util.HashMap<>();
        collect(root, null, nodes, parent);
        int best = Integer.MIN_VALUE;
        for (TreeNode a : nodes) {
            for (TreeNode b : nodes) {
                java.util.Set<TreeNode> up = new java.util.HashSet<>();
                for (TreeNode x = a; x != null; x = parent.get(x)) up.add(x);
                TreeNode lca = b;
                while (!up.contains(lca)) lca = parent.get(lca);
                int sum = 0;
                for (TreeNode x = a; x != lca; x = parent.get(x)) sum += x.val;
                for (TreeNode x = b; x != lca; x = parent.get(x)) sum += x.val;
                best = Math.max(best, sum + lca.val);
            }
        }
        return best;
    }

    private static void collect(TreeNode n, TreeNode p, java.util.List<TreeNode> nodes, java.util.Map<TreeNode, TreeNode> parent) {
        if (n == null) return;
        nodes.add(n);
        parent.put(n, p);
        collect(n.left, n, nodes, parent);
        collect(n.right, n, nodes, parent);
    }
}
