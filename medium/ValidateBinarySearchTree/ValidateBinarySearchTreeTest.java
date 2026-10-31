package ValidateBinarySearchTree;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateBinarySearchTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(6), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    private static boolean brute(TreeNode n) {
        if (n == null) return true;
        return all(n.left, v -> v < n.val) && all(n.right, v -> v > n.val) && brute(n.left) && brute(n.right);
    }

    private static boolean all(TreeNode n, java.util.function.IntPredicate p) {
        return n == null || (p.test(n.val) && all(n.left, p) && all(n.right, p));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, ValidateBinarySearchTree.isValidBST(TreeNode.of(2, 1, 3)));
        assertEquals(false, ValidateBinarySearchTree.isValidBST(TreeNode.of(5, 1, 4, null, null, 3, 6)));
        assertEquals(false, ValidateBinarySearchTree.isValidBST(TreeNode.of(2, 2, 2)));
        assertEquals(true, ValidateBinarySearchTree.isValidBST(TreeNode.of(Integer.MAX_VALUE)));
        assertEquals(true, ValidateBinarySearchTree.isValidBST(TreeNode.of(Integer.MIN_VALUE, null, Integer.MAX_VALUE)));
        Random r = new Random(98);
        for (int t = 0; t < 5000; t++) {
            TreeNode root = randomTree(r, r.nextInt(6));
            boolean expected = brute(root);
            assertEquals(expected, ValidateBinarySearchTree.isValidBST(root));
            assertEquals(expected, ValidateBinarySearchTree.isValidBSTInorder(root));
        }
    }
}
