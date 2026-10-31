package PermutationInString;

/*
 * 567. Permutation in String — https://leetcode.com/problems/permutation-in-string/
 * Return true if s2 contains a permutation of s1 as a substring.
 * Input: ("ab","eidbaooo") -> true ("ba") · ("ab","eidboaoo") -> false
 * Explanation: README.md
 */
public class PermutationInString {
    public static void main(String[] args) {
        System.out.println(checkInclusion("ab", "eidbaooo"));     // true
    }

    /*
     * Slide a window of length |s1| over s2. need[c] = count in s1 minus count in the window; `matched` counts letters
     * with need == 0. All 26 matched means the window is an anagram. Each move updates two letters. O(|s2|).
     */
    public static boolean checkInclusion(String s1, String s2) {
        int m = s1.length();
        if (m > s2.length()) return false;
        int[] need = new int[26];
        for (int i = 0; i < m; i++) {
            need[s1.charAt(i) - 'a']++;
            need[s2.charAt(i) - 'a']--;
        }
        int matched = 0;
        for (int c : need) if (c == 0) matched++;
        for (int i = m; ; i++) {
            if (matched == 26) return true;
            if (i == s2.length()) return false;
            matched += change(need, s2.charAt(i) - 'a', -1);       // enters the window
            matched += change(need, s2.charAt(i - m) - 'a', +1);   // leaves the window
        }
    }

    /** Applies delta to need[c] and returns how the number of zero entries changed. */
    private static int change(int[] need, int c, int delta) {
        int before = need[c] == 0 ? 1 : 0;
        need[c] += delta;
        return (need[c] == 0 ? 1 : 0) - before;
    }
}
