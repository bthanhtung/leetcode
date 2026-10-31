package MergeTwoBinaryTrees;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 617. Merge Two Binary Trees — https://leetcode.com/problems/merge-two-binary-trees/
 * Overlay two binary trees: overlapping nodes sum their values, otherwise the non-null node is used.
 * Input: [1,3,2,5] + [2,1,3,null,4,null,7] -> [3,4,5,5,4,null,7]
 * Explanation: README.md
 */
public class MergeTwoBinaryTrees {
    public static void main(String[] args) {
        System.out.println(TreeNode.toList(mergeTrees(TreeNode.of(1, 3, 2, 5), TreeNode.of(2, 1, 3, null, 4, null, 7))));
        // [3, 4, 5, 5, 4, null, 7]
    }

    /* Approach 1 - recursion reusing t1's nodes. Time O(min(m, n)), space O(h). */
    public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }

    /* Approach 2 - recursion building brand-new nodes: inputs stay untouched. Time O(m + n). */
    public static TreeNode mergeTreesCopy(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        TreeNode node = new TreeNode((t1 == null ? 0 : t1.val) + (t2 == null ? 0 : t2.val));
        node.left = mergeTreesCopy(t1 == null ? null : t1.left, t2 == null ? null : t2.left);
        node.right = mergeTreesCopy(t1 == null ? null : t1.right, t2 == null ? null : t2.right);
        return node;
    }

    /* Approach 3 - iterative with a stack of node pairs, merging into t1. Time O(min(m, n)). */
    public static TreeNode mergeTreesIterative(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        Deque<TreeNode[]> stack = new ArrayDeque<>();
        stack.push(new TreeNode[]{t1, t2});
        while (!stack.isEmpty()) {
            TreeNode[] pair = stack.pop();
            if (pair[1] == null) continue;
            pair[0].val += pair[1].val;
            if (pair[0].left == null) pair[0].left = pair[1].left;
            else stack.push(new TreeNode[]{pair[0].left, pair[1].left});
            if (pair[0].right == null) pair[0].right = pair[1].right;
            else stack.push(new TreeNode[]{pair[0].right, pair[1].right});
        }
        return t1;
    }
}
