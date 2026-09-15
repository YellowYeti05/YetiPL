package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class PermissionsBridgeService {
    private final JavaPlugin plugin;
    public PermissionsBridgeService(JavaPlugin plugin){this.plugin=plugin;}
    public boolean available(){return Bukkit.getPluginManager().getPlugin("LuckPerms")!=null;}
    public void open(Player p){Inventory i=Bukkit.createInventory(null,27,"✦ YetiPL Permissions ✦");i.setItem(11,Items.named(Material.PLAYER_HEAD,"Users","Use /lpgui user <player> <permission> <true|false>"));i.setItem(13,Items.named(available()?Material.GOLD_BLOCK:Material.REDSTONE_BLOCK,"LuckPerms",available()?"CONNECTED":"NOT FOUND"));i.setItem(15,Items.named(Material.NAME_TAG,"Groups","Use /lpgui group <group> <permission> <true|false>"));p.openInventory(i);}
    public boolean set(String type,String target,String permission,boolean value){if(!available())return false;String cmd="lp "+type+" "+target+" permission set "+permission+" "+value;return Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd);}
}
