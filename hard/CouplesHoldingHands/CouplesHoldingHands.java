package CouplesHoldingHands;

/*
 * 765. Couples Holding Hands — https://leetcode.com/problems/couples-holding-hands/
 * 2n seats; row[i] is the person in seat i. Couples are (0,1), (2,3), ... Return the minimum number of swaps (any two
 * people) so that every couple sits side by side (seats 2k and 2k+1).
 * Input: [0,2,1,3] -> 1 · [3,2,0,1] -> 0
 * Explanation: README.md
 */
public class CouplesHoldingHands {
    public static void main(String[] args) {
        System.out.println(minSwapsCouples(new int[]{0, 2, 1, 3}));           // 1
        System.out.println(minSwapsCouplesUnionFind(new int[]{3, 2, 0, 1}));  // 0
    }

    /*
     * Approach 1 - greedy: for each couch, if the left person's partner is not on the right, bring the partner over with
     * one swap. A position map keeps each lookup O(1). Time O(n).
     */
    public static int minSwapsCouples(int[] row) {
        int[] r = row.clone(), pos = new int[r.length];
        for (int i = 0; i < r.length; i++) pos[r[i]] = i;
        int swaps = 0;
        for (int i = 0; i < r.length; i += 2) {
            int partner = r[i] ^ 1;                      // 0 <-> 1, 2 <-> 3, ...
            if (r[i + 1] == partner) continue;
            int j = pos[partner];
            pos[r[i + 1]] = j;
            pos[partner] = i + 1;
            r[j] = r[i + 1];
            r[i + 1] = partner;
            swaps++;
        }
        return swaps;
    }

    /*
     * Approach 2 - graph view: couches are nodes; the two people on a couch link it to the couches of their partners.
     * Each connected component of size s needs s - 1 swaps, so answer = couches - components.
     */
    public static int minSwapsCouplesUnionFind(int[] row) {
        int couches = row.length / 2;
        int[] parent = new int[couches];
        for (int i = 0; i < couches; i++) parent[i] = i;
        int components = couches;
        for (int i = 0; i < row.length; i += 2) {
            int a = find(parent, row[i] / 2), b = find(parent, row[i + 1] / 2);      // couple ids of the two people
            if (a != b) {
                parent[a] = b;
                components--;
            }
        }
        return couches - components;
    }

    private static int find(int[] p, int x) {
        while (p[x] != x) {
            p[x] = p[p[x]];
            x = p[x];
        }
        return x;
    }
}
