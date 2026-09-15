package gg.yetisboxxed.yetipl.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class UpdatesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String version = args.length == 0 ? "latest" : args[0].toLowerCase();
        sender.sendMessage(Component.text("✦ YetiPL Updates ✦", NamedTextColor.GOLD));
        switch (version) {
            case "latest", "7.3" -> {
                sender.sendMessage(Component.text("7.3 - Error-resistant foundation", NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("• Module failure isolation"));
                sender.sendMessage(Component.text("• /yetipl diagnose, errors, selftest"));
                sender.sendMessage(Component.text("• Config validation and startup self-test"));
            }
            case "7.2" -> {
                sender.sendMessage(Component.text("7.2 - Updates system", NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("• Added /updates and update history"));
            }
            case "7.1" -> {
                sender.sendMessage(Component.text("7.1 - Item disguise", NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("• Designed /itemdisguise <item> packet-based held-item disguise"));
            }
            default -> sender.sendMessage(Component.text("Known versions: 7.1, 7.2, 7.3", NamedTextColor.RED));
        }
        return true;
    }
}
