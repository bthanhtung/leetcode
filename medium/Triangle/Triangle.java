package Triangle;

import java.util.List;

/*
 * 120. Triangle — https://leetcode.com/problems/triangle/
 * Return the minimum path sum from top to bottom of a triangle; from row i, index j you may move to index j or j + 1
 * of the next row. Follow up: O(n) extra space.
 * Input: [[2],[3,4],[6,5,7],[4,1,8,3]] -> 11 (2 + 3 + 5 + 1)
 * Explanation: README.md
 */
public class Triangle {
    public static void main(String[] args) {
        List<List<Integer>> t = List.of(List.of(2), List.of(3, 4), List.of(6, 5, 7), List.of(4, 1, 8, 3));
        System.out.println(minimumTotal(t));            // 11
        System.out.println(minimumTotalTopDown(t));     // 11
    }

    /* Approach 1 - top-down: best[j] = cheapest way to reach row i, index j (from j - 1 or j above). Space O(n). */
    public static int minimumTotalTopDown(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] best = new int[n];
        best[0] = triangle.getFirst().getFirst();
        for (int i = 1; i < n; i++) {
            List<Integer> row = triangle.get(i);
            best[i] = best[i - 1] + row.get(i);                     // right edge: only from above-left
            for (int j = i - 1; j >= 1; j--) {                      // right to left so best[j - 1] is still the old row
                best[j] = Math.min(best[j - 1], best[j]) + row.get(j);
            }
            best[0] += row.getFirst();                              // left edge: only from above
        }
        int min = Integer.MAX_VALUE;
        for (int v : best) min = Math.min(min, v);
        return min;
    }

    /* Approach 2 - bottom-up: start from the last row; dp[j] = row[j] + min(dp[j], dp[j + 1]). The answer ends in dp[0]. */
    public static int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            List<Integer> row = triangle.get(i);
            for (int j = 0; j <= i; j++) {
                dp[j] = row.get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }
        return dp[0];
    }
}
