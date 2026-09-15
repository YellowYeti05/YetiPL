package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class CrateService {
    private final YamlStore store;
    public CrateService(JavaPlugin plugin){store=new YamlStore(plugin,"crates.yml");}
    public void create(String name){store.yaml().set("crates."+name.toLowerCase()+".enabled",true);store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("crates");return s==null?Set.of():s.getKeys(false);}
    public void bind(String name,Location l){create(name);store.yaml().set("crates."+name.toLowerCase()+".location",Locations.blockKey(l));store.save();}
    public String at(Location l){String k=Locations.blockKey(l);for(String n:names())if(k.equals(store.yaml().getString("crates."+n+".location")))return n;return null;}
    public void bindKey(String name,ItemStack item){create(name);store.yaml().set("crates."+name.toLowerCase()+".key",item);store.save();}
    public ItemStack key(String name){return store.yaml().getItemStack("crates."+name.toLowerCase()+".key");}
    public void addReward(String name,ItemStack item){List<ItemStack> r=new ArrayList<>();for(Object o:store.yaml().getList("crates."+name.toLowerCase()+".rewards",List.of()))if(o instanceof ItemStack i)r.add(i);r.add(item.clone());store.yaml().set("crates."+name.toLowerCase()+".rewards",r);store.save();}
    public ItemStack randomReward(String name){List<?> raw=store.yaml().getList("crates."+name.toLowerCase()+".rewards",List.of());List<ItemStack> r=raw.stream().filter(ItemStack.class::isInstance).map(ItemStack.class::cast).toList();if(r.isEmpty())return null;return r.get(new Random().nextInt(r.size())).clone();}
    public boolean matchesKey(Player p,String name){ItemStack wanted=key(name);return wanted!=null&&p.getInventory().getItemInMainHand().isSimilar(wanted);}
}
