#!/usr/bin/env bash
# =============================================================================
# build.sh – Compiles all sources and packages search-engine.jar
# =============================================================================
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC="$SCRIPT_DIR/src/main/java"
LIB="$SCRIPT_DIR/lib"
BUILD="$SCRIPT_DIR/build"
JAR="$SCRIPT_DIR/search-engine.jar"
CLASSPATH="$LIB/jackson-databind-2.15.2.jar;$LIB/jackson-core-2.15.2.jar;$LIB/jackson-annotations-2.15.2.jar"

echo "==> Compiling sources..."
mkdir -p "$BUILD"
find "$SRC" -name "*.java" > "$BUILD/sources.txt"
javac -source 8 -target 8 -cp "$CLASSPATH" -d "$BUILD" @"$BUILD/sources.txt"

echo "==> Packaging JAR..."
# Extract Jackson jars into build dir so they end up in the fat-jar
mkdir -p "$BUILD/_extracted"
for jar in "$LIB"/*.jar; do
    (cd "$BUILD/_extracted" && jar xf "$jar")
done

# Merge compiled classes over extracted Jackson classes
cp -r "$BUILD/com" "$BUILD/_extracted/"

# Create a manifest
mkdir -p "$BUILD/_extracted/META-INF"
printf 'Main-Class: com.mayo.searchengine.Main\n' > "$BUILD/_extracted/META-INF/MANIFEST.MF"

# Package the fat-jar
(cd "$BUILD/_extracted" && jar cfm "$JAR" META-INF/MANIFEST.MF .)

echo "==> Build complete: $JAR"
