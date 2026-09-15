package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class PortalService {
    private final YamlStore store;
    public PortalService(JavaPlugin plugin){store=new YamlStore(plugin,"portals.yml");}
    public void create(String name,SelectionService.Selection s,Location destination){String b="portals."+name.toLowerCase();store.yaml().set(b+".pos1",Locations.encode(s.pos1()));store.yaml().set(b+".pos2",Locations.encode(s.pos2()));store.yaml().set(b+".destination",Locations.encode(destination));store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("portals");return s==null?Set.of():s.getKeys(false);}
    public Location destinationAt(Location l){for(String n:names()){Location a=Locations.decode(store.yaml().getString("portals."+n+".pos1")),b=Locations.decode(store.yaml().getString("portals."+n+".pos2"));if(a==null||b==null||a.getWorld()!=l.getWorld())continue;if(l.getBlockX()>=Math.min(a.getBlockX(),b.getBlockX())&&l.getBlockX()<=Math.max(a.getBlockX(),b.getBlockX())&&l.getBlockY()>=Math.min(a.getBlockY(),b.getBlockY())&&l.getBlockY()<=Math.max(a.getBlockY(),b.getBlockY())&&l.getBlockZ()>=Math.min(a.getBlockZ(),b.getBlockZ())&&l.getBlockZ()<=Math.max(a.getBlockZ(),b.getBlockZ()))return Locations.decode(store.yaml().getString("portals."+n+".destination"));}return null;}
}
