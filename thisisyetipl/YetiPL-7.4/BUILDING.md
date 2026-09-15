# Building YetiPL 7.3

## Requirements

1. JDK **25** installed and active.
2. Gradle **9.1 or newer** installed, or generate a Gradle wrapper once on a machine with Gradle available.
3. Internet access for Gradle to resolve the Paper 26.2 API from Paper's Maven repository.

## Build

Linux/macOS:

```bash
./build.sh
```

Windows:

```bat
build.bat
```

Or directly:

```bash
gradle clean build
```

The JAR is written to:

```text
build/libs/YetiPL-7.3.0.jar
```

## Optional Gradle wrapper

If you want a repository-local wrapper, run once with Gradle installed:

```bash
gradle wrapper --gradle-version 9.1.0
```

Then subsequent builds can use `./gradlew clean build` or `gradlew.bat clean build`.

## Staging checklist

1. Start Paper 26.2 on Java 25 with YetiPL only.
2. Run `/yetipl selftest` and `/yetipl diagnose`.
3. Configure and host the required resource pack.
4. Add integrations one at a time: LuckPerms, ViaVersion/ViaBackwards, Geyser/Floodgate, then WorldEdit/WorldGuard if desired.
5. Test Preset purchase/reset/upgrade and inventory restoration with disposable accounts.
6. Test Bedrock joins, PvP hit throttling, nukes/TNT and lag protection.
7. Back up `plugins/YetiPL/` before moving to production.
