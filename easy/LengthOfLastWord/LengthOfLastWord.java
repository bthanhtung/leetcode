package LengthOfLastWord;

/*
 * 58. Length of Last Word — https://leetcode.com/problems/length-of-last-word/
 * Given a string s of words and spaces, return the length of the last word (a maximal substring of non-space characters).
 * Input: "Hello World" -> 5 · "   fly me   to   the moon  " -> 4 · "luffy is still joyboy" -> 6
 * Explanation: README.md
 */
public class LengthOfLastWord {
    public static void main(String[] args) {
        System.out.println(lengthOfLastWordSplit("Hello World"));                    // 5
        System.out.println(lengthOfLastWord("   fly me   to   the moon  "));         // 4
        System.out.println(lengthOfLastWordTrim("luffy is still joyboy"));           // 6
    }

    /* Approach 1 - trim, split on spaces, take the last piece. Time O(n), space O(n). */
    public static int lengthOfLastWordSplit(String s) {
        String[] words = s.trim().split("\\s+");
        return words[words.length - 1].length();
    }

    /* Approach 2 - trim, then distance from the end to the last space. Time O(n), space O(n) for the trimmed copy. */
    public static int lengthOfLastWordTrim(String s) {
        String t = s.trim();
        return t.length() - t.lastIndexOf(' ') - 1;
    }

    /* Approach 3 - scan from the end: skip trailing spaces, then count letters. Time O(length of tail), space O(1). */
    public static int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }
        int length = 0;
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }
        return length;
    }
}
