package com.tungdadev.easy;

/**
 * @author TungDaDev
 * @since 25/06/2025 - 00:05
 */
public class RasomNote {
    public static void main(String[] args) {
        String ransomNote = "a";
        String magazine = "b";
        RasomNote rasomNote = new RasomNote();
        boolean result = rasomNote.canConstruct(ransomNote, magazine);
        System.out.println("Can construct ransom note? " + result);
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote == null || magazine == null) {
            return false; // If either string is null, cannot construct
        }

        int[] charCount = new int[26]; // Assuming only lowercase letters a-z

        // Count characters in the magazine
        for (char c : magazine.toCharArray()) {
            charCount[c - 'a']++;
        }

        // Check if we can construct the ransom note
        for (char c : ransomNote.toCharArray()) {
            if (charCount[c - 'a'] <= 0) {
                return false; // Not enough characters in magazine
            }
            charCount[c - 'a']--; // Use one character from the magazine
        }

        return true; // Successfully constructed the ransom note
    }
}
