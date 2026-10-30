# leetcode
The record of my problem solving journey on leetcode.com

## Layout

```
src/main/java/com/tungdadev/
├── easy/ medium/ hard/   LeetCode by difficulty: Problem.java (statement, main() to try it, one method per approach)
│                         + Problem.md (explanation with diagrams) for the newer ones
└── tutorial/ interview/ …  notes for interviews
src/test/java/…           JUnit tests: examples, edge cases, and every approach checked against brute force on random inputs
```

Run the tests: `./gradlew test`

## Solutions with full explanations

| # | Problem | Level | Approaches | Notes |
|---|---|---|---|---|
| 121 | [Best Time to Buy and Sell Stock](src/main/java/com/tungdadev/easy/BestTimeToBuyAndSellStock.java) | Easy | brute force · one pass · state-machine DP | [explanation](src/main/java/com/tungdadev/easy/BestTimeToBuyAndSellStock.md) |
| 347 | [Top K Frequent Elements](src/main/java/com/tungdadev/medium/TopKFrequentElements.java) | Medium | sort · min-heap · bucket sort · quickselect | [explanation](src/main/java/com/tungdadev/medium/TopKFrequentElements.md) |
| 42 | [Trapping Rain Water](src/main/java/com/tungdadev/hard/TrappingRainWater.java) | Hard | brute force · prefix max · monotonic stack · two pointers | [explanation](src/main/java/com/tungdadev/hard/TrappingRainWater.md) |
