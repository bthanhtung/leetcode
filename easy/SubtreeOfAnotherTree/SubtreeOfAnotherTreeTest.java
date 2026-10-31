package SubtreeOfAnotherTree;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtreeOfAnotherTreeTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(3), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, SubtreeOfAnotherTree.isSubtree(TreeNode.of(3, 4, 5, 1, 2), TreeNode.of(4, 1, 2)));
        assertEquals(false, SubtreeOfAnotherTree.isSubtree(TreeNode.of(3, 4, 5, 1, 2, null, null, null, null, 0), TreeNode.of(4, 1, 2)));
        assertEquals(false, SubtreeOfAnotherTree.isSubtreeSerialize(TreeNode.of(12), TreeNode.of(2)));
        Random r = new Random(572);
        for (int t = 0; t < 2000; t++) {
            TreeNode a = randomTree(r, r.nextInt(10)), b = randomTree(r, 1 + r.nextInt(3));
            assertEquals(SubtreeOfAnotherTree.isSubtree(a, b), SubtreeOfAnotherTree.isSubtreeSerialize(a, b));
        }
    }
}
