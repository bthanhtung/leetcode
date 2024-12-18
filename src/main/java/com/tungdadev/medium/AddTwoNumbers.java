package com.tungdadev.medium;

/**
 * Link: https://leetcode.com/problems/add-two-numbers/description/
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 */
public class AddTwoNumbers {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        System.out.println(addTwoNumbers(l1, l2));
        ListNode current = addTwoNumbers(l1, l2);
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }

    /*
     * Solution implement here
     */
     public static class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }
      }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Initialize a dummy node to facilitate the creation of the result list
        ListNode dummy = new ListNode(0);
        // Pointer to traverse the result list
        ListNode curr = dummy;
        // Variable to hold the carry value during addition
        int carry = 0;

        // Iterate until both input lists are exhausted and there is no carry left
        while (l1 != null || l2 != null || carry > 0) {
            // Calculate the sum of the current digits and the carry
            int sum = carry;
            // Add the value of the current node in the first list if it exists
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next; // Move to the next node in the first list
            }
            // Add the value of the current node in the second list if it exists
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next; // Move to the next node in the second list
            }
            // Update the carry for the next iteration
            carry = sum / 10;
            // Create a new node with the sum mod 10 and append it to the result list
            curr.next = new ListNode(sum % 10);
            // Move the pointer to the next node in the result list
            curr = curr.next;
        }

        // Return the result list, skipping the dummy node
        return dummy.next;
    }
}
