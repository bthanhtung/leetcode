package KthSmallestNumberInMultiplicationTable;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthSmallestNumberInMultiplicationTableTest {

    @Test
    void examplesAndAllSmallTables() {
        assertEquals(3, KthSmallestNumberInMultiplicationTable.findKthNumber(3, 3, 5));
        assertEquals(6, KthSmallestNumberInMultiplicationTable.findKthNumber(2, 3, 6));
        for (int m = 1; m <= 12; m++) {
            for (int n = 1; n <= 12; n++) {
                int[] all = new int[m * n];
                for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) all[i * n + j] = (i + 1) * (j + 1);
                Arrays.sort(all);
                for (int k = 1; k <= m * n; k++) {
                    assertEquals(all[k - 1], KthSmallestNumberInMultiplicationTable.findKthNumber(m, n, k));
                    assertEquals(all[k - 1], KthSmallestNumberInMultiplicationTable.findKthNumberHeap(m, n, k));
                }
            }
        }
    }
}
