package gg.yetisboxxed.yetipl.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Msg {
    private Msg() {}
    public static Component prefix() { return Component.text("✦ YetiPL ✦ ", NamedTextColor.GOLD); }
    public static Component gold(String text) { return prefix().append(Component.text(text, NamedTextColor.YELLOW)); }
    public static Component error(String text) { return prefix().append(Component.text(text, NamedTextColor.RED)); }
    public static Component plain(String text) { return Component.text(text); }
}
