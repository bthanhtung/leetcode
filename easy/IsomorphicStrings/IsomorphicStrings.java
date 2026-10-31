package IsomorphicStrings;

import java.util.HashMap;
import java.util.Map;

/*
 * 205. Isomorphic Strings — https://leetcode.com/problems/isomorphic-strings/
 * Two strings are isomorphic if the characters of s can be replaced (consistently, one-to-one) to get t.
 * Input: ("egg","add") -> true · ("foo","bar") -> false · ("paper","title") -> true · ("badc","baba") -> false
 * Explanation: README.md
 */
public class IsomorphicStrings {
    public static void main(String[] args) {
        System.out.println(isIsomorphic("paper", "title"));          // true
        System.out.println(isIsomorphicMaps("badc", "baba"));        // false
    }

    /* Approach 1 - map s->t and t->s; both must stay consistent (the second map enforces one-to-one). */
    public static boolean isIsomorphicMaps(String s, String t) {
        Map<Character, Character> st = new HashMap<>(), ts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i), b = t.charAt(i);
            if (st.getOrDefault(a, b) != b || ts.getOrDefault(b, a) != a) return false;
            st.put(a, b);
            ts.put(b, a);
        }
        return true;
    }

    /* Approach 2 - characters at i must have been last seen at the same position in both strings. */
    public static boolean isIsomorphic(String s, String t) {
        int[] lastS = new int[256], lastT = new int[256];
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i), b = t.charAt(i);
            if (lastS[a] != lastT[b]) return false;
            lastS[a] = lastT[b] = i + 1;            // +1 so 0 means "never seen"
        }
        return true;
    }
}
