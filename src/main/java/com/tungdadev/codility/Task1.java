package com.tungdadev.codility;

import java.util.HashMap;
import java.util.Map;

public class Task1 {
    public static void main(String[] args) {
        // int[] a = {1, 1, 3, 4, 4, 4};
        int[] a = {1, 2, 2, 2, 5, 5, 5, 8};
        // int[] a = {1, 1, 1, 1, 3, 3, 4, 4, 4, 4, 4};
        System.out.println(solution(a));
    }

    public static int solution(int[] A) {
        int minNumberOfMove = 0;
        Map<Integer, Integer> map = new HashMap<>();

        // count number of each number and put into map with key is number and value is number-counted
        for(int i = 0; i < A.length; i++) {
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);
        }

        if (map.size() == 1) {
            return A.length;
        }

        // 11 31 43
        // 11 23 53 81
        // 14 32 45
        // 103

        for(int number : map.keySet()) {
            int countValue = map.get(number);
            int numberDelete = 0;
            if(countValue > number) {
                numberDelete = countValue - number;
            } else if(countValue < number) {
                numberDelete = number - countValue;
            }
            if (countValue == 1 || number == 0) {
                numberDelete = 1;
            }
            minNumberOfMove = minNumberOfMove + numberDelete;
        }

        return minNumberOfMove;
    }

}
