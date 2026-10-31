package RecoverATreeFromPreorderTraversal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecoverATreeFromPreorderTraversalTest {

    /** Random tree where a single child is always on the left, as the statement guarantees. */
    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = size == 1 ? 0 : 1 + r.nextInt(size - 1);
        TreeNode n = new TreeNode(1 + r.nextInt(500));
        n.left = randomTree(r, left);
        n.right = randomTree(r, size - 1 - left);
        return n;
    }

    static void print(TreeNode n, int depth, StringBuilder sb) {
        if (n == null) return;
        sb.append("-".repeat(depth)).append(n.val);
        print(n.left, depth + 1, sb);
        print(n.right, depth + 1, sb);
    }

    @Test
    void examplesAndRoundTrip() {
        assertEquals(Arrays.asList(1, 2, 5, 3, 4, 6, 7), TreeNode.toList(RecoverATreeFromPreorderTraversal.recoverFromPreorder("1-2--3--4-5--6--7")));
        assertEquals(Arrays.asList(1, 2, 5, 3, null, 6, null, 4, null, 7), TreeNode.toList(RecoverATreeFromPreorderTraversal.recoverFromPreorder("1-2--3---4-5--6---7")));
        assertEquals(Arrays.asList(1, 401, null, 349, 88, 90), TreeNode.toList(RecoverATreeFromPreorderTraversal.recoverFromPreorder("1-401--349---90--88")));
        Random r = new Random(1028);
        for (int t = 0; t < 500; t++) {
            TreeNode tree = randomTree(r, 1 + r.nextInt(25));
            StringBuilder sb = new StringBuilder();
            print(tree, 0, sb);
            assertEquals(TreeNode.toList(tree), TreeNode.toList(RecoverATreeFromPreorderTraversal.recoverFromPreorder(sb.toString())));
            assertEquals(TreeNode.toList(tree), TreeNode.toList(RecoverATreeFromPreorderTraversal.recoverFromPreorderRecursive(sb.toString())));
        }
    }
}
