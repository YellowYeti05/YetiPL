package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class WarpService {
    private final YamlStore store;
    public WarpService(JavaPlugin plugin){store=new YamlStore(plugin,"warps.yml");}
    public void set(String name,Location l){store.yaml().set("warps."+name.toLowerCase(),Locations.encode(l));store.save();}
    public Location get(String name){return Locations.decode(store.yaml().getString("warps."+name.toLowerCase()));}
    public void delete(String name){store.yaml().set("warps."+name.toLowerCase(),null);store.save();}
    public Set<String> names(){var sec=store.yaml().getConfigurationSection("warps");return sec==null?Set.of():sec.getKeys(false);}
}
