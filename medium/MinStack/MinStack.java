package MinStack;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 155. Min Stack — https://leetcode.com/problems/min-stack/
 * Design a stack that supports push, pop, top and getMin, each in O(1).
 * Example: push(-2), push(0), push(-3), getMin() -> -3, pop(), top() -> 0, getMin() -> -2
 * Explanation: README.md
 */
public class MinStack {
    public static void main(String[] args) {
        MinStackPairs s = new MinStackPairs();
        s.push(-2);
        s.push(0);
        s.push(-3);
        System.out.println(s.getMin());   // -3
        s.pop();
        System.out.println(s.top());      // 0
        System.out.println(s.getMin());   // -2
    }

    /* Approach 1 - every entry remembers the minimum of the stack at the time it was pushed. */
    public static class MinStackPairs {
        private final Deque<int[]> stack = new ArrayDeque<>();     // {value, min so far}

        public void push(int val) {
            int min = stack.isEmpty() ? val : Math.min(val, stack.peek()[1]);
            stack.push(new int[]{val, min});
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek()[0];
        }

        public int getMin() {
            return stack.peek()[1];
        }
    }

    /* Approach 2 - a second stack holding only new minimums (<= current min, so duplicates are kept). */
    public static class MinStackTwoStacks {
        private final Deque<Integer> stack = new ArrayDeque<>(), mins = new ArrayDeque<>();

        public void push(int val) {
            stack.push(val);
            if (mins.isEmpty() || val <= mins.peek()) mins.push(val);
        }

        public void pop() {
            if (stack.pop().equals(mins.peek())) mins.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return mins.peek();
        }
    }

    /*
     * Approach 3 - one stack of differences from the current min (as long). A negative difference means this push
     * set a new min, and lets pop() recover the previous min. O(1) extra besides the stack itself.
     */
    public static class MinStackDiff {
        private final Deque<Long> stack = new ArrayDeque<>();
        private long min;

        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = val;
            } else {
                stack.push(val - min);
                if (val < min) min = val;
            }
        }

        public void pop() {
            long diff = stack.pop();
            if (diff < 0) min = min - diff;          // this element was the min; restore the previous one
        }

        public int top() {
            long diff = stack.peek();
            return (int) (diff < 0 ? min : min + diff);
        }

        public int getMin() {
            return (int) min;
        }
    }
}
