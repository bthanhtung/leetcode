package SubtreeOfAnotherTree;

/*
 * 572. Subtree of Another Tree — https://leetcode.com/problems/subtree-of-another-tree/
 * Return true if subRoot is a subtree of root: some node of root together with all its descendants equals subRoot.
 * Input: root = [3,4,5,1,2], subRoot = [4,1,2] -> true · root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2] -> false
 * Explanation: README.md
 */
public class SubtreeOfAnotherTree {
    public static void main(String[] args) {
        System.out.println(isSubtree(TreeNode.of(3, 4, 5, 1, 2), TreeNode.of(4, 1, 2)));      // true
    }

    /* Approach 1 - try every node of root as the top of an identical copy (problem 100). Time O(m * n). */
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) return subRoot == null;
        return same(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean same(TreeNode a, TreeNode b) {
        if (a == null || b == null) return a == b;
        return a.val == b.val && same(a.left, b.left) && same(a.right, b.right);
    }

    /* Approach 2 - preorder strings with null markers and value delimiters; then a substring search. O(m + n) with KMP. */
    public static boolean isSubtreeSerialize(TreeNode root, TreeNode subRoot) {
        return serialize(root).contains(serialize(subRoot));
    }

    private static String serialize(TreeNode n) {
        StringBuilder sb = new StringBuilder();
        write(n, sb);
        return sb.toString();
    }

    private static void write(TreeNode n, StringBuilder sb) {
        if (n == null) {
            sb.append(",#");
            return;
        }
        sb.append(',').append(n.val);              // leading comma: "2" must not match inside "12"
        write(n.left, sb);
        write(n.right, sb);
    }
}
