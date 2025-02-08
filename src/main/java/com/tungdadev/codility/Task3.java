package com.tungdadev.codility;

import java.util.HashMap;
import java.util.Map;

public class Task3 {
    public static void main(String[] args) {
        int[] A = {4, 2, 5, 8, 7, 3, 7};
        System.out.println(solution(A)); // Output: 2
        System.out.println(solutionz(A)); // Output: 2
    }

    public static int solution(int[] A) {
        int n = A.length;
        boolean[] paired = new boolean[n]; // Đánh dấu phần tử đã được ghép cặp
        int count = 0;

        // Duyệt qua từng cặp liền kề
        for (int i = 0; i < n; i++) {
            int next = (i + 1) % n; // Vòng tròn, phần tử cuối nối với phần tử đầu
            if (!paired[i] && !paired[next] && (A[i] % 2 == A[next] % 2)) {
                count++;
                paired[i] = true;  // Đánh dấu phần tử đã được ghép
                paired[next] = true;
            }
        }
        return count;
    }

    public static int solutionz(int[] A) {
        int n = A.length;
        Map<Integer, Boolean> paired = new HashMap<>(); // Đánh dấu phần tử đã được ghép cặp
        int count = 0;

        // Khởi tạo Map với giá trị mặc định là false
        for (int i = 0; i < n; i++) {
            paired.put(i, false);
        }

        // Duyệt qua từng cặp liền kề
        for (int i = 0; i < n; i++) {
            // int next = (i++) % n; // Vòng tròn, phần tử cuối nối với phần tử đầu
            int next = (i == n - 1) ? 0 : i + 1;
            if (!paired.get(i) && !paired.get(next) && (A[i] % 2 == A[next] % 2)) {
                count++;
                paired.put(i, true);  // Đánh dấu phần tử đã được ghép
                paired.put(next, true);
            }
        }
        return count;
    }


}
