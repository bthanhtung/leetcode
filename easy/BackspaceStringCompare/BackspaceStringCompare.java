package BackspaceStringCompare;

/*
 * 844. Backspace String Compare — https://leetcode.com/problems/backspace-string-compare/
 * '#' means backspace. Return whether s and t are equal after typing them into empty editors.
 * Input: ("ab#c","ad#c") -> true ("ac") · ("ab##","c#d#") -> true ("") · ("a#c","b") -> false
 * Explanation: README.md
 */
public class BackspaceStringCompare {
    public static void main(String[] args) {
        System.out.println(backspaceCompare("ab#c", "ad#c"));             // true
        System.out.println(backspaceCompareBuild("a#c", "b"));            // false
    }

    /* Approach 1 - type each string into a StringBuilder used as a stack. O(m + n) time and space. */
    public static boolean backspaceCompareBuild(String s, String t) {
        return type(s).equals(type(t));
    }

    private static String type(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c != '#') sb.append(c);
            else if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /* Approach 2 - walk both from the end, skipping characters that a later '#' erases. O(1) extra space. */
    public static boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1, j = t.length() - 1;
        while (i >= 0 || j >= 0) {
            i = nextKept(s, i);
            j = nextKept(t, j);
            if (i < 0 || j < 0) return i < 0 && j < 0;
            if (s.charAt(i) != t.charAt(j)) return false;
            i--;
            j--;
        }
        return true;
    }

    /** Index of the next character (going left from i) that survives the backspaces, or -1. */
    private static int nextKept(String s, int i) {
        int skip = 0;
        while (i >= 0) {
            if (s.charAt(i) == '#') skip++;
            else if (skip > 0) skip--;
            else break;
            i--;
        }
        return i;
    }
}
