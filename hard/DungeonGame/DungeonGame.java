package DungeonGame;

/*
 * 174. Dungeon Game — https://leetcode.com/problems/dungeon-game/
 * The knight starts top-left and must reach the princess bottom-right moving only right or down. Cells add or remove
 * health; health must stay >= 1 at all times. Return the minimum initial health.
 * Input: [[-2,-3,3],[-5,-10,1],[10,30,-5]] -> 7 · [[0]] -> 1
 * Explanation: README.md
 */
public class DungeonGame {
    public static void main(String[] args) {
        System.out.println(calculateMinimumHP(new int[][]{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}));          // 7
        System.out.println(calculateMinimumHPBinarySearch(new int[][]{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}})); // 7
    }

    /*
     * Approach 1 - need[i][j] = minimum health required when entering cell (i, j). Work backwards from the princess:
     * need = max(1, min(need right, need down) - cell). Forward DP fails because both "health left" and "lowest point
     * so far" matter. Time O(mn), space O(n).
     */
    public static int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[] need = new int[n + 1];
        java.util.Arrays.fill(need, Integer.MAX_VALUE);
        need[n - 1] = 1;                                         // after the princess cell you only need 1 health
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int next = Math.min(need[j], need[j + 1]);       // need[j] = below (previous row), need[j+1] = right
                need[j] = Math.max(1, next - dungeon[i][j]);
            }
            need[n] = Integer.MAX_VALUE;
        }
        return need[0];
    }

    /* Approach 2 - binary search the starting health; check with a forward DP of the best health that survives. */
    public static int calculateMinimumHPBinarySearch(int[][] dungeon) {
        int lo = 1, hi = 1;
        for (int[] row : dungeon) for (int v : row) hi += Math.max(0, -v);
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (survives(dungeon, mid)) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    private static boolean survives(int[][] d, int start) {
        int m = d.length, n = d[0].length;
        long[][] best = new long[m][n];                            // highest health on arrival after the cell, or -inf if dead
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                long before = i == 0 && j == 0 ? start : Math.max(i > 0 ? best[i - 1][j] : Long.MIN_VALUE, j > 0 ? best[i][j - 1] : Long.MIN_VALUE);
                long after = before == Long.MIN_VALUE ? Long.MIN_VALUE : before + d[i][j];
                best[i][j] = after >= 1 ? after : Long.MIN_VALUE;
            }
        }
        return best[m - 1][n - 1] != Long.MIN_VALUE;
    }
}
