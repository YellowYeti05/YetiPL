package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class RankService {
    private final YamlStore store;
    private final List<String> presets=List.of("OWNER","ADMIN","BUILDER","HELPER","JANITOR","VIP","MVP","ISOPOD","COPPER_BLOCK","AMONG_US","DEFAULT");
    public RankService(JavaPlugin plugin){store=new YamlStore(plugin,"ranks.yml");bootstrap();}
    private void bootstrap(){Map<String,String> defaults=Map.ofEntries(Map.entry("OWNER","<gradient:#FFD700:#FFF176:#B8860B><bold>OWNER</bold></gradient>"),Map.entry("ADMIN","<red>ADMIN</red>"),Map.entry("BUILDER","<dark_blue>BUILDER</dark_blue>"),Map.entry("HELPER","<gradient:#55FFFF:#0000AA>HELPER</gradient>"),Map.entry("JANITOR","<gold>JANITOR</gold>"),Map.entry("VIP","<green>VIP</green>"),Map.entry("MVP","<gradient:#5555FF:#55FF55>MVP</gradient>"),Map.entry("ISOPOD","<gradient:#FFFFFF:#808080>ISOPOD</gradient>"),Map.entry("COPPER_BLOCK","<gradient:#D97732:#B87333:#5F9E6E:#4FC3C7>COPPER BLOCK</gradient>"),Map.entry("AMONG_US","<gradient:#FF0000:#111111:#00FFFF>AMONG US</gradient>"),Map.entry("DEFAULT",""));for(var e:defaults.entrySet())if(!store.yaml().contains("presets."+e.getKey()+".prefix"))store.yaml().set("presets."+e.getKey()+".prefix",e.getValue());store.save();}
    public List<String> presets(){return presets;}
    public String rank(UUID id){return store.yaml().getString("players."+id+".rank","DEFAULT");}
    public void rank(UUID id,String rank){store.yaml().set("players."+id+".rank",rank.toUpperCase(Locale.ROOT));store.save();}
    public String prefix(UUID id){String r=rank(id);return store.yaml().getString("presets."+r+".prefix",r);}
    public void prefix(String rank,String text){store.yaml().set("presets."+rank.toUpperCase(Locale.ROOT)+".prefix",text);store.save();}
    public void suffix(String rank,String text){store.yaml().set("presets."+rank.toUpperCase(Locale.ROOT)+".suffix",text);store.save();}
    public String suffix(UUID id){return store.yaml().getString("presets."+rank(id)+".suffix","");}
    public void chatColor(String rank,String value){store.yaml().set("presets."+rank.toUpperCase(Locale.ROOT)+".chat-color",value);store.save();}
    public String chatColor(UUID id){return store.yaml().getString("presets."+rank(id)+".chat-color","<white>");}
    public void cooldown(String rank,double seconds){store.yaml().set("presets."+rank.toUpperCase(Locale.ROOT)+".cooldown",Math.max(0,seconds));store.save();}
    public double cooldown(Player p){if(p.hasPermission("yetipl.chatcooldown.bypass"))return 0;return store.yaml().getDouble("presets."+rank(p.getUniqueId())+".cooldown",3.0);}
}
