package ValidParentheses;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/*
 * 20. Valid Parentheses — https://leetcode.com/problems/valid-parentheses/
 * Given a string s containing just '(', ')', '{', '}', '[' and ']', determine if it is valid: open brackets are closed by
 * the same type, in the correct order, and every close bracket has a matching open one.
 * Input: "()[]{}" -> true · "(]" -> false · "([)]" -> false · "{[]}" -> true
 * Explanation: README.md
 */
public class ValidParentheses {
    public static void main(String[] args) {
        System.out.println(isValidStack("{[]}"));      // true
        System.out.println(isValidExpected("([)]"));   // false
        System.out.println(isValidReplace("()[]{}"));  // true
    }

    /* Approach 1 - stack of open brackets; a close bracket must match the top. Time O(n), space O(n). */
    public static boolean isValidStack(String s) {
        Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    /* Approach 2 - push the closing bracket we expect next; a close must equal the top. Same complexity, less lookup. */
    public static boolean isValidExpected(String s) {
        if (s.length() % 2 == 1) {
            return false;                                    // odd length can never balance
        }
        Deque<Character> expected = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(' -> expected.push(')');
                case '[' -> expected.push(']');
                case '{' -> expected.push('}');
                default -> {
                    if (expected.isEmpty() || expected.pop() != c) {
                        return false;
                    }
                }
            }
        }
        return expected.isEmpty();
    }

    /* Approach 3 - repeatedly delete "()", "[]", "{}" until nothing changes. Time O(n^2), space O(n). Intuitive, slow. */
    public static boolean isValidReplace(String s) {
        String prev;
        do {
            prev = s;
            s = s.replace("()", "").replace("[]", "").replace("{}", "");
        } while (!s.equals(prev));
        return s.isEmpty();
    }
}
