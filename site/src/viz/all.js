/* Loads every visualizer. Kept behind a dynamic import so the first paint does not wait for it. */
import "./arrays.js";
import "./strings.js";
import "./math.js";
import "./lists.js";
import "./trees.js";
import "./search.js";
import "./design.js";
import "./graphs.js";
import "./backtracking.js";
import "./dp.js";
import "./greedy.js";
import "./classics.js";

export { has, slugs, registry, parseInput, trace } from "./engine.js";
