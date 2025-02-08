package com.tungdadev.codility;

import java.util.Arrays;

public class Task7 {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 4, 1}, new int[]{1, 5, 11})); // Output: 2
        System.out.println(solution(new int[]{4, 4, 2, 4}, new int[]{5, 5, 2, 5})); // Output: 3
        System.out.println(solution(new int[]{2, 3, 4, 2}, new int[]{2, 5, 7, 2})); // Output: 2

        System.out.println(solution2(new int[]{1, 4, 1}, new int[]{1, 5, 11})); // Output: 2
        System.out.println(solution2(new int[]{4, 4, 2, 4}, new int[]{5, 5, 2, 5})); // Output: 3
        System.out.println(solution2(new int[]{2, 3, 4, 2}, new int[]{2, 5, 7, 2})); // Output: 2
    }

    public static int solution(int[] P, int[] S) {
        int pLength = P.length;
        int[] seatArr = new int[pLength];

        System.arraycopy(S, 0, seatArr, 0, pLength);

        int carUsed = 0;

        for(int i = 0; i< pLength; i++) {
            if(seatArr[i] > 0) {
                carUsed++;
                seatArr[i] -= P[i];

                int maxSeatIndex = 1;
                for(int j = i+1; j < pLength; j++) {
                    if(seatArr[j] > seatArr[maxSeatIndex]) {
                        maxSeatIndex = j;
                    }
                }

                if(seatArr[maxSeatIndex] > 0) {
                    int transfer = Math.min(P[i], seatArr[maxSeatIndex]);
                    seatArr[i] += transfer;
                    seatArr[maxSeatIndex] -= transfer;
                }
            }
        }
        return carUsed;
    }

    public static int solution2(int[] P, int[] S) {
        // Tính tổng số người cần ghế
        int totalPeople = Arrays.stream(P).sum();

        // Sắp xếp số ghế theo thứ tự giảm dần
        Arrays.sort(S);

        int carsUsed = 0;
        int i = S.length - 1; // Duyệt từ xe có nhiều ghế nhất

        // Phân phối số ghế cần thiết
        while (totalPeople > 0) {
            totalPeople -= S[i];
            carsUsed++;
            i--;
        }

        return carsUsed;
    }
}
