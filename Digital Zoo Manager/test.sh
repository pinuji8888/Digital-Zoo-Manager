#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
. ./scripts/java-env.sh
sh ./build.sh
mkdir -p build/test-classes
"$ZOO_JAVAC" $ZOO_COMPILE_FLAGS -encoding UTF-8 -cp build/classes -d build/test-classes src/test/java/*.java
"$ZOO_JAVA" -cp build/classes:build/test-classes ZooTests
"$ZOO_JAVA" -cp build/classes:build/test-classes ZooGuiTests
