package com.tungdadev.codility;

public class Task6 {
    public static void main(String[] args) {
        System.out.println(solution(3));
        System.out.println(solution(5));
        System.out.println(solution(30));
    }

    public static String solution(int N) {
        int letters = 26;
        // int numLetter = Math.min(N, letters);
        int numLetter = N > 15 ? 15 : N;
         int occurrences = N / numLetter;
         int remaining = N % letters;

         StringBuilder stringBuilder = new StringBuilder();
         for(int i = 0; i < numLetter; i++) {
             char letter = (char) ('a' + i);
             stringBuilder.append(String.valueOf(letter).repeat(occurrences));
         }

         for(int i = 0; i < remaining; i++) {
             char letter = (char) ('a' + i);
             stringBuilder.append(letter);
         }
         return stringBuilder.toString();
    }
}
