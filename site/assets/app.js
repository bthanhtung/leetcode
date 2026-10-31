/* Hash router: #/ (problem list), #/viz (visualizer gallery), #/p/<slug> (problem page). */
(function () {
  const app = document.getElementById("app");
  const esc = (s) => String(s).replace(/[&<>"]/g, (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" })[c]);
  let problems = [];
  const state = { q: "", level: "all", topic: "", vizOnly: false };
  try {
    Object.assign(state, JSON.parse(localStorage.getItem("filters") || "{}"));
  } catch (e) { /* storage unavailable */ }

  /* ---------- theme ---------- */
  const root = document.documentElement;
  const isDark = () => root.dataset.theme ? root.dataset.theme === "dark" : matchMedia("(prefers-color-scheme: dark)").matches;
  function applyTheme() {
    document.getElementById("hljs-light").disabled = isDark();
    document.getElementById("hljs-dark").disabled = !isDark();
  }
  try {
    const saved = localStorage.getItem("theme");
    if (saved) root.dataset.theme = saved;
  } catch (e) { /* ignore */ }
  applyTheme();
  document.querySelector(".theme").onclick = () => {
    root.dataset.theme = isDark() ? "light" : "dark";
    try { localStorage.setItem("theme", root.dataset.theme); } catch (e) { /* ignore */ }
    applyTheme();
    route();
  };

  /* ---------- views ---------- */
  function item(p) {
    const viz = Viz.has(p.slug) ? `<span class="badge play">▶ mô phỏng</span>` : "";
    return `<a class="item" href="#/p/${p.slug}">
      <span class="num">#${p.id}</span>
      <span><div class="t">${esc(p.title)}</div><div class="sub">${esc(p.approaches || p.topics.join(" · "))}</div></span>
      <span class="right">${viz}<span class="badge ${p.level}">${p.level}</span></span></a>`;
  }

  function home() {
    const count = (l) => problems.filter((p) => p.level === l).length;
    const topics = [...new Set(problems.flatMap((p) => p.topics))].sort();
    app.innerHTML = `
      <section class="hero">
        <div>
          <h1>Make LeetCode easy.</h1>
          <p>Xem từng bước thuật toán chạy để hiểu vì sao nó đúng, chứ không học thuộc lời giải. ${problems.length} bài LeetCode giải thích bằng tiếng Việt, code Java đã kiểm thử, ${Viz.slugs().length} bài có mô phỏng tự sửa được dữ liệu. Rèn tư duy logic mỗi ngày, tự tin hơn khi phỏng vấn ở các công ty công nghệ lớn.</p>
        </div>
        <div class="stats">
          <div class="stat easy"><b>${count("Easy")}</b><span>Easy</span></div>
          <div class="stat medium"><b>${count("Medium")}</b><span>Medium</span></div>
          <div class="stat hard"><b>${count("Hard")}</b><span>Hard</span></div>
          <div class="stat viz"><b>${Viz.slugs().length}</b><span>mô phỏng</span></div>
        </div>
      </section>
      <div class="filters">
        <input type="search" placeholder="Tìm theo tên, số bài, kỹ thuật…" value="${esc(state.q)}" aria-label="Tìm bài">
        <div class="chips">${["all", "Easy", "Medium", "Hard"].map((l) => `<button class="chip ${state.level === l ? "on" : ""}" data-level="${l}">${l === "all" ? "Tất cả" : l}</button>`).join("")}
          <button class="chip ${state.vizOnly ? "on" : ""}" data-viz>▶ Có mô phỏng</button></div>
        <select aria-label="Chủ đề"><option value="">Mọi chủ đề</option>${topics.map((t) => `<option ${t === state.topic ? "selected" : ""}>${esc(t)}</option>`).join("")}</select>
      </div>
      <p class="count"></p>
      <div class="list"></div>`;
    const list = app.querySelector(".list");
    const countEl = app.querySelector(".count");
    const update = () => {
      try { localStorage.setItem("filters", JSON.stringify(state)); } catch (e) { /* ignore */ }
      const q = state.q.trim().toLowerCase();
      const shown = problems.filter((p) =>
        (state.level === "all" || p.level === state.level) &&
        (!state.topic || p.topics.includes(state.topic)) &&
        (!state.vizOnly || Viz.has(p.slug)) &&
        (!q || `${p.id} ${p.title} ${p.approaches} ${p.topics.join(" ")}`.toLowerCase().includes(q)));
      countEl.textContent = `${shown.length} bài`;
      list.innerHTML = shown.map(item).join("") || `<p class="loading">Không có bài nào khớp.</p>`;
    };
    app.querySelector("input").oninput = (e) => { state.q = e.target.value; update(); };
    app.querySelector("select").onchange = (e) => { state.topic = e.target.value; update(); };
    app.querySelectorAll("[data-level]").forEach((b) => (b.onclick = () => {
      state.level = b.dataset.level;
      app.querySelectorAll("[data-level]").forEach((x) => x.classList.toggle("on", x === b));
      update();
    }));
    app.querySelector("[data-viz]").onclick = (e) => { state.vizOnly = !state.vizOnly; e.target.classList.toggle("on", state.vizOnly); update(); };
    update();
  }

  function gallery() {
    const withViz = problems.filter((p) => Viz.has(p.slug));
    app.innerHTML = `<section class="hero"><div><h1>Mô phỏng thuật toán</h1><p>Chọn một bài để xem từng bước: con trỏ di chuyển, cửa sổ trượt, bảng DP được lấp đầy, cây được duyệt… Mỗi mô phỏng cho phép sửa dữ liệu vào.</p></div></section>
      <div class="gallery">${withViz.map((p) => `<a class="item" href="#/p/${p.slug}"><span><div class="t">${p.id}. ${esc(p.title)}</div><div class="sub">${esc(p.topics.slice(0, 3).join(" · "))}</div></span><span class="badge ${p.level}">${p.level}</span></a>`).join("")}</div>`;
  }

  async function problem(slug) {
    const i = problems.findIndex((p) => p.slug === slug);
    if (i < 0) {
      app.innerHTML = `<p>Không tìm thấy bài này. <a href="#/">Về danh sách</a></p>`;
      return;
    }
    const p = problems[i];
    document.title = `${p.id}. ${p.title} · Algorithm Visualizer`;
    app.innerHTML = `<p class="loading">Đang tải…</p>`;
    const detail = await fetch(`data/p/${slug}.json`).then((r) => r.json());
    const prev = problems[i - 1], next = problems[i + 1];
    const hasViz = Viz.has(slug);
    app.innerHTML = `
      <div class="crumbs"><a href="#/">Bài tập</a> / ${p.level}</div>
      <h1 class="ptitle">${p.id}. ${esc(p.title)}</h1>
      <div class="meta"><span class="badge ${p.level}">${p.level}</span>${p.topics.map((t) => `<span class="tag">${esc(t)}</span>`).join("")}
        <a href="https://leetcode.com/problems/${p.slug}/" target="_blank" rel="noopener">LeetCode ↗</a></div>
      ${hasViz ? `<section class="section"><h2>▶ Mô phỏng</h2>
        <div class="legend"><span class="l-cur">đang xét</span><span class="l-ok">đúng / đã chốt</span><span class="l-bad">sai / loại</span><span class="l-win">vùng đang xét</span><span class="l-mark">tham chiếu</span></div>
        <div id="viz" style="margin-top:10px"></div></section>` : ""}
      <section class="section md">${marked.parse(detail.readme)}</section>
      ${detail.java ? `<section class="section"><details class="code"><summary>Code Java (${esc(p.folder.split("/")[1])}.java)</summary><pre><code class="language-java">${esc(detail.java)}</code></pre></details></section>` : ""}
      <nav class="pager">${prev ? `<a href="#/p/${prev.slug}">← ${prev.id}. ${esc(prev.title)}</a>` : "<span></span>"}${next ? `<a href="#/p/${next.slug}">${next.id}. ${esc(next.title)} →</a>` : ""}</nav>`;
    if (hasViz) Viz.mount(document.getElementById("viz"), slug);
    app.querySelectorAll("pre code.language-java").forEach((el) => hljs.highlightElement(el));
    const blocks = app.querySelectorAll(".md pre code.language-mermaid");
    blocks.forEach((code) => {
      const div = document.createElement("div");
      div.className = "mermaid";
      div.textContent = code.textContent;
      code.parentElement.replaceWith(div);
    });
    if (blocks.length) {
      mermaid.initialize({ startOnLoad: false, theme: isDark() ? "dark" : "neutral", securityLevel: "strict" });
      try { await mermaid.run({ querySelector: ".md .mermaid" }); } catch (e) { console.warn(e); }
    }
    window.scrollTo(0, 0);
  }

  function route() {
    const hash = location.hash || "#/";
    document.querySelectorAll(".top nav a").forEach((a) => a.classList.toggle("active", a.getAttribute("href") === (hash.startsWith("#/viz") ? "#/viz" : hash.startsWith("#/p/") ? "" : "#/")));
    if (!hash.startsWith("#/p/")) document.title = "Algorithm Visualizer";
    if (hash.startsWith("#/p/")) problem(decodeURIComponent(hash.slice(4)));
    else if (hash.startsWith("#/viz")) gallery();
    else home();
  }

  fetch("data/index.json")
    .then((r) => r.json())
    .then((data) => {
      problems = data;
      window.addEventListener("hashchange", route);
      route();
    })
    .catch(() => (app.innerHTML = `<p>Không tải được dữ liệu. Chạy <code>python3 site/build.py</code> trước.</p>`));
})();
