#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
. ./scripts/java-env.sh
mkdir -p build/classes dist
"$ZOO_JAVAC" $ZOO_COMPILE_FLAGS -encoding UTF-8 -d build/classes src/main/java/*.java
"$ZOO_JAR" cfe dist/DigitalZooManager.jar Main -C build/classes .
echo "Build berhasil: dist/DigitalZooManager.jar"
