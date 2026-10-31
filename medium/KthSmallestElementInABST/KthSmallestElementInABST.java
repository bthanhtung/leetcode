package KthSmallestElementInABST;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * 230. Kth Smallest Element in a BST — https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 * Return the k-th smallest value (1-indexed) among the nodes of a BST.
 * Input: [3,1,4,null,2], k = 1 -> 1 · [5,3,6,2,4,null,null,1], k = 3 -> 3
 * Follow up: the BST is modified often and you need the k-th smallest frequently.
 * Explanation: README.md
 */
public class KthSmallestElementInABST {
    public static void main(String[] args) {
        System.out.println(kthSmallest(TreeNode.of(5, 3, 6, 2, 4, null, null, 1), 3));       // 3
    }

    /* Approach 1 - full inorder into a list, take index k - 1. Time O(n), space O(n). */
    public static int kthSmallestList(TreeNode root, int k) {
        List<Integer> values = new ArrayList<>();
        inorder(root, values);
        return values.get(k - 1);
    }

    private static void inorder(TreeNode n, List<Integer> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(n.val);
        inorder(n.right, out);
    }

    /* Approach 2 - iterative inorder, stop at the k-th visited node. Time O(h + k), space O(h). */
    public static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (true) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (--k == 0) return curr.val;
            curr = curr.right;
        }
    }

    /*
     * Approach 3 - follow up: if every node knew its subtree size, we could go left/right like a binary search in O(h).
     * Here sizes are computed on the fly (O(n) per call); in a real augmented BST they would be stored and kept updated.
     */
    public static int kthSmallestBySize(TreeNode root, int k) {
        while (true) {
            int leftSize = size(root.left);
            if (k <= leftSize) {
                root = root.left;
            } else if (k == leftSize + 1) {
                return root.val;
            } else {
                k -= leftSize + 1;
                root = root.right;
            }
        }
    }

    private static int size(TreeNode n) {
        return n == null ? 0 : 1 + size(n.left) + size(n.right);
    }
}
