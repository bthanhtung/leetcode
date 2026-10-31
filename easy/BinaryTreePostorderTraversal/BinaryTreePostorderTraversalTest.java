package BinaryTreePostorderTraversal;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreePostorderTraversalTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examples() {
        assertEquals(List.of(3, 2, 1), BinaryTreePostorderTraversal.postorderTraversal(TreeNode.of(1, null, 2, 3)));
        assertEquals(List.of(4, 6, 7, 5, 2, 9, 8, 3, 1), BinaryTreePostorderTraversal.postorderTraversal(TreeNode.of(1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9)));
        assertEquals(List.of(), BinaryTreePostorderTraversal.postorderTraversal(null));
    }

    @Test
    void randomTrees() {
        Random r = new Random(145);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(25));
            List<Integer> expected = BinaryTreePostorderTraversal.postorderTraversalRecursive(root);
            assertEquals(expected, BinaryTreePostorderTraversal.postorderTraversal(root));
            assertEquals(expected, BinaryTreePostorderTraversal.postorderTraversalReverse(root));
        }
    }
}
