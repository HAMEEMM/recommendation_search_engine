#!/usr/bin/env bash
# =============================================================================
# run_all.sh – Runs the engine against all four datasets and writes outputs
# =============================================================================
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR="$SCRIPT_DIR/search-engine.jar"
CASES_DIR="$SCRIPT_DIR/../engineering_test_2/Archive/cases"
OUTPUTS_DIR="$SCRIPT_DIR/outputs"

mkdir -p "$OUTPUTS_DIR"

for dataset in dataset_1 dataset_2 dataset_3 dataset_4; do
    echo "==> Processing $dataset..."
    java -jar "$JAR" \
        --case "$CASES_DIR/$dataset" \
        --out  "$OUTPUTS_DIR/${dataset}.output.json"
done

echo ""
echo "All outputs written to: $OUTPUTS_DIR"
