package ValidPalindromeII;

/*
 * 680. Valid Palindrome II — https://leetcode.com/problems/valid-palindrome-ii/
 * Return true if s can become a palindrome after deleting at most one character.
 * Input: "aba" -> true · "abca" -> true (delete 'c') · "abc" -> false
 * Explanation: README.md
 */
public class ValidPalindromeII {
    public static void main(String[] args) {
        System.out.println(validPalindrome("abca"));           // true
        System.out.println(validPalindromeBudget("abc"));      // false
    }

    /* Approach 1 - two pointers; at the first mismatch, try skipping either side once. O(n). */
    public static boolean validPalindrome(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return isPal(s, i + 1, j) || isPal(s, i, j - 1);
        }
        return true;
    }

    private static boolean isPal(String s, int i, int j) {
        while (i < j) if (s.charAt(i++) != s.charAt(j--)) return false;
        return true;
    }

    /* Approach 2 - general version: allowed deletions as a budget (k = 1 here); generalizes to "at most k". */
    public static boolean validPalindromeBudget(String s) {
        return check(s, 0, s.length() - 1, 1);
    }

    private static boolean check(String s, int i, int j, int budget) {
        while (i < j && s.charAt(i) == s.charAt(j)) {
            i++;
            j--;
        }
        if (i >= j) return true;
        return budget > 0 && (check(s, i + 1, j, budget - 1) || check(s, i, j - 1, budget - 1));
    }
}
