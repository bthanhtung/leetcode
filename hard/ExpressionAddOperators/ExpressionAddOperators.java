package ExpressionAddOperators;

import java.util.ArrayList;
import java.util.List;

/*
 * 282. Expression Add Operators — https://leetcode.com/problems/expression-add-operators/
 * Insert '+', '-' or '*' between the digits of num (or nothing, to make multi-digit operands) so the expression
 * evaluates to target. Operands may not have leading zeros. Return all such expressions.
 * Input: "123", 6 -> ["1*2*3","1+2+3"] · "232", 8 -> ["2*3+2","2+3*2"] · "3456237490", 9191 -> []
 * Explanation: README.md
 */
public class ExpressionAddOperators {
    public static void main(String[] args) {
        System.out.println(addOperators("123", 6));        // [1+2+3, 1*2*3]
        System.out.println(addOperators("105", 5));        // [1*0+5, 10-5]
    }

    /*
     * Backtracking over the next operand num[pos..i]. Track the value so far and the last term added (signed). For '*'
     * undo that last term and add last * operand instead: value - last + last * operand. Time O(4^n * n).
     */
    public static List<String> addOperators(String num, int target) {
        List<String> out = new ArrayList<>();
        backtrack(num, target, 0, 0, 0, new StringBuilder(), out);
        return out;
    }

    private static void backtrack(String num, int target, int pos, long value, long last, StringBuilder expr, List<String> out) {
        if (pos == num.length()) {
            if (value == target) out.add(expr.toString());
            return;
        }
        int len = expr.length();
        long operand = 0;
        for (int i = pos; i < num.length(); i++) {
            if (i > pos && num.charAt(pos) == '0') break;          // no leading zeros like "05"
            operand = operand * 10 + (num.charAt(i) - '0');
            String text = num.substring(pos, i + 1);
            if (pos == 0) {
                backtrack(num, target, i + 1, operand, operand, expr.append(text), out);
                expr.setLength(len);
                continue;
            }
            backtrack(num, target, i + 1, value + operand, operand, expr.append('+').append(text), out);
            expr.setLength(len);
            backtrack(num, target, i + 1, value - operand, -operand, expr.append('-').append(text), out);
            expr.setLength(len);
            backtrack(num, target, i + 1, value - last + last * operand, last * operand, expr.append('*').append(text), out);
            expr.setLength(len);
        }
    }

    /** Reference evaluator for tests: '*' before '+' and '-'. */
    static long evaluate(String expr) {
        long total = 0, term = 0, operand = 0;
        char op = '+';
        for (int i = 0; i <= expr.length(); i++) {
            char c = i < expr.length() ? expr.charAt(i) : '+';
            if (Character.isDigit(c)) {
                operand = operand * 10 + (c - '0');
                continue;
            }
            if (op == '*') term *= operand;
            else {
                total += term;
                term = op == '+' ? operand : -operand;
            }
            op = c;
            operand = 0;
        }
        return total + term;
    }
}
