package com.tungdadev.easy;

public class ValidPalindrome {
    public static void main(String[] args) {
        String s = s = "A man, a plan, a canal: Panama";
        String s2 = s = "race a car";
        String s3 = s = " ";
        System.out.println(isPalindrome(s)); // Output: true
        System.out.println(isPalindrome(s2)); // Output: false
        System.out.println(isPalindrome(s3)); // Output: false

    }

    private static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
