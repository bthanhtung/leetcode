package PopulatingNextRightPointersInEachNode;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 116. Populating Next Right Pointers in Each Node — https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
 * In a perfect binary tree, set each node's next pointer to its right neighbour on the same level (null at the end).
 * Follow up: constant extra space (recursion stack does not count).
 * Input: [1,2,3,4,5,6,7] -> [1,#,2,3,#,4,5,6,7,#]
 * Explanation: README.md
 */
public class PopulatingNextRightPointersInEachNode {
    /** Perfect binary tree node with a next pointer, as used by LeetCode. */
    public static class Node {
        public int val;
        public Node left, right, next;

        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Node root = perfect(3, new int[]{1});
        connect(root);
        System.out.println(root.left.next.val + " " + root.left.left.next.next.val);       // 3 6
    }

    /** Builds a perfect tree of the given depth with values 1, 2, 3... in level order. */
    public static Node perfect(int depth, int[] counter) {
        Node[] level = {new Node(counter[0]++)};
        Node root = level[0];
        for (int d = 1; d < depth; d++) {
            Node[] next = new Node[level.length * 2];
            for (int i = 0; i < level.length; i++) {
                level[i].left = next[2 * i] = new Node(counter[0]++);
                level[i].right = next[2 * i + 1] = new Node(counter[0]++);
            }
            level = next;
        }
        return root;
    }

    /* Approach 1 - BFS by levels, link each node to the next one polled in the same level. Time O(n), space O(width). */
    public static Node connectBfs(Node root) {
        Queue<Node> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            Node prev = null;
            for (int size = queue.size(); size > 0; size--) {
                Node node = queue.poll();
                if (prev != null) prev.next = node;
                prev = node;
                if (node.left != null) {
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
        }
        return root;
    }

    /*
     * Approach 2 - O(1) space: walk each level through the next pointers already set on it, wiring the level below:
     * left.next = right, and right.next = node.next.left. Time O(n).
     */
    public static Node connect(Node root) {
        for (Node leftmost = root; leftmost != null && leftmost.left != null; leftmost = leftmost.left) {
            for (Node node = leftmost; node != null; node = node.next) {
                node.left.next = node.right;
                if (node.next != null) node.right.next = node.next.left;
            }
        }
        return root;
    }

    /* Approach 3 - recursion doing the same two links per node. Space O(log n) for the stack. */
    public static Node connectRecursive(Node root) {
        if (root == null || root.left == null) return root;
        root.left.next = root.right;
        if (root.next != null) root.right.next = root.next.left;
        connectRecursive(root.left);
        connectRecursive(root.right);
        return root;
    }
}
