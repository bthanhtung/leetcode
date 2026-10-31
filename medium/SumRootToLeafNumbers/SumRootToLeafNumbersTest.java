package SumRootToLeafNumbers;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumRootToLeafNumbersTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(10), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    static int depth(TreeNode n) {
        return n == null ? 0 : 1 + Math.max(depth(n.left), depth(n.right));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(25, SumRootToLeafNumbers.sumNumbers(TreeNode.of(1, 2, 3)));
        assertEquals(1026, SumRootToLeafNumbers.sumNumbers(TreeNode.of(4, 9, 0, 5, 1)));
        Random r = new Random(129);
        for (int t = 0; t < 500; t++) {
            TreeNode root = randomTree(r, r.nextInt(15));
            if (depth(root) > 9) continue;                  // keep numbers inside int like the constraints do
            assertEquals(SumRootToLeafNumbers.sumNumbers(root), SumRootToLeafNumbers.sumNumbersBfs(root));
        }
    }
}
