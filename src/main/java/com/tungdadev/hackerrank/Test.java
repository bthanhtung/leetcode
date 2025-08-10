package com.tungdadev.hackerrank;

public class Test {
    public class ListNode {

        int val;

        ListNode next;

        ListNode() {}

        ListNode(int val) { this.val = val; }

        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    }

    class Solution {

        public ListNode removeNthFromEnd(ListNode head, int n) {

                ListNode dummy = new ListNode(0);

                dummy.next = head;

                ListNode first = dummy;

                ListNode second = dummy;

                for (int i = 1; i <= n + 1; i++) {

                    first = first.next;

                }

                while (first != null) {

                    first = first.next;

                    second = second.next;

                }

                second.next = second.next.next;

                return dummy.next;
        }

    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");


    }
}
