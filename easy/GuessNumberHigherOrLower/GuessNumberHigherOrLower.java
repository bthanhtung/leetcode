package GuessNumberHigherOrLower;

import java.util.function.IntUnaryOperator;

/*
 * 374. Guess Number Higher or Lower — https://leetcode.com/problems/guess-number-higher-or-lower/
 * A number is picked from 1..n. guess(num) returns -1 if num is higher than the pick, 1 if lower, 0 if equal.
 * Return the picked number.
 * Input: n = 10, pick = 6 -> 6 · n = 1, pick = 1 -> 1
 * Explanation: README.md
 */
public class GuessNumberHigherOrLower {
    public static void main(String[] args) {
        System.out.println(guessNumber(10, api(6)));                         // 6
        System.out.println(guessNumber(Integer.MAX_VALUE, api(1702766719))); // 1702766719
    }

    /** Builds LeetCode's guess() API for a given pick. */
    public static IntUnaryOperator api(int pick) {
        return num -> Integer.compare(pick, num);
    }

    /* Approach 1 - try 1, 2, 3, ... Time O(n). */
    public static int guessNumberLinear(int n, IntUnaryOperator guess) {
        for (int i = 1; i < n; i++) {
            if (guess.applyAsInt(i) == 0) return i;
        }
        return n;
    }

    /* Approach 2 - binary search on 1..n. Time O(log n). */
    public static int guessNumber(int n, IntUnaryOperator guess) {
        int lo = 1, hi = n;
        while (true) {
            int mid = lo + (hi - lo) / 2;            // (lo + hi) / 2 overflows when n is near Integer.MAX_VALUE
            int answer = guess.applyAsInt(mid);
            if (answer == 0) return mid;
            if (answer < 0) hi = mid - 1;            // mid is too high
            else lo = mid + 1;                       // mid is too low
        }
    }
}
