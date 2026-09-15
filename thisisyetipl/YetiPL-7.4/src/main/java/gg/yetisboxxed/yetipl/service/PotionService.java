package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.util.Items;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class PotionService {
    private final JavaPlugin plugin;
    public PotionService(JavaPlugin plugin){this.plugin=plugin;}
    public void open(Player p){Inventory inv=plugin.getServer().createInventory(null,27,"✦ YetiPL Potion Maker ✦");inv.setItem(10,Items.named(Material.POTION,"Speed Potion","Click to create Speed V • 2:30"));inv.setItem(12,Items.named(Material.SPLASH_POTION,"Strength Splash","Click to create Strength III • 1:00"));inv.setItem(14,Items.named(Material.LINGERING_POTION,"Yeti Rush","Speed V + Jump III • 2:30"));inv.setItem(16,Items.named(Material.TIPPED_ARROW,"Custom Arrow","Resistance II • 1:00"));p.openInventory(inv);}
    public ItemStack speed(){return make(Material.POTION,"Yeti Speed Potion",Color.AQUA,new PotionEffect(PotionEffectType.SPEED,20*150,4));}
    public ItemStack strength(){return make(Material.SPLASH_POTION,"Yeti Strength Splash",Color.RED,new PotionEffect(PotionEffectType.STRENGTH,20*60,2));}
    public ItemStack rush(){return make(Material.LINGERING_POTION,"Yeti Rush",Color.AQUA,new PotionEffect(PotionEffectType.SPEED,20*150,4),new PotionEffect(PotionEffectType.JUMP_BOOST,20*150,2));}
    public ItemStack arrow(){return make(Material.TIPPED_ARROW,"Yeti Resistance Arrow",Color.YELLOW,new PotionEffect(PotionEffectType.RESISTANCE,20*60,1));}
    private ItemStack make(Material type,String name,Color color,PotionEffect... effects){ItemStack i=Items.named(type,name,"Created with YetiPL");PotionMeta m=(PotionMeta)i.getItemMeta();m.setColor(color);for(PotionEffect e:effects)m.addCustomEffect(e,true);i.setItemMeta(m);return i;}
}
