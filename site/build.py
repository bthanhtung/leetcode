#!/usr/bin/env python3
"""Builds the data files of the static site from the problem folders.

    python3 site/build.py

Writes data/index.json (one entry per solved problem) and data/p/<slug>.json (README markdown and the Java
solution) into site/public/data, which Vite copies to the output. Generated, not committed: run it before `npm run dev`.
"""
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT / "scripts"))
from build_index import LEVELS, approaches, folder_name  # noqa: E402

OUT = ROOT / "site" / "public" / "data"


def readme_body(text: str) -> str:
    """Drops the approaches comment, the H1 and the metadata line (the page renders those itself)."""
    lines = text.split("\n")
    while lines and (lines[0].startswith("<!--") or lines[0].startswith("# ") or not lines[0].strip()
                     or lines[0].startswith("**Easy**") or lines[0].startswith("**Medium**")
                     or lines[0].startswith("**Hard**")):
        lines.pop(0)
    return "\n".join(lines).strip() + "\n"


def split_readme(body: str) -> dict:
    """Separates the statement and the related-problems line from the rest so the page can lay them out on their own."""
    parts = re.split(r"^(?=## )", body, flags=re.M)
    statement, solution, related = "", [], ""
    for part in parts:
        if part.startswith("## Đề bài"):
            statement = part.split("\n", 1)[1].strip()
        elif part.startswith("## Bài liên quan"):
            related = part.split("\n", 1)[1].strip()
        elif part.strip():
            solution.append(part)
    return {"statement": statement, "solution": "".join(solution).strip(), "related": related}


def main():
    problems = json.loads((ROOT / "scripts/problems.json").read_text(encoding="utf-8"))
    (OUT / "p").mkdir(parents=True, exist_ok=True)
    for old in (OUT / "p").glob("*.json"):
        old.unlink()
    index = []
    for p in problems:
        name = folder_name(p["title"])
        folder = ROOT / LEVELS[p["level"]] / name
        if not folder.is_dir():
            continue
        readme = (folder / "README.md").read_text(encoding="utf-8")
        java = folder / f"{name}.java"
        index.append({
            "id": p["id"], "slug": p["slug"], "title": p["title"], "level": p["level"],
            "topics": p["topics"], "acRate": p["acRate"], "approaches": approaches(folder),
            "folder": f"{LEVELS[p['level']]}/{name}",
        })
        detail = {
            **split_readme(readme_body(readme)),
            "java": java.read_text(encoding="utf-8") if java.exists() else "",
        }
        (OUT / "p" / f"{p['slug']}.json").write_text(json.dumps(detail, ensure_ascii=False), encoding="utf-8")
    index.sort(key=lambda x: x["id"])
    (OUT / "index.json").write_text(json.dumps(index, ensure_ascii=False, separators=(",", ":")), encoding="utf-8")
    print(f"{len(index)} problems -> {OUT.relative_to(ROOT)}")


if __name__ == "__main__":
    main()
