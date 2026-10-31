package MinStack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinStackTest {

    @Test
    void randomOperationsMatchList() {
        Random r = new Random(155);
        MinStack.MinStackPairs a = new MinStack.MinStackPairs();
        MinStack.MinStackTwoStacks b = new MinStack.MinStackTwoStacks();
        MinStack.MinStackDiff c = new MinStack.MinStackDiff();
        List<Integer> ref = new ArrayList<>();
        for (int t = 0; t < 20000; t++) {
            int op = ref.isEmpty() ? 0 : r.nextInt(3);
            if (op == 0) {
                int v = r.nextInt(5) == 0 ? (r.nextBoolean() ? Integer.MIN_VALUE : Integer.MAX_VALUE) : r.nextInt(21) - 10;
                a.push(v);
                b.push(v);
                c.push(v);
                ref.add(v);
            } else if (op == 1) {
                a.pop();
                b.pop();
                c.pop();
                ref.removeLast();
            } else {
                assertEquals(ref.getLast(), a.top());
                assertEquals(ref.getLast(), b.top());
                assertEquals(ref.getLast(), c.top());
                int min = Collections.min(ref);
                assertEquals(min, a.getMin());
                assertEquals(min, b.getMin());
                assertEquals(min, c.getMin());
            }
        }
    }
}
