package ImplementQueueUsingStacks;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 232. Implement Queue using Stacks — https://leetcode.com/problems/implement-queue-using-stacks/
 * Implement a FIFO queue (push, pop, peek, empty) using only stack operations. Follow up: amortized O(1) per operation.
 * Example: push(1), push(2), peek() -> 1, pop() -> 1, empty() -> false
 * Explanation: README.md
 */
public class ImplementQueueUsingStacks {
    public static void main(String[] args) {
        MyQueue q = new MyQueue();
        q.push(1);
        q.push(2);
        System.out.println(q.peek());    // 1
        System.out.println(q.pop());     // 1
        System.out.println(q.empty());   // false
    }

    /*
     * Approach 1 - inbox and outbox. Push onto inbox; when outbox is empty, pour inbox into it (which reverses the order).
     * Every element moves at most once -> amortized O(1).
     */
    public static class MyQueue {
        private final Deque<Integer> in = new ArrayDeque<>();
        private final Deque<Integer> out = new ArrayDeque<>();

        public void push(int x) {
            in.push(x);
        }

        public int pop() {
            peek();
            return out.pop();
        }

        public int peek() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) out.push(in.pop());
            }
            return out.peek();
        }

        public boolean empty() {
            return in.isEmpty() && out.isEmpty();
        }
    }

    /* Approach 2 - keep one stack always in queue order: push moves everything aside, puts x at the bottom. push O(n). */
    public static class MyQueueEagerPush {
        private final Deque<Integer> stack = new ArrayDeque<>();
        private final Deque<Integer> tmp = new ArrayDeque<>();

        public void push(int x) {
            while (!stack.isEmpty()) tmp.push(stack.pop());
            stack.push(x);
            while (!tmp.isEmpty()) stack.push(tmp.pop());
        }

        public int pop() {
            return stack.pop();
        }

        public int peek() {
            return stack.peek();
        }

        public boolean empty() {
            return stack.isEmpty();
        }
    }
}
