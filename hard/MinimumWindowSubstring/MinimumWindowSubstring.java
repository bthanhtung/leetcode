package MinimumWindowSubstring;

/*
 * 76. Minimum Window Substring — https://leetcode.com/problems/minimum-window-substring/
 * Return the shortest substring of s that contains every character of t (with multiplicity), or "" if none.
 * Input: s = "ADOBECODEBANC", t = "ABC" -> "BANC" · s = "a", t = "a" -> "a" · s = "a", t = "aa" -> ""
 * Explanation: README.md
 */
public class MinimumWindowSubstring {
    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));       // BANC
        System.out.println(minWindowBrute("a", "aa"));              // ""
    }

    /*
     * Approach 1 - sliding window. need[c] = how many more of c the window still needs; missing = total still needed.
     * Grow right until missing == 0, then shrink left as long as the window stays valid, recording the best. O(m + n).
     */
    public static String minWindow(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int missing = t.length(), left = 0, bestStart = 0, bestLen = Integer.MAX_VALUE;
        for (int right = 0; right < s.length(); right++) {
            if (need[s.charAt(right)]-- > 0) missing--;            // this character was actually needed
            while (missing == 0) {
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestStart = left;
                }
                if (++need[s.charAt(left++)] > 0) missing++;      // dropped a needed character: window breaks
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
    }

    /* Approach 2 - check every window with counts. Time O(m^2 * alphabet). Reference for tests. */
    public static String minWindowBrute(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        String best = "";
        for (int i = 0; i < s.length(); i++) {
            int[] have = new int[128];
            for (int j = i; j < s.length(); j++) {
                have[s.charAt(j)]++;
                boolean ok = true;
                for (int c = 0; c < 128 && ok; c++) ok = have[c] >= need[c];
                if (ok) {
                    if (best.isEmpty() || j - i + 1 < best.length()) best = s.substring(i, j + 1);
                    break;
                }
            }
        }
        return best;
    }
}
