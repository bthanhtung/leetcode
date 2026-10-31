package FirstBadVersion;

import java.util.function.IntPredicate;

/*
 * 278. First Bad Version — https://leetcode.com/problems/first-bad-version/
 * Versions 1..n; once a version is bad, all later ones are bad. Using isBadVersion(v) as few times as possible,
 * find the first bad version.
 * Input: n = 5, first bad = 4 -> 4 · n = 1, first bad = 1 -> 1
 * Explanation: README.md
 */
public class FirstBadVersion {
    public static void main(String[] args) {
        System.out.println(firstBadVersion(5, v -> v >= 4));                         // 4
        System.out.println(firstBadVersion(Integer.MAX_VALUE, v -> v >= 1702766719)); // 1702766719
    }

    /*
     * Binary search for the boundary in good...good bad...bad. Half-open style: keep the answer inside [lo, hi].
     * mid = lo + (hi - lo) / 2 avoids overflow when n is close to Integer.MAX_VALUE. About log2(n) calls.
     */
    public static int firstBadVersion(int n, IntPredicate isBadVersion) {
        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (isBadVersion.test(mid)) hi = mid;            // mid may be the first bad one
            else lo = mid + 1;                               // the first bad one is after mid
        }
        return lo;
    }
}
