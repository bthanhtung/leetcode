package FindInMountainArray;

/*
 * 1095. Find in Mountain Array — https://leetcode.com/problems/find-in-mountain-array/
 * A mountain array strictly increases to a peak, then strictly decreases. Accessible only through get(i) and length(),
 * with at most 100 get calls. Return the minimum index with value target, or -1.
 * Input: [1,2,3,4,5,3,1], target = 3 -> 2 · [0,1,2,4,2,1], target = 3 -> -1
 * Explanation: README.md
 */
public class FindInMountainArray {
    /** LeetCode's interface, with a call counter to check the 100-call limit. */
    public static class MountainArray {
        private final int[] data;
        public int calls;

        public MountainArray(int... data) {
            this.data = data;
        }

        public int get(int i) {
            calls++;
            return data[i];
        }

        public int length() {
            return data.length;
        }
    }

    public static void main(String[] args) {
        System.out.println(findInMountainArray(3, new MountainArray(1, 2, 3, 4, 5, 3, 1)));     // 2
        System.out.println(findInMountainArray(3, new MountainArray(0, 1, 2, 4, 2, 1)));        // -1
    }

    /*
     * 1) Binary search the peak: if get(mid) < get(mid + 1) we are on the way up. 2) Binary search the increasing part.
     * 3) If not found, binary search the decreasing part. About 3 log n calls.
     */
    public static int findInMountainArray(int target, MountainArray arr) {
        int n = arr.length();
        int lo = 0, hi = n - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr.get(mid) < arr.get(mid + 1)) lo = mid + 1;
            else hi = mid;
        }
        int peak = lo;
        int left = search(arr, target, 0, peak, true);
        return left != -1 ? left : search(arr, target, peak + 1, n - 1, false);
    }

    private static int search(MountainArray arr, int target, int lo, int hi, boolean ascending) {
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int v = arr.get(mid);
            if (v == target) return mid;
            if ((v < target) == ascending) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }
}
