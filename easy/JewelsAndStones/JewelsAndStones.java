package JewelsAndStones;

import java.util.HashSet;
import java.util.Set;

/*
 * 771. Jewels and Stones — https://leetcode.com/problems/jewels-and-stones/
 * jewels lists the stone types that are jewels; stones lists the stones you have. How many of your stones are jewels?
 * Letters are case sensitive. Input: jewels = "aA", stones = "aAAbbbb" -> 3 · jewels = "z", stones = "ZZ" -> 0
 * Explanation: README.md
 */
public class JewelsAndStones {
    public static void main(String[] args) {
        System.out.println(numJewelsInStones("aA", "aAAbbbb"));      // 3
        System.out.println(numJewelsInStonesSet("z", "ZZ"));         // 0
    }

    /* Approach 1 - for every stone, search the jewels string. Time O(j * s). */
    public static int numJewelsInStonesScan(String jewels, String stones) {
        int count = 0;
        for (char c : stones.toCharArray()) {
            if (jewels.indexOf(c) >= 0) count++;
        }
        return count;
    }

    /* Approach 2 - jewels in a hash set. Time O(j + s), space O(j). */
    public static int numJewelsInStonesSet(String jewels, String stones) {
        Set<Character> jewelSet = new HashSet<>();
        for (char c : jewels.toCharArray()) jewelSet.add(c);
        int count = 0;
        for (char c : stones.toCharArray()) {
            if (jewelSet.contains(c)) count++;
        }
        return count;
    }

    /* Approach 3 - boolean table indexed by character code: no boxing. Time O(j + s), space O(1). */
    public static int numJewelsInStones(String jewels, String stones) {
        boolean[] isJewel = new boolean[128];
        for (char c : jewels.toCharArray()) isJewel[c] = true;
        int count = 0;
        for (char c : stones.toCharArray()) {
            if (isJewel[c]) count++;
        }
        return count;
    }
}
