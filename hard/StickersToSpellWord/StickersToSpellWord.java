package StickersToSpellWord;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/*
 * 691. Stickers to Spell Word — https://leetcode.com/problems/stickers-to-spell-word/
 * Each sticker can be used any number of times; you may cut letters out of stickers. Return the minimum number of
 * stickers needed to spell target, or -1 if impossible.
 * Input: ["with","example","science"], "thehat" -> 3 · ["notice","possible"], "basicbasic" -> -1
 * Explanation: README.md
 */
public class StickersToSpellWord {
    public static void main(String[] args) {
        System.out.println(minStickers(new String[]{"with", "example", "science"}, "thehat"));           // 3
        System.out.println(minStickersBfs(new String[]{"notice", "possible"}, "basicbasic"));           // -1
    }

    /*
     * Approach 1 - state = sorted remaining letters of target. Try every sticker that contains the first remaining
     * letter (some sticker must cover it, so this loses nothing and prunes a lot). Memoized on the state string.
     */
    public static int minStickers(String[] stickers, String target) {
        int[][] counts = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) for (char c : stickers[i].toCharArray()) counts[i][c - 'a']++;
        char[] t = target.toCharArray();
        Arrays.sort(t);
        Map<String, Integer> memo = new HashMap<>();
        memo.put("", 0);
        return dfs(new String(t), counts, memo);
    }

    private static int dfs(String remaining, int[][] counts, Map<String, Integer> memo) {
        Integer known = memo.get(remaining);
        if (known != null) return known;
        int[] need = new int[26];
        for (char c : remaining.toCharArray()) need[c - 'a']++;
        int best = Integer.MAX_VALUE;
        for (int[] sticker : counts) {
            if (sticker[remaining.charAt(0) - 'a'] == 0) continue;
            StringBuilder rest = new StringBuilder();
            for (int c = 0; c < 26; c++) {
                int left = need[c] - sticker[c];
                for (int k = 0; k < left; k++) rest.append((char) ('a' + c));
            }
            int sub = dfs(rest.toString(), counts, memo);
            if (sub != -1) best = Math.min(best, 1 + sub);
        }
        int result = best == Integer.MAX_VALUE ? -1 : best;
        memo.put(remaining, result);
        return result;
    }

    /* Approach 2 - BFS over masks of target positions already covered (target length <= 15); each edge applies a sticker. */
    public static int minStickersBfs(String[] stickers, String target) {
        int n = target.length(), full = (1 << n) - 1;
        int[] dist = new int[1 << n];
        Arrays.fill(dist, -1);
        dist[0] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int mask = queue.poll();
            if (mask == full) return dist[mask];
            for (String s : stickers) {
                int next = mask;
                for (char c : s.toCharArray()) {
                    for (int i = 0; i < n; i++) {
                        if ((next >> i & 1) == 0 && target.charAt(i) == c) {
                            next |= 1 << i;
                            break;
                        }
                    }
                }
                if (dist[next] < 0) {
                    dist[next] = dist[mask] + 1;
                    queue.add(next);
                }
            }
        }
        return -1;
    }
}
