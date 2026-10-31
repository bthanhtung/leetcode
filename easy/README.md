# Easy — 62 / 127 solved

Ordered from easiest to hardest (by LeetCode acceptance rate). Each solved problem has its own folder with
the statement, a detailed explanation with diagrams (`README.md`), several approaches (`*.java`) and tests.

| Order | # | Problem | Topics | Acceptance | Status · approaches |
|---|---|---|---|---|---|
| 1 | 1108 | [Defanging an IP Address](DefangingAnIPAddress/) [↗](https://leetcode.com/problems/defanging-an-ip-address/) | String | 90.2% | ✅ String replace · StringBuilder |
| 2 | 1512 | [Number of Good Pairs](NumberOfGoodPairs/) [↗](https://leetcode.com/problems/number-of-good-pairs/) | Array, Hash Table, Math, Counting | 90.0% | ✅ Brute force · Count while scanning · Combinatorics |
| 3 | 771 | [Jewels and Stones](JewelsAndStones/) [↗](https://leetcode.com/problems/jewels-and-stones/) | Hash Table, String | 89.7% | ✅ Nested scan · Hash set · Boolean table |
| 4 | 1672 | [Richest Customer Wealth](RichestCustomerWealth/) [↗](https://leetcode.com/problems/richest-customer-wealth/) | Array, Matrix | 88.9% | ✅ Nested loops · Streams |
| 5 | 938 | [Range Sum of BST](RangeSumOfBST/) [↗](https://leetcode.com/problems/range-sum-of-bst/) | Tree, Depth-First Search, Binary Search Tree, Binary Tree | 87.7% | ✅ DFS with pruning · Iterative stack · Inorder |
| 6 | 1480 | [Running Sum of 1d Array](RunningSumOf1dArray/) [↗](https://leetcode.com/problems/running-sum-of-1d-array/) | Array, Prefix Sum | 87.1% | ✅ New array · In place |
| 7 | 557 | [Reverse Words in a String III](ReverseWordsInAStringIII/) [↗](https://leetcode.com/problems/reverse-words-in-a-string-iii/) | Two Pointers, String | 84.2% | ✅ Split + reverse · Two pointers on char array |
| 8 | 700 | [Search in a Binary Search Tree](SearchInABinarySearchTree/) [↗](https://leetcode.com/problems/search-in-a-binary-search-tree/) | Tree, Binary Search Tree, Binary Tree | 83.3% | ✅ Recursive · Iterative |
| 9 | 876 | [Middle of the Linked List](MiddleOfTheLinkedList/) [↗](https://leetcode.com/problems/middle-of-the-linked-list/) | Linked List, Two Pointers | 82.5% | ✅ Count then walk · Array of nodes · Slow/fast pointers |
| 10 | 1290 | [Convert Binary Number in a Linked List to Integer](ConvertBinaryNumberInALinkedListToInteger/) [↗](https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/) | Linked List, Math | 82.5% | ✅ Shift and add · Collect then parse |
| 11 | 561 | [Array Partition](ArrayPartition/) [↗](https://leetcode.com/problems/array-partition/) | Array, Greedy, Sorting, Counting Sort | 82.3% | ✅ Sort · Counting sort |
| 12 | 1768 | [Merge Strings Alternately](MergeStringsAlternately/) [↗](https://leetcode.com/problems/merge-strings-alternately/) | Two Pointers, String | 82.1% | ✅ Two pointers · Single index |
| 13 | 344 | [Reverse String](ReverseString/) [↗](https://leetcode.com/problems/reverse-string/) | Two Pointers, String | 81.5% | ✅ Two pointers · Recursion |
| 14 | 206 | [Reverse Linked List](ReverseLinkedList/) [↗](https://leetcode.com/problems/reverse-linked-list/) | Linked List, Recursion | 81.2% | ✅ Iterative three pointers · Recursive · Stack |
| 15 | 94 | [Binary Tree Inorder Traversal](BinaryTreeInorderTraversal/) [↗](https://leetcode.com/problems/binary-tree-inorder-traversal/) | Stack, Tree, Depth-First Search, Binary Tree | 80.8% | ✅ Recursive · Iterative stack · Morris |
| 16 | 338 | [Counting Bits](CountingBits/) [↗](https://leetcode.com/problems/counting-bits/) | Dynamic Programming, Bit Manipulation | 80.8% | ✅ Count each number · DP with i >> 1 · DP with i & (i - 1) |
| 17 | 226 | [Invert Binary Tree](InvertBinaryTree/) [↗](https://leetcode.com/problems/invert-binary-tree/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 80.6% | ✅ Recursive DFS · BFS queue |
| 18 | 118 | [Pascal's Triangle](PascalsTriangle/) [↗](https://leetcode.com/problems/pascals-triangle/) | Array, Dynamic Programming | 79.5% | ✅ Row from previous row · Binomial coefficients |
| 19 | 145 | [Binary Tree Postorder Traversal](BinaryTreePostorderTraversal/) [↗](https://leetcode.com/problems/binary-tree-postorder-traversal/) | Stack, Tree, Depth-First Search, Binary Tree | 79.2% | ✅ Recursive · Reversed modified preorder · One stack with last-visited |
| 20 | 617 | [Merge Two Binary Trees](MergeTwoBinaryTrees/) [↗](https://leetcode.com/problems/merge-two-binary-trees/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 79.2% | ✅ Recursive in place · Recursive new tree · Iterative stack |
| 21 | 1207 | [Unique Number of Occurrences](UniqueNumberOfOccurrences/) [↗](https://leetcode.com/problems/unique-number-of-occurrences/) | Array, Hash Table | 79.0% | ✅ Hash map + set · Counting array + seen table |
| 22 | 104 | [Maximum Depth of Binary Tree](MaximumDepthOfBinaryTree/) [↗](https://leetcode.com/problems/maximum-depth-of-binary-tree/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 78.7% | ✅ Recursive DFS · BFS by levels · Iterative DFS with depth |
| 23 | 136 | [Single Number](SingleNumber/) [↗](https://leetcode.com/problems/single-number/) | Array, Bit Manipulation | 78.5% | ✅ Hash set toggle · Math (2·sum distinct − sum) · XOR |
| 24 | 349 | [Intersection of Two Arrays](IntersectionOfTwoArrays/) [↗](https://leetcode.com/problems/intersection-of-two-arrays/) | Array, Hash Table, Two Pointers, Binary Search | 78.5% | ✅ Two sets · Sort + two pointers · Sort + binary search |
| 25 | 657 | [Robot Return to Origin](RobotReturnToOrigin/) [↗](https://leetcode.com/problems/robot-return-to-origin/) | String, Simulation | 78.2% | ✅ Coordinates · Count letters |
| 26 | 191 | [Number of 1 Bits](NumberOf1Bits/) [↗](https://leetcode.com/problems/number-of-1-bits/) | Divide and Conquer, Bit Manipulation | 77.8% | ✅ Check each bit · Clear lowest set bit · Bit tricks (SWAR) |
| 27 | 867 | [Transpose Matrix](TransposeMatrix/) [↗](https://leetcode.com/problems/transpose-matrix/) | Array, Matrix, Simulation | 77.5% | ✅ New matrix · Streams |
| 28 | 461 | [Hamming Distance](HammingDistance/) [↗](https://leetcode.com/problems/hamming-distance/) | Bit Manipulation | 77.2% | ✅ XOR + bitCount · XOR + Kernighan · Compare bit by bit |
| 29 | 589 | [N-ary Tree Preorder Traversal](NAryTreePreorderTraversal/) [↗](https://leetcode.com/problems/n-ary-tree-preorder-traversal/) | Stack, Tree, Depth-First Search | 77.1% | ✅ Recursive · Iterative stack |
| 30 | 496 | [Next Greater Element I](NextGreaterElementI/) [↗](https://leetcode.com/problems/next-greater-element-i/) | Array, Hash Table, Stack, Monotonic Stack | 77.0% | ✅ Brute force · Monotonic stack + map |
| 31 | 144 | [Binary Tree Preorder Traversal](BinaryTreePreorderTraversal/) [↗](https://leetcode.com/problems/binary-tree-preorder-traversal/) | Stack, Tree, Depth-First Search, Binary Tree | 76.9% | ✅ Recursive · Iterative stack · Morris |
| 32 | 905 | [Sort Array By Parity](SortArrayByParity/) [↗](https://leetcode.com/problems/sort-array-by-parity/) | Array, Two Pointers, Sorting | 76.7% | ✅ Two passes · Two pointers in place · Stable write pointer |
| 33 | 108 | [Convert Sorted Array to Binary Search Tree](ConvertSortedArrayToBinarySearchTree/) [↗](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/) | Array, Divide and Conquer, Tree, Binary Search Tree | 76.3% | ✅ Recursive middle split · Iterative with a stack |
| 34 | 412 | [Fizz Buzz](FizzBuzz/) [↗](https://leetcode.com/problems/fizz-buzz/) | Math, String, Simulation | 76.0% | ✅ Modulo checks · String concatenation · Counters without modulo |
| 35 | 509 | [Fibonacci Number](FibonacciNumber/) [↗](https://leetcode.com/problems/fibonacci-number/) | Math, Dynamic Programming, Recursion, Memoization | 74.8% | ✅ Recursion · Memoization · Iterative · Matrix power |
| 36 | 500 | [Keyboard Row](KeyboardRow/) [↗](https://leetcode.com/problems/keyboard-row/) | Array, Hash Table, String | 74.6% | ✅ Row lookup table · Regex |
| 37 | 463 | [Island Perimeter](IslandPerimeter/) [↗](https://leetcode.com/problems/island-perimeter/) | Array, Depth-First Search, Breadth-First Search, Matrix | 74.5% | ✅ Count edges per cell · Count cells and shared edges · DFS |
| 38 | 977 | [Squares of a Sorted Array](SquaresOfASortedArray/) [↗](https://leetcode.com/problems/squares-of-a-sorted-array/) | Array, Two Pointers, Sorting | 74.2% | ✅ Square + sort · Two pointers from the ends |
| 39 | 303 | [Range Sum Query - Immutable](RangeSumQueryImmutable/) [↗](https://leetcode.com/problems/range-sum-query-immutable/) | Array, Design, Prefix Sum | 73.8% | ✅ Sum on every query · Prefix sums |
| 40 | 268 | [Missing Number](MissingNumber/) [↗](https://leetcode.com/problems/missing-number/) | Array, Hash Table, Math, Binary Search | 72.9% | ✅ Sort · Hash set · Gauss sum · XOR |
| 41 | 476 | [Number Complement](NumberComplement/) [↗](https://leetcode.com/problems/number-complement/) | Bit Manipulation | 70.6% | ✅ Flip bit by bit · Mask of ones |
| 42 | 225 | [Implement Stack using Queues](ImplementStackUsingQueues/) [↗](https://leetcode.com/problems/implement-stack-using-queues/) | Stack, Design, Queue | 70.5% | ✅ Two queues · One queue rotated on push |
| 43 | 232 | Implement Queue using Stacks [↗](https://leetcode.com/problems/implement-queue-using-stacks/) | Stack, Design, Queue | 70.3% | ⏳ |
| 44 | 1408 | [String Matching in an Array](StringMatchingInAnArray/) [↗](https://leetcode.com/problems/string-matching-in-an-array/) | Array, String, String Matching | 69.9% | ✅ All pairs · Sort by length · Joined text |
| 45 | 190 | Reverse Bits [↗](https://leetcode.com/problems/reverse-bits/) | Divide and Conquer, Bit Manipulation | 69.7% | ⏳ |
| 46 | 257 | Binary Tree Paths [↗](https://leetcode.com/problems/binary-tree-paths/) | String, Backtracking, Tree, Depth-First Search | 69.6% | ⏳ |
| 47 | 258 | Add Digits [↗](https://leetcode.com/problems/add-digits/) | Math, Simulation, Number Theory | 69.4% | ⏳ |
| 48 | 21 | [Merge Two Sorted Lists](MergeTwoSortedLists/) [↗](https://leetcode.com/problems/merge-two-sorted-lists/) | Linked List, Recursion | 69.0% | ✅ Iterative with dummy head · Recursive |
| 49 | 746 | Min Cost Climbing Stairs [↗](https://leetcode.com/problems/min-cost-climbing-stairs/) | Array, Dynamic Programming | 68.9% | ⏳ |
| 50 | 242 | Valid Anagram [↗](https://leetcode.com/problems/valid-anagram/) | Hash Table, String, Sorting | 68.8% | ⏳ |
| 51 | 171 | Excel Sheet Column Number [↗](https://leetcode.com/problems/excel-sheet-column-number/) | Math, String | 68.6% | ⏳ |
| 52 | 705 | Design HashSet [↗](https://leetcode.com/problems/design-hashset/) | Array, Hash Table, Linked List, Design | 68.5% | ⏳ |
| 53 | 100 | Same Tree [↗](https://leetcode.com/problems/same-tree/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 68.2% | ⏳ |
| 54 | 119 | Pascal's Triangle II [↗](https://leetcode.com/problems/pascals-triangle-ii/) | Array, Dynamic Programming | 68.2% | ⏳ |
| 55 | 13 | [Roman to Integer](RomanToInteger/) [↗](https://leetcode.com/problems/roman-to-integer/) | Hash Table, Math, String | 67.3% | ✅ Look ahead · Right to left · Pair table |
| 56 | 1046 | Last Stone Weight [↗](https://leetcode.com/problems/last-stone-weight/) | Array, Heap (Priority Queue) | 66.9% | ⏳ |
| 57 | 706 | Design HashMap [↗](https://leetcode.com/problems/design-hashmap/) | Array, Hash Table, Linked List, Design | 66.8% | ⏳ |
| 58 | 383 | [Ransom Note](RansomNote/) [↗](https://leetcode.com/problems/ransom-note/) | Hash Table, String, Counting | 66.6% | ✅ Remove from copy · Hash map count · Array of 26 |
| 59 | 387 | First Unique Character in a String [↗](https://leetcode.com/problems/first-unique-character-in-a-string/) | Hash Table, String, Queue, Counting | 66.6% | ⏳ |
| 60 | 169 | Majority Element [↗](https://leetcode.com/problems/majority-element/) | Array, Hash Table, Divide and Conquer, Sorting | 66.5% | ⏳ |
| 61 | 485 | Max Consecutive Ones [↗](https://leetcode.com/problems/max-consecutive-ones/) | Array | 66.4% | ⏳ |
| 62 | 543 | Diameter of Binary Tree [↗](https://leetcode.com/problems/diameter-of-binary-tree/) | Tree, Depth-First Search, Binary Tree, DP on Trees | 66.4% | ⏳ |
| 63 | 160 | Intersection of Two Linked Lists [↗](https://leetcode.com/problems/intersection-of-two-linked-lists/) | Hash Table, Linked List, Two Pointers | 64.9% | ⏳ |
| 64 | 217 | [Contains Duplicate](ContainsDuplicate/) [↗](https://leetcode.com/problems/contains-duplicate/) | Array, Hash Table, Sorting | 64.8% | ✅ Brute force · Sort · Hash set |
| 65 | 448 | Find All Numbers Disappeared in an Array [↗](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/) | Array, Hash Table | 64.7% | ⏳ |
| 66 | 283 | Move Zeroes [↗](https://leetcode.com/problems/move-zeroes/) | Array, Two Pointers | 64.5% | ⏳ |
| 67 | 724 | Find Pivot Index [↗](https://leetcode.com/problems/find-pivot-index/) | Array, Prefix Sum | 64.2% | ⏳ |
| 68 | 26 | Remove Duplicates from Sorted Array [↗](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) | Array, Two Pointers | 64.0% | ⏳ |
| 69 | 404 | Sum of Left Leaves [↗](https://leetcode.com/problems/sum-of-left-leaves/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 63.3% | ⏳ |
| 70 | 896 | Monotonic Array [↗](https://leetcode.com/problems/monotonic-array/) | Array | 62.8% | ⏳ |
| 71 | 27 | [Remove Element](RemoveElement/) [↗](https://leetcode.com/problems/remove-element/) | Array, Two Pointers | 62.7% | ✅ Read/write pointers · Swap with last |
| 72 | 345 | Reverse Vowels of a String [↗](https://leetcode.com/problems/reverse-vowels-of-a-string/) | Two Pointers, String | 62.7% | ⏳ |
| 73 | 101 | Symmetric Tree [↗](https://leetcode.com/problems/symmetric-tree/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 62.2% | ⏳ |
| 74 | 703 | Kth Largest Element in a Stream [↗](https://leetcode.com/problems/kth-largest-element-in-a-stream/) | Tree, Design, Binary Search Tree, Heap (Priority Queue) | 61.7% | ⏳ |
| 75 | 704 | Binary Search [↗](https://leetcode.com/problems/binary-search/) | Array, Binary Search | 61.4% | ⏳ |
| 76 | 9 | [Palindrome Number](PalindromeNumber/) [↗](https://leetcode.com/problems/palindrome-number/) | Math | 61.1% | ✅ String reverse · Full number reverse · Half reverse |
| 77 | 389 | Find the Difference [↗](https://leetcode.com/problems/find-the-difference/) | Hash Table, String, Bit Manipulation, Sorting | 60.8% | ⏳ |
| 78 | 292 | Nim Game [↗](https://leetcode.com/problems/nim-game/) | Math, Brainteaser, Minimax, Game Theory | 60.7% | ⏳ |
| 79 | 202 | [Happy Number](HappyNumber/) [↗](https://leetcode.com/problems/happy-number/) | Hash Table, Math, Two Pointers, Floyd's Cycle Finding Algorithm | 60.5% | ✅ Hash set · Floyd cycle detection · Known cycle |
| 80 | 350 | Intersection of Two Arrays II [↗](https://leetcode.com/problems/intersection-of-two-arrays-ii/) | Array, Hash Table, Two Pointers, Binary Search | 60.5% | ⏳ |
| 81 | 58 | [Length of Last Word](LengthOfLastWord/) [↗](https://leetcode.com/problems/length-of-last-word/) | String | 60.0% | ✅ Split · Trim + lastIndexOf · Scan from the end |
| 82 | 530 | Minimum Absolute Difference in BST [↗](https://leetcode.com/problems/minimum-absolute-difference-in-bst/) | Tree, Depth-First Search, Breadth-First Search, Binary Search Tree | 59.8% | ⏳ |
| 83 | 110 | Balanced Binary Tree [↗](https://leetcode.com/problems/balanced-binary-tree/) | Tree, Depth-First Search, Binary Tree | 59.4% | ⏳ |
| 84 | 234 | Palindrome Linked List [↗](https://leetcode.com/problems/palindrome-linked-list/) | Linked List, Two Pointers, Stack, Recursion | 59.0% | ⏳ |
| 85 | 67 | Add Binary [↗](https://leetcode.com/problems/add-binary/) | Math, String, Bit Manipulation, Simulation | 58.8% | ⏳ |
| 86 | 374 | Guess Number Higher or Lower [↗](https://leetcode.com/problems/guess-number-higher-or-lower/) | Binary Search, Interactive | 58.6% | ⏳ |
| 87 | 1 | [Two Sum](TwoSum/) [↗](https://leetcode.com/problems/two-sum/) | Array, Hash Table | 58.1% | ✅ Brute force · Hash map · Sort + two pointers |
| 88 | 83 | [Remove Duplicates from Sorted List](RemoveDuplicatesFromSortedList/) [↗](https://leetcode.com/problems/remove-duplicates-from-sorted-list/) | Linked List | 57.8% | ✅ Skip duplicates in place · Recursive |
| 89 | 121 | [Best Time to Buy and Sell Stock](BestTimeToBuyAndSellStock/) [↗](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) | Array, Dynamic Programming | 57.5% | ✅ Brute force · One pass · State-machine DP |
| 90 | 520 | Detect Capital [↗](https://leetcode.com/problems/detect-capital/) | String | 57.2% | ⏳ |
| 91 | 409 | Longest Palindrome [↗](https://leetcode.com/problems/longest-palindrome/) | Hash Table, String, Greedy | 56.3% | ⏳ |
| 92 | 112 | Path Sum [↗](https://leetcode.com/problems/path-sum/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 56.0% | ⏳ |
| 93 | 88 | Merge Sorted Array [↗](https://leetcode.com/problems/merge-sorted-array/) | Array, Two Pointers, Sorting | 55.9% | ⏳ |
| 94 | 203 | Remove Linked List Elements [↗](https://leetcode.com/problems/remove-linked-list-elements/) | Linked List, Recursion | 55.8% | ⏳ |
| 95 | 455 | Assign Cookies [↗](https://leetcode.com/problems/assign-cookies/) | Array, Two Pointers, Greedy, Sorting | 55.6% | ⏳ |
| 96 | 141 | Linked List Cycle [↗](https://leetcode.com/problems/linked-list-cycle/) | Hash Table, Linked List, Two Pointers, Floyd's Cycle Finding Algorithm | 55.3% | ⏳ |
| 97 | 405 | Convert a Number to Hexadecimal [↗](https://leetcode.com/problems/convert-a-number-to-hexadecimal/) | Math, String, Bit Manipulation | 55.2% | ⏳ |
| 98 | 228 | Summary Ranges [↗](https://leetcode.com/problems/summary-ranges/) | Array | 54.9% | ⏳ |
| 99 | 125 | [Valid Palindrome](ValidPalindrome/) [↗](https://leetcode.com/problems/valid-palindrome/) | Two Pointers, String | 54.6% | ✅ Clean + reverse · Two pointers |
| 100 | 70 | [Climbing Stairs](ClimbingStairs/) [↗](https://leetcode.com/problems/climbing-stairs/) | Math, Dynamic Programming, Memoization | 54.4% | ✅ Recursion · Memoization · DP table · Two variables |
| 101 | 111 | Minimum Depth of Binary Tree [↗](https://leetcode.com/problems/minimum-depth-of-binary-tree/) | Tree, Depth-First Search, Breadth-First Search, Binary Tree | 54.0% | ⏳ |
| 102 | 35 | [Search Insert Position](SearchInsertPosition/) [↗](https://leetcode.com/problems/search-insert-position/) | Array, Binary Search | 52.5% | ✅ Linear scan · Binary search (lower bound) · Arrays.binarySearch |
| 103 | 572 | Subtree of Another Tree [↗](https://leetcode.com/problems/subtree-of-another-tree/) | Tree, Depth-First Search, String Matching, Binary Tree | 52.4% | ⏳ |
| 104 | 415 | Add Strings [↗](https://leetcode.com/problems/add-strings/) | Math, String, Simulation | 52.3% | ⏳ |
| 105 | 219 | [Contains Duplicate II](ContainsDuplicateII/) [↗](https://leetcode.com/problems/contains-duplicate-ii/) | Array, Hash Table, Sliding Window | 52.1% | ✅ Brute force · Last index map · Sliding window set |
| 106 | 326 | Power of Three [↗](https://leetcode.com/problems/power-of-three/) | Math, Recursion | 51.8% | ⏳ |
| 107 | 66 | [Plus One](PlusOne/) [↗](https://leetcode.com/problems/plus-one/) | Array, Math | 50.8% | ✅ Stop at first non-9 · General carry · BigInteger |
| 108 | 231 | Power of Two [↗](https://leetcode.com/problems/power-of-two/) | Math, Bit Manipulation, Recursion | 50.7% | ⏳ |
| 109 | 844 | Backspace String Compare [↗](https://leetcode.com/problems/backspace-string-compare/) | Two Pointers, String, Stack, Simulation | 50.3% | ⏳ |
| 110 | 392 | Is Subsequence [↗](https://leetcode.com/problems/is-subsequence/) | Two Pointers, String, Dynamic Programming | 49.5% | ⏳ |
| 111 | 643 | Maximum Average Subarray I [↗](https://leetcode.com/problems/maximum-average-subarray-i/) | Array, Sliding Window | 49.5% | ⏳ |
| 112 | 205 | Isomorphic Strings [↗](https://leetcode.com/problems/isomorphic-strings/) | Hash Table, String | 49.3% | ⏳ |
| 113 | 459 | Repeated Substring Pattern [↗](https://leetcode.com/problems/repeated-substring-pattern/) | String, String Matching, Z Algorithm, Knuth–Morris–Pratt Algorithm | 49.0% | ⏳ |
| 114 | 14 | [Longest Common Prefix](LongestCommonPrefix/) [↗](https://leetcode.com/problems/longest-common-prefix/) | Array, String, Trie | 48.6% | ✅ Vertical scan · Horizontal shrink · Sort + compare ends |
| 115 | 628 | Maximum Product of Three Numbers [↗](https://leetcode.com/problems/maximum-product-of-three-numbers/) | Array, Math, Sorting | 48.3% | ⏳ |
| 116 | 168 | Excel Sheet Column Title [↗](https://leetcode.com/problems/excel-sheet-column-title/) | Math, String | 47.9% | ⏳ |
| 117 | 278 | First Bad Version [↗](https://leetcode.com/problems/first-bad-version/) | Binary Search, Interactive | 47.8% | ⏳ |
| 118 | 28 | Find the Index of the First Occurrence in a String [↗](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/) | Two Pointers, String, String Matching, Z Algorithm | 47.5% | ⏳ |
| 119 | 20 | [Valid Parentheses](ValidParentheses/) [↗](https://leetcode.com/problems/valid-parentheses/) | String, Stack, Bracket Sequences | 45.4% | ✅ Stack · Expected-closer stack · Repeated removal |
| 120 | 367 | Valid Perfect Square [↗](https://leetcode.com/problems/valid-perfect-square/) | Math, Binary Search | 45.1% | ⏳ |
| 121 | 680 | Valid Palindrome II [↗](https://leetcode.com/problems/valid-palindrome-ii/) | Two Pointers, String, Greedy | 45.0% | ⏳ |
| 122 | 290 | Word Pattern [↗](https://leetcode.com/problems/word-pattern/) | Hash Table, String | 44.8% | ⏳ |
| 123 | 263 | Ugly Number [↗](https://leetcode.com/problems/ugly-number/) | Math | 44.4% | ⏳ |
| 124 | 645 | Set Mismatch [↗](https://leetcode.com/problems/set-mismatch/) | Array, Hash Table, Bit Manipulation, Sorting | 43.2% | ⏳ |
| 125 | 69 | [Sqrt(x)](SqrtX/) [↗](https://leetcode.com/problems/sqrtx/) | Math, Binary Search, Newton's Method | 42.4% | ✅ Linear · Binary search · Newton's method |
| 126 | 414 | Third Maximum Number [↗](https://leetcode.com/problems/third-maximum-number/) | Array, Sorting | 40.7% | ⏳ |
| 127 | 434 | Number of Segments in a String [↗](https://leetcode.com/problems/number-of-segments-in-a-string/) | String | 38.0% | ⏳ |

## By topic

- **Array** (26): [1512. Number of Good Pairs](NumberOfGoodPairs/) · [1672. Richest Customer Wealth](RichestCustomerWealth/) · [1480. Running Sum of 1d Array](RunningSumOf1dArray/) · [561. Array Partition](ArrayPartition/) · [118. Pascal's Triangle](PascalsTriangle/) · [1207. Unique Number of Occurrences](UniqueNumberOfOccurrences/) · [136. Single Number](SingleNumber/) · [349. Intersection of Two Arrays](IntersectionOfTwoArrays/) · [867. Transpose Matrix](TransposeMatrix/) · [496. Next Greater Element I](NextGreaterElementI/) · [905. Sort Array By Parity](SortArrayByParity/) · [108. Convert Sorted Array to Binary Search Tree](ConvertSortedArrayToBinarySearchTree/) · [500. Keyboard Row](KeyboardRow/) · [463. Island Perimeter](IslandPerimeter/) · [977. Squares of a Sorted Array](SquaresOfASortedArray/) · [303. Range Sum Query - Immutable](RangeSumQueryImmutable/) · [268. Missing Number](MissingNumber/) · [1408. String Matching in an Array](StringMatchingInAnArray/) · [217. Contains Duplicate](ContainsDuplicate/) · [27. Remove Element](RemoveElement/) · [1. Two Sum](TwoSum/) · [121. Best Time to Buy and Sell Stock](BestTimeToBuyAndSellStock/) · [35. Search Insert Position](SearchInsertPosition/) · [219. Contains Duplicate II](ContainsDuplicateII/) · [66. Plus One](PlusOne/) · [14. Longest Common Prefix](LongestCommonPrefix/)
- **Binary Search** (2): [35. Search Insert Position](SearchInsertPosition/) · [69. Sqrt(x)](SqrtX/)
- **Binary Search Tree** (2): [938. Range Sum of BST](RangeSumOfBST/) · [700. Search in a Binary Search Tree](SearchInABinarySearchTree/)
- **Binary Tree** (1): [700. Search in a Binary Search Tree](SearchInABinarySearchTree/)
- **Bit Manipulation** (5): [338. Counting Bits](CountingBits/) · [136. Single Number](SingleNumber/) · [191. Number of 1 Bits](NumberOf1Bits/) · [461. Hamming Distance](HammingDistance/) · [476. Number Complement](NumberComplement/)
- **Bracket Sequences** (1): [20. Valid Parentheses](ValidParentheses/)
- **Breadth-First Search** (4): [226. Invert Binary Tree](InvertBinaryTree/) · [617. Merge Two Binary Trees](MergeTwoBinaryTrees/) · [104. Maximum Depth of Binary Tree](MaximumDepthOfBinaryTree/) · [463. Island Perimeter](IslandPerimeter/)
- **Counting** (1): [383. Ransom Note](RansomNote/)
- **Depth-First Search** (9): [938. Range Sum of BST](RangeSumOfBST/) · [94. Binary Tree Inorder Traversal](BinaryTreeInorderTraversal/) · [226. Invert Binary Tree](InvertBinaryTree/) · [145. Binary Tree Postorder Traversal](BinaryTreePostorderTraversal/) · [617. Merge Two Binary Trees](MergeTwoBinaryTrees/) · [104. Maximum Depth of Binary Tree](MaximumDepthOfBinaryTree/) · [589. N-ary Tree Preorder Traversal](NAryTreePreorderTraversal/) · [144. Binary Tree Preorder Traversal](BinaryTreePreorderTraversal/) · [463. Island Perimeter](IslandPerimeter/)
- **Design** (2): [303. Range Sum Query - Immutable](RangeSumQueryImmutable/) · [225. Implement Stack using Queues](ImplementStackUsingQueues/)
- **Divide and Conquer** (2): [191. Number of 1 Bits](NumberOf1Bits/) · [108. Convert Sorted Array to Binary Search Tree](ConvertSortedArrayToBinarySearchTree/)
- **Dynamic Programming** (5): [338. Counting Bits](CountingBits/) · [118. Pascal's Triangle](PascalsTriangle/) · [509. Fibonacci Number](FibonacciNumber/) · [121. Best Time to Buy and Sell Stock](BestTimeToBuyAndSellStock/) · [70. Climbing Stairs](ClimbingStairs/)
- **Greedy** (1): [561. Array Partition](ArrayPartition/)
- **Hash Table** (13): [1512. Number of Good Pairs](NumberOfGoodPairs/) · [771. Jewels and Stones](JewelsAndStones/) · [1207. Unique Number of Occurrences](UniqueNumberOfOccurrences/) · [349. Intersection of Two Arrays](IntersectionOfTwoArrays/) · [496. Next Greater Element I](NextGreaterElementI/) · [500. Keyboard Row](KeyboardRow/) · [268. Missing Number](MissingNumber/) · [13. Roman to Integer](RomanToInteger/) · [383. Ransom Note](RansomNote/) · [217. Contains Duplicate](ContainsDuplicate/) · [202. Happy Number](HappyNumber/) · [1. Two Sum](TwoSum/) · [219. Contains Duplicate II](ContainsDuplicateII/)
- **Linked List** (5): [876. Middle of the Linked List](MiddleOfTheLinkedList/) · [1290. Convert Binary Number in a Linked List to Integer](ConvertBinaryNumberInALinkedListToInteger/) · [206. Reverse Linked List](ReverseLinkedList/) · [21. Merge Two Sorted Lists](MergeTwoSortedLists/) · [83. Remove Duplicates from Sorted List](RemoveDuplicatesFromSortedList/)
- **Math** (11): [1512. Number of Good Pairs](NumberOfGoodPairs/) · [1290. Convert Binary Number in a Linked List to Integer](ConvertBinaryNumberInALinkedListToInteger/) · [412. Fizz Buzz](FizzBuzz/) · [509. Fibonacci Number](FibonacciNumber/) · [268. Missing Number](MissingNumber/) · [13. Roman to Integer](RomanToInteger/) · [9. Palindrome Number](PalindromeNumber/) · [202. Happy Number](HappyNumber/) · [70. Climbing Stairs](ClimbingStairs/) · [66. Plus One](PlusOne/) · [69. Sqrt(x)](SqrtX/)
- **Matrix** (2): [1672. Richest Customer Wealth](RichestCustomerWealth/) · [867. Transpose Matrix](TransposeMatrix/)
- **Memoization** (1): [70. Climbing Stairs](ClimbingStairs/)
- **Newton's Method** (1): [69. Sqrt(x)](SqrtX/)
- **Prefix Sum** (2): [1480. Running Sum of 1d Array](RunningSumOf1dArray/) · [303. Range Sum Query - Immutable](RangeSumQueryImmutable/)
- **Queue** (1): [225. Implement Stack using Queues](ImplementStackUsingQueues/)
- **Recursion** (3): [206. Reverse Linked List](ReverseLinkedList/) · [509. Fibonacci Number](FibonacciNumber/) · [21. Merge Two Sorted Lists](MergeTwoSortedLists/)
- **Simulation** (3): [657. Robot Return to Origin](RobotReturnToOrigin/) · [867. Transpose Matrix](TransposeMatrix/) · [412. Fizz Buzz](FizzBuzz/)
- **Sliding Window** (1): [219. Contains Duplicate II](ContainsDuplicateII/)
- **Sorting** (4): [561. Array Partition](ArrayPartition/) · [905. Sort Array By Parity](SortArrayByParity/) · [977. Squares of a Sorted Array](SquaresOfASortedArray/) · [217. Contains Duplicate](ContainsDuplicate/)
- **Stack** (7): [94. Binary Tree Inorder Traversal](BinaryTreeInorderTraversal/) · [145. Binary Tree Postorder Traversal](BinaryTreePostorderTraversal/) · [589. N-ary Tree Preorder Traversal](NAryTreePreorderTraversal/) · [496. Next Greater Element I](NextGreaterElementI/) · [144. Binary Tree Preorder Traversal](BinaryTreePreorderTraversal/) · [225. Implement Stack using Queues](ImplementStackUsingQueues/) · [20. Valid Parentheses](ValidParentheses/)
- **String** (15): [1108. Defanging an IP Address](DefangingAnIPAddress/) · [771. Jewels and Stones](JewelsAndStones/) · [557. Reverse Words in a String III](ReverseWordsInAStringIII/) · [1768. Merge Strings Alternately](MergeStringsAlternately/) · [344. Reverse String](ReverseString/) · [657. Robot Return to Origin](RobotReturnToOrigin/) · [412. Fizz Buzz](FizzBuzz/) · [500. Keyboard Row](KeyboardRow/) · [1408. String Matching in an Array](StringMatchingInAnArray/) · [13. Roman to Integer](RomanToInteger/) · [383. Ransom Note](RansomNote/) · [58. Length of Last Word](LengthOfLastWord/) · [125. Valid Palindrome](ValidPalindrome/) · [14. Longest Common Prefix](LongestCommonPrefix/) · [20. Valid Parentheses](ValidParentheses/)
- **String Matching** (1): [1408. String Matching in an Array](StringMatchingInAnArray/)
- **Tree** (10): [938. Range Sum of BST](RangeSumOfBST/) · [700. Search in a Binary Search Tree](SearchInABinarySearchTree/) · [94. Binary Tree Inorder Traversal](BinaryTreeInorderTraversal/) · [226. Invert Binary Tree](InvertBinaryTree/) · [145. Binary Tree Postorder Traversal](BinaryTreePostorderTraversal/) · [617. Merge Two Binary Trees](MergeTwoBinaryTrees/) · [104. Maximum Depth of Binary Tree](MaximumDepthOfBinaryTree/) · [589. N-ary Tree Preorder Traversal](NAryTreePreorderTraversal/) · [144. Binary Tree Preorder Traversal](BinaryTreePreorderTraversal/) · [108. Convert Sorted Array to Binary Search Tree](ConvertSortedArrayToBinarySearchTree/)
- **Trie** (1): [14. Longest Common Prefix](LongestCommonPrefix/)
- **Two Pointers** (10): [557. Reverse Words in a String III](ReverseWordsInAStringIII/) · [876. Middle of the Linked List](MiddleOfTheLinkedList/) · [1768. Merge Strings Alternately](MergeStringsAlternately/) · [344. Reverse String](ReverseString/) · [349. Intersection of Two Arrays](IntersectionOfTwoArrays/) · [905. Sort Array By Parity](SortArrayByParity/) · [977. Squares of a Sorted Array](SquaresOfASortedArray/) · [27. Remove Element](RemoveElement/) · [202. Happy Number](HappyNumber/) · [125. Valid Palindrome](ValidPalindrome/)
