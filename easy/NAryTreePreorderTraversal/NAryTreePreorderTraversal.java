package NAryTreePreorderTraversal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * 589. N-ary Tree Preorder Traversal — https://leetcode.com/problems/n-ary-tree-preorder-traversal/
 * Return the preorder traversal (node, then children left to right) of an n-ary tree.
 * Input: [1,null,3,2,4,null,5,6] -> [1,3,5,6,2,4]
 * Explanation: README.md
 */
public class NAryTreePreorderTraversal {
    /** N-ary tree node as used by LeetCode. */
    public static class Node {
        public int val;
        public List<Node> children;

        public Node(int val, Node... children) {
            this.val = val;
            this.children = new ArrayList<>(List.of(children));
        }
    }

    public static void main(String[] args) {
        Node root = new Node(1, new Node(3, new Node(5), new Node(6)), new Node(2), new Node(4));
        System.out.println(preorder(root));             // [1, 3, 5, 6, 2, 4]
        System.out.println(preorderIterative(root));    // [1, 3, 5, 6, 2, 4]
    }

    /* Approach 1 - recursion: the node, then each child's preorder. Time O(n), space O(h). */
    public static List<Integer> preorder(Node root) {
        List<Integer> out = new ArrayList<>();
        visit(root, out);
        return out;
    }

    private static void visit(Node node, List<Integer> out) {
        if (node == null) return;
        out.add(node.val);
        for (Node child : node.children) visit(child, out);
    }

    /* Approach 2 - stack: pop a node, push its children right to left so the leftmost comes out first. Time O(n). */
    public static List<Integer> preorderIterative(Node root) {
        List<Integer> out = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            out.add(node.val);
            for (int i = node.children.size() - 1; i >= 0; i--) {
                stack.push(node.children.get(i));
            }
        }
        return out;
    }
}
