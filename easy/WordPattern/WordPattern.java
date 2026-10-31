package WordPattern;

import java.util.HashMap;
import java.util.Map;

/*
 * 290. Word Pattern — https://leetcode.com/problems/word-pattern/
 * Does s (words separated by single spaces) follow pattern? There must be a bijection between letters and words.
 * Input: ("abba","dog cat cat dog") -> true · ("abba","dog cat cat fish") -> false · ("aaaa","dog cat cat dog") -> false
 * Explanation: README.md
 */
public class WordPattern {
    public static void main(String[] args) {
        System.out.println(wordPattern("abba", "dog cat cat dog"));          // true
        System.out.println(wordPatternIndex("abba", "dog dog dog dog"));     // false
    }

    /* Approach 1 - two maps enforce a one-to-one mapping in both directions. O(n). */
    public static boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (words.length != pattern.length()) return false;
        Map<Character, String> lw = new HashMap<>();
        Map<String, Character> wl = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            char c = pattern.charAt(i);
            String w = words[i];
            if (!lw.getOrDefault(c, w).equals(w) || wl.getOrDefault(w, c) != c) return false;
            lw.put(c, w);
            wl.put(w, c);
        }
        return true;
    }

    /* Approach 2 - letter i and word i must have first appeared at the same index. */
    public static boolean wordPatternIndex(String pattern, String s) {
        String[] words = s.split(" ");
        if (words.length != pattern.length()) return false;
        Map<Object, Integer> first = new HashMap<>();             // characters and strings never collide as keys
        for (int i = 0; i < words.length; i++) {
            Integer a = first.putIfAbsent(pattern.charAt(i), i), b = first.putIfAbsent(words[i], i);
            if (!java.util.Objects.equals(a, b)) return false;
        }
        return true;
    }
}
