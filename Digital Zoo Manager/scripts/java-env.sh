#!/usr/bin/env sh
# Shared JDK discovery, including Homebrew installations on macOS.
if [ -n "${JAVA_HOME:-}" ] && [ -x "$JAVA_HOME/bin/javac" ]; then
    ZOO_JDK_BIN="$JAVA_HOME/bin"
elif [ -x /opt/homebrew/opt/openjdk/bin/javac ]; then
    ZOO_JDK_BIN=/opt/homebrew/opt/openjdk/bin
elif [ -x /usr/local/opt/openjdk/bin/javac ]; then
    ZOO_JDK_BIN=/usr/local/opt/openjdk/bin
else
    ZOO_JDK_BIN=""
fi
ZOO_JAVA="${ZOO_JDK_BIN:+$ZOO_JDK_BIN/}java"
ZOO_JAVAC="${ZOO_JDK_BIN:+$ZOO_JDK_BIN/}javac"
ZOO_JAR="${ZOO_JDK_BIN:+$ZOO_JDK_BIN/}jar"
if ! "$ZOO_JAVAC" -version >/dev/null 2>&1; then
    echo "JDK 8+ diperlukan. Atur JAVA_HOME ke folder JDK (bukan JRE)." >&2
    exit 1
fi
# --release also checks Java 8 API compatibility on newer JDKs.
case "$("$ZOO_JAVAC" -version 2>&1)" in
    "javac 1.8"*) ZOO_COMPILE_FLAGS="-source 8 -target 8" ;;
    *) ZOO_COMPILE_FLAGS="--release 8" ;;
esac
