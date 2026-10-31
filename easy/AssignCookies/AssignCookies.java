package AssignCookies;

import java.util.Arrays;

/*
 * 455. Assign Cookies — https://leetcode.com/problems/assign-cookies/
 * Child i is content with a cookie of size >= g[i]; cookie j has size s[j]. Each child gets at most one cookie.
 * Maximize the number of content children.
 * Input: g = [1,2,3], s = [1,1] -> 1 · g = [1,2], s = [1,2,3] -> 2
 * Explanation: README.md
 */
public class AssignCookies {
    public static void main(String[] args) {
        System.out.println(findContentChildren(new int[]{1, 2, 3}, new int[]{1, 1}));        // 1
        System.out.println(findContentChildrenBig(new int[]{1, 2}, new int[]{1, 2, 3}));     // 2
    }

    /* Approach 1 - sort both; give each cookie (small to large) to the least greedy child it can satisfy. O(n log n). */
    public static int findContentChildren(int[] g, int[] s) {
        int[] greed = g.clone(), size = s.clone();
        Arrays.sort(greed);
        Arrays.sort(size);
        int child = 0;
        for (int cookie = 0; cookie < size.length && child < greed.length; cookie++) {
            if (size[cookie] >= greed[child]) child++;      // a cookie too small for this child is too small for all later ones
        }
        return child;
    }

    /* Approach 2 - from the other side: the biggest cookie goes to the greediest child it can satisfy. */
    public static int findContentChildrenBig(int[] g, int[] s) {
        int[] greed = g.clone(), size = s.clone();
        Arrays.sort(greed);
        Arrays.sort(size);
        int cookie = size.length - 1, content = 0;
        for (int child = greed.length - 1; child >= 0 && cookie >= 0; child--) {
            if (size[cookie] >= greed[child]) {
                content++;
                cookie--;
            }
        }
        return content;
    }
}
