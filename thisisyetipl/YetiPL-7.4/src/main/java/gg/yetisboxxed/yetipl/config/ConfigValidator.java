package gg.yetisboxxed.yetipl.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class ConfigValidator {
    private ConfigValidator() {}

    public static List<String> validate(FileConfiguration config) {
        List<String> issues = new ArrayList<>();
        if (config.getString("branding.prefix") == null) issues.add("branding.prefix is missing");

        int maxErrors = config.getInt("error-handling.max-recent-errors", 100);
        if (maxErrors < 10 || maxErrors > 10000) {
            issues.add("error-handling.max-recent-errors must be between 10 and 10000");
        }

        int maxBlocks = config.getInt("worldedit.max-blocks", 100000);
        if (maxBlocks < 1 || maxBlocks > 5_000_000) {
            issues.add("worldedit.max-blocks must be between 1 and 5000000");
        }

        long pvpCooldown = config.getLong("pvp.attack-cooldown-ms", 600L);
        if (pvpCooldown < 0 || pvpCooldown > 5000) {
            issues.add("pvp.attack-cooldown-ms must be between 0 and 5000");
        }

        boolean packRequired = config.getBoolean("resource-pack.required", true);
        String packUrl = config.getString("resource-pack.url", "").trim();
        if (packRequired && packUrl.isEmpty()) {
            issues.add("resource-pack.required is true but resource-pack.url is blank; players will not receive the required pack until a hosted URL is configured");
        } else if (!packUrl.isEmpty() && !(packUrl.startsWith("https://") || packUrl.startsWith("http://"))) {
            issues.add("resource-pack.url must be an http:// or https:// URL");
        }

        int chatGameInterval = config.getInt("chat-games.interval-ticks", 6000);
        if (chatGameInterval < 20) issues.add("chat-games.interval-ticks must be at least 20");

        int minPlayers = config.getInt("chat-games.minimum-players", 1);
        if (minPlayers < 1) issues.add("chat-games.minimum-players must be at least 1");

        return issues;
    }
}
