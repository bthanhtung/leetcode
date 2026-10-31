package ConvertSortedArrayToBinarySearchTree;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConvertSortedArrayToBinarySearchTreeTest {

    private static int height(TreeNode n) {
        if (n == null) return 0;
        int l = height(n.left), r = height(n.right);
        if (l < 0 || r < 0 || Math.abs(l - r) > 1) return -1;
        return 1 + Math.max(l, r);
    }

    private static void inorder(TreeNode n, List<Integer> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(n.val);
        inorder(n.right, out);
    }

    @Test
    void balancedAndSameInorder() {
        for (int n = 0; n <= 40; n++) {
            int[] nums = IntStream.range(0, n).map(i -> i * 3 - 20).toArray();
            for (TreeNode root : Arrays.asList(ConvertSortedArrayToBinarySearchTree.sortedArrayToBST(nums),
                    ConvertSortedArrayToBinarySearchTree.sortedArrayToBSTIterative(nums))) {
                assertTrue(height(root) >= 0, "not balanced for n=" + n);
                List<Integer> seq = new ArrayList<>();
                inorder(root, seq);
                assertEquals(IntStream.of(nums).boxed().toList(), seq);
            }
        }
    }
}
