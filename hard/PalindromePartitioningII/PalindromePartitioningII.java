package PalindromePartitioningII;

/*
 * 132. Palindrome Partitioning II — https://leetcode.com/problems/palindrome-partitioning-ii/
 * Partition s so that every part is a palindrome; return the minimum number of cuts.
 * Input: "aab" -> 1 ("aa" | "b") · "a" -> 0 · "ab" -> 1
 * Explanation: README.md
 */
public class PalindromePartitioningII {
    public static void main(String[] args) {
        System.out.println(minCut("aab"));               // 1
        System.out.println(minCutCenters("abccbc"));     // 2 (a | bccb | c)
    }

    /*
     * Approach 1 - isPal[i][j] table, then cuts[j] = min cuts for s[0..j]: 0 if s[0..j] is a palindrome, else
     * min over i of cuts[i-1] + 1 where s[i..j] is a palindrome. Time O(n^2), space O(n^2).
     */
    public static int minCut(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        int[] cuts = new int[n];
        for (int j = 0; j < n; j++) {
            cuts[j] = j;                                         // worst case: cut between every character
            for (int i = 0; i <= j; i++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || isPal[i + 1][j - 1])) {
                    isPal[i][j] = true;
                    cuts[j] = i == 0 ? 0 : Math.min(cuts[j], cuts[i - 1] + 1);
                }
            }
        }
        return cuts[n - 1];
    }

    /*
     * Approach 2 - expand around every center; whenever s[l..r] is a palindrome, cuts[r] can be cuts[l-1] + 1.
     * Time O(n^2), space O(n).
     */
    public static int minCutCenters(String s) {
        int n = s.length();
        int[] cuts = new int[n];
        for (int i = 0; i < n; i++) cuts[i] = i;
        for (int center = 0; center < n; center++) {
            for (int[] start : new int[][]{{center, center}, {center, center + 1}}) {
                int l = start[0], r = start[1];
                while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                    cuts[r] = Math.min(cuts[r], l == 0 ? 0 : cuts[l - 1] + 1);
                    l--;
                    r++;
                }
            }
        }
        return cuts[n - 1];
    }
}
