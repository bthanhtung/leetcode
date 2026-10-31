package MeetingRoomsIII;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * 2402. Meeting Rooms III — https://leetcode.com/problems/meeting-rooms-iii/
 * n rooms 0..n-1; meetings [start, end) with unique starts. Each meeting takes the free room with the lowest number;
 * if none is free it waits for the earliest room to free up and keeps its duration. Return the room that held the most
 * meetings (lowest number on ties).
 * Input: n = 2, [[0,10],[1,5],[2,7],[3,4]] -> 0 · n = 3, [[1,20],[2,10],[3,5],[4,9],[6,8]] -> 1
 * Explanation: README.md
 */
public class MeetingRoomsIII {
    public static void main(String[] args) {
        System.out.println(mostBooked(2, new int[][]{{0, 10}, {1, 5}, {2, 7}, {3, 4}}));                   // 0
        System.out.println(mostBookedScan(3, new int[][]{{1, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}}));     // 1
    }

    /*
     * Approach 1 - a min-heap of free room numbers and a min-heap of busy rooms by (end time, room). For each meeting
     * (by start): release rooms that ended, take the lowest free room, or delay the meeting into the earliest-ending
     * room. Time O(m log n).
     */
    public static int mostBooked(int n, int[][] meetings) {
        int[][] ms = meetings.clone();
        Arrays.sort(ms, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<Integer> free = new PriorityQueue<>();
        for (int i = 0; i < n; i++) free.add(i);
        PriorityQueue<long[]> busy = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));
        int[] count = new int[n];
        for (int[] m : ms) {
            while (!busy.isEmpty() && busy.peek()[0] <= m[0]) free.add((int) busy.poll()[1]);
            if (!free.isEmpty()) {
                int room = free.poll();
                count[room]++;
                busy.add(new long[]{m[1], room});
            } else {
                long[] earliest = busy.poll();                         // wait for it, keep the duration
                count[(int) earliest[1]]++;
                busy.add(new long[]{earliest[0] + (m[1] - m[0]), earliest[1]});
            }
        }
        int best = 0;
        for (int i = 1; i < n; i++) if (count[i] > count[best]) best = i;
        return best;
    }

    /* Approach 2 - keep each room's free time in an array and scan all rooms per meeting. Time O(m * n). */
    public static int mostBookedScan(int n, int[][] meetings) {
        int[][] ms = meetings.clone();
        Arrays.sort(ms, (a, b) -> Integer.compare(a[0], b[0]));
        long[] freeAt = new long[n];
        int[] count = new int[n];
        for (int[] m : ms) {
            int room = -1, earliest = 0;
            for (int i = 0; i < n; i++) {
                if (freeAt[i] <= m[0]) {
                    room = i;
                    break;
                }
                if (freeAt[i] < freeAt[earliest]) earliest = i;
            }
            if (room >= 0) freeAt[room] = m[1];
            else {
                room = earliest;
                freeAt[room] += m[1] - m[0];
            }
            count[room]++;
        }
        int best = 0;
        for (int i = 1; i < n; i++) if (count[i] > count[best]) best = i;
        return best;
    }
}
