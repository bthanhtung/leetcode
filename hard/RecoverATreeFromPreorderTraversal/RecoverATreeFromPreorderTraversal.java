package RecoverATreeFromPreorderTraversal;

import com.tungdadev.common.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 1028. Recover a Tree From Preorder Traversal — https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
 * A preorder DFS printed each node as D dashes (its depth) followed by its value; a single child is always the left
 * child. Recover the tree from the output string.
 * Input: "1-2--3--4-5--6--7" -> [1,2,5,3,4,6,7] · "1-2--3---4-5--6---7" -> [1,2,5,3,null,6,null,4,null,7]
 * Explanation: README.md
 */
public class RecoverATreeFromPreorderTraversal {
    public static void main(String[] args) {
        System.out.println(TreeNode.toList(recoverFromPreorder("1-2--3--4-5--6--7")));      // [1, 2, 5, 3, 4, 6, 7]
        System.out.println(TreeNode.toList(recoverFromPreorderRecursive("1-401--349---90--88")));
    }

    /*
     * Approach 1 - the stack holds the path from the root to the last node. A node at depth d is a child of the node at
     * depth d - 1, so pop until the stack size equals d, then attach. Time O(n).
     */
    public static TreeNode recoverFromPreorder(String traversal) {
        Deque<TreeNode> path = new ArrayDeque<>();
        int i = 0, n = traversal.length();
        while (i < n) {
            int depth = 0;
            while (i < n && traversal.charAt(i) == '-') {
                depth++;
                i++;
            }
            int value = 0;
            while (i < n && Character.isDigit(traversal.charAt(i))) value = value * 10 + (traversal.charAt(i++) - '0');
            TreeNode node = new TreeNode(value);
            while (path.size() > depth) path.pop();
            if (!path.isEmpty()) {
                TreeNode parent = path.peek();
                if (parent.left == null) parent.left = node;
                else parent.right = node;
            }
            path.push(node);
        }
        return path.peekLast();                                   // the root is at the bottom of the stack
    }

    /* Approach 2 - recursive descent: build(depth) reads a node only if the next token has exactly that many dashes. */
    public static TreeNode recoverFromPreorderRecursive(String traversal) {
        return build(traversal, new int[]{0}, 0);
    }

    private static TreeNode build(String s, int[] pos, int depth) {
        int i = pos[0], dashes = 0;
        while (i < s.length() && s.charAt(i) == '-') {
            dashes++;
            i++;
        }
        if (dashes != depth || i >= s.length()) return null;      // the next node belongs higher up
        int value = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))) value = value * 10 + (s.charAt(i++) - '0');
        pos[0] = i;
        TreeNode node = new TreeNode(value);
        node.left = build(s, pos, depth + 1);
        if (node.left != null) node.right = build(s, pos, depth + 1);
        return node;
    }
}
