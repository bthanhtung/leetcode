package PascalsTriangleII;

import java.util.ArrayList;
import java.util.List;

/*
 * 119. Pascal's Triangle II — https://leetcode.com/problems/pascals-triangle-ii/
 * Return the rowIndex-th (0-indexed) row of Pascal's triangle. Follow up: O(rowIndex) extra space.
 * Input: 3 -> [1,3,3,1] · 0 -> [1] · 1 -> [1,1]
 * Explanation: README.md
 */
public class PascalsTriangleII {
    public static void main(String[] args) {
        System.out.println(getRow(3));             // [1, 3, 3, 1]
        System.out.println(getRowBinomial(4));     // [1, 4, 6, 4, 1]
    }

    /* Approach 1 - build every row from the previous one. Time O(k^2), space O(k) for two rows. */
    public static List<Integer> getRowAllRows(int rowIndex) {
        List<Integer> row = List.of(1);
        for (int r = 1; r <= rowIndex; r++) {
            List<Integer> next = new ArrayList<>();
            next.add(1);
            for (int j = 1; j < r; j++) next.add(row.get(j - 1) + row.get(j));
            next.add(1);
            row = next;
        }
        return row;
    }

    /* Approach 2 - one array; update from right to left so row[j - 1] is still the old value. Time O(k^2), space O(k). */
    public static List<Integer> getRow(int rowIndex) {
        int[] row = new int[rowIndex + 1];
        row[0] = 1;
        for (int r = 1; r <= rowIndex; r++) {
            for (int j = r; j >= 1; j--) {
                row[j] += row[j - 1];
            }
        }
        List<Integer> out = new ArrayList<>();
        for (int v : row) out.add(v);
        return out;
    }

    /* Approach 3 - C(k, j) = C(k, j - 1) * (k - j + 1) / j. Time O(k). */
    public static List<Integer> getRowBinomial(int rowIndex) {
        List<Integer> out = new ArrayList<>();
        long c = 1;
        for (int j = 0; j <= rowIndex; j++) {
            out.add((int) c);
            c = c * (rowIndex - j) / (j + 1);
        }
        return out;
    }
}
