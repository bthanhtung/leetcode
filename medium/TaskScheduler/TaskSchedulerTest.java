package TaskScheduler;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskSchedulerTest {

    @Test
    void examplesAndRandom() {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
        assertEquals(8, TaskScheduler.leastInterval(tasks, 2));
        assertEquals(6, TaskScheduler.leastInterval(tasks, 0));
        assertEquals(16, TaskScheduler.leastInterval("AAAAAABCDEFG".toCharArray(), 2));
        Random r = new Random(621);
        for (int t = 0; t < 2000; t++) {
            char[] ts = new char[1 + r.nextInt(20)];
            for (int i = 0; i < ts.length; i++) ts[i] = (char) ('A' + r.nextInt(5));
            int n = r.nextInt(5);
            assertEquals(TaskScheduler.leastIntervalSimulate(ts, n), TaskScheduler.leastInterval(ts, n));
        }
    }
}
