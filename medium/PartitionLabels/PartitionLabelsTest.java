package PartitionLabels;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartitionLabelsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(9, 7, 8), PartitionLabels.partitionLabels("ababcbacadefegdehijhklij"));
        assertEquals(List.of(10), PartitionLabels.partitionLabels("eccbbbbdec"));
        assertEquals(List.of(1, 1, 1), PartitionLabels.partitionLabels("abc"));
        Random r = new Random(763);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(15); k > 0; k--) sb.append((char) ('a' + r.nextInt(6)));
            assertEquals(PartitionLabels.partitionLabels(sb.toString()), PartitionLabels.partitionLabelsIntervals(sb.toString()));
        }
    }
}
