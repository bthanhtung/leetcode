package MergeStringsAlternately;

/*
 * 1768. Merge Strings Alternately — https://leetcode.com/problems/merge-strings-alternately/
 * Merge word1 and word2 by adding letters in alternating order, starting with word1. Append the rest of the longer word.
 * Input: ("abc","pqr") -> "apbqcr" · ("ab","pqrs") -> "apbqrs" · ("abcd","pq") -> "apbqcd"
 * Explanation: README.md
 */
public class MergeStringsAlternately {
    public static void main(String[] args) {
        System.out.println(mergeAlternately("ab", "pqrs"));          // apbqrs
        System.out.println(mergeAlternatelyIndex("abcd", "pq"));     // apbqcd
    }

    /* Approach 1 - two pointers, one per word, take from each while it has letters. Time O(m + n). */
    public static String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder(word1.length() + word2.length());
        int i = 0, j = 0;
        while (i < word1.length() || j < word2.length()) {
            if (i < word1.length()) sb.append(word1.charAt(i++));
            if (j < word2.length()) sb.append(word2.charAt(j++));
        }
        return sb.toString();
    }

    /* Approach 2 - one index up to the shorter length, then append both tails. Time O(m + n). */
    public static String mergeAlternatelyIndex(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int common = Math.min(word1.length(), word2.length());
        for (int k = 0; k < common; k++) {
            sb.append(word1.charAt(k)).append(word2.charAt(k));
        }
        return sb.append(word1, common, word1.length()).append(word2, common, word2.length()).toString();
    }
}
