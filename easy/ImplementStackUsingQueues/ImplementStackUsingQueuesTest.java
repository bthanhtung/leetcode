package ImplementStackUsingQueues;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImplementStackUsingQueuesTest {

    @Test
    void randomOperationsMatchArrayDeque() {
        Random r = new Random(225);
        ImplementStackUsingQueues.MyStack a = new ImplementStackUsingQueues.MyStack();
        ImplementStackUsingQueues.MyStackTwoQueues b = new ImplementStackUsingQueues.MyStackTwoQueues();
        Deque<Integer> ref = new ArrayDeque<>();
        for (int t = 0; t < 5000; t++) {
            int op = ref.isEmpty() ? 0 : r.nextInt(3);
            if (op == 0) {
                int x = r.nextInt(100);
                a.push(x);
                b.push(x);
                ref.push(x);
            } else if (op == 1) {
                int expected = ref.pop();
                assertEquals(expected, a.pop());
                assertEquals(expected, b.pop());
            } else {
                assertEquals(ref.peek(), a.top());
                assertEquals(ref.peek(), b.top());
            }
            assertEquals(ref.isEmpty(), a.empty());
            assertEquals(ref.isEmpty(), b.empty());
        }
    }
}
