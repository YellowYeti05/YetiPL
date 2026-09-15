package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Items;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.*;

public final class CosmeticService {
    private final JavaPlugin plugin; private final YamlStore store; private final CoinsService coins; private BukkitTask task;
    private final Map<String,Long> prices=Map.of("enchanted_player",500L,"golden_trail",750L,"yeti_aura",1000L);
    public CosmeticService(JavaPlugin plugin,CoinsService coins){this.plugin=plugin;this.coins=coins;this.store=new YamlStore(plugin,"cosmetics.yml");}
    public void start(){if(task!=null)return;task=Bukkit.getScheduler().runTaskTimer(plugin,()->{for(Player p:Bukkit.getOnlinePlayers()){String equipped=store.yaml().getString("players."+p.getUniqueId()+".equipped","");if(equipped.equals("golden_trail"))p.getWorld().spawnParticle(Particle.WAX_ON,p.getLocation().add(0,.1,0),3,.2,.05,.2,0);else if(equipped.equals("yeti_aura"))p.getWorld().spawnParticle(Particle.SNOWFLAKE,p.getLocation().add(0,1,0),5,.5,.7,.5,0);else if(equipped.equals("enchanted_player"))applyGlint(p);}},20L,10L);}
    public void stop(){if(task!=null)task.cancel();task=null;}
    public boolean owns(Player p,String id){return store.yaml().getBoolean("players."+p.getUniqueId()+".owned."+id,false);}
    public boolean buy(Player p,String id){Long price=prices.get(id);if(price==null||owns(p,id))return false;if(!coins.spend(p,price,"cosmetic:"+id))return false;store.yaml().set("players."+p.getUniqueId()+".owned."+id,true);store.save();return true;}
    public void equip(Player p,String id){if(!owns(p,id))return;store.yaml().set("players."+p.getUniqueId()+".equipped",id);store.save();}
    public void open(Player p){Inventory i=Bukkit.createInventory(null,27,"✦ YETIPL COSMETICS ✦");i.setItem(11,item("enchanted_player",Material.ENCHANTED_BOOK,p));i.setItem(13,item("golden_trail",Material.GOLD_NUGGET,p));i.setItem(15,item("yeti_aura",Material.SNOWBALL,p));p.openInventory(i);}
    private ItemStack item(String id,Material m,Player p){return Items.named(m,id.replace('_',' ').toUpperCase(),owns(p,id)?"OWNED • Click to equip":"Price: "+prices.get(id)+" ❄","Permanent unlock • cosmetic only");}
    public String idForSlot(int slot){return switch(slot){case 11->"enchanted_player";case 13->"golden_trail";case 15->"yeti_aura";default->null;};}
    private void applyGlint(Player p){for(ItemStack i:p.getInventory().getArmorContents())if(i!=null&&!i.getType().isAir())try{var meta=i.getItemMeta();Method m=meta.getClass().getMethod("setEnchantmentGlintOverride",Boolean.class);m.invoke(meta,Boolean.TRUE);i.setItemMeta(meta);}catch(Exception ignored){}}
}
