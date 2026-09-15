package gg.yetisboxxed.yetipl.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class Items {
    private Items() {}
    public static ItemStack named(Material type, String name, String... lore) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name, NamedTextColor.GOLD));
        if (lore.length > 0) meta.lore(List.of(lore).stream().map(s -> Component.text(s, NamedTextColor.GRAY)).toList());
        item.setItemMeta(meta);
        return item;
    }
}
