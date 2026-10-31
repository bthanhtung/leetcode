package RectangleAreaII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/*
 * 850. Rectangle Area II — https://leetcode.com/problems/rectangle-area-ii/
 * Given axis-aligned rectangles [x1, y1, x2, y2], return the total area covered by their union modulo 10^9 + 7.
 * Input: [[0,0,2,2],[1,0,2,3],[1,0,3,1]] -> 6 · [[0,0,1000000000,1000000000]] -> 49
 * Explanation: README.md
 */
public class RectangleAreaII {
    private static final long MOD = 1_000_000_007L;

    public static void main(String[] args) {
        System.out.println(rectangleArea(new int[][]{{0, 0, 2, 2}, {1, 0, 2, 3}, {1, 0, 3, 1}}));     // 6
        System.out.println(rectangleAreaSweep(new int[][]{{0, 0, 1000000000, 1000000000}}));        // 49
    }

    /*
     * Approach 1 - coordinate compression: the distinct x and y edges cut the plane into a grid of cells; mark every
     * cell covered by some rectangle and add up the real areas of marked cells. Time O(n^3).
     */
    public static int rectangleArea(int[][] rectangles) {
        TreeSet<Integer> xs = new TreeSet<>(), ys = new TreeSet<>();
        for (int[] r : rectangles) {
            xs.add(r[0]);
            xs.add(r[2]);
            ys.add(r[1]);
            ys.add(r[3]);
        }
        Integer[] x = xs.toArray(new Integer[0]), y = ys.toArray(new Integer[0]);
        boolean[][] covered = new boolean[x.length][y.length];
        for (int[] r : rectangles) {
            int x1 = Arrays.binarySearch(x, r[0]), x2 = Arrays.binarySearch(x, r[2]);
            int y1 = Arrays.binarySearch(y, r[1]), y2 = Arrays.binarySearch(y, r[3]);
            for (int i = x1; i < x2; i++)
                for (int j = y1; j < y2; j++) covered[i][j] = true;
        }
        long area = 0;
        for (int i = 0; i + 1 < x.length; i++)
            for (int j = 0; j + 1 < y.length; j++)
                if (covered[i][j]) area += (long) (x[i + 1] - x[i]) * (y[j + 1] - y[j]);
        return (int) (area % MOD);
    }

    /*
     * Approach 2 - sweep a vertical line across x. Events open/close a rectangle's y-interval; between two events the
     * covered area is (covered length on y) * (dx). Covered length = union of active intervals. Time O(n^2 log n).
     */
    public static int rectangleAreaSweep(int[][] rectangles) {
        List<int[]> events = new ArrayList<>();                 // {x, +1 open / -1 close, y1, y2}
        for (int[] r : rectangles) {
            events.add(new int[]{r[0], 1, r[1], r[3]});
            events.add(new int[]{r[2], -1, r[1], r[3]});
        }
        events.sort((a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> active = new ArrayList<>();
        long area = 0;
        int prevX = events.getFirst()[0];
        for (int[] e : events) {
            area += coveredLength(active) * (e[0] - prevX);
            prevX = e[0];
            if (e[1] == 1) {
                active.add(new int[]{e[2], e[3]});
            } else {
                for (int i = 0; i < active.size(); i++) {
                    if (active.get(i)[0] == e[2] && active.get(i)[1] == e[3]) {
                        active.remove(i);
                        break;
                    }
                }
            }
        }
        return (int) (area % MOD);
    }

    private static long coveredLength(List<int[]> intervals) {
        List<int[]> sorted = new ArrayList<>(intervals);
        sorted.sort((a, b) -> Integer.compare(a[0], b[0]));
        long total = 0, curStart = -1, curEnd = -1;
        for (int[] in : sorted) {
            if (in[0] > curEnd) {
                total += curEnd - curStart;
                curStart = in[0];
                curEnd = in[1];
            } else {
                curEnd = Math.max(curEnd, in[1]);
            }
        }
        return total + (curEnd - curStart);
    }
}
