package KInversePairsArray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KInversePairsArrayTest {

    private static int[] countByInversions(int n) {
        int[] counts = new int[n * (n - 1) / 2 + 1];
        permute(new int[n], new boolean[n], 0, counts);
        return counts;
    }

    private static void permute(int[] a, boolean[] used, int pos, int[] counts) {
        int n = a.length;
        if (pos == n) {
            int inv = 0;
            for (int i = 0; i < n; i++) for (int j = i + 1; j < n; j++) if (a[i] > a[j]) inv++;
            counts[inv]++;
            return;
        }
        for (int v = 0; v < n; v++) {
            if (used[v]) continue;
            used[v] = true;
            a[pos] = v;
            permute(a, used, pos + 1, counts);
            used[v] = false;
        }
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals(1, KInversePairsArray.kInversePairs(3, 0));
        assertEquals(2, KInversePairsArray.kInversePairs(3, 1));
        assertEquals(663677020, KInversePairsArray.kInversePairs(1000, 1000));
        for (int n = 1; n <= 7; n++) {
            int[] counts = countByInversions(n);
            for (int k = 0; k < counts.length + 2; k++) {
                int expected = k < counts.length ? counts[k] : 0;
                assertEquals(expected, KInversePairsArray.kInversePairs(n, k), "n=" + n + " k=" + k);
                assertEquals(expected, KInversePairsArray.kInversePairsWindow(n, k), "n=" + n + " k=" + k);
            }
        }
    }
}
