package TrappingRainWaterII;

import java.util.PriorityQueue;

/*
 * 407. Trapping Rain Water II — https://leetcode.com/problems/trapping-rain-water-ii/
 * heightMap[i][j] is the height of a 2D elevation map. Return how much rain water it can trap.
 * Input: [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]] -> 4 · [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]] -> 10
 * Explanation: README.md
 */
public class TrappingRainWaterII {
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        System.out.println(trapRainWater(new int[][]{{1, 4, 3, 1, 3, 2}, {3, 2, 1, 3, 2, 4}, {2, 3, 3, 2, 3, 1}}));   // 4
    }

    /*
     * Approach 1 - the water level of a cell is limited by the lowest wall on the way out. Start with the border in a
     * min-heap; always process the lowest boundary cell: its unvisited neighbours hold max(0, level - height) water and
     * join the boundary at level max(level, height). Time O(mn log(mn)).
     */
    public static int trapRainWater(int[][] heightMap) {
        int m = heightMap.length, n = heightMap[0].length, water = 0;
        boolean[][] seen = new boolean[m][n];
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));    // {level, r, c}
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    heap.add(new int[]{heightMap[i][j], i, j});
                    seen[i][j] = true;
                }
        while (!heap.isEmpty()) {
            int[] cell = heap.poll();
            for (int[] d : DIRS) {
                int r = cell[1] + d[0], c = cell[2] + d[1];
                if (r < 0 || r >= m || c < 0 || c >= n || seen[r][c]) continue;
                seen[r][c] = true;
                water += Math.max(0, cell[0] - heightMap[r][c]);
                heap.add(new int[]{Math.max(cell[0], heightMap[r][c]), r, c});
            }
        }
        return water;
    }

    /*
     * Approach 2 - start every inner cell's water level at "infinity" and repeatedly lower it to
     * max(height, min neighbour level) until nothing changes (Bellman-Ford style). Slower, but shows the definition.
     */
    public static int trapRainWaterRelax(int[][] heightMap) {
        int m = heightMap.length, n = heightMap[0].length;
        int[][] level = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                level[i][j] = (i == 0 || j == 0 || i == m - 1 || j == n - 1) ? heightMap[i][j] : Integer.MAX_VALUE;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 1; i < m - 1; i++) {
                for (int j = 1; j < n - 1; j++) {
                    int lowest = Integer.MAX_VALUE;
                    for (int[] d : DIRS) lowest = Math.min(lowest, level[i + d[0]][j + d[1]]);
                    int next = Math.max(heightMap[i][j], lowest);
                    if (next < level[i][j]) {
                        level[i][j] = next;
                        changed = true;
                    }
                }
            }
        }
        int water = 0;
        for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) water += level[i][j] - heightMap[i][j];
        return water;
    }
}
