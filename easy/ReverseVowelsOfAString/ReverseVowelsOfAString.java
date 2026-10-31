package ReverseVowelsOfAString;

/*
 * 345. Reverse Vowels of a String — https://leetcode.com/problems/reverse-vowels-of-a-string/
 * Reverse only the vowels (a, e, i, o, u, upper or lower case) of a string.
 * Input: "IceCreAm" -> "AceCreIm" · "leetcode" -> "leotcede"
 * Explanation: README.md
 */
public class ReverseVowelsOfAString {
    private static boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) >= 0;
    }

    public static void main(String[] args) {
        System.out.println(reverseVowels("IceCreAm"));         // AceCreIm
        System.out.println(reverseVowelsCollect("leetcode"));  // leotcede
    }

    /* Approach 1 - two pointers move inwards, each stops on a vowel, then swap. Time O(n), space O(n) for the char array. */
    public static String reverseVowels(String s) {
        char[] c = s.toCharArray();
        int i = 0, j = c.length - 1;
        while (i < j) {
            if (!isVowel(c[i])) {
                i++;
            } else if (!isVowel(c[j])) {
                j--;
            } else {
                char tmp = c[i];
                c[i++] = c[j];
                c[j--] = tmp;
            }
        }
        return new String(c);
    }

    /* Approach 2 - collect the vowels, then write them back in reverse order. Two passes. */
    public static String reverseVowelsCollect(String s) {
        StringBuilder vowels = new StringBuilder();
        for (char ch : s.toCharArray()) if (isVowel(ch)) vowels.append(ch);
        char[] c = s.toCharArray();
        int k = vowels.length() - 1;
        for (int i = 0; i < c.length; i++) {
            if (isVowel(c[i])) c[i] = vowels.charAt(k--);
        }
        return new String(c);
    }
}
