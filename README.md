# Search Engine – Digital Product Engineering Test 2

## Overview

A **deterministic personalization, recommendation, and search engine** written in Java 8 (no external build tool required). Given a dataset of content, users, queries, and scoring rules, the engine:

1. **Indexes** content items
2. **Runs keyword search** against each query
3. **Applies personalization boosts** (specialty, channel)
4. **Ranks** content items with a deterministic tie-break
5. **Generates recommendations** excluding already-ranked and recently-viewed items

---

## Project Structure

```
search-engine/
├── src/main/java/com/mayo/searchengine/
│   ├── Main.java                        # CLI entry point
│   ├── engine/
│   │   └── SearchEngine.java            # Core scoring & ranking logic
│   ├── model/
│   │   ├── Content.java
│   │   ├── User.java
│   │   ├── Query.java
│   │   ├── Rules.java
│   │   └── Weights.java
│   └── output/
│       ├── Output.java
│       ├── Result.java
│       ├── Recommendation.java
│       └── Metrics.java
├── lib/                                  # Jackson 2.15.2 fat-jar dependencies
│   ├── jackson-databind-2.15.2.jar
│   ├── jackson-core-2.15.2.jar
│   └── jackson-annotations-2.15.2.jar
├── outputs/                              # Generated output files
│   ├── dataset_1.output.json
│   ├── dataset_2.output.json
│   ├── dataset_3.output.json
│   └── dataset_4.output.json
├── build.sh                              # Bash build script
├── build.bat                             # Windows CMD build script
├── run_all.sh                            # Runs engine for all 4 datasets
└── search-engine.jar                     # Pre-built fat JAR
```

---

## Prerequisites

| Requirement                 | Version |
| --------------------------- | ------- |
| Java (JDK or JRE)           | 8+      |
| curl (for downloading deps) | any     |

No Maven or Gradle required.

---

## Quick Start (Pre-built JAR)

The `search-engine.jar` is already built and included. Run directly:

```bash
# Single dataset
java -jar search-engine.jar --case path/to/cases/dataset_1 --out outputs/dataset_1.output.json
```

---

## Building from Source

### Linux / macOS / Git Bash (Windows)

> **Note:** If your project path contains spaces (common on Windows with OneDrive), create a short junction first:
>
> ```powershell
> # Run once as Administrator in PowerShell
> New-Item -ItemType Junction -Path 'C:\se'    -Target '<full_path_to_search-engine>'
> New-Item -ItemType Junction -Path 'C:\cases' -Target '<full_path_to_cases_dir>'
> ```

```bash
bash build.sh
```

### Windows CMD

```cmd
build.bat
```

---

## Running All Datasets

```bash
bash run_all.sh
```

This runs the engine against `dataset_1` through `dataset_4` and writes results to `outputs/`.

### Manual CLI Usage

**Step 1 – Open terminal and navigate to the search-engine folder:**

```bash
cd "C:/Users/m141014/OneDrive - Mayo Clinic/Desktop/applications/Digital Product Engineering Roles/Coding Sessions/TBD/TEST 2/search-engine"
```

**Step 2 – Run each dataset:**

```bash
java -jar search-engine.jar --case "../engineering_test_2/Archive/cases/dataset_1" --out "outputs/dataset_1.output.json"
```

```bash
java -jar search-engine.jar --case "../engineering_test_2/Archive/cases/dataset_2" --out "outputs/dataset_2.output.json"
```

```bash
java -jar search-engine.jar --case "../engineering_test_2/Archive/cases/dataset_3" --out "outputs/dataset_3.output.json"
```

```bash
java -jar search-engine.jar --case "../engineering_test_2/Archive/cases/dataset_4" --out "outputs/dataset_4.output.json"
```

**Arguments:**

| Argument       | Description                                                                     |
| -------------- | ------------------------------------------------------------------------------- |
| `--case <dir>` | Directory containing `content.json`, `users.json`, `queries.json`, `rules.json` |
| `--out <file>` | Destination path for the JSON output file                                       |

---

## Scoring Algorithm

For each query/user pair, every content item is scored:

```
score =
  (keyword_match_count  × weights.keywordMatch)   // query tokens found in title
+ (tag_match_count      × weights.tagMatch)        // query tokens matching tags
+ (specialtyBoost       if specialty ∈ user.preferredSpecialties)
+ (channelBoost         if channel == user.channelPreference)
+ (popularityScore      × weights.popularityWeight)
+ (1/freshnessDays      × weights.freshnessWeight × 100)
```

### Tie-Break (mandatory order)

1. Lower `freshnessDays` (ascending)
2. Higher `popularityScore` (descending)
3. Lexicographically smaller `contentId` (ascending)

### Recommendation Scoring

Exclude: top-K ranked results + `user.recentContentIds`

Remaining items scored by:

```
score =
  (shared_tags_with_top_result × weights.tagMatch)
+ (specialtyBoost if specialty ∈ user.preferredSpecialties)
+ (popularityScore × weights.popularityWeight)
+ (1/freshnessDays × weights.freshnessWeight × 100)
```

Same tie-break applied. Returns exactly `rules.recommendationCount` items.

---

## Output Format

```json
{
  "results": [
    { "queryId": "Q1", "rankedContentIds": ["C8", "C6", "C1", "C9", "C4"] }
  ],
  "recommendations": [
    { "queryId": "Q1", "recommendedContentIds": ["C7", "C2", "C10"] }
  ],
  "metrics": {
    "queriesProcessed": 2,
    "topK": 5,
    "recommendationCount": 3
  }
}
```

---

## Determinism Guarantee

- All sorting uses `Comparator` chains with no random or time-dependent state
- `LinkedHashMap` is used for user lookup (insertion-order preserved)
- Verified: running each dataset twice produces byte-for-byte identical output
