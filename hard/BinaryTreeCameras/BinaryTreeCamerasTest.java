package BinaryTreeCameras;

import com.tungdadev.common.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeCamerasTest {

    static TreeNode randomTree(Random r, int size) {
        if (size == 0) return null;
        int left = r.nextInt(size);
        return new TreeNode(0, randomTree(r, left), randomTree(r, size - 1 - left));
    }

    private static void collect(TreeNode n, TreeNode parent, List<TreeNode[]> out) {
        if (n == null) return;
        out.add(new TreeNode[]{n, parent});
        collect(n.left, n, out);
        collect(n.right, n, out);
    }

    /** Try every camera subset (tiny trees). */
    private static int brute(TreeNode root) {
        List<TreeNode[]> nodes = new ArrayList<>();
        collect(root, null, nodes);
        int n = nodes.size(), best = n;
        for (int mask = 0; mask < 1 << n; mask++) {
            if (Integer.bitCount(mask) >= best) continue;
            java.util.Set<TreeNode> cams = new java.util.HashSet<>();
            for (int i = 0; i < n; i++) if ((mask >> i & 1) == 1) cams.add(nodes.get(i)[0]);
            boolean ok = true;
            for (TreeNode[] p : nodes) {
                TreeNode x = p[0];
                if (!(cams.contains(x) || cams.contains(p[1]) || cams.contains(x.left) || cams.contains(x.right))) { ok = false; break; }
            }
            if (ok) best = Integer.bitCount(mask);
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(1, BinaryTreeCameras.minCameraCover(TreeNode.of(0, 0, null, 0, 0)));
        assertEquals(2, BinaryTreeCameras.minCameraCover(TreeNode.of(0, 0, null, 0, null, 0, null, null, 0)));
        assertEquals(1, BinaryTreeCameras.minCameraCover(TreeNode.of(0)));
        Random r = new Random(968);
        for (int t = 0; t < 300; t++) {
            TreeNode root = randomTree(r, 1 + r.nextInt(12));
            int expected = brute(root);
            assertEquals(expected, BinaryTreeCameras.minCameraCover(root));
            assertEquals(expected, BinaryTreeCameras.minCameraCoverDp(root));
        }
    }
}
