package TimeBasedKeyValueStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * 981. Time Based Key-Value Store — https://leetcode.com/problems/time-based-key-value-store/
 * TimeMap: set(key, value, timestamp) stores a value; get(key, timestamp) returns the value set with the largest
 * timestamp_prev <= timestamp, or "". Timestamps of set calls are strictly increasing.
 * Input: set("foo","bar",1), get("foo",1) -> "bar", get("foo",3) -> "bar", set("foo","bar2",4), get("foo",4) -> "bar2",
 *        get("foo",5) -> "bar2"
 * Explanation: README.md
 */
public class TimeBasedKeyValueStore {
    public static void main(String[] args) {
        TimeMap m = new TimeMap();
        m.set("foo", "bar", 1);
        System.out.println(m.get("foo", 3));    // bar
        m.set("foo", "bar2", 4);
        System.out.println(m.get("foo", 5));    // bar2
    }

    /* Approach 1 - timestamps arrive increasing, so each key's list is already sorted; get = upper-bound binary search. */
    public static class TimeMap {
        private final Map<String, List<Integer>> times = new HashMap<>();
        private final Map<String, List<String>> values = new HashMap<>();

        public void set(String key, String value, int timestamp) {
            times.computeIfAbsent(key, k -> new ArrayList<>()).add(timestamp);
            values.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }

        public String get(String key, int timestamp) {
            List<Integer> ts = times.get(key);
            if (ts == null) return "";
            int lo = 0, hi = ts.size();                   // first index with ts > timestamp
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (ts.get(mid) <= timestamp) lo = mid + 1;
                else hi = mid;
            }
            return lo == 0 ? "" : values.get(key).get(lo - 1);
        }
    }

    /* Approach 2 - a TreeMap per key; floorEntry does the search. O(log n) per call, works for any timestamp order. */
    public static class TimeMapTree {
        private final Map<String, TreeMap<Integer, String>> map = new HashMap<>();

        public void set(String key, String value, int timestamp) {
            map.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
        }

        public String get(String key, int timestamp) {
            TreeMap<Integer, String> t = map.get(key);
            if (t == null) return "";
            Map.Entry<Integer, String> e = t.floorEntry(timestamp);
            return e == null ? "" : e.getValue();
        }
    }
}
