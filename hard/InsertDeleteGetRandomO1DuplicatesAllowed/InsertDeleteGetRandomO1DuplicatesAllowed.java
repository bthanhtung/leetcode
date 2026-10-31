package InsertDeleteGetRandomO1DuplicatesAllowed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/*
 * 381. Insert Delete GetRandom O(1) - Duplicates allowed — https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
 * RandomizedCollection (a multiset): insert(val) returns true if val was not present; remove(val) removes one
 * occurrence and returns whether it was present; getRandom() returns an element with probability proportional to
 * its count. All average O(1).
 * Input: insert(1) -> true, insert(1) -> false, insert(2) -> true, getRandom() -> 1 with probability 2/3,
 *        remove(1) -> true, getRandom() -> 1 or 2 with probability 1/2 each
 * Explanation: README.md
 */
public class InsertDeleteGetRandomO1DuplicatesAllowed {
    public static void main(String[] args) {
        RandomizedCollection c = new RandomizedCollection();
        System.out.println(c.insert(1));     // true
        System.out.println(c.insert(1));     // false
        System.out.println(c.insert(2));     // true
        System.out.println(c.remove(1));     // true
        System.out.println(c.getRandom());   // 1 or 2
    }

    /*
     * values holds every occurrence; picking a uniform index gives the right distribution. idx maps each value to the
     * set of positions where it lives. Remove: take any position of val, move the last element into it, fix that
     * element's index set, drop the last slot.
     */
    public static class RandomizedCollection {
        private final List<Integer> values = new ArrayList<>();
        private final Map<Integer, Set<Integer>> idx = new HashMap<>();
        private final Random random;

        public RandomizedCollection() {
            this(new Random());
        }

        RandomizedCollection(Random random) {
            this.random = random;
        }

        public boolean insert(int val) {
            Set<Integer> positions = idx.computeIfAbsent(val, v -> new LinkedHashSet<>());
            positions.add(values.size());
            values.add(val);
            return positions.size() == 1;
        }

        public boolean remove(int val) {
            Set<Integer> positions = idx.get(val);
            if (positions == null || positions.isEmpty()) return false;
            int hole = positions.iterator().next();
            positions.remove(hole);
            int lastIndex = values.size() - 1, last = values.get(lastIndex);
            if (hole != lastIndex) {
                values.set(hole, last);
                Set<Integer> lastPositions = idx.get(last);
                lastPositions.remove(lastIndex);
                lastPositions.add(hole);
            }
            values.remove(lastIndex);
            if (positions.isEmpty()) idx.remove(val);
            return true;
        }

        public int getRandom() {
            return values.get(random.nextInt(values.size()));
        }

        int size() {
            return values.size();
        }
    }
}
