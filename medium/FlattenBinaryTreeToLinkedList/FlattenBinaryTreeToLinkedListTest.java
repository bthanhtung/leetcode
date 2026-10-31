package FlattenBinaryTreeToLinkedList;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FlattenBinaryTreeToLinkedListTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(r.nextInt(100), randomTree(r, left), randomTree(r, size - 1 - left));
    }

    static void preorder(TreeNode n, List<Integer> out) {
        if (n == null) return;
        out.add(n.val);
        preorder(n.left, out);
        preorder(n.right, out);
    }

    private static List<Integer> walkRight(TreeNode n) {
        List<Integer> out = new ArrayList<>();
        for (; n != null; n = n.right) {
            assertNull(n.left);
            out.add(n.val);
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        TreeNode root = TreeNode.of(1, 2, 5, 3, 4, null, 6);
        FlattenBinaryTreeToLinkedList.flatten(root);
        assertEquals(Arrays.asList(1, null, 2, null, 3, null, 4, null, 5, null, 6), TreeNode.toList(root));
        Random r = new Random(114);
        for (int t = 0; t < 300; t++) {
            int size = r.nextInt(25);
            long seed = r.nextLong();
            List<Integer> expected = new ArrayList<>();
            preorder(randomTree(new Random(seed), size), expected);
            TreeNode a = randomTree(new Random(seed), size), b = randomTree(new Random(seed), size), c = randomTree(new Random(seed), size);
            FlattenBinaryTreeToLinkedList.flatten(a);
            FlattenBinaryTreeToLinkedList.flattenList(b);
            FlattenBinaryTreeToLinkedList.flattenReverse(c);
            assertEquals(expected, walkRight(a));
            assertEquals(expected, walkRight(b));
            assertEquals(expected, walkRight(c));
        }
    }
}
