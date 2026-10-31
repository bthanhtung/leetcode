package ImplementQueueUsingStacks;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImplementQueueUsingStacksTest {

    @Test
    void randomOperationsMatchQueue() {
        Random r = new Random(232);
        ImplementQueueUsingStacks.MyQueue a = new ImplementQueueUsingStacks.MyQueue();
        ImplementQueueUsingStacks.MyQueueEagerPush b = new ImplementQueueUsingStacks.MyQueueEagerPush();
        Queue<Integer> ref = new ArrayDeque<>();
        for (int t = 0; t < 3000; t++) {
            int op = ref.isEmpty() ? 0 : r.nextInt(3);
            if (op == 0) {
                int x = r.nextInt(100);
                a.push(x);
                b.push(x);
                ref.add(x);
            } else if (op == 1) {
                int expected = ref.poll();
                assertEquals(expected, a.pop());
                assertEquals(expected, b.pop());
            } else {
                assertEquals(ref.peek(), a.peek());
                assertEquals(ref.peek(), b.peek());
            }
            assertEquals(ref.isEmpty(), a.empty());
            assertEquals(ref.isEmpty(), b.empty());
        }
    }
}
