package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.util.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class CustomItemService {
    private final NamespacedKey key;
    public CustomItemService(JavaPlugin plugin){key=new NamespacedKey(plugin,"custom_item");}
    public ItemStack create(String id){id=id.toLowerCase(Locale.ROOT);ItemStack item=switch(id){case "throwable_tnt"->Items.named(Material.TNT,"Throwable TNT","Preset-only custom item");case "tp_pads"->Items.named(Material.END_PORTAL_FRAME,"TP Pad","Place two to link them");case "one_hit_sword"->Items.named(Material.IRON_SWORD,"1 Hit Sword","Preset-only custom item");case "insta_mine_pick"->Items.named(Material.GOLDEN_PICKAXE,"Insta-Mine Pick","Preset-only custom item");case "nuke"->Items.named(Material.TNT,"Nuke","Vanilla-friendly YetiPL nuke");default->null;};if(item==null)return null;var meta=item.getItemMeta();meta.getPersistentDataContainer().set(key,PersistentDataType.STRING,id);item.setItemMeta(meta);return item;}
    public String id(ItemStack item){if(item==null||item.getType().isAir()||!item.hasItemMeta())return null;return item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);}
    public boolean tagged(ItemStack item,String id){return id.equals(id(item));}
}
