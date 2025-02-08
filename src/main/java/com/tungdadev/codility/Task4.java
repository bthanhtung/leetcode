package com.tungdadev.codility;

import java.util.HashSet;
import java.util.Set;

public class Task4 {
    public static void main(String[] args) {
        System.out.println(solution("><^v")); // Example output: 2
        System.out.println(solution("<<^<v>>"));   // Example output: 6
        System.out.println(solution("><><"));   // Example output: 0

        System.out.println(solutionz("><^v")); // Example output: 2
        System.out.println(solutionz("<<^<v>>"));   // Example output: 6
        System.out.println(solutionz("><><"));   // Example output: 0
    }

    public static int solution(String S) {
        int lengthStr = S.length();
        int result = 0;
        Set<Integer> occupied = new HashSet<>();

        // Đi qua từng ký tự trong chuỗi
        for (int i = 0; i < lengthStr; i++) {
            char step = S.charAt(i);
            int destinate = i;

            // Tính toán vị trí đích dựa trên ký tự di chuyển
            switch (step) {
                case '<':
                    destinate = i - 1; // Di chuyển sang trái
                    break;
                case '>':
                    destinate = i + 1; // Di chuyển sang phải
                    break;
                case '^':
                    destinate = i - lengthStr; // Di chuyển lên trên (giả lập vị trí trên)
                    break;
                case 'v':
                    destinate = i + lengthStr; // Di chuyển xuống dưới (giả lập vị trí dưới)
                    break;
            }

            // Kiểm tra xem vị trí đích có hợp lệ và chưa bị chiếm dụng
            if (destinate >= 0 && destinate < lengthStr && !occupied.contains(destinate)) {
                occupied.add(destinate); // Đánh dấu vị trí đã chiếm dụng
                result++; // Tăng số bước di chuyển hợp lệ
            }
        }

        return result;
    }

    public static int solutionz(String S) {
        int lengthStr = S.length();
        int result = 0;
        Set<Integer> occupied = new HashSet<>();

        for (int i = 0; i < lengthStr; i++) {
            char step = S.charAt(i);
            int destinate = i;

            switch (step) {
                case '<':
                    destinate = i - 1;
                    break;
                case '>':
                    destinate = i + 1;
                    break;
                case '^':
                    destinate = i - lengthStr; // Up direction (simulated)
                    break;
                case 'v':
                    destinate = i + lengthStr; // Down direction (simulated)
                    break;
                default:
                    continue; // Ignore invalid moves
            }

            // Check if the destination is valid and not already occupied
            if (destinate >= 0 && destinate < lengthStr && !occupied.contains(destinate)) {
                occupied.add(destinate);
                result++;
            }
        }
        return result;
    }
}
