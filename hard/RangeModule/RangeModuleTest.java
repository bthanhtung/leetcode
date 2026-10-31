package RangeModule;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeModuleTest {

    @Test
    void exampleAndRandom() {
        RangeModule.RangeModuleTree m = new RangeModule.RangeModuleTree();
        m.addRange(10, 20);
        m.removeRange(14, 16);
        assertEquals(true, m.queryRange(10, 14));
        assertEquals(false, m.queryRange(13, 15));
        assertEquals(true, m.queryRange(16, 17));
        Random r = new Random(715);
        for (int t = 0; t < 200; t++) {
            RangeModule.RangeModuleTree a = new RangeModule.RangeModuleTree();
            RangeModule.RangeModuleList b = new RangeModule.RangeModuleList();
            boolean[] tracked = new boolean[40];
            for (int op = 0; op < 60; op++) {
                int l = r.nextInt(39), rr = l + 1 + r.nextInt(39 - l);
                switch (r.nextInt(3)) {
                    case 0 -> { a.addRange(l, rr); b.addRange(l, rr); for (int x = l; x < rr; x++) tracked[x] = true; }
                    case 1 -> { a.removeRange(l, rr); b.removeRange(l, rr); for (int x = l; x < rr; x++) tracked[x] = false; }
                    default -> {
                        boolean expected = true;
                        for (int x = l; x < rr; x++) expected &= tracked[x];
                        assertEquals(expected, a.queryRange(l, rr));
                        assertEquals(expected, b.queryRange(l, rr));
                    }
                }
            }
        }
    }
}
