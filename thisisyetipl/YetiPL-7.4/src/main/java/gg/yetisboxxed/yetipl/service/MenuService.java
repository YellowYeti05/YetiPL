package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.util.Items;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MenuService {
    private final JavaPlugin plugin; private final PresetService presets; private final CoinsService coins; private final ShopService shop; private final CustomItemService items;
    public MenuService(JavaPlugin plugin,PresetService presets,CoinsService coins,ShopService shop,CustomItemService items){this.plugin=plugin;this.presets=presets;this.coins=coins;this.shop=shop;this.items=items;}
    public void preset(Player p){Inventory i=Bukkit.createInventory(null,27,"✦ YETIBOXXED PRESETS ✦");if(!presets.owns(p.getUniqueId()))i.setItem(13,Items.named(Material.GOLD_BLOCK,"Buy Preset",presets.purchaseCost()+" ❄","One Preset per player"));else{i.setItem(10,Items.named(Material.COMPASS,"Preset Home","Teleport to your Preset"));i.setItem(12,Items.named(Material.EXPERIENCE_BOTTLE,"Upgrades","Level "+presets.level(p.getUniqueId())+" / 10","Border: "+presets.size(p.getUniqueId())+"×"+presets.size(p.getUniqueId())));i.setItem(14,Items.named(Material.PLAYER_HEAD,"Access","Trust = visit • Hyper Trust = edit"));i.setItem(16,Items.named(Material.REDSTONE,"Reset Preset","Use /preset reset confirm"));}p.openInventory(i);}
    public void shop(Player p){Inventory i=Bukkit.createInventory(null,27,"✦ YETIBOXXED SHOP ✦");int slot=10;for(var e:shop.prices().entrySet()){ItemStack it=items.create(e.getKey());var meta=it.getItemMeta();meta.lore(java.util.List.of(Component.text((shop.owns(p,e.getKey())?"OWNED • Click to get":"Price: "+e.getValue()+" ❄"),shop.owns(p,e.getKey())?NamedTextColor.GOLD:NamedTextColor.YELLOW),Component.text("Use: your Preset only",NamedTextColor.GRAY)));it.setItemMeta(meta);i.setItem(slot,it);slot+=2;}p.openInventory(i);}
    public void upgrades(Player p){Inventory i=Bukkit.createInventory(null,27,"✦ PLOT UPGRADES ✦");if(!presets.owns(p.getUniqueId()))i.setItem(13,Items.named(Material.BARRIER,"No Preset","Buy one with /preset"));else{int l=presets.level(p.getUniqueId());i.setItem(13,Items.named(l>=10?Material.NETHER_STAR:Material.GOLD_BLOCK,l>=10?"MAX LEVEL":"Upgrade to Level "+(l+1),"Current: "+presets.size(p.getUniqueId())+"×"+presets.size(p.getUniqueId()),l>=10?"MAX LEVEL":"Cost: "+presets.nextUpgradeCost(p.getUniqueId())+" ❄"));}p.openInventory(i);}
    public void commands(Player p){Inventory i=Bukkit.createInventory(null,27,"✦ YETIBOXXED COMMANDS ✦");i.setItem(9,Items.named(Material.COMPASS,"Player","/rules /discord /warp /preset"));i.setItem(10,Items.named(Material.GOLD_INGOT,"Economy","/yeticoins /shop"));i.setItem(11,Items.named(Material.CHEST,"Storage","/vault /ec"));i.setItem(12,Items.named(Material.GOLDEN_AXE,"Regions & Gens","/rg /gen /arch"));i.setItem(13,Items.named(Material.POTION,"Custom Tools","/potionmaker /trim /itemdisguise"));i.setItem(14,Items.named(Material.NAME_TAG,"Ranks & Chat","/affix /chatcolor /chatcooldown"));i.setItem(15,Items.named(Material.TNT,"Crates & Custom Items","/crate /bindcrate /shop"));i.setItem(16,Items.named(Material.REDSTONE,"Moderation","/ban /freeze /vanish /spectate"));i.setItem(17,Items.named(Material.NETHER_STAR,"YetiPL","/yetipl /updates"));p.openInventory(i);}
}
