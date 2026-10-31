package LongestValidParentheses;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 32. Longest Valid Parentheses — https://leetcode.com/problems/longest-valid-parentheses/
 * Return the length of the longest valid (well-formed) parentheses substring.
 * Input: "(()" -> 2 · ")()())" -> 4 · "" -> 0
 * Explanation: README.md
 */
public class LongestValidParentheses {
    public static void main(String[] args) {
        System.out.println(longestValidParentheses(")()())"));            // 4
        System.out.println(longestValidParenthesesCounters("(()"));       // 2
    }

    /*
     * Approach 1 - stack of indexes; the bottom is the index just before the current valid run. ')' pops; if the stack
     * empties, this ')' becomes the new base, else the run length is i - stack.peek(). Time O(n).
     */
    public static int longestValidParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) stack.push(i);
                else best = Math.max(best, i - stack.peek());
            }
        }
        return best;
    }

    /*
     * Approach 2 - dp[i] = longest valid substring ending at i. For s[i] = ')': after "()" add dp[i-2]; after "))" check
     * the character before the inner valid block and glue the block before that. Time O(n).
     */
    public static int longestValidParenthesesDp(String s) {
        int n = s.length(), best = 0;
        int[] dp = new int[n];
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) != ')') continue;
            if (s.charAt(i - 1) == '(') {
                dp[i] = 2 + (i >= 2 ? dp[i - 2] : 0);
            } else {
                int open = i - dp[i - 1] - 1;                  // the '(' that should match s[i]
                if (open >= 0 && s.charAt(open) == '(') dp[i] = dp[i - 1] + 2 + (open >= 1 ? dp[open - 1] : 0);
            }
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    /* Approach 3 - left-to-right counters (reset when ')' wins) plus right-to-left (reset when '(' wins). O(n), O(1). */
    public static int longestValidParenthesesCounters(String s) {
        int best = 0, open = 0, close = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') open++;
            else close++;
            if (open == close) best = Math.max(best, 2 * close);
            else if (close > open) open = close = 0;
        }
        open = close = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') open++;
            else close++;
            if (open == close) best = Math.max(best, 2 * open);
            else if (open > close) open = close = 0;
        }
        return best;
    }
}
