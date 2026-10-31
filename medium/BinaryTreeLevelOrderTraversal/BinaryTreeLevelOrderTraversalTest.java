package BinaryTreeLevelOrderTraversal;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeLevelOrderTraversalTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(List.of(3), List.of(9, 20), List.of(15, 7)), BinaryTreeLevelOrderTraversal.levelOrder(TreeNode.of(3, 9, 20, null, null, 15, 7)));
        assertEquals(List.of(), BinaryTreeLevelOrderTraversal.levelOrder(null));
        Random r = new Random(102);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(30));
            assertEquals(BinaryTreeLevelOrderTraversal.levelOrder(root), BinaryTreeLevelOrderTraversal.levelOrderDfs(root));
        }
    }
}
