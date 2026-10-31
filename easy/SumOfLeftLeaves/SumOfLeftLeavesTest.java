package SumOfLeftLeaves;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumOfLeftLeavesTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(20) - 5, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(24, SumOfLeftLeaves.sumOfLeftLeaves(TreeNode.of(3, 9, 20, null, null, 15, 7)));
        assertEquals(0, SumOfLeftLeaves.sumOfLeftLeaves(TreeNode.of(1)));
        Random r = new Random(404);
        for (int t = 0; t < 500; t++) {
            TreeNode root = randomTree(r, r.nextInt(25));
            assertEquals(SumOfLeftLeaves.sumOfLeftLeaves(root), SumOfLeftLeaves.sumOfLeftLeavesIterative(root));
        }
    }
}
