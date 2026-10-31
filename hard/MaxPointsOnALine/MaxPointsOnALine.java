package MaxPointsOnALine;

import java.util.HashMap;
import java.util.Map;

/*
 * 149. Max Points on a Line — https://leetcode.com/problems/max-points-on-a-line/
 * Given distinct points on the plane, return the maximum number of points on one straight line.
 * Input: [[1,1],[2,2],[3,3]] -> 3 · [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]] -> 4
 * Explanation: README.md
 */
public class MaxPointsOnALine {
    public static void main(String[] args) {
        System.out.println(maxPoints(new int[][]{{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}}));     // 4
    }

    /*
     * Fix an anchor; every other point defines a direction (dx, dy). Reduce by gcd and normalise the sign so equal
     * slopes give equal keys (no floating point). The most common key + the anchor is the best line through it.
     * Time O(n^2 log C).
     */
    public static int maxPoints(int[][] points) {
        int n = points.length, best = Math.min(n, 1);
        for (int i = 0; i < n; i++) {
            Map<Long, Integer> slopes = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0], dy = points[j][1] - points[i][1];
                int g = gcd(Math.abs(dx), Math.abs(dy));
                dx /= g;
                dy /= g;
                if (dx < 0 || (dx == 0 && dy < 0)) {
                    dx = -dx;
                    dy = -dy;
                }
                long key = ((long) dx << 32) | (dy & 0xffffffffL);
                best = Math.max(best, slopes.merge(key, 1, Integer::sum) + 1);
            }
        }
        return best;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
