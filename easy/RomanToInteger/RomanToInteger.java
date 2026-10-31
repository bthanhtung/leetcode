package RomanToInteger;

import java.util.Map;

/*
 * 13. Roman to Integer — https://leetcode.com/problems/roman-to-integer/
 * Symbols: I=1, V=5, X=10, L=50, C=100, D=500, M=1000. Normally written largest to smallest; a smaller symbol before a
 * larger one is subtracted (IV=4, IX=9, XL=40, XC=90, CD=400, CM=900). Convert a valid roman numeral (1..3999) to an integer.
 * Input: "III" -> 3 · "LVIII" -> 58 · "MCMXCIV" -> 1994
 * Explanation: README.md
 */
public class RomanToInteger {
    private static final Map<Character, Integer> VALUE = Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000);

    public static void main(String[] args) {
        System.out.println(romanToIntLookAhead("MCMXCIV"));   // 1994
        System.out.println(romanToIntBackward("LVIII"));      // 58
        System.out.println(romanToIntPairs("IX"));            // 9
    }

    /* Approach 1 - left to right: subtract a symbol when the next one is larger. Time O(n), space O(1). */
    public static int romanToIntLookAhead(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int v = VALUE.get(s.charAt(i));
            if (i + 1 < s.length() && v < VALUE.get(s.charAt(i + 1))) {
                total -= v;
            } else {
                total += v;
            }
        }
        return total;
    }

    /* Approach 2 - right to left: subtract when smaller than the previous (right-hand) symbol. Time O(n), space O(1). */
    public static int romanToIntBackward(String s) {
        int total = 0, prev = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int v = VALUE.get(s.charAt(i));
            total += v < prev ? -v : v;
            prev = v;
        }
        return total;
    }

    /* Approach 3 - match the six two-letter pairs first, then single symbols. Time O(n), space O(1). Most readable. */
    public static int romanToIntPairs(String s) {
        Map<String, Integer> pairs = Map.of("IV", 4, "IX", 9, "XL", 40, "XC", 90, "CD", 400, "CM", 900);
        int total = 0;
        for (int i = 0; i < s.length(); ) {
            if (i + 1 < s.length() && pairs.containsKey(s.substring(i, i + 2))) {
                total += pairs.get(s.substring(i, i + 2));
                i += 2;
            } else {
                total += VALUE.get(s.charAt(i++));
            }
        }
        return total;
    }
}
