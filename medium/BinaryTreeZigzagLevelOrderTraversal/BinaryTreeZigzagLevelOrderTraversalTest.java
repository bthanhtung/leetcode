package BinaryTreeZigzagLevelOrderTraversal;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeZigzagLevelOrderTraversalTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(List.of(3), List.of(20, 9), List.of(15, 7)), BinaryTreeZigzagLevelOrderTraversal.zigzagLevelOrder(TreeNode.of(3, 9, 20, null, null, 15, 7)));
        assertEquals(List.of(), BinaryTreeZigzagLevelOrderTraversal.zigzagLevelOrder(null));
        Random r = new Random(103);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, r.nextInt(30));
            List<List<Integer>> expected = BinaryTreeZigzagLevelOrderTraversal.zigzagLevelOrderReverse(root);
            assertEquals(expected, BinaryTreeZigzagLevelOrderTraversal.zigzagLevelOrder(root));
            assertEquals(expected, BinaryTreeZigzagLevelOrderTraversal.zigzagLevelOrderDfs(root));
        }
    }
}
