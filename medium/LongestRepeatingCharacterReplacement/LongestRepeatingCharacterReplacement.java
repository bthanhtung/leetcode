package LongestRepeatingCharacterReplacement;

/*
 * 424. Longest Repeating Character Replacement — https://leetcode.com/problems/longest-repeating-character-replacement/
 * You may change at most k characters of s (uppercase letters) to any other letter. Return the length of the longest
 * substring containing one repeated letter you can get.
 * Input: ("ABAB", 2) -> 4 · ("AABABBA", 1) -> 4
 * Explanation: README.md
 */
public class LongestRepeatingCharacterReplacement {
    public static void main(String[] args) {
        System.out.println(characterReplacement("ABAB", 2));              // 4
        System.out.println(characterReplacementBruteForce("AABABBA", 1)); // 4
    }

    /* Approach 1 - every start; extend while (length - most frequent letter) <= k. Time O(26 * n^2) worst. */
    public static int characterReplacementBruteForce(String s, int k) {
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            int[] count = new int[26];
            int maxFreq = 0;
            for (int j = i; j < s.length(); j++) {
                maxFreq = Math.max(maxFreq, ++count[s.charAt(j) - 'A']);
                if (j - i + 1 - maxFreq <= k) best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }

    /*
     * Approach 2 - sliding window: a window is fine when (size - count of its most frequent letter) <= k. Grow right;
     * when the window breaks, slide it (left++). maxFreq never needs to go down: only a bigger maxFreq can produce a
     * bigger answer, so a stale value just keeps the window from growing. Time O(n).
     */
    public static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0, maxFreq = 0;
        for (int right = 0; right < s.length(); right++) {
            maxFreq = Math.max(maxFreq, ++count[s.charAt(right) - 'A']);
            if (right - left + 1 - maxFreq > k) {
                count[s.charAt(left++) - 'A']--;
            }
        }
        return s.length() - left;                       // the window only ever grows or slides, never shrinks
    }
}
