package RepeatedSubstringPattern;

/*
 * 459. Repeated Substring Pattern — https://leetcode.com/problems/repeated-substring-pattern/
 * Return true if s can be built by repeating one of its substrings (at least twice).
 * Input: "abab" -> true · "aba" -> false · "abcabcabcabc" -> true
 * Explanation: README.md
 */
public class RepeatedSubstringPattern {
    public static void main(String[] args) {
        System.out.println(repeatedSubstringPattern("abab"));            // true
        System.out.println(repeatedSubstringPatternKmp("aba"));          // false
    }

    /* Approach 1 - try every block length that divides n. Time O(n * divisors). */
    public static boolean repeatedSubstringPatternBrute(String s) {
        int n = s.length();
        for (int len = 1; len <= n / 2; len++) {
            if (n % len == 0 && s.substring(0, len).repeat(n / len).equals(s)) return true;
        }
        return false;
    }

    /* Approach 2 - s is periodic iff s appears in (s + s) with the first and last characters removed. */
    public static boolean repeatedSubstringPattern(String s) {
        String doubled = s + s;
        return doubled.substring(1, doubled.length() - 1).contains(s);
    }

    /* Approach 3 - KMP: the longest proper border L gives the smallest period n - L; periodic iff it divides n. O(n). */
    public static boolean repeatedSubstringPatternKmp(String s) {
        int n = s.length();
        int[] fail = new int[n];
        for (int i = 1; i < n; i++) {
            int k = fail[i - 1];
            while (k > 0 && s.charAt(i) != s.charAt(k)) k = fail[k - 1];
            if (s.charAt(i) == s.charAt(k)) k++;
            fail[i] = k;
        }
        int border = fail[n - 1];
        return border > 0 && n % (n - border) == 0;
    }
}
