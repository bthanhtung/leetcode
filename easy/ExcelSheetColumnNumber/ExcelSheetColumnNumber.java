package ExcelSheetColumnNumber;

/*
 * 171. Excel Sheet Column Number — https://leetcode.com/problems/excel-sheet-column-number/
 * Given an Excel column title ("A", "B", ..., "Z", "AA", "AB", ...), return its column number.
 * Input: "A" -> 1 · "AB" -> 28 · "ZY" -> 701
 * Explanation: README.md
 */
public class ExcelSheetColumnNumber {
    public static void main(String[] args) {
        System.out.println(titleToNumber("AB"));            // 28
        System.out.println(titleToNumberPowers("ZY"));      // 701
    }

    /* Approach 1 - Horner's rule in base 26 with digits 1..26. Time O(n). */
    public static int titleToNumber(String columnTitle) {
        int result = 0;
        for (char c : columnTitle.toCharArray()) {
            result = result * 26 + (c - 'A' + 1);
        }
        return result;
    }

    /* Approach 2 - from the right: digit * 26^position. Time O(n). */
    public static int titleToNumberPowers(String columnTitle) {
        int result = 0;
        long power = 1;
        for (int i = columnTitle.length() - 1; i >= 0; i--) {
            result += (int) ((columnTitle.charAt(i) - 'A' + 1) * power);
            power *= 26;
        }
        return result;
    }
}
