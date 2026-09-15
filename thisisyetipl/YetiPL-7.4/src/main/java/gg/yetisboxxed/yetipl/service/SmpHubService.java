package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.YetiPLPlugin;
import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Locale;

public final class SmpHubService {
    private final YetiPLPlugin plugin;
    private final YamlStore store;
    public SmpHubService(YetiPLPlugin plugin) {
        this.plugin = plugin;
        this.store = new YamlStore(plugin, "smp.yml");
    }
    public String keyForWorld(World world) {
        String n=world.getName().toLowerCase(Locale.ROOT);
        var sec=plugin.getConfig().getConfigurationSection("smp.worlds");
        if(sec!=null) for(String k:sec.getKeys(false))
            if(n.equals(plugin.getConfig().getString("smp.worlds."+k+".world-name","").toLowerCase(Locale.ROOT))) return k;
        return null;
    }
    public void setSpawn(String id, Location loc) {
        store.yaml().set("spawns."+id+".world", loc.getWorld().getName());
        store.yaml().set("spawns."+id+".x", loc.getX());
        store.yaml().set("spawns."+id+".y", loc.getY());
        store.yaml().set("spawns."+id+".z", loc.getZ());
        store.yaml().set("spawns."+id+".yaw", loc.getYaw());
        store.yaml().set("spawns."+id+".pitch", loc.getPitch());
        store.save();
    }
    public Location spawn(String id) {
        String p="spawns."+id+".";
        World w=Bukkit.getWorld(store.yaml().getString(p+"world",""));
        if(w==null) return null;
        return new Location(w,store.yaml().getDouble(p+"x"),store.yaml().getDouble(p+"y"),
                store.yaml().getDouble(p+"z"),(float)store.yaml().getDouble(p+"yaw"),(float)store.yaml().getDouble(p+"pitch"));
    }
    public boolean teleport(Player p, String id) {
        Location l=spawn(id); if(l==null) return false; p.teleport(l); return true;
    }
    public void applyBorders() {
        double size=plugin.getConfig().getDouble("smp.world-border-size",50000);
        var sec=plugin.getConfig().getConfigurationSection("smp.worlds");
        if(sec==null)return;
        for(String k:sec.getKeys(false)){
            World w=Bukkit.getWorld(plugin.getConfig().getString("smp.worlds."+k+".world-name",""));
            if(w!=null) w.getWorldBorder().setSize(size);
        }
    }
}
