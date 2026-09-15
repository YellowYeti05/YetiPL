# YetiPL 7.3 implementation matrix

## Core / safety
- [x] Module registration and failure isolation
- [x] Error IDs and recent-error log
- [x] `/yetipl diagnose`, `/yetipl errors`, `/yetipl selftest`, `/yetipl reload`
- [x] Startup config validation
- [x] Persistent YAML stores with temporary-file replacement
- [x] Lag protector

## Building / regions
- [x] Golden Axe selection wand
- [x] `//set`, `//replace`, `//copy`, `//paste`, `//undo`, `//redo`
- [x] Region creation/deletion and common protection flags
- [x] Blocked-hand / required-hand restrictions
- [x] Warps
- [x] Portals
- [x] Regenerating gens with configurable delay and block pool
- [x] Archaeology regions and custom rewards
- [x] Holograms with MiniMessage/Birdflop-style hex conversion

## Presets
- [x] One Preset maximum per player
- [x] Void Preset world
- [x] Creative mode for owners/hyper-trusted builders
- [x] Adventure mode for visit-only trusted players
- [x] Separate inventory/armor/offhand/XP/gamemode snapshot
- [x] Advancement suppression in Presets
- [x] `/preset trust` = visit only
- [x] `/preset hypertrust` = edit; editor must own a Preset
- [x] `/preset reset`
- [x] 10 upgrade levels with expanding personal border
- [x] Yeti Coin purchase/upgrade flow
- [x] Restricted technical items (command-block variants, test blocks, structure void)

## Economy / shop
- [x] Persistent Yeti Coins
- [x] Infinite-admin Yeti Coins permission
- [x] Persistent playtime rewards with restart-safe claim tracking
- [x] `/shop`
- [x] Permanent cosmetic unlock/equip flow
- [x] Permanent Preset custom-item unlock flow
- [x] Throwable TNT
- [x] TP pads
- [x] 1-Hit Sword
- [x] Insta-Mine Pick
- [x] Nuke with performance guardrails
- [x] Crates and keys

## Chat / ranks
- [x] Rank presets including OWNER, ADMIN, BUILDER, HELPER, JANITOR, VIP, MVP, ISOPOD, COPPER BLOCK, AMONG US
- [x] Prefix/suffix/chat color storage
- [x] Birdflop-style hex conversion
- [x] Decimal rank chat cooldowns
- [x] Configurable chat filter
- [x] Mentions
- [x] Chat Games baseline (math, unscramble, type-it)

## Player utilities / cosmetics
- [x] Potion-maker GUI baseline
- [x] Vaults
- [x] Ender Chest access
- [x] Portable workstation commands
- [x] Heads
- [x] Unsafe/high-level enchant command path
- [x] Ride command path
- [x] Trim item scaffolding
- [x] Item metadata editor (`/nbt`) with safe YetiPL PDC storage
- [x] `/itemdisguise`
- [x] Cosmetic glint/trail/aura system
- [x] Rules and Discord
- [x] Scoreboard

## Moderation / combat
- [x] Temporary-only ban system
- [x] IP bans
- [x] Ban list
- [x] Vanish
- [x] Spectate and restore
- [x] Freeze
- [x] Bug reports
- [x] Server-side PvP hit cooldown for Java/Bedrock-translated clients
- [x] Naked-killing toggle

## Integrations / delivery
- [x] Optional dependency detection
- [x] Required resource-pack status enforcement
- [x] LuckPerms command bridge when LuckPerms is installed
- [x] Cross-version deployment is designed for ViaVersion/ViaBackwards
- [x] Bedrock deployment is designed for Geyser/Floodgate

## Requires external assets or deployment configuration
- [ ] Host the required resource-pack ZIP and set `resource-pack.url`
- [ ] Install ViaVersion/ViaBackwards for Java cross-version translation
- [ ] Install Geyser/Floodgate for Bedrock access
- [ ] Put custom music audio into the required resource pack; YouTube URLs cannot be streamed directly by a vanilla Minecraft client
- [ ] Supply final custom trim/shopkeeper textures/models in the resource pack/datapack
- [ ] Run staging-server compatibility/performance tests before public release

The unchecked items are deployment/assets/testing tasks rather than missing core Java command wiring.
