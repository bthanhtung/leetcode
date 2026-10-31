package EvaluateReversePolishNotation;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 150. Evaluate Reverse Polish Notation — https://leetcode.com/problems/evaluate-reverse-polish-notation/
 * Evaluate an arithmetic expression in Reverse Polish Notation with + - * / (integer division truncates toward zero).
 * Input: ["2","1","+","3","*"] -> 9 · ["4","13","5","/","+"] -> 6 · ["10","6","9","3","+","-11","*","/","*","17","+","5","+"] -> 22
 * Explanation: README.md
 */
public class EvaluateReversePolishNotation {
    public static void main(String[] args) {
        System.out.println(evalRPN(new String[]{"2", "1", "+", "3", "*"}));                // 9
        System.out.println(evalRPNRecursive(new String[]{"4", "13", "5", "/", "+"}));     // 6
    }

    /* Approach 1 - push numbers; an operator pops two (right operand first), pushes the result. Time O(n). */
    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String t : tokens) {
            switch (t) {
                case "+" -> stack.push(stack.pop() + stack.pop());
                case "-" -> { int b = stack.pop(), a = stack.pop(); stack.push(a - b); }
                case "*" -> stack.push(stack.pop() * stack.pop());
                case "/" -> { int b = stack.pop(), a = stack.pop(); stack.push(a / b); }
                default -> stack.push(Integer.parseInt(t));
            }
        }
        return stack.pop();
    }

    /* Approach 2 - read from the end: the last token is the root; its right operand comes before its left operand. */
    public static int evalRPNRecursive(String[] tokens) {
        return eval(tokens, new int[]{tokens.length - 1});
    }

    private static int eval(String[] tokens, int[] pos) {
        String t = tokens[pos[0]--];
        if (!"+-*/".contains(t) || t.length() > 1) return Integer.parseInt(t);
        int right = eval(tokens, pos), left = eval(tokens, pos);
        return switch (t) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            default -> left / right;
        };
    }
}
