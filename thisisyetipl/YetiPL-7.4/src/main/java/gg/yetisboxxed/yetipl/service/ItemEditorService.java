package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.util.Format;
import gg.yetisboxxed.yetipl.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ItemEditorService {
    private final JavaPlugin plugin;
    public ItemEditorService(JavaPlugin plugin){this.plugin=plugin;}
    public void open(Player p){Inventory i=Bukkit.createInventory(null,27,"✦ YetiPL Item Editor ✦");i.setItem(10,Items.named(Material.NAME_TAG,"Name / Birdflop","Use /nbt name <format>"));i.setItem(12,Items.named(Material.BOOK,"Lore","/nbt lore add <format>","/nbt lore clear"));i.setItem(14,Items.named(Material.ENCHANTED_BOOK,"Cosmetic Glint","/nbt glint"));i.setItem(16,Items.named(Material.PAPER,"Persistent Data","/nbt data <key> <value>"));p.openInventory(i);}
    private ItemStack hand(Player p){return p.getInventory().getItemInMainHand();}
    public boolean name(Player p,String text){ItemStack i=hand(p);if(i.getType().isAir())return false;var m=i.getItemMeta();m.displayName(Format.component(text));i.setItemMeta(m);return true;}
    public boolean loreAdd(Player p,String text){ItemStack i=hand(p);if(i.getType().isAir())return false;var m=i.getItemMeta();List<net.kyori.adventure.text.Component> lore=m.lore()==null?new ArrayList<>():new ArrayList<>(m.lore());lore.add(Format.component(text));m.lore(lore);i.setItemMeta(m);return true;}
    public boolean loreClear(Player p){ItemStack i=hand(p);if(i.getType().isAir())return false;var m=i.getItemMeta();m.lore(List.of());i.setItemMeta(m);return true;}
    public boolean glint(Player p){ItemStack i=hand(p);if(i.getType().isAir())return false;try{var m=i.getItemMeta();Method method=m.getClass().getMethod("setEnchantmentGlintOverride",Boolean.class);method.invoke(m,Boolean.TRUE);i.setItemMeta(m);return true;}catch(Exception e){return false;}}
    public boolean data(Player p,String key,String value){ItemStack i=hand(p);if(i.getType().isAir()||!key.matches("[a-z0-9._-]+"))return false;var m=i.getItemMeta();m.getPersistentDataContainer().set(new NamespacedKey(plugin,key),PersistentDataType.STRING,value);i.setItemMeta(m);return true;}
}
