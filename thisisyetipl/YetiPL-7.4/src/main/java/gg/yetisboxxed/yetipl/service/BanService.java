package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.*;

public final class BanService {
    private final YamlStore store;
    public BanService(JavaPlugin plugin){store=new YamlStore(plugin,"bans.yml");}
    public void ban(UUID id,String name,long millis,String reason,String by){String b="players."+id;store.yaml().set(b+".name",name);store.yaml().set(b+".until",System.currentTimeMillis()+Math.max(1000,millis));store.yaml().set(b+".reason",reason);store.yaml().set(b+".by",by);store.yaml().set(b+".time",Instant.now().toString());store.save();}
    public void banIp(String ip,long millis,String reason,String by){String b="ips."+ip.replace('.','_').replace(':','-');store.yaml().set(b+".raw",ip);store.yaml().set(b+".until",System.currentTimeMillis()+Math.max(1000,millis));store.yaml().set(b+".reason",reason);store.yaml().set(b+".by",by);store.save();}
    public String active(UUID id){String b="players."+id;if(!store.yaml().contains(b+".until"))return null;long until=store.yaml().getLong(b+".until");if(until<=System.currentTimeMillis()){store.yaml().set(b,null);store.save();return null;}return store.yaml().getString(b+".reason","No reason")+" | remaining "+format(until-System.currentTimeMillis());}
    public String activeIp(String ip){var sec=store.yaml().getConfigurationSection("ips");if(sec==null)return null;for(String k:sec.getKeys(false)){String b="ips."+k;if(ip.equals(store.yaml().getString(b+".raw"))){long until=store.yaml().getLong(b+".until");if(until<=System.currentTimeMillis()){store.yaml().set(b,null);store.save();return null;}return store.yaml().getString(b+".reason","No reason")+" | remaining "+format(until-System.currentTimeMillis());}}return null;}
    public List<String> list(){List<String> out=new ArrayList<>();var sec=store.yaml().getConfigurationSection("players");if(sec!=null)for(String k:sec.getKeys(false)){try{UUID id=UUID.fromString(k);String a=active(id);if(a!=null)out.add(store.yaml().getString("players."+k+".name",k)+" | "+a);}catch(Exception ignored){}}return out;}
    public static long parse(String s){if(s==null||s.length()<2)return -1;try{long n=Long.parseLong(s.substring(0,s.length()-1));return switch(Character.toLowerCase(s.charAt(s.length()-1))){case 's'->n*1000L;case 'm'->n*60000L;case 'h'->n*3600000L;case 'd'->n*86400000L;case 'w'->n*604800000L;default->-1;};}catch(Exception e){return -1;}}
    private static String format(long ms){long s=Math.max(0,ms/1000);long d=s/86400;s%=86400;long h=s/3600;s%=3600;long m=s/60;return (d>0?d+"d ":"")+(h>0?h+"h ":"")+(m>0?m+"m":"<1m");}
}
