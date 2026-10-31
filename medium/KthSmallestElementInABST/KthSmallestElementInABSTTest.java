package KthSmallestElementInABST;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthSmallestElementInABSTTest {

    private static TreeNode insert(TreeNode root, int v) {
        if (root == null) return new TreeNode(v);
        if (v < root.val) root.left = insert(root.left, v);
        else if (v > root.val) root.right = insert(root.right, v);
        return root;
    }

    private static int count(TreeNode n) {
        return n == null ? 0 : 1 + count(n.left) + count(n.right);
    }

    @Test
    void examplesAndRandom() {
        assertEquals(1, KthSmallestElementInABST.kthSmallest(TreeNode.of(3, 1, 4, null, 2), 1));
        assertEquals(3, KthSmallestElementInABST.kthSmallest(TreeNode.of(5, 3, 6, 2, 4, null, null, 1), 3));
        Random r = new Random(230);
        for (int t = 0; t < 300; t++) {
            TreeNode root = null;
            for (int i = 1 + r.nextInt(25); i > 0; i--) root = insert(root, r.nextInt(100));
            int n = count(root);
            for (int k = 1; k <= n; k++) {
                int expected = KthSmallestElementInABST.kthSmallestList(root, k);
                assertEquals(expected, KthSmallestElementInABST.kthSmallest(root, k));
                assertEquals(expected, KthSmallestElementInABST.kthSmallestBySize(root, k));
            }
        }
    }
}
