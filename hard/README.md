# Hard — 13 / 115 solved

Ordered from easiest to hardest (by LeetCode acceptance rate). Each solved problem has its own folder with
the statement, a detailed explanation with diagrams (`README.md`), several approaches (`*.java`) and tests.

| Order | # | Problem | Topics | Acceptance | Status · approaches |
|---|---|---|---|---|---|
| 1 | 1028 | [Recover a Tree From Preorder Traversal](RecoverATreeFromPreorderTraversal/) [↗](https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/) | String, Tree, Depth-First Search, Binary Tree | 83.2% | ✅ Iterative with a stack of the current path · Recursive descent |
| 2 | 980 | [Unique Paths III](UniquePathsIII/) [↗](https://leetcode.com/problems/unique-paths-iii/) | Array, Backtracking, Bit Manipulation, Matrix | 83.0% | ✅ Backtracking over empty cells · Bitmask DP |
| 3 | 52 | [N-Queens II](NQueensII/) [↗](https://leetcode.com/problems/n-queens-ii/) | Backtracking, Algorithm X | 79.5% | ✅ Backtracking with boolean arrays · Backtracking with bitmasks |
| 4 | 51 | [N-Queens](NQueens/) [↗](https://leetcode.com/problems/n-queens/) | Array, Backtracking, Algorithm X | 76.7% | ✅ Backtracking with column/diagonal sets · Backtracking with bitmasks |
| 5 | 773 | [Sliding Puzzle](SlidingPuzzle/) [↗](https://leetcode.com/problems/sliding-puzzle/) | Array, Dynamic Programming, Backtracking, Breadth-First Search | 74.7% | ✅ BFS over board states · A* with Manhattan distance |
| 6 | 1074 | [Number of Submatrices That Sum to Target](NumberOfSubmatricesThatSumToTarget/) [↗](https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/) | Array, Hash Table, Matrix, Prefix Sum | 74.7% | ✅ Brute force with 2D prefix sums · Fix two rows + subarray-sum hash map |
| 7 | 1312 | [Minimum Insertion Steps to Make a String Palindrome](MinimumInsertionStepsToMakeAStringPalindrome/) [↗](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/) | String, Dynamic Programming | 74.6% | ✅ Interval DP · n - longest palindromic subsequence |
| 8 | 632 | [Smallest Range Covering Elements from K Lists](SmallestRangeCoveringElementsFromKLists/) [↗](https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/) | Array, Hash Table, Greedy, Sliding Window | 70.4% | ✅ Min-heap of one pointer per list · Merge all + sliding window |
| 9 | 1579 | [Remove Max Number of Edges to Keep Graph Fully Traversable](RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable/) [↗](https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/) | Union-Find, Graph Theory | 70.1% | ✅ Union-find, shared edges first |
| 10 | 992 | [Subarrays with K Different Integers](SubarraysWithKDifferentIntegers/) [↗](https://leetcode.com/problems/subarrays-with-k-different-integers/) | Array, Hash Table, Sliding Window, Counting | 69.2% | ✅ Brute force with a set · atMost(k) - atMost(k - 1) with sliding windows |
| 11 | 42 | [Trapping Rain Water](TrappingRainWater/) [↗](https://leetcode.com/problems/trapping-rain-water/) | Array, Two Pointers, Dynamic Programming, Stack | 68.5% | ✅ Brute force · Prefix max · Monotonic stack · Two pointers |
| 12 | 778 | Swim in Rising Water [↗](https://leetcode.com/problems/swim-in-rising-water/) | Array, Binary Search, Depth-First Search, Breadth-First Search | 68.4% | ⏳ |
| 13 | 1406 | Stone Game III [↗](https://leetcode.com/problems/stone-game-iii/) | Array, Math, Dynamic Programming, Minimax | 68.0% | ⏳ |
| 14 | 25 | Reverse Nodes in k-Group [↗](https://leetcode.com/problems/reverse-nodes-in-k-group/) | Linked List, Recursion | 67.6% | ⏳ |
| 15 | 2050 | Parallel Courses III [↗](https://leetcode.com/problems/parallel-courses-iii/) | Array, Dynamic Programming, Graph Theory, Topological Sort | 66.8% | ⏳ |
| 16 | 847 | Shortest Path Visiting All Nodes [↗](https://leetcode.com/problems/shortest-path-visiting-all-nodes/) | Dynamic Programming, Bit Manipulation, Breadth-First Search, Graph Theory | 66.6% | ⏳ |
| 17 | 37 | Sudoku Solver [↗](https://leetcode.com/problems/sudoku-solver/) | Array, Hash Table, Backtracking, Matrix | 65.6% | ⏳ |
| 18 | 726 | Number of Atoms [↗](https://leetcode.com/problems/number-of-atoms/) | Hash Table, String, Stack, Sorting | 65.3% | ⏳ |
| 19 | 312 | Burst Balloons [↗](https://leetcode.com/problems/burst-balloons/) | Array, Dynamic Programming | 64.6% | ⏳ |
| 20 | 407 | Trapping Rain Water II [↗](https://leetcode.com/problems/trapping-rain-water-ii/) | Array, Breadth-First Search, Heap (Priority Queue), Matrix | 64.3% | ⏳ |
| 21 | 857 | Minimum Cost to Hire K Workers [↗](https://leetcode.com/problems/minimum-cost-to-hire-k-workers/) | Array, Greedy, Sorting, Heap (Priority Queue) | 63.9% | ⏳ |
| 22 | 1547 | Minimum Cost to Cut a Stick [↗](https://leetcode.com/problems/minimum-cost-to-cut-a-stick/) | Array, Dynamic Programming, Sorting | 63.5% | ⏳ |
| 23 | 995 | Minimum Number of K Consecutive Bit Flips [↗](https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/) | Array, Bit Manipulation, Queue, Sliding Window | 62.5% | ⏳ |
| 24 | 1092 | Shortest Common Supersequence  [↗](https://leetcode.com/problems/shortest-common-supersequence/) | String, Dynamic Programming, Longest Common Subsequence | 62.4% | ⏳ |
| 25 | 410 | Split Array Largest Sum [↗](https://leetcode.com/problems/split-array-largest-sum/) | Array, Binary Search, Dynamic Programming, Greedy | 61.7% | ⏳ |
| 26 | 1220 | Count Vowels Permutation [↗](https://leetcode.com/problems/count-vowels-permutation/) | Dynamic Programming | 61.4% | ⏳ |
| 27 | 297 | Serialize and Deserialize Binary Tree [↗](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | String, Tree, Depth-First Search, Breadth-First Search | 61.3% | ⏳ |
| 28 | 23 | Merge k Sorted Lists [↗](https://leetcode.com/problems/merge-k-sorted-lists/) | Linked List, Divide and Conquer, Heap (Priority Queue), Merge Sort | 60.8% | ⏳ |
| 29 | 352 | Data Stream as Disjoint Intervals [↗](https://leetcode.com/problems/data-stream-as-disjoint-intervals/) | Hash Table, Binary Search, Union-Find, Design | 60.8% | ⏳ |
| 30 | 85 | Maximal Rectangle [↗](https://leetcode.com/problems/maximal-rectangle/) | Array, Dynamic Programming, Stack, Matrix | 60.2% | ⏳ |
| 31 | 765 | Couples Holding Hands [↗](https://leetcode.com/problems/couples-holding-hands/) | Greedy, Depth-First Search, Breadth-First Search, Union-Find | 59.9% | ⏳ |
| 32 | 1335 | Minimum Difficulty of a Job Schedule [↗](https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/) | Array, Dynamic Programming | 59.8% | ⏳ |
| 33 | 514 | Freedom Trail [↗](https://leetcode.com/problems/freedom-trail/) | String, Dynamic Programming, Depth-First Search, Breadth-First Search | 59.7% | ⏳ |
| 34 | 679 | 24 Game [↗](https://leetcode.com/problems/24-game/) | Array, Math, Backtracking | 59.7% | ⏳ |
| 35 | 753 | Cracking the Safe [↗](https://leetcode.com/problems/cracking-the-safe/) | String, Depth-First Search, Graph Theory, Eulerian Circuit | 58.9% | ⏳ |
| 36 | 827 | Making A Large Island [↗](https://leetcode.com/problems/making-a-large-island/) | Array, Depth-First Search, Breadth-First Search, Union-Find | 57.3% | ⏳ |
| 37 | 329 | Longest Increasing Path in a Matrix [↗](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/) | Array, Dynamic Programming, Depth-First Search, Breadth-First Search | 57.2% | ⏳ |
| 38 | 850 | Rectangle Area II [↗](https://leetcode.com/problems/rectangle-area-ii/) | Array, Segment Tree, Sweep Line, Ordered Set | 56.9% | ⏳ |
| 39 | 140 | Word Break II [↗](https://leetcode.com/problems/word-break-ii/) | Array, Hash Table, String, Dynamic Programming | 56.4% | ⏳ |
| 40 | 668 | Kth Smallest Number in Multiplication Table [↗](https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/) | Math, Binary Search | 55.3% | ⏳ |
| 41 | 295 | Find Median from Data Stream [↗](https://leetcode.com/problems/find-median-from-data-stream/) | Two Pointers, Design, Sorting, Heap (Priority Queue) | 55.2% | ⏳ |
| 42 | 768 | Max Chunks To Make Sorted II [↗](https://leetcode.com/problems/max-chunks-to-make-sorted-ii/) | Array, Stack, Greedy, Sorting | 55.2% | ⏳ |
| 43 | 123 | Best Time to Buy and Sell Stock III [↗](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/) | Array, Dynamic Programming | 55.0% | ⏳ |
| 44 | 330 | Patching Array [↗](https://leetcode.com/problems/patching-array/) | Array, Greedy | 54.9% | ⏳ |
| 45 | 1235 | Maximum Profit in Job Scheduling [↗](https://leetcode.com/problems/maximum-profit-in-job-scheduling/) | Array, Binary Search, Dynamic Programming, Sorting | 54.9% | ⏳ |
| 46 | 60 | Permutation Sequence [↗](https://leetcode.com/problems/permutation-sequence/) | Math, Recursion | 54.7% | ⏳ |
| 47 | 115 | Distinct Subsequences [↗](https://leetcode.com/problems/distinct-subsequences/) | String, Dynamic Programming | 54.5% | ⏳ |
| 48 | 502 | IPO [↗](https://leetcode.com/problems/ipo/) | Array, Greedy, Sorting, Heap (Priority Queue) | 54.0% | ⏳ |
| 49 | 587 | Erect the Fence [↗](https://leetcode.com/problems/erect-the-fence/) | Array, Math, Geometry, Convex Hull | 53.7% | ⏳ |
| 50 | 1032 | Stream of Characters [↗](https://leetcode.com/problems/stream-of-characters/) | Array, String, Design, Trie | 53.0% | ⏳ |
| 51 | 301 | Remove Invalid Parentheses [↗](https://leetcode.com/problems/remove-invalid-parentheses/) | String, Backtracking, Breadth-First Search | 52.6% | ⏳ |
| 52 | 68 | Text Justification [↗](https://leetcode.com/problems/text-justification/) | Array, String, Simulation | 52.5% | ⏳ |
| 53 | 2009 | Minimum Number of Operations to Make Array Continuous [↗](https://leetcode.com/problems/minimum-number-of-operations-to-make-array-continuous/) | Array, Hash Table, Binary Search, Sliding Window | 51.7% | ⏳ |
| 54 | 188 | Best Time to Buy and Sell Stock IV [↗](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/) | Array, Dynamic Programming | 51.6% | ⏳ |
| 55 | 691 | Stickers to Spell Word [↗](https://leetcode.com/problems/stickers-to-spell-word/) | Array, Hash Table, String, Dynamic Programming | 51.4% | ⏳ |
| 56 | 2402 | Meeting Rooms III [↗](https://leetcode.com/problems/meeting-rooms-iii/) | Array, Hash Table, Sorting, Heap (Priority Queue) | 51.3% | ⏳ |
| 57 | 84 | Largest Rectangle in Histogram [↗](https://leetcode.com/problems/largest-rectangle-in-histogram/) | Array, Stack, Monotonic Stack, Range Minimum/Maximum Query | 51.1% | ⏳ |
| 58 | 1345 | Jump Game IV [↗](https://leetcode.com/problems/jump-game-iv/) | Array, Hash Table, Breadth-First Search | 51.1% | ⏳ |
| 59 | 460 | LFU Cache [↗](https://leetcode.com/problems/lfu-cache/) | Hash Table, Linked List, Design, Doubly-Linked List | 50.6% | ⏳ |
| 60 | 472 | Concatenated Words [↗](https://leetcode.com/problems/concatenated-words/) | Array, String, Dynamic Programming, Depth-First Search | 50.1% | ⏳ |
| 61 | 546 | Remove Boxes [↗](https://leetcode.com/problems/remove-boxes/) | Array, Dynamic Programming, Memoization | 50.0% | ⏳ |
| 62 | 135 | Candy [↗](https://leetcode.com/problems/candy/) | Array, Greedy | 49.5% | ⏳ |
| 63 | 239 | Sliding Window Maximum [↗](https://leetcode.com/problems/sliding-window-maximum/) | Array, Queue, Sliding Window, Heap (Priority Queue) | 49.4% | ⏳ |
| 64 | 629 | K Inverse Pairs Array [↗](https://leetcode.com/problems/k-inverse-pairs-array/) | Dynamic Programming | 49.1% | ⏳ |
| 65 | 76 | Minimum Window Substring [↗](https://leetcode.com/problems/minimum-window-substring/) | Hash Table, String, Sliding Window | 48.7% | ⏳ |
| 66 | 968 | Binary Tree Cameras [↗](https://leetcode.com/problems/binary-tree-cameras/) | Dynamic Programming, Tree, Depth-First Search, Binary Tree | 48.3% | ⏳ |
| 67 | 4 | [Median of Two Sorted Arrays](MedianOfTwoSortedArrays/) [↗](https://leetcode.com/problems/median-of-two-sorted-arrays/) | Array, Binary Search, Divide and Conquer | 47.9% | ✅ Merge to the middle · k-th smallest · Binary search on partition |
| 68 | 403 | Frog Jump [↗](https://leetcode.com/problems/frog-jump/) | Array, Dynamic Programming | 47.8% | ⏳ |
| 69 | 815 | Bus Routes [↗](https://leetcode.com/problems/bus-routes/) | Array, Hash Table, Breadth-First Search | 47.6% | ⏳ |
| 70 | 224 | Basic Calculator [↗](https://leetcode.com/problems/basic-calculator/) | Math, String, Stack, Recursion | 47.3% | ⏳ |
| 71 | 154 | Find Minimum in Rotated Sorted Array II [↗](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/) | Array, Binary Search | 47.2% | ⏳ |
| 72 | 127 | Word Ladder [↗](https://leetcode.com/problems/word-ladder/) | Hash Table, String, Breadth-First Search, Bidirectional Search | 46.8% | ⏳ |
| 73 | 218 | The Skyline Problem [↗](https://leetcode.com/problems/the-skyline-problem/) | Array, Divide and Conquer, Binary Indexed Tree, Segment Tree | 46.2% | ⏳ |
| 74 | 87 | Scramble String [↗](https://leetcode.com/problems/scramble-string/) | String, Dynamic Programming | 46.0% | ⏳ |
| 75 | 715 | Range Module [↗](https://leetcode.com/problems/range-module/) | Design, Segment Tree, Ordered Set | 45.7% | ⏳ |
| 76 | 1499 | Max Value of Equation [↗](https://leetcode.com/problems/max-value-of-equation/) | Array, Queue, Sliding Window, Heap (Priority Queue) | 45.4% | ⏳ |
| 77 | 332 | Reconstruct Itinerary [↗](https://leetcode.com/problems/reconstruct-itinerary/) | Array, String, Depth-First Search, Graph Theory | 45.2% | ⏳ |
| 78 | 432 | All O`one Data Structure [↗](https://leetcode.com/problems/all-oone-data-structure/) | Hash Table, Linked List, Design, Doubly-Linked List | 44.7% | ⏳ |
| 79 | 315 | Count of Smaller Numbers After Self [↗](https://leetcode.com/problems/count-of-smaller-numbers-after-self/) | Array, Binary Search, Divide and Conquer, Binary Indexed Tree | 44.3% | ⏳ |
| 80 | 952 | Largest Component Size by Common Factor [↗](https://leetcode.com/problems/largest-component-size-by-common-factor/) | Array, Hash Table, Math, Union-Find | 44.2% | ⏳ |
| 81 | 41 | First Missing Positive [↗](https://leetcode.com/problems/first-missing-positive/) | Array, Hash Table | 44.0% | ⏳ |
| 82 | 282 | Expression Add Operators [↗](https://leetcode.com/problems/expression-add-operators/) | Math, String, Backtracking | 44.0% | ⏳ |
| 83 | 214 | Shortest Palindrome [↗](https://leetcode.com/problems/shortest-palindrome/) | String, Rolling Hash, String Matching, Hash Function | 43.4% | ⏳ |
| 84 | 829 | Consecutive Numbers Sum [↗](https://leetcode.com/problems/consecutive-numbers-sum/) | Math, Enumeration | 43.2% | ⏳ |
| 85 | 124 | Binary Tree Maximum Path Sum [↗](https://leetcode.com/problems/binary-tree-maximum-path-sum/) | Dynamic Programming, Tree, Depth-First Search, Binary Tree | 42.9% | ⏳ |
| 86 | 174 | Dungeon Game [↗](https://leetcode.com/problems/dungeon-game/) | Array, Dynamic Programming, Matrix | 42.6% | ⏳ |
| 87 | 630 | Course Schedule III [↗](https://leetcode.com/problems/course-schedule-iii/) | Array, Greedy, Sorting, Heap (Priority Queue) | 42.4% | ⏳ |
| 88 | 871 | Minimum Number of Refueling Stops [↗](https://leetcode.com/problems/minimum-number-of-refueling-stops/) | Array, Dynamic Programming, Greedy, Heap (Priority Queue) | 42.1% | ⏳ |
| 89 | 1095 | Find in Mountain Array [↗](https://leetcode.com/problems/find-in-mountain-array/) | Array, Binary Search, Interactive, Ternary Search | 42.1% | ⏳ |
| 90 | 975 | Odd Even Jump [↗](https://leetcode.com/problems/odd-even-jump/) | Array, Dynamic Programming, Stack, Sorting | 41.7% | ⏳ |
| 91 | 32 | Longest Valid Parentheses [↗](https://leetcode.com/problems/longest-valid-parentheses/) | String, Dynamic Programming, Stack, Bracket Sequences | 41.4% | ⏳ |
| 92 | 745 | Prefix and Suffix Search [↗](https://leetcode.com/problems/prefix-and-suffix-search/) | Array, Hash Table, String, Design | 41.3% | ⏳ |
| 93 | 327 | Count of Range Sum [↗](https://leetcode.com/problems/count-of-range-sum/) | Array, Binary Search, Divide and Conquer, Binary Indexed Tree | 40.3% | ⏳ |
| 94 | 233 | Number of Digit One [↗](https://leetcode.com/problems/number-of-digit-one/) | Math, Dynamic Programming, Recursion | 40.2% | ⏳ |
| 95 | 480 | Sliding Window Median [↗](https://leetcode.com/problems/sliding-window-median/) | Array, Hash Table, Sliding Window, Heap (Priority Queue) | 39.2% | ⏳ |
| 96 | 212 | Word Search II [↗](https://leetcode.com/problems/word-search-ii/) | Array, String, Backtracking, Trie | 39.1% | ⏳ |
| 97 | 354 | Russian Doll Envelopes [↗](https://leetcode.com/problems/russian-doll-envelopes/) | Array, Binary Search, Dynamic Programming, Sorting | 38.4% | ⏳ |
| 98 | 132 | Palindrome Partitioning II [↗](https://leetcode.com/problems/palindrome-partitioning-ii/) | String, Dynamic Programming | 38.2% | ⏳ |
| 99 | 803 | Bricks Falling When Hit [↗](https://leetcode.com/problems/bricks-falling-when-hit/) | Array, Union-Find, Matrix | 38.2% | ⏳ |
| 100 | 336 | Palindrome Pairs [↗](https://leetcode.com/problems/palindrome-pairs/) | Array, Hash Table, String, Trie | 37.8% | ⏳ |
| 101 | 321 | Create Maximum Number [↗](https://leetcode.com/problems/create-maximum-number/) | Array, Two Pointers, Stack, Greedy | 37.6% | ⏳ |
| 102 | 381 | Insert Delete GetRandom O(1) - Duplicates allowed [↗](https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/) | Array, Hash Table, Math, Design | 37.2% | ⏳ |
| 103 | 685 | Redundant Connection II [↗](https://leetcode.com/problems/redundant-connection-ii/) | Depth-First Search, Breadth-First Search, Union-Find, Graph Theory | 36.8% | ⏳ |
| 104 | 30 | Substring with Concatenation of All Words [↗](https://leetcode.com/problems/substring-with-concatenation-of-all-words/) | Hash Table, String, Sliding Window | 35.6% | ⏳ |
| 105 | 493 | Reverse Pairs [↗](https://leetcode.com/problems/reverse-pairs/) | Array, Binary Search, Divide and Conquer, Binary Indexed Tree | 35.5% | ⏳ |
| 106 | 273 | Integer to English Words [↗](https://leetcode.com/problems/integer-to-english-words/) | Math, String, Recursion | 35.3% | ⏳ |
| 107 | 780 | Reaching Points [↗](https://leetcode.com/problems/reaching-points/) | Math, Euclidean Algorithm, Greatest Common Divisor | 34.9% | ⏳ |
| 108 | 862 | Shortest Subarray with Sum at Least K [↗](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/) | Array, Binary Search, Queue, Sliding Window | 33.3% | ⏳ |
| 109 | 44 | Wildcard Matching [↗](https://leetcode.com/problems/wildcard-matching/) | String, Dynamic Programming, Greedy, Recursion | 33.1% | ⏳ |
| 110 | 564 | Find the Closest Palindrome [↗](https://leetcode.com/problems/find-the-closest-palindrome/) | Math, String | 32.2% | ⏳ |
| 111 | 10 | [Regular Expression Matching](RegularExpressionMatching/) [↗](https://leetcode.com/problems/regular-expression-matching/) | String, Dynamic Programming, Recursion | 32.0% | ✅ Recursion · Memoized recursion · Bottom-up DP |
| 112 | 149 | Max Points on a Line [↗](https://leetcode.com/problems/max-points-on-a-line/) | Array, Hash Table, Math, Geometry | 31.7% | ⏳ |
| 113 | 887 | Super Egg Drop [↗](https://leetcode.com/problems/super-egg-drop/) | Math, Binary Search, Dynamic Programming | 31.0% | ⏳ |
| 114 | 126 | Word Ladder II [↗](https://leetcode.com/problems/word-ladder-ii/) | Hash Table, String, Backtracking, Breadth-First Search | 28.2% | ⏳ |
| 115 | 65 | Valid Number [↗](https://leetcode.com/problems/valid-number/) | String | 23.9% | ⏳ |

## By topic

- **Algorithm X** (2): [52. N-Queens II](NQueensII/) · [51. N-Queens](NQueens/)
- **Array** (8): [980. Unique Paths III](UniquePathsIII/) · [51. N-Queens](NQueens/) · [773. Sliding Puzzle](SlidingPuzzle/) · [1074. Number of Submatrices That Sum to Target](NumberOfSubmatricesThatSumToTarget/) · [632. Smallest Range Covering Elements from K Lists](SmallestRangeCoveringElementsFromKLists/) · [992. Subarrays with K Different Integers](SubarraysWithKDifferentIntegers/) · [42. Trapping Rain Water](TrappingRainWater/) · [4. Median of Two Sorted Arrays](MedianOfTwoSortedArrays/)
- **Backtracking** (4): [980. Unique Paths III](UniquePathsIII/) · [52. N-Queens II](NQueensII/) · [51. N-Queens](NQueens/) · [773. Sliding Puzzle](SlidingPuzzle/)
- **Binary Search** (1): [4. Median of Two Sorted Arrays](MedianOfTwoSortedArrays/)
- **Bit Manipulation** (1): [980. Unique Paths III](UniquePathsIII/)
- **Depth-First Search** (1): [1028. Recover a Tree From Preorder Traversal](RecoverATreeFromPreorderTraversal/)
- **Divide and Conquer** (1): [4. Median of Two Sorted Arrays](MedianOfTwoSortedArrays/)
- **Dynamic Programming** (4): [773. Sliding Puzzle](SlidingPuzzle/) · [1312. Minimum Insertion Steps to Make a String Palindrome](MinimumInsertionStepsToMakeAStringPalindrome/) · [42. Trapping Rain Water](TrappingRainWater/) · [10. Regular Expression Matching](RegularExpressionMatching/)
- **Graph Theory** (1): [1579. Remove Max Number of Edges to Keep Graph Fully Traversable](RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable/)
- **Greedy** (1): [632. Smallest Range Covering Elements from K Lists](SmallestRangeCoveringElementsFromKLists/)
- **Hash Table** (3): [1074. Number of Submatrices That Sum to Target](NumberOfSubmatricesThatSumToTarget/) · [632. Smallest Range Covering Elements from K Lists](SmallestRangeCoveringElementsFromKLists/) · [992. Subarrays with K Different Integers](SubarraysWithKDifferentIntegers/)
- **Matrix** (1): [1074. Number of Submatrices That Sum to Target](NumberOfSubmatricesThatSumToTarget/)
- **Recursion** (1): [10. Regular Expression Matching](RegularExpressionMatching/)
- **Sliding Window** (1): [992. Subarrays with K Different Integers](SubarraysWithKDifferentIntegers/)
- **String** (3): [1028. Recover a Tree From Preorder Traversal](RecoverATreeFromPreorderTraversal/) · [1312. Minimum Insertion Steps to Make a String Palindrome](MinimumInsertionStepsToMakeAStringPalindrome/) · [10. Regular Expression Matching](RegularExpressionMatching/)
- **Tree** (1): [1028. Recover a Tree From Preorder Traversal](RecoverATreeFromPreorderTraversal/)
- **Two Pointers** (1): [42. Trapping Rain Water](TrappingRainWater/)
- **Union-Find** (1): [1579. Remove Max Number of Edges to Keep Graph Fully Traversable](RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable/)
