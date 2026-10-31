#!/usr/bin/env python3
"""Builds easy/README.md, medium/README.md, hard/README.md and the progress table in README.md.

Problems come from scripts/problems.json (official LeetCode metadata), ordered from easiest to hardest by
acceptance rate. A problem counts as solved when its folder exists, e.g. easy/TwoSum/. The first line of
the problem's README may list its approaches:  <!-- approaches: Brute force · Hash map -->
    python3 scripts/build_index.py
"""
import json
import re
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
LEVELS = {"Easy": "easy", "Medium": "medium", "Hard": "hard"}
NUMBERS = {"1": "One", "2": "Two", "3": "Three", "4": "Four", "24": "TwentyFour"}


def folder_name(title: str) -> str:
    """'Best Time to Buy and Sell Stock' -> BestTimeToBuyAndSellStock, '3Sum' -> ThreeSum, 'Pow(x, n)' -> PowXN."""
    words = re.findall(r"[A-Za-z0-9]+", title.replace("'", ""))
    name = "".join(w[0].upper() + w[1:] for w in words)
    lead = re.match(r"\d+", name)
    if lead:
        name = NUMBERS.get(lead.group(), "N" + lead.group()) + name[lead.end():]
    return name


def approaches(path: Path) -> str:
    readme = path / "README.md"
    if readme.exists():
        m = re.match(r"<!--\s*approaches:\s*(.*?)\s*-->", readme.read_text(encoding="utf-8"))
        if m:
            return m.group(1)
    return ""


def main():
    problems = json.loads((ROOT / "scripts/problems.json").read_text(encoding="utf-8"))
    summary = []
    for level, directory in LEVELS.items():
        items = [p for p in problems if p["level"] == level]
        base = ROOT / directory
        base.mkdir(exist_ok=True)
        solved = 0
        rows, by_topic = [], defaultdict(list)
        for i, p in enumerate(items, 1):
            name = folder_name(p["title"])
            path = base / name
            done = path.is_dir()
            solved += done
            title = f"[{p['title']}]({name}/)" if done else p["title"]
            link = f"[↗](https://leetcode.com/problems/{p['slug']}/)"
            rows.append(f"| {i} | {p['id']} | {title} {link} | {', '.join(p['topics'][:4])} | {p['acRate']}% | "
                        f"{'✅ ' + approaches(path) if done else '⏳'} |")
            if done:
                for t in p["topics"][:3]:
                    by_topic[t].append(f"[{p['id']}. {p['title']}]({name}/)")
        lines = [
            f"# {level} — {solved} / {len(items)} solved",
            "",
            "Ordered from easiest to hardest (by LeetCode acceptance rate). Each solved problem has its own folder with",
            "the statement, a detailed explanation with diagrams (`README.md`), several approaches (`*.java`) and tests.",
            "",
            "| Order | # | Problem | Topics | Acceptance | Status · approaches |",
            "|---|---|---|---|---|---|",
            *rows,
        ]
        if by_topic:
            lines += ["", "## By topic", ""]
            for topic in sorted(by_topic):
                lines.append(f"- **{topic}** ({len(by_topic[topic])}): " + " · ".join(by_topic[topic]))
        lines.append("")
        (base / "README.md").write_text("\n".join(lines), encoding="utf-8")
        summary.append((level, directory, solved, len(items)))

    table = ["| Level | Solved | Planned | Progress |", "|---|---|---|---|"]
    for level, directory, solved, total in summary:
        bar = "█" * round(10 * solved / total) + "░" * (10 - round(10 * solved / total))
        table.append(f"| [{level}]({directory}/) | {solved} | {total} | `{bar}` |")
    readme = ROOT / "README.md"
    text = readme.read_text(encoding="utf-8")
    block = "<!-- progress:start -->\n" + "\n".join(table) + "\n<!-- progress:end -->"
    text = re.sub(r"<!-- progress:start -->.*?<!-- progress:end -->", block, text, flags=re.S) \
        if "<!-- progress:start -->" in text else text + "\n" + block + "\n"
    readme.write_text(text, encoding="utf-8")
    for level, _, solved, total in summary:
        print(f"{level}: {solved}/{total}")


if __name__ == "__main__":
    main()
