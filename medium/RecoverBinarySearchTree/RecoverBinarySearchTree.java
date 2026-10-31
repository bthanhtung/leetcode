package RecoverBinarySearchTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 99. Recover Binary Search Tree — https://leetcode.com/problems/recover-binary-search-tree/
 * Exactly two nodes of a BST had their values swapped by mistake. Recover the tree without changing its structure.
 * Follow up: O(1) space.
 * Input: [1,3,null,null,2] -> [3,1,null,null,2] · [3,1,4,null,null,2] -> [2,1,4,null,null,3]
 * Explanation: README.md
 */
public class RecoverBinarySearchTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(3, 1, 4, null, null, 2);
        recoverTree(root);
        System.out.println(TreeNode.toList(root));      // [2, 1, 4, null, null, 3]
    }

    /* Approach 1 - collect nodes in inorder, sort their values, write them back. Time O(n log n), space O(n). */
    public static void recoverTreeSort(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        inorder(root, nodes);
        List<Integer> values = new ArrayList<>();
        for (TreeNode n : nodes) values.add(n.val);
        Collections.sort(values);
        for (int i = 0; i < nodes.size(); i++) nodes.get(i).val = values.get(i);
    }

    private static void inorder(TreeNode n, List<TreeNode> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(n);
        inorder(n.right, out);
    }

    /*
     * Approach 2 - in the inorder sequence a swap creates one or two "drops" (prev > curr). The first wrong node is prev
     * at the first drop, the second is curr at the last drop. Time O(n), space O(h).
     */
    public static void recoverTree(TreeNode root) {
        TreeNode[] s = new TreeNode[3];         // {first, second, prev}
        find(root, s);
        swap(s[0], s[1]);
    }

    private static void find(TreeNode n, TreeNode[] s) {
        if (n == null) return;
        find(n.left, s);
        if (s[2] != null && s[2].val > n.val) {
            if (s[0] == null) s[0] = s[2];
            s[1] = n;
        }
        s[2] = n;
        find(n.right, s);
    }

    /* Approach 3 - the same detection on a Morris inorder traversal: no stack, O(1) extra space. */
    public static void recoverTreeMorris(TreeNode root) {
        TreeNode first = null, second = null, prev = null, curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) pred = pred.right;
                if (pred.right == null) {
                    pred.right = curr;
                    curr = curr.left;
                    continue;
                }
                pred.right = null;
            }
            if (prev != null && prev.val > curr.val) {          // visit curr
                if (first == null) first = prev;
                second = curr;
            }
            prev = curr;
            curr = curr.right;
        }
        swap(first, second);
    }

    private static void swap(TreeNode a, TreeNode b) {
        int t = a.val;
        a.val = b.val;
        b.val = t;
    }
}
