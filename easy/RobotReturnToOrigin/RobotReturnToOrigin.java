package RobotReturnToOrigin;

/*
 * 657. Robot Return to Origin — https://leetcode.com/problems/robot-return-to-origin/
 * A robot starts at (0, 0) and follows moves of 'U', 'D', 'L', 'R'. Does it end at the origin?
 * Input: "UD" -> true · "LL" -> false
 * Explanation: README.md
 */
public class RobotReturnToOrigin {
    public static void main(String[] args) {
        System.out.println(judgeCircle("UD"));         // true
        System.out.println(judgeCircleCount("LL"));    // false
    }

    /* Approach 1 - simulate x, y. Time O(n), space O(1). */
    public static boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        for (char m : moves.toCharArray()) {
            switch (m) {
                case 'U' -> y++;
                case 'D' -> y--;
                case 'L' -> x--;
                case 'R' -> x++;
            }
        }
        return x == 0 && y == 0;
    }

    /* Approach 2 - back home iff #U == #D and #L == #R. Time O(n). */
    public static boolean judgeCircleCount(String moves) {
        int[] count = new int[128];
        for (char m : moves.toCharArray()) count[m]++;
        return count['U'] == count['D'] && count['L'] == count['R'];
    }
}
