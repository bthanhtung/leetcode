package com.tungdadev.medium;

/**
 * Link: https://leetcode.com/problems/longest-palindromic-substring/description/
 * Given a string s, return the longest palindromic substring in s.
 */
public class LongestPalindromicSubstring {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
    }

    /*
     * Solution implement here
     */
    static int start = 0, end = 0;
    public static String longestPalindrome(String s) {
        char[] temp = s.toCharArray();
        search(temp, 0);
        return s.substring(start, end+1);
    }

    public static void search(char[] temp, int index){
        int startIndex = index;
        int endIndex = index;
        int size = temp.length;
        if(index >= size-1)
            return;

        while(endIndex < size-1 && temp[endIndex] == temp[endIndex+1])
            endIndex += 1;

        index = endIndex;
        while(startIndex > 0 && endIndex < size-1 && temp[startIndex-1] == temp[endIndex+1]){
            startIndex -= 1;
            endIndex += 1;
        }

        if(endIndex - startIndex > end - start){
            end = endIndex;
            start = startIndex;
        }
        search(temp, index+1);
    }
}
