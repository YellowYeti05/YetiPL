package gg.yetisboxxed.yetipl.command;

import gg.yetisboxxed.yetipl.YetiPLPlugin;
import gg.yetisboxxed.yetipl.config.ConfigValidator;
import gg.yetisboxxed.yetipl.diagnostics.ErrorRecord;
import gg.yetisboxxed.yetipl.module.ManagedModule;
import gg.yetisboxxed.yetipl.module.ModuleState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class YetiPLCommand implements CommandExecutor, TabCompleter {
    private final YetiPLPlugin plugin;

    public YetiPLCommand(YetiPLPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(Component.text("✦ YetiPL 7.3 ✦", NamedTextColor.GOLD));
            sender.sendMessage(Component.text("Paper target: 26.2 | Running: " + Bukkit.getMinecraftVersion(), NamedTextColor.YELLOW));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "diagnose" -> diagnose(sender);
            case "errors" -> errors(sender);
            case "selftest" -> selfTest(sender);
            case "reload" -> reload(sender);
            default -> sender.sendMessage(Component.text("Usage: /yetipl <version|diagnose|errors|selftest|reload>", NamedTextColor.RED));
        }
        return true;
    }

    private void diagnose(CommandSender sender) {
        sender.sendMessage(Component.text("✦ YetiPL Diagnostics ✦", NamedTextColor.GOLD));
        List<String> configIssues = ConfigValidator.validate(plugin.getConfig());
        sender.sendMessage(Component.text("Config: " + (configIssues.isEmpty() ? "OK" : configIssues.size() + " issue(s)"), configIssues.isEmpty() ? NamedTextColor.YELLOW : NamedTextColor.RED));
        for (ManagedModule m : plugin.modules().snapshot().values()) {
            NamedTextColor color = m.state() == ModuleState.ENABLED ? NamedTextColor.YELLOW : NamedTextColor.RED;
            sender.sendMessage(Component.text("• " + m.module().displayName() + ": " + m.state(), color));
        }
        for (String dependency : List.of("WorldEdit", "WorldGuard", "LuckPerms", "Vault", "Geyser-Spigot", "floodgate", "ViaVersion", "ProtocolLib")) {
            boolean present = Bukkit.getPluginManager().getPlugin(dependency) != null;
            sender.sendMessage(Component.text("• " + dependency + ": " + (present ? "CONNECTED" : "NOT FOUND"), present ? NamedTextColor.YELLOW : NamedTextColor.RED));
        }
    }

    private void errors(CommandSender sender) {
        List<ErrorRecord> recent = plugin.errors().recent();
        sender.sendMessage(Component.text("✦ Recent YetiPL Errors ✦", NamedTextColor.GOLD));
        if (recent.isEmpty()) {
            sender.sendMessage(Component.text("No recent errors.", NamedTextColor.YELLOW));
            return;
        }
        recent.stream().limit(10).forEach(e -> sender.sendMessage(Component.text(e.id() + " | " + e.module() + " | " + e.message(), NamedTextColor.RED)));
    }

    private void selfTest(CommandSender sender) {
        List<String> failures = plugin.selfTests().run();
        if (failures.isEmpty()) sender.sendMessage(Component.text("✦ Self-test passed ✦", NamedTextColor.YELLOW));
        else {
            sender.sendMessage(Component.text("Self-test found " + failures.size() + " issue(s):", NamedTextColor.RED));
            failures.forEach(f -> sender.sendMessage(Component.text("• " + f, NamedTextColor.RED)));
        }
    }

    private void reload(CommandSender sender) {
        plugin.reloadConfig();
        List<String> issues = ConfigValidator.validate(plugin.getConfig());
        if (issues.isEmpty()) sender.sendMessage(Component.text("YetiPL configuration reloaded.", NamedTextColor.YELLOW));
        else sender.sendMessage(Component.text("Reloaded with " + issues.size() + " validation issue(s). Use /yetipl diagnose.", NamedTextColor.RED));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return List.of("version", "diagnose", "errors", "selftest", "reload").stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        return List.of();
    }
}
