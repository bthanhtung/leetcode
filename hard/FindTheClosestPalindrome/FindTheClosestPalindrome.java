package FindTheClosestPalindrome;

import java.util.ArrayList;
import java.util.List;

/*
 * 564. Find the Closest Palindrome — https://leetcode.com/problems/find-the-closest-palindrome/
 * n is an integer string (up to 18 digits). Return the closest palindrome integer, not including n itself;
 * on a tie, the smaller one.
 * Input: "123" -> "121" · "1" -> "0" · "10" -> "9" · "99" -> "101" · "11" -> "9"
 * Explanation: README.md
 */
public class FindTheClosestPalindrome {
    public static void main(String[] args) {
        System.out.println(nearestPalindromic("123"));     // 121
        System.out.println(nearestPalindromic("99"));      // 101
    }

    /*
     * The answer keeps n's length and mirrors its first half, with that half changed by -1, 0 or +1 - unless the
     * length changes, where the only options are 99..9 (one digit shorter) and 100..001 (one digit longer).
     * Pick the closest of these five, excluding n. O(L).
     */
    public static String nearestPalindromic(String n) {
        int len = n.length();
        long value = Long.parseLong(n);
        List<Long> candidates = new ArrayList<>();
        candidates.add((long) Math.pow(10, len - 1) - 1);          // 9, 99, 999 ...
        candidates.add((long) Math.pow(10, len) + 1);              // 11, 101, 1001 ...
        long prefix = Long.parseLong(n.substring(0, (len + 1) / 2));
        for (long p = prefix - 1; p <= prefix + 1; p++) candidates.add(mirror(p, len % 2 == 1));
        long best = -1;
        for (long c : candidates) {
            if (c == value || c < 0) continue;
            long d = Math.abs(c - value), bd = Math.abs(best - value);
            if (best == -1 || d < bd || (d == bd && c < best)) best = c;
        }
        return String.valueOf(best);
    }

    /** prefix followed by its reverse (dropping the middle digit when the length is odd). */
    private static long mirror(long prefix, boolean odd) {
        String p = String.valueOf(prefix);
        String rev = new StringBuilder(odd ? p.substring(0, p.length() - 1) : p).reverse().toString();
        String s = p + rev;
        return s.length() > 18 ? -1 : Long.parseLong(s);      // longer palindromes are covered by 10^L + 1
    }
}
