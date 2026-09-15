package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.Set;

public final class BugService {
    private final YamlStore store;
    public BugService(JavaPlugin plugin){store=new YamlStore(plugin,"bugs.yml");}
    public int submit(Player p,String text){int id=store.yaml().getInt("next-id",1);store.yaml().set("next-id",id+1);String b="reports."+id;store.yaml().set(b+".player",p.getUniqueId().toString());store.yaml().set(b+".name",p.getName());store.yaml().set(b+".description",text);store.yaml().set(b+".location",Locations.encode(p.getLocation()));store.yaml().set(b+".status","OPEN");store.yaml().set(b+".time",Instant.now().toString());store.save();return id;}
    public Set<String> ids(){var s=store.yaml().getConfigurationSection("reports");return s==null?Set.of():s.getKeys(false);}
    public String summary(String id){String b="reports."+id;return "#"+id+" ["+store.yaml().getString(b+".status","OPEN")+"] "+store.yaml().getString(b+".name","?")+": "+store.yaml().getString(b+".description","");}
    public void status(String id,String value){store.yaml().set("reports."+id+".status",value);store.save();}
}
