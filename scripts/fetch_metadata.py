#!/usr/bin/env python3
"""Fetches official metadata (title, difficulty, topics, acceptance rate) for the curated interview
problem list from LeetCode, drops paid-only problems, and writes scripts/problems.json.

The list is the union of the classic interview sets (Blind 75, NeetCode 150, LeetCode Top Interview)
plus problems already solved in this repo. Run again after editing CANDIDATES:
    python3 scripts/fetch_metadata.py
"""
import json
import time
import urllib.request
from pathlib import Path

CANDIDATES = """
two-sum palindrome-number roman-to-integer longest-common-prefix valid-parentheses merge-two-sorted-lists
remove-duplicates-from-sorted-array remove-element find-the-index-of-the-first-occurrence-in-a-string search-insert-position
length-of-last-word plus-one add-binary sqrtx climbing-stairs remove-duplicates-from-sorted-list merge-sorted-array
binary-tree-inorder-traversal same-tree symmetric-tree maximum-depth-of-binary-tree convert-sorted-array-to-binary-search-tree
balanced-binary-tree minimum-depth-of-binary-tree path-sum pascals-triangle pascals-triangle-ii best-time-to-buy-and-sell-stock
valid-palindrome single-number linked-list-cycle binary-tree-preorder-traversal binary-tree-postorder-traversal
intersection-of-two-linked-lists majority-element excel-sheet-column-title excel-sheet-column-number reverse-bits
number-of-1-bits happy-number remove-linked-list-elements isomorphic-strings reverse-linked-list contains-duplicate
contains-duplicate-ii count-complete-tree-nodes implement-stack-using-queues invert-binary-tree summary-ranges
implement-queue-using-stacks palindrome-linked-list valid-anagram binary-tree-paths add-digits ugly-number missing-number
first-bad-version move-zeroes word-pattern nim-game range-sum-query-immutable power-of-two power-of-three counting-bits
reverse-string reverse-vowels-of-a-string intersection-of-two-arrays intersection-of-two-arrays-ii valid-perfect-square
guess-number-higher-or-lower ransom-note first-unique-character-in-a-string find-the-difference is-subsequence
sum-of-left-leaves convert-a-number-to-hexadecimal longest-palindrome fizz-buzz third-maximum-number add-strings
number-of-segments-in-a-string find-all-numbers-disappeared-in-an-array assign-cookies repeated-substring-pattern
hamming-distance island-perimeter number-complement max-consecutive-ones next-greater-element-i keyboard-row
fibonacci-number detect-capital minimum-absolute-difference-in-bst diameter-of-binary-tree reverse-words-in-a-string-iii
array-partition subtree-of-another-tree n-ary-tree-preorder-traversal merge-two-binary-trees maximum-product-of-three-numbers
maximum-average-subarray-i set-mismatch robot-return-to-origin valid-palindrome-ii search-in-a-binary-search-tree
kth-largest-element-in-a-stream binary-search design-hashset design-hashmap find-pivot-index min-cost-climbing-stairs
jewels-and-stones backspace-string-compare transpose-matrix middle-of-the-linked-list monotonic-array sort-array-by-parity
range-sum-of-bst squares-of-a-sorted-array last-stone-weight defanging-an-ip-address unique-number-of-occurrences
convert-binary-number-in-a-linked-list-to-integer running-sum-of-1d-array number-of-good-pairs richest-customer-wealth
merge-strings-alternately string-matching-in-an-array lowest-common-ancestor-of-a-binary-search-tree
add-two-numbers longest-substring-without-repeating-characters longest-palindromic-substring zigzag-conversion reverse-integer
string-to-integer-atoi container-with-most-water integer-to-roman 3sum 3sum-closest letter-combinations-of-a-phone-number 4sum
remove-nth-node-from-end-of-list generate-parentheses swap-nodes-in-pairs divide-two-integers next-permutation
search-in-rotated-sorted-array find-first-and-last-position-of-element-in-sorted-array valid-sudoku count-and-say
combination-sum combination-sum-ii multiply-strings jump-game-ii permutations permutations-ii rotate-image group-anagrams
powx-n maximum-subarray spiral-matrix jump-game merge-intervals insert-interval spiral-matrix-ii rotate-list unique-paths
unique-paths-ii minimum-path-sum simplify-path edit-distance set-matrix-zeroes search-a-2d-matrix sort-colors combinations subsets
word-search remove-duplicates-from-sorted-array-ii remove-duplicates-from-sorted-list-ii partition-list subsets-ii decode-ways
reverse-linked-list-ii restore-ip-addresses unique-binary-search-trees interleaving-string validate-binary-search-tree
recover-binary-search-tree binary-tree-level-order-traversal binary-tree-zigzag-level-order-traversal
construct-binary-tree-from-preorder-and-inorder-traversal path-sum-ii flatten-binary-tree-to-linked-list
populating-next-right-pointers-in-each-node triangle best-time-to-buy-and-sell-stock-ii longest-consecutive-sequence
sum-root-to-leaf-numbers surrounded-regions palindrome-partitioning clone-graph gas-station single-number-ii
copy-list-with-random-pointer word-break linked-list-cycle-ii reorder-list lru-cache sort-list evaluate-reverse-polish-notation
reverse-words-in-a-string maximum-product-subarray find-minimum-in-rotated-sorted-array min-stack find-peak-element
two-sum-ii-input-array-is-sorted binary-search-tree-iterator largest-number rotate-array house-robber binary-tree-right-side-view
number-of-islands course-schedule implement-trie-prefix-tree minimum-size-subarray-sum course-schedule-ii
design-add-and-search-words-data-structure house-robber-ii kth-largest-element-in-an-array maximal-square
kth-smallest-element-in-a-bst lowest-common-ancestor-of-a-binary-tree product-of-array-except-self search-a-2d-matrix-ii
perfect-squares find-the-duplicate-number game-of-life longest-increasing-subsequence coin-change odd-even-linked-list
house-robber-iii top-k-frequent-elements decode-string partition-equal-subset-sum pacific-atlantic-water-flow
longest-repeating-character-replacement non-overlapping-intervals find-all-anagrams-in-a-string target-sum
longest-palindromic-subsequence coin-change-ii subarray-sum-equals-k permutation-in-string task-scheduler palindromic-substrings
max-area-of-island daily-temperatures network-delay-time partition-labels koko-eating-bananas rotting-oranges
longest-common-subsequence k-closest-points-to-origin time-based-key-value-store best-time-to-buy-and-sell-stock-with-cooldown
car-fleet asteroid-collision accounts-merge redundant-connection
median-of-two-sorted-arrays regular-expression-matching merge-k-sorted-lists reverse-nodes-in-k-group
substring-with-concatenation-of-all-words longest-valid-parentheses sudoku-solver first-missing-positive trapping-rain-water
wildcard-matching n-queens n-queens-ii permutation-sequence valid-number text-justification minimum-window-substring
largest-rectangle-in-histogram maximal-rectangle scramble-string distinct-subsequences best-time-to-buy-and-sell-stock-iii
binary-tree-maximum-path-sum word-ladder-ii word-ladder palindrome-partitioning-ii candy word-break-ii max-points-on-a-line
find-minimum-in-rotated-sorted-array-ii dungeon-game best-time-to-buy-and-sell-stock-iv word-search-ii shortest-palindrome
the-skyline-problem basic-calculator number-of-digit-one sliding-window-maximum integer-to-english-words
expression-add-operators find-median-from-data-stream serialize-and-deserialize-binary-tree remove-invalid-parentheses
burst-balloons count-of-smaller-numbers-after-self create-maximum-number count-of-range-sum
longest-increasing-path-in-a-matrix patching-array reconstruct-itinerary palindrome-pairs data-stream-as-disjoint-intervals
russian-doll-envelopes insert-delete-getrandom-o1-duplicates-allowed frog-jump trapping-rain-water-ii
split-array-largest-sum all-oone-data-structure lfu-cache concatenated-words sliding-window-median reverse-pairs ipo
freedom-trail remove-boxes find-the-closest-palindrome erect-the-fence course-schedule-iii
smallest-range-covering-elements-from-k-lists kth-smallest-number-in-multiplication-table 24-game redundant-connection-ii
stickers-to-spell-word range-module number-of-atoms prefix-and-suffix-search cracking-the-safe couples-holding-hands
max-chunks-to-make-sorted-ii sliding-puzzle swim-in-rising-water reaching-points bricks-falling-when-hit bus-routes
making-a-large-island consecutive-numbers-sum shortest-path-visiting-all-nodes rectangle-area-ii
shortest-subarray-with-sum-at-least-k minimum-number-of-refueling-stops super-egg-drop largest-component-size-by-common-factor
binary-tree-cameras odd-even-jump unique-paths-iii subarrays-with-k-different-integers minimum-number-of-k-consecutive-bit-flips
recover-a-tree-from-preorder-traversal stream-of-characters shortest-common-supersequence find-in-mountain-array
maximum-profit-in-job-scheduling minimum-insertion-steps-to-make-a-string-palindrome minimum-difficulty-of-a-job-schedule
jump-game-iv stone-game-iii max-value-of-equation minimum-cost-to-cut-a-stick
remove-max-number-of-edges-to-keep-graph-fully-traversable minimum-number-of-operations-to-make-array-continuous
parallel-courses-iii meeting-rooms-iii median-of-a-row-wise-sorted-matrix minimum-cost-to-hire-k-workers
k-inverse-pairs-array number-of-submatrices-that-sum-to-target minimum-window-subsequence count-vowels-permutation
""".split()

QUERY = "query q($s: String!){ question(titleSlug:$s){ questionFrontendId title titleSlug difficulty acRate topicTags{ name } isPaidOnly } }"


def fetch(slug):
    body = json.dumps({"query": QUERY, "variables": {"s": slug}}).encode()
    req = urllib.request.Request("https://leetcode.com/graphql", data=body,
                                 headers={"Content-Type": "application/json", "Referer": "https://leetcode.com",
                                          "User-Agent": "Mozilla/5.0"})
    with urllib.request.urlopen(req, timeout=20) as r:
        return json.load(r)["data"]["question"]


def main():
    out, skipped = [], []
    for slug in dict.fromkeys(CANDIDATES):
        q = None
        for attempt in range(3):
            try:
                q = fetch(slug)
                break
            except Exception:
                time.sleep(2 * (attempt + 1))
        if not q or q["isPaidOnly"]:
            skipped.append(slug)
            continue
        out.append({"id": int(q["questionFrontendId"]), "slug": q["titleSlug"], "title": q["title"], "level": q["difficulty"],
                    "acRate": round(q["acRate"], 1), "topics": [t["name"] for t in q["topicTags"]]})
        time.sleep(0.15)
    out.sort(key=lambda p: ({"Easy": 0, "Medium": 1, "Hard": 2}[p["level"]], -p["acRate"]))
    Path(__file__).with_name("problems.json").write_text(json.dumps(out, indent=1, ensure_ascii=False) + "\n")
    for lvl in ("Easy", "Medium", "Hard"):
        print(lvl, sum(p["level"] == lvl for p in out))
    print("skipped (paid or not found):", skipped)


if __name__ == "__main__":
    main()
