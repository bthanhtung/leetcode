package PathSum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathSumTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(7) - 3, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        TreeNode root = TreeNode.of(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1);
        assertEquals(true, PathSum.hasPathSum(root, 22));
        assertEquals(false, PathSum.hasPathSum(TreeNode.of(1, 2, 3), 5));
        assertEquals(false, PathSum.hasPathSum(null, 0));
        assertEquals(false, PathSum.hasPathSum(TreeNode.of(1, 2), 1));      // 1 is not a leaf
        Random r = new Random(112);
        for (int t = 0; t < 1000; t++) {
            TreeNode tree = randomTree(r, r.nextInt(12));
            int target = r.nextInt(11) - 5;
            assertEquals(PathSum.hasPathSum(tree, target), PathSum.hasPathSumIterative(tree, target));
        }
    }
}
