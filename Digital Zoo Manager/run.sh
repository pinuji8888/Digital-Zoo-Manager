#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
. ./scripts/java-env.sh
sh ./build.sh
exec "$ZOO_JAVA" -jar dist/DigitalZooManager.jar
