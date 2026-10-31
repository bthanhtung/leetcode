package BusRoutes;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusRoutesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, BusRoutes.numBusesToDestination(new int[][]{{1, 2, 7}, {3, 6, 7}}, 1, 6));
        assertEquals(-1, BusRoutes.numBusesToDestination(new int[][]{{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}}, 15, 12));
        assertEquals(0, BusRoutes.numBusesToDestination(new int[][]{{1, 2}}, 3, 3));
        Random r = new Random(815);
        for (int t = 0; t < 1000; t++) {
            int[][] routes = new int[1 + r.nextInt(5)][];
            for (int i = 0; i < routes.length; i++) routes[i] = r.ints(0, 10).distinct().limit(1 + r.nextInt(4)).toArray();
            int source = r.nextInt(10), target = r.nextInt(10);
            assertEquals(BusRoutes.numBusesToDestinationStops(routes, source, target), BusRoutes.numBusesToDestination(routes, source, target));
        }
    }
}
