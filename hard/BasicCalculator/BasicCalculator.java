package BasicCalculator;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 224. Basic Calculator — https://leetcode.com/problems/basic-calculator/
 * Evaluate an expression with non-negative integers, '+', '-', '(', ')' and spaces. '-' may be unary
 * ("-(2 + 3)", "-1"). Do not use eval.
 * Input: "1 + 1" -> 2 · " 2-1 + 2 " -> 3 · "(1+(4+5+2)-3)+(6+8)" -> 23
 * Explanation: README.md
 */
public class BasicCalculator {
    public static void main(String[] args) {
        System.out.println(calculate("(1+(4+5+2)-3)+(6+8)"));      // 23
        System.out.println(calculateRecursive("- (3 + (4 + 5))"));  // -12
    }

    /*
     * Approach 1 - keep result and the current sign; '(' pushes (result, sign) and starts fresh, ')' pops and combines.
     * Time O(n).
     */
    public static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0, sign = 1, i = 0, n = s.length();
        while (i < n) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) num = num * 10 + (s.charAt(i++) - '0');
                result += sign * num;
                continue;
            }
            if (c == '+') sign = 1;
            else if (c == '-') sign = -1;
            else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                int signBefore = stack.pop(), resultBefore = stack.pop();
                result = resultBefore + signBefore * result;
            }
            i++;
        }
        return result;
    }

    /* Approach 2 - recursive descent: expr := term (('+'|'-') term)*, term := number | '(' expr ')' | '-' term. */
    public static int calculateRecursive(String s) {
        return expr(s.replace(" ", ""), new int[]{0});
    }

    private static int expr(String s, int[] pos) {
        int value = term(s, pos);
        while (pos[0] < s.length() && (s.charAt(pos[0]) == '+' || s.charAt(pos[0]) == '-')) {
            char op = s.charAt(pos[0]++);
            int rhs = term(s, pos);
            value = op == '+' ? value + rhs : value - rhs;
        }
        return value;
    }

    private static int term(String s, int[] pos) {
        char c = s.charAt(pos[0]);
        if (c == '-') {
            pos[0]++;
            return -term(s, pos);
        }
        if (c == '(') {
            pos[0]++;
            int v = expr(s, pos);
            pos[0]++;                                         // skip ')'
            return v;
        }
        int num = 0;
        while (pos[0] < s.length() && Character.isDigit(s.charAt(pos[0]))) num = num * 10 + (s.charAt(pos[0]++) - '0');
        return num;
    }
}
