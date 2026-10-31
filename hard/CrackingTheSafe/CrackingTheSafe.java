package CrackingTheSafe;

import java.util.HashSet;
import java.util.Set;

/*
 * 753. Cracking the Safe — https://leetcode.com/problems/cracking-the-safe/
 * A safe's password has n digits from 0..k-1; the safe checks the last n digits typed. Return any shortest string that
 * contains every possible password as a substring (length k^n + n - 1).
 * Input: n = 1, k = 2 -> "10" · n = 2, k = 2 -> "01100" (or "10011", ...)
 * Explanation: README.md
 */
public class CrackingTheSafe {
    public static void main(String[] args) {
        System.out.println(crackSafe(2, 2));          // e.g. 01100
        System.out.println(crackSafeGreedy(2, 3));
    }

    /*
     * Approach 1 - de Bruijn sequence: nodes are the k^(n-1) strings of length n-1, each password is an edge
     * (node + digit). An Eulerian circuit uses every edge once. Hierholzer: DFS, append the digit after its edges are done.
     */
    public static String crackSafe(int n, int k) {
        if (n == 1) {
            StringBuilder sb = new StringBuilder();
            for (int d = k - 1; d >= 0; d--) sb.append(d);
            return sb.toString();
        }
        Set<String> used = new HashSet<>();
        StringBuilder out = new StringBuilder();
        String start = "0".repeat(n - 1);
        dfs(start, k, used, out);
        return out.append(start).toString();
    }

    private static void dfs(String node, int k, Set<String> used, StringBuilder out) {
        for (int d = 0; d < k; d++) {
            String edge = node + d;
            if (used.add(edge)) {
                dfs(edge.substring(1), k, used, out);
                out.append(d);                           // post-order: digits come out in reverse circuit order
            }
        }
    }

    /*
     * Approach 2 - greedy: start with n zeros' worth of prefix and repeatedly append the largest digit whose new
     * n-length suffix has not been seen. This always succeeds (it produces the "prefer-largest" de Bruijn sequence).
     */
    public static String crackSafeGreedy(int n, int k) {
        StringBuilder sb = new StringBuilder("0".repeat(n));
        Set<String> seen = new HashSet<>();
        seen.add(sb.toString());
        int total = (int) Math.pow(k, n);
        while (seen.size() < total) {
            String tail = sb.substring(sb.length() - (n - 1));
            for (int d = k - 1; d >= 0; d--) {
                String candidate = tail + d;
                if (seen.add(candidate)) {
                    sb.append(d);
                    break;
                }
            }
        }
        return sb.toString();
    }
}
