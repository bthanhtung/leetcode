package MeetingRoomsIII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeetingRoomsIIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(0, MeetingRoomsIII.mostBooked(2, new int[][]{{0, 10}, {1, 5}, {2, 7}, {3, 4}}));
        assertEquals(1, MeetingRoomsIII.mostBooked(3, new int[][]{{1, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}}));
        Random r = new Random(2402);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + r.nextInt(5);
            int[] starts = r.ints(0, 40).distinct().limit(1 + r.nextInt(12)).toArray();
            int[][] meetings = new int[starts.length][];
            for (int i = 0; i < starts.length; i++) meetings[i] = new int[]{starts[i], starts[i] + 1 + r.nextInt(10)};
            assertEquals(MeetingRoomsIII.mostBookedScan(n, meetings), MeetingRoomsIII.mostBooked(n, meetings));
        }
    }
}
