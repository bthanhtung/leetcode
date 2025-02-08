package com.tungdadev.codility;

public class Task2 {
    public static void main(String[] args) {
// int[] D = {5,8,2,7};
int[] D = {2,5,9,2,1,4};
// int X = 3;
int X = 4;
        System.out.println(solution(D, X));
    }

    public static int solution(int[] D, int X) {
        int n = D.length;
        int dayToComplete = 1;  // Ít nhất 1 ngày để hoàn thành
        int dayMin = D[0];  // Độ khó của nhiệm vụ đầu tiên trong ngày
        int dayMax = D[0];  // Độ khó của nhiệm vụ khó nhất trong ngày

        for (int i = 1; i < n; i++) {
            // Nếu nhiệm vụ hiện tại có độ khó chênh lệch quá X với nhiệm vụ khó nhất trong ngày, bắt đầu ngày mới
            if (Math.abs(D[i] - dayMax) > X) {
                dayToComplete++;  // Bắt đầu ngày mới
                dayMin = D[i];  // Đặt độ khó của nhiệm vụ đầu tiên trong ngày mới
                dayMax = D[i];  // Đặt độ khó của nhiệm vụ khó nhất trong ngày mới
            } else {
                // Nếu không, cập nhật độ khó tối thiểu và tối đa trong ngày
                dayMin = Math.min(dayMin, D[i]);
                dayMax = Math.max(dayMax, D[i]);
            }
        }

        return dayToComplete;
    }

    public int solution1(int[] D, int X) {
        int dayToComplete = 1;
        int diff = D[0];
        for (int j : D) {
            if ((-1) * (j - diff) > X) {
                dayToComplete = dayToComplete + 1;
            }
            diff = j;
        }
        return dayToComplete + 1;
    }
}
