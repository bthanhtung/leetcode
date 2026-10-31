package TopKFrequentElements;

import java.util.*;

/*
 * Link: https://leetcode.com/problems/top-k-frequent-elements/
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 * Input: nums = [1,1,1,2,2,3], k = 2 -> Output: [1,2]
 * Input: nums = [1], k = 1           -> Output: [1]
 * Constraints: 1 <= nums.length <= 10^5, -10^4 <= nums[i] <= 10^4, k is in [1, number of distinct elements],
 * and the answer is unique.
 * Follow up: your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 * Explanation: README.md
 */
public class TopKFrequentElements {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        System.out.println(Arrays.toString(topKSort(nums, 2)));        // [1, 2]
        System.out.println(Arrays.toString(topKHeap(nums, 2)));        // [2, 1] (any order)
        System.out.println(Arrays.toString(topKBucket(nums, 2)));      // [1, 2]
        System.out.println(Arrays.toString(topKQuickselect(nums, 2))); // any order
    }

    /* Step shared by every approach: value -> how many times it appears. O(n). */
    static Map<Integer, Integer> count(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.merge(x, 1, Integer::sum);
        }
        return freq;
    }

    /*
     * Approach 1 - sort the distinct values by frequency, take the first k.
     * Time O(n + m log m) with m distinct values, space O(m). Simple; fails the follow-up when m ~ n.
     */
    public static int[] topKSort(int[] nums, int k) {
        Map<Integer, Integer> freq = count(nums);
        List<Integer> values = new ArrayList<>(freq.keySet());
        values.sort((a, b) -> freq.get(b) - freq.get(a));
        int[] out = new int[k];
        for (int i = 0; i < k; i++) {
            out[i] = values.get(i);
        }
        return out;
    }

    /*
     * Approach 2 - min-heap of size k: keep the k most frequent seen so far; the root is the weakest of them
     * and is evicted when a more frequent value arrives. Time O(n + m log k), space O(m + k).
     * Best when k is small or the input is a stream.
     */
    public static int[] topKHeap(int[] nums, int k) {
        Map<Integer, Integer> freq = count(nums);
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.comparingInt(freq::get));
        for (int value : freq.keySet()) {
            heap.offer(value);
            if (heap.size() > k) {
                heap.poll();   // drop the least frequent
            }
        }
        int[] out = new int[k];
        for (int i = 0; i < k; i++) {
            out[i] = heap.poll();
        }
        return out;
    }

    /*
     * Approach 3 - bucket sort: a frequency is between 1 and n, so put each value in bucket[frequency]
     * and read buckets from the highest. Time O(n), space O(n). Meets the follow-up.
     */
    public static int[] topKBucket(int[] nums, int k) {
        Map<Integer, Integer> freq = count(nums);
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int f = e.getValue();
            if (buckets[f] == null) {
                buckets[f] = new ArrayList<>();
            }
            buckets[f].add(e.getKey());
        }
        int[] out = new int[k];
        int i = 0;
        for (int f = nums.length; f >= 1 && i < k; f--) {
            if (buckets[f] != null) {
                for (int value : buckets[f]) {
                    if (i == k) {
                        break;
                    }
                    out[i++] = value;
                }
            }
        }
        return out;
    }

    /*
     * Approach 4 - quickselect (Hoare's selection) on the distinct values by frequency: partition until the k most
     * frequent are on the right. Average O(n), worst O(m^2) (random pivot makes it unlikely), space O(m).
     * The answer is not sorted - fine, any order is accepted.
     */
    public static int[] topKQuickselect(int[] nums, int k) {
        Map<Integer, Integer> freq = count(nums);
        int[] values = freq.keySet().stream().mapToInt(Integer::intValue).toArray();
        int m = values.length;
        int target = m - k;                 // index where the k most frequent start (ascending order)
        Random random = new Random(42);
        int lo = 0, hi = m - 1;
        while (lo < hi) {
            int p = partition(values, freq, lo, hi, lo + random.nextInt(hi - lo + 1));
            if (p == target) {
                break;
            } else if (p < target) {
                lo = p + 1;
            } else {
                hi = p - 1;
            }
        }
        return Arrays.copyOfRange(values, target, m);
    }

    /* Lomuto partition by frequency: smaller frequencies left of the pivot. Returns the pivot's final index. */
    private static int partition(int[] a, Map<Integer, Integer> freq, int lo, int hi, int pivotIndex) {
        int pivotFreq = freq.get(a[pivotIndex]);
        swap(a, pivotIndex, hi);
        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (freq.get(a[i]) < pivotFreq) {
                swap(a, store++, i);
            }
        }
        swap(a, store, hi);
        return store;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
