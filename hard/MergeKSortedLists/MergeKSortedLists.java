package MergeKSortedLists;

import com.tungdadev.common.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 23. Merge k Sorted Lists — https://leetcode.com/problems/merge-k-sorted-lists/
 * Merge k sorted linked lists into one sorted list.
 * Input: [[1,4,5],[1,3,4],[2,6]] -> [1,1,2,3,4,4,5,6] · [] -> [] · [[]] -> []
 * Explanation: README.md
 */
public class MergeKSortedLists {
    public static void main(String[] args) {
        ListNode[] lists = {ListNode.of(1, 4, 5), ListNode.of(1, 3, 4), ListNode.of(2, 6)};
        System.out.println(mergeKLists(lists));        // [1, 1, 2, 3, 4, 4, 5, 6]
    }

    /* Approach 1 - copy every value, sort, rebuild. Time O(N log N), space O(N). */
    public static ListNode mergeKListsSort(ListNode[] lists) {
        List<Integer> values = new ArrayList<>();
        for (ListNode l : lists) for (ListNode p = l; p != null; p = p.next) values.add(p.val);
        Collections.sort(values);
        return ListNode.of(values.stream().mapToInt(Integer::intValue).toArray());
    }

    /* Approach 2 - min-heap holding the current head of every list; pop the smallest, push its successor. O(N log k). */
    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        for (ListNode l : lists) if (l != null) heap.add(l);
        ListNode dummy = new ListNode(0), tail = dummy;
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            tail.next = node;
            tail = node;
            if (node.next != null) heap.add(node.next);
        }
        return dummy.next;
    }

    /* Approach 3 - merge lists in pairs, halving their number each round (like merge sort). O(N log k), no heap. */
    public static ListNode mergeKListsDivide(ListNode[] lists) {
        if (lists.length == 0) return null;
        List<ListNode> round = new ArrayList<>(java.util.Arrays.asList(lists));   // lists may contain null
        while (round.size() > 1) {
            List<ListNode> next = new ArrayList<>();
            for (int i = 0; i < round.size(); i += 2) {
                next.add(i + 1 < round.size() ? merge(round.get(i), round.get(i + 1)) : round.get(i));
            }
            round = next;
        }
        return round.getFirst();
    }

    private static ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = a != null ? a : b;
        return dummy.next;
    }
}
