package IntegerToRoman;

/*
 * 12. Integer to Roman — https://leetcode.com/problems/integer-to-roman/
 * Convert an integer 1..3999 to a roman numeral (I=1, V=5, X=10, L=50, C=100, D=500, M=1000; subtractive forms
 * IV, IX, XL, XC, CD, CM).
 * Input: 3749 -> "MMMDCCXLIX" · 58 -> "LVIII" · 1994 -> "MCMXCIV"
 * Explanation: README.md
 */
public class IntegerToRoman {
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static void main(String[] args) {
        System.out.println(intToRoman(1994));         // MCMXCIV
        System.out.println(intToRomanDigits(3749));   // MMMDCCXLIX
    }

    /* Approach 1 - greedy: take the largest value that still fits, as many times as it fits. Time O(1) (bounded output). */
    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VALUES.length && num > 0; i++) {
            while (num >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
        }
        return sb.toString();
    }

    /* Approach 2 - one lookup table per decimal digit: thousands, hundreds, tens, ones. Time O(1). */
    public static String intToRomanDigits(int num) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return thousands[num / 1000] + hundreds[num / 100 % 10] + tens[num / 10 % 10] + ones[num % 10];
    }
}
