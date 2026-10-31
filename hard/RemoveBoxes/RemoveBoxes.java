package RemoveBoxes;

/*
 * 546. Remove Boxes — https://leetcode.com/problems/remove-boxes/
 * Boxes have colors. Removing k adjacent boxes of the same color earns k * k points. Remove all boxes, maximizing points.
 * Input: [1,3,2,2,2,3,4,3,1] -> 23 · [1,1,1] -> 9 · [1] -> 1
 * Explanation: README.md
 */
public class RemoveBoxes {
    public static void main(String[] args) {
        System.out.println(removeBoxes(new int[]{1, 3, 2, 2, 2, 3, 4, 3, 1}));     // 23
    }

    /*
     * dp[l][r][k] = best for boxes l..r when k boxes equal to boxes[l] are already glued to its left.
     * Option A: remove boxes[l] together with its k companions now: (k+1)^2 + dp[l+1][r][0].
     * Option B: for some m in (l, r] with boxes[m] == boxes[l], first clear l+1..m-1, then glue l (and its k) onto m:
     *           dp[l+1][m-1][0] + dp[m][r][k+1].  Time O(n^4), space O(n^3).
     */
    public static int removeBoxes(int[] boxes) {
        int n = boxes.length;
        return best(boxes, 0, n - 1, 0, new int[n][n][n]);
    }

    private static int best(int[] b, int l, int r, int k, int[][][] memo) {
        if (l > r) return 0;
        while (l < r && b[l + 1] == b[l]) {                      // merge an initial run into the glued count
            l++;
            k++;
        }
        if (memo[l][r][k] > 0) return memo[l][r][k];
        int result = (k + 1) * (k + 1) + best(b, l + 1, r, 0, memo);
        for (int m = l + 1; m <= r; m++) {
            if (b[m] == b[l]) result = Math.max(result, best(b, l + 1, m - 1, 0, memo) + best(b, m, r, k + 1, memo));
        }
        return memo[l][r][k] = result;
    }

    /** Reference for tests: try removing every maximal run in every order (tiny inputs). */
    static int bruteForce(java.util.List<Integer> boxes) {
        if (boxes.isEmpty()) return 0;
        int best = 0;
        for (int i = 0; i < boxes.size(); ) {
            int j = i;
            while (j < boxes.size() && boxes.get(j).equals(boxes.get(i))) j++;
            java.util.List<Integer> rest = new java.util.ArrayList<>(boxes.subList(0, i));
            rest.addAll(boxes.subList(j, boxes.size()));
            best = Math.max(best, (j - i) * (j - i) + bruteForce(rest));
            i = j;
        }
        return best;
    }
}
