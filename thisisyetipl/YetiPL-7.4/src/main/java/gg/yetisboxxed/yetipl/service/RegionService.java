package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class RegionService {
    private final YamlStore store;
    public RegionService(JavaPlugin plugin){store=new YamlStore(plugin,"regions.yml");}
    public void create(String name,SelectionService.Selection s){String b="regions."+name.toLowerCase();store.yaml().set(b+".pos1",Locations.encode(s.pos1()));store.yaml().set(b+".pos2",Locations.encode(s.pos2()));for(String f:List.of("pvp","pve","break","place","interact","containers","projectiles","vehicles"))store.yaml().set(b+".flags."+f,true);store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("regions");return s==null?Set.of():s.getKeys(false);}
    public String at(Location l){for(String n:names())if(contains(n,l))return n;return null;}
    public boolean contains(String n,Location l){Location a=Locations.decode(store.yaml().getString("regions."+n+".pos1")),b=Locations.decode(store.yaml().getString("regions."+n+".pos2"));if(a==null||b==null||a.getWorld()!=l.getWorld())return false;return l.getBlockX()>=Math.min(a.getBlockX(),b.getBlockX())&&l.getBlockX()<=Math.max(a.getBlockX(),b.getBlockX())&&l.getBlockY()>=Math.min(a.getBlockY(),b.getBlockY())&&l.getBlockY()<=Math.max(a.getBlockY(),b.getBlockY())&&l.getBlockZ()>=Math.min(a.getBlockZ(),b.getBlockZ())&&l.getBlockZ()<=Math.max(a.getBlockZ(),b.getBlockZ());}
    public boolean flag(String n,String f){return store.yaml().getBoolean("regions."+n+".flags."+f,true);}
    public void flag(String n,String f,boolean value){store.yaml().set("regions."+n+".flags."+f,value);store.save();}
    public void listMaterial(String n,String type,Material m,boolean add){String p="regions."+n+"."+type;List<String> l=new ArrayList<>(store.yaml().getStringList(p));if(add&&!l.contains(m.name()))l.add(m.name());if(!add)l.remove(m.name());store.yaml().set(p,l);store.save();}
    public boolean blockedHand(String n,Material m){return store.yaml().getStringList("regions."+n+".blockedhand").contains(m.name());}
    public boolean requiresHand(String n,Material m){List<String> r=store.yaml().getStringList("regions."+n+".requirehand");return r.isEmpty()||r.contains(m.name());}
    public void delete(String n){store.yaml().set("regions."+n.toLowerCase(),null);store.save();}
}
