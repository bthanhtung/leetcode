package FirstUniqueCharacterInAString;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 387. First Unique Character in a String — https://leetcode.com/problems/first-unique-character-in-a-string/
 * Return the index of the first non-repeating character in s, or -1. Lowercase English letters.
 * Input: "leetcode" -> 0 · "loveleetcode" -> 2 · "aabb" -> -1
 * Explanation: README.md
 */
public class FirstUniqueCharacterInAString {
    public static void main(String[] args) {
        System.out.println(firstUniqChar("loveleetcode"));         // 2
        System.out.println(firstUniqCharQueue("aabb"));            // -1
    }

    /* Approach 1 - count letters, then the first index whose letter count is 1. Two passes over s. Time O(n). */
    public static int firstUniqChar(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) count[s.charAt(i) - 'a']++;
        for (int i = 0; i < s.length(); i++) {
            if (count[s.charAt(i) - 'a'] == 1) return i;
        }
        return -1;
    }

    /* Approach 2 - remember each letter's first index (or -2 if repeated); the answer is the smallest index left. */
    public static int firstUniqCharIndex(String s) {
        int[] first = new int[26];
        java.util.Arrays.fill(first, -1);
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            first[c] = first[c] == -1 ? i : -2;
        }
        int best = Integer.MAX_VALUE;
        for (int f : first) {
            if (f >= 0) best = Math.min(best, f);
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    /* Approach 3 - streaming: a queue of candidate indexes; drop the front while its letter has repeated. One pass. */
    public static int firstUniqCharQueue(String s) {
        int[] count = new int[26];
        Queue<Integer> candidates = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (count[c]++ == 0) candidates.add(i);
            while (!candidates.isEmpty() && count[s.charAt(candidates.peek()) - 'a'] > 1) candidates.poll();
        }
        return candidates.isEmpty() ? -1 : candidates.peek();
    }
}
