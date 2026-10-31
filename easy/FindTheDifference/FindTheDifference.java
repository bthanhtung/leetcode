package FindTheDifference;

/*
 * 389. Find the Difference — https://leetcode.com/problems/find-the-difference/
 * t is s shuffled with one extra letter added at a random position. Return the added letter.
 * Input: s = "abcd", t = "abcde" -> 'e' · s = "", t = "y" -> 'y'
 * Explanation: README.md
 */
public class FindTheDifference {
    public static void main(String[] args) {
        System.out.println(findTheDifference("abcd", "abcde"));      // e
        System.out.println(findTheDifferenceXor("", "y"));           // y
    }

    /* Approach 1 - count letters of s, subtract letters of t; the one that goes negative is extra. Time O(n). */
    public static char findTheDifferenceCount(String s, String t) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;
        for (char c : t.toCharArray()) {
            if (--count[c - 'a'] < 0) return c;
        }
        throw new IllegalArgumentException("t has no extra letter");
    }

    /* Approach 2 - the sum of character codes differs exactly by the extra letter. Time O(n). */
    public static char findTheDifference(String s, String t) {
        int diff = 0;
        for (char c : t.toCharArray()) diff += c;
        for (char c : s.toCharArray()) diff -= c;
        return (char) diff;
    }

    /* Approach 3 - XOR all characters of both strings; every pair cancels. Time O(n). */
    public static char findTheDifferenceXor(String s, String t) {
        int x = 0;
        for (char c : s.toCharArray()) x ^= c;
        for (char c : t.toCharArray()) x ^= c;
        return (char) x;
    }
}
