package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ArchaeologyService {
    private final YamlStore store;
    public ArchaeologyService(JavaPlugin plugin){store=new YamlStore(plugin,"archaeology.yml");}
    public void create(String name,SelectionService.Selection s){String b="regions."+name.toLowerCase();store.yaml().set(b+".pos1",Locations.encode(s.pos1()));store.yaml().set(b+".pos2",Locations.encode(s.pos2()));store.yaml().set(b+".respawn-ticks",12000);store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("regions");return s==null?Set.of():s.getKeys(false);}
    public String at(Location l){for(String n:names()){Location a=Locations.decode(store.yaml().getString("regions."+n+".pos1")),b=Locations.decode(store.yaml().getString("regions."+n+".pos2"));if(a!=null&&b!=null&&a.getWorld()==l.getWorld()&&l.getBlockX()>=Math.min(a.getBlockX(),b.getBlockX())&&l.getBlockX()<=Math.max(a.getBlockX(),b.getBlockX())&&l.getBlockY()>=Math.min(a.getBlockY(),b.getBlockY())&&l.getBlockY()<=Math.max(a.getBlockY(),b.getBlockY())&&l.getBlockZ()>=Math.min(a.getBlockZ(),b.getBlockZ())&&l.getBlockZ()<=Math.max(a.getBlockZ(),b.getBlockZ()))return n;}return null;}
    public void addReward(String name,ItemStack item){String p="regions."+name.toLowerCase()+".rewards";List<ItemStack> l=new ArrayList<>();for(Object o:store.yaml().getList(p,List.of()))if(o instanceof ItemStack i)l.add(i);l.add(item.clone());store.yaml().set(p,l);store.save();}
    public ItemStack reward(String name){List<?> raw=store.yaml().getList("regions."+name+".rewards",List.of());List<ItemStack> l=raw.stream().filter(ItemStack.class::isInstance).map(ItemStack.class::cast).toList();return l.isEmpty()?null:l.get(new Random().nextInt(l.size())).clone();}
    public long respawn(String name){return store.yaml().getLong("regions."+name+".respawn-ticks",12000);}
}
