package RemoveInvalidParentheses;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/*
 * 301. Remove Invalid Parentheses — https://leetcode.com/problems/remove-invalid-parentheses/
 * Remove the minimum number of parentheses to make s valid (letters stay). Return all distinct results, any order.
 * Input: "()())()" -> ["(())()","()()()"] · "(a)())()" -> ["(a())()","(a)()()"] · ")(" -> [""]
 * Explanation: README.md
 */
public class RemoveInvalidParentheses {
    public static void main(String[] args) {
        System.out.println(removeInvalidParentheses("()())()"));           // [(())(), ()()()]
        System.out.println(removeInvalidParenthesesBfs("(a)())()"));
    }

    private static boolean valid(String s) {
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') balance++;
            else if (c == ')' && --balance < 0) return false;
        }
        return balance == 0;
    }

    /* Approach 1 - BFS over strings by number of removals; the first level containing a valid string is the answer. */
    public static List<String> removeInvalidParenthesesBfs(String s) {
        Set<String> seen = new HashSet<>(List.of(s));
        Queue<String> level = new ArrayDeque<>(List.of(s));
        while (!level.isEmpty()) {
            List<String> found = new ArrayList<>();
            Queue<String> next = new ArrayDeque<>();
            for (String cur : level) {
                if (valid(cur)) found.add(cur);
                if (!found.isEmpty()) continue;                // no need to go deeper
                for (int i = 0; i < cur.length(); i++) {
                    char c = cur.charAt(i);
                    if (c != '(' && c != ')') continue;
                    String t = cur.substring(0, i) + cur.substring(i + 1);
                    if (seen.add(t)) next.add(t);
                }
            }
            if (!found.isEmpty()) return found;
            level = next;
        }
        return List.of("");
    }

    /*
     * Approach 2 - count exactly how many '(' and ')' must go (unmatched ones), then backtrack deleting only that many,
     * skipping equal neighbours to avoid duplicate results.
     */
    public static List<String> removeInvalidParentheses(String s) {
        int open = 0, close = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') open++;
            else if (c == ')') {
                if (open > 0) open--;
                else close++;
            }
        }
        Set<String> out = new HashSet<>();
        remove(s, 0, open, close, out);
        return new ArrayList<>(out);
    }

    private static void remove(String s, int start, int open, int close, Set<String> out) {
        if (open == 0 && close == 0) {
            if (valid(s)) out.add(s);
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (i > start && s.charAt(i) == s.charAt(i - 1)) continue;          // removing either of equal neighbours is the same
            String t = s.substring(0, i) + s.substring(i + 1);
            if (s.charAt(i) == ')' && close > 0) remove(t, i, open, close - 1, out);
            else if (s.charAt(i) == '(' && open > 0) remove(t, i, open - 1, close, out);
        }
    }
}
