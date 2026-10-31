package HouseRobberIII;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HouseRobberIIITest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(20), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    private static int brute(TreeNode n, boolean parentRobbed) {
        if (n == null) return 0;
        int skip = brute(n.left, false) + brute(n.right, false);
        if (parentRobbed) return skip;
        return Math.max(skip, n.val + brute(n.left, true) + brute(n.right, true));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(7, HouseRobberIII.rob(TreeNode.of(3, 2, 3, null, 3, null, 1)));
        assertEquals(9, HouseRobberIII.rob(TreeNode.of(3, 4, 5, 1, 3, null, 1)));
        Random r = new Random(337);
        for (int t = 0; t < 500; t++) {
            TreeNode root = randomTree(r, r.nextInt(18));
            int expected = brute(root, false);
            assertEquals(expected, HouseRobberIII.rob(root));
            assertEquals(expected, HouseRobberIII.robMemo(root));
        }
    }
}
