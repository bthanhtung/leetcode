package JumpGameII;

/*
 * 45. Jump Game II — https://leetcode.com/problems/jump-game-ii/
 * nums[i] is the maximum jump length from i. Return the minimum number of jumps to reach the last index
 * (it is always reachable).
 * Input: [2,3,1,1,4] -> 2 · [2,3,0,1,4] -> 2
 * Explanation: README.md
 */
public class JumpGameII {
    public static void main(String[] args) {
        System.out.println(jump(new int[]{2, 3, 1, 1, 4}));     // 2
    }

    /*
     * Implicit BFS: indices reachable with `jumps` jumps form a window ending at `end`. While scanning that window
     * track the farthest index reachable with one more jump; when we pass `end`, jump. Time O(n), space O(1).
     */
    public static int jump(int[] nums) {
        int jumps = 0, end = 0, farthest = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == end) {
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }

    /* Reference DP: best[i] = min jumps to reach i. O(n^2). */
    public static int jumpDp(int[] nums) {
        int n = nums.length;
        int[] best = new int[n];
        java.util.Arrays.fill(best, Integer.MAX_VALUE);
        best[0] = 0;
        for (int i = 0; i < n; i++) {
            if (best[i] == Integer.MAX_VALUE) continue;
            for (int j = i + 1; j <= Math.min(n - 1, i + nums[i]); j++) best[j] = Math.min(best[j], best[i] + 1);
        }
        return best[n - 1];
    }
}
