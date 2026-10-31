package ZigzagConversion;

/*
 * 6. Zigzag Conversion — https://leetcode.com/problems/zigzag-conversion/
 * Write s in a zigzag over numRows rows, then read it row by row.
 * "PAYPALISHIRING", 3 rows:   P   A   H   N
 *                             A P L S I I G      -> "PAHNAPLSIIGYIR"
 *                             Y   I   R
 * Explanation: README.md
 */
public class ZigzagConversion {
    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 3));             // PAHNAPLSIIGYIR
        System.out.println(convertFormula("PAYPALISHIRING", 4));      // PINALSIGYAHRPI
    }

    /* Approach 1 - walk down and up the rows, appending each character to its row. Time O(n). */
    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;
        StringBuilder[] rows = new StringBuilder[Math.min(numRows, s.length())];
        for (int i = 0; i < rows.length; i++) rows[i] = new StringBuilder();
        int row = 0, step = 1;
        for (char c : s.toCharArray()) {
            rows[row].append(c);
            if (row == 0) step = 1;
            else if (row == numRows - 1) step = -1;
            row += step;
        }
        StringBuilder out = new StringBuilder();
        for (StringBuilder r : rows) out.append(r);
        return out.toString();
    }

    /*
     * Approach 2 - the pattern repeats every cycle = 2 * numRows - 2 characters. Row r takes positions k*cycle + r and,
     * for middle rows, also k*cycle + cycle - r. Time O(n), no buckets.
     */
    public static String convertFormula(String s, int numRows) {
        if (numRows == 1) return s;
        int n = s.length(), cycle = 2 * numRows - 2;
        StringBuilder out = new StringBuilder(n);
        for (int r = 0; r < numRows; r++) {
            for (int k = 0; k + r < n; k += cycle) {
                out.append(s.charAt(k + r));
                if (r != 0 && r != numRows - 1 && k + cycle - r < n) out.append(s.charAt(k + cycle - r));
            }
        }
        return out.toString();
    }
}
