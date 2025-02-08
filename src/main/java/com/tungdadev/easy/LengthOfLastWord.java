package com.tungdadev.easy;

public class LengthOfLastWord {
    public static void main(String[] args) {
        String s = "Hello World";
        String s2 = "   fly me   to   the moon  ";
        String s3 = "luffy is still joyboy";
        System.out.println(lengthOfLastWord(s)); // Output: 5
        System.out.println(lengthOfLastWord(s2)); // Output: 4
        System.out.println(lengthOfLastWord(s3)); // Output: 6
    }

    public static int lengthOfLastWord(String s) {
        int length = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ' && length > 0) {
                break;
            } else if (s.charAt(i) != ' ') {
                length++;
            }
        }
        return length;
    }
}
