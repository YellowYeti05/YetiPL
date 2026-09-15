package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import gg.yetisboxxed.yetipl.util.Format;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Display;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class HologramService {
    private final JavaPlugin plugin; private final YamlStore store; private final Map<String,UUID> entities=new HashMap<>();
    public HologramService(JavaPlugin plugin){this.plugin=plugin;store=new YamlStore(plugin,"holograms.yml");}
    public void load(){var s=store.yaml().getConfigurationSection("holograms");if(s!=null)for(String n:s.getKeys(false))spawn(n);}
    public void create(String name,Location l,String text){String b="holograms."+name.toLowerCase();store.yaml().set(b+".location",Locations.encode(l));store.yaml().set(b+".text",text);store.save();spawn(name.toLowerCase());}
    public void delete(String name){UUID id=entities.remove(name.toLowerCase());if(id!=null&&Bukkit.getEntity(id)!=null)Bukkit.getEntity(id).remove();store.yaml().set("holograms."+name.toLowerCase(),null);store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("holograms");return s==null?Set.of():s.getKeys(false);}
    private void spawn(String name){Location l=Locations.decode(store.yaml().getString("holograms."+name+".location"));if(l==null)return;UUID old=entities.get(name);if(old!=null&&Bukkit.getEntity(old)!=null)Bukkit.getEntity(old).remove();TextDisplay d=l.getWorld().spawn(l,TextDisplay.class);d.text(Format.component(store.yaml().getString("holograms."+name+".text","<gold>YetiPL</gold>")));d.setBillboard(Display.Billboard.CENTER);d.setPersistent(true);entities.put(name,d.getUniqueId());}
}
