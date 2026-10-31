package JumpGame;

/*
 * 55. Jump Game — https://leetcode.com/problems/jump-game/
 * nums[i] is the maximum jump length from index i. Starting at index 0, can you reach the last index?
 * Input: [2,3,1,1,4] -> true · [3,2,1,0,4] -> false
 * Explanation: README.md
 */
public class JumpGame {
    public static void main(String[] args) {
        System.out.println(canJump(new int[]{2, 3, 1, 1, 4}));     // true
        System.out.println(canJump(new int[]{3, 2, 1, 0, 4}));     // false
    }

    /* Approach 1 - track the farthest reachable index; standing beyond it means we are stuck. O(n). */
    public static boolean canJump(int[] nums) {
        int reach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > reach) return false;
            reach = Math.max(reach, i + nums[i]);
        }
        return true;
    }

    /* Approach 2 - walk backwards moving the goal to any index that can reach it. */
    public static boolean canJumpBackward(int[] nums) {
        int goal = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) if (i + nums[i] >= goal) goal = i;
        return goal == 0;
    }
}
