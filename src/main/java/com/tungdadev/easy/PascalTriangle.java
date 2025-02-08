package com.tungdadev.easy;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
    public static void main(String[] args) {
        int rows = 5;
        System.out.println(generate(rows)); // Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
    }

    private static List<List<Integer>> generate(int numRows) {
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
}
