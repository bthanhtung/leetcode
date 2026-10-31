package NumberOfAtoms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;

/*
 * 726. Number of Atoms — https://leetcode.com/problems/number-of-atoms/
 * Parse a chemical formula (elements like H, Mg; optional counts; parenthesized groups with multipliers) and return
 * the atom counts as "element[count]..." with elements sorted and count 1 omitted.
 * Input: "H2O" -> "H2O" · "Mg(OH)2" -> "H2MgO2" · "K4(ON(SO3)2)2" -> "K4N2O14S4"
 * Explanation: README.md
 */
public class NumberOfAtoms {
    public static void main(String[] args) {
        System.out.println(countOfAtoms("K4(ON(SO3)2)2"));             // K4N2O14S4
        System.out.println(countOfAtomsRecursive("Mg(OH)2"));          // H2MgO2
    }

    /* Approach 1 - a stack of maps: '(' pushes a new map, ')k' pops it, multiplies by k and merges into the outer map. */
    public static String countOfAtoms(String formula) {
        Deque<Map<String, Integer>> stack = new ArrayDeque<>();
        stack.push(new TreeMap<>());
        int i = 0, n = formula.length();
        while (i < n) {
            char c = formula.charAt(i);
            if (c == '(') {
                stack.push(new TreeMap<>());
                i++;
            } else if (c == ')') {
                i++;
                int start = i;
                while (i < n && Character.isDigit(formula.charAt(i))) i++;
                int mult = start == i ? 1 : Integer.parseInt(formula.substring(start, i));
                Map<String, Integer> inner = stack.pop();
                for (Map.Entry<String, Integer> e : inner.entrySet()) stack.peek().merge(e.getKey(), e.getValue() * mult, Integer::sum);
            } else {
                int start = i++;
                while (i < n && Character.isLowerCase(formula.charAt(i))) i++;
                String name = formula.substring(start, i);
                start = i;
                while (i < n && Character.isDigit(formula.charAt(i))) i++;
                int count = start == i ? 1 : Integer.parseInt(formula.substring(start, i));
                stack.peek().merge(name, count, Integer::sum);
            }
        }
        return format(stack.pop());
    }

    /* Approach 2 - recursive descent: parse() reads until ')' or the end and returns that group's counts. */
    public static String countOfAtomsRecursive(String formula) {
        return format(parse(formula, new int[]{0}));
    }

    private static Map<String, Integer> parse(String s, int[] pos) {
        Map<String, Integer> counts = new TreeMap<>();
        while (pos[0] < s.length() && s.charAt(pos[0]) != ')') {
            if (s.charAt(pos[0]) == '(') {
                pos[0]++;
                Map<String, Integer> inner = parse(s, pos);
                pos[0]++;                                         // skip ')'
                int mult = number(s, pos);
                inner.forEach((k, v) -> counts.merge(k, v * mult, Integer::sum));
            } else {
                int start = pos[0]++;
                while (pos[0] < s.length() && Character.isLowerCase(s.charAt(pos[0]))) pos[0]++;
                String name = s.substring(start, pos[0]);
                counts.merge(name, number(s, pos), Integer::sum);
            }
        }
        return counts;
    }

    private static int number(String s, int[] pos) {
        int start = pos[0];
        while (pos[0] < s.length() && Character.isDigit(s.charAt(pos[0]))) pos[0]++;
        return start == pos[0] ? 1 : Integer.parseInt(s.substring(start, pos[0]));
    }

    private static String format(Map<String, Integer> counts) {
        StringBuilder sb = new StringBuilder();
        counts.forEach((k, v) -> sb.append(k).append(v > 1 ? v.toString() : ""));
        return sb.toString();
    }
}
