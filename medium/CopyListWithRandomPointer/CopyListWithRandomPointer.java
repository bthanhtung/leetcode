package CopyListWithRandomPointer;

import java.util.HashMap;
import java.util.Map;

/*
 * 138. Copy List with Random Pointer — https://leetcode.com/problems/copy-list-with-random-pointer/
 * Each node has next and random (any node or null). Return a deep copy of the list.
 * Input: [[7,null],[13,0],[11,4],[10,2],[1,0]] (value, index of random) -> the same structure with brand-new nodes
 * Explanation: README.md
 */
public class CopyListWithRandomPointer {
    /** List node with a random pointer, as used by LeetCode. */
    public static class Node {
        public int val;
        public Node next, random;

        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Node head = build(new int[][]{{7, -1}, {13, 0}, {11, 4}, {10, 2}, {1, 0}});
        Node copy = copyRandomList(head);
        System.out.println(copy != head && copy.next.random.val == 7);     // true
    }

    /** Builds a list from {value, random index or -1} pairs. */
    public static Node build(int[][] spec) {
        Node[] nodes = new Node[spec.length];
        for (int i = 0; i < spec.length; i++) nodes[i] = new Node(spec[i][0]);
        for (int i = 0; i < spec.length; i++) {
            if (i + 1 < spec.length) nodes[i].next = nodes[i + 1];
            if (spec[i][1] >= 0) nodes[i].random = nodes[spec[i][1]];
        }
        return spec.length == 0 ? null : nodes[0];
    }

    /* Approach 1 - first pass creates copies in a map old -> new, second pass wires next and random. O(n) time and space. */
    public static Node copyRandomListMap(Node head) {
        Map<Node, Node> copy = new HashMap<>();
        for (Node p = head; p != null; p = p.next) copy.put(p, new Node(p.val));
        for (Node p = head; p != null; p = p.next) {
            copy.get(p).next = copy.get(p.next);
            copy.get(p).random = copy.get(p.random);      // get(null) is null
        }
        return copy.get(head);
    }

    /*
     * Approach 2 - O(1) extra space: put each copy right after its original (A -> A' -> B -> B'), so original.random.next
     * is the copy's random. Then split the two lists apart.
     */
    public static Node copyRandomList(Node head) {
        for (Node p = head; p != null; p = p.next.next) {
            Node c = new Node(p.val);
            c.next = p.next;
            p.next = c;
        }
        for (Node p = head; p != null; p = p.next.next) {
            if (p.random != null) p.next.random = p.random.next;
        }
        Node dummy = new Node(0), tail = dummy;
        for (Node p = head; p != null; p = p.next) {
            tail.next = p.next;
            tail = tail.next;
            p.next = p.next.next;                         // restore the original list
        }
        return dummy.next;
    }
}
