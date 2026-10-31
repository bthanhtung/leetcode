package SerializeAndDeserializeBinaryTree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/*
 * 297. Serialize and Deserialize Binary Tree — https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 * Design an algorithm to serialize a binary tree to a string and deserialize the string back to the same tree.
 * Input: [1,2,3,null,null,4,5] -> serialize -> deserialize -> [1,2,3,null,null,4,5]
 * Explanation: README.md
 */
public class SerializeAndDeserializeBinaryTree {
    public static void main(String[] args) {
        TreeNode root = TreeNode.of(1, 2, 3, null, null, 4, 5);
        Codec codec = new Codec();
        String data = codec.serialize(root);
        System.out.println(data);                                        // 1,2,#,#,3,4,#,#,5,#,#
        System.out.println(TreeNode.toList(codec.deserialize(data)));    // [1, 2, 3, null, null, 4, 5]
    }

    /* Approach 1 - preorder with '#' for null children: the null markers make the shape unambiguous. O(n) both ways. */
    public static class Codec {
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            write(root, sb);
            return sb.substring(0, sb.length() - 1);              // drop the trailing comma
        }

        private void write(TreeNode n, StringBuilder sb) {
            if (n == null) {
                sb.append("#,");
                return;
            }
            sb.append(n.val).append(',');
            write(n.left, sb);
            write(n.right, sb);
        }

        public TreeNode deserialize(String data) {
            Deque<String> tokens = new ArrayDeque<>(java.util.Arrays.asList(data.split(",")));
            return read(tokens);
        }

        private TreeNode read(Deque<String> tokens) {
            String t = tokens.poll();
            if (t.equals("#")) return null;
            TreeNode n = new TreeNode(Integer.parseInt(t));
            n.left = read(tokens);
            n.right = read(tokens);
            return n;
        }
    }

    /* Approach 2 - level order like LeetCode's own format; each dequeued node consumes the next two tokens as children. */
    public static class CodecBfs {
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            Queue<TreeNode> queue = new LinkedList<>();          // LinkedList accepts null
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode n = queue.poll();
                if (n == null) {
                    sb.append("#,");
                    continue;
                }
                sb.append(n.val).append(',');
                queue.add(n.left);
                queue.add(n.right);
            }
            return sb.substring(0, sb.length() - 1);
        }

        public TreeNode deserialize(String data) {
            String[] t = data.split(",");
            if (t[0].equals("#")) return null;
            TreeNode root = new TreeNode(Integer.parseInt(t[0]));
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.add(root);
            int i = 1;
            while (!queue.isEmpty()) {
                TreeNode n = queue.poll();
                if (!t[i].equals("#")) {
                    n.left = new TreeNode(Integer.parseInt(t[i]));
                    queue.add(n.left);
                }
                i++;
                if (!t[i].equals("#")) {
                    n.right = new TreeNode(Integer.parseInt(t[i]));
                    queue.add(n.right);
                }
                i++;
            }
            return root;
        }
    }
}
