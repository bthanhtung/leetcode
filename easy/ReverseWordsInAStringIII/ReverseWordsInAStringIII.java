package ReverseWordsInAStringIII;

/*
 * 557. Reverse Words in a String III — https://leetcode.com/problems/reverse-words-in-a-string-iii/
 * Reverse the characters of each word in a sentence while keeping the word order and the single spaces.
 * Input: "Let's take LeetCode contest" -> "s'teL ekat edoCteeL tsetnoc" · "Mr Ding" -> "rM gniD"
 * Explanation: README.md
 */
public class ReverseWordsInAStringIII {
    public static void main(String[] args) {
        System.out.println(reverseWords("Let's take LeetCode contest"));    // s'teL ekat edoCteeL tsetnoc
        System.out.println(reverseWordsSplit("Mr Ding"));                   // rM gniD
    }

    /* Approach 1 - split, reverse each word, join. Time O(n), space O(n). */
    public static String reverseWordsSplit(String s) {
        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = new StringBuilder(words[i]).reverse().toString();
        }
        return String.join(" ", words);
    }

    /* Approach 2 - one char array: find each word's bounds, reverse it with two pointers. Time O(n), space O(n) for the array. */
    public static String reverseWords(String s) {
        char[] c = s.toCharArray();
        int start = 0;
        for (int end = 0; end <= c.length; end++) {
            if (end == c.length || c[end] == ' ') {
                for (int i = start, j = end - 1; i < j; i++, j--) {
                    char tmp = c[i];
                    c[i] = c[j];
                    c[j] = tmp;
                }
                start = end + 1;
            }
        }
        return new String(c);
    }
}
