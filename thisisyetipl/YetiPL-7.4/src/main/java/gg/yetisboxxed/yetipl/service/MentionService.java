package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MentionService {
    private final YamlStore store;
    public MentionService(JavaPlugin plugin){store=new YamlStore(plugin,"mentions.yml");}
    public boolean enabled(Player p){return store.yaml().getBoolean("players."+p.getUniqueId()+".enabled",true);}
    public void enabled(Player p,boolean value){store.yaml().set("players."+p.getUniqueId()+".enabled",value);store.save();}
}
