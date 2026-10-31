package DailyTemperatures;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 739. Daily Temperatures — https://leetcode.com/problems/daily-temperatures/
 * For each day, how many days until a warmer temperature? 0 if none.
 * Input: [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0] · [30,40,50,60] -> [1,1,1,0]
 * Explanation: README.md
 */
public class DailyTemperatures {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        // [1, 1, 4, 2, 1, 1, 0, 0]
    }

    /* Approach 1 - scan right from every day. Time O(n^2). */
    public static int[] dailyTemperaturesBruteForce(int[] t) {
        int[] out = new int[t.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = i + 1; j < t.length; j++) {
                if (t[j] > t[i]) {
                    out[i] = j - i;
                    break;
                }
            }
        }
        return out;
    }

    /*
     * Approach 2 - monotonic stack of indexes with decreasing temperatures, all still waiting for a warmer day.
     * A warmer day resolves every colder index on top. Each index is pushed and popped once: O(n).
     */
    public static int[] dailyTemperatures(int[] t) {
        int[] out = new int[t.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < t.length; i++) {
            while (!stack.isEmpty() && t[stack.peek()] < t[i]) {
                int j = stack.pop();
                out[j] = i - j;
            }
            stack.push(i);
        }
        return out;
    }

    /*
     * Approach 3 - from the right, no stack: to find day i's warmer day, start at i + 1 and jump by the answers already
     * computed (a day that is not warmer tells us where its own warmer day is). Amortized O(n), O(1) extra space.
     */
    public static int[] dailyTemperaturesJumps(int[] t) {
        int n = t.length;
        int[] out = new int[n];
        int hottest = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (t[i] >= hottest) {
                hottest = t[i];                    // nothing to the right is warmer
                continue;
            }
            int j = i + 1;
            while (t[j] <= t[i]) j += out[j];
            out[i] = j - i;
        }
        return out;
    }
}
