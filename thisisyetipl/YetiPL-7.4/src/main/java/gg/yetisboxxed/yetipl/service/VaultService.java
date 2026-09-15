package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class VaultService {
    private final JavaPlugin plugin; private final YamlStore store; private final Map<UUID,Integer> open=new HashMap<>();
    public VaultService(JavaPlugin plugin){this.plugin=plugin;store=new YamlStore(plugin,"vaults.yml");}
    public void open(Player p,int number){number=Math.max(1,Math.min(10,number));Inventory inv=Bukkit.createInventory(null,54,"✦ YetiPL Vault "+number+" ✦");List<?> raw=store.yaml().getList("players."+p.getUniqueId()+"."+number,List.of());ItemStack[] items=raw.stream().filter(ItemStack.class::isInstance).map(ItemStack.class::cast).toArray(ItemStack[]::new);inv.setContents(Arrays.copyOf(items,54));open.put(p.getUniqueId(),number);p.openInventory(inv);}
    public boolean isVault(Player p,String title){return open.containsKey(p.getUniqueId())&&title.startsWith("✦ YetiPL Vault ");}
    public void save(Player p,Inventory inv){Integer n=open.remove(p.getUniqueId());if(n==null)return;store.yaml().set("players."+p.getUniqueId()+"."+n,Arrays.asList(inv.getContents()));store.save();}
}
