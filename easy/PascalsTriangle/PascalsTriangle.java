package PascalsTriangle;

import java.util.ArrayList;
import java.util.List;

/*
 * 118. Pascal's Triangle — https://leetcode.com/problems/pascals-triangle/
 * Given numRows, return the first numRows of Pascal's triangle: each number is the sum of the two numbers directly above it.
 * Input: 5 -> [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 * Explanation: README.md
 */
public class PascalsTriangle {
    public static void main(String[] args) {
        System.out.println(generate(5));            // [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]]
        System.out.println(generateBinomial(5));    // same
    }

    /* Approach 1 - build each row from the previous: ends are 1, inside is the sum of the two above. Time O(n^2), space O(1) extra. */
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
                }
            }
            result.add(row);
        }
        return result;
    }

    /* Approach 2 - row i is C(i, 0..i); compute C(i, j) = C(i, j-1) * (i - j + 1) / j without the row above. Time O(n^2). */
    public static List<List<Integer>> generateBinomial(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            long c = 1;
            for (int j = 0; j <= i; j++) {
                row.add((int) c);
                c = c * (i - j) / (j + 1);
            }
            result.add(row);
        }
        return result;
    }
}
