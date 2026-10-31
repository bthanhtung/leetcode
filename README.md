# LeetCode — interview problems, explained

Interview-focused LeetCode problems, ordered from easiest to hardest inside each level. Every solved problem has:

- **several approaches** in Java, from brute force to optimal, each with its time and space complexity;
- **a detailed explanation** (Vietnamese) with Mermaid diagrams, hand-traced tables, common pitfalls and follow-up questions;
- **tests** — the official examples, edge cases, and every approach cross-checked on random inputs.

## Progress

<!-- progress:start -->
| Level | Solved | Planned | Progress |
|---|---|---|---|
| [Easy](easy/) | 127 | 127 | `██████████` |
| [Medium](medium/) | 142 | 142 | `██████████` |
| [Hard](hard/) | 115 | 115 | `██████████` |
<!-- progress:end -->

The problem list is the union of the classic interview sets (Blind 75, NeetCode 150, LeetCode Top Interview 150)
plus common follow-ups, free problems only. Open a level to see every problem with its status:
[easy/](easy/) · [medium/](medium/) · [hard/](hard/).

## Layout

```
easy/ medium/ hard/
└── TwoSum/
    ├── README.md        statement, idea, diagrams, every approach explained, pitfalls, related problems
    ├── TwoSum.java      one method per approach, main() to try it
    └── TwoSumTest.java  JUnit 5 tests
src/main/java/com/tungdadev/common/   ListNode, TreeNode shared by linked-list and tree problems
scripts/                              problem list (problems.json) and the index generator
```

## Run

Needs Java 21.

```bash
./gradlew test                          # every problem
./gradlew test --tests "TwoSum.*"       # one problem
```

Each problem lives in its own package named after its folder, so a single file can also be pasted into LeetCode
after removing the `package` line.

## Adding a problem

1. Create `level/ProblemName/` with the three files (copy any solved problem as a template). The folder name is the
   LeetCode title in PascalCase, e.g. `Best Time to Buy and Sell Stock` → `BestTimeToBuyAndSellStock`.
2. Start `README.md` with `<!-- approaches: A · B · C -->` so the index lists the approaches.
3. Run `python3 scripts/build_index.py` to refresh the level indexes and the progress table.
