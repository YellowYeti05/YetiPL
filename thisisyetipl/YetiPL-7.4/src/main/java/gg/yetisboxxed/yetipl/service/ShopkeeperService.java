package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class ShopkeeperService {
    private final YamlStore store;
    public ShopkeeperService(JavaPlugin plugin){store=new YamlStore(plugin,"shopkeepers.yml");}
    public void create(String name,Location l){Villager v=l.getWorld().spawn(l,Villager.class);v.customName(Component.text(name,NamedTextColor.GOLD));v.setCustomNameVisible(true);v.setAI(false);v.setInvulnerable(true);v.setPersistent(true);String b="shops."+name.toLowerCase();store.yaml().set(b+".uuid",v.getUniqueId().toString());store.yaml().set(b+".location",Locations.encode(l));store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("shops");return s==null?Set.of():s.getKeys(false);}
}
