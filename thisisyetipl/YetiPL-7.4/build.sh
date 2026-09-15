#!/usr/bin/env bash
set -euo pipefail

if ! command -v java >/dev/null 2>&1; then
  echo "Java is required. Install JDK 25." >&2
  exit 1
fi
JAVA_MAJOR=$(java -version 2>&1 | awk -F'[".]' '/version/ {print $2; exit}')
if [ "$JAVA_MAJOR" -lt 25 ]; then
  echo "YetiPL 7.3 targets Paper 26.2 and requires JDK 25 to build. Found Java $JAVA_MAJOR." >&2
  exit 1
fi
if command -v ./gradlew >/dev/null 2>&1; then
  ./gradlew clean build
elif command -v gradle >/dev/null 2>&1; then
  gradle clean build
else
  echo "Gradle 9.1+ is required. Install Gradle or generate the wrapper described in BUILDING.md." >&2
  exit 1
fi
