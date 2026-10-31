package ReverseString;

/*
 * 344. Reverse String — https://leetcode.com/problems/reverse-string/
 * Reverse a string given as a char array, in place with O(1) extra memory.
 * Input: ['h','e','l','l','o'] -> ['o','l','l','e','h']
 * Explanation: README.md
 */
public class ReverseString {
    public static void main(String[] args) {
        char[] s = "hello".toCharArray();
        reverseString(s);
        System.out.println(new String(s));     // olleh
    }

    /* Approach 1 - swap the two ends and move inwards. Time O(n), space O(1). */
    public static void reverseString(char[] s) {
        for (int i = 0, j = s.length - 1; i < j; i++, j--) {
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
    }

    /* Approach 2 - recursion: swap the ends, reverse the inside. Time O(n), space O(n) stack - breaks the O(1) rule. */
    public static void reverseStringRecursive(char[] s) {
        reverse(s, 0, s.length - 1);
    }

    private static void reverse(char[] s, int i, int j) {
        if (i >= j) return;
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
        reverse(s, i + 1, j - 1);
    }
}
