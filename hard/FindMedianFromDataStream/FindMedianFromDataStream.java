package FindMedianFromDataStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 295. Find Median from Data Stream — https://leetcode.com/problems/find-median-from-data-stream/
 * MedianFinder: addNum(num) adds a number from a stream; findMedian() returns the median of all numbers so far.
 * Example: addNum(1), addNum(2), findMedian() -> 1.5, addNum(3), findMedian() -> 2.0
 * Explanation: README.md
 */
public class FindMedianFromDataStream {
    public static void main(String[] args) {
        MedianFinder m = new MedianFinder();
        m.addNum(1);
        m.addNum(2);
        System.out.println(m.findMedian());    // 1.5
        m.addNum(3);
        System.out.println(m.findMedian());    // 2.0
    }

    /*
     * Approach 1 - two heaps: low is a max-heap with the smaller half, high a min-heap with the larger half, and
     * low.size() is high.size() or one more. The median is low's top, or the mean of both tops. add O(log n), find O(1).
     */
    public static class MedianFinder {
        private final PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder());
        private final PriorityQueue<Integer> high = new PriorityQueue<>();

        public void addNum(int num) {
            low.add(num);
            high.add(low.poll());                          // the largest of the small half moves up
            if (high.size() > low.size()) low.add(high.poll());
        }

        public double findMedian() {
            return low.size() > high.size() ? low.peek() : (low.peek() + (double) high.peek()) / 2;
        }
    }

    /* Approach 2 - keep everything sorted, inserting with binary search. add O(n) (shifting), find O(1). */
    public static class MedianFinderSorted {
        private final List<Integer> sorted = new ArrayList<>();

        public void addNum(int num) {
            int i = Collections.binarySearch(sorted, num);
            sorted.add(i >= 0 ? i : -i - 1, num);
        }

        public double findMedian() {
            int n = sorted.size();
            return n % 2 == 1 ? sorted.get(n / 2) : (sorted.get(n / 2 - 1) + (double) sorted.get(n / 2)) / 2;
        }
    }
}
