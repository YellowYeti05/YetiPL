# YetiPL 7.4

YetiPL is the all-in-one Paper 26.2 server suite for **YetisBOXXED**. This source tree is organized as error-isolated services so a failure in one feature can be diagnosed without intentionally taking down every other module.

## Build target

- Minecraft/Paper: **26.2**
- Java: **25**
- Gradle: **9.1+ recommended**
- Output: `build/libs/YetiPL-7.3.0.jar`

See `BUILDING.md` for exact commands.

## Implemented gameplay systems

This 7.3 source includes working baseline implementations for:

- Golden-Axe WorldEdit-style selection, set/replace/copy/paste/undo/redo
- Simplified region protection and flags
- Warps and portals
- One-per-player void **Presets**, isolated Creative inventories, trust/hyper-trust, reset, 10 border levels and Yeti Coin upgrades
- Yeti Coins with persistent transaction-backed balances and infinite-admin permission
- `/shop`, permanent custom-item unlocks and permanent cosmetics
- Preset-only Throwable TNT, TP Pads, 1-Hit Sword, Insta-Mine Pick and Nuke
- Crates and bindable crate keys
- Region-based regenerating gens
- Configurable archaeology regions using Suspicious Sand/Gravel
- Rank presets, affixes, Birdflop-style colors, chat colors and decimal chat cooldowns
- Chat filter, mentions and automatic chat games
- Custom potion-maker GUI
- Holograms
- Vaults and Ender Chest access
- Temporary bans, vanish, spectate and freeze
- Bug reports
- Cosmetic item disguise and item editor
- Custom trims scaffolding
- Custom music-disc metadata/sound integration scaffolding
- Shopkeeper management scaffolding
- Portable workstation commands
- Rules and Discord commands
- PvP server-side spam-click normalization
- Naked-killing toggle
- Lag protection
- Required resource-pack enforcement
- Scoreboard and persistent playtime Yeti Coin rewards
- Optional LuckPerms command bridge
- Diagnostics, self-tests, module isolation and recent error IDs
- `/updates` changelog UI/commands

## Optional integrations

YetiPL does not bundle third-party plugins. It detects these when installed and continues safely when they are absent:

- WorldEdit / WorldGuard
- LuckPerms
- Vault
- ViaVersion / ViaBackwards
- Geyser / Floodgate
- ProtocolLib

For the intended cross-version setup, install ViaVersion/ViaBackwards and Geyser/Floodgate alongside YetiPL.

## Resource pack

`resource-pack.required` defaults to `true`, but `resource-pack.url` is intentionally blank. Host the YetiPL resource-pack ZIP on a direct HTTP(S) URL and set that URL before production use. Startup diagnostics warn when required mode is enabled without a URL.

## Important production note

This project is **build-ready source**, not a claim that every system has been live-tested on a production Paper 26.2 server. Before public release, compile with Java 25, run `/yetipl selftest`, and test destructive/high-load systems (Preset reset, nukes, TNT, gens, resource pack, Bedrock translation) on a staging server first.
