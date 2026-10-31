package BinaryTreeCameras;

/*
 * 968. Binary Tree Cameras — https://leetcode.com/problems/binary-tree-cameras/
 * Install cameras on tree nodes; a camera monitors its parent, itself and its children. Return the minimum number of
 * cameras to monitor every node.
 * Input: [0,0,null,0,0] -> 1 · [0,0,null,0,null,0,null,null,0] -> 2
 * Explanation: README.md
 */
public class BinaryTreeCameras {
    public static void main(String[] args) {
        System.out.println(minCameraCover(TreeNode.of(0, 0, null, 0, 0)));                         // 1
        System.out.println(minCameraCoverDp(TreeNode.of(0, 0, null, 0, null, 0, null, null, 0)));  // 2
    }

    private static final int NOT_COVERED = 0, HAS_CAMERA = 1, COVERED = 2;

    /*
     * Approach 1 - greedy from the leaves up: never put a camera on a leaf - its parent covers more. Each node reports
     * NOT_COVERED, HAS_CAMERA or COVERED; a node with an uncovered child must take a camera. Time O(n).
     */
    public static int minCameraCover(TreeNode root) {
        int[] cameras = {0};
        if (state(root, cameras) == NOT_COVERED) cameras[0]++;      // the root has no parent to cover it
        return cameras[0];
    }

    private static int state(TreeNode n, int[] cameras) {
        if (n == null) return COVERED;                                // nothing to watch
        int l = state(n.left, cameras), r = state(n.right, cameras);
        if (l == NOT_COVERED || r == NOT_COVERED) {
            cameras[0]++;
            return HAS_CAMERA;
        }
        if (l == HAS_CAMERA || r == HAS_CAMERA) return COVERED;
        return NOT_COVERED;
    }

    /*
     * Approach 2 - exact DP: each node returns {cost if it has a camera, cost if covered without a camera,
     * cost if not covered yet (its parent must cover it)} for its whole subtree.
     */
    public static int minCameraCoverDp(TreeNode root) {
        int[] r = dp(root);
        return Math.min(r[0], r[1]);
    }

    private static int[] dp(TreeNode n) {
        int inf = 1_000_000;
        if (n == null) return new int[]{inf, 0, 0};
        int[] l = dp(n.left), r = dp(n.right);
        int bestL = Math.min(l[0], Math.min(l[1], l[2])), bestR = Math.min(r[0], Math.min(r[1], r[2]));
        int camera = 1 + bestL + bestR;
        int covered = Math.min(l[0] + Math.min(r[0], r[1]), r[0] + Math.min(l[0], l[1]));     // a child holds a camera
        int notCovered = l[1] + r[1];                                                           // children covered on their own
        return new int[]{camera, covered, notCovered};
    }
}
