package ErectTheFence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 587. Erect the Fence — https://leetcode.com/problems/erect-the-fence/
 * Trees at integer points. Fence all of them with the shortest rope: return the points lying on the fence perimeter
 * (the convex hull, including points on its edges), in any order.
 * Input: [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]] -> [[1,1],[2,0],[4,2],[3,3],[2,4]] · [[1,2],[2,2],[4,2]] -> all three
 * Explanation: README.md
 */
public class ErectTheFence {
    public static void main(String[] args) {
        int[][] trees = {{1, 1}, {2, 2}, {2, 0}, {2, 4}, {3, 3}, {4, 2}};
        System.out.println(Arrays.deepToString(outerTrees(trees)));
    }

    /** Cross product of OA x OB: > 0 counter-clockwise turn, < 0 clockwise, 0 collinear. */
    private static long cross(int[] o, int[] a, int[] b) {
        return (long) (a[0] - o[0]) * (b[1] - o[1]) - (long) (a[1] - o[1]) * (b[0] - o[0]);
    }

    /*
     * Approach 1 - Andrew's monotone chain: sort by x (then y), build the lower hull left to right and the upper hull
     * right to left, popping points that make a clockwise turn. Keeping collinear points (cross == 0) includes points
     * on edges. Time O(n log n).
     */
    public static int[][] outerTrees(int[][] trees) {
        int[][] pts = trees.clone();
        Arrays.sort(pts, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        List<int[]> hull = new ArrayList<>();
        for (int[] p : pts) {                                           // lower hull
            while (hull.size() >= 2 && cross(hull.get(hull.size() - 2), hull.getLast(), p) < 0) hull.removeLast();
            hull.add(p);
        }
        for (int i = pts.length - 2; i >= 0; i--) {                     // upper hull
            while (hull.size() >= 2 && cross(hull.get(hull.size() - 2), hull.getLast(), pts[i]) < 0) hull.removeLast();
            hull.add(pts[i]);
        }
        Set<Long> seen = new HashSet<>();                              // collinear runs appear on both hulls
        List<int[]> out = new ArrayList<>();
        for (int[] p : hull) if (seen.add(p[0] * 1000L + p[1])) out.add(p);
        return out.toArray(new int[0][]);
    }

    /*
     * Approach 2 - Jarvis march: from the leftmost point, repeatedly pick the point such that every other point lies to
     * its left (the farthest one if several are collinear, so every step reaches the end of an edge), add the points
     * lying on that edge, and walk until back at the start. Time O(n * h).
     */
    public static int[][] outerTreesJarvis(int[][] trees) {
        int n = trees.length;
        if (n < 4) return trees.clone();
        int start = 0;
        for (int i = 1; i < n; i++) {                       // leftmost, then lowest: always a hull vertex
            if (trees[i][0] < trees[start][0] || (trees[i][0] == trees[start][0] && trees[i][1] < trees[start][1])) start = i;
        }
        Set<Integer> onHull = new HashSet<>();
        int p = start;
        do {
            int q = (p + 1) % n;
            for (int r = 0; r < n; r++) {
                long c = cross(trees[p], trees[q], trees[r]);
                if (c < 0 || (c == 0 && dist(trees[p], trees[r]) > dist(trees[p], trees[q]))) q = r;   // more clockwise, or farther on the same line
            }
            for (int r = 0; r < n; r++) {
                if (r != p && r != q && cross(trees[p], trees[q], trees[r]) == 0 && between(trees[p], trees[r], trees[q])) {
                    onHull.add(r);
                }
            }
            onHull.add(q);
            p = q;
        } while (p != start);
        int[][] out = new int[onHull.size()][];
        int k = 0;
        for (int i : onHull) out[k++] = trees[i];
        return out;
    }

    private static long dist(int[] a, int[] b) {
        return (long) (a[0] - b[0]) * (a[0] - b[0]) + (long) (a[1] - b[1]) * (a[1] - b[1]);
    }

    private static boolean between(int[] a, int[] b, int[] c) {
        return Math.min(a[0], c[0]) <= b[0] && b[0] <= Math.max(a[0], c[0]) && Math.min(a[1], c[1]) <= b[1] && b[1] <= Math.max(a[1], c[1]);
    }
}
