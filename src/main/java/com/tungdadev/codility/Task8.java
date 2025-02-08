package com.tungdadev.codility;

public class Task8 {
    public static void main(String[] args) {
        System.out.println(solution("ayxbx"));
        System.out.println(solution("xzzzy"));
        System.out.println(solution("toyxmy"));
    }

    public static int solution(String S) {
        int sLength = S.length();

        int[] xFirst = new int[sLength + 1];
        int[] yFirst = new int[sLength + 1];
        int[] xSecond = new int[sLength + 1];
        int[] ySecond = new int[sLength + 1];

        for(int i = 1; i <= sLength; i++) {
            xFirst[i] = xFirst[i-1] + (S.charAt(i-1) == 'x' ? 1 : 0);
            yFirst[i] = yFirst[i-1] + (S.charAt(i-1) == 'y' ? 1 : 0);
        }

        for(int j = sLength-1; j >= 0; j--) {
            xSecond[j] = xSecond[j+1] + (S.charAt(j) == 'x' ? 1 : 0);
            ySecond[j] = ySecond[j+1] + (S.charAt(j) == 'y' ? 1 : 0);
        }

        int count = 0;
        for(int k = 1; k < sLength; k++) {
            if(xFirst[k] == yFirst[k] || xSecond[k] == ySecond[k]) {
                count++;
            }
        }

        return count;

    }
}
