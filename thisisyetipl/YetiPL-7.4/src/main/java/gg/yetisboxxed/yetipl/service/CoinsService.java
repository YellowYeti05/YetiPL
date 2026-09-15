package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class CoinsService {
    private final YamlStore store;
    public CoinsService(JavaPlugin plugin){store=new YamlStore(plugin,"coins.yml");}
    public long balance(UUID id){return Math.max(0,store.yaml().getLong("players."+id+".balance",0));}
    public boolean infinite(Player p){return p.hasPermission("yetipl.yeticoins.infinite");}
    public boolean spend(Player p,long amount,String reason){if(amount<0)return false;if(infinite(p))return true;long b=balance(p.getUniqueId());if(b<amount)return false;set(p.getUniqueId(),b-amount,"SPEND: "+reason);return true;}
    public void add(UUID id,long amount,String reason){set(id,Math.max(0,balance(id)+amount),"ADD "+amount+": "+reason);}
    public void take(UUID id,long amount,String reason){set(id,Math.max(0,balance(id)-Math.max(0,amount)),"TAKE "+amount+": "+reason);}
    public void set(UUID id,long value,String reason){store.yaml().set("players."+id+".balance",Math.max(0,value));List<String> h=new ArrayList<>(store.yaml().getStringList("players."+id+".history"));h.add(0,Instant.now()+" | "+reason+" | balance="+Math.max(0,value));if(h.size()>100)h=h.subList(0,100);store.yaml().set("players."+id+".history",h);store.save();}
    public List<String> history(UUID id){return store.yaml().getStringList("players."+id+".history");}
    public List<OfflinePlayer> top(){List<OfflinePlayer> out=new ArrayList<>();return out;}
}
