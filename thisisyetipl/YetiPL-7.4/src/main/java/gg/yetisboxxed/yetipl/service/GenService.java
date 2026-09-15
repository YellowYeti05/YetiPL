package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class GenService {
    private final YamlStore store;
    public GenService(JavaPlugin plugin){store=new YamlStore(plugin,"gens.yml");}
    public void create(String name,SelectionService.Selection s){String b="gens."+name.toLowerCase();store.yaml().set(b+".pos1",Locations.encode(s.pos1()));store.yaml().set(b+".pos2",Locations.encode(s.pos2()));store.yaml().set(b+".delay-ticks",100);store.yaml().set(b+".blocks",List.of("STONE"));store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("gens");return s==null?Set.of():s.getKeys(false);}
    public void setDelay(String name,long ticks){store.yaml().set("gens."+name.toLowerCase()+".delay-ticks",Math.max(1,ticks));store.save();}
    public long delay(String name){return store.yaml().getLong("gens."+name+".delay-ticks",100);}
    public void addBlock(String name,Material m){String p="gens."+name.toLowerCase()+".blocks";List<String> l=new ArrayList<>(store.yaml().getStringList(p));if(!l.contains(m.name()))l.add(m.name());store.yaml().set(p,l);store.save();}
    public String at(Location l){for(String n:names()){Location a=Locations.decode(store.yaml().getString("gens."+n+".pos1")),b=Locations.decode(store.yaml().getString("gens."+n+".pos2"));if(a==null||b==null||a.getWorld()!=l.getWorld())continue;if(l.getBlockX()>=Math.min(a.getBlockX(),b.getBlockX())&&l.getBlockX()<=Math.max(a.getBlockX(),b.getBlockX())&&l.getBlockY()>=Math.min(a.getBlockY(),b.getBlockY())&&l.getBlockY()<=Math.max(a.getBlockY(),b.getBlockY())&&l.getBlockZ()>=Math.min(a.getBlockZ(),b.getBlockZ())&&l.getBlockZ()<=Math.max(a.getBlockZ(),b.getBlockZ()))return n;}return null;}
    public Material randomBlock(String name){List<String> l=store.yaml().getStringList("gens."+name+".blocks");if(l.isEmpty())return Material.STONE;Material m=Material.matchMaterial(l.get(new Random().nextInt(l.size())));return m==null?Material.STONE:m;}
}
