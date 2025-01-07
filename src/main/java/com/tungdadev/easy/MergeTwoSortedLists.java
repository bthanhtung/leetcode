package com.tungdadev.easy;

/**
 * Link: https://leetcode.com/problems/merge-two-sorted-lists/
 * You are given the heads of two sorted linked lists list1 and list2.
 * Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
 * Return the head of the merged linked list.
 */
public class MergeTwoSortedLists {
    public static void main(String[] args) {
        MergeTwoSortedLists solution = new MergeTwoSortedLists();

        // Tạo danh sách liên kết l1: 1 -> 2 -> 4
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        // Tạo danh sách liên kết l2: 1 -> 3 -> 4
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        // Hợp nhất hai danh sách đã sắp xếp
        ListNode result = solution.mergeTwoLists(l1, l2);

        // In danh sách kết quả
        solution.printList(result);  // Output: 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> null
    }

    // Hàm để in danh sách liên kết (for testing)
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    /*
     * Solution implement here
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Tạo một node giả để tiện xử lý
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Duyệt qua hai danh sách l1 và l2
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Nếu còn phần tử nào trong l1 hoặc l2, nối chúng vào kết quả
        if (list1 != null) {
            current.next = list1;
        } else if (list2 != null) {
            current.next = list2;
        }

        // Trả về kết quả, bỏ qua node giả đầu tiên
        return dummy.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
