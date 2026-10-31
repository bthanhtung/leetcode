package ImplementStackUsingQueues;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 225. Implement Stack using Queues — https://leetcode.com/problems/implement-stack-using-queues/
 * Implement a LIFO stack (push, pop, top, empty) using only queue operations: add to back, peek/poll from front, size, isEmpty.
 * Example: push(1), push(2), top() -> 2, pop() -> 2, empty() -> false
 * Explanation: README.md
 */
public class ImplementStackUsingQueues {
    public static void main(String[] args) {
        MyStack s = new MyStack();
        s.push(1);
        s.push(2);
        System.out.println(s.top());     // 2
        System.out.println(s.pop());     // 2
        System.out.println(s.empty());   // false
    }

    /*
     * Approach 1 - one queue: after adding x, rotate the older elements behind it so x is at the front.
     * push O(n), pop/top O(1).
     */
    public static class MyStack {
        private final Queue<Integer> queue = new ArrayDeque<>();

        public void push(int x) {
            queue.add(x);
            for (int i = queue.size() - 1; i > 0; i--) {
                queue.add(queue.poll());
            }
        }

        public int pop() {
            return queue.poll();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    /*
     * Approach 2 - two queues, cheap push: pop moves all but the last element to the helper queue, then swaps the queues.
     * push O(1), pop/top O(n).
     */
    public static class MyStackTwoQueues {
        private Queue<Integer> main = new ArrayDeque<>();
        private Queue<Integer> helper = new ArrayDeque<>();

        public void push(int x) {
            main.add(x);
        }

        public int pop() {
            while (main.size() > 1) helper.add(main.poll());
            int last = main.poll();
            Queue<Integer> tmp = main;
            main = helper;
            helper = tmp;
            return last;
        }

        public int top() {
            int last = pop();
            main.add(last);
            return last;
        }

        public boolean empty() {
            return main.isEmpty();
        }
    }
}
