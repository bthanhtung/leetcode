package GenerateParentheses;

import java.util.ArrayList;
import java.util.List;

/*
 * 22. Generate Parentheses — https://leetcode.com/problems/generate-parentheses/
 * Given n pairs of parentheses, generate all combinations of well-formed parentheses.
 * Input: 3 -> ["((()))","(()())","(())()","()(())","()()()"] · 1 -> ["()"]
 * Explanation: README.md
 */
public class GenerateParentheses {
    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));       // [((())), (()()), (())(), ()(()), ()()()]
        System.out.println(generateParenthesisDp(3));
    }

    /* Approach 1 - all 2^(2n) strings of '(' and ')', keep the balanced ones. Time O(2^(2n) * n). */
    public static List<String> generateParenthesisBruteForce(int n) {
        List<String> out = new ArrayList<>();
        for (int mask = 0; mask < 1 << (2 * n); mask++) {
            StringBuilder sb = new StringBuilder();
            int balance = 0;
            for (int i = 2 * n - 1; i >= 0 && balance >= 0; i--) {
                boolean open = (mask >> i & 1) == 1;
                sb.append(open ? '(' : ')');
                balance += open ? 1 : -1;
            }
            if (balance == 0 && sb.length() == 2 * n) out.add(sb.toString());
        }
        return out;
    }

    /*
     * Approach 2 - backtracking that only builds valid prefixes: add '(' while open < n, add ')' while close < open.
     * Time O(4^n / sqrt(n)) - the n-th Catalan number of results.
     */
    public static List<String> generateParenthesis(int n) {
        List<String> out = new ArrayList<>();
        backtrack(n, 0, 0, new StringBuilder(), out);
        return out;
    }

    private static void backtrack(int n, int open, int close, StringBuilder path, List<String> out) {
        if (path.length() == 2 * n) {
            out.add(path.toString());
            return;
        }
        if (open < n) {
            path.append('(');
            backtrack(n, open + 1, close, path, out);
            path.deleteCharAt(path.length() - 1);
        }
        if (close < open) {
            path.append(')');
            backtrack(n, open, close + 1, path, out);
            path.deleteCharAt(path.length() - 1);
        }
    }

    /* Approach 3 - every valid string is "(" + A + ")" + B where A has k pairs and B has n - 1 - k. Build from smaller n. */
    public static List<String> generateParenthesisDp(int n) {
        List<List<String>> dp = new ArrayList<>();
        dp.add(List.of(""));
        for (int size = 1; size <= n; size++) {
            List<String> cur = new ArrayList<>();
            for (int k = 0; k < size; k++) {
                for (String inside : dp.get(k)) {
                    for (String after : dp.get(size - 1 - k)) {
                        cur.add("(" + inside + ")" + after);
                    }
                }
            }
            dp.add(cur);
        }
        return dp.get(n);
    }
}
