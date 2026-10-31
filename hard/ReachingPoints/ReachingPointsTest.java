package ReachingPoints;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReachingPointsTest {

    @Test
    void examplesAndExhaustive() {
        assertEquals(true, ReachingPoints.reachingPoints(1, 1, 3, 5));
        assertEquals(false, ReachingPoints.reachingPoints(1, 1, 2, 2));
        assertEquals(true, ReachingPoints.reachingPoints(1, 1, 1, 1));
        assertEquals(true, ReachingPoints.reachingPoints(1, 1, 1, 1_000_000_000));
        assertEquals(false, ReachingPoints.reachingPoints(35, 13, 455955547, 420098884));
        for (int sx = 1; sx <= 6; sx++)
            for (int sy = 1; sy <= 6; sy++)
                for (int tx = 1; tx <= 25; tx++)
                    for (int ty = 1; ty <= 25; ty++)
                        assertEquals(ReachingPoints.reachingPointsBrute(sx, sy, tx, ty), ReachingPoints.reachingPoints(sx, sy, tx, ty),
                                sx + "," + sy + " -> " + tx + "," + ty);
    }
}
