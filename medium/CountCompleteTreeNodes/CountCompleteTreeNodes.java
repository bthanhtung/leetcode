package CountCompleteTreeNodes;

/*
 * 222. Count Complete Tree Nodes — https://leetcode.com/problems/count-complete-tree-nodes/
 * Count the nodes of a complete binary tree (every level full except possibly the last, filled from the left).
 * Design an algorithm faster than O(n).
 * Input: [1,2,3,4,5,6] -> 6 · [] -> 0 · [1] -> 1
 * Explanation: README.md
 */
public class CountCompleteTreeNodes {
    public static void main(String[] args) {
        System.out.println(countNodes(TreeNode.of(1, 2, 3, 4, 5, 6)));                 // 6
        System.out.println(countNodesBinarySearch(TreeNode.of(1, 2, 3, 4, 5, 6)));     // 6
    }

    /* Approach 1 - ordinary count. Time O(n). */
    public static int countNodesNaive(TreeNode root) {
        return root == null ? 0 : 1 + countNodesNaive(root.left) + countNodesNaive(root.right);
    }

    /*
     * Approach 2 - if the leftmost and rightmost depths are equal the tree is perfect: 2^h - 1 nodes. Otherwise recurse;
     * one of the two subtrees is always perfect, so only one side goes deep. Time O(log^2 n).
     */
    public static int countNodes(TreeNode root) {
        if (root == null) return 0;
        int left = 0, right = 0;
        for (TreeNode n = root; n != null; n = n.left) left++;
        for (TreeNode n = root; n != null; n = n.right) right++;
        if (left == right) return (1 << left) - 1;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /*
     * Approach 3 - the last level has positions 0 .. 2^d - 1; binary search the last position that exists. Checking a
     * position walks one root-to-leaf path following its bits. Time O(log^2 n).
     */
    public static int countNodesBinarySearch(TreeNode root) {
        if (root == null) return 0;
        int d = 0;
        for (TreeNode n = root.left; n != null; n = n.left) d++;       // depth of the last level (root is depth 0)
        if (d == 0) return 1;
        int lo = 0, hi = (1 << d) - 1;                                  // find the last existing index
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (exists(root, d, mid)) lo = mid;
            else hi = mid - 1;
        }
        return (1 << d) - 1 + lo + 1;                                   // full levels above + nodes on the last level
    }

    private static boolean exists(TreeNode root, int d, int index) {
        TreeNode node = root;
        for (int bit = d - 1; bit >= 0; bit--) {
            node = (index >> bit & 1) == 0 ? node.left : node.right;
        }
        return node != null;
    }
}
