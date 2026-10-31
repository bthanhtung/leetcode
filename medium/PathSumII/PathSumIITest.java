package PathSumII;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathSumIITest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(7) - 3, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        TreeNode root = TreeNode.of(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1);
        assertEquals(List.of(List.of(5, 4, 11, 2), List.of(5, 8, 4, 5)), PathSumII.pathSum(root, 22));
        assertEquals(List.of(), PathSumII.pathSum(TreeNode.of(1, 2), 0));
        Random r = new Random(113);
        for (int t = 0; t < 500; t++) {
            TreeNode tree = randomTree(r, r.nextInt(15));
            int target = r.nextInt(9) - 4;
            List<List<Integer>> a = PathSumII.pathSum(tree, target);
            assertEquals(new HashSet<>(a), new HashSet<>(PathSumII.pathSumBfs(tree, target)));
        }
    }
}
