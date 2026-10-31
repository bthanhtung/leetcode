package GasStation;

/*
 * 134. Gas Station — https://leetcode.com/problems/gas-station/
 * n stations on a circle: gas[i] fuel available, cost[i] fuel to drive to station i + 1. Starting with an empty
 * tank, return the unique start index from which you can complete the circuit, or -1.
 * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2] -> 3 · gas = [2,3,4], cost = [3,4,3] -> -1
 * Explanation: README.md
 */
public class GasStation {
    public static void main(String[] args) {
        System.out.println(canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));     // 3
    }

    /*
     * If total gas < total cost, impossible. Otherwise, whenever the tank goes negative after station i, no station
     * between the current start and i can be the start either - restart at i + 1. Time O(n), space O(1).
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return total >= 0 ? start : -1;
    }

    /* Brute force reference: simulate from every start. O(n^2). */
    public static int canCompleteCircuitBrute(int[] gas, int[] cost) {
        int n = gas.length;
        for (int s = 0; s < n; s++) {
            int tank = 0, k = 0;
            for (; k < n; k++) {
                int i = (s + k) % n;
                tank += gas[i] - cost[i];
                if (tank < 0) break;
            }
            if (k == n) return s;
        }
        return -1;
    }
}
