package PalindromePartitioning;

import java.util.ArrayList;
import java.util.List;

/*
 * 131. Palindrome Partitioning — https://leetcode.com/problems/palindrome-partitioning/
 * Partition s so that every substring of the partition is a palindrome. Return all possible partitions.
 * Input: "aab" -> [["a","a","b"],["aa","b"]] · "a" -> [["a"]]
 * Explanation: README.md
 */
public class PalindromePartitioning {
    public static void main(String[] args) {
        System.out.println(partition("aab"));          // [[a, a, b], [aa, b]]
        System.out.println(partitionDp("racecar"));
    }

    /* Approach 1 - try every palindromic prefix, recurse on the rest; check palindromes with two pointers. O(n * 2^n). */
    public static List<List<String>> partition(String s) {
        List<List<String>> out = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), out, null);
        return out;
    }

    /* Approach 2 - same search, but isPal[i][j] is precomputed so each check is O(1). */
    public static List<List<String>> partitionDp(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                isPal[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || isPal[i + 1][j - 1]);
            }
        }
        List<List<String>> out = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), out, isPal);
        return out;
    }

    private static void backtrack(String s, int start, List<String> path, List<List<String>> out, boolean[][] isPal) {
        if (start == s.length()) {
            out.add(new ArrayList<>(path));
            return;
        }
        for (int end = start; end < s.length(); end++) {
            boolean pal = isPal != null ? isPal[start][end] : isPalindrome(s, start, end);
            if (!pal) continue;
            path.add(s.substring(start, end + 1));
            backtrack(s, end + 1, path, out, isPal);
            path.removeLast();
        }
    }

    private static boolean isPalindrome(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) return false;
        }
        return true;
    }
}
