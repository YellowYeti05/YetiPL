# YetiPL 7.4 — Build-Ready Source Package

Version: 7.4.0
Target: Paper 26.2 / Java 25

## 7.4 specification locked in
- Hub with separate inventory state and Hub XP fixed at 0
- WorldEdit-selected Hub region / WorldGuard integration target
- Half-yellow / half-green custom Server Selector compass
- YetisBOXXED and YetisSMP custom selector stands (`/stand YetisBOXXED`, `/stand YetisSMP`)
- Join message delayed until a player chooses YetisBOXXED or a YetisSMP destination
- Four YetisSMP destinations: Resource, Actual, Claiming, Cool World
- Resource World items transferable to survival destinations
- Claiming World and Cool World claims use a Golden Hoe and gold visual border
- `/setsmpspawn` is contextual to the SMP world the admin is standing in
- 50,000 x 50,000 default SMP world borders
- Cool World companion datapack with 29 custom biome definitions
- YetiCoins display symbol: ¥
- Tebex console delivery command: `giveyeticoins {username} 10000`
- Admin command specification: freeze/unfreeze, mute/unmute, clearchat, mutechat, broadcast, consolebroadcast
- YetiBot commands: `/bottoggle`, `/bot`, `/botfeedback`; API key configurable but blank by default

## Included companion files
See `extras/` for the Cool World datapack and resource-pack assets available in this build package.

## Important status
This archive is a 7.4 build-ready DEVELOPMENT source package. The existing 7.3 systems are retained and the
7.4 configuration/command surface plus SMP foundation are staged. Complex 7.4 systems (crash-safe multi-world
inventory isolation, complete WorldGuard/WorldEdit claim integration, custom selector rendering, all moderation
persistence, AI HTTP integration, and full custom-block biome integration) still require implementation and live
Paper 26.2 testing before this should be treated as a production release.

Do not install on the live server without compiling with Java 25 and testing on a staging Paper 26.2 server first.
