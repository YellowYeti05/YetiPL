package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ShopService {
    private final YamlStore store; private final CoinsService coins;
    private final Map<String,Long> prices=new LinkedHashMap<>();
    public ShopService(JavaPlugin plugin,CoinsService coins){this.coins=coins;store=new YamlStore(plugin,"shop.yml");prices.put("throwable_tnt",4000L);prices.put("tp_pads",6000L);prices.put("insta_mine_pick",7500L);prices.put("one_hit_sword",9000L);prices.put("nuke",12500L);}
    public Map<String,Long> prices(){return Map.copyOf(prices);}
    public boolean owns(Player p,String id){return store.yaml().getBoolean("players."+p.getUniqueId()+"."+id,false);}
    public boolean buy(Player p,String id){Long price=prices.get(id);if(price==null||owns(p,id))return false;if(!coins.spend(p,price,"shop:"+id))return false;store.yaml().set("players."+p.getUniqueId()+"."+id,true);store.save();return true;}
}
