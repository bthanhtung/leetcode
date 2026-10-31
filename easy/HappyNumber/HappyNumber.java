package HappyNumber;

import java.util.HashSet;
import java.util.Set;

/*
 * 202. Happy Number — https://leetcode.com/problems/happy-number/
 * Replace a positive integer by the sum of the squares of its digits and repeat. It is happy if this process reaches 1;
 * otherwise it loops forever in a cycle that does not include 1. Return true if n is happy.
 * Input: 19 -> true (1+81=82, 64+4=68, 36+64=100, 1) · 2 -> false
 * Explanation: README.md
 */
public class HappyNumber {
    public static void main(String[] args) {
        System.out.println(isHappySet(19));       // true
        System.out.println(isHappy(2));           // false
        System.out.println(isHappyKnownCycle(7)); // true
    }

    static int next(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    /* Approach 1 - remember every number seen; seeing one twice means a cycle. Time O(log n), space O(log n). */
    public static boolean isHappySet(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && seen.add(n)) {
            n = next(n);
        }
        return n == 1;
    }

    /* Approach 2 - Floyd's tortoise and hare: slow moves 1 step, fast 2; they meet inside the cycle. Space O(1). */
    public static boolean isHappy(int n) {
        int slow = n;
        int fast = next(n);
        while (fast != 1 && slow != fast) {
            slow = next(slow);
            fast = next(next(fast));
        }
        return fast == 1;
    }

    /* Approach 3 - math fact: every unhappy number falls into the cycle 4 -> 16 -> 37 -> 58 -> 89 -> 145 -> 42 -> 20 -> 4. */
    public static boolean isHappyKnownCycle(int n) {
        while (n != 1 && n != 4) {
            n = next(n);
        }
        return n == 1;
    }
}
