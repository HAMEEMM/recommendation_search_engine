# AI Usage Log

## Tool Used

GitHub Copilot (Claude Sonnet 4.6) via VS Code

## Session Summary

Used AI assistance to scaffold and implement the full deterministic search engine from scratch in one session.

---

## Interactions

### 1. Spec Extraction

- Used `pdftotext` to extract the problem specification from the provided PDF
- AI parsed the spec to identify: scoring formula, tie-break rules, recommendation logic, output schema, and submission requirements

### 2. Architecture Design

- AI proposed a clean Java package layout:
  - `model/` – POJO data classes
  - `engine/` – core scoring logic
  - `output/` – output POJOs
  - `Main.java` – CLI entry point
- Confirmed Jackson 2.15.2 as the JSON library (no build tool required, fat-jar approach)

### 3. Code Generation

- AI generated all source files:
  - `Content.java`, `User.java`, `Query.java`, `Rules.java`, `Weights.java` – model POJOs with Jackson annotations
  - `SearchEngine.java` – full scoring, ranking, tie-break, and recommendation logic
  - `Main.java` – CLI arg parsing, file I/O, orchestration
  - `Output.java`, `Result.java`, `Recommendation.java`, `Metrics.java` – output POJOs
- AI generated `build.sh`, `build.bat`, and `run_all.sh` scripts

### 4. Build & Dependency Resolution

- AI identified that Maven/Gradle were unavailable and adapted to a manual fat-jar build
- Diagnosed and resolved Windows path-with-spaces compilation issue by creating NTFS junctions (`C:\se`, `C:\cases`) to provide short paths to `javac`

### 5. Validation

- AI ran determinism check: executed all four datasets twice and diff'd outputs
- All four datasets confirmed byte-for-byte identical across runs

---

## Human Decisions / Overrides

- User requested Java as the implementation language
- User provided admin credentials to create NTFS junctions needed to work around OneDrive path spaces

---

## Code Authenticity

All business logic (scoring formula, tie-break, recommendation exclusion) was written by the AI strictly following the spec. No pre-existing templates were used.
