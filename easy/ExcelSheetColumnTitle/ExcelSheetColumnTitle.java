package ExcelSheetColumnTitle;

/*
 * 168. Excel Sheet Column Title — https://leetcode.com/problems/excel-sheet-column-title/
 * Return the Excel column title for a column number: 1 -> A, 26 -> Z, 27 -> AA, 28 -> AB, 701 -> ZY.
 * Explanation: README.md
 */
public class ExcelSheetColumnTitle {
    public static void main(String[] args) {
        System.out.println(convertToTitle(701));            // ZY
        System.out.println(convertToTitleRecursive(28));    // AB
    }

    /* Approach 1 - digits run 1..26 (there is no zero), so shift to 0..25 with n - 1 before taking the remainder. */
    public static String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            sb.append((char) ('A' + columnNumber % 26));
            columnNumber /= 26;
        }
        return sb.reverse().toString();
    }

    /* Approach 2 - the same idea recursively: title(n) = title((n - 1) / 26) + letter((n - 1) % 26). */
    public static String convertToTitleRecursive(int n) {
        return n == 0 ? "" : convertToTitleRecursive((n - 1) / 26) + (char) ('A' + (n - 1) % 26);
    }
}
