package com.tungdadev.codility;

import java.util.Arrays;

public class Task5 {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{11,20,15}));
        System.out.println(solution(new int[]{15, 20, 9, 11}));
        System.out.println(solution(new int[]{0,44,32,30,42,18,34,16,35}));
        System.out.println(solution(new int[]{9}));
    }

    public static int solution(int[] A) {
        // Sắp xếp mảng vị trí các lỗ
        Arrays.sort(A);

        // Xác định phạm vi tìm kiếm chiều dài tấm ván
        int left = 1; // Chiều dài tối thiểu
        int right = A[A.length - 1] - A[0]; // Chiều dài tối đa
        int result = right;

        // Tìm kiếm nhị phân để tìm chiều dài tối thiểu của tấm ván
        while (left <= right) {
            int mid = (left + right) / 2; // Chiều dài thử nghiệm

            // Kiểm tra có thể dùng 2 tấm ván với chiều dài `mid` để bao phủ tất cả các lỗ
            int boardsUsed = 0;
            int i = 0;
            while (i < A.length) {
                boardsUsed++; // Dùng 1 tấm ván
                int start = A[i]; // Bắt đầu bao phủ từ vị trí này

                // Duyệt qua các lỗ nằm trong phạm vi của tấm ván
                while (i < A.length && A[i] <= start + mid) {
                    i++;
                }

                // Nếu dùng quá 2 tấm ván thì không hợp lệ
                if (boardsUsed > 2) {
                    break;
                }
            }

            // Nếu có thể bao phủ bằng 2 tấm ván, cập nhật kết quả
            if (boardsUsed <= 2) {
                result = mid;
                right = mid - 1; // Tiếp tục tìm chiều dài nhỏ hơn
            } else {
                left = mid + 1; // Tăng chiều dài thử nghiệm
            }
        }

        return result;
    }
}
